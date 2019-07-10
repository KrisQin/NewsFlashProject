package com.blockadm.common.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.common.R;
import com.blockadm.common.dialog.ComLoadingDialog;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.StatusBarUtil;


/**
 *
 */

public class BaseFragmentActivity extends FragmentActivity implements IbaseView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarColor();
        ARouter.getInstance().inject(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        ContextUtils.setActivity(this);
    }


    private void setBarColor() {
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
    protected void onPause() {
        ContextUtils.dismiss();
        super.onPause();
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
