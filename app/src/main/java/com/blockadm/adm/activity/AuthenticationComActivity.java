package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/2/18.
 */

public class AuthenticationComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.rl_perernal)
    RelativeLayout rlPerernal;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.tv_2)
    TextView tv2;

    @BindView(R.id.tv_personal_status)
    TextView tvPersonalStauts;
    @BindView(R.id.tv_jigou_status)
    TextView tvJigouStauts;
    @BindView(R.id.iv_status2)
    ImageView ivStatus2;
    @BindView(R.id.rl_jigou)
    RelativeLayout rlJigou;
    private UserInfoDto userInfoDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_authentication);
        ButterKnife.bind(this);
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        CommonModel.getUserData(this,new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfoDto) {
                AuthenticationComActivity.this.userInfoDto = userInfoDto;
                ACache.get(AuthenticationComActivity.this).put("userInfoDto",userInfoDto);
                initView();
            }

            @Override
            public void error(int code, String msg) {

            }

        });


    }

    private void initView() {
        switch (userInfoDto.getPersonalCredentialsSate()){
            case 0:
                ivStatus.setImageResource(R.mipmap.ic_group2);
                break;

            case 1:
                tvPersonalStauts.setText("审核中");
                ivStatus.setImageResource(R.mipmap.under_review);
                break;

            case 2:
                ivStatus.setImageResource(R.mipmap.finish_certification);
                break;
        }

        switch (userInfoDto.getOrganizationCredentialsSate()){
            case 0:
                ivStatus2.setImageResource(R.mipmap.ic_group2);
                break;

            case 1:

                tvJigouStauts.setText("审核中");
                ivStatus2.setImageResource(R.mipmap.under_review);
                break;

            case 2:
                ivStatus2.setImageResource(R.mipmap.finish_certification);
                break;
        }
    }

    @OnClick({R.id.rl_perernal, R.id.rl_jigou})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_perernal:
                if (userInfoDto!=null){
                    if (userInfoDto.getPersonalCredentialsSate()==1){
                        ToastUtils.showToast("你的认证申请已提交，平台将会在1-7个工作日内，把审核结果发到你的账户内,请注意查收");
                    }else if (userInfoDto.getPersonalCredentialsSate()==0){
                        Intent intent = new Intent(AuthenticationComActivity.this, AuthenticationPersonnalComActivity.class);
                        startActivity(intent);
                    }

                }


                break;
            case R.id.rl_jigou:
                if (userInfoDto!=null){
                    if (userInfoDto.getOrganizationCredentialsSate()==1){
                        ToastUtils.showToast("你的认证申请已提交，平台将会在1-7个工作日内，把审核结果发到你的账户内,请注意查收");
                    }else if (userInfoDto.getOrganizationCredentialsSate()==0){
                        Intent intent = new Intent(AuthenticationComActivity.this, AuthenticationInstitutionComActivity.class);
                        startActivity(intent);
                    }
                }



                break;
        }
    }
}
