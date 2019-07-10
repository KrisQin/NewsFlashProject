/*
 * 文件名：MD5Utils.java
 * 创建日期：2014-06-04
 * 产品名称：捷顺捷生活APP软件
 * 版权所有：深圳市捷顺科技股份有限责任公司
 * 该文件隶属于公司该产品下的内部有价值的私密文件，仅供内部研发使用，不得私自拷贝、抄录
 * 
 */
package com.blockadm.common.utils;

import java.security.MessageDigest;

public class MD5Utils {
	private MD5Utils() {
			
		}

//	public static void main(String[] args) {
//
//		String s = new String("1000_100,AB23DC12");
//		System.out.println("原始：" + s);
//		System.out.println("MD5后：" + SecurityByMD5(s));
//
//	}
	
	
	
	public static String SecurityByMD5(String inStr){
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		} catch(Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		
		for (int i = 0; i < charArray.length;i++)
			byteArray[i] = (byte)charArray[i];
		
		byte[] md5Bytes = md5.digest(byteArray);


		StringBuffer hexValue = new StringBuffer();
		
		for (int i = 0;i < md5Bytes.length; i++){
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
			
		}
		return hexValue.toString();
	
		
	}

	/**
	 * 获取经过加密后的蓝牙协议数据
	 * @param inStr
	 * @return
	 */
	public static byte[] getBlueToothMD5Byte(String inStr) {
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		} catch(Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return null;
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length;i++)
			byteArray[i] = (byte)charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);


		StringBuffer hexValue = new StringBuffer();

		for (int i = 0;i < md5Bytes.length; i++){
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));

		}
		String value = hexValue.toString();
		// 得到加密后的16位字节数

		L.d("XMPP"," 得到加密后的16位字节数 value = "+value);

		byte[] bytes = new byte[4];

		bytes[0] = ConvertStrUtil.HexString2Byte(value.substring(2,4));
		bytes[1] = ConvertStrUtil.HexString2Byte(value.substring(4,6));
		bytes[2] = ConvertStrUtil.HexString2Byte(value.substring(20,22));
		bytes[3] = ConvertStrUtil.HexString2Byte(value.substring(22,24));


		return bytes;
	}


	//	/**
//	 * 获取经过加密后的蓝牙协议数据
//	 * @param inStr
//	 * @return
//     */
//	public static byte[] getBlueToothMD5Byte(String inStr) {
//		MessageDigest md5 = null;
//		byte[] bytes2 = new byte[4];
//		try{
//			md5 = MessageDigest.getInstance("MD5");
//		} catch(Exception e) {
//			System.out.println(e.toString());
//			e.printStackTrace();
//			return bytes2;
//		}
//		char[] charArray = inStr.toCharArray();
//		byte[] byteArray = new byte[charArray.length];
//
//		for (int i = 0; i < charArray.length;i++)
//			byteArray[i] = (byte)charArray[i];
//
//		// 得到加密后的16位字节数
//		byte[] md5Bytes = md5.digest(byteArray);
//
//		if (md5Bytes.length > 10) {
//			// 在16位加密的字节数中,取第1,2,9,10位组成4字节数作为蓝牙的签名值
//			bytes2[0] = md5Bytes[1];
//			bytes2[1] = md5Bytes[2];
//			bytes2[2] = md5Bytes[10];
//			bytes2[3] = md5Bytes[11];
//		}
//
//		return bytes2;
//	}


}
