package com.blockadm.adm.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.comstomview.BaseTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/3/7.
 */

public class WithdrawSuccessComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_complete)
    TextView tvComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_withdraw_success);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_complete)
    public void onViewClicked() {

        finish();
    }
}
