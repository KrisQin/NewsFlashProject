package com.blockadm.common.base;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.common.BuildConfig;
import com.blockadm.common.R;
import com.blockadm.common.config.AppConfig;
import com.blockadm.common.exception.CrashHandler;
import com.blockadm.common.exception.LogcatHelper;
import com.blockadm.common.greeddao.manager.GreenDaoManager;
import com.blockadm.common.im.BaseUIKitConfigs;
import com.blockadm.common.im.LiveManager;
import com.blockadm.common.im.TUIKit;
import com.blockadm.common.im.UIKitConstants;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.Utils;
import com.blockadm.common.wxpay.Constants;
import com.github.anrwatchdog.ANRWatchDog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.RecordConfig;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hp on 2018/12/13.
 */

public class BaseApplication extends MultiDexApplication {


    private List<Activity> activities = new ArrayList<Activity>();



    @Override
    public void onCreate() {
        super.onCreate();


        GreenDaoManager.getInstance(this);
        LitePal.initialize(this);
        if (BuildConfig.isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        ContextUtils.init(this);
        Utils.init(this);

        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context,
                                                     @NonNull RefreshLayout layout) {
                return new ClassicsHeader(BaseApplication.this);
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context,
                                                     @NonNull RefreshLayout layout) {
                return new ClassicsFooter(BaseApplication.this);
            }
        });

        //本地Log记录
        LogcatHelper.getInstance(this).init(AppConfig.fileCrashLogPath).start();
        //crash记录及上传
        CrashHandler.getInstance().init(this);

        //ANR监控
        new ANRWatchDog(5000).start();

        //ANR监控
        //        initLeakCanary();

        //社群聊天初始化
        LiveManager.getInstance().initSdk(this);

        //录音初始化
        recordInit();

    }




    public static BaseApplication getInstance() {
        return SingletonHolder.instance;
    }

    //内部类，在装载该内部类时才会去创建单利对象
    private static class SingletonHolder {
        public static BaseApplication instance = new BaseApplication();
    }

    private void recordInit() {
        /**
         * 参数1： Application 实例
         * 参数2： 是否打印日志
         */
        RecordManager.getInstance().init(this, true);
        //录音文件保存格式
        RecordManager.getInstance().changeFormat(RecordConfig.RecordFormat.MP3);
        //录音文件保存路径
        RecordManager.getInstance().changeRecordDir(UIKitConstants.RECORD_DIR);
        //修改录音配置
        //音频采样率,如：16000Hz
        RecordManager.getInstance().changeRecordConfig(RecordManager.getInstance().getRecordConfig().setSampleRate(16000));
        //音频位宽，如：8Bit
        RecordManager.getInstance().changeRecordConfig(RecordManager.getInstance().getRecordConfig().setEncodingConfig(AudioFormat.ENCODING_PCM_8BIT));
    }

    /**
     * 初始化内存泄漏检测
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }


    public void addActivity(BaseActivity activity) {

        activities.add(activity);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (Activity activity : activities) {
            activity.finish();
        }
        System.exit(0);
    }



    public void clear() {

    }

    public void clearActivity() {

    }
}
