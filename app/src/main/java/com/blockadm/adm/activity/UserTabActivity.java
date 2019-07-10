package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.ThirdWebButtonAdapter;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.adm.utils.DialogUtils;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.SysThirdWebListInfo;
import com.blockadm.common.bean.ThirdWebListInfo;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.comstomview.FullRecyclerView;
import com.blockadm.common.comstomview.RoundImageView;
import com.blockadm.common.http.ApiService;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.web.FullWebBaseViewActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Kris on 2019/5/30
 *
 * @Describe TODO {  }
 */
public class UserTabActivity extends BaseComActivity {

    @BindView(R.id.civ_headimage)
    CircleImageView civHeadimage;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.civ_levle)
    ImageView civLevle;
    @BindView(R.id.tv_synopsis)
    TextView tvSynopsis;
    @BindView(R.id.banner_view)
    RoundImageView bannerView;
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_uersid)
    TextView tvUersid;
    @BindView(R.id.tv_usercollect)
    TextView tvUsercollect;
    @BindView(R.id.tv_huntsman)
    TextView tvHuntsman;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.tv_help)
    TextView tvHelp;
    @BindView(R.id.tv_attention_count)
    TextView tvAttentionCount;
    @BindView(R.id.tv_fans)
    TextView tvFans;

    @BindView(R.id.tv_yaoqing)
    TextView tv_yaoqing;
    @BindView(R.id.v)
    View v;

    Unbinder unbinder;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.rl_root)
    LinearLayout rlRoot;
    @BindView(R.id.tv_she_qun)
    TextView tvSheQun;
    @BindView(R.id.recycler_view)
    FullRecyclerView recyclerView;
    @BindView(R.id.layout_third)
    LinearLayout layoutThird;
    private UserInfoDto userInfoDto;
    private int mCredentialsState;
    private List<SysThirdWebListInfo> sysThirdWebList = new ArrayList<>();
    private ThirdWebButtonAdapter mThirdWebButtonAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tab);
        ButterKnife.bind(this);

        mThirdWebButtonAdapter = new ThirdWebButtonAdapter(this,sysThirdWebList);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mThirdWebButtonAdapter);

        mThirdWebButtonAdapter.setOnItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(UserTabActivity.this, FullWebBaseViewActivity.class);
                intent.putExtra(FullWebBaseViewActivity.DATA_WEB_URL,sysThirdWebList.get(position).getWebUrl());
                intent.putExtra(FullWebBaseViewActivity.Is_Back_Finish_Activity,true);
                intent.putExtra(FullWebBaseViewActivity.TITLE, sysThirdWebList.get(position).getWebName());
                startActivity(intent);
            }
        });
    }

    public void initView() {
        String token =
                (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            tvNum.setVisibility(View.INVISIBLE);
            civLevle.setVisibility(View.GONE);
            tvSynopsis.setVisibility(View.INVISIBLE);
            tvName.setText("用户未登录");
            v.setVisibility(View.GONE);
            tvAttentionCount.setVisibility(View.GONE);
            tvFans.setVisibility(View.GONE);
            userInfoDto = null;
            civHeadimage.setImageResource(R.mipmap.picture_default);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserData(UserDataEvent userDataEvent) {
        getUserData();
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        getUserData();
        findAllThirdWebList();
    }

    private void findAllThirdWebList() {
        CommonModel.findAllThirdWebList(new MyObserver<ThirdWebListInfo>() {
            @Override
            public void onSuccess(BaseResponse<ThirdWebListInfo> t) {
                if (t != null) {
                    showThirdData(t.getData());
                }
            }
        });
    }

    private void getUserData() {

        showDefaultLoadingDialog();
        CommonModel.getUserData(this, new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfo) {
                dismissLoadingDialog();
                userInfoDto = userInfo;
                if (userInfoDto != null) {

                    mCredentialsState = userInfoDto.getCredentialsState();

                    tvNum.setVisibility(View.VISIBLE);
                    civLevle.setVisibility(View.VISIBLE);
                    tvSynopsis.setVisibility(View.VISIBLE);
                    iv.setVisibility(View.VISIBLE);
                    if (userInfoDto.getMsgSize() > 0) {
                        tvNum.setText(userInfoDto.getMsgSize() + "");
                        tvNum.setVisibility(View.VISIBLE);
                    } else {
                        tvNum.setVisibility(View.GONE);
                    }

                    tvName.setText(userInfoDto.getNickName());
                    tvSynopsis.setText(userInfoDto.getSign() + "");
                    ImageUtils.loadImageView(userInfoDto.getIcon(), civHeadimage);
                    tvAttentionCount.setVisibility(View.VISIBLE);
                    tvFans.setVisibility(View.VISIBLE);
                    tvAttentionCount.setText("关注 " + userInfoDto.getConcern());
                    tvFans.setText("粉丝 " + userInfoDto.getFollower());
                    if (userInfoDto.getVipState() == 1) {
                        iv.setImageResource(R.mipmap.user_vip_copy);
                    } else {
                        iv.setImageResource(R.mipmap.user_vip_default);
                    }
                    ImageUtils.loadImageView(userInfoDto.getLevelDiamondIcon(), civLevle);
                    if (UserTabActivity.this != null) {
                        ACache.get(UserTabActivity.this).put("userInfoDto", userInfoDto);
                    }

                }

            }

            @Override
            public void error(int code, String msg) {
                dismissLoadingDialog();
            }

        });
    }

    private void showThirdData(ThirdWebListInfo thirdWebListInfo) {
        if (thirdWebListInfo == null)
            return;

        if (thirdWebListInfo.getIsShowThirdWebButton() == 1) {
            layoutThird.setVisibility(View.VISIBLE);
        }else {
            layoutThird.setVisibility(View.GONE);
        }

        sysThirdWebList.clear();
        sysThirdWebList.addAll(thirdWebListInfo.getSysThirdWebList());

        mThirdWebButtonAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    @OnClick({R.id.tv_huntsman, R.id.rl_yaoqing, R.id.iv_msg, R.id.civ_headimage,
            R.id.tv_setting, R.id.tv_help, R.id.tv_usercollect, R.id.tv_pay, R.id.tv_uersid,
            R.id.tv_attention_count, R.id.tv_fans, R.id.banner_view,R.id.iv,R.id.civ_levle,
            R.id.tv_production, R.id.tv_mystudy, R.id.tv_azuan, R.id.tv_she_qun, R.id.tv_name})
    public void onViewClicked(View view) {

        if (userInfoDto == null) {
            ContextUtils.showNoLoginDialog(this);
            return;
        }

        switch (view.getId()) {
            case R.id.rl_yaoqing:
            case R.id.civ_levle:
                Intent intent1 = new Intent(UserTabActivity.this, InvitePrizeComActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_huntsman:
                Intent intentHuntsman = new Intent(UserTabActivity.this,
                        BountyHunterComActivity.class);
                startActivity(intentHuntsman);

                break;

            case R.id.tv_fans:
                ARouter.getInstance().build("/app/index/FansListComActivity").withInt("id",
                        -1).navigation();

                break;

            case R.id.tv_mystudy:
                Intent intentStudy = new Intent(UserTabActivity.this, MyStudyActiviy.class);
                startActivity(intentStudy);

                break;
            case R.id.tv_production:
                Intent intentPro = new Intent(UserTabActivity.this,
                        ProductManagerComActivity.class);
                intentPro.putExtra("memberId", userInfoDto.getMemberId());
                startActivity(intentPro);

                break;

            case R.id.banner_view:
                Intent intentBanner = new Intent(UserTabActivity.this, MyVipComActivity.class);
                startActivity(intentBanner);

                break;

            case R.id.tv_attention_count:
                ARouter.getInstance().build("/app/index/AttentionListComActivity").withInt(
                        "id", -1).navigation();
                break;
            case R.id.tv_uersid:
                Intent intentUser = new Intent(UserTabActivity.this,
                        AuthenticationComActivity.class);
                startActivity(intentUser);
                break;
            case R.id.tv_setting:
                Intent intentSetting = new Intent(UserTabActivity.this, SettingComActivity.class);
                startActivity(intentSetting);
                break;

            case R.id.tv_pay:
                Intent intentPay = new Intent(UserTabActivity.this, MyMoneyComActivity.class);
                startActivity(intentPay);
                break;
            case R.id.iv_msg:
                Intent intentMsg = new Intent(UserTabActivity.this, MsgListComActivity.class);
                startActivity(intentMsg);
                break;

            case R.id.tv_help:
                Intent intentHelp = new Intent(UserTabActivity.this, FeedBackComActivity.class);
                startActivity(intentHelp);

                break;

            case R.id.tv_usercollect:
                Intent intentCollect = new Intent(UserTabActivity.this,
                        CollectListComActivity.class);
                startActivity(intentCollect);
                break;

            case R.id.civ_headimage:
            case R.id.tv_name:
            case R.id.iv:
                Intent intent = new Intent(UserTabActivity.this, UpdateMyActitivity.class);
                startActivity(intent);

                break;

            case R.id.tv_azuan:
                String url = ApiService.BASR_URL_RELEASE + "/user/page/visitor" +
                        "/getEncourageDataHtml" + "?memberId=" + userInfoDto.getMemberId();
                Intent intent2 = new Intent(UserTabActivity.this, FullWebBaseViewActivity.class);
                intent2.putExtra(FullWebBaseViewActivity.DATA_WEB_URL, url);
                intent2.putExtra(FullWebBaseViewActivity.TITLE, "A钻攻略");
                startActivity(intent2);

                break;
        }
    }



}
