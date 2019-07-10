package com.blockadm.common.exception;

import android.content.Context;

import com.blockadm.common.utils.FileUtils;
import com.blockadm.common.utils.TimeUtils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * log日志统计保存
 * 
 * @author way
 * 
 */

public class LogcatHelper {

	private static LogcatHelper INSTANCE = null;
	//Log存储目录
	private static String PATH_LOGCAT;
	private LogDumper mLogDumper = null;
	private int mPId;
	private Context context;
	/**
	 * 
	 * 初始化目录
	 * 
	 * */
	public LogcatHelper init(String path) {
//		if (Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
//			PATH_LOGCAT = Environment.getExternalStorageDirectory()
//					.getAbsolutePath() + File.separator + "miniGPS";
//		} else {// 如果SD卡不存在，就保存到本应用的目录下
//			PATH_LOGCAT = context.getFilesDir().getAbsolutePath()
//					+ File.separator + "miniGPS";
//		}
//		File file = new File(PATH_LOGCAT);
//		if (!file.exists()) {
//			file.mkdirs();
//		}
		PATH_LOGCAT = path;
		return getInstance(context);
		
	
	}

	public static LogcatHelper getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new LogcatHelper(context);
		}
		return INSTANCE;
	}

	private LogcatHelper(Context context) {
		this.context = context;
		mPId = android.os.Process.myPid();
	}

	public void start() {
		if (mLogDumper == null)
			mLogDumper = new LogDumper(String.valueOf(mPId), PATH_LOGCAT);
		mLogDumper.start();
	}

//	public void stop() {
//		if (mLogDumper != null) {
//			mLogDumper.stopLogs();
//			mLogDumper = null;
//		}
//	}

	private class LogDumper extends Thread {

		private Process logcatProc;
		private BufferedReader mReader = null;
		private boolean mRunning = true;
		String cmds = null;
		private String mPID;
		private FileOutputStream out = null;

		public LogDumper(String pid, String dir) {
			mPID = pid;
			try {

				//TODO 待修改
				//java.io.FileNotFoundException: /storage/emulated/0/.blockadm/.log/2019-05-21.log (Permission denied)
				out = new FileOutputStream(FileUtils.getFileFromPath(dir + TimeUtils.getTime(
						TimeUtils.getCurrentTimeInLong(),
						TimeUtils.DATE_FORMAT_DATE)
						+ ".log"));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/**
			 * 
			 * 日志等级：*:v , *:d , *:w , *:e , *:f , *:s
			 * 
			 * 显示当前mPID程序的 E和W等级的日志.
			 * 
			 * */

			// cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
			// cmds = "logcat  | grep \"(" + mPID + ")\"";//打印所有日志信息
			 cmds = "logcat -s XMPP *:e *:w | grep \"(" + mPID + ")\"";//打印标签过滤信息
//			cmds = "logcat *:e *:i | grep \"(" + mPID + ")\"";

		}

//		public void stopLogs() {
//			mRunning = false;
//		}

		@Override
		public void run() {
			try {
				logcatProc = Runtime.getRuntime().exec(cmds);
				mReader = new BufferedReader(new InputStreamReader(
						logcatProc.getInputStream()), 1024);
				String line = null;
				while (mRunning && (line = mReader.readLine()) != null) {
					if (!mRunning) {
						break;
					}
					if (line.length() == 0) {
						continue;
					}
					if (out != null && line.contains(mPID)) {
						out.write((TimeUtils.getCurrentTimeInString() + "  "
								+ line + "\n").getBytes());
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (logcatProc != null) {
					logcatProc.destroy();
					logcatProc = null;
				}
				if (mReader != null) {
					try {
						mReader.close();
						mReader = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					out = null;
				}

			}

		}

	}
}
