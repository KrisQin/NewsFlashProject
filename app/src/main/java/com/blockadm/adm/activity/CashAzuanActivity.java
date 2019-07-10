package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.dialog.MyComstomDialogView;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.HBPayPasswordDialog;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kris on 2019/6/17
 *
 * @Describe TODO { A点转A钻 界面 }
 */
public class CashAzuanActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.et_acount)
    EditText etAcount;
    @BindView(R.id.tv_can_change)
    TextView tvCanChange;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.tv_sxf)
    TextView tvSxf;

    private UserInfoDto userInfoDto;
    private int mMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_azuan);
        ButterKnife.bind(this);

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etAcount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (userInfoDto == null) {
                    ContextUtils.showNoLoginDialog(CashAzuanActivity.this);
                    return;
                }

                if (s.length() > 0) {
                    tvSxf.setVisibility(View.VISIBLE);

                    if (userInfoDto.getLevelId() >= 1) {
                        tvCanChange.setVisibility(View.VISIBLE);
                    }else {
                        tvCanChange.setVisibility(View.GONE);
                        tvSxf.setText("还未达到白银等级，不能进行兑换！");
                    }

                }else {
                    tvSxf.setVisibility(View.GONE);
                    tvCanChange.setVisibility(View.VISIBLE);
                    tvCanChange.setText("可兑换" + StringUtils.amountNoZero(userInfoDto.getRewardPoint()+"") + "A点");
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserData();
    }

    private void getUserData() {
        CommonModel.getUserData(this,new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfo) {
                userInfoDto = userInfo;
                tvSxf.setText("手续费"+StringUtils.changeToPercentage(userInfoDto.getRewardPointToDiamondRate()));
                ACache.get(CashAzuanActivity.this).put("userInfoDto", userInfoDto);
                tvCanChange.setText("可兑换" + StringUtils.amountNoZero(userInfoDto.getRewardPoint()+"") + "A点");
            }

            @Override
            public void error(int code, String msg) {

            }

        });
    }

    @OnClick(R.id.submit)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (userInfoDto == null) {
                    ContextUtils.showNoLoginDialog(CashAzuanActivity.this);
                    return;
                }

                if (userInfoDto.getLevelId() < 1) {
                    return;
                }

                String moneyStr = etAcount.getText().toString().trim().replaceAll(" ","");
                
                if (StringUtils.isEmpty(moneyStr)) {
                    T.showShort(this,"请输入兑换金额");
                    return;
                }

                mMoney = Integer.valueOf(moneyStr);

                checkPwd();


                break;
        }
    }
    
    private void change() {
        showDefaultLoadingDialog();
        CommonModel.balanceExchange(0, mMoney, new MyObserver() {

            @Override
            public void onSuccess(BaseResponse t) {
                dismissLoadingDialog();
                if (t != null) {
                    if (t.isSuccess()) {
                        Intent intent = new Intent(CashAzuanActivity.this,ExchangeResultActivity.class);
                        intent.putExtra(ExchangeResultActivity.COME_FROM_ACTIVITY,ExchangeResultActivity.Cash_Azuan_Activity);
                        startActivity(intent);
                        CashAzuanActivity.this.finish();
                    }
                    else {
                        T.showShort(CashAzuanActivity.this,t.getMsg());
                    }
                }
            }
        });

    }

    private void checkPwd() {
        if (userInfoDto.getIsSetPayPwd() == 0) {
            final MyComstomDialogView myComstomDialogView =
                    new MyComstomDialogView(this);
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
                    Intent intent1 = new Intent(CashAzuanActivity.this,
                            SettingComActivity.class);
                    startActivity(intent1);
                }
            });
            myComstomDialogView.show();
        } else {

            String rate = userInfoDto.getRewardPointToDiamondExchangeRate();
            String mAzuanMoney = "";
            if (StringUtils.isNotEmpty(rate)) {
                double rateM = Double.parseDouble(rate);
                mAzuanMoney = StringUtils.string2Decimal(String.valueOf(rateM * mMoney),4);
            }

            final HBPayPasswordDialog dialog = new HBPayPasswordDialog(CashAzuanActivity.this,
                    R.style.mydialog);
            dialog.setDialogClick(new HBPayPasswordDialog.DialogClick() {
                @Override
                public void doConfirm(String password) {
                    dialog.dismiss();
                    showDefaultLoadingDialog();
                    CommonModel.checkPayPassword(password, new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse<String> t) {
                            if (t.getCode() == 0) {

                                change();
                            } else {
                                dismissLoadingDialog();
                                Toast.makeText(CashAzuanActivity.this, t.getMsg(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            dialog.setMoney("可兑换"+mAzuanMoney+"A钻","");
            dialog.setTextSize(15);
            dialog.show();
        }
    }

}
