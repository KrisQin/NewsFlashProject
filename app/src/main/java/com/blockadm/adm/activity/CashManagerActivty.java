package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blockadm.adm.R;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.utils.ACache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/2/20.
 */

public class CashManagerActivty extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.rl_alipay)
    RelativeLayout rlAlipay;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.rl_wechat)
    RelativeLayout rlWechat;
    private UserInfoDto userInfoDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_cash_manager);
        ButterKnife.bind(this);

        userInfoDto = (UserInfoDto) ACache.get(this).getAsObject("userInfoDto");

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.rl_alipay, R.id.rl_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_alipay:
                if (userInfoDto.getIsBindZFBWithdrawAccount()==0){
                    Intent intent = new Intent(CashManagerActivty.this, AddAlipayAcountComActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(CashManagerActivty.this, UpdataAlipayAcountComActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.rl_wechat:

             /*   if (userInfoDto.getIsBindWXWithdrawAccount()==0){
                    Intent intent = new Intent(CashManagerActivty.this,AddWetchatAcountComActivity.class);
                    startActivity(intent);
             }else{
                    Intent intent = new Intent(CashManagerActivty.this,UpdataAlipayAcountComActivity.class);
                    startActivity(intent);
                }*/
                break;
        }
    }
}
