package com.blockadm.adm.downloadapk;

import android.content.Context;
import android.os.Bundle;

import com.blockadm.common.utils.L;
import com.blockadm.common.utils.T;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*************************************
 * 
 * @description TODO(多线程文件下载工具类)
 * @author 张辉
 * @date 2015-4-22 19:40:00
 * 
 **************************************/
public class DownloadFileUtils {
	private String mDownloadUrl;// 下载地址
	private long mFileSize;// 下载的文件大小
	private long mTotalReadSize;// 已读取的文件大小
	private long mBlock;// 每条线程下载的长度
	private int mThreadCount;// 下载的线程数
	private final int mThreadPoolNum = 5;// 线程池的大小
	private final int mBufferSize = 1024 * 100;// 缓冲区大小
	private String mFileName;// 存储在本地的文件名称
	private String mFilePath;// 存储的路径
	private HttpURLConnection urlConnection;
	private RandomAccessFile randomAccessFile;// 根据指定位置写入数据
	private URL uri;
	private CallbackBundle<Bundle> callback;// 下载的回调接口
	private ExecutorService executorService;// 固定大小的线程池
	private volatile boolean error = false;// 全局变量，使用volatile同步，下载产生异常时改变
	private File[] mTempFiles;// 保存thread的下载进度缓存文件的集合
	// 是否暂停
	private  boolean mIsPause = false;
	// 是否正在下载
	private  boolean isDownloading = false;

	private static DownloadFileUtils instance = null;
	private long[] startPositions;
	private long[] endPositions;
	// 定时器，用于更新下载进度
	private Timer mTimer;
	// 定时器执行的任务,用于更新下载进度
	private TimerTask mTask;

	// 下载成功
	public static final int STATUS_COMPLETE = 0;
	// 下载失败
	public static final int STATUS_FAIL = 1;
	// 下载进度,同时也用于,正在下载中状态
	public static final int STATUS_IS_DOWNLOADING = 2;

	// 下载暂停
	public static final int STATUS_PAUSE = 3;
	//未下载
	public static final int STATUS_UNDOWNLOAD = -1;
	//下载状态
	private  int downLoadStatus = STATUS_UNDOWNLOAD;

	public synchronized int getDownLoadStatus() {
		return downLoadStatus;
	}
	private ArrayList<CallbackBundle<Bundle>> callbackBundleArrayList = new ArrayList<>();
	private Context mContext;
	//下载线程
	DownloadFileThread downloadFileThread;
	private String downLoadPercent;

	public String getDownLoadPercent() {
		return downLoadPercent;
	}

	private DownloadFileUtils() {
	}

	public static DownloadFileUtils getInstance() {
		if (instance == null)
			instance = new DownloadFileUtils();
		return instance;
	}



	public void initBaseData(Context context, String url, String filePath,
                             String fileName, int threadCount, CallbackBundle<Bundle> callback) {
		this.mDownloadUrl = url;
		this.mContext = context;
		// this.url = url;
		this.mFilePath = filePath;
		this.mFileName = fileName;
		this.mThreadCount = threadCount;
		this.callback = callback;
		mTempFiles = new File[threadCount];
		// 创建固定大小的线程池5个
		executorService = Executors
				.newFixedThreadPool(mThreadPoolNum);

		putCallback(callback);
		L.d(getClass(), "初始化  downloadUrl="+ mDownloadUrl + "  filePath=" + filePath + " fileName=" +fileName + " threadCount=" + threadCount);
	}

	public void putCallback( CallbackBundle<Bundle> callback) {
		if(!callbackBundleArrayList.contains(callback)) {
			callbackBundleArrayList.add(callback);
			L.d(getClass(), "加入回调 : " + callback.toString());
		}

	}



	public synchronized void setPause(boolean isPause) {
		this.mIsPause = isPause;
		isDownloading = !isPause;
		if(isPause) {
			L.d(getClass(), "暂停下载线程====");


			destoryCaculatePercentTask();
			setStatusAndCallback(STATUS_PAUSE);

		}else {
			L.d(getClass(), "开启下载线程==== downloadFileThread=" + downloadFileThread);
			downloadFileThread = new DownloadFileThread();
			downloadFileThread.start();;
			startCaculatePercentTask();
		}

	}

