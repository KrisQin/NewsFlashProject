package tv.danmaku.ijk.media.player.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 */
public final class Utils {

    public static String timeFormat(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(ms);
        return hms;
    }

    public static boolean isAudio(File file) {
        if (null != file) {
            String fileName = file.getName();
            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (prefix.equals("mp3") || prefix.equals("aac") || prefix.equals("amr") || prefix.equals("ape")//
                    || prefix.equals("flac") || prefix.equals("mmf") || prefix.equals("mp2")//
                    || prefix.equals("ogg") || prefix.equals("wav") || prefix.equals("wma")//
                    || prefix.equals("m4r") || prefix.equals("wv")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    public static boolean isVideo(File file) {
        if (null != file) {
            String fileName = file.getName();
            int index = fileName.lastIndexOf(".");
            if(index == -1){
                return false;
            }
            String prefix = fileName.substring(index + 1);
            if (prefix.equals("mp4") || prefix.equals("3gp") || prefix.equals("avi") || prefix.equals("flv")//
                    || prefix.equals("mkv") || prefix.equals("mov") || prefix.equals("mpg")//
                    || prefix.equals("rmvb") || prefix.equals("swf") || prefix.equals("vob")//
                    || prefix.equals("wmv") || prefix.equals("gif")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    public static String timeFormat(int time) {
        int iTime = time / 1000;
        int minute = iTime / 60;
        int hour = minute / 60;
        int second = iTime % 60;
        minute %= 60;
        String value = null;
        if (hour == 0) {
            value = String.format("%02d:%02d", minute, second);
        } else {
            value = String.format("%02d:%02d:%02d", hour, minute, second);
        }
        return value;
    }
}
