package com.blockadm.common.comstomview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


public class FullRecyclerView extends RecyclerView {



    public FullRecyclerView(Context context) {
        super(context);
    }

    public FullRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullRecyclerView(Context context, AttributeSet attrs,
                            int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
