package com.blockadm.adm.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.adm.Fragment.BuyHistoryFragment;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.TabFragmentAdapter;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.MyTabLayout;
import com.blockadm.common.comstomview.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/3/15.
 */

public class BuyHistoryComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.tl_tab)
    MyTabLayout tlTab;
    @BindView(R.id.vp)
    NoScrollViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_buy_history);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> tabNames = new ArrayList<>();


        fragments.add(new BuyHistoryFragment(0));
//        fragments.add(new BuyHistoryFragment(1));
        tabNames.add("独家专栏");
//        tabNames.add("精品课程");
        setViewpageAdapter(fragments, tabNames);
    }

    private void setViewpageAdapter(ArrayList<Fragment> fragments, ArrayList<String> tabNames) {
        TabFragmentAdapter tabFragmentAdapter  =  new TabFragmentAdapter<Fragment>(getSupportFragmentManager(),fragments,tabNames);
        vp.setAdapter(tabFragmentAdapter);
        vp.setOffscreenPageLimit(0);
        tlTab.setupWithViewPager(vp);
        tlTab.setTabsFromPagerAdapter(tabFragmentAdapter);

        TextView title = (TextView)(((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(0)).getChildAt(1));
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tlTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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

}
