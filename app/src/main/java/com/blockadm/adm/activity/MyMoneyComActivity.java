package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/2/14.
 */

public class MyMoneyComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_recharge)
    TextView tvRecharge;
    @BindView(R.id.tv_withdraw)
    TextView tvWithdraw;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_diamond)
    TextView tvDiamond;
    @BindView(R.id.tv_bill)
    TextView tvBill;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_diamond_recharge)
    TextView tvDiamondRecharge;

    @BindView(R.id.tv_manager)
    TextView tvManager;
    @BindView(R.id.tv_adian)
    TextView tvAdian;
    @BindView(R.id.tv_exchange_azuan)
    TextView tvExchangeAzuan;
    @BindView(R.id.tv_cash_adian)
    TextView tvCashAdian;
    @BindView(R.id.tv_exchange)
    TextView tvExchange;//兑换

    private UserInfoDto userInfoDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_money);
        ButterKnife.bind(this);


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
                initView();
            }

            @Override
            public void error(int code, String msg) {

            }

        });
    }


    private void initView() {
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvMoney.setText(StringUtils.amountNoZero(userInfoDto.getPoint() + "") + "   A点");
        tvDiamond.setText(StringUtils.string2Decimal(userInfoDto.getDiamond() + "", 4) + "   A钻");
        tvAdian.setText(StringUtils.amountNoZero(userInfoDto.getRewardPoint() + "") + "   A点");
    }

    @OnClick({R.id.tv_recharge, R.id.tv_withdraw, R.id.tv_bill, R.id.tv_income, R.id.tv_manager,
            R.id.tv_diamond_recharge, R.id.tv_exchange_azuan, R.id.tv_cash_adian,R.id.tv_exchange})
    public void onViewClicked(View view) {
        if (userInfoDto == null) {
            ContextUtils.showNoLoginDialog(this);
            return;
        }

        switch (view.getId()) {
            case R.id.tv_recharge:
                Intent intent = new Intent(this, ArechargeComActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_diamond_recharge:
                Toast.makeText(this, "暂不支持", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_withdraw:
                Intent intent4 = new Intent(this, WithdrawComActivity.class);
                startActivity(intent4);
                break;
            case R.id.tv_bill:
                Intent intent1 = new Intent(this, AcountListComActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_income:
                Intent intent2 = new Intent(this, MyInComeActivty.class);
                startActivity(intent2);
                break;

            case R.id.tv_manager:
                Intent intent3 = new Intent(this, CashManagerActivty.class);
                startActivity(intent3);
                break;

            case R.id.tv_exchange_azuan: //兑换A钻
                Intent intent5 = new Intent(this, CashAzuanActivity.class);
                startActivity(intent5);
                break;
            case R.id.tv_cash_adian: //转入现金A点
                Intent intent6 = new Intent(this, CashAdianActivity.class);
                startActivity(intent6);
                break;
            case R.id.tv_exchange://兑换
                Intent intentExchange = new Intent(this, CashActivity.class);
                startActivity(intentExchange);
                break;
        }
    }

}
