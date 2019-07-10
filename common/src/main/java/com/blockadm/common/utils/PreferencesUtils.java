package com.blockadm.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * PreferencesUtils, easy to get or put data
 * <ul>
 * <strong>Preference Name</strong>
 * <li>you can change preference name by {@link #TB_APP_CONFIG}</li>
 * </ul>
 * <ul>
 * <strong>Put Value</strong>
 * <li>put string {@link #putString(Context, String, String)}</li>
 * <li>put int {@link #putInt(Context, String, int)}</li>
 * <li>put long {@link #putLong(Context, String, long)}</li>
 * <li>put float {@link #putFloat(Context, String, float)}</li>
 * <li>put boolean {@link #putBoolean(Context, String, boolean)}</li>
 * </ul>
 * <ul>
 * <strong>Get Value</strong>
 * <li>get string {@link #getString(Context, String)},
 * {@link #getString(Context, String, String)}</li>
 * <li>get int {@link #getInt(Context, String)},
 * {@link #getInt(Context, String, int)}</li>
 * <li>get long {@link #getLong(Context, String)},
 * {@link #getLong(Context, String, long)}</li>
 * <li>get float {@link #getFloat(Context, String)},
 * {@link #getFloat(Context, String, float)}</li>
 * <li>get boolean {@link #getBoolean(Context, String)},
 * {@link #getBoolean(Context, String, boolean)}</li>
 * </ul>
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-3-6
 */
public class PreferencesUtils {

	//APP配置数据，不会因为用户不同而注销数据
	public static String TB_APP_CONFIG = "TB_APP_CONFIG";
	//注销后都会清除数据
	public static String TB_USER_CONFIG = "TB_USER_CONFIG";
	private PreferencesUtils() {
		throw new AssertionError();
	}

	private static SharedPreferences appSettings;
	private static SharedPreferences userSettings;


	/**
	 * put string preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putAppString(Context context, String key, String value) {

		if (appSettings == null)
			appSettings = context.getSharedPreferences(TB_APP_CONFIG,
					Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = appSettings.edit();
		editor.putString(key, value);
		return editor.commit();
	}



	public static boolean putUserString(Context context, String key,
			String value) {
		if (userSettings == null)
			userSettings = context.getSharedPreferences(
					TB_USER_CONFIG, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = userSettings.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	/**
	 * get string preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or null. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a string
	 * @see #getString(Context, String, String)
	 */
	public static String getAppString(Context context, String key) {
		return getAppString(context, key, "");
	}

	public static String getUserString(Context context, String key) {

		return getUserString(context, key, "");
	}

	public static String getPersonString(Context context, String key) {

		return getUserString(context, key, "");
	}

	/**
	 * get string preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a string
	 */
	public static String getAppString(Context context, String key,
			String defaultValue) {
		if (appSettings == null)
			appSettings = context.getSharedPreferences(
					TB_APP_CONFIG, Context.MODE_PRIVATE);

		return appSettings.getString(key, defaultValue);
	}

	public static String getUserString(Context context, String key,
			String defaultValue) {

		if (userSettings == null)
			userSettings = context.getSharedPreferences(
					TB_USER_CONFIG, Context.MODE_PRIVATE);
		String temp = userSettings.getString(key, defaultValue);
		return temp;
	}

	/**
	 * put int preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putAppInt(Context context, String key, int value) {

		if (appSettings == null)
			appSettings = context.getSharedPreferences(
					TB_APP_CONFIG, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = appSettings.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	public static boolean putUserInt(Context context, String key, int value) {

		if (userSettings == null)
			userSettings = context.getSharedPreferences(
					TB_USER_CONFIG, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = userSettings.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	/**
	 * get int preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a int
	 * @see #getInt(Context, String, int)
	 */
	public static int getAppInt(Context context, String key) {
		return getAppInt(context, key, -1);
	}

	/**
	 * get int preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a int
	 */
	public static int getAppInt(Context context, String key, int defaultValue) {

		if (appSettings == null)
			appSettings = context.getSharedPreferences(
					TB_APP_CONFIG, Context.MODE_PRIVATE);
		return appSettings.getInt(key, defaultValue);
	}

	public static int getUserInt(Context context, String key) {

		return getUserInt(context, key, -1);
	}

	public static int getUserInt(Context context, String key, int defaultValue) {

		if (userSettings == null)
			userSettings = context.getSharedPreferences(
					TB_USER_CONFIG, Context.MODE_PRIVATE);
		return userSettings.getInt(key, defaultValue);
	}

	/**
	 * put long preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putAppLong(Context context, String key, long value) {

		if (appSettings == null)
			appSettings = context.getSharedPreferences(
					TB_APP_CONFIG, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = appSettings.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	/**
	 * get long preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a long
	 * @see #getLong(Context, String, long)
	 */
	public static long getAppLong(Context context, String key) {
		return getAppLong(context, key, -1);
	}

	/**
	 * get long preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a long
	 */
	public static long getAppLong(Context context, String key, long defaultValue) {

		if (appSettings == null)
			appSettings = context.getSharedPreferences(
					TB_APP_CONFIG, Context.MODE_PRIVATE);
		return appSettings.getLong(key, defaultValue);
	}

	/**
	 * put float preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putAppFloat(Context context, String key, float value) {
		if (appSettings == null)
			appSettings = context.getSharedPreferences(
					TB_APP_CONFIG, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = appSettings.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}

	/**
	 * get float preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a float
	 * @see #getFloat(Context, String, float)
	 */
	public static float getAppFloat(Context context, String key) {
		return getAppFloat(context, key, -1);
	}

	/**
	 * get float preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a float
	 */
	public static float getAppFloat(Context context, String key, float defaultValue) {
		if (appSettings == null)
			appSettings = context.getSharedPreferences(
					TB_APP_CONFIG, Context.MODE_PRIVATE);
		return appSettings.getFloat(key, defaultValue);
	}

	/**
	 * put boolean preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putAppBoolean(Context context, String key, boolean value) {
		if (appSettings == null)
			appSettings = context.getSharedPreferences(
					TB_APP_CONFIG, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = appSettings.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	public static boolean putUserBoolean(Context context, String key, boolean value) {
		if (userSettings == null)
			userSettings = context.getSharedPreferences(
					TB_USER_CONFIG, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = userSettings.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	/**
	 * get boolean preferences, default is false
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or false. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a boolean
	 * @see #getBoolean(Context, String, boolean)
	 */
	public static boolean getAppBoolean(Context context, String key) {
		return getAppBoolean(context, key, false);
	}

	public static boolean getUserBoolean(Context context, String key) {
		return getUserBoolean(context, key, false);
	}

	/**
	 * get boolean preferences
	 *
	 * @param mContext
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a boolean
	 */
	public static boolean getAppBoolean(Context context, String key,
			boolean defaultValue) {
		if (appSettings == null)
			appSettings = context.getSharedPreferences(
					TB_APP_CONFIG, Context.MODE_PRIVATE);
		return appSettings.getBoolean(key, defaultValue);
	}

	public static boolean getUserBoolean(Context context, String key,
			boolean defaultValue) {
		if (userSettings == null)
			userSettings = context.getSharedPreferences(
					TB_USER_CONFIG, Context.MODE_PRIVATE);
		return userSettings.getBoolean(key, defaultValue);
	}

	public static boolean hasAppKey(Context context, final String key) {
		if (appSettings == null)
			appSettings = context.getSharedPreferences(
					TB_APP_CONFIG, Context.MODE_PRIVATE);
		return appSettings.contains(key);
	}

	public static void clearAppData(Context context) {
		if (appSettings == null)
			appSettings = context.getSharedPreferences(
					TB_APP_CONFIG, Context.MODE_PRIVATE);
		Editor editor = appSettings.edit();
		editor.clear();
		editor.commit();
	}

	public static boolean cleanUserData(Context context) {
		if (userSettings == null)
			userSettings = context.getSharedPreferences(TB_USER_CONFIG,
					Context.MODE_PRIVATE);
		Editor editor = userSettings.edit();
		editor.clear();
		return editor.commit();
	}
}
