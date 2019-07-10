package com.blockadm.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Kris on 2019/5/24
 *
 * @Describe TODO {  }
 */
public class NoInterceptLinearLayout extends LinearLayout {
    public NoInterceptLinearLayout(Context context) {
        super(context);
    }

    public NoInterceptLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
