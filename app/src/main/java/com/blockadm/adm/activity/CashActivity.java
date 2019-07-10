package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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
import com.blockadm.common.comstomview.PayPasswordDialog;
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
 * @Describe TODO { A钻提现 界面 }
 */
public class CashActivity extends BaseComActivity {

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
    @BindView(R.id.tv_money_limit)
    TextView tvMoneyLimit;
    @BindView(R.id.et_address)
    EditText etAddress;

    private UserInfoDto userInfoDto;
    private double mMoney;
    private String mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        ButterKnife.bind(this);

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etAcount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        //只能输入数字和小数点

        etAcount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    tvSxf.setVisibility(View.VISIBLE);
                } else {
                    tvSxf.setVisibility(View.GONE);
                }

                tvMoneyLimit.setVisibility(View.GONE);
                tvCanChange.setVisibility(View.VISIBLE);
            }
        });
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
                tvSxf.setText("手续费" + StringUtils.changeToPercentage(userInfoDto.getDiamondWithdrawFeeRate()));
                ACache.get(CashActivity.this).put("userInfoDto", userInfoDto);
                tvCanChange.setText("可兑换" + StringUtils.string2Decimal(userInfoDto.getDiamond() +"",4) + "A钻");
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
                    ContextUtils.showNoLoginDialog(CashActivity.this);
                    return;
                }

                String moneyStr = etAcount.getText().toString().trim().replaceAll(" ", "");
                mAddress = etAddress.getText().toString().trim().replaceAll(" ", "");

                if (StringUtils.isEmpty(moneyStr)) {
                    T.showShort(this, "请输入兑换金额");
                    return;
                }

                if (moneyStr.startsWith(".") || moneyStr.split(".").length > 2) {
                    T.showShort(this, "请输入正确的兑换金额");
                    return;
                }

                mMoney = Double.valueOf(moneyStr);

                if (mMoney < 1000) {
                    tvMoneyLimit.setVisibility(View.VISIBLE);
                    tvCanChange.setVisibility(View.GONE);
                    tvSxf.setVisibility(View.GONE);
                    return;
                }else {
                    tvMoneyLimit.setVisibility(View.GONE);
                    tvCanChange.setVisibility(View.VISIBLE);
                    tvSxf.setVisibility(View.VISIBLE);
                }

                if (StringUtils.isEmpty(mAddress)) {
                    T.showShort(this, "请输入兑换地址");
                    return;
                }

                checkPwd();

                break;
        }
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
                    Intent intent1 = new Intent(CashActivity.this,
                            SettingComActivity.class);
                    startActivity(intent1);
                }
            });
            myComstomDialogView.show();
        } else {
            final PayPasswordDialog dialog = new PayPasswordDialog(CashActivity.this,
                    R.style.mydialog);
            dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
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
                                Toast.makeText(CashActivity.this, t.getMsg(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }


                    });
                }
            });

            dialog.show();
        }
    }


    private void change() {
        showDefaultLoadingDialog();
        CommonModel.withdrawDiamond(mMoney, mAddress,new MyObserver() {

            @Override
            public void onSuccess(BaseResponse t) {
                dismissLoadingDialog();
                if (t != null) {
                    if (t.isSuccess()) {
                        Intent intent = new Intent(CashActivity.this, ExchangeResultActivity.class);
                        intent.putExtra(ExchangeResultActivity.COME_FROM_ACTIVITY,ExchangeResultActivity.Cash_Activity);
                        startActivity(intent);
                        CashActivity.this.finish();
                    } else {
                        T.showShort(CashActivity.this, t.getMsg());
                    }
                }
            }
        });
    }
}
