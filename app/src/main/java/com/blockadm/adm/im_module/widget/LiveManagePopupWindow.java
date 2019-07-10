package com.blockadm.adm.im_module.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.blockadm.adm.im_module.adapter.SpeakerManageAdapter;
import com.blockadm.common.R;
import com.blockadm.common.bean.StudyTypeInfo;
import com.blockadm.common.bean.live.responseBean.LiveManagerInfo;
import com.blockadm.common.utils.ScreenUtils;

import java.util.List;

/**
 * Created by Kris on 2019/5/17
 *
 * @Describe TODO { 打赏  选择主讲人 }
 */
public class LiveManagePopupWindow extends PopupWindow {

    private Activity mActivity;
    private List<LiveManagerInfo> mList;
    private OnItemClick mItemClick;
    private View mLayout;

    public LiveManagePopupWindow(Activity activity, List<LiveManagerInfo> mList, OnItemClick itemClick) {
        mActivity = activity;
        this.mList = mList;
        this.mItemClick = itemClick;
        initPopuWindow();
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopuWindow() {
        // PopupWindow浮动下拉框布局
        mLayout = mActivity.getLayoutInflater().inflate(R.layout.lesson_type_layout, null);
        setContentView(mLayout);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        int bottomStatusHeight = ScreenUtils.getBottomStatusHeight(mActivity);

        ListView listView = mLayout.findViewById(R.id.listview);
        View bottom_view = mLayout.findViewById(R.id.bottom_view);

        if (bottomStatusHeight == 0) {
            bottom_view.setVisibility(View.GONE);
        }else {
            bottom_view.setVisibility(View.VISIBLE);
        }


        SpeakerManageAdapter mPopupListAdapter = new SpeakerManageAdapter(mActivity,mList);
        listView.setAdapter(mPopupListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                mItemClick.itemClick(position);

                LiveManagePopupWindow.this.dismiss();
            }
        });

        setOnDismissListener(new poponDismissListener() );
        setAnimationStyle(R.style.MyPopupWindow_anim_style);
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效

    }

    /**
     popupWindow.showAsDropDown(v,-10,14);
     发现水平偏移没效果
     弹出后紧贴在屏幕右边

     查阅资料发现 showAsDropDown 的对齐方式是v控件的左下角与popupWindow的控件左上角对齐

     而popupWindow不会超出屏幕，所以显示效果是紧贴右边框。

     小幅设置偏移量真实位置还是超出边框，窗口依旧会紧贴右边框，并不会看出有什么效果。
     */

    /**
     * 弹框在屏幕右边
     */
    public void showPop() {
        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量

        showAtLocation(mLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        mActivity.getWindow().setAttributes(lp);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }


    public interface OnItemClick {
        void itemClick(int position);
    }

}