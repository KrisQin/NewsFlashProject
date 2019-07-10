package com.blockadm.adm.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.MainApp;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.ArechargeAdapter;
import com.blockadm.adm.adapter.TipsAdapter;
import com.blockadm.adm.dialog.PayTypeDialog;
import com.blockadm.adm.event.FinishEvent;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.alipay.AliPayUtils;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.BannerListDto;
import com.blockadm.common.bean.PayDTO;
import com.blockadm.common.bean.RechargeTypeDto;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.NoScrollListView;
import com.blockadm.common.comstomview.RecycleViewDivider;
import com.blockadm.common.comstomview.banner.BannerView;
import com.blockadm.common.config.ComConfig;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ToastUtils;
import com.blockadm.common.wxpay.MyWxPayUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/2/13.
 * 充值界面
 */

public class ArechargeComActivity extends BaseComActivity {


    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.bannerView)
    BannerView bannerView;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.nsl)
    NoScrollListView nsl;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_text)
    TextView tvText;
    private ClipboardManager myClipboard;
    private ArrayList<BannerListDto> bannerListDtos1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_recharge);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        CommonModel.listRechargeType(new MyObserver<RechargeTypeDto>() {
            @Override
            public void onSuccess(BaseResponse<RechargeTypeDto> t) {
                setDataToView(t.getData());
            }


        });
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tilte.setOnRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent(ArechargeComActivity.this,FAQactivity.class);
                startActivity(intent);*/
                Intent intent = new Intent(ArechargeComActivity.this, HtmlComActivity.class);
                intent.putExtra("url","http://app.blockadm.pro/user/page/visitor/html?html=commonProblem");
                intent.putExtra("title", "常见问题");
                startActivity(intent);
            }
        });


        bannerView.startLoop();
        bannerView.setScrollerDuration(1000);
        bannerView.setItemClick(new BannerView.OnBannerClickListener() {
            @Override
            public void onBannerItemClick(int position) {
                String  url =  bannerListDtos1.get(position).getRedirectUrl();
                if (!TextUtils.isEmpty(url)){
                    Intent intent = new Intent(ArechargeComActivity.this, HtmlComActivity.class);
                    intent.putExtra("url",url);
                    intent.putExtra("title", bannerListDtos1.get(position).getTitle());
                    startActivity(intent);
                }


            }
        });
        CommonModel.findSysBannerList(0,4 ,new MyObserver<ArrayList<BannerListDto>>() {
            @Override
            public void onSuccess(BaseResponse<ArrayList<BannerListDto>> t) {
                bannerListDtos1 = t.getData();
                bannerView.setPictrues(t.getData());
            }


        });
    }
    private RechargeTypeDto data;
    private void setDataToView(RechargeTypeDto data) {
        if (data!=null){
         this.data = data;


            rv.addItemDecoration(new RecycleViewDivider(this, GridLayoutManager.VERTICAL, 10, getResources().getColor(R.color.color_ffffff)));
            ArechargeAdapter arechargeAdapter = new ArechargeAdapter(this,data.getRechargeTypeList());
            rv.setAdapter(arechargeAdapter);
            GridLayoutManager  gridLayoutManager = new GridLayoutManager(this,2);
            rv.setLayoutManager(gridLayoutManager);

            TipsAdapter  tipsAdapter = new TipsAdapter(this,data.getExplainList());
            nsl.setAdapter(tipsAdapter);
            tvCopy.setText(data.getWxCustomService());
            tvText.setText(data.getWxCustomExplain());
            myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        }
    }
  private PayTypeDialog payTypeDialog;
    @OnClick({R.id.tv_copy,R.id.tv_now_pay})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_copy:
                if (myClipboard!=null){
                    String text = tvCopy.getText().toString();
                    ClipData  myClip = ClipData.newPlainText("text", text);
                    myClipboard.setPrimaryClip(myClip);
                    ToastUtils.showToast("已经复制到剪切板");
                }

                break;

            case R.id.tv_now_pay:

                if (payTypeDialog==null){
                    payTypeDialog = new PayTypeDialog(this);
                }
                payTypeDialog.setSexSaveListner(new PayTypeDialog.SexSaveListner() {
                    @Override
                    public void onSaveClick(final String textType,  int   type) {
                         int typeId = -1;
                        for (int i = 0; i < data.getRechargeTypeList().size(); i++) {
                            if (data.getRechargeTypeList().get(i).isCheck()){
                                typeId = data.getRechargeTypeList().get(i).getTypeId();
                                payTypeDialog.dismiss();

                            }
                        }
                        Pay(typeId,type);
                    }
                });
                payTypeDialog.show();
                break;
        }

    }
    private void Pay( int typeId, final int type ) {
        CommonModel.purchase(type, ComConfig.Buy_Type_Id_APoint_Pay, typeId, new MyObserver<PayDTO>() {
            @Override
            public void onSuccess(BaseResponse <PayDTO> t) {


                if (t.getCode() == 0) {
                    MainApp.setOrderNum(t.getData().getOutTradeNo());
                    switch (type){

                        //支付宝
                        case 0:
                            AliPayUtils aliPayUtils = new AliPayUtils(ArechargeComActivity.this);
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
                            MyWxPayUtils.sendReq(t.getData().getWxPayParam(), ArechargeComActivity.this);
                            break;
                    }

                } else {
                    Toast.makeText(ArechargeComActivity.this, t.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }


        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void thisFinish(FinishEvent finishEvent) {
        finish();
    }




    private void setEnd() {
        EventBus.getDefault().post(new UserDataEvent());
        Intent  intent = new Intent(this, PaySuccesComActivity.class);
        startActivity(intent);
        finish();
    }
}
