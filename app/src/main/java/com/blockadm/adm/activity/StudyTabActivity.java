//package com.blockadm.adm.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.constraint.ConstraintLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.view.ViewPager;
//import android.support.v4.widget.NestedScrollView;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AccelerateInterpolator;
//import android.view.animation.DecelerateInterpolator;
//import android.widget.LinearLayout;
//
//import com.blockadm.adm.R;
//import com.blockadm.adm.adapter.StudyTopListAdapter;
//import com.blockadm.adm.model.CommonModel;
//import com.blockadm.adm.study_module.adapter.ComFragmentAdapter;
//import com.blockadm.adm.study_module.widget.ColorFlipPagerTitleView;
//import com.blockadm.adm.study_module.widget.JudgeNestedScrollView;
//import com.blockadm.common.base.BaseComActivity;
//import com.blockadm.common.bean.AllListDto;
//import com.blockadm.common.bean.BannerListDto;
//import com.blockadm.common.bean.StudyTypeInfo;
//import com.blockadm.common.comstomview.banner.BannerView;
//import com.blockadm.common.http.BaseResponse;
//import com.blockadm.common.http.ComObserver;
//import com.blockadm.common.http.MyObserver;
//import com.blockadm.common.utils.L;
//import com.blockadm.common.utils.ScreenUtils;
//import com.blockadm.common.utils.T;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//import com.scwang.smartrefresh.layout.util.DensityUtil;
//
//import net.lucode.hackware.magicindicator.MagicIndicator;
//import net.lucode.hackware.magicindicator.ViewPagerHelper;
//import net.lucode.hackware.magicindicator.buildins.UIUtil;
//import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
//import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
//import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
//import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
//import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
//import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by Kris on 2019/5/30
// *
// * @Describe TODO {  }
// */
//public class StudyTabActivity extends BaseComActivity {
//
//    @BindView(R.id.bannerView)
//    BannerView bannerView;
//    @BindView(R.id.view_pager)
//    ViewPager viewPager;
//    @BindView(R.id.scrollView)
//    JudgeNestedScrollView scrollView;
//    @BindView(R.id.magic_indicator)
//    MagicIndicator magicIndicator;
//    int toolBarPositionY = 0;
//    @BindView(R.id.recycler_view)
//    RecyclerView recyclerView;
//    @BindView(R.id.layout_title)
//    LinearLayout layoutTitle;
//    @BindView(R.id.refreshLayout)
//    public SmartRefreshLayout refreshLayout;
//    @BindView(R.id.main_title)
//    ConstraintLayout mainTitle;
//    @BindView(R.id.view_line)
//    View viewLine;
//    private ArrayList<BannerListDto> bannerListDtos1;
//    private List<String> mTitleList;
//    private int mScreenHeightPx;
//
//    public static final String Limit_Time_Free_Title = "限时免费";
//    public static final String Column_Select_Title = "精选专栏";
//    public static final String Xmly_Select_Title = "喜马拉雅";
//    private ComFragmentAdapter mFragmentAdapter;
//    private List<Fragment> mFragments;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_study_tab);
//        ButterKnife.bind(this);
//
//        mainTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(StudyTabActivity.this, SearchviewComActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        showDefaultLoadingDialog();
//        CommonModel.findAllList(observerFindAll);
//
//        mainTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(StudyTabActivity.this, SearchviewComActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        scrollView.setVisibility(View.GONE);
//        refreshLayout.setEnableLoadMore(false);//不启用加载
//
//
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//
//                StudyColumnFragment fragment = (StudyColumnFragment) mFragments.get(viewPagerIndex);
//                fragment.loadMore();
//            }
//        });
//
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                StudyColumnFragment fragment =
//                        (StudyColumnFragment) mFragments.get(viewPagerIndex);
//                fragment.refreshData();
//                CommonModel.findAllList(observerFindAll);
//            }
//        });
//
//
//        initView();
//
//
//        initBannerView();
//
//
//    }
//
//    public void setLoadMore(boolean isCanLoadMore) {
//        refreshLayout.setEnableLoadMore(isCanLoadMore);//启用加载
//    }
//
//    private void initBannerView() {
//        bannerView.startLoop();
//        bannerView.setScrollerDuration(1000);
//        CommonModel.findSysBannerList(0, 2, new MyObserver<ArrayList<BannerListDto>>() {
//            @Override
//            public void onSuccess(BaseResponse<ArrayList<BannerListDto>> t) {
//                bannerListDtos1 = t.getData();
//                bannerView.setPictrues(t.getData());
//            }
//        });
//
//        bannerView.setItemClick(new BannerView.OnBannerClickListener() {
//            @Override
//            public void onBannerItemClick(int position) {
//
//                if (bannerListDtos1 != null) {
//                    String url = bannerListDtos1.get(position).getRedirectUrl();
//                    if (!TextUtils.isEmpty(url)) {
//                        Intent intent = new Intent(StudyTabActivity.this, HtmlComActivity.class);
//                        intent.putExtra("url", url);
//                        intent.putExtra("title", bannerListDtos1.get(position).getTitle());
//                        startActivity(intent);
//                    }
//                }
//            }
//        });
//
//    }
//
//
//    MyObserver<ArrayList<AllListDto>> observerFindAll = new MyObserver<ArrayList<AllListDto>>() {
//        @Override
//        public void onSuccess(BaseResponse<ArrayList<AllListDto>> t) {
//            if (t.getCode() == 0) {
//                recycleViewInit(t.getData());
//            } else {
//                T.showShort(StudyTabActivity.this, t.getMsg());
//                scrollView.setVisibility(View.VISIBLE);
//            }
//
//            dismissLoadingDialog();
//
//            refreshLayout.finishRefresh();
//
//        }
//    };
//
//
//    public void recycleViewInit(ArrayList<AllListDto> list) {
//
//        StudyTopListAdapter youlikeAdapterYouLike = new StudyTopListAdapter(list, this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(youlikeAdapterYouLike);
//
//        scrollView.setVisibility(View.VISIBLE);
//        scrollView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dismissLoadingDialog();
//            }
//        }, 400);
//
//    }
//
//    private void initView() {
//
//        recyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//                dealWithViewPager();
//            }
//        });
//        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            int lastScrollY = 0;
//            int h = DensityUtil.dp2px(170);
//            int color =
//                    ContextCompat.getColor(getApplicationContext(), R.color.mainWhite) & 0x00ffffff;
//
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY,
//                                       int oldScrollX, int oldScrollY) {
//                int[] location = new int[2];
//                magicIndicator.getLocationOnScreen(location);
//                int yPosition = location[1];
//
//                Log.d("xxx", " 00000000000000000000 -------------- yPosition: " + yPosition + " ;" +
//                        " toolBarPositionY : " + toolBarPositionY);
//
//                if (yPosition < toolBarPositionY) {
//
//                    scrollView.setNeedScroll(false);
//
//                    Log.d("xxx", "00000000000000000000---------------- scrollView.setNeedScroll" +
//                            "(false)");
//                } else {
//                    scrollView.setNeedScroll(true);
//
//                }
//
//                if (lastScrollY < h) {
//                    scrollY = Math.min(h, scrollY);
//                }
//
//                lastScrollY = scrollY;
//            }
//        });
//        queryStudyTypeList();
//
//    }
//
//    /**
//     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
//     */
//    public int dip2px(Context context, float dpValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }
//
//    private void dealWithViewPager() {
//
//        // marginTop: 110 ; barHeight: 110 ; titleHeight : 113 ; bottomStatusHeight: 216 ;
//        // tablayoutHeiht: 138 ; viewpagerHeight: 1668
//        mScreenHeightPx = ScreenUtils.getScreenHeightPx(this);
//        //        toolBarPositionY = dip2px(this, 120)+mBottomStatusHeight;
//
//        int marginTop = dip2px(this, 40);
//
//        int barHeight = ScreenUtils.getStatusBarHeight(this);
//
//        int titleHeight = layoutTitle.getHeight();
//
//        int bottomStatusHeight = ScreenUtils.getBottomStatusHeight(this);
//
//        if (bottomStatusHeight == 0) {
//            toolBarPositionY = marginTop + titleHeight + barHeight;
//        } else {
//            toolBarPositionY = marginTop + titleHeight;
//        }
//
////        int viewLine
////
////        int[] location = new  int[2] ;
////        viewLine.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
////        viewLine.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
////        location [0]--->x坐标,location [1]--->y坐标
//
//        int[] location = new int[2];
//        viewLine.getLocationOnScreen(location);
//        int yPosition = location[1];
//
//        int tabLayoutHeight = magicIndicator.getHeight();
//
//        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
//
//        int viewpagerHeight =
//                mScreenHeightPx - toolBarPositionY - tabLayoutHeight - bottomStatusHeight + 1;
//        params.height = viewpagerHeight;
//
//        L.d("ttt", " marginTop: " + marginTop + " ; barHeight: " + barHeight + " ; titleHeight : "
//                + titleHeight + " ; bottomStatusHeight: " + bottomStatusHeight + " ; " +
//                "tabLayoutHeight: " + tabLayoutHeight + " ; viewpagerHeight: " + viewpagerHeight+" ; yPosition: "+yPosition);
//
//
//        viewPager.setLayoutParams(params);
//    }
//
//
//    public void queryStudyTypeList() {
//
//        mFragments = new ArrayList<>();
//        mTitleList = new ArrayList<>();
//
//        mFragments.add(new StudyColumnFragment(Column_Select_Title));
//        mFragments.add(new StudyColumnFragment(Limit_Time_Free_Title));
////        mFragments.add(new StudyColumnFragment(Xmly_Select_Title));
//        mTitleList.add(Column_Select_Title);
//        mTitleList.add(Limit_Time_Free_Title);
////        mTitleList.add(Xmly_Select_Title);
//
//        CommonModel.queryStudyTypeList("1", new ComObserver<ArrayList<StudyTypeInfo>>() {
//
//            @Override
//            public void onSuccess(BaseResponse<ArrayList<StudyTypeInfo>> t, String errorMsg) {
//                L.d("-------------- queryStudyTypeList  --> onSuccess code: " + t.getCode());
//                if (t.isSuccess() && t.getData() != null && t.getData().size() > 0) {
//                    for (int i = 0; i < t.getData().size(); i++) {
//                        StudyTypeInfo studyTypeInfo = t.getData().get(i);
//
//                        L.d("-------------- queryStudyTypeList  --> onSuccess TypeId: " + studyTypeInfo.getId());
//
//                        StudyColumnFragment study1ColumnFragment =
//                                new StudyColumnFragment(studyTypeInfo.getId() + "");
//
//                        mFragments.add(study1ColumnFragment);
//                        mTitleList.add(studyTypeInfo.getTypeName());
//
//
//                    }
//                }
//
//                mFragmentAdapter = new ComFragmentAdapter(getSupportFragmentManager(), mFragments);
//                viewPager.setAdapter(mFragmentAdapter);
//                viewPager.setOffscreenPageLimit(10);
//
//                initMagicIndicator();
//            }
//        });
//    }
//
//    private int viewPagerIndex = 0;
//
//    private void initMagicIndicator() {
//        CommonNavigator commonNavigator = new CommonNavigator(this);
//        commonNavigator.setScrollPivotX(0.65f);
//        commonNavigator.setAdjustMode(false);
//        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
//            @Override
//            public int getCount() {
//                return mTitleList == null ? 0 : mTitleList.size();
//            }
//
//            @Override
//            public IPagerTitleView getTitleView(Context context, final int index) {
//                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
//                simplePagerTitleView.setText(mTitleList.get(index));
//                simplePagerTitleView.setNormalColor(ContextCompat.getColor(StudyTabActivity.this,
//                        R.color.color_97979F));
//                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(StudyTabActivity.this,
//                        R.color.color_0A0A0A));
//                simplePagerTitleView.setTextSize(16);
//                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        viewPagerIndex = index;
//                        viewPager.setCurrentItem(index, false);
//                    }
//                });
//                return simplePagerTitleView;
//            }
//
//            @Override
//            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
//                indicator.setLineHeight(UIUtil.dip2px(context, 2));
//                indicator.setLineWidth(UIUtil.dip2px(context, 20));
//                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
//                indicator.setStartInterpolator(new AccelerateInterpolator());
//                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
//                indicator.setColors(ContextCompat.getColor(StudyTabActivity.this,
//                        R.color.color_blue));
//                return indicator;
//            }
//        });
//        magicIndicator.setNavigator(commonNavigator);
//        ViewPagerHelper.bind(magicIndicator, viewPager);
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset,
//                                       int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                viewPagerIndex = position;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }
//
//    public void refreshFinish() {
//        refreshLayout.finishRefresh();
//        refreshLayout.finishLoadMore();
//    }
//
//}
