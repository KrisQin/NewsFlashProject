package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blockadm.adm.Fragment.AttentionFragment1;
import com.blockadm.adm.Fragment.InformationFragment;
import com.blockadm.adm.Fragment.WalksSchFragment;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.MyPagerAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.FindSysTypeListDto;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;


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
//        fragments.add(new NewsFlashFragment());
        tabNames.add("关注");
//        tabNames.add("快讯");

        CommonModel.findSysTypeList(new MyObserver<ArrayList<FindSysTypeListDto>>() {
            @Override
            public void onSuccess(BaseResponse<ArrayList<FindSysTypeListDto>> t) {
                if (t.getData() != null && t.getData().size() > 0) {
                    for (int i = 0; i < t.getData().size(); i++) {
                        FindSysTypeListDto findSysTypeListDto = t.getData().get(i);

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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
