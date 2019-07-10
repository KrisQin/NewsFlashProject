package com.blockadm.adm.im_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.ArechargeComActivity;
import com.blockadm.adm.activity.SettingComActivity;
import com.blockadm.adm.dialog.MyComstomDialogView;
import com.blockadm.adm.im_module.adapter.RewardAdapter;
import com.blockadm.adm.im_module.widget.LiveManagePopupWindow;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.RewardInfo;
import com.blockadm.common.bean.RewardListInfo;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.live.responseBean.LiveDetailInfo;
import com.blockadm.common.bean.live.responseBean.LiveManageInfo;
import com.blockadm.common.bean.live.responseBean.LiveManagerInfo;
import com.blockadm.common.bean.params.RewardParams;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.PayPasswordDialog;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.ComObserver;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kris on 2019/5/15
 *
 * @Describe TODO { 打赏界面 }
 */
public class RewardActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.rb_Azuan)
    RadioButton rbAzuan;
    @BindView(R.id.rb_Adian)
    RadioButton rbAdian;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.tv_manager)
    TextView tvManager;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.layout_arrow)
    RelativeLayout layoutArrow;
    private RewardAdapter mRewardAdapter;
    private List<RewardInfo> mRewardInfoList = new ArrayList<>();
    private RewardListInfo mRewardListInfo;
    private int mHistorySelectPosition = -1;
    private int mPoint;
    private double mDiamond;
    private RewardInfo mCurrentRewardInfo;

    private final int A_ZUAN_TYPE = 0;
    private final int A_DIAN_TYPE = 1;
    private int rewardType = 0;  // 0:A钻  1：A点

    public static final String user_Info_Dto = "userInfoDto"; // 主讲人的memberId
    public static final String Live_Detail_Info = "Live_Detail_Info";
    private UserInfoDto mUserInfoDto;
    private int mRewardMoney = 0;

    public static final int FAIL = 20;
    public static final String CONTENT = "content";
    private List<LiveManagerInfo> mManagerInfoList = new ArrayList<>();
    private LiveManagerInfo mCurrentLiveManagerInfo;
    private LiveDetailInfo mLiveDetailInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        ButterKnife.bind(this);

        mLiveDetailInfo = (LiveDetailInfo) getIntent().getSerializableExtra(Live_Detail_Info);
        if (mLiveDetailInfo != null) {
            mCurrentLiveManagerInfo = new LiveManagerInfo();
            mCurrentLiveManagerInfo.setManagerNickName(mLiveDetailInfo.getNickName());
            mCurrentLiveManagerInfo.setManagerMemberId(mLiveDetailInfo.getMemberId());
            mCurrentLiveManagerInfo.setSpeaker(true);
            mCurrentLiveManagerInfo.setSelect(true);
            mManagerInfoList.add(mCurrentLiveManagerInfo);

            tvManager.setText("主讲人");
            tvName.setText(mCurrentLiveManagerInfo.getManagerNickName());

            findAllManagerListByNewsLiveLessonsId(mLiveDetailInfo.getId()+"");
        }


        queryRewardList();

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rbAzuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tvStatus.setVisibility(View.INVISIBLE);
                if (isChecked) {
                    showMoneyStatus(false, true);
                    if (mHistorySelectPosition != -1)
                        mRewardInfoList.get(mHistorySelectPosition).setSelect(false);
                    rewardType = A_ZUAN_TYPE;
                }


                if (isChecked && mRewardListInfo != null) {
                    mRewardMoney = 0;
                    mRewardInfoList.clear();
                    mRewardInfoList.addAll(mRewardListInfo.getDiamondList());
                    mRewardAdapter.notifyDataSetChanged();
                }

            }
        });

        rbAdian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tvStatus.setVisibility(View.INVISIBLE);

                if (isChecked) {
                    showMoneyStatus(true, false);
                    if (mHistorySelectPosition != -1)
                        mRewardInfoList.get(mHistorySelectPosition).setSelect(false);
                    rewardType = A_DIAN_TYPE;
                }


                if (isChecked && mRewardListInfo != null) {
                    mRewardMoney = 0;
                    mRewardInfoList.clear();
                    mRewardInfoList.addAll(mRewardListInfo.getPointList());
                    mRewardAdapter.notifyDataSetChanged();
                }

            }
        });

        mRewardAdapter = new RewardAdapter(mRewardInfoList, this);

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mRewardAdapter);

        mRewardAdapter.setOnItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (mHistorySelectPosition != -1)
                    mRewardInfoList.get(mHistorySelectPosition).setSelect(false);
                mCurrentRewardInfo = mRewardInfoList.get(position);
                mCurrentRewardInfo.setSelect(true);
                mHistorySelectPosition = position;
                mRewardAdapter.notifyDataSetChanged();

                mRewardMoney = mRewardInfoList.get(position).getRewardMoney();

                showMoneyStatus(rbAdian.isChecked(), rbAzuan.isChecked());

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserData();
    }

    private void findAllManagerListByNewsLiveLessonsId(String newsLiveLessonsId) {
        showDefaultLoadingDialog();
        CommonModel.findAllManagerListByNewsLiveLessonsId(newsLiveLessonsId, new MyObserver<List<LiveManageInfo>>() {
            @Override
            public void onSuccess(BaseResponse<List<LiveManageInfo>> response) {
                dismissLoadingDialog();
                if (response != null && response.getData() != null) {
                    for (int i = 0; i < response.getData().size(); i++) {
                        LiveManageInfo info = response.getData().get(i);
                        LiveManagerInfo liveManagerInfo = new LiveManagerInfo();

                        liveManagerInfo.setSpeaker(false);
                        liveManagerInfo.setManagerNickName(info.getNickName());
                        mManagerInfoList.add(liveManagerInfo);
                    }
                }
            }
        });
    }

    private LiveManagePopupWindow mLiveManagePopupWindow;
        private void ShowTypePopup() {
            if (mLiveManagePopupWindow == null) {
                mLiveManagePopupWindow = new LiveManagePopupWindow(this,
                        mManagerInfoList, new LiveManagePopupWindow.OnItemClick() {
                    @Override
                    public void itemClick(int position) {

                        for (int i = 0; i < mManagerInfoList.size(); i++) {
                            mManagerInfoList.get(i).setSelect(false);
                        }

                        mCurrentLiveManagerInfo = mManagerInfoList.get(position);
                        mCurrentLiveManagerInfo.setSelect(true);
                        tvManager.setText(mCurrentLiveManagerInfo.isSpeaker()? "主讲人":"管理员");
                        tvName.setText(mCurrentLiveManagerInfo.getManagerNickName());
                    }
                });
                mLiveManagePopupWindow.setClippingEnabled(false);
            }
            mLiveManagePopupWindow.showPop();
        }

    private void showMoneyStatus(boolean isCheckAdian, boolean isCheckAzuan) {
        if (mRewardMoney == 0) {
            return;
        }

        if (isCheckAdian) {
            if (mRewardMoney > mPoint) {
                tvStatus.setVisibility(View.VISIBLE);
                tvStatus.setText("余额不足请充值 >");
            } else {
                tvStatus.setVisibility(View.INVISIBLE);
            }
        } else {
            if (isCheckAzuan) {
                if (mRewardMoney > mDiamond) {
                    tvStatus.setVisibility(View.VISIBLE);
                    tvStatus.setText("余额不足");
                } else {
                    tvStatus.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void getUserData() {

        CommonModel.getUserData(this, new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfo) {
                mUserInfoDto = userInfo;
                mPoint = mUserInfoDto.getPoint();
                String diamondStr = mUserInfoDto.getDiamond();
                if (StringUtils.isNotEmpty(diamondStr)) {
                    mDiamond = Double.parseDouble(diamondStr);
                }

                showMoneyStatus(rbAdian.isChecked(), rbAzuan.isChecked());
            }

            @Override
            public void error(int code, String msg) {

            }

        });
    }

    /**
     * 查询打赏金额列表
     */
    private void queryRewardList() {
        showDefaultLoadingDialog();
        CommonModel.queryRewardList(new ComObserver<RewardListInfo>() {

            @Override
            public void onSuccess(BaseResponse<RewardListInfo> response, String errorMsg) {
                dismissLoadingDialog();
                mRewardListInfo = response.getData();
                if (mRewardInfoList != null) {
                    mRewardInfoList.addAll(mRewardListInfo.getDiamondList());
                }
                mRewardAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.bt_submit, R.id.tv_status,R.id.layout_arrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                submit();
                break;
            case R.id.tv_status:
                goBuy();
                break;
            case R.id.layout_arrow:
                ShowTypePopup();
                break;
        }
    }

    private void goBuy() {
        if (rbAdian.isChecked()) { //
            Intent intent = new Intent(this, ArechargeComActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 打赏
     */
    private void submit() {

        if (mRewardMoney == 0) {
            T.showShort(this, "请选择打赏金额");
            return;
        }

        if (tvStatus.getVisibility() == View.VISIBLE) {
            if (rbAzuan.isChecked()) {
                T.showShort(this, "余额不足");
            }

            if (rbAdian.isChecked()) {
                T.showShort(this, "余额不足请充值");
            }
            return;
        }

        checkPwd();


    }

    private void checkPwd() {
        if (mUserInfoDto.getIsSetPayPwd() == 0) {
            final MyComstomDialogView myComstomDialogView =
                    new MyComstomDialogView(this);
            myComstomDialogView.setTvTitle("为了你的账户安全，购买前需要设置支付密码", View.VISIBLE)
                    .setChildMsg("", View.GONE)
                    .setChildMsg2("", View.GONE)
                    .setConcelMsg("", View.GONE)
                    .setRootBg(R.mipmap.boxbg2)
                    .setConfirmMsg("确定", View.VISIBLE)
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
                    Intent intent1 = new Intent(RewardActivity.this,
                            SettingComActivity.class);
                    startActivity(intent1);
                }
            });
            myComstomDialogView.show();
        } else {
            final PayPasswordDialog dialog = new PayPasswordDialog(RewardActivity.this,
                    R.style.mydialog);
            dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
                @Override
                public void doConfirm(String password) {
                    dialog.dismiss();
                    showDefaultLoadingDialog();
                    CommonModel.checkPayPassword(password, new MyObserver<String>() {
                        @Override
                        public void onSuccess(BaseResponse<String> t) {
                            if (t.getCode() == 0) {

                                reward();
                            } else {
                                dismissLoadingDialog();
                                Toast.makeText(RewardActivity.this, t.getMsg(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }


                    });
                }
            });
            dialog.show();
        }
    }

    private void reward() {

        int newsLiveLessonsId = 0;
        if (mLiveDetailInfo != null) {
            newsLiveLessonsId = mLiveDetailInfo.getId();
        }
        String jsonString = GsonUtil.GsonString(new RewardParams(mCurrentLiveManagerInfo.getManagerMemberId(), rewardType,
                mCurrentRewardInfo.getTypeId(),newsLiveLessonsId));

        CommonModel.reward(jsonString, new MyObserver() {
            @Override
            public void onSuccess(BaseResponse t) {
                dismissLoadingDialog();

                String content;
                if (rbAzuan.isChecked() && mCurrentLiveManagerInfo != null) {
                    content = mUserInfoDto.getNickName() + "打赏了"+mCurrentLiveManagerInfo.getManagerNickName() + mCurrentRewardInfo.getRewardMoney() + "A钻";
                } else {
                    content =mUserInfoDto.getNickName() + "打赏了" +mCurrentLiveManagerInfo.getManagerNickName()+ mCurrentRewardInfo.getRewardMoney() + "A点";
                }

                Intent intent = new Intent();
                intent.putExtra(CONTENT, content);

                if (t.getCode() == 0) {
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(FAIL, intent);
                }
                finish();
            }
        });
    }

}
