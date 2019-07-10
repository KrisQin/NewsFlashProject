package com.blockadm.adm.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.adm.Fragment.InfomartionListSearchFragment;
import com.blockadm.adm.Fragment.LearnFragment;
import com.blockadm.adm.Fragment.UserListFragment;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.TabFragmentAdapter;
import com.blockadm.adm.im_module.fragment.PersonLiveFragment;
import com.blockadm.common.base.BaseComActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hp on 2019/1/26.
 */

public class SearchviewComActivity extends BaseComActivity {

    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp)
    ViewPager vp;
    Unbinder unbinder;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.sv)
    SearchView mSearchView;
    private UserListFragment userListFragment;
    private LearnFragment mLearnFragment;
    private InfomartionListSearchFragment infomartionListSearchFragment;
    private PersonLiveFragment mPersonLiveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_searchview);


        initView();
    }

    private void initView() {
        unbinder = ButterKnife.bind(this);
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> tabNames = new ArrayList<>();
        tabNames.add("资讯");
        tabNames.add("学习");
       // tabNames.add("问答");
        tabNames.add("用户");
        tabNames.add("社群");
        userListFragment = new UserListFragment();
        mLearnFragment = new LearnFragment();
        infomartionListSearchFragment = new InfomartionListSearchFragment();
        mPersonLiveFragment = new PersonLiveFragment();
        fragments.add(infomartionListSearchFragment);
        fragments.add(mLearnFragment);
       // fragments.add(new UserListFragment());
        fragments.add(userListFragment);
        fragments.add(mPersonLiveFragment);
        setViewpageAdapter(fragments, tabNames);
        mSearchView.setVisibility(View.VISIBLE);
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setFocusable(true);
        mSearchView.setIconified(false);
        mSearchView.requestFocusFromTouch();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                        infomartionListSearchFragment.search(newText);
                        mLearnFragment.search(newText);
                        userListFragment.search(newText);
                        mPersonLiveFragment.search(newText);
                return false;
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    private int position;
    private void setViewpageAdapter(ArrayList<Fragment> fragments, ArrayList<String> tabNames) {
        TabFragmentAdapter tabFragmentAdapter  =  new TabFragmentAdapter<Fragment>(getSupportFragmentManager(),fragments,tabNames);
        vp.setAdapter(tabFragmentAdapter);
        vp.setOffscreenPageLimit(3);
        tlTab.setupWithViewPager(vp);
        tlTab.setTabsFromPagerAdapter(tabFragmentAdapter);

        TextView title = (TextView)(((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(0)).getChildAt(1));
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tlTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                vp.setCurrentItem(tab.getPosition());
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                title.setText(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                title.setText(tab.getText());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
