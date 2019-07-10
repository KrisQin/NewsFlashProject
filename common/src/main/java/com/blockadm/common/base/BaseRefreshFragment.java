package com.blockadm.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.common.R;
import com.blockadm.common.dialog.ComLoadingDialog;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.T;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by hp on 2018/12/12.
 */

public abstract class BaseRefreshFragment extends Fragment implements IbaseView {

    protected SmartRefreshLayout mSmartRefreshLayout;
    protected BaseApplication mApplication;

    protected int mPageIndex = 1;
    protected int mPageSize = 10;
    protected boolean isCanLoadMore = true;
    protected boolean isRefresh = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);

        View view = inflater.inflate(getLayoutId(),null,false);

        mApplication = new BaseApplication();
        mSmartRefreshLayout = view.findViewById(getRefreshLayoutId());
        if (mSmartRefreshLayout !=null) {
            initRefreshLayout();
        }
        ContextUtils.setActivity(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        initView(rootView);
    }



    private void initRefreshLayout() {
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mSmartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));

        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                isRefresh = false;
                if (isCanLoadMore) {
                    mPageIndex++;
                    refreshData();
                } else {
                    T.showShort(mApplication, R.string.no_data_load_more);
                    mSmartRefreshLayout.finishLoadMore();

                }
            }
        });

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isCanLoadMore = true;
                isRefresh = true;
                mPageIndex = 1;
                refreshData();
            }
        });

        mSmartRefreshLayout.setDisableContentWhenRefresh(true);
        mSmartRefreshLayout.setDisableContentWhenLoading(true);
    }

    protected void finishRefresh() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    protected abstract int getRefreshLayoutId();

    protected abstract void refreshData();

    protected void backPage() {
        if (mPageIndex > 1) {
            mPageIndex--;
        }
    }

    protected void nextPage() {
        mPageIndex++;
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
