package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.adapter.MyVipAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.VipDto;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by hp on 2019/2/21.
 */

public class MyVipComActivity extends BaseComActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.civ_headimage)
    CircleImageView civHeadimage;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_vip)
    ImageView ivVip;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.swipe_target)
    EmptyRecyclerView swipeTarget;
    @BindView(R.id.iv_bottom)
    ImageView ivBottom;
    private UserInfoDto userInfoDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_myvip);
        ButterKnife.bind(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        userInfoDto = (UserInfoDto) ACache.get(this).getAsObject("userInfoDto");
        CommonModel.getVipPrivilege(new MyObserver<ArrayList<VipDto>>() {
            @Override
            public void onSuccess(BaseResponse<ArrayList<VipDto>> t) {
                if (t.getCode() == 0) {
                    setDataToView(t.getData());
                } else {
                    ToastUtils.showToast(t.getMsg());
                }
            }
        });

        CommonModel.getUserData(this, new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfo) {
                userInfoDto = userInfo;
                ACache.get(MyVipComActivity.this).put("userInfoDto", userInfoDto);
                ImageUtils.loadImageView(userInfoDto.getIcon(), civHeadimage);
                title.setText(userInfoDto.getNickName());

                if (userInfoDto.getVipState() == 1) {
                    ivVip.setImageResource(R.mipmap.idpicture01);
                    ivBottom.setVisibility(View.GONE);
                }
            }

            @Override
            public void error(int code, String msg) {

            }

        });


    }

    private void setDataToView(ArrayList<VipDto> vipDto) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        swipeTarget.setLayoutManager(linearLayoutManager);
        swipeTarget.setAdapter(new MyVipAdapter(vipDto, userInfoDto.getVipState()));
    }

    @OnClick({R.id.iv_back, R.id.iv_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_bottom:

                Intent intent = new Intent(this, PayOrderComActivity.class);
                startActivity(intent);
                break;
        }
    }
}
