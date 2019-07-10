package com.blockadm.adm.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.HtmlComActivity;
import com.blockadm.adm.adapter.TabFragmentAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.BannerListDto;
import com.blockadm.common.comstomview.MyTabLayout;
import com.blockadm.common.comstomview.banner.BannerView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hp on 2019/1/10.
 */

@SuppressLint("ValidFragment")
public class InformationFragment extends BaseComFragment {
    @BindView(R.id.bannerView)
    BannerView bannerView;
/*    @BindView(R.id.fl_containt)
    FrameLayout flContaint;*/
    Unbinder unbinder;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.tl_tab)
    MyTabLayout myTabLayout;
    @BindView(R.id.vp)
    ViewPager vp;

    private ArrayList<BannerListDto> bannerListDtos1;
    private int id;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> tabNames;

    @SuppressLint("ValidFragment")
    public InformationFragment(int  id) {
        this.id = id;

    }

    public InformationFragment() {
    }

    @Override
    protected void initView(View rootView) {

        unbinder = ButterKnife.bind(this, rootView);


        fragments = new ArrayList<>();
        tabNames = new ArrayList<>();

        fragments.add(new InformationListNewFragment(0,appBarLayout,id));

        if (id != -2) {
            tabNames.add("最新");
            tabNames.add("最热");
            myTabLayout.setVisibility(View.VISIBLE);
            fragments.add(new InformationListFragment(1,appBarLayout,id));
        }else {
            tabNames.add("");
            myTabLayout.setVisibility(View.GONE);
        }




        bannerView.startLoop();
        bannerView.setScrollerDuration(1000);
        bannerView.setItemClick(new BannerView.OnBannerClickListener() {
            @Override
            public void onBannerItemClick(int position) {
                if (bannerListDtos1!=null){
                   String  url =  bannerListDtos1.get(position).getRedirectUrl();
                   if (!TextUtils.isEmpty(url)){
                       Intent intent = new Intent(getActivity(), HtmlComActivity.class);
                       intent.putExtra("url",url);
                       intent.putExtra("title", bannerListDtos1.get(position).getTitle());
                       startActivity(intent);
                   }

                }


            }
        });



        CommonModel.findSysBannerList(id,3, new MyObserver<ArrayList<BannerListDto>>() {
            @Override
            public void onSuccess(BaseResponse<ArrayList<BannerListDto>> t) {
                bannerListDtos1 = t.getData();
                try{
                    bannerView.setPictrues(t.getData());
                }catch (Exception e){

                }

            }


        });



        setViewpageAdapter(fragments);
    }

    private void setViewpageAdapter(ArrayList<Fragment> fragments) {
        TabFragmentAdapter tabFragmentAdapter  =  new TabFragmentAdapter<Fragment>(getChildFragmentManager(),fragments,tabNames);
        vp.setAdapter(tabFragmentAdapter);
        vp.setOffscreenPageLimit(0);
        myTabLayout.setupWithViewPager(vp);
        myTabLayout.setTabsFromPagerAdapter(tabFragmentAdapter);

        TextView title = (TextView)(((LinearLayout) ((LinearLayout) myTabLayout.getChildAt(0)).getChildAt(0)).getChildAt(1));
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        myTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) myTabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                title.setText(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) myTabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                title.setText(tab.getText());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_information;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
