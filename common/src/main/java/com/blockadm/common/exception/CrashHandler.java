package com.blockadm.common.exception;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import com.blockadm.common.base.BaseApplication;
import com.blockadm.common.config.AppConfig;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.EmailUtil;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.TimeUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;

import javax.mail.MessagingException;


public class CrashHandler implements UncaughtExceptionHandler {
    /**
     * Debug Log tag
     */
    public final String TAG = "CrashHandler";
    /**
     * 是否开启日志输出,在Debug状态下开启, 在Release状态下关闭以提示程序性能
     */
    public final boolean DEBUG = true;
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;
    /**
     * CrashHandler实例
     */
    private static CrashHandler INSTANCE;
    /**
     * 程序的Context对象
     */
    private Context mContext;

    /**
     * 使用Properties来保存设备的信息和错误堆栈信息
     */
    private Properties mDeviceCrashInfo = new Properties();
    private final String APP_NAME = "appName";
    private final String VERSION_NAME = "versionName";
    private final String VERSION_CODE = "versionCode";
    private final String STACK_TRACE = "STACK_TRACE";
    /**
     * 错误报告文件的扩展名
     */
    private final String CRASH_REPORTER_EXTENSION = ".cr";

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public void init(Context ctx) {
        mContext = ctx;

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
            L.i(TAG, "处理结束======");
        } else {
            // Sleep一会后结束程序
            L.i(TAG, "处理错误完成后，休息3秒后干掉当前应用进程======");

            try {
                Thread.sleep(3000);

                restart();

                if (mContext != null) {
                    MobclickAgent.onKillProcess(mContext);
                }else {
                    MobclickAgent.onKillProcess(ContextUtils.getBaseApplication());
                }

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return true;
        }
        mDeviceCrashInfo.put(APP_NAME, AppConfig.APP_NAME);
        final String msg = ex.getLocalizedMessage();
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                try {
                    Looper.prepare();

                    collectCrashDeviceInfo(mContext);
                    // 保存错误报告文件
                    saveCrashInfoToFile(ex);
                    // 发送错误报告到服务器,暂时没有用到,以后可以加上
                    // sendCrashReportsToServer(mContext);
                    Looper.loop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();

        return true;
    }

    /**
     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
     */
    public void sendPreviousReportsToServer() {
        sendCrashReportsToServer(mContext);
    }

    /**
     * 把错误报告发送给服务器,包含新产生的和以前没发送的.
     *
     * @param ctx
     */
    private void sendCrashReportsToServer(Context ctx) {
        String[] crFiles = getCrashReportFiles(ctx);
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));

            for (String fileName : sortedFiles) {
                File cr = new File(ctx.getFilesDir(), fileName);
                postReport(cr);
                cr.delete();// 删除已发送的报告
            }
        }
    }

    private void postReport(File file) {
        // 这里不再详述,开发者可以根据OPhoneSDN上的其他网络操作
        // 教程来提交错误报告
    }

    /**
     * 获取错误报告文件名
     *
     * @param ctx
     * @return
     */
    private String[] getCrashReportFiles(Context ctx) {
        File filesDir = ctx.getFilesDir();
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        return filesDir.list(filter);
    }

    /**
     * ***********************************
     *
     * @param @param  ex
     * @param @return
     * @return String
     * @throws ***********************************
     * @Title:
     * @Description: 保存错误信息到SD卡中
     */
    private String saveCrashInfoToFile(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<Object, Object> entry : mDeviceCrashInfo.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            sb.append(key + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        mDeviceCrashInfo.put(STACK_TRACE, result);
        // 这里把刚才异常堆栈信息写入SD卡的Log日志里面

        String localFileUrl = writeLog(sb.toString(), AppConfig.fileCrashLogPath);
        L.i(TAG, "崩溃信息已经写入路径： " + localFileUrl);
        Log.e("error", result);
        try {
            EmailUtil.sendEmail(AppConfig.getInstance().getCrashEmail(), android.os.Build.MODEL+" APP异常 ",mDeviceCrashInfo.toString());
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * @param log
     * @param name
     * @return 返回写入的文件路径 写入Log信息的方法，写入到SD卡里面
     */
    private String writeLog(String log, String name) {
        CharSequence timestamp = new Date().toString().replace(" ", "");
        timestamp = "crash";
        String filename = name + timestamp + ".log";
        File file = new File(filename);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            Log.d(TAG, "写入crash信息到SD卡里面=====");
            file.createNewFile();
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            // 写入相关Log到文件
            bw.write(log);
            bw.newLine();
            bw.close();
            fw.close();
            return filename;
        } catch (IOException e) {
            Log.e(TAG, "an error occured while writing file...", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ***********************************
     *
     * @param
     * @return void
     * @throws ***********************************
     * @Title:
     * @Description: 崩溃后重新启动应用
     */
    private void restart() {
        L.i(TAG, "启动重启应用闹钟======");
        BaseApplication baseApplication = (BaseApplication) ContextUtils.getBaseApplication();
        Intent intent1 = baseApplication.getApplicationContext().getPackageManager().
                getLaunchIntentForPackage(baseApplication.getApplicationContext().getPackageName());
        PendingIntent restartIntent =
                PendingIntent.getActivity(baseApplication.getApplicationContext(), 0, intent1,
                        PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr =
                (AlarmManager) baseApplication.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 3秒钟后重启应用
    }


    /**
     * ***********************************
     *
     * @param @param ctx
     * @return void
     * @throws ***********************************
     * @Title:
     * @Description: 收集程序崩溃的设备信息
     */
    public void collectCrashDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" :
                        pi.versionName);
                mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode);

                mDeviceCrashInfo.put("crashTime", TimeUtils.getCurrentTimeInString());
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        // 使用反射来收集设备信息.在Build类中包含各种设备信息,
        // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        // 具体信息请参考后面的截图
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), field.get(null));
                if (DEBUG) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}