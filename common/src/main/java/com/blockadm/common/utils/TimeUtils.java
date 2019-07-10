package com.blockadm.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

/**
 * TimeUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_FORMAT_MINIUTE = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat DATE_FORMAT_MONTH = new SimpleDateFormat(
			"yyyy-MM");
	public static final SimpleDateFormat DATE_FORMAT_ONLY_TIME = new SimpleDateFormat(
			"HH:mm");
	

	private TimeUtils() {
		throw new AssertionError();
	}

	/**
	 * long time to string
	 * 
	 * @param timeInMillis
	 * @param dateFormat
	 * @return
	 */
	public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(timeInMillis));
	}

	/**
	 * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static String getTime(long timeInMillis) {
		return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 获取时间，格式为 小时：分钟
	 * @return
     */
	public static String getNowTimeWithHm() {
		return getTime(getCurrentTimeInLong(), DATE_FORMAT_ONLY_TIME);
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}

	/**
	 * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString() {
		return getTime(getCurrentTimeInLong());
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
		return getTime(getCurrentTimeInLong(), dateFormat);
	}

	/****************************************
	 * String转日期 （yyyy-MM-dd HH:MM:ss）
	 * 
	 * @throws ParseException
	 * 
	 * @param：date：日期
	 * @return：String 格式化后日期
	 * @author：黄俊鑫
	 ******************************************/
	public static Calendar stringToCalendar(String sDate,
			SimpleDateFormat dateFormat) throws ParseException {

		Date date = dateFormat.parse(sDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;

	}

	/****************************************
	 * 日期转String （yyyy-MM-dd HH:MM:ss）
	 * 
	 * @param：date：日期
	 * @return：String 格式化后日期
	 * @author：黄俊鑫
	 ******************************************/
	public static String dateFormatToString(Date date,
			SimpleDateFormat dateFormat) {

		return dateFormat.format(date);
	}

	public static Date stringFormatToDate(String dateStr,
			SimpleDateFormat dateFormat) throws ParseException {

		return dateFormat.parse(dateStr);

	}

	public static long stringDateFromatToInLong(String dateStr,
			SimpleDateFormat dateFormat) throws ParseException {

		return dateFormat.parse(dateStr).getTime();

	}

	/**
	 * 月/日/小时/分 可能以0开头，如： 2018-05-06 03:06:59
	 * @param time
	 * @return
	 */
	public static int getIntegerTime(String time) {

		if (time.startsWith("0") && time.length() > 1) {
			return Integer.valueOf(time.substring(1));
		}else {
			return Integer.valueOf(time);
		}
	}

	/**
	 * 选择的时间与当前时间进行比较
	 * @param dateTimeStr 格式：2018-12-15
	 * @param minuteTimeStr 格式：03:56  秒数默认为 00
	 * @return
	 */
	public static boolean smallThenCurrentTime(String dateTimeStr,String minuteTimeStr) {
		Date date = new Date();
		int currentYear = TimeUtils.getYearFromDate(date);
		int currentMonth = TimeUtils.getMonthFromDate(date);
		int currentDay = TimeUtils.getDayFromDate(date);
		int currentHour = TimeUtils.getHourFromDate(date);
		int currentMinute = TimeUtils.getMinuteFromDate(date);

		int year = 0;
		int month = 0;
		int day = 0;
		int hour = 0;
		int minute = 0;
		if (dateTimeStr.contains("-")) {
			String[] split = dateTimeStr.split("-");
			if (split.length > 2) {
				year = getIntegerTime(split[0]);
				month = getIntegerTime(split[1]);
				day = getIntegerTime(split[2]);
			}
		}

		if (minuteTimeStr.contains(":")) {
			String[] split = minuteTimeStr.split(":");
			if (split.length > 1) {
				hour = getIntegerTime(split[0]);
				minute = getIntegerTime(split[1]);
			}
		}

		if (year > currentYear) { //年
			return false;
		}else {
			if (month > currentMonth) {//月
				return false;
			}else {
				if (day > currentDay) {//日
					return false;
				}else {
					if (hour > currentHour) {//小时
						return false;
					}else {
						if (minute > currentMinute) {//分
							return false;
						}
					}
				}
			}
		}

		return true;
	}


	/**
	 * 返回年份
	 * 
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYearFromDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 返回月份
	 * 
	 * @param date
	 *            日期
	 * @return 返回月份
	 */
	public static int getMonthFromDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 返回日份
	 * 
	 * @param date
	 *            日期
	 * @return 返回日份
	 */
	public static int getDayFromDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回星期
	 * 
	 * @param date
	 *            日期
	 * @return 返回星期
	 */
	public static int getWeekFromDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 返回星期 如：星期一
	 * @param date
	 * @return
	 */
	public static String getWeekString(Date date) {
		final Calendar c = Calendar.getInstance();

		String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if("1".equals(mWay)){
			mWay ="天";
		}else if("2".equals(mWay)){
			mWay ="一";
		}else if("3".equals(mWay)){
			mWay ="二";
		}else if("4".equals(mWay)){
			mWay ="三";
		}else if("5".equals(mWay)){
			mWay ="四";
		}else if("6".equals(mWay)){
			mWay ="五";
		}else if("7".equals(mWay)){
			mWay ="六";
		}

		return "星期"+mWay;

	}

	/**
	 * 返回小时
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHourFromDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前的小时
	 * @return
     */
	public static int getNowHourFromDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(c.getTime());
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回分钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinuteFromDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回秒钟
	 */
	public static int getSecondFromDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            日期
	 * @param month
	 *            天数
	 * @return 返回相加后的日期
	 */
	public static Date addMonth(Date date, int month) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		return c.getTime();

	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            日期
	 * @param day
	 *            天数
	 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillisFromDate(date) + ((long) day) * 24 * 3600
				* 1000);
		return c.getTime();
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 *            日期
	 * @param date1
	 *            日期
	 * @return 返回相减后的日期
	 */
	public static int subtractDateForDay(Date date, Date date1) {
		return (int) ((getMillisFromDate(date) - getMillisFromDate(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 日期相减获得秒钟
	 * 
	 * @param date
	 *            日期
	 * @param date1
	 *            日期
	 * @return 返回相减后的秒
	 */
	public static int subtractDateForSecond(Date date, Date date1) {
		return (int) ((getMillisFromDate(date) - getMillisFromDate(date1)) / 1000);
	}

	/**
	 * 返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillisFromDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            日期
	 * @param min
	 *            分钟
	 * @return 返回相加后的日期
	 */
	public static Date addMin(Date date, int min) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillisFromDate(date) + ((long) min) * 60 * 1000);
		return c.getTime();
	}

	/**
	 * 分转换为小时
	 * @param chargeSec 分
	 * @return 小时格式
	 */
	public static String changeMinuts2SpecFormat(String chargeSec) {

		String serviceTime = "0分";
		if(chargeSec!=null) {
			
			Double min = Double.valueOf(chargeSec);
			String format;
			Object[] array;
			Integer cvtDay = (int) (min / (60 * 24));
			Integer cvtHour = (int) (min / (60) - cvtDay * 24);
			Integer cvtMinute = (int) (min - cvtHour * 60 - cvtDay * 24 * 60);
			if (cvtDay > 0)
			{
				format = "%1$,d天%2$,d小时%3$,d分";
				array = new Object[]
						{ cvtDay, cvtHour, cvtMinute };
			} else if (cvtHour > 0)
			{
				format = "%1$,d小时%2$,d分";
				array = new Object[]
						{ cvtHour, cvtMinute };
			} else
			{
				format = "%1$,d分";
				array = new Object[]
						{ cvtMinute };
			}
			serviceTime = String.format(format, array);
		}
		return serviceTime;
	}

	/**
	 * 秒转换为小时
	 * @param second
	 * @return
	 */
	public static String changeSecond2SpaceFormat(String second){  

		String  serviceTime="0秒";  
		if(second!=null){  
			Double s=Double.valueOf(second);  
			String format;  
			Object[] array; 
			Integer day =(int) (s/(60*60*24));  
			Integer hours =(int) (s/(60*60)-day*24);  
			Integer minutes = (int) (s/60-hours*60-day*24*60);  
			Integer seconds = (int) (s-minutes*60-hours*60*60-day*24*60*60);  
			if(day>0){
				format="%1$,d天%2$,d时%3$,d分%4$,d秒";  
				array=new Object[]{day,hours,minutes,seconds};  
			}
			else if(hours>0){  
				format="%1$,d时%2$,d分%3$,d秒";  
				array=new Object[]{hours,minutes,seconds};  
			}else if(minutes>0){  
				format="%1$,d分%2$,d秒";  
				array=new Object[]{minutes,seconds};  
			}else{  
				format="%1$,d秒";  
				array=new Object[]{seconds};  
			}  
			serviceTime= String.format(format, array);  
		}  

		return serviceTime;  

	}


	/**
	 * 获得指定日期的后一天
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay){
		Calendar c = Calendar.getInstance();
		Date date=null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day=c.get(Calendar.DATE);
		c.set(Calendar.DATE,day+1);

		String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

}
