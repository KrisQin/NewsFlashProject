package com.blockadm.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.common.R;
import com.blockadm.common.dialog.ComLoadingDialog;
import com.blockadm.common.utils.ContextUtils;

/**
 * Created by hp on 2018/12/12.
 */

public abstract class ComBaseFragment extends Fragment implements IbaseView {

    protected FrameLayout flContentBase;
    protected View mErrorView;
    protected View mEmptyView;
    protected View mLoadingView;

    protected View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);

        View view = initBaseView(inflater, container, savedInstanceState);
        mContentView = view;

        flContentBase.addView(inflater.inflate(getLayoutId(), null), new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        ContextUtils.setActivity(getActivity());
        initView(savedInstanceState);
        initBaseData(savedInstanceState);

        initData(savedInstanceState);

        return view;
    }

    protected View findViewById(int id) {
       return mContentView.findViewById(id);
    }

    protected View initBaseView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, null);

        flContentBase = (FrameLayout) view.findViewById(R.id.fl_content_base);
        mErrorView = view.findViewById(R.id.layout_error);
        mEmptyView = view.findViewById(R.id.layout_empty);
        mLoadingView = view.findViewById(R.id.layout_loading);

        showLoadingView();

        return view;
    }



    protected void initBaseData(Bundle savedInstanceState) {

        initErrorAndEmptyView();

    }

    protected void showEmptyView() {
        if (mEmptyView != null)
            mEmptyView.setVisibility(View.VISIBLE);
        if (mErrorView != null)
            mErrorView.setVisibility(View.GONE);
        if (flContentBase != null)
            flContentBase.setVisibility(View.GONE);
        if (mLoadingView != null)
            mLoadingView.setVisibility(View.GONE);
    }

    protected void showErrorView() {
        if (mErrorView != null)
            mErrorView.setVisibility(View.VISIBLE);
        if (mEmptyView != null)
            mEmptyView.setVisibility(View.GONE);
        if (flContentBase != null)
            flContentBase.setVisibility(View.GONE);
        if (mLoadingView != null)
            mLoadingView.setVisibility(View.GONE);    }

    protected void showContentView() {
        if (flContentBase != null)
            flContentBase.setVisibility(View.VISIBLE);
        if (mEmptyView != null)
            mEmptyView.setVisibility(View.GONE);
        if (mErrorView != null)
            mErrorView.setVisibility(View.GONE);
        if (mLoadingView != null)
            mLoadingView.setVisibility(View.GONE);
    }

    protected void showLoadingView() {
        if (mLoadingView != null)
            mLoadingView.setVisibility(View.VISIBLE);
        if (mEmptyView != null)
            mEmptyView.setVisibility(View.GONE);
        if (mErrorView != null)
            mErrorView.setVisibility(View.GONE);
        if (flContentBase != null)
            flContentBase.setVisibility(View.GONE);
    }


    protected void initErrorAndEmptyView() {

        //        try {
        //
        //            if (isOpenErrorView) {
        //
        //                mErrorView = findContentViewById(R.id.layout_error);
        //                TextView tvError     = (TextView) findContentViewById(R.id.tv_error);
        //                String   errorPrompt = "网络出小差了,请点击屏幕重试";
        //                tvError.setText(errorPrompt);
        //                mErrorView.setOnClickListener(this);
        //            }
        //            if (isOpenEmptyView) {
        //                mEmptyView = findContentViewById(R.id.layout_empty);
        //                tv_empty = (TextView) findContentViewById(R.id.tv_empty);
        //                emptyPrompt = "暂没有任何信息";//prompt + ",点击刷新"
        //                //				tv_empty.setText(emptyPrompt);
        //                mEmptyView.setOnClickListener(this);
        //            }
        //
        //
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }

    }


    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData(Bundle savedInstanceState);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissLoadingDialog();
    }


    private ComLoadingDialog mLoadingDialog;

    protected void showDefaultLoadingDialog() {

        Activity activity = getActivity();
        if (activity != null) {
            if (activity.isFinishing()) {
                return;
            }

            if (mLoadingDialog == null) {
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
                    } catch (Exception e) {
                        e.printStackTrace();

                        try {
                            if (mLoadingDialog != null && mLoadingDialog.isShowing()
                                    && !activity.isFinishing()) {
                                mLoadingDialog.cancel();
                            }

                        } catch (Exception e1) {
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
