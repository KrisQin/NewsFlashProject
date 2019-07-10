package com.blockadm.adm.im_module.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 2019/5/29
 *
 * @Describe TODO {  }
 */
public class LivePagerAdapter  <T extends Fragment> extends FragmentPagerAdapter {

    private ArrayList<T> fragments;
    private ArrayList<String> tabNames;
    private List<View> mTabViewList;

    public LivePagerAdapter(FragmentManager fm, ArrayList<T> fragments, ArrayList<String> tabNames,List<View> tabViewList) {
        super(fm);
        this.fragments = fragments;
        this.tabNames  = tabNames;
        this.mTabViewList = tabViewList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public View getTabView(int position) {
        return mTabViewList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }
}
