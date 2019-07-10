package com.blockadm.common.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * ***********************************
 *
 * @ClassName: GeneralUtil
 * @Description: 常用工具类
 * @author 唐宏宇
 * @date 2015年8月13日 下午4:00:10
 *
 *       ***********************************
 */
public class GeneralUtil {
	/** 金额为分的格式 */
	public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

	/**
	 * 解决大部分的中文乱码 但是还有部分的乱码无法解决 .
	 * 
	 * @param：str：源字符串
	 * @return：String 目标字符串
	 * @author：黄俊鑫
	 */
	// public static String recode(String str) {
	// String formart = "";
	//
	// try {
	// boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
	// .canEncode(str);
	// if (ISO) {
	// formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
	// Log.i("1234      ISO8859-1", formart);
	// } else {
	// formart = str;
	// Log.i("1234      stringExtra", str);
	// }
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return formart;
	// }

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	/**
	 * 将分为单位的转换为元 （除100）
	 * 
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String changeF2Y(String amount) throws Exception {
		if (!amount.matches(CURRENCY_FEN_REGEX)) {
			throw new Exception("金额格式有误");
		}
		return BigDecimal.valueOf(Long.valueOf(amount))
				.divide(new BigDecimal(100)).toString();
	}

	/**
	 * 将分为单位的转换为xx时xx分 （除60）
	 * 
	 * @param sec
	 * @return
	 * @throws Exception
	 */
	public static String changeM2H(String sec) throws Exception {
		if (!sec.matches(CURRENCY_FEN_REGEX)) {
			throw new Exception("时间有误");
		}
		int totlaSecond = Integer.valueOf(sec);
		int totalMin = totlaSecond / 60;

		int hour = totalMin / 60;
		int minute = totalMin - hour * 60;
		int second = totlaSecond - totalMin * 60;

		int day = hour / 24;
		hour = hour - day * 24;

		return day + "天" + hour + "小时" + minute + "分" + second + "秒";
	}

	/****************************************
	 * 不足位数左补0
	 * 
	 * @param：date：日期
	 * @return：String 格式化后日期
	 * @author：黄俊鑫
	 ******************************************/
	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}



	/**
	 * 
	 * @autor 唐宏宇 TODO 生成签名
	 */
	public static String getSign(String data) {
		String signKey = "i9ouj89*H(U(y_U";
		return MD5Utils.SecurityByMD5(AsciiSortUtil
				.queryStringSort((data + signKey).replace(",", "&")));
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	private static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	private static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	private static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The mContext.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	private static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 * 
	 * @param context
	 *            The mContext.
	 * @param uri
	 *            The Uri to query.
	 * @author paulburke
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	@SuppressLint("NewApi")
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * //TODO: TAOTAO 将bitmap由RGB转换为YUV
	 * 
	 * @param bitmap
	 *            转换的图形
	 * @return YUV数据
	 */
	public static byte[] rgb2YUV(Bitmap bitmap) {
		// 该方法来自QQ空间
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

		int len = width * height;
		byte[] yuv = new byte[len * 3 / 2];
		int y, u, v;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int rgb = pixels[i * width + j] & 0x00FFFFFF;

				int r = rgb & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb >> 16) & 0xFF;

				y = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
				u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
				v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;

				y = y < 16 ? 16 : (y > 255 ? 255 : y);
				u = u < 0 ? 0 : (u > 255 ? 255 : u);
				v = v < 0 ? 0 : (v > 255 ? 255 : v);

				yuv[i * width + j] = (byte) y;
				// yuv[len + (i >> 1) * width + (j & ~1) + 0] = (byte) u;
				// yuv[len + (i >> 1) * width + (j & ~1) + 1] = (byte) v;
			}
		}
		return yuv;
	}

	public static Bitmap createImage(String text, int QR_WIDTH, int QR_HEIGHT) {

		Bitmap bitmap = null;
		try {
			// 需要引入core包
			QRCodeWriter writer = new QRCodeWriter();

			if (text == null || "".equals(text) || text.length() < 1) {
				return null;
			}

			// 把输入的文本转为二维码
			BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
					QR_WIDTH, QR_HEIGHT);

			System.out.println("w:" + martix.getWidth() + "h:"
					+ martix.getHeight());

			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new QRCodeWriter().encode(text,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}

				}
			}

			bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);

			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

		} catch (WriterException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * 生成条形码的Bitmap
	 * 
	 * @param contents
	 *            需要生成的内容
	 * @param desiredWidth
	 * @param desiredHeight
	 * @return
	 * @throws WriterException
	 */
	public static Bitmap encodeAsBitmap(String contents, int desiredWidth,
			int desiredHeight) {
		final int WHITE = 0xFFFFFFFF;
		final int BLACK = 0xFF000000;
		BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix result = null;
		try {
			result = writer.encode(contents, barcodeFormat, desiredWidth,
					desiredHeight, null);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int width = result.getWidth();
		int height = result.getHeight();
		int[] pixels = new int[width * height];
		// All are 0, or black, by default
		for (int y = 0; y < height; y++) {
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	public static String getFileNameFromUrl(String url)
			throws IndexOutOfBoundsException {
		return url.substring(url.lastIndexOf("/") + 1, url.length());

	}

	public static boolean isHttpUrl(String url) {

		return !StringUtils.isEmpty(url) && (url.startsWith("http://") || url.startsWith("https://")|| url.startsWith("www."));
	}

	// Handler类应该应该为static类型，否则有可能造成泄露。在程序消息队列中排队的消息保持了对目标Handler类的应用。
	// 如果Handler是个内部类，那么它也会保持它所在的外部类的引用。
	// 为了避免泄露这个外部类，应该将Handler声明为static嵌套类，并且使用对外部类的弱应用
//	public static class MainHandler1 extends Handler {
//
//		WeakReference<ResponseMessageInterface> mActivity;
//
//		public MainHandler1(ResponseMessageInterface activity) {
//			mActivity = new WeakReference<ResponseMessageInterface>(activity);
//		}
//
//		@Override
//		public void handleMessage(Message msg) {
//			if (msg.obj != null && mActivity.get() != null) {
//				if (msg.what == Constants.OK) {
//
//					mActivity.get().handleMsg((ServiceResponseData) msg.obj);
//
//				} else if (msg.what == Constants.ERR) {
//					mActivity.get().handleErrorMsg(
//							(ServiceResponseData) msg.obj);
//				}
//				mActivity.get().finallyDo((ServiceResponseData) msg.obj);
//
//			}
//
//		}
//
//
//	}
	
//	public static class InnerHandler extends Handler {
//		WeakReference<InnerHandlerInterface> mActivity;
//
//		public InnerHandler(InnerHandlerInterface activity) {
//			mActivity = new WeakReference<InnerHandlerInterface>(activity);
//		}
//
//		@Override
//		public void handleMessage(Message msg) {
//
//			if(mActivity.get() != null) {
//				mActivity.get().innerHandlerDo(msg);
//			}
//		}
//	}
	public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();

    }
}
