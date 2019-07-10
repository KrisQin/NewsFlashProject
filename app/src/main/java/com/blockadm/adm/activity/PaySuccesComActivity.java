package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blockadm.adm.MainApp;
import com.blockadm.adm.R;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.AcountListDtoRecordBean;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/3/4.
 */

public class PaySuccesComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.tv_see_order)
    TextView tvSeeOrder;
  private AcountListDtoRecordBean  acountListDtoRecordBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay_success);
        ButterKnife.bind(this);
        EventBus.getDefault().post(new UserDataEvent());
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       String otderNum =  MainApp.getOrderNum();
        CommonModel.findUserAccountByOutTradeNo(otderNum, new MyObserver <AcountListDtoRecordBean>() {
            @Override
            public void onSuccess(BaseResponse<AcountListDtoRecordBean> t) {
                if (t.getCode()==0){
                    acountListDtoRecordBean = t.getData();
                }else{
                    showToast(PaySuccesComActivity.this,t.getMsg());
                }
            }


        });
    }

    @OnClick(R.id.tv_see_order)
    public void onViewClicked() {
        if (acountListDtoRecordBean!=null){
            Intent intent = new Intent(this, AcountDetailComActivity.class);
            intent.putExtra("recordsBean",acountListDtoRecordBean);
            startActivity(intent);
            finish();
        }

    }
}
