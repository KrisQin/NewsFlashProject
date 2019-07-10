package com.blockadm.common.utils;

import android.text.TextUtils;

public class ConvertStrUtil {

	// 转化字符串为十六进制编码 
	public static String toHexString(String s)
	{
		String str="";
		for (int i=0;i<s.length();i++)
		{
			int ch = (int)s.charAt(i);
			String frm = Integer.toHexString(ch);
			str = str + frm;
		}
		return str.length()>1?"0x"+str:"0x0"+str;
	}

	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
		_b0 = (byte)(_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
		byte ret = (byte)(_b0 ^ _b1);
		return ret;
	}

	/**
	 * 将指定字符串src，以每两个字符分割转换为16进制形式
	 * 如："2B44EFD9" –> byte[]{0x2B, 0×44, 0xEF, 0xD9}
	 * @param src String
	 * @return byte[]
	 */
	public static byte[] HexString2Bytes(String src){
		byte[] ret = new byte[src.length()/2];
		byte[] tmp = src.getBytes();
		for(int i=0; i< tmp.length/2; i++){
			ret[i] = uniteBytes(tmp[i*2], tmp[i*2+1]);
		}
		return ret;
	}

	/**
	 * 将指定字符串src，以两个字符分割转换为16进制形式
	 * 如："2B" –> {0x2B}
	 * @param src String
	 * @return byte
	 */
	public static Byte HexString2Byte(String src){
		if(TextUtils.isEmpty(src) || src.length() != 2){
			return null;
		}
		byte[] tmp = src.getBytes();
		return uniteBytes(tmp[0], tmp[1]);
	}

	/**
	 * 将指定byte数组以16进制的形式打印到控制台
	 * @param b byte[]
	 * @return String
	 */
	public static String Bytes2HexString(byte[] b) {
		String ret = "";
		if(null != b){
			for (int i = 0; i < b.length; i++) {
				String hex = Integer.toHexString(b[i] & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				ret += hex.toUpperCase();
			}
		}
		return ret;
	}
	/*
    * 将字符串编码成16进制数字,适用于所有字符（包括中文） 
    */
	public static String encode(String str)
	{
		//根据默认编码获取字节数组
		byte[] bytes=str.getBytes();
		StringBuilder sb=new StringBuilder(bytes.length*2);
		//将字节数组中每个字节拆解成2位16进制整数
		for(int i=0;i<bytes.length;i++)
		{
			sb.append(str.charAt((bytes[i]&0xf0)>>4));
			sb.append(str.charAt((bytes[i]&0x0f)>>0));
		}
		return sb.toString();
	}

	/**
	 * 逆序转换
	 * @param arrSrc
	 */
	public static void reverse(byte[] arrSrc){
		for(int i = 0,j=arrSrc.length - 1; i < arrSrc.length/2;i++,j--){
			swap(arrSrc,i,j);
		}
	}

	public static void swap(byte[] arr, int a, int b) {
		byte temp;
		temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}

	/**
	 * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用
	 * @param value
	 *            要转换的int值
	 * @return byte数组
	 */
	public static byte[] intToBytes( int value )
	{
		byte[] src = new byte[4];
		src[3] =  (byte) ((value>>24) & 0xFF);
		src[2] =  (byte) ((value>>16) & 0xFF);
		src[1] =  (byte) ((value>>8) & 0xFF);
		src[0] =  (byte) (value & 0xFF);
		return src;
	}
	/**
	 * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToInt2（）配套使用
	 */
	public static byte[] intToBytes2(int value)
	{
		byte[] src = new byte[4];
		src[0] = (byte) ((value>>24) & 0xFF);
		src[1] = (byte) ((value>>16)& 0xFF);
		src[2] = (byte) ((value>>8)&0xFF);
		src[3] = (byte) (value & 0xFF);
		return src;
	}
	/**
	 * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用
	 *
	 * @param src
	 *            byte数组
	 * @param offset
	 *            从数组的第offset位开始
	 * @return int数值
	 */
	public static int bytesToInt(byte[] src, int offset) {
		int value;
		value = (int) ((src[offset] & 0xFF)
				| ((src[offset+1] & 0xFF)<<8)
				| ((src[offset+2] & 0xFF)<<16)
				| ((src[offset+3] & 0xFF)<<24));
		return value;
	}

	/**
	 * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
	 */
	public static int bytesToInt2(byte[] src, int offset) {
		int value;
		value = (int) (((src[offset] & 0xFF)<<24)
				|((src[offset+1] & 0xFF)<<16)
				|((src[offset+2] & 0xFF)<<8)
				|(src[offset+3] & 0xFF));
		return value;
	}


	//数组扩展
	public static byte[] arrayExpand(byte[] arr,int expandLen,boolean isFront){
		if(null == arr || expandLen < 0)return null;
		int length = arr.length;
		byte[] result = new byte[length + expandLen];
		if(isFront){
			for(int i=0;i<length;i++){
				result[expandLen + i] = arr[i];
			}
		}else{
			for(int i=0;i<length;i++){
				result[i] = arr[i];
			}
		}
		return result;
	}

	//拼接数组
	public static byte[] spliceArray(byte[] arr1,byte[] arr2){
		if(null == arr1 || null == arr2){
			return null;
		}
		int length = arr1.length;
		int Len = length + arr2.length;
		byte[] result = new byte[Len];
		for(int i=0;i<Len;i++){
			if(i < length){
				result[i] = arr1[i];
			}else{
				result[i] = arr2[i - length];
			}
		}
		return result;
	}
	//拼接数组和rssi
	public static byte[] spliceArray(byte[] arr1,byte[] arr2,int rssi){
		if(null == arr1 || null == arr2){
			return null;
		}
		int length = arr1.length;
		int Len = length + arr2.length;
		byte[] result = new byte[Len+1];
		for(int i=0;i<Len;i++){
			if(i < length){
				result[i] = arr1[i];
			}else{
				result[i] = arr2[i - length];
			}
		}
		result[Len] = (byte)(Math.abs(rssi) & 0xFF);
		return result;
	}



	/**
	 * 16进制字符串转换为字符串
	 *
	 * @param s
	 * @return
	 */
	public static String hexStringToString(String s) {
		if (s == null || s.equals("")) {
			return null;
		}
		s = s.replace(" ", "");
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "gbk");
			new String();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}


}
