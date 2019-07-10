package com.blockadm.common.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

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
public abstract class BaseTitleActivity extends BaseComActivity {



    private BaseTitleBar mTitleBar;
    private FrameLayout mContentLayout;
    private View mContentView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base_title_layout);

        mTitleBar = findViewById(R.id.title_container);
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


    }



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



}