	private void destoryCaculatePercentTask() {
		if (mTimer != null && mTask != null) {//暂停

			L.d(getClass(), "销毁定时器====");
			mTimer.cancel();
			mTask.cancel();
			mTimer = null;
			mTask = null;
		}
	}

	/**
	 * 停止下载
	 */
	public void stopDownLoad() {
		setPause(true);
	}

	/**
	 * 计算进度
	 */
	private void caculateProgress() {

		float progress = (float) mTotalReadSize * 100 / (float) mFileSize;
		String percent = "0";

		if (progress > 0) {
			if (progress >= 100)
				progress = 100;
			// 格式化十进制数
			DecimalFormat format = new DecimalFormat("0");
			percent = format.format(progress);

		}
		downLoadPercent = percent;
		Bundle bundle = new Bundle();
		bundle.putFloat("progress", progress);
		bundle.putString("percent", percent);
		L.d(getClass(), "计算进度 percent=" + percent);
		setStatusAndCallback(STATUS_IS_DOWNLOADING, bundle);
	}
	/**
	 * 开始下载
	 * @return
	 */
	public boolean startDownLoad() {

		if (!NetUtil.isNetWorkConnected(mContext)) {
			T.showShort(mContext, "请打开网络");
			return false;
		}
		if ( downloadFileThread == null || !downloadFileThread.isRunning()) {//如果线程未在下载

			if (mIsPause) {//如果是暂停状态,则开启下载
				L.d(getClass(), "暂停状态,开启下载");
				setPause(false);
			} else {
				L.d(getClass(), "未下载,新建下载任务");
				downloadFileThread = new DownloadFileThread();
				downloadFileThread.start();
				startCaculatePercentTask();
			}


			// 创建定时器，每秒计算一次下载进度
			return true;
		}
		return false;
	}


	private class DownloadFileThread extends Thread {

		private boolean isRunning = true;

		public boolean isRunning() {
			return isRunning;
		}

		public void setRunning(boolean isRunning) {
			this.isRunning = isRunning;
		}

		@Override
		public void run() {
			super.run();
			// 开始下载文件

			downloadFile();
			isRunning = false;
		}
	}


	private void startCaculatePercentTask() {
		L.d(getClass(), "开始定时器====");
		mTimer = new Timer();
		mTask = new TimerTask() {

			@Override
			public void run() {
				// 发送下载的进度消息
				caculateProgress();


			}

		};
		// 每隔1s执行一次
		mTimer.schedule(mTask, 1000, 1000);
	}

	/**
	 * 文件下载
	 * 
	 * @return true 下载成功 false 下载失败
	 */
	public boolean downloadFile() {
		

		try {
			
			uri = new URL(mDownloadUrl);
			// 连接
			urlConnection = (HttpURLConnection) uri.openConnection();
			// 连接方式GET
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Accept-Encoding", "identity");
			urlConnection.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			urlConnection.setRequestProperty("Accept-Language", "zh-CN");
			urlConnection.setRequestProperty("Referer", mDownloadUrl);
			urlConnection.setRequestProperty("Charset", "UTF-8");
			urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			urlConnection.setRequestProperty("Connection", "Keep-Alive");
			urlConnection.setRequestProperty("Range", "bytes=0"
					);
			urlConnection.connect();


			Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
			L.d(getClass(), "链接结果==" + urlConnection.getResponseCode() + " ,headerFields = " + headerFields);


			if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				
				isDownloading = true;
				L.d(getClass(), "开始下载APK===========" );
				mFileSize = urlConnection.getContentLength();// 获取文件的长度

				mBlock = mFileSize / mThreadCount   + 1;// 为了避免文件长度缺失每条线程下载长度增加1
				L.d(getClass(), "FileSize="+ mFileSize + " threadCount="+ mThreadCount + " block=" + mBlock);
				// 文件是否已下载好
				boolean isFileComplete = false;
				File fileAll = new File(mFilePath);
				for (File file : fileAll.listFiles()) {
					if (file != null && file.getName().equals(mFileName)) {
						if (file.length() == mFileSize) {
							isFileComplete = true;
							break;
						}
					}
				}

				if (isFileComplete) {//检测到文件下载好的,则通知下载完成
					setStatusAndCallback(STATUS_COMPLETE);
					isDownloading = false;
					downLoadPercent = "100";
					destoryCaculatePercentTask();
					return isDownloading;
				
				}else {
					File file = new File(mFilePath, mFileName);
					// 如果文件不存在，新建文件
					if (!file.getParentFile().exists())
						file.getParentFile().mkdirs();
					if(startPositions == null || endPositions == null) {
						startPositions = new long[mThreadCount];
						endPositions = new long[mThreadCount];
					}
					if(executorService == null || executorService.isShutdown()) {
						executorService = Executors
								.newFixedThreadPool(mThreadPoolNum);

					}

					// 同步计数器（倒计时）大于0时await()方法会阻塞程序继续执行
					CountDownLatch countDownLatch = new CountDownLatch(
							mThreadCount);// 线程计数器
					for (int i = 0; i < mThreadCount; i++) {

						long startPosition = i * mBlock;// 每条线程的开始读取位置
						long endPosition = (i + 1) * mBlock - 1;// 每条线程的读取结束位置
						randomAccessFile = new RandomAccessFile(file, "rwd");
						L.d(getClass(), "开始执行下载线程" + i +   "   startPosition="+ startPosition + " endPosition="+ endPosition);
						executorService.execute(new DownloadThread(i,
								startPosition, endPosition, randomAccessFile,
								countDownLatch));
					}

					countDownLatch.await();// 阻塞线程,直到countDownLatch线程数为零
					if(!mIsPause) {
						//如果不是暂停，则删除本地记录文件
						for (int i = 0; i < mThreadCount; i++) {
							// tempFiles[]保存thread的下载进度缓存文件的集合

							if (mTempFiles[i] != null && mTempFiles[i].exists())
								mTempFiles[i].delete();
						}
						executorService.shutdown();
					}
				}

				L.i(getClass(), "下载方法执行完成。。。");
			}else {
				isDownloading = false;

				setStatusAndCallback(STATUS_FAIL);
				setPause(true);
				return isDownloading;
			}
		} catch (Exception e) {

			error = true;
			e.printStackTrace();

		}

