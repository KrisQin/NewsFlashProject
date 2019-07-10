package com.blockadm.adm.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.Fragment.ExclusiveColumnFragment;
import com.blockadm.adm.Fragment.InformationListFragment;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.TabFragmentAdapter;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.PersonalDTO;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.comstomview.MyTabLayout;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ImageUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/1/25.
 */

public class PersonnalIndexComActivity extends BaseComActivity {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.civ_headimage)
    CircleImageView civHeadimage;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_institution)
    TextView tvInstitution;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_attention_count)
    TextView tvAttentionCount;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.tv_arrow)
    ImageView tvArrow;
    @BindView(R.id.tv_jianjie)
    TextView tvJianjie;
    @BindView(R.id.civ_level)
    ImageView civLevel;
    @BindView(R.id.iv_level)
    ImageView ivLevel;
    @BindView(R.id.rl_level)
    RelativeLayout rlLevel;
    @BindView(R.id.tl_tab)
    MyTabLayout tlTab;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.rl_root)
    LinearLayout rlRoot;
    private PersonalDTO personalDTO;
    private  int memberId;
    private static int total;
    private ArrayList<String> tabNames1;
    private UserInfoDto userInfoDto;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_personal_index);
        ButterKnife.bind(this);
        memberId =  getIntent().getIntExtra(ConstantUtils.MENBERID,-1);
        userInfoDto = (UserInfoDto) ACache.get(this).getAsObject("userInfoDto");
        if (userInfoDto !=null&& userInfoDto.getMemberId()==memberId){
           tvAttention.setVisibility(View.GONE);
            tvArrow.setVisibility(View.VISIBLE);
        }else{
          tvAttention.setVisibility(View.VISIBLE);
            tvArrow.setVisibility(View.GONE);
        }
        CommonModel.getMemberInfoByUserId(memberId, new MyObserver<PersonalDTO>() {


            @Override
            public void onSuccess(BaseResponse<PersonalDTO> t) {
                  personalDTO =  t.getData();
              if (personalDTO!=null){
                  ImageUtils.loadImageView(personalDTO.getIcon(),civHeadimage);
                  tvName.setText(personalDTO.getNickName());
                  tvAge.setText(personalDTO.getAge()+"");
                  tvAttentionCount.setText("关注 "+personalDTO.getConcern());
                  tvFans.setText("粉丝 "+personalDTO.getFollower());
                  if (personalDTO.getConcernSate() == 1) {
                      Drawable dra= getResources().getDrawable(R.drawable.login_login_btn_normal);
                      tvAttention.setBackground(dra);
                      tvAttention.setText("已关注");
                  } else {
                      Drawable dra= getResources().getDrawable(R.drawable.login_login_btn_ok);
                      tvAttention.setBackground(dra);
                      tvAttention.setText("关注");
                  }
                  tvJianjie.setText(personalDTO.getSign());
                   switch (personalDTO.getCredentialsState()){
                       case 0:
                           tvInstitution.setText("未认证");
                           tvInstitution.setVisibility(View.GONE);
                           break;
                       case 1:
                           tvInstitution.setText("已个人认证");
                           tvInstitution.setVisibility(View.GONE);
                           break;
                       case 2:
                           tvInstitution.setText("已机构认证");
                           break;
                       case 3:
                           tvInstitution.setText("已机构认证");
                           break;
                   }
                   if (personalDTO.getVipState()==1){
                       iv.setImageResource(R.mipmap.user_vip_copy);
                   }else{
                       iv.setImageResource(R.mipmap.user_vip_default);
                   }

                  ImageUtils.loadImageView(personalDTO.getLevelNameIcon(),ivLevel);
                  ImageUtils.loadImageView(personalDTO.getLevelDiamondIcon(),civLevel);

              }
            }


        });


        ArrayList<Fragment> fragments = new ArrayList<>();
        tabNames1 = new ArrayList<>();
        ExclusiveColumnFragment exclusiveColumnFragment = new ExclusiveColumnFragment(memberId+"");
        InformationListFragment informationListFragmentNew = new InformationListFragment(memberId+"");
//        PersonLiveListFragment personLiveFragment = new PersonLiveListFragment(memberId+"");

        fragments.add(exclusiveColumnFragment);
        fragments.add(informationListFragmentNew);
        tabNames1.add("精彩专栏");
        tabNames1.add("精彩文章");
        setViewpageAdapter(fragments, tabNames1);
    }

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
                vp.setCurrentItem(tab.getPosition());
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                title.setText(tabNames1.get(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
               // title.setText(tab.getText());
                title.setText(tabNames1.get(tab.getPosition()));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    public  void setTotal(int total,int position) {
/*
            TextView title = (TextView)(((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(position)).getChildAt(1));
            switch (position){
                case 0:
                    tabNames1.remove(0);
                    tabNames1.add(0,"专栏"+total);
                    title.setText(tabNames1.get(0));
                    break;
                case 1:
                    tabNames1.remove(1);
                    tabNames1.add(1,"课程"+total);
                    title.setText(tabNames1.get(1));
                    break;

                case 2:
                    tabNames1.remove(2);
                    tabNames1.add(2,"文章"+total);
                    title.setText(tabNames1.get(2));
                    break;
            }*/
    }


    @OnClick({R.id.tv_back, R.id.tv_attention, R.id.rl_level, R.id.tv_fans, R.id.tv_attention_count})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_fans:
                ARouter.getInstance().build("/app/index/FansListComActivity").withInt("id", memberId).navigation();
                break;

            case R.id.tv_attention_count:
                ARouter.getInstance().build("/app/index/AttentionListComActivity").withInt("id", memberId).navigation();
                break;

            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_attention:

                if (personalDTO.getConcernSate()==0){
                    CommonModel.addUserFollow(memberId, new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse t) {
                            personalDTO.setConcernSate(1);
                            Drawable dra= getResources().getDrawable(R.drawable.login_login_btn_normal);
                            tvAttention.setBackground(dra);
                            tvAttention.setText("未关注");
                            EventBus.getDefault().post(new UserDataEvent());
                        }
                    });
                }else{
                    CommonModel.deleteUserFollow(memberId, new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse<String> baseResponse) {
                            personalDTO.setConcernSate(0);
                            Drawable dra= getResources().getDrawable(R.drawable.login_login_btn_ok);
                            tvAttention.setBackground(dra);
                            tvAttention.setText("关注");
                            EventBus.getDefault().post(new UserDataEvent());
                        }


                    });
                }
                break;
            case R.id.rl_level:
                if (userInfoDto!=null&&userInfoDto.getMemberId()==memberId){
                    Intent intent1 = new Intent(this, InvitePrizeComActivity.class);
                    startActivity(intent1);
                }

                break;
        }
    }
}
