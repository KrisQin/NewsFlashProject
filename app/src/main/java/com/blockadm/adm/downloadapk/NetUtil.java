package com.blockadm.adm.downloadapk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;

public class NetUtil {
	public static final int NETWORN_NONE = 0;
	public static final int NETWORN_WIFI = 1;
	public static final int NETWORN_MOBILE = 2;

	public static int getNetworkState(Context context) {
		if(context == null){
			return NETWORN_NONE;
		}


		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// Wifi
		State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (state == State.CONNECTED || state == State.CONNECTING) {
			return NETWORN_WIFI;
		}

		// 3G
		state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		if (state == State.CONNECTED || state == State.CONNECTING) {
			return NETWORN_MOBILE;
		}
		return NETWORN_NONE;
	}
	

//    /**
//     * 判断网络是否连接
//     */
//    public static boolean checkNet(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = cm.getActiveNetworkInfo();
//        return info != null;// 网络是否连接
//    }

//    /**
//     * 仅wifi联网功能是否开启
//     */
//    public static boolean checkOnlyWifi(Context context) {
//        if (PreferencesUtils.getBoolean(context, PreferenceConstants.ONLY_WIFI,
//        		false)) {
//            return isWiFi(context);
//        } else {
//            return true;
//        }
//    }

    /**
     * 判断是否为wifi联网
     */
    public static boolean isWiFi(Context cxt) {
		if(cxt == null){
			return false;
		}


        ConnectivityManager cm = (ConnectivityManager) cxt
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // wifi的状态：ConnectivityManager.TYPE_WIFI
        // 3G的状态：ConnectivityManager.TYPE_MOBILE
        State state = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        return State.CONNECTED == state;
    }
    
    /****************************************
	 * 判断wifi是否已打开
	 * 
	 * @param：mContext：上下文
	 * @return：boolean 打开true，关闭false
	 * @author：黄俊鑫
	 ******************************************/
	public boolean IsWifiEnabled(Context context) {
		WifiManager wifiMgr = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiMgr.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
			return true;
		} else {
			return false;
		}
	}


	/****************************************
	 * * 判断网络是否正常（wifi或者3G）
	 * 
	 * @param：mContext：上下文
	 * @return：boolean 打开true，关闭false
	 * @author：黄俊鑫
	 ******************************************/
	public static boolean isNetWorkConnected(Context context) {
		if(context == null){
			return false;
		}


		boolean netWorkInfoState = false;
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		

	
		
		if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
			netWorkInfoState = true;

		} else {
			State mobile = manager.getNetworkInfo(
					ConnectivityManager.TYPE_MOBILE).getState();
			if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
				netWorkInfoState = true;
			}
		}
		if (netWorkInfoState) {
			NetworkInfo info = manager.getActiveNetworkInfo();
			if (info == null || !info.isAvailable()) {
				return false;
			}else {
				return true;
			}
		}
		return false;

	}
	
	

	
}
