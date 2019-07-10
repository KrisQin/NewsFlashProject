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
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.alipay.AliPayUtils;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.LessonsSpecialColumnDto;
import com.blockadm.common.bean.PayDTO;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.PayPasswordDialog;
import com.blockadm.common.comstomview.RoundImageView;
import com.blockadm.common.config.ComConfig;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.wxpay.MyWxPayUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/1/29.
 */

public class BuyDetailComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.iv_banner)
    RoundImageView ivBanner;
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_preferential_price)
    TextView tvPreferentialPrice;
    @BindView(R.id.rl_huiyuan)
    RelativeLayout rlHuiyuan;


    @BindView(R.id.tv_actual_amount)
    TextView tvActualAmount;
    @BindView(R.id.tv_now_pay)
    TextView tvNowPay;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.v_line)
    View view;
    @BindView(R.id.tv_alipay)
    TextView tvAlipay;
    @BindView(R.id.tv_wetchat)
    TextView tvWetchat;
    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.tv_go_recharge)
    TextView toRecharge;
    @BindView(R.id.iv_check)
    ImageView ivCheck;

    @BindView(R.id.tv_nickName)
    TextView tvNickName;
    private LessonsSpecialColumnDto lessonsSpecialColumnDto;
    private UserInfoDto userInfoDto;

    public static final String LESSONS_DTO = "lessonsDto";
    public static final String LIVE_LESSONS = "LIVE_LESSONS";

    private boolean isLiveLessonBuy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_buy_detail);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);

        isLiveLessonBuy = getIntent().getBooleanExtra(LIVE_LESSONS, false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        lessonsSpecialColumnDto =
                (LessonsSpecialColumnDto) getIntent().getSerializableExtra(LESSONS_DTO);


        getUserData();

        initView();
    }

    private void getUserData() {
        CommonModel.getUserData(this,new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfo) {
                userInfoDto = userInfo;
                showData();
            }

            @Override
            public void error(int code, String msg) {

            }

        });
    }

    private void showData() {
        tvNickName.setText(userInfoDto.getNickName());
        if (userInfoDto.getVipState() == 0) {
            rlHuiyuan.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            if (lessonsSpecialColumnDto != null) {
                double moneyPoint = lessonsSpecialColumnDto.getMoney() * 100;
                if (moneyPoint > userInfoDto.getPoint()) {
                    tvPoint.setText(userInfoDto.getPoint() + "A点  " + "(余额不足)");
                    toRecharge.setVisibility(View.VISIBLE);
                    ivCheck.setImageResource(R.mipmap.ic_group2);
                } else {
                    tvPoint.setText(StringUtils.string2Decimal(String.valueOf(moneyPoint)
                            , 0) + "A点");
                }
                tvActualAmount.setText(StringUtils.getUnitAmount()+lessonsSpecialColumnDto.getMoney());

            }
            rlHuiyuan.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        } else {


            if (lessonsSpecialColumnDto != null) {
                double moneyPoint = lessonsSpecialColumnDto.getVipMoney() * 100;
                if (moneyPoint > userInfoDto.getPoint()) {
                    tvPoint.setText(userInfoDto.getPoint() + "A点  " + "(余额不足)");
                } else {
                    tvPoint.setText(userInfoDto.getPoint() + "A点");
                }
                tvActualAmount.setText("¥" + lessonsSpecialColumnDto.getVipMoney());
            }


        }
    }

    private void initView() {
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (lessonsSpecialColumnDto != null) {
            ImageUtils.loadImageView(lessonsSpecialColumnDto.getCoverImgs(), ivBanner);
            tvTitle.setText(lessonsSpecialColumnDto.getTitle());
            tvTotalPrice.setText(StringUtils.getUnitAmount()+lessonsSpecialColumnDto.getMoney());
            tvPreferentialPrice.setText(StringUtils.getUnitAmount()+lessonsSpecialColumnDto.getVipMoney());
            tvAuthor.setText(lessonsSpecialColumnDto.getNickName());
        }
    }

    private void purchase() {
        if (lessonsSpecialColumnDto != null) {

            int typeId;
            if (isLiveLessonBuy) {
                typeId = ComConfig.Buy_Type_Id_Live_Lessons_Pay;
            } else {
                typeId = ComConfig.Buy_Type_Id_Exclusive_Column;
            }

            showDefaultLoadingDialog();
            CommonModel.purchase(type, typeId, lessonsSpecialColumnDto.getId(), myObserver);

        }

    }

    private int type = 0;
    private int Go_REECHAR = 2341;
    private boolean isRecharge = false;

    @OnClick({R.id.rl_huiyuan, R.id.tv_now_pay, R.id.tv_alipay, R.id.tv_wetchat,
            R.id.tv_go_recharge, R.id.iv_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_huiyuan:
                Intent intent = new Intent(this, PayOrderComActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_now_pay:
                if (type == 3) {
                    if (userInfoDto.getIsSetPayPwd() == 0) {
                        final MyComstomDialogView myComstomDialogView =
                                new MyComstomDialogView(BuyDetailComActivity.this);
                        myComstomDialogView.setTvTitle("为了你的账户安全，购买前需要设置支付密码", View.VISIBLE)
                                .setChildMsg("", View.GONE)
                                .setChildMsg2("", View.GONE)
                                .setConcelMsg("", View.GONE)
                                .setRootBg(R.mipmap.boxbg2)
                                .setConfirmMsg("确定", View.VISIBLE)
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
                                Intent intent1 = new Intent(BuyDetailComActivity.this,
                                        SettingComActivity.class);
                                startActivity(intent1);
                            }
                        });
                        myComstomDialogView.show();
                    } else {
                        final PayPasswordDialog dialog =
                                new PayPasswordDialog(BuyDetailComActivity.this, R.style.mydialog);
                        dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
                            @Override
                            public void doConfirm(String password) {
                                dialog.dismiss();
                                CommonModel.checkPayPassword(password, new MyObserver<String>() {
                                    @Override
                                    public void onSuccess(BaseResponse<String> t) {
                                        if (t.getCode() == 0) {

                                            if (lessonsSpecialColumnDto != null) {
                                                purchase();
                                            }
                                        } else {
                                            Toast.makeText(BuyDetailComActivity.this, t.getMsg(),
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }


                                });
                            }
                        });
                        dialog.show();
                    }
                } else if (lessonsSpecialColumnDto != null) {
                    purchase();
                }
                break;
            case R.id.tv_go_recharge:
            case R.id.iv_check:
                if (toRecharge.getVisibility() == View.VISIBLE) {
                    isRecharge = true;
                    Intent intent1 = new Intent(this, ArechargeComActivity.class);
                    startActivityForResult(intent1, Go_REECHAR);
                } else {
                    type = 3;
                    ivCheck.setImageResource(R.mipmap.checked);


                    Drawable rightDrawableWetchat = getResources().getDrawable(R.mipmap.ic_wechat);
                    rightDrawableWetchat.setBounds(0, 0, rightDrawableWetchat.getMinimumWidth(),
                            rightDrawableWetchat.getMinimumHeight());

                    Drawable wetChat = getResources().getDrawable(R.mipmap.suggest_unselect);
                    wetChat.setBounds(0, 0, wetChat.getMinimumWidth(), wetChat.getMinimumHeight());
                    tvWetchat.setCompoundDrawables(rightDrawableWetchat, null, wetChat, null);

                    Drawable rightDrawableAlipay2 = getResources().getDrawable(R.mipmap.ic_alipay);
                    rightDrawableAlipay2.setBounds(0, 0, rightDrawableAlipay2.getMinimumWidth(),
                            rightDrawableAlipay2.getMinimumHeight());


                    Drawable wetChat1 = getResources().getDrawable(R.mipmap.suggest_unselect);
                    wetChat1.setBounds(0, 0, wetChat1.getMinimumWidth(),
                            wetChat1.getMinimumHeight());
                    tvAlipay.setCompoundDrawables(rightDrawableAlipay2, null, wetChat1, null);


                }

                break;
            case R.id.tv_alipay:
                type = 0;
                Drawable rightDrawable = getResources().getDrawable(R.mipmap.checked);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(),
                        rightDrawable.getMinimumHeight());


                Drawable rightDrawableAlipay1 = getResources().getDrawable(R.mipmap.ic_alipay);
                rightDrawableAlipay1.setBounds(0, 0, rightDrawableAlipay1.getMinimumWidth(),
                        rightDrawableAlipay1.getMinimumHeight());

                tvAlipay.setCompoundDrawables(rightDrawableAlipay1, null, rightDrawable, null);

                Drawable rightDrawableWetchat = getResources().getDrawable(R.mipmap.ic_wechat);
                rightDrawableWetchat.setBounds(0, 0, rightDrawableWetchat.getMinimumWidth(),
                        rightDrawableWetchat.getMinimumHeight());

                Drawable wetChat = getResources().getDrawable(R.mipmap.suggest_unselect);
                wetChat.setBounds(0, 0, wetChat.getMinimumWidth(), wetChat.getMinimumHeight());
                tvWetchat.setCompoundDrawables(rightDrawableWetchat, null, wetChat, null);

                if (toRecharge.getVisibility() != View.VISIBLE) {
                    ivCheck.setImageResource(R.mipmap.suggest_unselect);
                }

                break;
            case R.id.tv_wetchat:
                type = 1;
                Drawable rightDrawable1 = getResources().getDrawable(R.mipmap.checked);
                rightDrawable1.setBounds(0, 0, rightDrawable1.getMinimumWidth(),
                        rightDrawable1.getMinimumHeight());


                Drawable rightDrawableWetchat3 = getResources().getDrawable(R.mipmap.ic_wechat);
                rightDrawableWetchat3.setBounds(0, 0, rightDrawableWetchat3.getMinimumWidth(),
                        rightDrawableWetchat3.getMinimumHeight());

                tvWetchat.setCompoundDrawables(rightDrawableWetchat3, null, rightDrawable1, null);

                Drawable rightDrawableAlipay2 = getResources().getDrawable(R.mipmap.ic_alipay);
                rightDrawableAlipay2.setBounds(0, 0, rightDrawableAlipay2.getMinimumWidth(),
                        rightDrawableAlipay2.getMinimumHeight());


                Drawable wetChat1 = getResources().getDrawable(R.mipmap.suggest_unselect);
                wetChat1.setBounds(0, 0, wetChat1.getMinimumWidth(), wetChat1.getMinimumHeight());
                tvAlipay.setCompoundDrawables(rightDrawableAlipay2, null, wetChat1, null);

                if (toRecharge.getVisibility() != View.VISIBLE) {
                    ivCheck.setImageResource(R.mipmap.suggest_unselect);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getUserData();
    }

    @Override
    protected void onDestroy() {
        dismissLoadingDialog();
        super.onDestroy();
    }

    private MyObserver myObserver = new MyObserver<PayDTO>() {
        @Override
        public void onSuccess(final BaseResponse<PayDTO> t) {

            dismissLoadingDialog();
            if (t.getCode() == 0) {
                MainApp.setOrderNum(t.getData().getOutTradeNo());
                switch (type) {
                    case 0:
                        AliPayUtils aliPayUtils = new AliPayUtils(BuyDetailComActivity.this);
                        aliPayUtils.payByAli(t.getData().getZfbPayParam(),
                                new AliPayUtils.OnResponListener() {
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
                        MyWxPayUtils.sendReq(t.getData().getWxPayParam(),
                                BuyDetailComActivity.this);
                        break;
                    case 3:
                        setEnd();
                        break;
                }

            } else {
                Toast.makeText(BuyDetailComActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void thisFinish(FinishEvent finishEvent) {
        if (!isRecharge) {
            finish();
        }

    }

    private void setEnd() {
        Intent intent = new Intent(this, PaySuccesComActivity.class);
        startActivity(intent);
        finish();

    }
}
