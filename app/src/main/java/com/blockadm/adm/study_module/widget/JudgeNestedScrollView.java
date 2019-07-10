package com.blockadm.adm.study_module.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @Created SiberiaDante
 * @Describe：
 * @CreateTime: 2017/12/17
 * @UpDateTime:
 * @Email: 2654828081@qq.com
 * @GitHub: https://github.com/SiberiaDante
 */

public class JudgeNestedScrollView extends NestedScrollView {
    private boolean isNeedScroll = true;
    private float xDistance, yDistance, xLast, yLast;
    private int scaledTouchSlop;

    public JudgeNestedScrollView(Context context) {
        super(context, null);
    }

    public JudgeNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public JudgeNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("xxx", "111111111111111 ---------------super.dispatchTouchEvent(ev) ：" +super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("xxx", "33333333333333333 ----------------------super.onTouchEvent(ev) ："+super.onTouchEvent(ev) );
        return super.onTouchEvent(ev);
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                boolean isInterceptTouchEvent =  !(xDistance >= yDistance || yDistance < scaledTouchSlop) && isNeedScroll;
                Log.d("xxx", "2222222222 ---- xDistance ：" + xDistance + "---yDistance:" + yDistance
                        +" ; scaledTouchSlop: "+scaledTouchSlop+" ; isNeedScroll: "+isNeedScroll+" ; isInterceptTouchEvent: "+isInterceptTouchEvent);


                return isInterceptTouchEvent;


        }
        Log.d("xxx", "2222222222222222 ----------------------super.onInterceptTouchEvent(ev) ："+super.onInterceptTouchEvent(ev) );
        return super.onInterceptTouchEvent(ev);
    }

    /*
    该方法用来处理NestedScrollView是否拦截滑动事件
     */
    public void setNeedScroll(boolean isNeedScroll) {
        this.isNeedScroll = isNeedScroll;
    }
}
