package com.blockadm.adm.activity;

import android.os.Bundle;
import android.view.View;

import com.blockadm.adm.R;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.comstomview.BaseTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/2/15.
 */

public class FAQactivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_faq);
        ButterKnife.bind(this);
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
