package com.blockadm.common.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.common.R;
import com.blockadm.common.dialog.ComLoadingDialog;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.StatusBarUtil;
import com.blockadm.common.utils.TUtil;


/**
 */

public class BaseActivity<P extends  BasePersenter,M extends BaseModel >extends AppCompatActivity implements IbaseView {
   public P mPersenter;
   public M mModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         setVM();
         setBarColor();
        ARouter.getInstance().inject(this);
        BaseApplication  baseApplication   = (BaseApplication) ContextUtils.getBaseApplication();
        baseApplication.addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        ContextUtils.setActivity(this);
    }


    private void setVM() {
        mPersenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof IbaseView&&mPersenter!=null){
            mPersenter.addView(this,mModel);
        }
    }

    private void setBarColor() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.color_ffffff));
                StatusBarUtil.setImmersiveStatusBar(this,true);

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

        if (mLoadingDialog == null ) {
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
                }catch (Exception e){
                    e.printStackTrace();

                    try {
                        if (mLoadingDialog != null && mLoadingDialog.isShowing()
                                && !isFinishing()) {
                            mLoadingDialog.cancel();
                        }

                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        });
    }


    private ComLoadingDialog mLoadingMsgDialog;
    protected void showMsgLoadingDialog(boolean isShowMsg) {

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
    protected void onDestroy() {
        dismissLoadingDialog();

        super.onDestroy();

        if (mPersenter!=null){
            mPersenter.destory();
        }


    }


    @Override
    protected void onPause() {
        ContextUtils.dismiss();
        super.onPause();
    }

    @Override
    public void showToast(Context context, String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void hideLoadDialog() {

    }



}
