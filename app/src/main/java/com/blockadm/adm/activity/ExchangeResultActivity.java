package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kris on 2019/6/17
 *
 * @Describe TODO { A点和A钻兑换结果界面 }
 */
public class ExchangeResultActivity extends BaseComActivity {

    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.submit)
    Button submit;

    public static final String Cash_Adian_Activity = "Cash_Adian_Activity"; //A点转现金A点
    public static final String Cash_Azuan_Activity = "Cash_Azuan_Activity"; //A点转A钻
    public static final String Cash_Activity = "Cash_Activity"; //A钻提现

    public static final String COME_FROM_ACTIVITY = "COME_FROM_ACTIVITY";
    @BindView(R.id.line_white)
    View lineWhite;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tilte)
    BaseTitleBar tilte;

    private String mComeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_result);
        ButterKnife.bind(this);

        mComeActivity = getIntent().getStringExtra(COME_FROM_ACTIVITY);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitClick();

            }
        });

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExchangeResultActivity.this.finish();
            }
        });


        if (StringUtils.isEquals(mComeActivity, Cash_Azuan_Activity)) {
            tilte.setTitle("兑换A钻");
            tilte.setLeftImageShow(false);
            tvDesc.setText("兑换成功");
            lineWhite.setVisibility(View.VISIBLE);
            tvText.setVisibility(View.GONE);
        } else if (StringUtils.isEquals(mComeActivity, Cash_Adian_Activity)) {
            tilte.setTitle("转入现金A点");
            tilte.setLeftImageShow(false);
            tvDesc.setText("转入成功");
            lineWhite.setVisibility(View.VISIBLE);
            tvText.setVisibility(View.GONE);
        } else {
            tilte.setTitle("兑换");
            tilte.setLeftImageShow(true);
            tvDesc.setText("提交成功");
            lineWhite.setVisibility(View.GONE);
            tvText.setVisibility(View.VISIBLE);
        }
    }

    private void submitClick() {
        if (StringUtils.isEquals(mComeActivity, Cash_Azuan_Activity)
                || StringUtils.isEquals(mComeActivity, Cash_Adian_Activity)) {
            finish();
        } else {
            startActivity(new Intent(this, AcountListComActivity.class));
            finish();
        }
    }
}
