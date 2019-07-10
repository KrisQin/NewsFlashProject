package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blockadm.adm.Fragment.StudyColumnTestFragment;
import com.blockadm.adm.Fragment.StudyXMLYFragment;
import com.blockadm.adm.MainApp;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.MyPagerAdapter;
import com.blockadm.adm.im_module.widget.CustomViewPager;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.AllListDto;
import com.blockadm.common.bean.BannerListDto;
import com.blockadm.common.bean.RecordsBean;
import com.blockadm.common.bean.StudyTypeInfo;
import com.blockadm.common.comstomview.banner.BannerView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.ComObserver;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.ListUtils;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.widget.StudyTopView;
import com.flyco.tablayout.SlidingTabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Kris on 2019/5/30
 *
 * @Describe TODO { 学习界面 }
 */
public class StudyTabTestActivity extends BaseComActivity {

    BannerView bannerView;

    private ArrayList<BannerListDto> bannerListDtos1;

    private SlidingTabLayout tabLayout;
    private LinearLayout layout_middle;
    private CustomViewPager viewPager;
    private ConstraintLayout mainTitle;
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTitleList;
    private LinearLayout mLayout_title;
    public SmartRefreshLayout refreshLayout;

    public static final String Limit_Time_Free_Title = "限时免费";
    public static final String Column_Select_Title = "精选专栏";
    public static final String Xmly_Select_Title = "喜马拉雅";
    private ImageView mIv_sound_status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_tab_test);

        initView();

        showDefaultLoadingDialog();
        CommonModel.findAllList(observerFindAll);
    }

    protected void initView() {

        refreshLayout = findViewById(R.id.refreshLayout);
        bannerView = findViewById(R.id.bannerView);
        mLayout_title = findViewById(R.id.layout_title);
        layout_middle = findViewById(R.id.layout_middle);
        mainTitle = findViewById(R.id.main_title);
        mIv_sound_status = findViewById(R.id.iv_sound_status);

        tabLayout = findViewById(R.id.community_container_tab_layout);
        viewPager = findViewById(R.id.viewPager);

        mainTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyTabTestActivity.this, SearchviewComActivity.class);
                startActivity(intent);
            }
        });

        mIv_sound_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isSeeStatus = MainApp.getIsSeeStatus();
                RecordsBean recordsBean = MainApp.getRecordsBean();
                if (recordsBean != null) {
                    Intent intent = new Intent(StudyTabTestActivity.this, AudioContentComActivity.class);
                    intent.putExtra("isSeeStatus",isSeeStatus);
                    intent.putExtra("recordsBean",recordsBean);
                    startActivity(intent);
                }
            }
        });


        queryBanner();
        initViewpager();

        refreshLayoutInit();
    }

    private int viewPagerIndex = 0;

    private void refreshLayoutInit() {

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                StudyColumnTestFragment fragment =
                        (StudyColumnTestFragment) mFragments.get(viewPagerIndex);
                fragment.loadMore();
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (viewPagerIndex != 2) {
                    StudyColumnTestFragment fragment =
                            (StudyColumnTestFragment) mFragments.get(viewPagerIndex);
                    fragment.refreshData();
                }

                CommonModel.findAllList(observerFindAll);
            }
        });
    }

    MyObserver<ArrayList<AllListDto>> observerFindAll = new MyObserver<ArrayList<AllListDto>>() {
        @Override
        public void onSuccess(BaseResponse<ArrayList<AllListDto>> t) {

            try {
                if (t.getCode() == 0) {
                    showTopView(t.getData());
                }

            }catch (Exception e) {

            }finally {
                dismissLoadingDialog();
                refreshFinish();
            }


        }
    };

    public void showTopView(ArrayList<AllListDto> list) {
        if(ListUtils.isEmpty(list)) {
            return;
        }
        StudyTopView studyTopView = new StudyTopView(this, list, layout_middle);
        studyTopView.notifyDataSetChanged();

        studyTopView.setOnItemClickListener(new StudyTopView.ClickItemListener() {
            @Override
            public void callback(AllListDto.PageListBean.RecordsBean bean) {
                //                T.showShort(StudyTabTestActivity.this,bean.getTitle());
                if (bean != null) {
                    Intent intent = new Intent(StudyTabTestActivity.this,
                            ColumnOneDetailComActivity.class);
                    intent.putExtra("id", bean.getId());
                    StudyTabTestActivity.this.startActivity(intent);
                }

            }
        });
    }

    public void refreshFinish() {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
        }

    }


    private ArrayList<BannerListDto> pictrues = new ArrayList<>();

    /**
     * 查询轮播图的图片
     */
    private void queryBanner() {

        CommonModel.findSysBannerList(0, 2, new ComObserver<ArrayList<BannerListDto>>() {

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
                        Intent intent = new Intent(StudyTabTestActivity.this,
                                HtmlComActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra("title", bannerListDtos1.get(position).getTitle());
                        startActivity(intent);
                    }

                }

            }
        });
    }


    private MyPagerAdapter mAdapter;

    private void initViewpager() {

        mFragments = new ArrayList<>();
        mTitleList = new ArrayList<>();

        mFragments.add(new StudyColumnTestFragment(Column_Select_Title));
        mFragments.add(new StudyColumnTestFragment(Limit_Time_Free_Title));
        mFragments.add(new StudyXMLYFragment());
        mTitleList.add(Column_Select_Title);
        mTitleList.add(Limit_Time_Free_Title);
        mTitleList.add(Xmly_Select_Title);


        queryStudyTypeList();
        L.d("-------------- queryStudyTypeList  ------------  ");
    }

    private void setAdapter() {
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(mFragments.size());
        tabLayout.setViewPager(viewPager);
        tabLayout.onPageSelected(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPagerIndex = position;
                if (position == 2) {
                    refreshLayout.setEnableLoadMore(false);
                }else {
                    refreshLayout.setEnableLoadMore(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void queryStudyTypeList() {

        showDefaultLoadingDialog();

        CommonModel.queryStudyTypeList("1", new ComObserver<ArrayList<StudyTypeInfo>>() {

            @Override
            public void onSuccess(BaseResponse<ArrayList<StudyTypeInfo>> t, String errorMsg) {
                L.d("-------------- queryStudyTypeList  --> onSuccess code: " + t.getCode());
                if (t.isSuccess() && t.getData() != null && t.getData().size() > 0) {
                    for (int i = 0; i < t.getData().size(); i++) {
                        StudyTypeInfo studyTypeInfo = t.getData().get(i);

                        L.d("-------------- queryStudyTypeList  --> onSuccess TypeId: " + studyTypeInfo.getId());

                        StudyColumnTestFragment studyColumnTestFragment =
                                new StudyColumnTestFragment(studyTypeInfo.getId() + "");

                        mFragments.add(studyColumnTestFragment);
                        mTitleList.add(studyTypeInfo.getTypeName());


                    }
                }

                setAdapter();
            }
        });
    }


}
