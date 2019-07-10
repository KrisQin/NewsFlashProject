package com.blockadm.adm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Kris on 2019/6/18
 *
 * @Describe TODO {  }
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTitles;

    public MyPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments, ArrayList<String> tabNames) {
        super(fm);
        this.mFragments = fragments;
        this.mTitles  = tabNames;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
