package com.blockadm.adm.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.MainApp;
import com.blockadm.adm.R;
import com.blockadm.adm.dialog.MyComstomDialogView;
import com.blockadm.adm.event.FinishEvent;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.alipay.AliPayUtils;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.PayDTO;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.PayPasswordDialog;
import com.blockadm.common.config.ComConfig;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.wxpay.MyWxPayUtils;
import com.blockadm.common.wxpay.WXPayUtils;



import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/2/28.
 */

public class PayOrderComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.v)
    View v;
    @BindView(R.id.tv)
    TextView tv;

    @BindView(R.id.tv_alipay)
    TextView tvAlipay;
    @BindView(R.id.tv_wetchat)
    TextView tvWetchat;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_point)
    TextView tvPoint;

    @BindView(R.id.tv_go_recharge)
    TextView toRecharge;
    @BindView(R.id.iv_check)
    ImageView ivCheck;
    @BindView(R.id.rl_dian)
    RelativeLayout rlDian;
    @BindView(R.id.v3)
    View v3;
    private UserInfoDto userInfoDto;
    private int type = -1;
    private int point;
    private WXPayUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay_order);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initView();
    }
    private void initData(){
        userInfoDto = (UserInfoDto) ACache.get(this).getAsObject("userInfoDto");
        point = userInfoDto.getPoint();
        if (point >29800){
            tvPoint.setText(point +"A点");
            toRecharge.setVisibility(View.GONE);
            ivCheck.setImageResource(R.mipmap.suggest_unselect);
        }else{
            tvPoint.setText("余额不足");
            toRecharge.setVisibility(View.VISIBLE);
            ivCheck.setImageResource(R.mipmap.ic_group2);
        }
    }

    private void initView() {
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        onViewClicked(tvAlipay);
    }
    private  int Go_REECHAR = 2345;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==Go_REECHAR){
            CommonModel.getUserData(PayOrderComActivity.this, new GetUserCallBack() {
                @Override
                public void backUserInfo(UserInfoDto userInfo) {
                    userInfoDto = userInfo;
                    initData();
                }

                @Override
                public void error(int code, String msg) {

                }

            });

        }
    }
  private boolean isRecharge =false;
    @OnClick({R.id.iv_check, R.id.tv_alipay, R.id.tv_wetchat, R.id.tv_pay,R.id.tv_go_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_check:
            case  R.id.tv_go_recharge:
                if (point >29800){
                    type = 3;
                    ivCheck.setImageResource(R.mipmap.checked);

                    Drawable wetChat2 = getResources().getDrawable(R.mipmap.suggest_unselect);
                    wetChat2.setBounds(0, 0, wetChat2.getMinimumWidth(), wetChat2.getMinimumHeight());

                    Drawable rightDrawableAlipay3 = getResources().getDrawable(R.mipmap.ic_alipay);
                    rightDrawableAlipay3.setBounds(0, 0, rightDrawableAlipay3.getMinimumWidth(), rightDrawableAlipay3.getMinimumHeight());
                    tvAlipay.setCompoundDrawables(rightDrawableAlipay3, null, wetChat2, null);

                    Drawable rightDrawableWetchat3 = getResources().getDrawable(R.mipmap.ic_wechat);
                    rightDrawableWetchat3.setBounds(0, 0, rightDrawableWetchat3.getMinimumWidth(), rightDrawableWetchat3.getMinimumHeight());
                    tvWetchat.setCompoundDrawables(rightDrawableWetchat3, null, wetChat2, null);
                }else{
                    isRecharge = true;
                    Intent intent = new Intent(PayOrderComActivity.this, ArechargeComActivity.class);
                    startActivityForResult(intent,Go_REECHAR);
                }

                break;
            case R.id.tv_alipay:
                type = 0;
                Drawable rightDrawable = getResources().getDrawable(R.mipmap.checked);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());

                Drawable rightDrawableAlipay = getResources().getDrawable(R.mipmap.ic_alipay);
                rightDrawableAlipay.setBounds(0, 0, rightDrawableAlipay.getMinimumWidth(), rightDrawableAlipay.getMinimumHeight());

                tvAlipay.setCompoundDrawables(rightDrawableAlipay, null, rightDrawable, null);


                Drawable wetChat = getResources().getDrawable(R.mipmap.suggest_unselect);
                wetChat.setBounds(0, 0, wetChat.getMinimumWidth(), wetChat.getMinimumHeight());

                Drawable rightDrawableWetchat = getResources().getDrawable(R.mipmap.ic_wechat);
                rightDrawableWetchat.setBounds(0, 0, rightDrawableWetchat.getMinimumWidth(), rightDrawableWetchat.getMinimumHeight());
                tvWetchat.setCompoundDrawables(rightDrawableWetchat, null, wetChat, null);

                ivCheck.setImageResource(R.mipmap.suggest_unselect);
                initData();
                break;
            case R.id.tv_wetchat:
                type = 1;
                Drawable rightDrawable1 = getResources().getDrawable(R.mipmap.checked);
                rightDrawable1.setBounds(0, 0, rightDrawable1.getMinimumWidth(), rightDrawable1.getMinimumHeight());


                Drawable rightDrawableWetchat2 = getResources().getDrawable(R.mipmap.ic_wechat);
                rightDrawableWetchat2.setBounds(0, 0, rightDrawableWetchat2.getMinimumWidth(), rightDrawableWetchat2.getMinimumHeight());
                tvWetchat.setCompoundDrawables(rightDrawableWetchat2, null, rightDrawable1, null);


                Drawable wetChat1 = getResources().getDrawable(R.mipmap.suggest_unselect);
                wetChat1.setBounds(0, 0, wetChat1.getMinimumWidth(), wetChat1.getMinimumHeight());

                Drawable rightDrawableAlipay1 = getResources().getDrawable(R.mipmap.ic_alipay);
                rightDrawableAlipay1.setBounds(0, 0, rightDrawableAlipay1.getMinimumWidth(), rightDrawableAlipay1.getMinimumHeight());
                tvAlipay.setCompoundDrawables(rightDrawableAlipay1, null, wetChat1, null);

                ivCheck.setImageResource(R.mipmap.suggest_unselect);
                break;
            case R.id.tv_pay:



                if (type==3){
                    if (userInfoDto.getIsSetPayPwd()==0){
                        final MyComstomDialogView myComstomDialogView = new MyComstomDialogView(PayOrderComActivity.this);
                        myComstomDialogView.setTvTitle("为了你的账户安全，购买前需要设置支付密码",View.VISIBLE)
                                .setChildMsg("",View.GONE)
                                .setChildMsg2("",View.GONE)
                                .setConcelMsg("",View.GONE)
                                .setRootBg(R.mipmap.boxbg2)
                                .setConfirmMsg("确定",View.VISIBLE)
                                .setConfirmSize(6)
                                .setCancelLisenner(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        myComstomDialogView.dismiss();

                                    }
                                }).setConfirmLisenner(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myComstomDialogView.dismiss();
                                Intent intent1 = new Intent(PayOrderComActivity.this, SettingComActivity.class);
                                startActivity(intent1);
                            }
                        });
                        myComstomDialogView.show();
                    }else{
                        final PayPasswordDialog dialog=new PayPasswordDialog(PayOrderComActivity.this,R.style.mydialog);
                        dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
                            @Override
                            public void doConfirm(String password) {
                                dialog.dismiss();
                                CommonModel.checkPayPassword(password, new MyObserver <String>() {
                                    @Override
                                    public void onSuccess(BaseResponse <String> t) {
                                        if (t.getCode()==0){
                                            purchase();

                                        }else{
                                            Toast.makeText(PayOrderComActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                                        }

                                    }


                                });
                            }
                        });
                        dialog.show();
                    }

                }else{
                    purchase();
                }

                break;
        }
    }

    private void purchase() {
        CommonModel.purchase(type, ComConfig.Buy_Type_Id_VIP_Pay, 0,myObserver);

    }


    private  MyObserver   myObserver =new MyObserver<PayDTO>() {
        @Override
        public void onSuccess(final BaseResponse<PayDTO> t) {

            if (t.getCode() == 0) {
                MainApp.setOrderNum(t.getData().getOutTradeNo());
                switch (type) {
                    case 0:
                        AliPayUtils aliPayUtils = new AliPayUtils(PayOrderComActivity.this);
                        aliPayUtils.payByAli(t.getData().getZfbPayParam(), new AliPayUtils.OnResponListener() {
                            @Override
                            public void onRespon(String respon) {

                            }

                            @Override
                            public void onSucceed() {
                                setEnd();
                            }
                        });
                        break;

                    case 1:
                        MyWxPayUtils.sendReq(t.getData().getWxPayParam(), PayOrderComActivity.this);
                        break;
                    case 3:
                        setEnd();
                        break;
                }

            } else {
                Toast.makeText(PayOrderComActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void thisFinish(FinishEvent finishEvent) {
        if (!isRecharge){
            finish();
        }

    }

    private void setEnd() {
        EventBus.getDefault().post(new UserDataEvent());
        Intent  intent = new Intent(this, PaySuccesComActivity.class);
        startActivity(intent);
        finish();
    }
}
