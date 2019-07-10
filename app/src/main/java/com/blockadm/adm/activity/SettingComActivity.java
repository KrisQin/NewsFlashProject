package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.event.MessageEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseApplication;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.T;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/1/30.
 */

public class SettingComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.tv_login_password_status)
    TextView tvloginPasswordStatus;
    @BindView(R.id.rl_zhifu)
    RelativeLayout rlZhifu;
    @BindView(R.id.tv_telnum)
    TextView tvTelnum;

    @BindView(R.id.tv_loginout)
    TextView tvLoginout;
    @BindView(R.id.rl_shoujihao)
    RelativeLayout rlShoujihao;
    @BindView(R.id.rl_private)
    RelativeLayout rlPrivate;
    @BindView(R.id.rl_clear)
    RelativeLayout rlClear;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_bind_status)
    TextView tvBindStatus;
    @BindView(R.id.rl_bind_wx)
    RelativeLayout rlBindWx;
    @BindView(R.id.iv_bind_wx_arrow)
    ImageView ivBindWxArrow;
    private UserInfoDto userInfoDto;
    private UMShareAPI umShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_setting);
        ButterKnife.bind(this);
        SharedpfTools.getInstance(this).put("telsetting", -1);

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSize.setText("0kb");

    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserData();
    }

    private void getUserData() {

        CommonModel.getUserData(this, new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfo) {
                userInfoDto = userInfo;
                tvTelnum.setText(userInfoDto.getPhone());
                tvStatus.setText(userInfoDto.getIsSetPayPwd() == 0 ? "未设置" : "已设置");
                tvloginPasswordStatus.setText(userInfoDto.getIsSetLoginPwd() == 0 ? "未设置" : "修改");
                boolean isBindWX = userInfoDto.getIsBindWX() == 1;
                tvBindStatus.setText(isBindWX ? "已绑定" : "未绑定");
                ivBindWxArrow.setVisibility(isBindWX ?View.INVISIBLE : View.VISIBLE);
                tvBindStatus.setClickable(!isBindWX);
            }

            @Override
            public void error(int code, String msg) {

            }

        });
    }


    public static final String className = "SettingComActivity";

    @OnClick({R.id.rl_zhifu, R.id.rl_shoujihao, R.id.rl_private, R.id.rl_clear, R.id.tv_loginout,
            R.id.rl_login_password, R.id.rl_about, R.id.tv_bind_status})
    public void onViewClicked(View view) {
        if (userInfoDto == null) {
            ContextUtils.showNoLoginDialog(this);
            return;
        }

        switch (view.getId()) {

            case R.id.tv_bind_status:
                try {
                    umShareAPI = UMShareAPI.get(this);
                    umShareAPI.getPlatformInfo(SettingComActivity.this, SHARE_MEDIA.WEIXIN,
                            umAuthListener);
                } catch (Exception e) {
                    Toast.makeText(SettingComActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.rl_login_password:
                Intent intent3 = new Intent(this, UpdatePasswordComActivity.class);
                SharedpfTools.getInstance(this).put("telsetting", 4);
                startActivity(intent3);

                break;

            case R.id.rl_about:

                Intent intent4 = new Intent(this, AbountUsComActivity.class);
                startActivity(intent4);
                break;
            case R.id.rl_zhifu:
                Intent intent1 = new Intent(this, UpdatePasswordComActivity.class);
                SharedpfTools.getInstance(this).put("telsetting", 2);
                startActivity(intent1);
                break;
            case R.id.rl_shoujihao:

                if (userInfoDto.getIsBindPhone() == 1) {
                    Intent intent2 = new Intent(this, UpdatePasswordComActivity.class);
                    SharedpfTools.getInstance(this).put("telsetting", 3);
                    startActivity(intent2);
                } else {
                    Intent intentBindTel = new Intent(this, BindTelNumComActivity.class);
                    intentBindTel.putExtra(BindTelNumComActivity.COM_FROM, className);
                    startActivity(intentBindTel);
                }
                break;
            case R.id.rl_private:

                Intent intent = new Intent(this, PrivateSettingComActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_clear:
                break;

            case R.id.tv_loginout:
                SharedpfTools.getInstance(ContextUtils.getBaseApplication()).put(ConstantUtils.TOKEN, "");
                ACache.get(this).put("userInfoDto", "");
                BaseApplication baseApplication =
                        (BaseApplication) ContextUtils.getBaseApplication();
                baseApplication.clear();
                baseApplication.clearActivity();
                ACache.get(ContextUtils.getBaseApplication()).put(ConstantUtils.TAGS, "");
                ACache.get(ContextUtils.getBaseApplication()).put(ConstantUtils.TAGS_ALL, "");
                ACache.get(ContextUtils.getBaseApplication()).put(ConstantUtils.AUDIO_LIST, "");
                ACache.get(ContextUtils.getBaseApplication()).put(ConstantUtils.PICTRUE_LIST, "");
                EventBus.getDefault().post(new MessageEvent(""));
                Intent intent5 = new Intent(this, LoginComActivity.class);
                startActivity(intent5);
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
            showDefaultLoadingDialog();
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
            Log.e("onComplete openid: ", data.get("openid"));

            CommonModel.bindWX(data.get("openid"), new MyObserver<String>() {
                @Override
                public void onSuccess(BaseResponse t) {

                    if (t != null) {
                        if (t.isSuccess()) {
                            tvBindStatus.setText("已绑定");
                            ivBindWxArrow.setVisibility(View.INVISIBLE);
                            tvBindStatus.setClickable(false);
                        } else {
                            T.showShort(SettingComActivity.this, t.getMsg());
                        }
                    }

                    dismissLoadingDialog();

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
            dismissLoadingDialog();
            Toast.makeText(SettingComActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            dismissLoadingDialog();
            Toast.makeText(SettingComActivity.this, "取消了", Toast.LENGTH_LONG).show();
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

}