		isDownloading = false;
		if(mIsPause) {

			setStatusAndCallback(STATUS_PAUSE);
			setPause(true);
		}else if(error){//下载出错

			setStatusAndCallback(STATUS_FAIL);
			destoryCaculatePercentTask();
		}else {//下载完成
			setStatusAndCallback(STATUS_COMPLETE);
			destoryCaculatePercentTask();
		}
		
		return isDownloading;
	}

	private void setStatusAndCallback(int downLoadStatus) {
		Bundle bundle = new Bundle();
		bundle.putInt("downloadStatus", downLoadStatus);

		this.downLoadStatus = downLoadStatus;

		for(CallbackBundle<Bundle> callback : callbackBundleArrayList) {
			callback.callback(bundle);
			L.d(getClass(), "回调:" + callback + ", 下载状态当前" + this.downLoadStatus);
		}



	}

	private void setStatusAndCallback(int downLoadStatus, Bundle bundle) {

		bundle.putInt("downloadStatus", downLoadStatus);
		this.downLoadStatus = downLoadStatus;
		for(CallbackBundle<Bundle> callback : callbackBundleArrayList) {
			callback.callback(bundle);
			L.d(getClass(), "回调:" + callback + ", 下载状态当前" + this.downLoadStatus);
		}

	}

	class DownloadThread implements Runnable {
		private int threadId;
		private long startPosition;
		private long endPosition;
		private RandomAccessFile randomAccessFile;
		private CountDownLatch countDownLatch;
		private boolean isFirst = true;
	
		private File tempFile;

		public DownloadThread(int threadId, long startPosition,
				long endPosition, RandomAccessFile randomAccessFile,
				CountDownLatch countDownLatch) {
			this.threadId = threadId;
			this.startPosition = startPosition;
			this.endPosition = endPosition;
			this.randomAccessFile = randomAccessFile;
			this.countDownLatch = countDownLatch;
			error = false;
			// 下载的文件
			tempFile = new File(mFilePath + "/thread" + threadId,
					mFileName.replaceAll(".apk", ".position"));
			// 下载的文件集合
			mTempFiles[threadId] = tempFile;
			
			
			if (tempFile.exists()) {
				// 如果文件存在，以前已下载
				// isFirst = false;
				// 读取临时文件
				readPositionInfo();
			} else {
				tempFile.getParentFile().mkdirs();
				mTotalReadSize = 0;

			
			}
		}

		@Override
		public void run() {
			try {
				
			
				if (isFirst) {
					// 从头开始下载
					randomAccessFile.seek(startPosition);
					L.d(getClass(), "第一次，游标转移到  position=" + startPosition);
				} else {
					// 定位到已下载的位置
					startPosition = startPositions[threadId];
					endPosition = endPositions[threadId];
					//移动多少个byte位
					randomAccessFile.seek(startPosition);
					
					L.d(getClass(), "游标转移到  startPosition =" + startPosition + "  endPosition="+ endPosition + " threadId="+ threadId );
				}
				urlConnection.disconnect();
				urlConnection = null;
				urlConnection = (HttpURLConnection) uri.openConnection();
				// 连接方式GET
				urlConnection.setRequestMethod("GET");
				urlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
				urlConnection.setConnectTimeout(5 * 60 * 1000);// 设置连接超时
				urlConnection.setReadTimeout(60 * 1000);// 设置数据读取超时
				urlConnection.setAllowUserInteraction(true);// 允许用户交互
				urlConnection.setRequestProperty("Range", "bytes=" + startPosition
						+ "-" + endPosition);// 设置服务端传递文件块的位置


				InputStream inputStream = new BufferedInputStream(
						urlConnection.getInputStream(), mBufferSize);// 使用缓冲区读取文件

				byte[] b = new byte[mBufferSize];
				int len = 0;
				long readSize = startPosition;
				while (!error && (len = inputStream.read(b)) != -1 && !mIsPause) {
					randomAccessFile.write(b, 0, len);
					
					mTotalReadSize += len;
					readSize += len;

					savePositionInfo(readSize, endPosition, mTotalReadSize);
				}
				if(mIsPause) {
					L.d(getClass(), "线程" + threadId + "暂停下载。。。");
				}else if (!error)
					L.d(getClass(), "线程" + threadId + "下载完成。。。");
				else
					L.e(getClass(), "线程" + threadId + "下载失败。。。");
				inputStream.close();
				randomAccessFile.close();
//				if (!isStop) {
				urlConnection.disconnect();

//				}
			} catch (Exception e) {
				L.e(getClass(), "线程" + threadId + "下载失败。。。");
				error = true;
				e.printStackTrace();


			}
			countDownLatch.countDown();// 每条线程执行完之后减一
		}

		/**
		 * 
		 * ***********************************
		 *
		 * @Description: 将每条线程下载的开始和结束位置写入到临时文件中
		 * @param  @param startPosition 文件已经写到哪里的位置，即下一次读取的开始位置
		 * @param  @param endPosition 文件结束位置，刚开始时已经分好
		 * @param  @param totalReadSize 总共写入文件的大小
		 * @return void
		 * @throws 
		 * ***********************************
		 */
		private void savePositionInfo(long startPosition, long endPosition,
				long totalReadSize) {
			try {
				DataOutputStream outputStream = new DataOutputStream(
						new FileOutputStream(tempFile));
				outputStream.writeInt(startPositions.length);
				
				outputStream.writeLong(totalReadSize);
				L.d(getClass(), "开始写入临时文件,保存位置   startPositions.length=" + startPositions.length  + "  totalReadSize="+ totalReadSize + " startPosition="+ startPosition + " endPosition=" + endPosition);
				for (int i = 0; i < startPositions.length; i++) {
					outputStream.writeLong(startPosition);
					outputStream.writeLong(endPosition);
				}
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		/**
		 * 
		 * ***********************************
		 *
		 * @Description: 读取临时文件
		 * @param  
		 * @return void
		 * @throws 
		 * ***********************************
		 */
		private void readPositionInfo() {
			try {
				// 数据输入流
				DataInputStream inputStream = new DataInputStream(
						new FileInputStream(tempFile));
				//读取开始位置
				int startPositionsLength = inputStream.readInt();
				//读取上次文件写入的大小
				mTotalReadSize = inputStream.readLong();
				startPositions = new long[startPositionsLength];
				endPositions = new long[startPositionsLength];
				
				for (int i = 0; i < startPositionsLength; i++) {
					startPositions[i] = inputStream.readLong();
					endPositions[i] = inputStream.readLong();
					L.d(getClass(), "开始读取临时文件，下载位置   TotalReadSize" + mTotalReadSize +   "   startPositions[" + i +"]="+ startPositions[i] + " endPosition[" + i +"]="+ endPositions[i]);
				}
				isFirst = false;
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
