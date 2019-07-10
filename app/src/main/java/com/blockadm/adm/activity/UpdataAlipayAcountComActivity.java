package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.blockadm.adm.R;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.params.WithdrawAccountParams;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.CheckEmptyTextView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.RegularuUtil;
import com.blockadm.common.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/2/20.
 */

public class UpdataAlipayAcountComActivity extends BaseComActivity {


    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.et_name)
    AppCompatEditText etName;
    @BindView(R.id.et_pay_acount)
    AppCompatEditText etPayAcount;
    @BindView(R.id.add_alipay)
    CheckEmptyTextView addAlipay;
    private String name;
    private String acount;
    private UserInfoDto userInfoDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_update_alipay_acount);
        ButterKnife.bind(this);
        addAlipay.setCheckEmptyEditTexts(etName,etPayAcount);
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toInmoney();
            }
        });
        userInfoDto = (UserInfoDto) ACache.get(UpdataAlipayAcountComActivity.this).getAsObject("userInfoDto");
        etName.setText(userInfoDto.getWithdrawName());
        etPayAcount.setText(userInfoDto.getWithdrawAccountNumber());
    }

    @OnClick(R.id.add_alipay)
    public void onViewClicked() {
        name = etName.getText().toString();
        acount = etPayAcount.getText().toString();
        if ( RegularuUtil.isEmail(acount)|| RegularuUtil.telNumberRegex(acount,this)){
            WithdrawAccountParams withdrawAccountParams  = new WithdrawAccountParams();
            withdrawAccountParams.setName(name);
            withdrawAccountParams.setAccountNumber(acount);
            withdrawAccountParams.setTypeId(0+"");
            CommonModel.addOrUpdateWithdrawAccount(new MyObserver<String> () {
                @Override
                public void onSuccess(BaseResponse<String> t) {
                    ToastUtils.showToast(t.getMsg());
                      if (t.getCode()==0){

                          EventBus.getDefault().post(new UserDataEvent());
                         toInmoney();
                      }
                }


            }, GsonUtil.GsonString(withdrawAccountParams));

        }else{
            ToastUtils.showToast("请填写正确的账号");
        }
    }


    private void toInmoney() {
        Intent intent = new Intent(UpdataAlipayAcountComActivity.this, MyMoneyComActivity.class);
        startActivity(intent);
    }
}
