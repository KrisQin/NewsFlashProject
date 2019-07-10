package com.blockadm.adm.im_module.widget;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.comstomview.MyTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 2019/5/29
 *
 * @Describe TODO {  }
 */
public class LiveLessonsPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> mFragmentList;
    List<String> mTabNameList;

    //ArrayList<Fragment> fragments, ArrayList<String> tabNames
    public LiveLessonsPagerAdapter(Activity activity,FragmentManager fm,  List<Fragment> list, ArrayList<String> tabNames) {
        super(fm);
        this.mFragmentList = list;
        this.mTabNameList = tabNames;

        for (int i = 0; i < list.size(); i++) {
            TextView textView = new TextView(activity);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(activity.getResources().getColor(R.color.color_97979F));
            textView.setText(tabNames.get(i));
            textView.setTextSize(16);
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            mTabViewList.add(textView);
        }
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }


    private List<View> mTabViewList = new ArrayList<View>();

    public View getTabView(int position) {
        return mTabViewList.get(position);
    }


    //重写这个方法，将设置每个Tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return  mTabNameList.get(position); //((TextView) mTabViewList.get(position)).getText();
    }
}
