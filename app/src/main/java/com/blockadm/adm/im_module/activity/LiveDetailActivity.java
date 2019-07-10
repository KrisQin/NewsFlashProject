package com.blockadm.adm.im_module.activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.adm.MainApp;
import com.blockadm.adm.R;
import com.blockadm.adm.activity.PersonnalIndexComActivity;
import com.blockadm.adm.adapter.MyPagerAdapter;
import com.blockadm.adm.dialog.LiveLessonsPicDialog;
import com.blockadm.adm.dialog.LiveLessonsShareDialog;
import com.blockadm.common.bean.live.param.NewsLiveLessonsCommentParams;
import com.blockadm.common.bean.live.responseBean.LiveManagerInfo;
import com.blockadm.common.bean.live.responseBean.RealTimeDetailInfo;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.config.ComConfig;
import com.blockadm.common.config.ComEvent;
import com.blockadm.common.config.LiveConfig;
import com.blockadm.common.im.LiveManager;
import com.blockadm.common.im.call.ComCallback;
import com.blockadm.common.im.call.LoginCallBack;
import com.blockadm.adm.im_module.fragment.CommunicationFragment;
import com.blockadm.adm.im_module.fragment.IntroductionFragment;
import com.blockadm.adm.im_module.fragment.LiveFragment;
import com.blockadm.adm.im_module.utils.ShareDialogUtils;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.adm.service.PlayService;
import com.blockadm.common.base.BaseFragmentActivity;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.live.responseBean.LiveDetailInfo;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.ComObserver;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.im.call.TCallback;
import com.blockadm.common.im.entity.MessageInfo;
import com.blockadm.common.im.entity.MessageType;
import com.blockadm.common.im.utils.MessageInfoUtil;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.SystemUtils;
import com.blockadm.common.widget.BlackToastDialog;
import com.blockadm.common.widget.HBDialog;
import com.blockadm.common.wxpay.Constants;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kris on 2019/5/14
 * <p>
 * 交流界面发文字内容，格式： IS_COMMUNICATION_TEXT + 消息体
 * 直接界面普通用户发送文字内容，格式：IS_COMMUNICATION_TEXT + 消息体
 * 直接界面主讲人或管理员发送文字内容，格式：IS_LIVE_TEXT + 消息体
 *
 * @Describe TODO {  }
 */
public class LiveDetailActivity extends BaseFragmentActivity {


