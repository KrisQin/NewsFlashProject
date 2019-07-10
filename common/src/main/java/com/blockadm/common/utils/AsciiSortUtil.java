/**
 * 
 */
package com.blockadm.common.utils;

import android.util.Log;

/**
 * 
 * ***********************************
 *
 * @ClassName: AsciiSortUtil
 * @Description: 字符串根据Ascii码排序签名会用到
 * @author 唐宏宇
 * @date 2015年7月9日 上午10:27:13
 *
 * ***********************************
 */
public class AsciiSortUtil {

	private AsciiSortUtil(){};
	
	/**
	 * 对参数字段排序（ascill从小到大）
	 * @param qs
	 * @return
	 */
	public static String queryStringSort(String qs){
		
		if(qs == null || qs.equals("")){
			return qs;
		}
		StringBuffer sb = new StringBuffer();
		String[] queryString = qs.split("&");
		if(null != queryString && queryString.length>0){
			for(int i=queryString.length-1;i>=0;i--)
	        {
	            for(int j=0;j<i;j++)
	            {
	            	if(compare( queryString[j], queryString[j+1])> 0){
						String temp = queryString[j];
						queryString[j] = queryString[j+1];
						queryString[j+1] = temp;
					}
	            }
	        }

			for (int i = 0; i < queryString.length; i++) {
				sb.append(queryString[i]);
				if(i != queryString.length-1){
					sb.append("&");
				}
			}
		}
		Log.i("info", "login str="+sb.toString());
		return sb.toString();
	}
	
	
	
	/**
	 * 字符串的ascill码
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unused")
	private static int getAsciiCode(String str){
		int result = 0;
		if(str!=null&&!"".equals(str)){//str != null || !"".equals(str)
			char[] a = str.toCharArray();
			for (int i = 0; i < a.length; i++) {
				result += (int)a[i];
			}
		}
		return result;
	}
	
	/**
	 * 字符串首字母的ascill码
	 * @param str
	 * @return
	 */
	private static int getFirstCharAsciiCode(String str){
		int result = 0;
		if(str!=null&&!"".equals(str)){//str != null || !"".equals(str)
			char s= str.charAt(0);
			result = (int)s;
		}
		return result;
	}
	
	
	/**
	 * 通过首字母比较字符串
	 * @param strA
	 * @param strB
	 * @return
	 */
	public static int compare(String strA, String strB) {
	    char[] arrA = strA.toCharArray(), arrB = strB.toCharArray();
	    int char_index = 0, lenA = arrA.length, lenB = arrB.length;
	    int len = lenA < lenB ? lenA : lenB;
	    while (char_index < len) {
	        char cA = arrA[char_index], cB = arrB[char_index];
	        char cA_ = (char) (cA >= 'a' ? cA - ('a' - 'A') : cA);
	        char cB_ = (char) (cB >= 'a' ? cB - ('a' - 'A') : cB);
	        if (cA_ == cB_) {
	            if (cA != cB)
	                return cA - cB;
	        } else
	            return cA_ - cB_;
	        char_index++;
	    }
	    if (lenA == lenB)
	        return 0;
	    else if (lenA > lenB)
	        return arrA[len];
	    else
	        return -arrB[len];
	}
	 

}
