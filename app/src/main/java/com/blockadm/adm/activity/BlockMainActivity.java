package com.blockadm.adm.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.R;
import com.blockadm.adm.dialog.MyComstomDialogView;
import com.blockadm.adm.downloadapk.DownloadProgressDialog;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.UpdataVersionDto;
import com.blockadm.common.config.ARouterPathConfig;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.im.call.ComCallback;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.OSUtils;
import com.blockadm.common.utils.StatusBarUtil;
import com.blockadm.common.utils.StringUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.io.File;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by 覃毛丹 on 2019/5/30
 *
 * @Describe TODO {  }
 */

@Route(path = ARouterPathConfig.block_main_activity_path)
public class BlockMainActivity extends TabActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(android.R.id.tabs)
    TabWidget tabs;
    @BindView(R.id.tab_line)
    View tabLine;
    @BindView(R.id.rb_information)
    RadioButton rbInformation;
    @BindView(R.id.rb_study)
    RadioButton rbStudy;
    @BindView(R.id.rb_community)
    RadioButton rbCommunity;
    @BindView(R.id.rb_user)
    RadioButton rbUser;
    @BindView(R.id.rg_main_radio)
    RadioGroup radioGroup;
    @BindView(R.id.layout_tab)
    LinearLayout layoutTab;
    @BindView(android.R.id.tabhost)
    TabHost mTabHost;
    private long mDownloadId;
    private DownloadManager mDownloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarColor();
        ARouter.getInstance().inject(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        setContentView(R.layout.activity_main_block);
        ButterKnife.bind(this);
        initView(savedInstanceState);

        rbInformation.setChecked(true);
        if (radioGroup != null)
            radioGroup.setOnCheckedChangeListener(this);

        checkPermission();
    }

    private void initView(Bundle savedInstanceState) {

        mTabHost.addTab(mTabHost.newTabSpec("information_tab").setIndicator("0").setContent(new Intent(this, InformationTabActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("study_tab").setIndicator("1").setContent(new Intent(this, StudyTabTestActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("community_tab").setIndicator("2").setContent(new Intent(this, CommunityTabActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("user_tab").setIndicator("3").setContent(new Intent(this, UserTabActivity.class)));

    }

    private void checkPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里填写所需要的权限
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //当所有权限都允许之后，返回true
                            updateApk();
                        } else {
                            //只要有一个权限禁止，返回false，
                            //下一次申请只申请没通过申请的权限


                            
                        }
                    }
                });
    }

    //TODO ------------------------- begin
    // -------------------------------------------------------------------


    private void updateApk() {
        {
            String code = OSUtils.getAppVersionCode(this);

            CommonModel.getAppUpdateData(code, 0, new MyObserver<UpdataVersionDto>() {
                @Override
                public void onSuccess(final BaseResponse<UpdataVersionDto> t) {
                    if (t.getCode() == 0) {
                        if (!t.getData().getUpdateState().equals("0")) {
                            final MyComstomDialogView myComstomDialogView =
                                    new MyComstomDialogView(BlockMainActivity.this);
                            myComstomDialogView.setTvTitle("有新版本更新", View.VISIBLE).setRootBg(R.mipmap.boxbg2)
                                    .setChildMsg("", View.GONE)
                                    .setChildMsg2("", View.GONE)
                                    .setConcelMsg("", View.GONE)
                                    .setConfirmMsg("现在更新", View.VISIBLE)
                                    .setConcelMsg("暂不更新", View.VISIBLE)
                                    .setConfirmSize(6)
                                    .setCancelLisenner(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            myComstomDialogView.dismiss();
                                        }
                                    }).setConfirmLisenner(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    downloadBySelf(BlockMainActivity.this,
                                            t.getData().getUpdateUrl(),
                                            "aidemei");
                                    if (t.getData().getUpdateState().equals("2")) {
                                        new Thread() {
                                            public void run() {
                                                try {
                                                    Instrumentation inst = new Instrumentation();
                                                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                                                } catch (Exception e) {
                                                    Log.e("Exception when onBack", e.toString());
                                                }
                                            }
                                        }.start();
                                    }
                                    myComstomDialogView.dismiss();
                                }
                            });
                            if (t.getData().getUpdateState().equals("2")) {
                                myComstomDialogView.setConcelButtonEable(false);
                            }
                            myComstomDialogView.show();
                        } else {
                            //Toast.makeText(MainComActivity.this,t.getData().getUpdateMsg(),Toast
                            // .LENGTH_SHORT).show();
                        }

                    }

                }


            });
        }
    }

    private String downloadUpdateApkFilePath;

    private void downloadBySelf(Context context, String apkUrl, String fileName) {
        {
            if (TextUtils.isEmpty(apkUrl)) {
                return;
            }
            try {
                Uri uri = Uri.parse(apkUrl);
                mDownloadManager = (DownloadManager) context
                        .getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                //在通知栏中显示
                request.setVisibleInDownloadsUi(true);
                request.setTitle("应用更新");
                request.setDescription("更新爱德媒新版本");
                //MIME_MapTable是所有文件的后缀名所对应的MIME类型的一个String数组  {".apk",    "application/vnd.android
                // .package-archive"},
                request.setMimeType("application/vnd.android.package-archive");
                // 在通知栏通知下载中和下载完成
                // 下载完成后该Notification才会被显示
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                    // Android 3.0版本 以后才有该方法
                    //在下载过程中通知栏会一直显示该下载的Notification，在下载完成后该Notification会继续显示，直到用户点击该Notification
                    // 或者消除该Notification
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                }
                String filePath = null;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    //外部存储卡
                    filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                } else {
                    Log.i("abount", "没有SD卡");
                    return;
                }
                downloadUpdateApkFilePath = filePath + File.separator + fileName + System.currentTimeMillis() + ".apk";
                // 若存在，则删除 (这里具体逻辑具体看,我这里是删除)

                Uri fileUri = Uri.fromFile(new File(downloadUpdateApkFilePath));
                request.setDestinationUri(fileUri);
                //下载管理Id
                mDownloadManager.enqueue(request);
                DownloadReceiver mDownloaderReceiver = new DownloadReceiver();
                //注册下载完成广播
                context.registerReceiver(mDownloaderReceiver,
                        new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                // 加入下载队列
                mDownloadId = mDownloadManager.enqueue(request);
                startQuery();

            } catch (Exception e) {
                e.printStackTrace();
                //注意:如果文件下载失败则 使用浏览器下载
                // downloadByWeb(context, apkUrl);
            }
        }
    }

    //更新下载进度
    private void startQuery() {
        if (mDownloadId != 0) {
            //            displayProgressDialog();
            mHandler.post(mQueryProgressRunnable);
        }
    }

    private final QueryRunnable mQueryProgressRunnable = new QueryRunnable();
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            L.d("xsdd",
                    "msg.what: " + msg.what + " ; msg.arg1: " + msg.arg1 + " ; msg.arg2: " + msg.arg2);
            if (msg.what == 1001) {

                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(1);

                String current = nf.format(msg.arg1 * 1.0 / (1024 * 1024));
                String total = nf.format(msg.arg2 * 1.0 / (1024.0 * 1024));

                String mm = current+"M/"+total+"M";

                String per = StringUtils.changeToNoZeroPercentage( msg.arg1 * 1.0 / msg.arg2+"");

                showProgress(msg.arg1, msg.arg2,per,mm);

                if (msg.arg1 == msg.arg2) {
                    mHandler.removeCallbacksAndMessages(null);
                }
            }
        }
    };

    DownloadProgressDialog mDownloadProgressDialog;

    private void showProgress(int progressBar, int max, String per, String mm) {
        if (mDownloadProgressDialog == null) {
            mDownloadProgressDialog = new DownloadProgressDialog(this);
        }

        mDownloadProgressDialog.setOnCancelListener(new DownloadProgressDialog.CancelListener() {
            @Override
            public void callback() {
                mHandler.removeCallbacksAndMessages(null);
            }
        });

        mDownloadProgressDialog.setProgressBar(progressBar, max);
        mDownloadProgressDialog.setProgressData(per, mm);
        mDownloadProgressDialog.setDownComplete(progressBar == max, new ComCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void callback() {
                if (!TextUtils.isEmpty(downloadUpdateApkFilePath)) {
                    installNormal(BlockMainActivity.this, downloadUpdateApkFilePath);
                }}
        });

        if (!mDownloadProgressDialog.isShowing()) {
            mDownloadProgressDialog.show();
        }

    }

    //查询下载进度
    private class QueryRunnable implements Runnable {
        @Override
        public void run() {
            queryState();
            mHandler.postDelayed(mQueryProgressRunnable, 100);
        }

    }

    //查询下载进度
    private void queryState() {
        // 通过ID向下载管理查询下载情况，返回一个cursor
        Cursor c = mDownloadManager.query(new DownloadManager.Query().setFilterById(mDownloadId));
        if (c == null) {
            Toast.makeText(this, "下载失败", Toast.LENGTH_SHORT).show();
        } else { // 以下是从游标中进行信息提取
            if (!c.moveToFirst()) {
                Toast.makeText(this, "下载失败", Toast.LENGTH_SHORT).show();
                if (!c.isClosed()) {
                    c.close();
                }
                return;
            }
            int mDownload_so_far =
                    c.getInt(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            int mDownload_all = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            Message msg = Message.obtain();
            if (mDownload_all > 0) {
                msg.what = 1001;
                msg.arg1 = mDownload_so_far;
                msg.arg2 = mDownload_all;
                mHandler.sendMessage(msg);
            }
            if (!c.isClosed()) {
                c.close();
            }
        }
    }


    //TODO ------------------------ end
    // --------------------------------------------------------------------

    public class DownloadReceiver extends BroadcastReceiver {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!TextUtils.isEmpty(downloadUpdateApkFilePath)) {
                installNormal(context, downloadUpdateApkFilePath);
            }
        }
    }


    @Override
    protected void onPause() {
        ContextUtils.dismiss();
        super.onPause();
    }

    /**
     * 提示安装
     *
     * @param context 上下文
     * @param apkPath apk下载完成在手机中的路径
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void installNormal(Context context, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {

            boolean hasInstallPermission = isHasInstallPermissionWithO(context);
            if (!hasInstallPermission) {
                startInstallPermissionSettingActivity(context);
                return;
            }

            File file = (new File(apkPath));
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1:上下文, 参数2:Provider主机地址 和配置文件中保持一致,参数3:共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.adm.fileprovider_app", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isHasInstallPermissionWithO(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().canRequestPackageInstalls();
    }

    /**
     * 开启设置安装未知来源应用权限界面
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        ((Activity) context).startActivityForResult(intent, 3233);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK && requestCode == 3233) {
            installNormal(this, downloadUpdateApkFilePath);
        }

    }


    private void setBarColor() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(com.blockadm.common.R.color.color_ffffff));
                StatusBarUtil.setImmersiveStatusBar(this, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int selectIndex = 0;
        switch (checkedId) {
            case R.id.rb_information:
                selectIndex = 0;
                break;
            case R.id.rb_study:
                selectIndex = 1;
                break;
            case R.id.rb_community:
                selectIndex = 2;
                break;
            case R.id.rb_user:
                selectIndex = 3;
                break;
        }

        mTabHost.setCurrentTab(selectIndex);
    }

    //    public void onEventMainThread(ComEvent event) {
    //        if ("finish_app".equals(event.getAction())) {
    //            moveTaskToBack(true);
    //        }
    //    }

    // 2次点击退出
    private long exitTime = 0;

    /**
     * 退出
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                /**
                 * 参数为false——代表只有当前activity是task根，指应用启动的第一个activity时，才有效;
                 参数为true——则忽略这个限制，任何activity都可以有效。
                 */
                //                EventBus.getDefault().post(new ComEvent("finish_app"));
                moveTaskToBack(true);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);

    }


    @Override
    protected void onDestroy() {
        //        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
