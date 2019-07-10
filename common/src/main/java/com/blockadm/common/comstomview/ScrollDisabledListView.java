package com.blockadm.common.comstomview;

/**
 * Created by hp on 2019/1/15.
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ListView;

public class ScrollDisabledListView extends ListView {

        public ScrollDisabledListView(Context context) {
            super(context);
        }

        public ScrollDisabledListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ScrollDisabledListView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ScrollDisabledListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }


        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }



}
