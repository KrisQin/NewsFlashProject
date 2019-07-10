package com.blockadm.adm.activity;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.Fragment.MoneyRewardFragment;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.TabFragmentAdapter;
import com.blockadm.adm.dialog.YaoqingmaShareDialog;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.AllRecommendCodeDto;
import com.blockadm.common.bean.RecommendEarnedPointDto;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.MyTabLayout;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by hp on 2019/2/25.
 */

public class BountyHunterComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.tv_today_point)
    TextView tvTodaypPoint;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv_dynamic)
    TextView tvDynamic;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.iv_banner_share)
    ImageView ivBannerShare;



    @BindView(R.id.tv_total_today)
    TextView tvTotalToday;
    @BindView(R.id.tl_tab)
    MyTabLayout tlTab;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.vp)
    ViewPager vp;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> tabNames;
    private AllRecommendCodeDto allRecommendCodeDto;
    private ClipboardManager myClipboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bounty_hunter);
        ButterKnife.bind(this);
        initView();
        CommonModel.recommendEarnedPoint(new MyObserver<RecommendEarnedPointDto>() {
            @Override
            public void onSuccess(BaseResponse<RecommendEarnedPointDto> t) {
                if ( t.getData()!=null){
                    tvTodaypPoint.setText(t.getData().getDayEarned()+"");
                    tvTotalToday.setText(t.getData().getAllEarned()+"");
                }
            }
        });

        CommonModel.getAllRecommendCode(new MyObserver <AllRecommendCodeDto>() {
            @Override
            public void onSuccess(BaseResponse<AllRecommendCodeDto> t) {
                if(t.getCode()==0){
                    allRecommendCodeDto = t.getData();
                }

            }

        });
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        ivBannerShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allRecommendCodeDto!=null){
                    RxPermissions rxPermissions = new RxPermissions(BountyHunterComActivity.this);
                    rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里填写所需要的权限
                            .subscribe(new Consumer<Boolean>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void accept(@NonNull Boolean aBoolean) throws Exception {
                                    if (aBoolean) {
                                        //当所有权限都允许之后，返回true
                                        YaoqingmaShareDialog yaoqingmaShareDialog = new YaoqingmaShareDialog(BountyHunterComActivity.this,allRecommendCodeDto,myClipboard);
                                        yaoqingmaShareDialog.show();
                                    } else {
                                        //只要有一个权限禁止，返回false，
                                        //下一次申请只申请没通过申请的权限
                                    }
                                }
                            });

                }
            }
        });

    }

    private void initView() {
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tilte.setOnRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BountyHunterComActivity.this, HtmlComActivity.class);
                intent.putExtra("url","http://app.blockadm.pro/user/page/visitor/html?html=activeRules");
                intent.putExtra("title", "活动规则");
                startActivity(intent);
            }
        });

        fragments = new ArrayList<>();

        tabNames = new ArrayList<>();
        tabNames.add("全部");
        tabNames.add("M1");
        tabNames.add("M2");



        fragments.add(new MoneyRewardFragment(0,appbar));
        fragments.add(new MoneyRewardFragment(1,appbar));
        fragments.add(new MoneyRewardFragment(2,appbar));
        setViewpageAdapter(fragments);

    }

    private void setViewpageAdapter(ArrayList<Fragment> fragments) {
        TabFragmentAdapter tabFragmentAdapter  =  new TabFragmentAdapter<Fragment>(getSupportFragmentManager(),fragments,tabNames);
        vp.setAdapter(tabFragmentAdapter);
        vp.setOffscreenPageLimit(3);
        tlTab.setupWithViewPager(vp);
        tlTab.setTabsFromPagerAdapter(tabFragmentAdapter);

    }

    public void setTotal(int total) {
        tvTotal.setText(total+"份");
    }
}
