package com.blockadm.common.comstomview;

/**
 * Created by hp on 2019/4/1.
 */


import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by Jack on 2017/7/12.
 */

public class MySwipeRefreshLayout extends SwipeRefreshLayout{
    //实际需要滑动的child view
    private View mScrollUpChild;

    public MySwipeRefreshLayout(Context context) {
        super(context);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollUpChild(View view) {
        mScrollUpChild = view;
    }


    @Override
    public boolean canChildScrollUp() {
        if (mScrollUpChild != null) {
            if (android.os.Build.VERSION.SDK_INT < 14) {
                if (mScrollUpChild instanceof AbsListView) {
                    final AbsListView absListView = (AbsListView) mScrollUpChild;
                    return absListView.getChildCount() > 0
                            && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                            .getTop() < absListView.getPaddingTop());
                } else {
                    return ViewCompat.canScrollVertically(mScrollUpChild, -1) || mScrollUpChild.getScrollY() > 0;
                }
            } else {
                return ViewCompat.canScrollVertically(mScrollUpChild, -1);
            }
        }
        return super.canChildScrollUp();
    }


}

