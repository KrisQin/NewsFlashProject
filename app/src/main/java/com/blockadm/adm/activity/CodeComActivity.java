package com.blockadm.adm.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/1/31.
 */

public class CodeComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.tv_telnum)
    TextView tvTelnum;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.tv_next)
    TextView tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_code);
        ButterKnife.bind(this);
        UserInfoDto  userInfoDto = (UserInfoDto) ACache.get(this).getAsObject("userInfoDto");
        tvTelnum.setText("已向手机号"+userInfoDto.getPhone().replace(userInfoDto.getPhone().substring(7,userInfoDto.getPhone().length()),"xxxx")+"发送短信验证码");
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSend.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        tvSend.getPaint().setAntiAlias(true);//抗锯齿
        send();

    }
     MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);

    //复写倒计时
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            tvSend.setClickable(false);
            tvSend.setText(l / 1000 + "s后重新发送验证码");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            tvSend.setText("重新获取验证码");
            //设置可点击
            tvSend.setClickable(true);
        }
    }

    private void send() {
        myCountDownTimer.start();
        CommonModel.sendSetSms(new MyObserver<String>() {
            @Override
            public void onSuccess(BaseResponse<String> t) {
                ToastUtils.showToast(t.getMsg());
            }
        });

    }

    @OnClick({R.id.tv_send, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                send();
                break;
            case R.id.tv_next:
             final String code =    et.getText().toString().trim();
             if (TextUtils.isEmpty(code)){
                 ToastUtils.showToast("请输入验证码");
             }else{
                 CommonModel.checkSetSms(code,new MyObserver<String>() {
                     @Override
                     public void onSuccess(BaseResponse<String> t) {
                         ToastUtils.showToast(t.getMsg());
                         if (t.getCode()==0){
                           int type = (int) SharedpfTools.getInstance(CodeComActivity.this).get("telsetting",-1);
                             if(type==3){
                                 Intent intent = new Intent(CodeComActivity.this, BindTelNumComActivity.class);
                                 intent.putExtra("code",code);
                                 startActivity(intent);
                             }else if (type==2){
                                 Intent intent = new Intent(CodeComActivity.this, PayPasswordComActivity.class);
                                 intent.putExtra("code",code);
                                 startActivity(intent);
                             }else if (type==4){
                                 Intent intent = new Intent(CodeComActivity.this, PayPasswordComActivity.class);
                                 intent.putExtra("code",code);
                                 startActivity(intent);
                             }

                         }
                     }
                 });

                 break;
             }

        }
    }


}

