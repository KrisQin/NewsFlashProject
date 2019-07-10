package com.blockadm.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.StatFs;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*ALPHA_8 代表8位Alpha位图
 ARGB_4444 代表16位ARGB位图  在API level 13 已经废弃，被ARGB_8888代替 内存占用减少一半，但是每个值图片失真度很严重，这个参数本身已经不推荐使用了
 ARGB_8888 代表32位ARGB位图  图像质量最高，也最占内存
 RGB_565 代表16位RGB位图，内存占用减少一半，舍弃了透明度，同时三色值也有部分损失，但是图片失真度很小

 位图位数越高代表其可以存储的颜色信息越多，当然图像也就越逼真。*/

public class BitmapUtil {

	public static final int HIGH_QUALITY = 0;
	public static final int MIDDLE_QUALITY = 1;
	public static final int LOW_QUALITY = 2;
	public static final String IMG_FORMAT_JPG = ".jpg";
	public static final String IMG_FORMAT_PNG = ".png";



	/***
	 * 根据资源文件获取Bitmap
	 * 
	 * @param context
	 * @param drawableId
	 * @return
	 */
	public static Bitmap readBitmapById(Context context, int drawableId,
			int width, int height) throws OutOfMemoryError {

		return readBitmapById(context, drawableId, width, height,
				MIDDLE_QUALITY);
	}

	public static Bitmap readBitmapById(Context context, int drawableId,
			int width, int height, int quality) throws OutOfMemoryError {
		BitmapFactory.Options options = new BitmapFactory.Options();
		int maxSize;
		CompressFormat format = CompressFormat.PNG;
		switch (quality) {
		case HIGH_QUALITY:
			options.inPreferredConfig = Config.ARGB_8888;
			maxSize = 600;
			format = CompressFormat.PNG;
			break;
		case MIDDLE_QUALITY:
			maxSize = 200;
			options.inPreferredConfig = Config.RGB_565;
			format = CompressFormat.JPEG;
			break;
		case LOW_QUALITY:
			maxSize = 50;
			options.inPreferredConfig = Config.ALPHA_8;
			format = CompressFormat.JPEG;
			break;
		default:
			maxSize = 50;
			break;
		}
		options.inJustDecodeBounds = true;
		options.inInputShareable = true;
		options.inPurgeable = true;
		InputStream stream = context.getResources().openRawResource(drawableId);
		Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);

		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		options.inSampleSize = caculateInSampleSize(options, width, height);
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeStream(stream, null, options);
		try {
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return compressBitmapByMatrix(bitmap, maxSize, format);// 压缩好比例大小后再进行质量压缩

	}

	/**
	 * 
	 * ***********************************
	 *
	 * @Description: 读取本地资源的图片,默认压缩率480*800
	 * @param @param context
	 * @param @param resId
	 * @param @return
	 * @return Bitmap
	 * @throws ***********************************
	 */
	public static Bitmap readBitmapById(Context context, int resId)
			throws OutOfMemoryError {
		return readBitmapById(context, resId, 480, 800);
	}

	public static Bitmap readBitmapById(Context context, int resId, int quality)
			throws OutOfMemoryError {
		return readBitmapById(context, resId, 480, 800, quality);
	}

	// /***
	// * 等比例压缩图片
	// *
	// * @param bitmap
	// * @param screenWidth
	// * @param screenHight
	// * @return
	// */
	// private static Bitmap getBitmap(Bitmap bitmap, int width, int height) {
	// int w = bitmap.getWidth();
	// int h = bitmap.getHeight();
	// Matrix matrix = new Matrix();
	// float scale = (float) width / w;
	// float scale2 = (float) height / h;
	//
	// scale = scale < scale2 ? scale : scale2;
	//
	// // 保证图片不变形.
	// matrix.postScale(scale, scale);
	// // w,h是原图的属性.
	// return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	// }

