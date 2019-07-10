package com.blockadm.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.common.dialog.ComLoadingDialog;
import com.blockadm.common.utils.ContextUtils;

/**
 * Created by hp on 2018/12/12.
 */

public abstract class BaseComFragment extends Fragment implements IbaseView {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        ContextUtils.setActivity(getActivity());
        return inflater.inflate(getLayoutId(),null,false);
    }


    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        initView(rootView);
    }

    protected abstract void initView(View view);

    public abstract int  getLayoutId() ;


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    private ComLoadingDialog mLoadingDialog;

    protected void showDefaultLoadingDialog() {

        Activity activity = getActivity();
        if (activity != null) {
            if (activity.isFinishing()) {
                return;
            }

            if (mLoadingDialog == null ) {
                mLoadingDialog = new ComLoadingDialog.Builder(activity).onCreate();
            }

            if (mLoadingDialog != null) {
                mLoadingDialog.show();
            }
        }

    }


    protected void dismissLoadingDialog() {
        final Activity activity = getActivity();

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mLoadingDialog != null && mLoadingDialog.isShowing()
                                && !activity.isFinishing()) {
                            mLoadingDialog.dismiss();
                        }
                    }catch (Exception e){
                        e.printStackTrace();

                        try {
                            if (mLoadingDialog != null && mLoadingDialog.isShowing()
                                    && !activity.isFinishing()) {
                                mLoadingDialog.cancel();
                            }

                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                }
            });
        }


    }




    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showToast(Context context, String msg) {

    }

    @Override
    public void hideLoadDialog() {

    }


}
