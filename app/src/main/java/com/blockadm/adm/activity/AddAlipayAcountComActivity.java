package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.params.WithdrawAccountParams;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.CheckEmptyTextView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
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

public class AddAlipayAcountComActivity extends BaseComActivity {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_alipay_acount);
        ButterKnife.bind(this);
        addAlipay.setCheckEmptyEditTexts(etName,etPayAcount);
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.add_alipay)
    public void onViewClicked() {
        name = etName.getText().toString().trim();
        acount = etPayAcount.getText().toString().trim();
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
                          Intent intent = new Intent(AddAlipayAcountComActivity.this, UpdataAlipayAcountComActivity.class);
                          startActivity(intent);
                      }
                }


            }, GsonUtil.GsonString(withdrawAccountParams));

        }else{
            Toast.makeText(this,"请填写正确的账号",Toast.LENGTH_SHORT).show();
        }
    }
}