    private final String INTRODUCTION = "简介";
    private final String LIVE = "直播";
    private final String COMMUNICATION = "交流";
    @BindView(R.id.iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.layout_title)
    RelativeLayout layoutTitle;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tlTab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.layout_root)
    RelativeLayout layoutRoot;

    @BindView(R.id.tv_number_look)
    TextView tvNumberLook;

    private IntroductionFragment mIntroductionFragment;

    public static final String LIVE_ID = "id";
    public static final String TITLE = "title";
    public LiveDetailInfo mLiveDetailInfo;

    public int mLiveId;
    private UserInfoDto mUserInfoDto = null;
    private LiveFragment mLiveFragment;
    private CommunicationFragment mCommunicationFragment;
    public String mIdentifier;
    public boolean isSpeaker;

    private List<String> mManagerIdentifierList = new ArrayList<>(); //管理员账号列表
    private List<String> mManagerNameList = new ArrayList<>(); //管理员名字列表
    public boolean isSpeakerOrManager = false; //true:是主讲人或管理员
    private boolean isNeedRefreshIntroductionFragmentBottomView; //输入密码或者购买后，需要刷新简介界面底部的输入密码按钮或购买按钮

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);

        setContentView(R.layout.activity_live_detaill);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        MessageInfoUtil.setContext(this);
        LiveManager.getInstance().setContext(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        mLiveId = getIntent().getIntExtra(LIVE_ID, -1);

        LiveManager.getInstance().setNewsLiveLessonsId(mLiveId);

        String title = getIntent().getStringExtra(TITLE);
        tvTitle.setText(title);
        bindPlayService();

        initViewPager();
        getUserData();


    }

    /**
     * 弹幕：最多显示4条消息
     *
     * @param msgInfoList
     */
    public void toastMsgList(List<MessageInfo> msgInfoList) {
        if (mLiveFragment != null)
            mLiveFragment.toastMsgList(msgInfoList);
    }

    /**
     * 普通用户在直播界面发出的文字消息显示到交流界面上
     *
     * @param msg
     */
    public void addLiveMsgList(MessageInfo msg) {
        mCommunicationFragment.addLiveMsgList(msg);
    }

    /**
     * 进入个人主页
     * @param memberId
     */
    public void goUserView(int memberId) {
        Intent intent = new Intent(this,PersonnalIndexComActivity.class);
        intent.putExtra(ConstantUtils.MENBERID,memberId);
        startActivity(intent);
    }

    private void back() {
        //        startActivity(new Intent(LiveDetailActivity.this, MainComActivity.class));
        LiveDetailActivity.this.finish();
    }

    private boolean isBound = false;

    //绑定服务
    private void bindPlayService() {
        try {
            if (isBound == false) {
                Intent intent = new Intent(this, PlayService.class);
                bindService(intent, conn, BIND_AUTO_CREATE);
                isBound = true;
            }
        } catch (Exception e) {

        }
    }


    //解绑服务
    private void unbindPlayService() {
        if (isBound == true) {
            isBound = false;
            unbindService(conn);
        }
    }

    //连接Activity和Service
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayService.PlayBinder playBinder = (PlayService.PlayBinder) service;
            PlayService playService = playBinder.getPlayService();
            MainApp.setPlayService1(playService);
            MainApp.setServiceConnection(conn);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            MainApp.setPlayService1(null);

        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedRefreshIntroductionFragmentBottomView) {
            queryLessonsDetail(true);
        }
    }


    public void queryLessonsDetail(boolean isShowLoadingDialog) {
        queryNewsLiveLessonsDetailById(mLiveId, isShowLoadingDialog);
    }

    private void getUserData() {

        showDefaultLoadingDialog();
        CommonModel.getUserData(this, new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfo) {

                try {
                    mUserInfoDto = userInfo;
                    LiveManager.getInstance().setUserInfoDto(mUserInfoDto);
                    LiveManager.getInstance().setMemberId(mUserInfoDto.getMemberId());
                    ACache.get(LiveDetailActivity.this).put(ACache.user_Info_Dto, mUserInfoDto);
                    mIdentifier = mUserInfoDto.getLoginIMAccount();
                    String userSig = mUserInfoDto.getLoginIMSign();

                    queryLessonsDetail(false);
                    LiveManager.getInstance().login(mIdentifier, userSig, mLoginCallBack);
                } catch (Exception e) {

                } finally {
                    dismissLoadingDialog();
                }

            }

            @Override
            public void error(int code, String msg) {
                try {
                    LiveManager.getInstance().setUserInfoDto(null);
                    queryLessonsDetail(false);

                } catch (Exception e) {

                } finally {
                    dismissLoadingDialog();
                }
            }

        });

    }

    /**
     * 保存交流间的消息
     *
     * @param content
     * @param contentType 0:文本内容、1：图片URL、2：语音URL、3：视频URL
     */
    public void addCommunityNewsLiveLessonsComment(final String content, String contentType,
                                                   int playTime) {
        String json = GsonUtil.GsonString(new NewsLiveLessonsCommentParams(content, contentType,
                playTime, mLiveId, 1));
        CommonModel.addNewsLiveLessonsComment(json, new MyObserver() {
            @Override
            public void onSuccess(BaseResponse t) {

                L.d(LiveManager.TAG,
                        "保存成功 --> " + content);
            }
        });
    }

    /**
     * 保存直播间的消息
     *
     * @param content
     * @param contentType 0:文本内容、1：图片URL、2：语音URL、3：视频URL
     */
    public void addLiveNewsLiveLessonsComment(final String content, String contentType,
                                              int playTime) {
        String json = GsonUtil.GsonString(new NewsLiveLessonsCommentParams(content, contentType,
                playTime, mLiveId, 0));
        CommonModel.addNewsLiveLessonsComment(json, new MyObserver() {
            @Override
            public void onSuccess(BaseResponse t) {

                L.d(LiveManager.TAG,
                        "保存成功 --> " + content);
            }
        });
    }

    private final int whatStatus = 0;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case whatStatus:

                    getNewsLiveLessonsRealTimeDetailById();

                    removeMessages(whatStatus);
                    sendEmptyMessageDelayed(whatStatus, 60000);//这里想几秒刷新一次就写几秒
                    break;
            }
        }
    };

    public boolean isForbid = false; //true:已被禁言

    /**
     * 查询实时消息
     */
    private void getNewsLiveLessonsRealTimeDetailById() {
        CommonModel.getNewsLiveLessonsRealTimeDetailById(mLiveId,
                new MyObserver<RealTimeDetailInfo>() {
                    @Override
                    public void onSuccess(BaseResponse<RealTimeDetailInfo> t) {
                        if (t.isSuccess() && t.getData() != null) {
                            RealTimeDetailInfo realTimeDetailInfo = t.getData();
                            isForbid = realTimeDetailInfo.getIsForbid() == 1;
                        }
                    }
                });
    }


    /**
     * 判断当前用户是否有权限禁言
     */
    public void isUserManagerPermissions() {
        if (mLiveDetailInfo == null || mUserInfoDto == null) {
            return;
        }

        if (null != mHandler) {
            mHandler.sendEmptyMessage(whatStatus);
        }

        String identifier = mUserInfoDto.getLoginIMAccount();

        mManagerIdentifierList.clear();
        mManagerNameList.clear();


        for (int i = 0; i < mLiveDetailInfo.getNewsLiveLessonsManagerList().size(); i++) {
            LiveManagerInfo liveManagerInfo =
                    mLiveDetailInfo.getNewsLiveLessonsManagerList().get(i);
            mManagerIdentifierList.add(liveManagerInfo.getManagerLoginIMAccount() + "");
            mManagerNameList.add(liveManagerInfo.getManagerNickName());
        }
        mManagerIdentifierList.add(mLiveDetailInfo.getSpeakerIMAccount() + "");

        mManagerNameList.add(mLiveDetailInfo.getNickName());

        if (mManagerIdentifierList.contains(identifier)) {
            isSpeakerOrManager = true;
        }

        LiveManager.getInstance().setManagerIdentifierList(mManagerIdentifierList);
        LiveManager.getInstance().setManagerNameList(mManagerNameList);
        LiveManager.getInstance().setCurrentUserSpeakerOrManager(true);

        if (mCommunicationFragment != null) {
            mCommunicationFragment.setSpeaker(isSpeakerOrManager);
        }

        if (mLiveFragment != null)
            mLiveFragment.showInputGroup(isSpeakerOrManager);
    }


    private final int reward_requestCode = 101; //奖赏
    private final int ask_requestCode = 102; //提问
    private final int send_HB_requestCode = 103; //发红包

    /**
     * 去打赏
     */
    public void goReward() {
        if (mUserInfoDto != null) {
            Intent intent = new Intent(this, RewardActivity.class);
            intent.putExtra(RewardActivity.user_Info_Dto, mUserInfoDto);
            intent.putExtra(RewardActivity.Live_Detail_Info, mLiveDetailInfo);
            startActivityForResult(intent, reward_requestCode);
        }else {
            ContextUtils.showNoLoginDialog(this);
            SystemUtils.hideSoftInput(this);
        }
    }

    /**
     * 去提问
     */
    public void askQuestions() {
        if (mUserInfoDto == null) {
            ContextUtils.showNoLoginDialog(this);
            SystemUtils.hideSoftInput(this);
            return;
        }

        if (mLiveDetailInfo != null) {
            Intent intent = new Intent(this, AskQuestionsActivity.class);
            intent.putExtra(AskQuestionsActivity.Live_Detail_Info, mLiveDetailInfo);
            startActivityForResult(intent, ask_requestCode);
        }
    }

    /**
     * 去发红包
     */
    public void sendHB() {
        if (mUserInfoDto == null) {
            ContextUtils.showNoLoginDialog(this);
            SystemUtils.hideSoftInput(this);
            return;
        }

        if (mLiveDetailInfo != null) {
            Intent intent = new Intent(this, SendHBActivity.class);
            intent.putExtra(SendHBActivity.news_Live_Lessons_Id, mLiveDetailInfo.getId());
            startActivityForResult(intent, send_HB_requestCode);
        }
    }

    /**
     * 展示红包列表
     */
    public void goHBlist(MessageInfo msg) {
        if (msg != null) {
            Intent intent = new Intent(this, HBListActivity.class);
            intent.putExtra(HBListActivity.HB_id, msg.getHongbaoId());
            intent.putExtra(HBListActivity.HB_Head_image_url, msg.getHeadImageUrl());
            intent.putExtra(HBListActivity.HB_name, msg.getName());
            startActivity(intent);
        }
    }


    private MessageInfo mCurrentHBmsg;

    /**
     * 0 红包抢完了  2 红包已经领取  3 红包已过期  4 可领取
     *
     * @param receiveState
     */
    public void dealHBstatus(int receiveState, MessageInfo msg) {
        if (msg == null) {
            return;
        }
        mCurrentHBmsg = msg;

        if (receiveState == 0 || receiveState == 2) {
            goHBlist(msg);
        } else if (receiveState == 3) {
            showHbDialog(msg, true);
        } else if (receiveState == 4) {
            showHbDialog(msg, false);
        }
    }

    private void showHbDialog(final MessageInfo msg, boolean isOverTime) {
        new HBDialog.Builder(this)
                .setHeadImageUrl(msg.getHeadImageUrl())
                .setOverTime(isOverTime)
                .setOnOpenClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goHBlist(msg);
                    }
                })
                .setOnLookClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goHBlist(msg);
                    }
                })
                .create().show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveHbSuccess(ComEvent event) {
        if (StringUtils.isEquals(event.getAction(), ComEvent.ReceiveHBSuccess)) { //
            MessageInfo info = new MessageInfo();
            info.setMsgType(MessageType.MSG_TYPE_TEXT);
            info.setNormalMsg(true);
            info.setReceiveHongbao(true);
            String content = "";
            if (mUserInfoDto != null) {
                content = mUserInfoDto.getNickName() + "领取了" + mCurrentHBmsg.getName() + "的红包";
            }
            info.setContent(content);
            mLiveFragment.adapterChange(info);
            LiveManager.getInstance().sendTextMessage(content + LiveConfig.BlockADM_LHB, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == reward_requestCode) { //打赏
            if (resultCode == RESULT_OK) {
                BlackToastDialog.showToastDialog(this, true);
                MessageInfo info = new MessageInfo();
                info.setReward(true);
                info.setNormalMsg(true);

                if (data != null) {
                    String content = data.getStringExtra(RewardActivity.CONTENT);
                    info.setContent(content);
                }

                LiveManager.getInstance().sendTextMessage(info.getContent() + LiveConfig.BlockADM_PAY, null);

                mLiveFragment.adapterChange(info);
            } else if (resultCode == RewardActivity.FAIL) {
                BlackToastDialog.showToastDialog(this, false);
            }

        } else if (requestCode == ask_requestCode) { //提问
            if (resultCode == RESULT_OK) {

                MessageInfo info = new MessageInfo();
                info.setAskQuestion(true);
                info.setMsgType(MessageType.MSG_TYPE_TEXT);
                info.setMySendMsg(true);

                if (data != null) {
                    String content = data.getStringExtra(AskQuestionsActivity.CONTENT);
                    info.setContent(content);
                    String name = data.getStringExtra(AskQuestionsActivity.TITLE);
                    info.setName(name);
                    if (mUserInfoDto != null)
                        info.setHeadImageUrl(mUserInfoDto.getIcon());
                }

                LiveManager.getInstance().sendTextMessage(info.getName() + LiveConfig.BlockADM_ASK + info.getContent(), null);

                mLiveFragment.adapterChange(info);
            }
        } else if (requestCode == send_HB_requestCode) { //发红包
            if (resultCode == RESULT_OK) {
                MessageInfo info = new MessageInfo();
                info.setMsgType(MessageType.MSG_TYPE_TEXT);
                info.setMySendMsg(true);
                info.setSendHongbao(true);
                if (mUserInfoDto != null) {
                    info.setHeadImageUrl(mUserInfoDto.getIcon());
                    info.setName(mUserInfoDto.getNickName());
                }
                int hbId = -1;
                if (data != null) {
                    hbId = data.getIntExtra(SendHBActivity.HB_id, 0);
                    info.setHongbaoId(hbId);
                }

                mLiveFragment.adapterChange(info);
                LiveManager.getInstance().sendTextMessage(hbId + LiveConfig.BlockADM_FHB, null);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 群聊登录结果返回
     */
    LoginCallBack mLoginCallBack = new LoginCallBack() {
        @Override
        public void onError(int code, String desc) {

            L.d(LiveManager.TAG, " Login onError code: " + code + " ; desc: " + desc);

        }

        @Override
        public void onSuccess() {

            L.d(LiveManager.TAG, " Login 成功 ");

        }
    };


    @Override
    protected void onDestroy() {
        unbindPlayService();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private boolean isHadQueryHistoryMsg = false;

    /**
     * 查询直播課程详情
     *
     * @param liveId
     */
    private void queryNewsLiveLessonsDetailById(final int liveId,
                                                final boolean isShowLoadingDialog) {

        if (isShowLoadingDialog)
            showDefaultLoadingDialog();

        CommonModel.queryNewsLiveLessonsDetailById(String.valueOf(liveId),
                new ComObserver<LiveDetailInfo>() {

                    @Override
                    public void onSuccess(BaseResponse<LiveDetailInfo> t, String errorMsg) {

                        dismissLoadingDialog();
                        mLiveDetailInfo = t.getData();
                        if (mLiveDetailInfo != null) {

                            isUserManagerPermissions();

                            LiveManager.getInstance().setGroupId(mLiveDetailInfo.getImGroupId());
                            LiveManager.getInstance().setLiveStatus(mLiveDetailInfo.getLiveStatus());

                            L.d("-------------------- liveId: " + liveId + " mLiveDetailInfo" +
                                    ".getId(): " + mLiveDetailInfo.getId());

                            isSpeaker = mLiveDetailInfo.getIsIMGroupManager() == 2;


                            if (mLiveDetailInfo.getIsJoinIMGroup() == 1 && !isHadQueryHistoryMsg) {
                                isHadQueryHistoryMsg = true;
                                if (mLiveFragment != null) {
                                    mLiveFragment.queryLiveHistoryMsg();
                                }

                                if (mCommunicationFragment != null) {
                                    mCommunicationFragment.findAllExchangeCommentList();
                                }
                            }

                            isNeedRefreshIntroductionFragmentBottomView =
                                    mLiveDetailInfo.getIsSeeStatus() == 1 || mLiveDetailInfo.getIsSeeStatus() == 3;
                            if (mIntroductionFragment != null) {
                                mIntroductionFragment.showData(mLiveDetailInfo);
                            }

                            if (mCommunicationFragment != null) {
                                mCommunicationFragment.showDescLayout(mLiveDetailInfo);
                            }


                            if (mLiveFragment != null) {
                                mLiveFragment.showDescLayout(mLiveDetailInfo);
                            }


                            ACache.get(LiveDetailActivity.this).put(ConstantUtils.Live_Detail_Info, mLiveDetailInfo);

                            SharedpfTools.getInstance().put(ConstantUtils.im_Group_Id,
                                    mLiveDetailInfo.getImGroupId());

                            tvNumberLook.setText(mLiveDetailInfo.getConvertVisitCount());

                        }
                    }
                });
    }


    private void initViewPager() {

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> tabNames = new ArrayList<>();

        mIntroductionFragment = new IntroductionFragment();
        fragments.add(mIntroductionFragment);
        tabNames.add(INTRODUCTION);

        mLiveFragment = new LiveFragment();
        fragments.add(mLiveFragment);
        tabNames.add(LIVE);

        mCommunicationFragment = new CommunicationFragment();
        fragments.add(mCommunicationFragment);
        tabNames.add(COMMUNICATION);

        setViewpageAdapter(fragments, tabNames);
        //        showViewPager(fragments, tabNames);

    }


    @OnClick({R.id.iv_share})
    public void onViewClicked(View view) {

        //        if (!ComConfig.isLogin(this)) {
        //            ContextUtils.showNoLoginDialog(this);
        //            return;
        //        }

        switch (view.getId()) {
            case R.id.iv_share:

                if (mUserInfoDto == null) {
                    ContextUtils.showNoLoginDialog(this);
                    SystemUtils.hideSoftInput(this);
                    return;
                }

                CommonModel.addEncourage(105,mMyObserver);

                if (mLiveDetailInfo != null && mUserInfoDto != null) {
                    ShareDialogUtils.shareLive(this, mLiveDetailInfo.getCoverImgs(), 4,
                            mLiveDetailInfo.getId() + "", mLiveDetailInfo.getTitle(), "",
                            new ComCallback() {
                                @Override
                                public void callback() {

                                    LiveLessonsPicDialog dialog =
                                            new LiveLessonsPicDialog(LiveDetailActivity.this,
                                                    mUserInfoDto, mLiveDetailInfo,
                                                    new TCallback<UMImage>() {
                                                @Override
                                                public void callback(UMImage umImage) {
                                                    new LiveLessonsShareDialog(LiveDetailActivity.this, umImage).show();
                                                }
                                            });
                                    dialog.show();
                                }
                            });
                }


                break;
        }
    }

    private MyObserver mMyObserver = new MyObserver() {
        @Override
        public void onSuccess(BaseResponse t) {

        }

        @Override
        public void onNext(Object o) {

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private MyPagerAdapter mAdapter;

    private void setViewpageAdapter(ArrayList<Fragment> fragments, ArrayList<String> tabNames) {
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, tabNames);
        viewpager.setAdapter(mAdapter);
        viewpager.setOffscreenPageLimit(fragments.size());
        tlTab.setViewPager(viewpager);
        tlTab.onPageSelected(0);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1 || position == 2) {
                    String token =
                            (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
                    if (TextUtils.isEmpty(token)) {
                        ContextUtils.showNoLoginDialog(LiveDetailActivity.this);
                    }
                }

                if (position == 1) {
                    CommonModel.addEncourage(202,mMyObserver);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
