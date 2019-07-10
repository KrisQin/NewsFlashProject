package com.blockadm.adm.activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.dialog.MyComstomDialogView;
import com.blockadm.adm.downloadapk.DownloadProgressDialog;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.UpdataVersionDto;
import com.blockadm.common.call.ComCallback;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.OSUtils;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.call.ComCallback;

import java.io.File;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/3/19.
 */

public class AbountUsComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.v)
    View v;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    private static String downloadUpdateApkFilePath;
    private long mDownloadId;
    private DownloadManager mDownloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_abount_us);
        ButterKnife.bind(this);

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvVersion.setText("爱德媒 BlockADM "+ OSUtils.getAppVersionName(this));
    }

    @OnClick(R.id.iv_updata)
    public void onViewClicked() {
        updateApk();

    }

    private void updateApk() {
        String code =  OSUtils.getAppVersionCode(this);
        Log.i("code", code);
        CommonModel.getAppUpdateData(code, 0, new MyObserver<UpdataVersionDto>() {
            @Override
            public void onSuccess(final BaseResponse<UpdataVersionDto> t) {
                if (t.getCode()==0){
                    if (!t.getData().getUpdateState().equals("0")){
                        final MyComstomDialogView myComstomDialogView = new MyComstomDialogView(AbountUsComActivity.this);
                        myComstomDialogView.setTvTitle("有新版本更新", View.VISIBLE).setRootBg(R.mipmap.boxbg2)
                                .setChildMsg("",View.GONE)
                                .setChildMsg2("",View.GONE)
                                .setConcelMsg("",View.GONE)

                                .setConfirmMsg("现在更新",View.VISIBLE)
                                .setConcelMsg("暂不更新",View.VISIBLE)
                                .setConfirmSize(6)
                                .setCancelLisenner(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        myComstomDialogView.dismiss();
                                    }
                                }).setConfirmLisenner(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                downloadBySelf(AbountUsComActivity.this, t.getData().getUpdateUrl(), "aidemei");
                                if (t.getData().getUpdateState().equals("2")) {
                                    new Thread(){
                                        public void run() {
                                            try{
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
                        if (t.getData().getUpdateState().equals("2")){
                            myComstomDialogView.setConcelButtonEable(false);
                        }
                        myComstomDialogView.show();
                    }else{
                        Toast.makeText(AbountUsComActivity.this,t.getData().getUpdateMsg(),Toast.LENGTH_SHORT).show();
                    }

                }

            }


        });
    }


    private  void downloadBySelf(Context context, String apkUrl, String fileName) {
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
            //MIME_MapTable是所有文件的后缀名所对应的MIME类型的一个String数组  {".apk",    "application/vnd.android.package-archive"},
            request.setMimeType("application/vnd.android.package-archive");
            // 在通知栏通知下载中和下载完成
            // 下载完成后该Notification才会被显示
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                // Android 3.0版本 以后才有该方法
                //在下载过程中通知栏会一直显示该下载的Notification，在下载完成后该Notification会继续显示，直到用户点击该Notification或者消除该Notification
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            String filePath = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//外部存储卡
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
            context.registerReceiver(mDownloaderReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            // 加入下载队列
            mDownloadId = mDownloadManager.enqueue(request);
            startQuery();

        } catch (Exception e) {
            e.printStackTrace();
            //注意:如果文件下载失败则 使用浏览器下载
            // downloadByWeb(context, apkUrl);
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

    private void showProgress(int progress, int max, String per, String mm) {
        if (mDownloadProgressDialog == null) {
            mDownloadProgressDialog = new DownloadProgressDialog(this);
        }
        mDownloadProgressDialog.setOnCancelListener(new DownloadProgressDialog.CancelListener() {
            @Override
            public void callback() {
                mHandler.removeCallbacksAndMessages(null);
            }
        });
        mDownloadProgressDialog.setProgressBar(progress, max);
        mDownloadProgressDialog.setProgressData(per, mm);
        mDownloadProgressDialog.setDownComplete(progress == max, new ComCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void callback() {
                if (!TextUtils.isEmpty(downloadUpdateApkFilePath)) {
                    installNormal(AbountUsComActivity.this, downloadUpdateApkFilePath);
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


    public  class DownloadReceiver extends BroadcastReceiver {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!TextUtils.isEmpty(downloadUpdateApkFilePath)) {
                installNormal(context, downloadUpdateApkFilePath);
            }
        }
    }

    /**
     * 提示安装
     * @param context 上下文
     * @param apkPath apk下载完成在手机中的路径
     *
     *
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
    private boolean isHasInstallPermissionWithO(Context context){
        if (context == null){
            return false;
        }
        return context.getPackageManager().canRequestPackageInstalls();
    }

    /**
     * 开启设置安装未知来源应用权限界面
     * @param context
     */
    @RequiresApi (api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity(Context context) {
        if (context == null){
            return;
        }
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        ((Activity)context).startActivityForResult(intent,3236);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==this.RESULT_OK&&requestCode== 3236){
            installNormal(AbountUsComActivity.this, downloadUpdateApkFilePath);
        }

    }
}
