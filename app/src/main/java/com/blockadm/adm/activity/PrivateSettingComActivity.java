package com.blockadm.adm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blockadm.adm.R;
import com.blockadm.adm.adapter.PrivateSettingAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.PrivateListBean;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.RecycleViewDivider;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/30.
 */

public class PrivateSettingComActivity extends BaseComActivity {

    @BindView(R.id.rl)
    RecyclerView rl;
    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_private_setting);
        ButterKnife.bind(this);

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               finish();
                                           }
                                       }
        );
        CommonModel.userSecretList(new MyObserver<ArrayList<PrivateListBean>>() {
            @Override
            public void onSuccess(BaseResponse<ArrayList<PrivateListBean>> t) {
                Log.i("onSuccess", t.getData().toString());
                initView(t.getData());
            }


        });

    }

    private void initView(ArrayList<PrivateListBean> privateListBeans) {
        PrivateSettingAdapter   privateSettingAdapter = new PrivateSettingAdapter(privateListBeans,this);
        rl.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.color_fff2f3f4)));
        rl.setAdapter(privateSettingAdapter);
        LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rl.setLayoutManager(linearLayoutManager);

    }
}
