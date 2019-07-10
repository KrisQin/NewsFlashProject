package com.blockadm.common.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;


import android.view.ViewGroup.LayoutParams;

import com.blockadm.common.R;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.utils.T;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by Kris on 2019/5/16
 *
 * @Describe TODO {  }
 */
public abstract class BaseRefreshActivity extends BaseComActivity {

    protected int mPageNum = 1;
    protected int mPageSize = 10;
    protected boolean isCanLoadMore = true;
    protected boolean isRefresh = true;


    private BaseTitleBar mTitleBar;
    private SmartRefreshLayout smartRefreshLayout;
    private FrameLayout mContentLayout;
    private View mContentView;
    private View mLayout_empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base_refresh_layout);

        mLayout_empty = findViewById(R.id.layout_empty);
        mTitleBar = findViewById(R.id.title_container);
        smartRefreshLayout = findViewById(R.id.smart_refresh_layout);
        mContentLayout = findViewById(R.id.content_layout);

        mTitleBar.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTitleLeft();
            }
        });

        mTitleBar.setOnRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTitleRight();
            }
        });

        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                isRefresh = false;
                if (isCanLoadMore) {
                    mPageNum++;
                    refreshData();
                } else {
                    T.showShort(BaseRefreshActivity.this, R.string.no_data_load_more);
                    smartRefreshLayout.finishLoadMore();
                }
            }
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isCanLoadMore = true;
                isRefresh = true;

                mPageNum = 1;
                refreshData();
            }
        });

        smartRefreshLayout.setDisableContentWhenRefresh(true);
        smartRefreshLayout.setDisableContentWhenLoading(true);

    }

    protected void showEmptyView(boolean isShowEmptyView) {
        if (isShowEmptyView) {
            mLayout_empty.setVisibility(View.VISIBLE);
        } else {
            mLayout_empty.setVisibility(View.GONE);
        }
    }

    protected abstract void refreshData();


    protected void setTitle(String title) {
        if (mTitleBar != null) {
            mTitleBar.setTitle(title);
        }
    }

    protected void setRightText(String text) {
        if (mTitleBar != null) {
            mTitleBar.setRightText(text);
        }
    }

    protected void clickTitleLeft() {
        finish();
    }

    protected void clickTitleRight() {

    }

    /**
     * 设置容器中布局
     */

    protected void setCustomView(int layout) {
        mContentView = this.getLayoutInflater().inflate(layout, null);
        if (mContentLayout != null)
            mContentLayout.addView(mContentView, new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public <T extends View> T findContentViewById(int id) {
        return mContentView.findViewById(id);
    }

    protected void finishRefresh() {
        if (isRefresh) {
            smartRefreshLayout.finishRefresh();
        }else {
            smartRefreshLayout.finishLoadMore();
        }

    }

    protected void nextPage() {
        mPageNum++;
    }

    protected void backPage() {
        mPageNum--;
    }



}
