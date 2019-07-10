package com.blockadm.common.utils;

import android.util.Log;

/**
 * 
* @ClassName: L 
* @Description:
*
 */
public class L {
	public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
	private static final String TAG = "respInterceptor";

	// 下面四个是默认tag的函数
	public static void i(String msg) {
		if (isDebug)
			Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (isDebug)
			Log.d(TAG, msg);
	}
	
	public static void w(String msg) {
		if (isDebug)
			Log.w(TAG, msg);
	}

	public static void e(String msg) {
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (isDebug)
			Log.v(TAG, msg);
	}
	//下面是传入类名打印log
	public static void i(Class<?> _class,String msg){
		if (isDebug)
			Log.i(_class.getName(), msg);
	}
	public static void d(Class<?> _class,String msg){
		if (isDebug)
			Log.d(_class.getName(), msg);
	}
	public static void e(Class<?> _class,String msg){
		if (isDebug)
			Log.e(_class.getName(), msg);
	}
	public static void v(Class<?> _class,String msg){
		if (isDebug)
			Log.v(_class.getName(), msg);
	}
	
	public static void w(Class<?> _class,String msg){
		if (isDebug)
			Log.w(_class.getName(), msg);
	}
	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			Log.d(tag, msg);
	}
	
	public static void w(String tag, String msg) {
		if (isDebug)
			Log.w(tag, msg);
	}


	public static void e(String tag, String msg) {
		if (isDebug)
			Log.e(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (isDebug)
			Log.v(tag, msg);
	}

	public static void all(String tag, String msg) {  //信息太长,分段打印

		if (!isDebug) {
			return;
		}

		int count = 1;

//		//因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
//		//  把4*1024的MAX字节打印长度改为2001字符数
//		int max_str_length = 2001 - tag.length();
//
//		//大于4000时
//		while (msg.length() > max_str_length) {
//			Log.i(tag, "\n第 "+count++ + " 部分    " + msg.substring(0, max_str_length));
//			msg = msg.substring(max_str_length);
//		}

		//剩余部分
		if (count > 1) {
			Log.i(tag, "\n第 "+count + " 部分    " + msg);
		}else {
			Log.i(tag, msg);
		}


	}
}
