package com.blockadm.common.utils;

/**
 * Created by Administrator on 2019/2/11.
 */

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author xuanyouwu
 */
public class DesignViewUtils {

    /**
     * AppBarLayout 完全显示 打开状态
     *
     * @param verticalOffset
     * @return
     */
    public static boolean isAppBarLayoutOpen(int verticalOffset) {
        return verticalOffset >= 0;
    }

    /**
     * AppBarLayout 关闭或折叠状态
     *
     * @param appBarLayout
     * @param verticalOffset
     * @return
     */
    public static boolean isAppBarLayoutClose(AppBarLayout appBarLayout, int verticalOffset) {
        return appBarLayout.getTotalScrollRange() == Math.abs(verticalOffset);
    }

    /**
     * RecyclerView 滚动到底部 最后一条完全显示
     *
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }


    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager!=null){
            //屏幕中最后一个可见子项的position
            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            //当前屏幕所看到的子项个数
            int visibleItemCount = layoutManager.getChildCount();
            //当前RecyclerView的所有子项个数
            int totalItemCount = layoutManager.getItemCount();
            //RecyclerView的滑动状态
            int state = recyclerView.getScrollState();
            if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }


    /**
     * RecyclerView 滚动到顶端
     *
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToTop(RecyclerView recyclerView) {
        return recyclerView.computeVerticalScrollOffset() <= 0;
    }
}
