package com.blockadm.common.utils;

import java.text.ParseException;
import java.util.Date;


/**
 * Object Utils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-10-24
 */
public class ObjectUtils {

    /**
     * compare two object
     * 
     * @param actual
     * @param expected
     * @return <ul>
     *         <li>if both are null, return true</li>
     *         <li>return actual.{@link Object#equals(Object)}</li>
     *         </ul>
     */
    public static boolean isEquals(Object actual, Object expected) {
        return actual == expected || (actual == null ? expected == null : actual.equals(expected));
    }

    public static String empty2String(Object obj) {

        return ((obj == null || "null".equals(obj.toString()))? "" : obj.toString());

    }

    /**
     * convert long array to Long array
     * 
     * @param source
     * @return
     */
    public static Long[] transformLongArray(long[] source) {
        Long[] destin = new Long[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * convert Long array to long array
     * 
     * @param source
     * @return
     */
    public static long[] transformLongArray(Long[] source) {
        long[] destin = new long[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * convert int array to Integer array
     * 
     * @param source
     * @return
     */
    public static Integer[] transformIntArray(int[] source) {
        Integer[] destin = new Integer[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * convert Integer array to int array
     * 
     * @param source
     * @return
     */
    public static int[] transformIntArray(Integer[] source) {
        int[] destin = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * compare two object
     * <ul>
     * <strong>About result</strong>
     * <li>if v1 > v2, return 1</li>
     * <li>if v1 = v2, return 0</li>
     * <li>if v1 < v2, return -1</li>
     * </ul>
     * <ul>
     * <strong>About rule</strong>
     * <li>if v1 is null, v2 is null, then return 0</li>
     * <li>if v1 is null, v2 is not null, then return -1</li>
     * <li>if v1 is not null, v2 is null, then return 1</li>
     * <li>return v1.{@link Comparable#compareTo(Object)}</li>
     * </ul>
     * 
     * @param v1
     * @param v2
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <V> int compare(V v1, V v2) {
        return v1 == null ? (v2 == null ? 0 : -1) : (v2 == null ? 1 : ((Comparable)v1).compareTo(v2));
    }
    
    public static String nullStrToEmpty(Object obj) {
    	
		return (obj == null ? "" : obj.toString());

	}
    
    public static boolean nullBooleanToDefault(Object obj) {
    	
		return (obj == null ? false : Boolean.parseBoolean(obj.toString()));

	}

	public static double nullDoubleToDefault(Object obj) {
		
		try {
			return (obj == null ? 0 : Double.parseDouble(obj.toString()));
		}catch(NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	public static int nullIntegerToDefault(Object obj) {
		
		
		try {
			return (obj == null ? 0 : Integer.parseInt(obj.toString()));
		}catch(NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	public static Date nullDateToDefault(Object obj) {

		String dateStr = (obj == null) ? "1997-01-01 00:00:00" : obj.toString();

		try {
			return TimeUtils.stringFormatToDate(dateStr,
					TimeUtils.DEFAULT_DATE_FORMAT) == null ? TimeUtils
					.stringFormatToDate("1997-01-01 00:00:00",
							TimeUtils.DEFAULT_DATE_FORMAT) : TimeUtils
					.stringFormatToDate(dateStr, TimeUtils.DEFAULT_DATE_FORMAT);
		} catch (ParseException e) {
			
		
			e.printStackTrace();
		}
		return null;
	}
}
