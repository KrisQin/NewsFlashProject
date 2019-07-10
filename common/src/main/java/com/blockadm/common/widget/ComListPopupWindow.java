package com.blockadm.common.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.blockadm.common.R;
import com.blockadm.common.adapter.PopupListAdapter;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 2019/5/17
 *
 * @Describe TODO {  }
 */
public class ComListPopupWindow extends PopupWindow {

    private Activity mActivity;
    private List<String> mList;
    private OnItemClick mItemClick;
    private PopupListAdapter mPopupListAdapter;

    public ComListPopupWindow(Activity activity, List<String> mList, OnItemClick itemClick) {
        mActivity = activity;
        this.mList = mList;
        this.mItemClick = itemClick;
        initPopuWindow();
    }

    public void adapterDataChanged() {
        if (mPopupListAdapter != null) {
            mPopupListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopuWindow() {
        // PopupWindow浮动下拉框布局
        View layout = mActivity.getLayoutInflater().inflate(R.layout.popup_window_list_layout, null);
        setContentView(layout);
        setWidth(ScreenUtils.dip2px(mActivity,74));
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        ListView listView = layout.findViewById(R.id.listview);

        mPopupListAdapter = new PopupListAdapter(mActivity,mList);
        listView.setAdapter(mPopupListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                mItemClick.itemClick(position);

                ComListPopupWindow.this.dismiss();
            }
        });

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
    public void showPopAtScreenRight(View v,int xoff,int yoff) {
        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
//        showAsDropDown(localView, xoff, yoff);
        //        showAtLocation(view, Gravity.CENTER, 0, 0);
        //设置背景颜色变暗
        //        Window window = mActivity.getWindow();
        //        // 这行代码会导致软键盘往上弹的时候,activity界面也会自动往上移动
        //        //        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //        WindowManager.LayoutParams lp     = window.getAttributes();
        //        lp.alpha = 0.5f;
        //        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        //        window.setAttributes(lp);

//        showAsDropDown(v,-getWidth()+xoff,yoff);
        showAsDropDown(v,-getWidth()+v.getWidth(),yoff);
//        showAsDropDown(v,0,yoff);
    }

    /**
     * 弹出设置
     */
    public void showSetting(View v,int xoff,int yoff) {

        int x = -v.getWidth() / 4;
        int y = yoff+getHeight()-450;
        L.d("xxx"," mPopupWindow.showSetting  x: "+x+" ; y: "+y);
        showAsDropDown(v,x,y);
    }



    public interface OnItemClick {
        void itemClick(int position);
    }

}