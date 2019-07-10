//package com.blockadm.adm.downloadapk;
//
//import android.app.Activity;
//import android.app.Instrumentation;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.icu.util.VersionInfo;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//
//
//import com.blockadm.adm.R;
//import com.blockadm.adm.activity.BlockMainActivity;
//import com.blockadm.adm.dialog.MyComstomDialogView;
//import com.blockadm.adm.model.CommonModel;
//import com.blockadm.common.bean.UpdataVersionDto;
//import com.blockadm.common.config.AppConfig;
//import com.blockadm.common.config.ComConfig;
//import com.blockadm.common.http.BaseResponse;
//import com.blockadm.common.http.MyObserver;
//import com.blockadm.common.utils.FileUtils;
//import com.blockadm.common.utils.L;
//import com.blockadm.common.utils.ListUtils;
//import com.blockadm.common.utils.OSUtils;
//import com.blockadm.common.utils.PreferencesUtils;
//import com.blockadm.common.utils.SystemUtils;
//import com.blockadm.common.utils.T;
//import com.blockadm.common.widget.CommonDialog;
//
//import java.io.File;
//import java.util.List;
//
//
///**
// * 描述：
// * apk更新逻辑类------ 只处理更新逻辑，其他禁止
// *
// * Author：zl
// * Time: 2018/5/18 15:08
// * organization：捷顺科技
// */
//public class UpdateApkLogic {
//
//    public interface UpdateApkLogicCallBack extends LoadComCallBack{
//
//
//        void updateProgress(int progress, String downloadPercent);
//
//        void noUpdateAction();
//    }
//
//    private UpdateApkLogicCallBack callBack;
//
//    private Activity context;
//
//    private Handler handler;
//
//    private CallbackBundle<Bundle> updateCallback;
//    private CallbackBundle<ServiceResponseData> xmppCallBack;
//
//
//    public UpdateApkLogic(final Activity activity, UpdateApkLogicCallBack updateApkLogicCallBack){
//        this.context = activity;
//        this.callBack = updateApkLogicCallBack;
//        handler = new Handler();
//
//        updateCallback = new CallbackBundle<Bundle>() {
//
//            @Override
//            public void callback(final Bundle bundle) {
//
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Message message = new Message();
//                        message.setData(bundle);
//                        message.what = bundle.getInt("downloadStatus");
//
//
//                        innerHandlerDo(message);
//                    }
//                });
//            }
//        };
//
//        xmppCallBack = new CallbackBundle<ServiceResponseData>() {
//            @Override
//            public void callback(final ServiceResponseData data) {
//
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(callBack != null){
//                            callBack.dismissLoadingDialog();
//                        }
//
//                        if (data.getResultCode() == 0) {
//                            List<VersionInfo> versionInfoList = LoadingUpdate.receiveNewVersion(data.getDataItems());
//                            // 获取信息
//                            VersionInfo versionInfo = ListUtils.isEmpty(versionInfoList) ? null : versionInfoList.get(0);
//                            if (versionInfo != null) {
//
//                                // 保存信息
//                                LoadingUpdate.saveVersionInfo(context, versionInfo);
//
//                                checkVersion();
//
//                                initVersionData();
//
//                            }
//                        }
//                    }
//                });
//
//            }
//        };
//
//    }
//
//    private CommonDialog.Builder iSContuinBuilder;
//
//    /**
//     * 4g下载提示
//     */
//    public void initLoadingCommonDialog() {
//        // 新版本开始下载
//        if (NetUtil.getNetworkState(context) == NetUtil.NETWORN_MOBILE) {
//            iSContuinBuilder = new CommonDialog.Builder(context);
//
//            iSContuinBuilder.setTitle("温馨提示")
//                    .setMessage("当前网络为2G/3G状态，是否继续下载?")
//                    .setPositiveButton(R.string.btn_sure,
//                            new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int arg1) {
//                                    //去下载
//                                    startDownloadAndRefreshUI();
//                                    dialog.dismiss();
//                                }
//                            })
//                    .setNegativeButton(R.string.btn_canel,
//                            new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog,
//                                                    int arg1) {
//                                    dialog.dismiss();
//                                }
//                            }).create().show();
//        } else {
//            if (NetUtil.getNetworkState(context) == NetUtil.NETWORN_WIFI) {
//                //去下载
//                startDownloadAndRefreshUI();
//            }
//        }
//
//    }
//
//    /**
//     * 初始化版本下载需要的信息
//     */
//    private void initVersionData() {
//
//        String downUrl = PreferencesUtils.getAppString(context,
//                PreferenceConstants.VERSION_DOWNLOAD_URL);
//        DownloadFileUtils.getInstance().initBaseData(context, downUrl, getFilePath(),
//                getFileName(), 1, updateCallback);
//    }
//
//    private String getFilePath(){
//        return  AppConfig.getInstance().getFileCachePath();
//    }
//
//    private String getFileName(){
//        // 获取传值
//        String versionName = PreferencesUtils.getAppString(context,
//                PreferenceConstants.VERSION_NO);
//        return "jslife_" + versionName + ".apk";
//    }
//
//    /**
//     * 获取安装文件
//     * @return
//     */
//    private File getFileByName(){
//        return FileUtils.getFileFromPath(getFilePath() + getFileName());
//    }
//
//    /**
//     * 是否是点击检测
//     */
//    private boolean isClickAction = false;
//
//    /**
//     * 查询版本号
//     */
//    public void queryVersion(Activity activity,boolean isClickAction) {
//        this.isClickAction = isClickAction;
//        if(callBack != null){
//            callBack.showDefaultLoadingDialog();
//        }
////        //先清除
////        CallbackListener.getInstance().removeRequestMap(xmppCallBack);
////        Log.i("http","----> 开始检测版本");
////
////        // JSCSP_VERSION_GETNEWVERSION
////        XMPPRequest.addToRequestQueue(context, BusinessManage.queryVersion(
////                AppConstants.MOBILE_MODEL_ANDROID, BusinessConstants.APP_TYPE_JSLIFE), xmppCallBack);
//
//        updateApk(activity);
//    }
//
//    private void updateApk(final Activity activity) {
//        {
//            String code = OSUtils.getAppVersionCode(activity);
//
//            CommonModel.getAppUpdateData(code, 0, new MyObserver<UpdataVersionDto>() {
//                @Override
//                public void onSuccess(final BaseResponse<UpdataVersionDto> t) {
//                    if (t.getCode() == 0) {
//                        if (!t.getData().getUpdateState().equals("0")) {
//                            final MyComstomDialogView myComstomDialogView =
//                                    new MyComstomDialogView(activity);
//                            myComstomDialogView.setTvTitle("有新版本更新", View.VISIBLE).setRootBg(R.mipmap.boxbg2)
//                                    .setChildMsg("", View.GONE)
//                                    .setChildMsg2("", View.GONE)
//                                    .setConcelMsg("", View.GONE)
//                                    .setConfirmMsg("现在更新", View.VISIBLE)
//                                    .setConcelMsg("暂不更新", View.VISIBLE)
//                                    .setConfirmSize(6)
//                                    .setCancelLisenner(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            myComstomDialogView.dismiss();
//                                        }
//                                    }).setConfirmLisenner(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    downloadBySelf(activity,
//                                            t.getData().getUpdateUrl(),
//                                            "aidemei");
//                                    if (t.getData().getUpdateState().equals("2")) {
//                                        new Thread() {
//                                            public void run() {
//                                                try {
//                                                    Instrumentation inst = new Instrumentation();
//                                                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//                                                } catch (Exception e) {
//                                                    Log.e("Exception when onBack", e.toString());
//                                                }
//                                            }
//                                        }.start();
//                                    }
//                                    myComstomDialogView.dismiss();
//                                }
//                            });
//                            if (t.getData().getUpdateState().equals("2")) {
//                                myComstomDialogView.setConcelButtonEable(false);
//                            }
//                            myComstomDialogView.show();
//                        } else {
//                            //Toast.makeText(MainComActivity.this,t.getData().getUpdateMsg(),Toast
//                            // .LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                }
//
//
//            });
//        }
//    }
//
//    private CommonDialog.Builder showUpdateDialog;
//
//    private void checkVersion() {
//        ComConfig.putNextRemindUpdate(context,false);
//
//        if (context == null || context.isDestroyed() || context.isFinishing()
//                || !SystemUtils.isForeground(context,context.getClass().getName())){
//            return;
//        }
//
//        if(showUpdateDialog != null && showUpdateDialog.create().isShowing()){
//            showUpdateDialog.create().dismiss();
//        }
//
//
//        final int localVersionNo = AppConfig.getInstance().getVersionNo();
//        final int versionNo = PreferencesUtils.getAppInt(context,
//                PreferenceConstants.VERSION_CODE, localVersionNo);
//        final boolean isForced = PreferencesUtils.getAppBoolean(context,
//                PreferenceConstants.IS_FORCED);
//        final int minUpdateVersionNo = PreferencesUtils.getAppInt(
//                context, PreferenceConstants.MIN_UPDATE_VERSION_NO);
//        final String versionInfo = PreferencesUtils.getAppString(context,
//                PreferenceConstants.VERSION_INFO);
//
//        try {
//
//            final int updateVersionNo = minUpdateVersionNo;
//
//            L.d("XMPP", "checkVersion！=========== 是否强制更新 = "+isForced
//                    +" ; 更新的版本 versionNo= "+versionNo+" ; 当前本地版本 localVersionNo= "+ localVersionNo
//                    + "; 是否需要去更新？ == "+(versionNo >= localVersionNo)
//                    +"; updateVersionNo ="+updateVersionNo);
//
//
//            // 与本地版本号对比，如果是大于本地版本号，则提示更新
//            if (versionNo > localVersionNo) {
//                setIsHaveNewVersion(context,true);
//
//                if(isClickAction){
//                    //如果已经下载中，则直接去继续下载
//                    //如果下载暂停，则继续下载
//                    if(DownloadFileUtils.getInstance().getDownLoadStatus() == DownloadFileUtils.STATUS_IS_DOWNLOADING
//                            || DownloadFileUtils.getInstance().getDownLoadStatus() == DownloadFileUtils.STATUS_PAUSE){
//                        startDownloadAndRefreshUI();
//                        T.showShort(context,"下载中");
//                        return;
//                    }
//
//                    //如果下载成功，则直接安装
//                    else if(DownloadFileUtils.getInstance().getDownLoadStatus() == DownloadFileUtils.STATUS_COMPLETE){
//                        if(callBack != null){
//                            callBack.updateProgress(100,"100");
//                        }
//                        showUpdateFinishDialog();
//                        return;
//                    }
//                }
//
//
//                showUpdateDialog = new CommonDialog.Builder(
//                        context);
//                showUpdateDialog.setMessage(versionInfo)
//                        .setPositiveButton("立即更新",
//                                new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog,
//                                                        int arg1) {
//
//                                        initLoadingCommonDialog();
//
//                                        dialog.dismiss();
//                                    }
//                                });
//
//
//                if(updateVersionNo >= localVersionNo && isForced) {
//                    showUpdateDialog.setCancelable(false);
//                    showUpdateDialog.setNegativeButton("退出应用",
//                            new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog,
//                                                    int arg1) {
//                                    MobclickAgent.onKillProcess(context);
//                                    System.exit(0);
//                                }
//                            }).create().show();
//
//                } else {
//                    showUpdateDialog.setNegativeButton("下次再说",
//                            new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog,
//                                                    int arg1) {
//
//                                    AppDefualtConfig.putNextRemindUpdate(context,true);
//
//                                    dialog.dismiss();
//                                }
//                            }).create().show();
//                }
//            }else {
//                setIsHaveNewVersion(context,false);
//                if(callBack != null){
//                    callBack.noUpdateAction();
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            setIsHaveNewVersion(context,false);
//        }
//
//    }
//
//
//    /**
//     * 开始下载
//     */
//    private void startDownloadAndRefreshUI() {
//        if (DownloadFileUtils.getInstance().getDownLoadStatus() == DownloadFileUtils.STATUS_UNDOWNLOAD) {
//
//        }
//        DownloadFileUtils.getInstance().startDownLoad();
//    }
//
//    private void innerHandlerDo(Message msg) {
//
//        LogOpen.i("下载进度----> " + msg.what);
//
//        if (msg.what == DownloadFileUtils.STATUS_IS_DOWNLOADING) {
//            // 更新下载进度
//
//            Bundle bundle = msg.getData();
//            float progress = bundle.getFloat("progress");
//            String downloadPercent = bundle.getString("percent");
//
//            LogOpen.i("下载进度---progress ---> " + progress);
//
//
//
//            if(callBack != null){
//                callBack.updateProgress((int)progress,downloadPercent);
//            }
//
//
//        } else if (msg.what == DownloadFileUtils.STATUS_COMPLETE) {
//            // 下载完成
//            if(callBack != null){
//                callBack.updateProgress(100,"100");
//            }
//            // 结束下载
//            showUpdateFinishDialog();
//        }
//
//        else if (msg.what == DownloadFileUtils.STATUS_FAIL) {
//            // 下载失败
//            T.showShort(context, "下载失败，请稍后再试");
//        }
//
//        //暂停
//        else if (msg.what == DownloadFileUtils.STATUS_PAUSE) {
//            stopDownloadAndRefreshUI();
//        }
//
//    }
//
//    private CommonDialog.Builder isInstallBuilder;
//
//    /**
//     * 下载完成
//     */
//    private void showUpdateFinishDialog(){
//        if (context == null || context.isDestroyed() || context.isFinishing()
//                || !SystemUtils.isForeground(context,context.getClass().getName())){
//            return;
//        }
//
//
//        if(isInstallBuilder != null && isInstallBuilder.create().isShowing()){
//            isInstallBuilder.create().dismiss();
//        }
//
//
//        final boolean mIsForcedUpdate = PreferencesUtils.getAppBoolean(context,PreferenceConstants.IS_FORCED);
//
//        isInstallBuilder  = new JSCommonDialog.Builder(context);
//        isInstallBuilder.setTitle("应用安装");
//        isInstallBuilder.setMessage("确定安装新版本捷生活吗？");
//        isInstallBuilder.setPositiveButton("安装",
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int arg1) {
//                        installApk();
//                        // 如果是强制更新，对话框不消失
//                        if (!mIsForcedUpdate)
//                            dialog.dismiss();
//
//                    }
//                });
//        if (mIsForcedUpdate) {
//            isInstallBuilder.setCancelable(false);
//            isInstallBuilder.setNegativeButton("退出应用",
//                    new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int arg1) {
//                            MobclickAgent.onKillProcess(context);
//                            System.exit(0);
//                        }
//                    });
//
//        } else {
//            isInstallBuilder.setNegativeButton(R.string.cancel,
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int arg1) {
//                        dialog.dismiss();
//                    }
//                });
//        }
//        isInstallBuilder.create().show();
//    }
//
//
//    /**
//     * 停止下载并刷新UI
//     */
//    public void stopDownloadAndRefreshUI() {
//        if (DownloadFileUtils.getInstance().getDownLoadStatus() == DownloadFileUtils.STATUS_IS_DOWNLOADING) {
//            DownloadFileUtils.getInstance().stopDownLoad();
//        }
//    }
//
//    /**
//     * 安装
//     */
//    public void installApk() {
//        File updateFile = getFileByName();
//
//        if (FileUtils.isFileExist(updateFile)) {
//            try {
//                Uri uri = Uri.fromFile(updateFile);
//
//                Intent installIntent = new Intent(Intent.ACTION_VIEW);
//                installIntent.setDataAndType(uri,
//                        "application/vnd.android.package-archive");
//                installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(installIntent);
//
//                // 版本更新重新进入app时,需要从引导页进入app
//                PreferencesUtils.putAppInt(context, PreferenceConstants.START_COUNT, 0);
//            } catch (Exception e) {
//                T.showShort(context, "找不到相应的安装程序");
//                e.printStackTrace();
//            }
//
//        } else {
//            T.showShort(context, "安装文件不存在，请重新下载");
//            if(callBack != null){
//                callBack.updateProgress(0,"0");
//            }
//        }
//    }
//
//    public static boolean isHaveNewVersion(Context context){
//        return PreferencesUtils.getAppBoolean(context, PreferenceConstants.IS_HAVA_NEW_VERSION);
//    }
//
//    public static void setIsHaveNewVersion(Context context, boolean isHaveNewVersion){
//        PreferencesUtils.putAppBoolean(context, PreferenceConstants.IS_HAVA_NEW_VERSION,isHaveNewVersion);
//    }
//
//    /**
//     * 销毁
//     */
//    public void onDestory(){
//        xmppCallBack = null;
//        handler.removeCallbacksAndMessages(null);
//        handler = null;
//        callBack = null;
//        updateCallback = null;
//    }
//
//}