	/***
	 * 保存图片至SD卡
	 * 
	 * @param bm
	 * @param mUrl
	 * @param quantity
	 */
	private static int FREE_SD_SPACE_NEEDED_TO_CACHE = 1;
	private static int MB = 1024 * 1024;

	public static void saveBmpToSd(Bitmap bm, String fileName, int quantity,
			String savePath) {
		// 判断sdcard上的空间
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
			return;
		}
		if (!Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()))
			return;
		// 目录不存在就创建
		File dirPath = new File(savePath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		File file = new File(savePath + File.separator + fileName);
		try {
			file.createNewFile();
			OutputStream outStream = new FileOutputStream(file);
			bm.compress(CompressFormat.JPEG, quantity, outStream);
			outStream.flush();
			outStream.close();

		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** * 计算sdcard上的剩余空间 * @return */
	private static int freeSpaceOnSd() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
				.getBlockSize()) / MB;

		return (int) sdFreeMB;
	}

	/**
	 * 
	 * ***********************************
	 *
	 * @Description: 获取最优最小占内存的图片
	 * @param @param image
	 * @param @return
	 * @return Bitmap
	 * @throws ***********************************
	 */
	public static Bitmap getBitmap(Bitmap image, int width, int height,
			int quality) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int maxSize;
		image.compress(CompressFormat.PNG, 100, baos);
		// if (baos.toByteArray().length / 1024 > 1024) {//
		// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
		// baos.reset();// 重置baos即清空baos
		// image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//
		// 这里压缩50%，把压缩后的数据存放到baos中
		// }
		ByteArrayInputStream inStream = new ByteArrayInputStream(
				baos.toByteArray());
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		options.inJustDecodeBounds = true;
		CompressFormat format = CompressFormat.PNG;
		switch (quality) {
		case HIGH_QUALITY:
			options.inPreferredConfig = Config.ARGB_8888;
			format = CompressFormat.PNG;
			maxSize = 600;
			break;
		case MIDDLE_QUALITY:
			maxSize = 200;
			options.inPreferredConfig = Config.RGB_565;
			format = CompressFormat.JPEG;
			break;
		case LOW_QUALITY:
			maxSize = 50;
			options.inPreferredConfig = Config.ALPHA_8;
			format = CompressFormat.JPEG;
			break;
		default:
			maxSize = 100;
			break;
		}
		Bitmap bitmap = BitmapFactory.decodeStream(inStream, null, options);

		// int w = newOpts.outWidth;
		// int h = newOpts.outHeight;
		// // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		// float hh = 800f;// 这里设置高度为800f
		// float ww = 480f;// 这里设置宽度为480f
		// // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		// int be = 1;// be=1表示不缩放
		// if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
		// be = (int) (newOpts.outWidth / ww);
		// } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
		// be = (int) (newOpts.outHeight / hh);
		// }
		// if (be <= 0)
		// be = 1;
		// newOpts.inSampleSize = be;// 设置缩放比例
		options.inSampleSize = caculateInSampleSize(options, width, height);
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		options.inJustDecodeBounds = false;
		inStream = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(inStream, null, options);
		return compressBitmapByMatrix(bitmap, maxSize, format);// 压缩好比例大小后再进行质量压缩
	}

	public static Bitmap getBitmap(Bitmap image) {
		return getBitmap(image, 480, 800, MIDDLE_QUALITY);
	}

	public static Bitmap getOptimalBitmapFromLocal(String srcPath, int quality)
			throws OutOfMemoryError, NullPointerException {
		return getOptimalBitmapFromLocal(srcPath, 480, 800, quality);
	}

	public static Bitmap getOptimalBitmapFromLocal(String srcPath,
			int viewWidth, int viewHeight) throws OutOfMemoryError {
		return getOptimalBitmapFromLocal(srcPath, viewWidth, viewWidth,
				MIDDLE_QUALITY);
	}

	/**
	 * 
	 * ***********************************
	 *
	 * @Description: 获取针对imageview大小质量最优的图片，先进行图片大小缩放，再进行质量压缩
	 * @param @param srcPath
	 * @param @param viewHeight
	 * @param @param viewWidth
	 * @param @return
	 * @return Bitmap
	 * @throws ***********************************
	 */
	public static Bitmap getOptimalBitmapFromLocal(String srcPath,
			int viewWidth, int viewHeight, int quality) throws OutOfMemoryError {
		// BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		// newOpts.inJustDecodeBounds = true;
		// Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//
		// 此时返回bm为空
		//
		// newOpts.inJustDecodeBounds = false;
		// int w = newOpts.outWidth;
		// int h = newOpts.outHeight;
		// // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		// int be = 1;// be=1表示不缩放
		// if (w > h && w > viewWidth) {// 如果宽度大的话根据宽度固定大小缩放
		// be = (int) (newOpts.outWidth / viewWidth);
		// } else if (w < h && h > viewHeight) {// 如果高度高的话根据宽度固定大小缩放
		// be = (int) (newOpts.outHeight / viewHeight);
		// }
		// if (be <= 0)
		// be = 1;
		// newOpts.inSampleSize = be;// 设置缩放比例
		// // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		// bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		BitmapFactory.Options options = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设为true了，在不占用内存情况下，计算图片大小
		options.inJustDecodeBounds = true;
		CompressFormat format = CompressFormat.PNG;
		int maxSize;
		switch (quality) {
		case HIGH_QUALITY:
			options.inPreferredConfig = Config.ARGB_8888;
			format = CompressFormat.PNG;
			maxSize = 600;
			break;
		case MIDDLE_QUALITY:
			maxSize = 200;
			options.inPreferredConfig = Config.RGB_565;
			format = CompressFormat.JPEG;
			break;
		case LOW_QUALITY:
			maxSize = 50;
			options.inPreferredConfig = Config.ALPHA_8;
			format = CompressFormat.JPEG;
			break;
		default:
			maxSize = 100;
			break;
		}
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, options);
		options.inSampleSize = caculateInSampleSize(options, viewWidth,
				viewHeight);

		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(srcPath, options);
		if (bitmap==null){
			return null;
		}

		return compressBitmapByMatrix(bitmap, maxSize, format);// 压缩好比例大小后再进行质量压缩
	}

	public static Bitmap getBitmapFromLocal(String srcPath,
												   int viewWidth, int viewHeight, int quality) throws OutOfMemoryError {

		BitmapFactory.Options options = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设为true了，在不占用内存情况下，计算图片大小
		options.inJustDecodeBounds = true;
		CompressFormat format = CompressFormat.PNG;
		int maxSize;
		switch (quality) {
			case HIGH_QUALITY:
				options.inPreferredConfig = Config.ARGB_8888;
				format = CompressFormat.PNG;
				maxSize = 600;
				break;
			case MIDDLE_QUALITY:
				maxSize = 200;
				options.inPreferredConfig = Config.RGB_565;
				format = CompressFormat.JPEG;
				break;
			case LOW_QUALITY:
				maxSize = 50;
				options.inPreferredConfig = Config.ALPHA_8;
				format = CompressFormat.JPEG;
				break;
			default:
				maxSize = 100;
				break;
		}
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, options);
		options.inSampleSize = caculateInSampleSize(options, viewWidth,
				viewHeight);

		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(srcPath, options);

		return bitmap;// 压缩好比例大小后再进行质量压缩
	}
	

	/**
	 * 
	 * ***********************************
	 *
	 * @Description: 比例大小压缩方法 默认图片大小取图片
	 * @param @param srcPath
	 * @param @return
	 * @return Bitmap
	 * @throws ***********************************
	 */
	public static Bitmap getOptimalBitmapFromLocal(String srcPath)
			throws OutOfMemoryError {

		return getOptimalBitmapFromLocal(srcPath, 480, 800, MIDDLE_QUALITY);
	}

	public static Bitmap compressBitmapByMatrix(Bitmap bitmap, int size,
			CompressFormat format) {
		// // 图片压缩到100K
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到out中
		bitmap.compress(format, 100, out);

		// 计算缩放比例
		float zoom = (float) Math.sqrt(size * 1024
				/ (float) out.toByteArray().length);

		Matrix matrix = new Matrix();
		matrix.setScale(zoom, zoom);

		Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);

		// out.reset();
		// result.compress(Bitmap.CompressFormat.PNG, 85, out);
		while (out.toByteArray().length > size * 1024) {
			// System.out.println(out.toByteArray().length);
			matrix.setScale(0.9f, 0.9f);
			result = Bitmap.createBitmap(result, 0, 0, result.getWidth(),
					result.getHeight(), matrix, true);
			out.reset();
			result.compress(format, 85, out);
		}

		ByteArrayInputStream isBm = new ByteArrayInputStream(out.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap image = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片

		return image;

	}

	/**
	 * 
	 * ***********************************
	 *
	 * @Description: 质量压缩法,压缩图片，可能图片变小但加载到内存中bitmap没有变小
	 * @param @param image
	 * @param @return
	 * @return Bitmap 这种压缩方法之所以称之为质量压缩，是因为它不会减少图片的像素。它是在保持像素的前提下改变图片的位深及透明度等，
	 *         来达到压缩图片的目的
	 *         。进过它压缩的图片文件大小会有改变，但是导入成bitmap后占得内存是不变的。因为要保持像素不变，所以它就无法无限压缩，
	 *         到达一个值之后就不会继续变小了。显然这个方法并不适用与缩略图，其实也不适用于想通过压缩图片减少内存的适用，
	 *         仅仅适用于想在保证图片质量的同时减少文件大小的情况而已
	 * 
	 * @throws ***********************************
	 */
	public static void compressBitmapAndSave(String fromPath,
			String fileSavePath, int quality) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int maxSize;
		CompressFormat format = CompressFormat.PNG;
		switch (quality) {
		case HIGH_QUALITY:
			maxSize = 800;
			format = CompressFormat.PNG;
			break;
		case MIDDLE_QUALITY:
			maxSize = 150;
			format = CompressFormat.JPEG;
			break;
		case LOW_QUALITY:
			maxSize = 50;
			format = CompressFormat.JPEG;
			break;
		default:
			maxSize = 100;
			break;
		}
		Bitmap image = BitmapFactory.decodeFile(fromPath);
		image.compress(format, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > maxSize && options > 0) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 20;// 每次都减少10
			image.compress(format, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

		}
		File dirFile = new File(fileSavePath);
		// 检测图片是否存在
		if (dirFile.exists()) {
			dirFile.delete(); // 删除原图片
		}
		try {
			baos.writeTo(new FileOutputStream(new File(fileSavePath)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		File myCaptureFile = new File(fileSavePath);
		// BufferedOutputStream bos = null;
		// bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		// 100表示不进行压缩，70表示压缩率为30%
//		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

		try {
			baos.flush();
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void compressBitmapAndSave(Bitmap bitmap,
			String fileSavePath) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	

		bitmap.compress(CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
	
		File dirFile = new File(fileSavePath);
		// 检测图片是否存在
		if (dirFile.exists()) {
			dirFile.delete(); // 删除原图片
		}
		try {
			baos.writeTo(new FileOutputStream(new File(fileSavePath)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		File myCaptureFile = new File(fileSavePath);
		// BufferedOutputStream bos = null;
		// bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		// 100表示不进行压缩，70表示压缩率为30%
//		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

		try {
			baos.flush();
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static final Bitmap createRGBImage(Bitmap bitmap, int color) {
		int bitmap_w = bitmap.getWidth();
		int bitmap_h = bitmap.getHeight();
		int[] arrayColor = new int[bitmap_w * bitmap_h];
		int count = 0;
		for (int i = 0; i < bitmap_h; i++) {
			for (int j = 0; j < bitmap_w; j++) {
				int color1 = bitmap.getPixel(j, i);
				// 这里也可以取出 R G B 可以扩展一下 做更多的处理，
				// 暂时我只是要处理除了透明的颜色，改变其他的颜色
				// if(color1!=0){
				// }else{
				// color1=color;
				// }
				if (color1 != Color.TRANSPARENT) {
					arrayColor[count] = color;
				} else {
					arrayColor[count] = Color.TRANSPARENT;
				}
				count++;
			}
		}
		bitmap = Bitmap.createBitmap(arrayColor, bitmap_w, bitmap_h,
		// Config.ARGB_4444 );
				Config.RGB_565);
		return bitmap;
	}

	/**
	 * 保存bitmap文件
	 * 
	 * @param file
	 *            ，文件。bitmap，图片
	 * @return
	 */
	public static void saveBitmap(String dirPath, String filePath, Bitmap bitmap) {
		FileOutputStream out;
		try {
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File f = new File(filePath);
			f.createNewFile();
			out = new FileOutputStream(f);
			if (bitmap.compress(CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * ***********************************
	 *
	 * @Description: 计算压缩率
	 * @param @param options
	 * @param @param rqsW
	 * @param @param rqsH
	 * @param @return
	 * @return int
	 * @throws ***********************************
	 */
	public final static int caculateInSampleSize(BitmapFactory.Options options,
			int rqsW, int rqsH) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (rqsW == 0 || rqsH == 0)
			return 1;
		if (height > rqsH || width > rqsW) {
			final int heightRatio = Math.round((float) height / (float) rqsH);
			final int widthRatio = Math.round((float) width / (float) rqsW);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	public static Bitmap saveAvatarBitmap(String path, String saveFolderPath, String saveFilePath) {

		Bitmap bitmap = BitmapUtil
				.getOptimalBitmapFromLocal(
						path);
		return  saveAvatarBitmap(bitmap, saveFolderPath, saveFilePath);
	}

	public static Bitmap saveAvatarBitmap(Bitmap bitmap, String saveFolderPath, String saveFilePath) {



		// 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片
		int bmpWidth = bitmap.getWidth();
		int bmpHeight = bitmap.getHeight();
		int squareWidth = 0, squareHeight = 0;
		int x = 0, y = 0;
		Bitmap squareBitmap;
		if (bmpHeight > bmpWidth) {// 高大于宽
			squareWidth = squareHeight = bmpWidth;
			x = 0;
			y = (bmpHeight - bmpWidth) / 2;
			// 截取正方形图片
			squareBitmap = Bitmap.createBitmap(bitmap, x, y, squareWidth,
					squareHeight);
		} else if (bmpHeight < bmpWidth) {// 宽大于高
			squareWidth = squareHeight = bmpHeight;
			x = (bmpWidth - bmpHeight) / 2;
			y = 0;
			squareBitmap = Bitmap.createBitmap(bitmap, x, y, squareWidth,
					squareHeight);
		} else {
			squareBitmap = bitmap;
		}
		BitmapUtil.saveBitmap(saveFolderPath, saveFilePath, squareBitmap);
		return squareBitmap;
	}

	public static boolean isSupportedImage(String filePath) {
		if (filePath.endsWith(IMG_FORMAT_JPG)
				|| filePath.endsWith(IMG_FORMAT_PNG)) {
			return true;
		}
		return false;
	}

	public static void recycleBackgroundBitMap(ImageView view) {
		if (view != null) {
			BitmapDrawable bd = (BitmapDrawable) view.getBackground();
			recycleBitmapDrawable(bd);
		}
	}

	public static void recycleImageViewBitMap(ImageView imageView) {
		if (imageView != null) {
			BitmapDrawable bd = (BitmapDrawable) imageView.getDrawable();
			recycleBitmapDrawable(bd);
		}
	}

	public static void recycleBitmapDrawable(BitmapDrawable bd) {
		if (bd != null) {
			Bitmap bitmap = bd.getBitmap();
			recycleBitmap(bitmap);
		}
		bd = null;
	}

	public static void recycleBitmap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
	}


}
