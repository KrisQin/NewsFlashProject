package com.blockadm.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Kris on 2019/5/24
 *
 * @Describe TODO {  }
 */
public class NoInterceptRecycleView extends RecyclerView {
    public NoInterceptRecycleView(Context context) {
        super(context);
    }

    public NoInterceptRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }
}
