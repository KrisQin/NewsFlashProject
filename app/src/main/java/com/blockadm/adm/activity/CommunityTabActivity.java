package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.adapter.MyPagerAdapter;
import com.blockadm.adm.im_module.activity.CommunityListManagerActivity;
import com.blockadm.adm.im_module.fragment.LiveChildFragment;
import com.blockadm.adm.im_module.widget.CustomViewPager;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.BannerListDto;
import com.blockadm.common.comstomview.banner.BannerView;
import com.blockadm.common.config.ComEvent;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.ComObserver;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.StringUtils;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Kris on 2019/5/30
 *
 * @Describe TODO { 社群界面 }
 */
public class CommunityTabActivity extends BaseComActivity {

    BannerView bannerView;

    private ArrayList<BannerListDto> bannerListDtos1;

    private SlidingTabLayout tabLayout;
    private CustomViewPager viewPager;
    private ArrayList<Fragment> bodyFragments;
    private ArrayList<String> tabNames;
    private ConstraintLayout mLayout_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_tab);

        initView();
    }

    protected void initView() {

        bannerView = findViewById(R.id.bannerView);
        mLayout_title = findViewById(R.id.layout_title);

        tabLayout = findViewById(R.id.community_container_tab_layout);
        viewPager = findViewById(R.id.viewPager);

        mLayout_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityTabActivity.this, SearchviewComActivity.class);
                startActivity(intent);
            }
        });

        queryBanner();
        initViewpager();
    }

    private MyPagerAdapter mAdapter;

    private void initViewpager() {

        bodyFragments = new ArrayList<>();
        tabNames = new ArrayList<>();

        LiveChildFragment myFragment1 = new LiveChildFragment();
        tabNames.add("最新");
        bodyFragments.add(myFragment1);

        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), bodyFragments, tabNames);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(bodyFragments.size());
        tabLayout.setViewPager(viewPager);
        tabLayout.onPageSelected(0);

        //        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(this
        //        .getSupportFragmentManager()) {
        //            @Override
        //            public Fragment getItem(int position) {
        //                return bodyFragments.get(position);
        //            }
        //
        //            @Override
        //            public int getCount() {
        //                return bodyFragments.size();
        //            }
        //
        //            //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
        //            @Override
        //            public CharSequence getPageTitle(int position) {
        //                return bodyFragments.get(position).getmTitle();
        //            }
        //        };
        //        viewPager.setAdapter(mAdapter);
        //        viewPager.setOffscreenPageLimit(bodyFragments.size());
        //        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。

        L.d("-------------- queryStudyTypeList  ------------  ");
    }

    private ArrayList<BannerListDto> pictrues = new ArrayList<>();

    /**
     * 查询轮播图的图片
     */
    private void queryBanner() {
        showDefaultLoadingDialog();

        CommonModel.findSysBannerList(0, 5, new ComObserver<ArrayList<BannerListDto>>() {

            @Override
            public void onSuccess(BaseResponse<ArrayList<BannerListDto>> t, String errorMsg) {
                bannerListDtos1 = t.getData();
                try {

                    if (t != null && t.getData() != null) {
                        pictrues.clear();
                        pictrues.addAll(t.getData());
                    }

                    bannerView.setPictrues(pictrues);

                    bannerViewShow();
                } catch (Exception e) {

                } finally {
                    dismissLoadingDialog();
                }
            }


        });
    }

    private void bannerViewShow() {
        bannerView.startLoop();
        bannerView.setScrollerDuration(1000);
        bannerView.setItemClick(new BannerView.OnBannerClickListener() {
            @Override
            public void onBannerItemClick(int position) {
                if (bannerListDtos1 != null) {
                    String url = bannerListDtos1.get(position).getRedirectUrl();
                    if (!TextUtils.isEmpty(url)) {
                        Intent intent = new Intent(CommunityTabActivity.this,
                                HtmlComActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra("title", bannerListDtos1.get(position).getTitle());
                        startActivity(intent);
                    }

                }

            }
        });
    }


}
