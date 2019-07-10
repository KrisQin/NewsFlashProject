package com.blockadm.adm.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.MainApp;
import com.blockadm.adm.R;
import com.blockadm.adm.contact.LoginContract;
import com.blockadm.adm.event.LoginPassWordEvent;
import com.blockadm.adm.event.MessageEvent;
import com.blockadm.adm.event.UpdataStudyEvent;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.adm.model.LoginModel;
import com.blockadm.adm.persenter.LoginPresenter;
import com.blockadm.common.base.BaseActivity;
import com.blockadm.common.bean.LoginByThirdPartyDTO;
import com.blockadm.common.bean.LoginByThirdPartyParams;
import com.blockadm.common.bean.params.LoginBean;
import com.blockadm.common.comstomview.CheckEmptyTextView;
import com.blockadm.common.config.ARouterPathConfig;
import com.blockadm.common.config.ComConfig;
import com.blockadm.common.http.ApiService;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.KeyboardUtils;
import com.blockadm.common.utils.RegularuUtil;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.Utils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPathConfig.login_activity_path)
public class LoginComActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {


    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.et_acount)
    EditText etAcount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_get_msg_code)
    TextView tvGetMsgCode;
    @BindView(R.id.et_recommend)
    EditText etRecommend;
    @BindView(R.id._bt_login2)
    CheckEmptyTextView BtLogin2;


    @BindView(R.id.iv_wetchat_login)
    ImageView ivWetchatLogin;
    private UMShareAPI umShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }

    }

    private void initView() {
        BtLogin2.setCheckEmptyEditTexts(etAcount, etPassword);

    }

    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);

    @OnClick({R.id.tv_get_msg_code, R.id._bt_login2, R.id.iv_wetchat_login,
            R.id.tv_password_login, R.id.tv_agreement, R.id.tv_private, R.id.iv_back})
    public void onViewClicked(View view) {


        switch (view.getId()) {
            case R.id.tv_get_msg_code:
                String account = etAcount.getText().toString().trim();

                if (TextUtils.isEmpty(account)) {
                    showToast(this, "请输入手机号");
                } else if (!RegularuUtil.telNumberRegex(account, this)) {
                    showToast(this, "请填写正确的手机号");
                    return;
                } else {
                    myCountDownTimer.start();
                    mPersenter.sendSms(account);
                }

                break;
            case R.id._bt_login2:
                String account1 = etAcount.getText().toString().trim();
                String passWord = etPassword.getText().toString().trim();
                String recommend = etRecommend.getText().toString().trim();
                if (TextUtils.isEmpty(account1)) {
                    showToast(this, "请输入手机号");
                    return;
                }
                if (!RegularuUtil.telNumberRegex(account1, this)) {
                    showToast(this, "请填写正确的手机号");
                    return;
                }

                mPersenter.login(0, account1, passWord, recommend);
                break;
            case R.id.iv_wetchat_login:

                try {
                    umShareAPI = UMShareAPI.get(this);
                    umShareAPI.getPlatformInfo(LoginComActivity.this, SHARE_MEDIA.WEIXIN,
                            umAuthListener);
                } catch (Exception e) {
                    Toast.makeText(LoginComActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                break;


            case R.id.iv_back:
                finish();
                EventBus.getDefault().post(new LoginPassWordEvent());
                break;

            case R.id.tv_password_login:
                Intent intent = new Intent(this, LoginPasswordComActivity.class);
                startActivity(intent);
                break;


            case R.id.tv_agreement:
                String url = ApiService.BASR_URL_RELEASE + "/user/page/visitor/html?html" +
                        "=userAgreement";
                Intent intent2 = new Intent(this, HtmlComActivity.class);
                intent2.putExtra("url", url);
                intent2.putExtra("title", "用户协议");
                startActivity(intent2);
                break;
            case R.id.tv_private:
                String url1 = ApiService.BASR_URL_RELEASE + "/user/page/visitor/html?html" +
                        "=privacyAgreement";
                Intent intent1 = new Intent(this, HtmlComActivity.class);
                intent1.putExtra("url", url1);
                intent1.putExtra("title", "隐私政策");
                startActivity(intent1);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }



    UMAuthListener umAuthListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.e("onComplete: ", data.toString());
            Log.e("onComplete: ", data.get("uid"));
            Log.e("onComplete openid: ", data.get("openid"));
            Log.e("onComplete: ", data.get("name"));
            Log.e("onComplete: ", data.get("iconurl"));
            Log.e("onComplete: ", data.get("gender"));
            int sex = data.get("gender").equals("女") ? 1 : 0;
            LoginByThirdPartyParams loginByThirdPartyParams =
                    new LoginByThirdPartyParams(data.get("iconurl"), data.get("name"), sex,
                            data.get("uid"), 0);
            CommonModel.loginByThirdParty(GsonUtil.GsonString(loginByThirdPartyParams),
                    new MyObserver<LoginByThirdPartyDTO>() {
                @Override
                public void onSuccess(BaseResponse<LoginByThirdPartyDTO> t) {

                    if (t.getCode() == 0) {
                        MainApp.setThreellogin(true);
                        if (t.getData() != null) {
                            SharedpfTools.getInstance(ContextUtils.getBaseApplication()).put(ConstantUtils.TOKEN, t.getData().getToken());
                            ACache.get(LoginComActivity.this).put(ConstantUtils.TAGS, t.getData().getSubscribeLableList());
                            Toast.makeText(LoginComActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                        EventBus.getDefault().post(new MessageEvent(""));
                        EventBus.getDefault().post(new UserDataEvent());
                        EventBus.getDefault().post(new UpdataStudyEvent());

                        ARouter.getInstance().build(ARouterPathConfig.block_main_activity_path).navigation();

                        finish();
                    }

                }
            });

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(LoginComActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginComActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        umShareAPI.get(this).release();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        umShareAPI.get(this).onSaveInstanceState(outState);
    }

    @Override
    public void showLoginSucceed(BaseResponse<LoginBean> t) {
        try {
            if (t.getCode() == 0) {
                LoginBean loginBean = t.getData();
                KeyboardUtils.hideSoftInput(this);
                ACache.get(this).put(ConstantUtils.TAGS, loginBean.getSubscribeLableList());

                SharedpfTools.getInstance(ContextUtils.getBaseApplication()).put(ConstantUtils.TOKEN, loginBean.getToken());
                EventBus.getDefault().post(new MessageEvent(""));
                ARouter.getInstance().build(ARouterPathConfig.block_main_activity_path).navigation();
                EventBus.getDefault().post(new UserDataEvent());
                finish();
            } else {
                Toast.makeText(this, t.getMsg(), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void showYanzhengma(BaseResponse<String> baseResponse) {
        if (!baseResponse.isSuccess()) {
            myCountDownTimer.cancel();
            //重新给Button设置文字
            tvGetMsgCode.setText("重新获取验证码");
            tvGetMsgCode.setTextColor(ContextCompat.getColor(Utils.getContext(),
                    R.color.color_FF6B00));
            //设置可点击
            tvGetMsgCode.setClickable(true);
        }
    }

    //复写倒计时
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            tvGetMsgCode.setClickable(false);
            tvGetMsgCode.setTextColor(ContextCompat.getColor(LoginComActivity.this,
                    R.color.color_FFB6B6BA));
            tvGetMsgCode.setText(l / 1000 + "s");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            tvGetMsgCode.setText("重新获取验证码");
            tvGetMsgCode.setTextColor(ContextCompat.getColor(LoginComActivity.this,
                    R.color.color_FF6B00));
            //设置可点击
            tvGetMsgCode.setClickable(true);
        }
    }
}
