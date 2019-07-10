package com.blockadm.adm.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.adapter.MyLevelAdapter;
import com.blockadm.adm.dialog.MyComstomDialogView;
import com.blockadm.adm.dialog.YaoqingmaShareDialog;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.AllRecommendCodeDto;
import com.blockadm.common.bean.GetMyLevelAppDto;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by hp on 2019/2/25.
 */

public class InvitePrizeComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.civ_headimage)
    CircleImageView civHeadimage;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_progress_left)
    TextView tvProgressLeft;
    @BindView(R.id.tv_progress_right)
    TextView tvProgressRight;
    @BindView(R.id.tv_rule)
    TextView tvRule;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.iv_go)
    ImageView ivGo;
    @BindView(R.id.iv_level)
    ImageView ivLevel;

    @BindView(R.id.iv_name_icon)
    ImageView ivNameIcon;
    @BindView(R.id.swipe_target)
    EmptyRecyclerView swipeTarget;
    private ClipboardManager myClipboard;
    private UserInfoDto userInfoDto;
    private AllRecommendCodeDto allRecommendCodeDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_invite_prize);
        ButterKnife.bind(this);

        showDefaultLoadingDialog();
        CommonModel.getMyLevelApp(new MyObserver <GetMyLevelAppDto>() {
            @Override
            public void onSuccess(BaseResponse <GetMyLevelAppDto> t) {
                if (t.getData()!=null){
                    setView(t.getData());
                }
            }
        });


        CommonModel.getUserData(this,new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfo) {
                dismissLoadingDialog();
                userInfoDto = userInfo;
                ImageUtils.loadImageView(userInfoDto.getIcon(),civHeadimage);
                title.setText(userInfoDto.getNickName());
            }

            @Override
            public void error(int code, String msg) {
                dismissLoadingDialog();
            }

        });


        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        tilte.setOnRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MyComstomDialogView  myComstomDialogView = new MyComstomDialogView(InvitePrizeComActivity.this);
                myComstomDialogView.setTvTitle("关注微信公众号，进群和小伙伴 一起学习，赚取更高收益",View.VISIBLE)
                        .setChildMsg("QQ群：828845152",View.VISIBLE)
                        .setChildMsg2("公众号: BlockADM",View.VISIBLE)
                        .setConcelMsg("复制QQ群",View.VISIBLE)
                        .setConfirmMsg("复制公众号",View.VISIBLE).setCancelLisenner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myComstomDialogView.dismiss();
                        ClipData myClip = ClipData.newPlainText("text", "828845152");
                        myClipboard.setPrimaryClip(myClip);
                        ToastUtils.showToast("已经复制到剪切板");
                    }
                }).setConfirmLisenner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myComstomDialogView.dismiss();
                        ClipData myClip = ClipData.newPlainText("text", "BlockADM");
                        myClipboard.setPrimaryClip(myClip);
                        ToastUtils.showToast("已经复制到剪切板");
                    }
                });
                myComstomDialogView.show();
            }
        });
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        CommonModel.getAllRecommendCode(new MyObserver <AllRecommendCodeDto>() {
            @Override
            public void onSuccess(BaseResponse<AllRecommendCodeDto> t) {
                if(t.getCode()==0){
                    allRecommendCodeDto = t.getData();
                }

            }

        });
    }



    @OnClick({R.id.civ_headimage, R.id.iv_go,R.id.tv_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_headimage:
                break;
            case R.id.iv_go:
                if (allRecommendCodeDto!=null){
                    RxPermissions rxPermissions = new RxPermissions(this);
                    rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里填写所需要的权限
                            .subscribe(new Consumer<Boolean>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void accept(@NonNull Boolean aBoolean) throws Exception {
                                    if (aBoolean) {
                                        //当所有权限都允许之后，返回true
                                        YaoqingmaShareDialog yaoqingmaShareDialog = new YaoqingmaShareDialog(InvitePrizeComActivity.this,allRecommendCodeDto,myClipboard);
                                        yaoqingmaShareDialog.show();
                                    } else {
                                        //只要有一个权限禁止，返回false，
                                        //下一次申请只申请没通过申请的权限
                                    }
                                }
                            });

                }

                break;
            case R.id.tv_rule:
                final MyComstomDialogView  myComstomDialogView = new MyComstomDialogView(InvitePrizeComActivity.this);
                myComstomDialogView.setTvTitle("每个等级至少有一位用户是终身学习大使，才能升级到下一个等级。",View.VISIBLE)
                        .setChildMsg("",View.GONE)
                        .setChildMsg2("",View.GONE)
                        .setConcelMsg("",View.GONE)

                        .setConfirmMsg("我知道了",View.VISIBLE)
                        .setConfirmSize(6)
                        .setCancelLisenner(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myComstomDialogView.dismiss();
                            }
                        }).setConfirmLisenner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myComstomDialogView.dismiss();
                    }
                });
                myComstomDialogView.show();
                break;
        }
    }

    public void setView(GetMyLevelAppDto getMyLevelAppDto) {
        ImageUtils.loadImageView(getMyLevelAppDto.getLevelDiamondIcon(),ivLevel);
        ImageUtils.loadImageView(getMyLevelAppDto.getLevelNameIcon(),ivNameIcon);

        tvProgressRight.setText("升级需好友助力"+(getMyLevelAppDto.getAdvanceBase()-getMyLevelAppDto.getAdvanceSize())+"次");
        tvProgressLeft.setText("好友助力"+getMyLevelAppDto.getAdvanceSize()+"次");
        progressBar.setMax(getMyLevelAppDto.getAdvanceBase());
        progressBar.setProgress(getMyLevelAppDto.getAdvanceSize());

        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        swipeTarget.setLayoutManager(linearLayoutManager);

        swipeTarget.setAdapter(new MyLevelAdapter(getMyLevelAppDto.getLevelList(), new MyLevelAdapter.ClickItemCallback() {
            @Override
            public void showLoading() {
                showDefaultLoadingDialog();
            }

            @Override
            public void dismissLoading() {
                dismissLoadingDialog();
            }
        }));
    }
}
