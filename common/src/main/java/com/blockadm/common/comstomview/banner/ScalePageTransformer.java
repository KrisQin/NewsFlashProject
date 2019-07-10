package com.blockadm.common.comstomview.banner;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Description:
 *类似3D效果
 */
public class ScalePageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
/*        float abs = Math.abs(position - 1 / 3f);
        float scale = (4 * (abs * abs));
        page.setScaleX(1 - scale);
        page.setScaleY(1 - scale);*/

        Log.i("Log_LYL:position_",position+"");
        if (position > -1 && position < 1) {
//            效果1：相对于XY轴缩放；
            page.setScaleX(1-Math.abs(position));
            page.setScaleY(1-Math.abs(position));

        }


    }
}