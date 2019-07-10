package com.blockadm.common.base;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.common.BuildConfig;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.Utils;
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


        //ANR监控
        new ANRWatchDog(5000).start();


    }




    public static BaseApplication getInstance() {
        return SingletonHolder.instance;
    }

    //内部类，在装载该内部类时才会去创建单利对象
    private static class SingletonHolder {
        public static BaseApplication instance = new BaseApplication();
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
