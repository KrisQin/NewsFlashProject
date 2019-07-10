package com.blockadm.adm.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.Fragment.AttentionFragment1;
import com.blockadm.adm.Fragment.InformationFragment;
import com.blockadm.adm.Fragment.NewsFlashFragment;
import com.blockadm.adm.Fragment.WalksSchFragment;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.MyPagerAdapter;
import com.blockadm.adm.adapter.TabFragmentAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.FindSysTypeListDto;
import com.blockadm.common.comstomview.MyTabLayout;
import com.blockadm.common.config.ComEvent;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.IndicatorLineUtil;
import com.blockadm.common.utils.L;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Kris on 2019/5/30
 *
 * @Describe TODO {  }
 */
public class InformationTabActivity extends BaseComActivity {

    ImageView ivLogo;
    RelativeLayout mainTitle;
    SlidingTabLayout tlTab;
    ViewPager viewPager;

    private ArrayList<Fragment> fragments;
    private ArrayList<String> tabNames;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_tab);

        ivLogo = findViewById(R.id.iv_logo);
        mainTitle = findViewById(R.id.main_title);
        tlTab = findViewById(R.id.tl_tab);
        viewPager = findViewById(R.id.vp);

        mainTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationTabActivity.this,
                        SearchviewComActivity.class);
                startActivity(intent);
            }
        });

        initData();
    }


    private void initData() {
        fragments = new ArrayList<>();
        tabNames = new ArrayList<>();

        fragments.add(new AttentionFragment1());
        fragments.add(new NewsFlashFragment());
        tabNames.add("关注");
        tabNames.add("快讯");

        CommonModel.findSysTypeList(new MyObserver<ArrayList<FindSysTypeListDto>>() {
            @Override
            public void onSuccess(BaseResponse<ArrayList<FindSysTypeListDto>> t) {
                if (t.getData() != null && t.getData().size() > 0) {
                    for (int i = 0; i < t.getData().size(); i++) {
                        FindSysTypeListDto findSysTypeListDto = t.getData().get(i);

                        L.d("sfsf",
                                "findSysTypeListDto.getId(): " + findSysTypeListDto.getId() + " " +
                                        "findSysTypeListDto.getTypeName(): " + findSysTypeListDto.getTypeName());
                        fragments.add(new InformationFragment(findSysTypeListDto.getId()));
                        tabNames.add(findSysTypeListDto.getTypeName());
                    }
                }

                tabNames.add("活动");
                fragments.add(new WalksSchFragment());
                setViewpageAdapter(fragments);
            }
        });

    }

    private MyPagerAdapter mAdapter;
    private void setViewpageAdapter(ArrayList<Fragment> fragments) {
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(),fragments,tabNames);
        viewPager.setAdapter(mAdapter);
        tlTab.setViewPager(viewPager);
        tlTab.onPageSelected(0);
//        TabFragmentAdapter tabFragmentAdapter =
//                new TabFragmentAdapter<Fragment>(getSupportFragmentManager(), fragments, tabNames);
//        vp.setAdapter(tabFragmentAdapter);
//        vp.setOffscreenPageLimit(0);
//        tlTab.setViewPager(vp);
//
//        TextView title =
//                (TextView) (((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(0)).getChildAt(1));
//        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//        tlTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                vp.setCurrentItem(tab.getPosition());
//                TextView title =
//                        (TextView) (((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
//                title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//                title.setText(tab.getText());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                vp.setCurrentItem(tab.getPosition());
//                TextView title =
//                        (TextView) (((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
//                title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                title.setText(tab.getText());
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //    // 2次点击退出
    //    private long exitTime = 0;
    //
    //    /**
    //     * 按键监听
    //     */
    //    @Override
    //    public boolean onKeyDown(int keyCode, KeyEvent event) {
    //        if (keyCode == KeyEvent.KEYCODE_BACK
    //                && event.getAction() == KeyEvent.ACTION_DOWN) {
    //
    //            if ((System.currentTimeMillis() - exitTime) > 2000) {
    //                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
    //                exitTime = System.currentTimeMillis();
    //            } else {
    //                /**
    //                 * 参数为false——代表只有当前activity是task根，指应用启动的第一个activity时，才有效;
    //                 参数为true——则忽略这个限制，任何activity都可以有效。
    //                 */
    //                EventBus.getDefault().post(new ComEvent("finish_app"));
    //                moveTaskToBack(true);
    //            }
    //            return true;
    //        }
    //        return super.onKeyDown(keyCode, event);
    //    }
}
