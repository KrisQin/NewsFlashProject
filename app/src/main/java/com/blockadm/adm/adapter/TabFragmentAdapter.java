package com.blockadm.adm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2018/12/13.
 */
public class TabFragmentAdapter <T extends Fragment> extends FragmentPagerAdapter {

    private ArrayList<T> fragments;
    private ArrayList<String> tabNames;

    public TabFragmentAdapter(FragmentManager fm, ArrayList<T> fragments, ArrayList<String> tabNames) {
        super(fm);
        this.fragments = fragments;
        this.tabNames  = tabNames;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private List<View> mTabViewList = new ArrayList<View>();

    public View getTabView(int position) {
        return mTabViewList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }
}
