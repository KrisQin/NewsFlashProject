package com.blockadm.common.base;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.common.R;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.config.Contants;
import com.blockadm.common.dialog.ComLoadingDialog;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.StatusBarUtil;
import com.blockadm.common.utils.StringUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 *
 */

public class BaseComActivity extends AppCompatActivity implements IbaseView {


//    private NoLoginReceiver mNoLoginReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBarColor();
        ARouter.getInstance().inject(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

//        mNoLoginReceiver = new NoLoginReceiver();
//        //注册下载完成广播
//        registerReceiver(mNoLoginReceiver,
//                new IntentFilter(Contants.IntentFilter_NoLogin));

        ContextUtils.setActivity(this);
    }


//    private class NoLoginReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (StringUtils.isEquals(intent.getAction(),Contants.IntentFilter_NoLogin)) {
//
//            }
//        }
//    }

    protected void setBarColor() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.color_ffffff));
                StatusBarUtil.setImmersiveStatusBar(this, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private ComLoadingDialog mLoadingDialog;

    protected void showDefaultLoadingDialog() {

        if (isFinishing()) {
            return;
        }

        if (mLoadingDialog == null) {
            mLoadingDialog = new ComLoadingDialog.Builder(this).onCreate();
        }

        mLoadingDialog.show();


    }


    protected void dismissLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mLoadingDialog != null && mLoadingDialog.isShowing()
                            && !isFinishing()) {
                        mLoadingDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    try {
                        if (mLoadingDialog != null && mLoadingDialog.isShowing()
                                && !isFinishing()) {
                            mLoadingDialog.cancel();
                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }


    private ComLoadingDialog mLoadingMsgDialog;
    protected void showMsgLoadingDialog() {

        if (isFinishing()) {
            return;
        }

        if (mLoadingMsgDialog == null ) {
            mLoadingMsgDialog = new ComLoadingDialog.Builder(this).isShowMessage(true).onCreate();
        }

        mLoadingMsgDialog.show();

    }




    protected void dismissMsgLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mLoadingMsgDialog != null && mLoadingMsgDialog.isShowing()
                            && !isFinishing()) {
                        mLoadingMsgDialog.dismiss();
                    }
                }catch (Exception e){
                    e.printStackTrace();

                    try {
                        if (mLoadingMsgDialog != null && mLoadingMsgDialog.isShowing()
                                && !isFinishing()) {
                            mLoadingMsgDialog.cancel();
                        }

                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    protected void onPause() {
        ContextUtils.dismiss();
        super.onPause();
    }

    @Override
    protected void onStop() {
        dismissLoadingDialog();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        dismissLoadingDialog();
        super.onDestroy();


    }


    @Override
    public void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void hideLoadDialog() {

    }


}
