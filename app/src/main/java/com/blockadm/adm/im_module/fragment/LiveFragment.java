package com.blockadm.adm.im_module.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.adm.MainApp;
import com.blockadm.adm.R;
import com.blockadm.adm.dialog.LiveSendMsgDialog;
import com.blockadm.adm.im_module.activity.LiveDetailActivity;
import com.blockadm.adm.im_module.activity.LiveManagerActivity;
import com.blockadm.adm.im_module.activity.ShowBigImageActivity;
import com.blockadm.adm.im_module.adapter.LiveListAdapter;
import com.blockadm.adm.im_module.call.LiveClikcJiaToastCallback;
import com.blockadm.adm.im_module.call.LiveMsgCallback;
import com.blockadm.adm.im_module.utils.BeanChangeUtils;
import com.blockadm.adm.service.PlayService;
import com.blockadm.common.bean.live.responseBean.HBStatusInfo;
import com.blockadm.common.call.OnSoundClickListener;
import com.blockadm.common.config.LiveConfig;
import com.blockadm.adm.im_module.widget.SpeakerBottomInputGroup;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.QiniuTokenParams;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.live.param.LiveHistoryParams;
import com.blockadm.common.bean.live.responseBean.LiveDetailInfo;
import com.blockadm.common.bean.live.responseBean.HistoryLiveLessonsListInfo;
import com.blockadm.common.call.OnImageClickListener;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.im.FileUtil;
import com.blockadm.common.im.LiveManager;
import com.blockadm.common.im.call.ComCallback;
import com.blockadm.common.im.call.SendMessageCallback;
import com.blockadm.common.im.call.TCallback;
import com.blockadm.common.im.entity.MessageInfo;
import com.blockadm.common.im.entity.MessageType;
import com.blockadm.common.im.utils.MessageInfoUtil;
import com.blockadm.common.im.widget.ToastConstraintLayout;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.CopyTextUtils;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.KeyboardUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.ListUtils;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.SystemUtils;
import com.blockadm.common.utils.T;
import com.blockadm.common.widget.ComListPopupWindow;
import com.blockadm.common.widget.CommonDialog;
import com.blockadm.common.widget.MarqueeText;
import com.blockadm.common.widget.RoundRectLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMSoundElem;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUserProfile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Kris on 2019/5/8
 *
 * @Describe TODO { 直播 }
 */
