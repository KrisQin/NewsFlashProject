package com.blockadm.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedpfTools {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static SharedpfTools instance;
    public static final String FILE_NAME = "shared_data";


    public static final String userSig = "userSig";
    public static final String identifier = "identifier";

    public SharedpfTools(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME,context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static SharedpfTools getInstance() {
        if (instance == null) {
            instance = new SharedpfTools(ContextUtils.getBaseApplication());
        }
        return instance;
    }

    public static SharedpfTools getInstance(Context context) {
        if (instance == null) {
            instance = new SharedpfTools(ContextUtils.getBaseApplication());
        }
        return instance;
    }

    /**
     * 保存数据的方法，拿到数据保存数据的基本类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public void put(String key, Object object) {

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    /**
     * 获取保存数据的方法，我们根据默认值的到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key           键的值
     * @param defaultObject 默认值
     * @return
     */

    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return preferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return preferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return preferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return preferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return preferences.getLong(key, (Long) defaultObject);
        } else {
            return preferences.getString(key, null);
        }

    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */


    /**
     * 退出登录，清除缓存
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * remove
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }



}
