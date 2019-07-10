package com.blockadm.adm;

import android.app.Activity;
import android.content.ServiceConnection;

import com.blockadm.adm.service.PlayService;
import com.blockadm.common.base.BaseActivity;
import com.blockadm.common.base.BaseApplication;
import com.blockadm.common.bean.PalyDetailDto;
import com.blockadm.common.bean.RecordsBean;
import com.blockadm.common.wxpay.Constants;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Administrator on 2019/2/24.
 */

public class MainApp extends BaseApplication {

    private static ServiceConnection serviceConnection;
    private static String orderNum;
    private static int isSeeStatus;

    public static int getIsSeeStatus() {
        return isSeeStatus;
    }

    public static void setIsSeeStatus(int isSeeStatus) {
        MainApp.isSeeStatus = isSeeStatus;
    }

    public static void setServiceConnection(ServiceConnection serviceConnection) {
        MainApp.serviceConnection = serviceConnection;
    }

    public static ServiceConnection getServiceConnection() {
        return serviceConnection;
    }

    private static int palyerId;

    private static RecordsBean recordsBean;

    public static int getPalyerId() {
        return palyerId;
    }

    public static void setPalyerId(int palyerId) {
        MainApp.palyerId = palyerId;
    }

    public static RecordsBean getRecordsBean() {
        return recordsBean;
    }

    public static void setRecordsBean(RecordsBean recordsBean) {
        MainApp.recordsBean = recordsBean;
    }

    public static String getOrderNum() {
        return orderNum;
    }

    public static void
    setOrderNum(String orderNum) {
        MainApp.orderNum = orderNum;
    }

    private static boolean threellogin;

    public static boolean isThreellogin() {
        return threellogin;
    }

    public static void setThreellogin(boolean threellogin) {
        MainApp.threellogin = threellogin;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, "5c7e2c5b3fc19538fc0005ef"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2
        // 5965ee00734be40b580001a0


        //友盟相关平台配置。注意友盟官方新文档中没有这项配置，但是如果不配置会吊不起来相关平台的授权界面
        //8ae614b54465863fe83defa181326925  a19e43725e2f6f012fda581f872bd5ce
        //微信APPID和AppSecret
        PlatformConfig.setWeixin(Constants.WX_APP_ID, Constants.WX_APP_SECRET);
        PlatformConfig.setQQZone("1108074970", "3cdd57368f36104e6b97233e710ec2d2");//QQAPPID
        // 和AppSecret
        PlatformConfig.setSinaWeibo("3343088060", "a8884fb760b052775986d91e99e8fbe7", "https" +
                "://www.guokr.com/mobile/");//微博
        //CrashHandler.getInstance().init(this);

        closeAndroidPDialog();
    }


    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            if (declaredConstructor != null)
                declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = null;
            Field mHiddenApiWarningShown = null;
            if (cls != null) {
                declaredMethod = cls.getDeclaredMethod("currentActivityThread");
                mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            }

            Object activityThread = null;
            if (declaredMethod != null) {
                declaredMethod.setAccessible(true);
                if (declaredMethod != null)
                    activityThread = declaredMethod.invoke(null);
            }

            if (mHiddenApiWarningShown != null) {
                mHiddenApiWarningShown.setAccessible(true);
                mHiddenApiWarningShown.setBoolean(activityThread, true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static PalyDetailDto getPlayerRes() {
        return playerRes;
    }

    public static void setPlayerRes(PalyDetailDto playerRes) {
        MainApp.playerRes = playerRes;
    }

    private static PlayService playService1;
    private static PalyDetailDto playerRes;

    public static PlayService getPlayService1() {
        return playService1;
    }

    public static void setPlayService1(PlayService playService1) {
        MainApp.playService1 = playService1;
    }

    @Override
    public void clear() {
        super.clear();
        if (playService1 != null && playService1.getMediaPlayer() != null) {
            playService1.getMediaPlayer().pause();
            playService1.getMediaPlayer().reset();
        }

        serviceConnection = null;
        playerRes = null;
        playService1 = null;
        orderNum = "";
        palyerId = -1;
        recordsBean = null;
        isSeeStatus = -1;

    }

    private ArrayList<BaseActivity> activitys = new ArrayList<>();

    @Override
    public void clearActivity() {
        for (int i = 0; i < activitys.size(); i++) {
            Activity activity = activitys.get(i);
            activity.finish();
        }
    }

    @Override
    public void addActivity(BaseActivity activity) {
        activitys.add(activity);
    }
}