public class LiveFragment extends BaseComFragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;

    private List<MessageInfo> mMsgInfoList = new ArrayList<>();
    private LiveDetailActivity mActivity;
    private LiveListAdapter mAdapter;


    private SpeakerBottomInputGroup mSpeakerBottomInputGroup;
    private RoundRectLayout mRecordingGroup;
    private ImageView mRecordingIcon;
    private TextView mRecordingTips;
    private View layout_desc;
    private TextView tv_desc;

    private AnimationDrawable mVolumeAnim;
    private View mNormal_person_input_view;
    private EditText mNormal_Et_say_content;
    private ImageView mNormal_iv_reward;
    private ImageView mNormal_Iv_ask;
    private ImageView mNormal_Iv_show;
    private TextView mNormal_Tv_send;
    private LinearLayout mNormal_Layout_bt;
    private SmartRefreshLayout mSmartRefreshLayout;
    private ToastConstraintLayout mLayout_toast;

    private boolean isSpeaker;
    private MarqueeText mTv_notice;
    private View mLayout_notice;

    private AnimationDrawable mCurrentSoundAnim;
    private int mSoundCurrentLocation = mSoundStopLocation;
    private static final int mSoundStopLocation = -1;
    private LiveSendMsgDialog mLiveSendMsgDialog;

    @Override
    public int getLayoutId() {
        return R.layout.live_fragment;
    }

    @Override
    protected void initView(View view) {

        mActivity = (LiveDetailActivity) getActivity();
        initRefreshLayout(view);
        layout_desc = view.findViewById(R.id.layout_desc);
        tv_desc = view.findViewById(R.id.tv_desc);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mSpeakerBottomInputGroup = view.findViewById(R.id.speaker_input_view);
        mRecordingGroup = view.findViewById(R.id.voice_recording_view);
        mRecordingIcon = view.findViewById(R.id.recording_icon);
        mRecordingTips = view.findViewById(R.id.recording_tips);
        mLayout_toast = view.findViewById(R.id.layout_toast);

        mTv_notice = view.findViewById(R.id.tv_notice);
        mLayout_notice = view.findViewById(R.id.layout_notice);

        mNormal_person_input_view = view.findViewById(R.id.normal_person_input_view);
        mNormal_Et_say_content = view.findViewById(R.id.et_say_content);
        mNormal_iv_reward = view.findViewById(R.id.iv_reward);
        mNormal_Iv_ask = view.findViewById(R.id.iv_ask);
        mNormal_Iv_show = view.findViewById(R.id.iv_normal_show);
        mNormal_Tv_send = view.findViewById(R.id.text_send);
        mNormal_Layout_bt = view.findViewById(R.id.layout_bt);

        mNormal_Tv_send.setOnClickListener(this);
        mNormal_Iv_show.setOnClickListener(this);
        mNormal_Iv_ask.setOnClickListener(this);
        mNormal_iv_reward.setOnClickListener(this);

        etContentTouchListener();
        mSpeakerBottomInputGroup.setInputHandler(mChatInputHandler);
        mSpeakerBottomInputGroup.setActivity(mActivity);
        normalEtTextChangedListener();
        setRecyclerViewAdapter();

        inputGroupClickJiaButtonListener(); //点击加号
        inputGroupSendTextMessageListener(); //管理员发送文字
        inputGroupSendVoiceMessage(); //发送语音
        newMessageListener(); //监听接收消息


        //activity调用此方法的时候，fragment的布局可能还没创建完
        showInputGroup(isSpeaker);
        toastMsgList(toastMsgList);
        showDescLayout(mDescLayoutLiveDetailInfo);


    }

    private void inputGroupClickJiaButtonListener() {
        mSpeakerBottomInputGroup.setOnClickJiaButtonListener(new LiveClikcJiaToastCallback() {
            @Override
            public void clickJiaBtCallback() {
                if (StringUtils.isEmpty(LiveManager.getInstance().getUserInfoDto().getLoginIMSign())) {
                    ContextUtils.showNoLoginDialog(mActivity);
                    SystemUtils.hideSoftInput(mActivity);
                    return;
                }
                showAll();
            }

            @Override
            public void clickToastBtCallback() {
                showToastLayout();
            }
        });
    }

    private void showAll() {
        if (mLiveSendMsgDialog == null) {
            mLiveSendMsgDialog = new LiveSendMsgDialog(mActivity, new LiveSendMsgDialog.ClickBtListener() {
                @Override
                public void sendHB() {
                    mActivity.sendHB();
                }

                @Override
                public void sendPic() {
                    mSpeakerBottomInputGroup.hideSoftInput();
                    //调用系统相册
                    gallery();
                }

                @Override
                public void setting() {
                    settingManager();
                }

                @Override
                public void connectPT() {
                    contactPlatform();
                }
            });
        }

        mLiveSendMsgDialog.show();
    }

    public void queryLiveHistoryMsg() {
        if (mMsgInfoList != null)
            mMsgInfoList.clear();
        queryNewsLiveLessonsComment(1);
    }

    private void etContentTouchListener() {
        mNormal_Et_say_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mNormal_Et_say_content.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                    }
                }, 200);
                return false;
            }
        });

    }

    private void setRecyclerViewAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new LiveListAdapter(mActivity, mMsgInfoList, new LiveMsgCallback() {

            @Override
            public void clickHBCallback(final MessageInfo msg) {
                if (msg != null) {
                    showDefaultLoadingDialog();
                    CommonModel.getRedpacketState(msg.getHongbaoId(),
                            new MyObserver<HBStatusInfo>() {
                                @Override
                                public void onSuccess(BaseResponse<HBStatusInfo> t) {
                                    dismissLoadingDialog();
                                    if (t != null) {
                                        if (t.isSuccess() && t.getData() != null) {

                                            int receiveState = t.getData().getReceiveState();
                                            mActivity.dealHBstatus(receiveState, msg);
                                        } else {
                                            T.showShort(mActivity, t.getMsg());
                                        }
                                    }

                                }
                            });
                }
            }

            @Override
            public void clickHeadImageCallback(final MessageInfo msg) {
                if (mActivity != null && !mActivity.isFinishing()) {
                    mActivity.goUserView(msg.getMemberId());
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                KeyboardUtils.hideSoftInput(mActivity);
            }
        });

        //点击播放语音
        mAdapter.setOnSoundClickListener(new OnSoundClickListener() {
            @Override
            public void onSoundClickCallback(int position, final ImageView ivPlayer) {
                MessageInfo msg = mMsgInfoList.get(position);

                if (msg.isMySendMsg()) {
                    ivPlayer.setImageResource(R.drawable.play_sound_self_volume);
                } else {
                    ivPlayer.setImageResource(R.drawable.play_sound_volume);
                }

                final AnimationDrawable soundAnim = (AnimationDrawable) ivPlayer.getDrawable();

                if (mSoundCurrentLocation == mSoundStopLocation) { //说明当前没有语音在播放
                    playSound(msg, soundAnim, position);
                } else {
                    if (mSoundCurrentLocation == position) { //点击的是当前正在播放的语音，此时去暂停语音
                        stopSound();
                    }
                    //点击的是另一条语音，此时应先去暂停之前正在播放语音，然后再打开此条语音
                    else {
                        stopSound();
                        playSound(msg, soundAnim, position);
                    }
                }
            }
        });

        //点击图片放大图片
        mAdapter.setOnImageClickListener(new OnImageClickListener() {
            @Override
            public void onImageClickCallback(String originalImageUrl, String largeImageUrl) {
                showBigImage(largeImageUrl);
            }
        });

    }


    @Override
    public void onDestroy() {
        stopSound();
        super.onDestroy();
    }

    public void stopSound() {
        if (mCurrentSoundAnim != null) {
            mCurrentSoundAnim.stop();
            mCurrentSoundAnim.selectDrawable(0); //AnimationDrawable回到初始状态
        }
        if (MainApp.getPlayService1() != null)
            MainApp.getPlayService1().pause();
        mSoundCurrentLocation = mSoundStopLocation;
    }

    private void playSound(MessageInfo msg, final AnimationDrawable soundAnim, int position) {
        mSoundCurrentLocation = position;
        mCurrentSoundAnim = soundAnim;
        soundAnim.start();
        if (MainApp.getPlayService1() != null)
            MainApp.getPlayService1().play(msg.getDataPath(),
                    new PlayService.PlayCompletionCallback() {
                        @Override
                        public void onCompletion() {
                            soundAnim.stop();
                            mSoundCurrentLocation = mSoundStopLocation;
                        }
                    });

    }


    protected int mPageIndex = 1;
    protected int mPageSize = 99;
    protected boolean isCanLoadMore = false;

    private void initRefreshLayout(View view) {

        mSmartRefreshLayout = view.findViewById(R.id.smart_refresh_layout);
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mSmartRefreshLayout.setEnableLoadMore(false);

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                if (isCanLoadMore) {
                    queryNewsLiveLessonsComment(mPageIndex);
                } else {
                    finishRefresh();
                    T.showShort(mActivity, R.string.no_data_load_more);
                }

            }
        });

        mSmartRefreshLayout.setDisableContentWhenRefresh(true);
        mSmartRefreshLayout.setDisableContentWhenLoading(true);
    }

    protected void finishRefresh() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }


    private LiveDetailInfo mDescLayoutLiveDetailInfo;

    public void showDescLayout(LiveDetailInfo liveDetailInfo) {
        mDescLayoutLiveDetailInfo = liveDetailInfo;

        if (liveDetailInfo == null) {
            return;
        }

        //activity调用此方法的时候，fragment的布局可能还没创建完，所以需要判断空指针
        if (mLayout_notice != null && mTv_notice != null) {
            if (StringUtils.isEmpty(liveDetailInfo.getNoticeContent())) {
                mLayout_notice.setVisibility(View.GONE);
            } else {
                mLayout_notice.setVisibility(View.VISIBLE);
                mTv_notice.setText(liveDetailInfo.getNoticeContent());
            }
        }


        boolean isShow =
                liveDetailInfo.getIsSeeStatus() == 1 || liveDetailInfo.getIsSeeStatus() == 3;
        boolean isNeedBuy = liveDetailInfo.getIsSeeStatus() == 1;

        if (layout_desc != null && tv_desc != null) {
            if (isShow) {
                layout_desc.setVisibility(View.VISIBLE);
                if (isNeedBuy) {
                    tv_desc.setText("购买后才可以浏览该页面");
                } else {
                    tv_desc.setText("输入密码后才可以浏览该页面");
                }
            } else {
                layout_desc.setVisibility(View.GONE);
            }
        }


    }

    private void normalEtTextChangedListener() {
        mNormal_Et_say_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int count = editable.toString().length();
                if (count > 0) {
                    mNormal_Layout_bt.setVisibility(View.GONE);
                    mNormal_Tv_send.setVisibility(View.VISIBLE);
                } else {
                    mNormal_Layout_bt.setVisibility(View.VISIBLE);
                    mNormal_Tv_send.setVisibility(View.GONE);
                }
            }
        });

    }


    /**
     * 监听接收消息
     */
    private void newMessageListener() {

        LiveManager.getInstance().setOnMessageListener(new LiveManager.MessageListener() {
            @Override
            public void onNewMessages(List<TIMMessage> msgList) {

                if (ListUtils.isNotEmpty(msgList)) {
                    final TIMMessage lastMsg = msgList.get(0);


                    TIMConversation conversation = lastMsg.getConversation();
                    TIMConversationType type = conversation.getType();
                    if (type == TIMConversationType.Group) {
                        L.d(LiveManager.TAG,
                                "conversation: " + conversation + " ; conversation.getPeer():" +
                                        " " + conversation.getPeer());
                        if (conversation == null || conversation.getPeer() == null)
                            return;
                        UserInfoDto userInfoDto = LiveManager.getInstance().getUserInfoDto();
                        //                            final MessageInfo msgInfo =
                        MessageInfoUtil.TIMMessage2MessageInfo(lastMsg, userInfoDto,
                                true, true, new TCallback<MessageInfo>() {
                                    @Override
                                    public void callback(MessageInfo msgInfo) {
                                        if (msgInfo != null) {
                                            judgeMsgManageStatus(lastMsg, msgInfo);

                                            String content = msgInfo.getContent();

                                            if (content.contains(LiveConfig.BlockADM_LHB)
                                                    || content.contains(LiveConfig.BlockADM_PAY)
                                                    || content.contains(LiveConfig.BlockADM_CAN)
                                                    || content.contains(LiveConfig.BlockADM_BAN)) {
                                                msgInfo.setNormalMsg(true);
                                            }

                                            msgInfo.setContent(content);
                                            mMsgInfoList.add(msgInfo);
                                            scrollToEnd();
                                            L.d(LiveManager.TAG, "onNewMessages::: " + lastMsg);
                                        }
                                    }
                                });


                    }
                }
            }
        });
    }


    /**
     * 发送语音
     */
    private void inputGroupSendVoiceMessage() {

        mSpeakerBottomInputGroup.setMsgHandler(new SpeakerBottomInputGroup.MessageHandler() {
            @Override
            public void sendVoiceMessage(final MessageInfo messageInfo) {
                //                ChatPanel.this.sendNormalTextMessage(msg);

                LiveManager.getInstance().sendVoiceMessage(messageInfo,
                        new SendMessageCallback() {
                            @Override
                            public void onError(int code, String desc) {


                            }

                            @Override
                            public void onSuccess(TIMMessage msg) {
                                messageInfo.setSpeaker(mActivity.isSpeaker);
                                messageInfo.setMySendMsg(true);
                                messageInfo.setMemberId(LiveManager.getInstance().getMemberId());
                                mMsgInfoList.add(messageInfo);
                                getImageOrVoiceToken(messageInfo.getDataPath(),
                                        LiveConfig.VOICE_Content_Type, messageInfo);
                                mSpeakerBottomInputGroup.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        scrollToEnd();
                                    }
                                });
                            }
                        });

            }
        });
    }

    /**
     * 管理员发送文字
     */
    private void inputGroupSendTextMessageListener() {
        mSpeakerBottomInputGroup.setOnSendTextMessageListener(new SendMessageCallback() {
            @Override
            public void onError(int code, String desc) {

            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                UserInfoDto userInfoDto = LiveManager.getInstance().getUserInfoDto();

                MessageInfo msg = new MessageInfo();
                msg.setMsgType(MessageType.MSG_TYPE_TEXT);
                msg.setSpeaker(mActivity.isSpeaker);
                msg.setHeadImageUrl(userInfoDto.getIcon());
                msg.setName(userInfoDto.getNickName());
                msg.setMySendMsg(true);
                msg.setMemberId(LiveManager.getInstance().getMemberId());

                String content = "";

                if (timMessage.getElementCount() > 0) {
                    TIMTextElem timTextElem = (TIMTextElem) timMessage.getElement(0);

                    content = timTextElem.getText();
                    if (content != null && content.contains(LiveConfig.BlockADM_ZBM)) {
                        content = MessageInfoUtil.subContent(content);
                    }
                }

                msg.setContent(content);

                mActivity.addLiveNewsLiveLessonsComment(msg.getContent(),
                        LiveConfig.TEXT_Content_Type, 0);
                mMsgInfoList.add(msg);
                scrollToEnd();
            }
        });
    }

    private void judgeMsgManageStatus(TIMMessage lastMsg, MessageInfo msgInfo) {
        if (lastMsg != null) {
            TIMUserProfile timUserProfile = lastMsg.getSenderProfile();
            if (timUserProfile != null) {
                String identifier = timUserProfile.getIdentifier();
                if (identifier != null) {
                    String memberIdStr = identifier.substring(identifier.lastIndexOf("_") + 1);
                    if (mActivity.mLiveDetailInfo != null
                            && StringUtils.isEquals(mActivity.mLiveDetailInfo.getMemberId() + "",
                            memberIdStr)) {
                        msgInfo.setSpeaker(true);
                    }
                }
            }
        }


    }




    CommonDialog mDownDialog;

    private void contactPlatform() {
        mSpeakerBottomInputGroup.hideSoftInput();
        if (mDownDialog == null) {
            mDownDialog = new CommonDialog.Builder(mActivity)
                    .setTitle("联系平台客服可以更好为你的直播间维持秩序哦!")
                    .setMessage("客服微信号：BlockADM001")
                    .setPositiveButton("复制微信号", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CopyTextUtils.copyText(mActivity, "BlockADM001");
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
        }
        mDownDialog.show();
    }

    private void settingManager() {
        mSpeakerBottomInputGroup.hideSoftInput();
        Intent intent = new Intent(mActivity, LiveManagerActivity.class);
        intent.putExtra(LiveManagerActivity.LIVE_ID,
                LiveManager.getInstance().getNewsLiveLessonsId());
        if (mActivity.mLiveDetailInfo != null) {
            intent.putExtra(LiveManagerActivity.Live_Detail_Info, mActivity.mLiveDetailInfo);
        }
        startActivity(intent);
    }

    private int historyPosition = 0;


    /**
     * 直播界面历史消息
     */
    public void queryNewsLiveLessonsComment(final int pageIndex) {
        String json = GsonUtil.GsonString(new LiveHistoryParams("0",
                LiveManager.getInstance().getNewsLiveLessonsId(), pageIndex
                , mPageSize));

        L.d("直播界面历史消息 json: " + json);
        CommonModel.queryNewsLiveLessonsComment(json, new MyObserver<HistoryLiveLessonsListInfo>() {
            @Override
            public void onSuccess(BaseResponse<HistoryLiveLessonsListInfo> t) {
                finishRefresh();

                if (t.isSuccess()) {
                    HistoryLiveLessonsListInfo listInfo = t.getData();
                    historyPosition = mMsgInfoList.size();
                    if (listInfo != null) {
                        BeanChangeUtils.historyLiveLessonsList2MessageInfoList(listInfo,
                                mMsgInfoList);

                        if (mMsgInfoList.size() >= mPageIndex * mPageSize) { //说明可以加载更多
                            isCanLoadMore = true;
                            mPageIndex++;
                        } else {
                            isCanLoadMore = false;
                        }

                        if (pageIndex == 1) {
                            scrollToEnd();
                        } else {
                            if (mAdapter != null) {
                                mAdapter.notifyDataSetChanged();
                                if (mMsgInfoList.size() > 3)
                                    mRecyclerView.scrollToPosition(mMsgInfoList.size() - historyPosition + 3);
                            }
                        }

                    }
                }
            }
        });
    }

    private List<MessageInfo> toastMsgList;

    public void toastMsgList(List<MessageInfo> msgInfoList) {
        toastMsgList = msgInfoList;
        if (mActivity != null && !mActivity.isFinishing() && mLayout_toast != null) {
            mLayout_toast.showToastMsg(msgInfoList, mActivity);
        }

    }


    public void showInputGroup(boolean isSpeaker) {
        this.isSpeaker = isSpeaker;
        if (mSpeakerBottomInputGroup != null && mNormal_person_input_view != null) {
            if (isSpeaker) {
                mSpeakerBottomInputGroup.setVisibility(View.VISIBLE);
                mNormal_person_input_view.setVisibility(View.GONE);
            } else {
                mSpeakerBottomInputGroup.setVisibility(View.GONE);
                mNormal_person_input_view.setVisibility(View.VISIBLE);
            }
        }

    }

    private SpeakerBottomInputGroup.ChatInputHandler mChatInputHandler =
            new SpeakerBottomInputGroup.ChatInputHandler() {
                @Override
                public void popupAreaShow() {
                    L.d("xxx", " popupAreaShow =----: ");
                    scrollToEnd();
                }

                @Override
                public void popupAreaHide() {
                    L.d("xxx", " popupAreaHide =----: ");
                }

                @Override
                public void startRecording() {
                    L.d("xxx", " startRecording =----: ");
                    mRecordingIcon.setImageResource(R.drawable.recording_volume);
                    mVolumeAnim = (AnimationDrawable) mRecordingIcon.getDrawable();
                    mRecordingGroup.setVisibility(View.VISIBLE);
                    mVolumeAnim.start();

                    mRecordingTips.setTextColor(Color.WHITE);
                    mRecordingTips.setText("上滑取消录音");
                }

                @Override
                public void stopRecording() {
                    L.d("xxx", " stopRecording =----: ");
                    mSpeakerBottomInputGroup.post(new Runnable() {
                        @Override
                        public void run() {
                            mVolumeAnim.stop();
                            mRecordingGroup.setVisibility(View.GONE);
                        }
                    });

                }

                @Override
                public void tooShortRecording() {

                    L.d("xxx", " tooShortRecording =----: ");

                    mSpeakerBottomInputGroup.post(new Runnable() {
                        @Override
                        public void run() {
                            mVolumeAnim.stop();
                            mRecordingIcon.setImageResource(R.drawable.exclama);
                            mRecordingTips.setTextColor(Color.WHITE);
                            mRecordingTips.setText("说话时间太短");
                        }
                    });

                    mSpeakerBottomInputGroup.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRecordingGroup.setVisibility(View.GONE);
                        }
                    }, 1000);
                }

                @Override
                public void cancelRecording() {
                    L.d("xxx", " cancelRecording =----: ");
                    mRecordingIcon.setImageResource(R.drawable.recording_cancel);
                    mRecordingTips.setTextColor(Color.RED);
                    mRecordingTips.setText("松开手指，取消发送");
                }

                @Override
                public void endRecord() {
                    mSpeakerBottomInputGroup.post(new Runnable() {
                        @Override
                        public void run() {
                            mVolumeAnim.stop();
                            mRecordingGroup.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void endTime(final int time) {
                    mSpeakerBottomInputGroup.post(new Runnable() {
                        @Override
                        public void run() {
                            mRecordingTips.setText(time + " S");
                            mRecordingTips.setTextColor(Color.WHITE);
                        }
                    });
                }
            };

    private void showBigImage(String url) {
        Intent intent = new Intent(mActivity, ShowBigImageActivity.class);
        intent.putExtra(ShowBigImageActivity.Live_Msg_Info, url);
        mActivity.startActivity(intent);
    }


    /*
     * 从相册获取
     */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        L.d("222  requestCode: " + requestCode + " ; resultCode : " + resultCode + " ; data: " + data);
        if (requestCode == Activity.RESULT_FIRST_USER && resultCode == -1) {
            Uri uri = null;
            //相册
            if (data != null) {
                // 得到图片的全路径
                uri = data.getData();
                //
                String localPath = FileUtil.getPathFromUri(uri);
                getImageOrVoiceToken(localPath, LiveConfig.IMAGE_Content_Type, null);

                MessageInfo info = MessageInfoUtil.buildImageMessage(mActivity, uri, true, false);
                sendImageMessage(localPath, info);

                L.d(LiveManager.TAG,
                        " requestCode: " + requestCode + " ; resultCode : " + resultCode + " ; " +
                                "data: " + data);
                L.d(LiveManager.TAG, "  uri: " + uri + "\n uri.getPath(): " + uri.getPath() +
                        "\n localPath: " + localPath);

            }

        }

        //        else  if (requestCode == Activity.RESULT_FIRST_USER && resultCode == -2) {
        //            BlackToastDialog.showToastDialog(mActivity,true);
        //        }
    }

    private void getImageOrVoiceToken(final String localPath, final String contentType,
                                      MessageInfo msg) {

        try {
            int type = 0;
            if (StringUtils.isEquals(contentType, LiveConfig.VOICE_Content_Type)) {
                type = 5;
            }

            int duration = 1;
            if (msg != null) {
                final TIMSoundElem soundElem = (TIMSoundElem) msg.getTIMMessage().getElement(0);
                duration = (int) soundElem.getDuration();
            }


            final int finalDuration = duration;
            CommonModel.getImageUploadToken(type, new MyObserver<QiniuTokenParams>() {
                @Override
                public void onSuccess(BaseResponse<QiniuTokenParams> t) {
                    QiniuTokenParams qiniuTokenDto = t.getData();
                    try {
                        if (StringUtils.isEquals(contentType, LiveConfig.IMAGE_Content_Type)) {
                            uploadImage(qiniuTokenDto, localPath);
                        } else {
                            uploadVoice(qiniuTokenDto, localPath, finalDuration);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });

            L.d(LiveManager.TAG, " uploadImage new File(path): " + new File(localPath));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void uploadImage(final QiniuTokenParams qiniuTokenDto, final String localPath) throws IOException {
        CommonModel.uploadImage(new File(localPath), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.d(LiveManager.TAG,
                        "Send image uploadImage onFailure  e: " + e.toString() + " ; call: " + call);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    L.d(LiveManager.TAG,
                            "Send image uploadImage success ");
                    String imageServiceUrl = qiniuTokenDto.getSaveFullPath(); //
                    mActivity.addLiveNewsLiveLessonsComment(imageServiceUrl,
                            LiveConfig.IMAGE_Content_Type, 0);
                } else {
                    L.d(LiveManager.TAG,
                            "Send image uploadImage fail  code: " + response.code() + " ; " +
                                    "response: " + response.body().toString());
                }

            }
        }, qiniuTokenDto.getUploadToken());
    }

    private void uploadVoice(final QiniuTokenParams qiniuTokenDto, final String localPath,
                             final int duration) {

        try {

            L.d(LiveManager.TAG, " uploadVoice new File(path): " + new File(localPath));
            CommonModel.uploadVoice(new File(localPath), qiniuTokenDto.getUploadToken(),
                    new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            L.d(LiveManager.TAG,
                                    "Send Voice uploadVoice onFailure  e: " + e.toString() + " ; " +
                                            "call: " + call);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.code() == 200) {

                                L.d(LiveManager.TAG, "Send image uploadVoice success: ");
                                String imageServiceUrl = qiniuTokenDto.getSaveFullPath(); //

                                mActivity.addLiveNewsLiveLessonsComment(imageServiceUrl,
                                        LiveConfig.VOICE_Content_Type, duration);

                            } else {
                                L.d(LiveManager.TAG,
                                        "Send image uploadVoice fail  code: " + response.code() + " ; " +
                                                "response: " + response.body().toString() + " ; " +
                                                "message: "
                                                + response.message());
                            }

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendImageMessage(String path, final MessageInfo info) {
        LiveManager.getInstance().sendImageMessage(path, new SendMessageCallback() {
            @Override
            public void onError(int code, String desc) {

            }

            @Override
            public void onSuccess(TIMMessage msg) {
                info.setSpeaker(mActivity.isSpeaker);
                info.setMySendMsg(true);
                info.setMemberId(LiveManager.getInstance().getMemberId());
                mMsgInfoList.add(info);
                scrollToEnd();
            }
        });
    }


    private void scrollToEnd() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
        }
    }

    public void adapterChange(MessageInfo info) {
        mMsgInfoList.add(info);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
        }
    }


    private boolean isshowToastLayout = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_reward:
                mActivity.goReward();
                break;
            case R.id.iv_ask:
                mActivity.askQuestions();
                break;
            case R.id.iv_normal_show:
                showToastLayout();
                break;
            case R.id.text_send:
                if (StringUtils.isEmpty(LiveManager.getInstance().getUserInfoDto().getLoginIMSign())) {
                    ContextUtils.showNoLoginDialog(mActivity);
                    SystemUtils.hideSoftInput(mActivity);
                    return;
                }

                if (mActivity.isForbid) {
                    T.showShort(mActivity, "您已经被禁言，无法发送消息");
                    return;
                }
                String content = mNormal_Et_say_content.getText().toString().trim();
                sendNormalTextMessage(content);
                break;
        }
    }

    private void showToastLayout() {
        if (!isshowToastLayout) {
            //ic_show_text_no
            mLayout_toast.setVisibility(View.VISIBLE);
            mNormal_Iv_show.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_show_text));
        } else {
            mLayout_toast.setVisibility(View.GONE);
            mNormal_Iv_show.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_show_text_no));
        }
        isshowToastLayout = !isshowToastLayout;
    }


    /**
     * 普通用户发送文字消息
     *
     * @param content
     */
    private void sendNormalTextMessage(final String content) {
        if (StringUtils.isEmpty(content)) {
            return;
        }

        LiveManager.getInstance().sendTextMessage(content, new SendMessageCallback() {
            @Override
            public void onError(int i, String s) {
                mNormal_Et_say_content.setText("");
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {

                UserInfoDto userInfoDto = LiveManager.getInstance().getUserInfoDto();
                MessageInfo msg = new MessageInfo();
                msg.setHeadImageUrl(userInfoDto.getIcon());
                msg.setName(userInfoDto.getNickName());
                msg.setContent(content);
                msg.setMySendMsg(true);

                mActivity.addCommunityNewsLiveLessonsComment(content, "0", 0);

                mActivity.addLiveMsgList(msg);

                mNormal_Et_say_content.setText("");
            }
        });
    }
}
