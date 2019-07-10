package com.blockadm.adm.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.MainApp;
import com.blockadm.adm.R;
import com.blockadm.adm.event.MessageEvent;
import com.blockadm.adm.event.UpdataStudyEvent;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.LoginByThirdPartyDTO;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.config.ARouterPathConfig;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/1/31.
 */

public class BindTelNumComActivity extends BaseComActivity {


    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.tv_telnum)
    TextView tvTelnum;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private LoginByThirdPartyDTO loginByThirdPartyDTO;

    public static final String COM_FROM = "COM_FROM";

    private String className = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_tel_num);
        ButterKnife.bind(this);

        className = getIntent().getStringExtra(COM_FROM);

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSend.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        tvSend.getPaint().setAntiAlias(true);//抗锯齿
        loginByThirdPartyDTO = (LoginByThirdPartyDTO) getIntent().getSerializableExtra("LoginByThirdPartyDTO");

    }
      MyCountDownTimer myCountDownTimer = new  MyCountDownTimer(60000, 1000);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (MainApp.isThreellogin()){
//            SharedpfTools.getInstance(ContextUtils.getBaseApplication()).put(ConstantUtils.TOKEN,"");
//        }
    }

    private void send() {

        String phone  =  etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            ToastUtils.showToast("请输入手机号");
            return;
        }
        myCountDownTimer.start();
        CommonModel.sendSms(phone,new MyObserver<String>() {
            @Override
            public void onSuccess(BaseResponse<String> t) {
                ToastUtils.showToast(t.getMsg());
            }
        });

    }


    @OnClick({R.id.tv_send, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                send();
                break;
            case R.id.tv_commit:
               String code  =  etCode.getText().toString().trim();
                String phone  =  etPhone.getText().toString().trim();
               if (TextUtils.isEmpty(code)){
                   ToastUtils.showToast("请输入验证码");
                   return;
               }
                if (TextUtils.isEmpty(phone)){
                    ToastUtils.showToast("请输入手机号");
                    return;
                }
                CommonModel.bindPhone(phone,code,new MyObserver<String>() {
                    @Override
                    public void onSuccess(BaseResponse<String> t) {
                        ToastUtils.showToast(t.getMsg());
                        if (t.getCode()==0){

                            if (StringUtils.isEquals(className,SettingComActivity.className)) {
                                finish();
                            }else {
                                if (MainApp.isThreellogin()) {
                                    if (loginByThirdPartyDTO != null) {
                                        ACache.get(BindTelNumComActivity.this).put(ConstantUtils.TAGS, loginByThirdPartyDTO.getSubscribeLableList());

                                    }
                                    EventBus.getDefault().post(new MessageEvent(""));
                                    EventBus.getDefault().post(new UserDataEvent());
                                    EventBus.getDefault().post(new UpdataStudyEvent());
                                    ARouter.getInstance().build(ARouterPathConfig.block_main_activity_path).navigation();
                                } else {
                                    Intent intent = new Intent(BindTelNumComActivity.this, SettingComActivity.class);
                                    startActivity(intent);
                                }
                            }

                        }

//                        if (MainApp.isThreellogin()&&t.getCode()!=0){
//                            SharedpfTools.getInstance(ContextUtils.getBaseApplication()).put(ConstantUtils.TOKEN,"");
//                        }
                    }
                });
                break;
        }
    }
}
