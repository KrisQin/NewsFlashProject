package com.blockadm.adm.im_module.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.im_module.adapter.HBListAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.live.responseBean.LiveHBInfo;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.config.ComEvent;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.StatusBarUtil;
import com.blockadm.common.utils.T;
import com.blockadm.common.widget.MyListView;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Kris on 2019/6/13
 *
 * @Describe TODO { 收到的红包列表 }
 */
public class HBListActivity extends BaseComActivity {

    @BindView(R.id.layout_back)
    RelativeLayout layoutBack;
    @BindView(R.id.iv_head_image)
    CircleImageView ivHeadImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.listview)
    MyListView listview;
    private Unbinder mBind;

    private List<LiveHBInfo.ReceiveListBean> mList;

    public static final String HB_id = "HB_id";
    public static final String HB_Head_image_url = "HB_Head_image_url";
    public static final String HB_name = "HB_name";

    private HBListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_list);
        mBind = ButterKnife.bind(this);
        mList = new ArrayList<>();

        layoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HBListActivity.this.finish();
            }
        });

        mAdapter = new HBListAdapter(mList, this);
        listview.setAdapter(mAdapter);

        initData();
    }

    private void initData() {

        //0 红包抢完了  2 红包已经领取  3 红包已过期  4 可领取
        int hbId = getIntent().getIntExtra(HB_id, -1);
        String url = getIntent().getStringExtra(HB_Head_image_url);
        String name = getIntent().getStringExtra(HB_name);

        tvName.setText(name);
        Glide.with(this).load(url).error(R.mipmap.picture_default).into(ivHeadImage);

        showDefaultLoadingDialog();
        CommonModel.queryHBlist(hbId, new MyObserver<LiveHBInfo>() {
            @Override
            public void onSuccess(BaseResponse<LiveHBInfo> t) {

                dismissLoadingDialog();
                if (t != null) {
                    if (t.isSuccess()) {
                        if (t.getData() != null) {
                            LiveHBInfo info = t.getData();
                            dealStatus(info);
                            List<LiveHBInfo.ReceiveListBean> receiveList = t.getData().getReceiveList();
                            Collections.reverse(receiveList);
                            mList.addAll(receiveList);
                            mAdapter.notifyDataSetChanged();
                        }

                    }else {
                        T.showShort(HBListActivity.this,t.getMsg());
                    }

                }


            }
        });


    }

    /**
     *
     * @param info
     */
    private void dealStatus(LiveHBInfo info) {

        int receiveState = info.getReceiveState();
        //0 红包抢完了  1 红包抢成功   2 红包已经领取  3 红包已过期  4 可领取
        if (receiveState == 0) {
            if (info.getRewardType() == 0) {
                tvCount.setText("红包已被抢光，"+info.getSize()+"个红包共"+info.getMoney()+"A钻");
            }else {
                tvCount.setText("红包已被抢光，"+info.getSize()+"个红包共"+info.getMoney()+"A点");
            }
        }else {
            tvCount.setText(info.getSize() - info.getRemainSize() + "/"+info.getSize());
            if (receiveState == 1) { //抢红包成功
                EventBus.getDefault().post(new ComEvent(ComEvent.ReceiveHBSuccess));
            }
        }

    }


    @Override
    protected void setBarColor() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(com.blockadm.common.R.color.color_e0614b));
                StatusBarUtil.setImmersiveStatusBar(this, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (mBind != null) {
            mBind.unbind();
        }
        super.onDestroy();
    }
}
