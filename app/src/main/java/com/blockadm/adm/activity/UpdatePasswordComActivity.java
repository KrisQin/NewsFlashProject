package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.SharedpfTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/1/31.
 */

public class UpdatePasswordComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_telnum)
    TextView tvTelnum;
    @BindView(R.id.tv_yanzheng)
    TextView tvYanzheng;
    private UserInfoDto userInfoDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_update_password);
        ButterKnife.bind(this);

        userInfoDto = (UserInfoDto) ACache.get(this).getAsObject("userInfoDto");
        if (userInfoDto!=null){
            tvTelnum.setText("你的手机号："+userInfoDto.getPhone());
        }
        int type = (int) SharedpfTools.getInstance(UpdatePasswordComActivity.this).get("telsetting",-1);
        if (type==4){
            tilte.setTitle("设置登录密码");
        }

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.tv_yanzheng)
    public void onViewClicked() {

        Intent intent = new Intent(this, CodeComActivity.class);
        startActivity(intent);
    }
}
