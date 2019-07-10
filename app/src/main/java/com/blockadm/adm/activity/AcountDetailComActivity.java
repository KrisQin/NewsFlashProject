package com.blockadm.adm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.AcountListDtoRecordBean;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/2/15.
 */

public class AcountDetailComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_other_nickname)
    TextView tvOtherNickname;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.tv_service_charge)
    TextView serviceCcharge;

    @BindView(R.id.tv_arrival_amount)
    TextView tvArrivalAmount;

    @BindView(R.id.iv_status)
    ImageView ivStatus;

    @BindView(R.id.ll_nickname)
    LinearLayout llNickName;

    @BindView(R.id.ll_arrival_amount)
    LinearLayout llArrrivalAmount;


    @BindView(R.id.ll_service_charge)
    LinearLayout llServiceCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_acount_detail);
        ButterKnife.bind(this);
        AcountListDtoRecordBean recordsBean =
                (AcountListDtoRecordBean) getIntent().getSerializableExtra("recordsBean");

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

          /*
        *   "payType": 1,                               资金类型:  0 A点流水  1 现金流水 2 A钻流水
        "orderNumber": "454545",        订单号
        "io": 1,                                        收入支出： 0 收入 1 支出
        "icon": "http://image.blockadm.pro/sys/account/png/type_trade.png",           流水图片
        "remark": "A点兑换",                 流水标题
        "typeId": 2001,                          流水类型（暂时无用）
        "channelName": "微信",            交易方式
        "id": 14,                                     账单id
        "detailIcon": "http://image.blockadm.pro/sys/account/png/detail_withdraw_ongoing.png",
              详情图片 (交易成功，交易失败，交易中)
        "relatedNickName": "米尔丁",  对方昵称
       "relatedIsShow": "米尔丁",        是否显示对方昵称（0 否 1 是）
        "point": 520,                             金额
        "createDate": "2019-02-15 12:01:27"  交易时间
        <TextView
android:layout_width="29dp"
android:layout_height="22dp"
android:text="¥66"
android:textColor="#ffff6b00"
android:textSize="16sp"
/>
        *
        * */
        if (recordsBean.getRelatedIsShow() == 1) {
            llNickName.setVisibility(View.VISIBLE);
        } else {
            llNickName.setVisibility(View.GONE);
        }

        if (recordsBean.getPayType() == 0) {
            if (recordsBean.getIo() == 0) {
                tvPoint.setText("+" + StringUtils.string2Decimal(String.valueOf(recordsBean.getPoint()), 0));
                tvPrice.setText("¥ +" + recordsBean.getPoint() / 100);
            } else {
                tvPoint.setText("-" + StringUtils.string2Decimal(String.valueOf(recordsBean.getPoint()), 0));
                tvPrice.setText("¥ -" + recordsBean.getPoint() / 100);
            }


        } else if (recordsBean.getPayType() == 1) {
            tvPrice.setText(recordsBean.getPoint() + "");
            tvPoint.setText(StringUtils.string2Decimal(String.valueOf(recordsBean.getPoint() * 100), 0) + " A点");
        } else if (recordsBean.getPayType() == 2) {
            if (recordsBean.getIo() == 0) {
                //                tvPoint.setText("+" + recordsBean.getPoint() + " A钻");
                //                tvPrice.setText("");
                tvPrice.setText("+" + recordsBean.getPoint() + " A钻");
                tvPoint.setText("");
            } else {
                tvPrice.setText("-" + recordsBean.getPoint() + " A钻");
                tvPoint.setText("");
            }
        }

        tvOrderNum.setText(recordsBean.getOrderNumber() + "");
        tvGoodsName.setText(recordsBean.getRemark());
        tvType.setText(recordsBean.getChannelName());
        tvOtherNickname.setText(recordsBean.getRelatedNickName());
        tvTime.setText(recordsBean.getCreateDate());
        ImageUtils.loadImageView(recordsBean.getDetailIcon(), ivStatus);

        if (recordsBean.getTypeId() == 2001) {
            llArrrivalAmount.setVisibility(View.VISIBLE);
            llArrrivalAmount.setVisibility(View.VISIBLE);
            serviceCcharge.setText(recordsBean.getWithdrawCharge());
            tvArrivalAmount.setText(recordsBean.getWithdrawEnter());
        }

    }

    private String showMoney(AcountListDtoRecordBean recordsBean) {

        if (recordsBean.getPayType() == 0) {
            return recordsBean.getPoint() + " A点";
        } else if (recordsBean.getPayType() == 1) {
            return "¥ " + recordsBean.getPoint();
        } else if (recordsBean.getPayType() == 2) {
            return recordsBean.getPoint() + " A钻";
        }
        return recordsBean.getPoint() + "";
    }
}
