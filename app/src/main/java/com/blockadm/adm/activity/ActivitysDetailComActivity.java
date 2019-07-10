package com.blockadm.adm.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.dialog.ActivitysShareBottomViewDialog;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.ActivitysDetailDto;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.DimenUtils;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by hp on 2019/2/21.
 */

public class ActivitysDetailComActivity extends BaseComActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_shard)
    ImageView ivShard;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_zhuban)
    TextView tvZhuban;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.wv_content)
    WebView webView;

    @BindView(R.id.ll_web)
    LinearLayout llWeb;
    int id;
    private ActivitysDetailDto data;
    private String mScanUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_activitys_detail);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", -1);
        int imageMaxWidth = ScreenUtils.getScreenWidth(getApplicationContext());
        CommonModel.getNewsActivity(new MyObserver<ActivitysDetailDto>() {
            @Override
            public void onSuccess(BaseResponse<ActivitysDetailDto> t) {
                if (t.getCode() == 0) {
                    setDatatoView(t.getData());
                } else {
                    ToastUtils.showToast(t.getMsg());
                }

            }


        }, id, DimenUtils.px2dip(this, imageMaxWidth));

        getUserData(false);
    }


    private void setDatatoView(ActivitysDetailDto data) {
        this.data = data;
        if (data.getIsCollection() == 1) {
            ivCollect.setImageResource(R.mipmap.activity_like_press);
        } else {
            ivCollect.setImageResource(R.mipmap.activity_like_default);
        }
        ImageUtils.loadImageView(data.getLogo(), ivLogo);
        tvTitle.setText(data.getTitle());
        tvAddress.setText(data.getAddress());
        tvZhuban.setText(data.getSponsor());
        tvTime.setText(data.getShowTime() + "\n" + data.getStartToEndTime());
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        if (data.getCreateType() == 1) {
            tvZhuban.setVisibility(View.VISIBLE);
            //  webView.loadDataWithBaseURL(null,data.getContent(), "text/html", "utf-8",null);
            webView.loadUrl(data.getShowContentHtmlUrl());
        } else {
            tvZhuban.setVisibility(View.GONE);
            webView.loadUrl(data.getUrl());
        }
        llWeb.addView(webView);

    }

    private void getUserData(final boolean isShowDialog) {
        CommonModel.getUserData(this,new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfoDto) {
                if (isShowDialog) {
                    if (data != null) {
                        showShareDialog(data);
                    }
                }
            }

            @Override
            public void error(int code, String msg) {

            }

        });
    }

    private void prepareShare(final ActivitysDetailDto data) {
        RxPermissions rxPermissions = new RxPermissions(ActivitysDetailComActivity.this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里填写所需要的权限
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            if (StringUtils.isNotEmpty(mScanUrl)) {
                                showShareDialog(data);
                            }else {
                                getUserData(true);
                            }
                        } else {
                            //只要有一个权限禁止，返回false，
                            //下一次申请只申请没通过申请的权限
                        }
                    }
                });


    }

    private void showShareDialog(ActivitysDetailDto data) {
        //当所有权限都允许之后，返回true
        ActivitysShareBottomViewDialog activitysShareDialog =
                new ActivitysShareBottomViewDialog(ActivitysDetailComActivity.this, data);
        activitysShareDialog.show();
    }

    @OnClick({R.id.iv_back, R.id.iv_shard, R.id.iv_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_shard:
                if (data != null) {
                    prepareShare(data);
                }

                break;
            case R.id.iv_collect:
                if (data != null) {
                    if (data.getIsCollection() == 0) {
                        CommonModel.operateNewsActivityCount(new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse t) {
                                ToastUtils.showToast(t.getMsg());
                                if (t.getCode() == 0) {
                                    ivCollect.setImageResource(R.mipmap.activity_like_press);
                                    data.setIsCollection(1);
                                }
                            }


                        }, data.getId(), 0, 0);
                    } else {
                        CommonModel.operateNewsActivityCount(new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse t) {
                                ToastUtils.showToast(t.getMsg());
                                if (t.getCode() == 0) {
                                    data.setIsCollection(0);
                                    ivCollect.setImageResource(R.mipmap.activity_like_default);
                                }
                            }


                        }, data.getId(), 0, 1);
                    }
                }

                break;
        }
    }
}
