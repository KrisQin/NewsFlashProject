package com.blockadm.adm.im_module.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.im_module.activity.LiveDetailActivity;
import com.blockadm.adm.im_module.adapter.CommunicationAdapter;
import com.blockadm.adm.im_module.utils.BeanChangeUtils;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComFragment;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.live.param.BlackARemoveParams;
import com.blockadm.common.bean.live.responseBean.HistoryLiveLessonsInfo;
import com.blockadm.common.bean.live.responseBean.HistoryLiveLessonsListInfo;
import com.blockadm.common.bean.live.responseBean.LiveDetailInfo;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.im.LiveManager;
import com.blockadm.common.im.call.SendMessageCallback;
import com.blockadm.common.im.call.TCallback;
import com.blockadm.common.im.entity.MessageInfo;
import com.blockadm.common.im.entity.MessageType;
import com.blockadm.common.im.utils.MessageInfoUtil;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.KeyboardUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.ListUtils;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.SystemUtils;
import com.blockadm.common.utils.T;
import com.blockadm.common.widget.MarqueeText;
import com.blockadm.common.widget.RoundRectLayout;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kris on 2019/5/8
 *
 * @Describe TODO { 交流界面 }
 */
public class CommunicationFragment extends BaseComFragment {

    private RecyclerView mRecyclerView;
    private EditText mEt_say_content;
    private TextView mTv_send;
    private View layout_desc;
    private TextView tv_desc;
    private MarqueeText mTv_notice;

    private List<MessageInfo> mMsgInfoList = new ArrayList<>();
    private LiveDetailActivity mActivity;
    private CommunicationAdapter mAdapter;
    private ArrayList<String> mPopList;

    private View mLayout_notice;

    @Override
    public int getLayoutId() {
        return R.layout.communication_fragment;
    }


    @Override
    protected void initView(View view) {
        mActivity = (LiveDetailActivity) getActivity();
        mRecyclerView = view.findViewById(R.id.recycler_view);

        layout_desc = view.findViewById(R.id.layout_desc);
        tv_desc = view.findViewById(R.id.tv_desc);
        mEt_say_content = view.findViewById(R.id.et_say_content);
        mTv_send = view.findViewById(R.id.tv_send);
        mTv_notice = view.findViewById(R.id.tv_notice);
        mLayout_notice = view.findViewById(R.id.layout_notice);

        showDescLayout(mLiveDetailInfo);

        mPopList = new ArrayList<>();
        mPopList.add(CANCEL_LIMIT);


        mEt_say_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mEt_say_content.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                    }
                }, 200);
                return false;
            }
        });

        setRecyclerViewAdapter();


        mTv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEt_say_content.getText().toString().trim();
                sendTextMsg(content);
            }
        });

        newMsgReceiveListener();

    }

    private void setRecyclerViewAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CommunicationAdapter(mActivity, mMsgInfoList, mActivity.isSpeaker,
                new CommunicationAdapter.ClickCallback() {
                    @Override
                    public void clickDianView(RoundRectLayout layoutLimit, TextView textView,
                                              int position) {
                        MessageInfo info = mMsgInfoList.get(position);
                        findDetail(info, layoutLimit, textView);
                    }

                    @Override
                    public void clickLimitLayout(int position) {
                        MessageInfo info = mMsgInfoList.get(position);
                        if (mRoundRectLayout != null) {
                            mRoundRectLayout.setVisibility(View.GONE);
                        }
                        if (StringUtils.isEquals(mCurrentLimitText, LIMIT_TEXT)) {
                            addOrRemove(info, "0");
                        } else {
                            addOrRemove(info, "1");
                        }

                    }

                    @Override
                    public void clickHeadImage(MessageInfo msg) {
                        if (mActivity != null && !mActivity.isFinishing()) {
                            mActivity.goUserView(msg.getMemberId());
                        }
                    }
                });
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnRecycleViewItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mRoundRectLayout != null) {
                    mRoundRectLayout.setVisibility(View.GONE);
                    KeyboardUtils.hideSoftInput(mActivity);
                }
            }
        });
    }

    public void setSpeaker(boolean isSpeaker) {
        if (mAdapter != null) {
            mAdapter.setSpeaker(isSpeaker);
            mAdapter.notifyDataSetChanged();
        }
    }

    private LiveDetailInfo mLiveDetailInfo;

    public void showDescLayout(LiveDetailInfo liveDetailInfo) {

        if (liveDetailInfo == null) {
            return;
        }

        mLiveDetailInfo = liveDetailInfo;

        //showDescLayout方法被调用的时候，布局可以还没创建完成，导致mLayout_notice，mTv_notice可能还为空
        if (mLayout_notice != null && mTv_notice != null) {
            if (StringUtils.isEmpty(liveDetailInfo.getNoticeContent())) {
                mLayout_notice.setVisibility(View.GONE);
            } else {
                mLayout_notice.setVisibility(View.VISIBLE);
                mTv_notice.setText(liveDetailInfo.getNoticeContent());
            }
        }

        if (layout_desc != null && tv_desc != null) {
            boolean isShow =
                    liveDetailInfo.getIsSeeStatus() == 1 || liveDetailInfo.getIsSeeStatus() == 3;
            boolean isNeedBuy = liveDetailInfo.getIsSeeStatus() == 1;
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

    private String mCurrentLimitText = "";
    private RoundRectLayout mRoundRectLayout;

    private void findDetail(final MessageInfo info, final RoundRectLayout layoutLimit,
                            final TextView textView) {

        CommonModel.findDetail(LiveManager.getInstance().getNewsLiveLessonsId() + "", info.getMemberId()+"", "1", new MyObserver<Integer>() {
            @Override
            public void onSuccess(BaseResponse t) {

                if (t.getCode() == 0) {
                    mPopList.clear();
                    if (mRoundRectLayout != null) {
                        mRoundRectLayout.setVisibility(View.GONE);
                    }
                    layoutLimit.setVisibility(View.VISIBLE);
                    mRoundRectLayout = layoutLimit;

                    int data = (int) t.getData();
                    if (data == 0) { //0为未禁言
                        mCurrentLimitText = LIMIT_TEXT;
                    } else {
                        mCurrentLimitText = CANCEL_LIMIT;
                    }

                    textView.setText(mCurrentLimitText);
                    //                    showLimitPupW(info, view);
                } else {
                    T.showShort(mActivity, t.getMsg());
                }


            }
        });
    }

    private static final String LIMIT_TEXT = "禁言";
    private static final String CANCEL_LIMIT = "取消禁言";


    /**
     * @param info
     * @param choose 0:加入【禁言、黑名单】，1：移除【群组、禁言、黑名单】
     */
    private void addOrRemove(final MessageInfo info, final String choose) {

        String json = GsonUtil.GsonString(new BlackARemoveParams(choose,
                info.getMemberId(), LiveManager.getInstance().getNewsLiveLessonsId(), "1"));
        L.d("addOrRemove json: " + json);
        CommonModel.addOrRemove(json, new MyObserver() {
            @Override
            public void onSuccess(BaseResponse t) {
                if (t.getCode() == 0) {
                    MessageInfo messageInfo = new MessageInfo();
                    messageInfo.setNormalMsg(true);
                    messageInfo.setLimit(true);
                    if (StringUtils.isEquals(choose, "0")) {
                        messageInfo.setContent("昵称" + info.getName() + "已被禁言");
                    } else {
                        messageInfo.setContent("昵称" + info.getName() + "已被取消禁言");
                    }

                    mMsgInfoList.add(messageInfo);
                    scrollToEnd();
                } else {
                    T.showShort(mActivity, t.getMsg());
                }
            }
        });
    }


    /**
     * 查询当前直播课程所有历史交流信息
     */
    public void findAllExchangeCommentList() {
        CommonModel.findAllExchangeCommentList(LiveManager.getInstance().getNewsLiveLessonsId(), new MyObserver<List<HistoryLiveLessonsInfo>>() {
            @Override
            public void onSuccess(BaseResponse<List<HistoryLiveLessonsInfo>> t) {

                if (t != null && t.isSuccess()) {
                    HistoryLiveLessonsListInfo listInfo = new HistoryLiveLessonsListInfo();

                    List<HistoryLiveLessonsInfo> list = t.getData();
                    Collections.reverse(list);
                    listInfo.setRecords(list);
                    BeanChangeUtils.historyLiveLessonsList2MessageInfoList(listInfo,mMsgInfoList);
                    scrollToEnd();
                }

            }
        });
    }



    /**
     * 监听接收消息
     */
    private void newMsgReceiveListener() {
        LiveManager.getInstance().setOnMessageListener(new LiveManager.MessageListener() {
            @Override
            public void onNewMessages(List<TIMMessage> msgList) {

                if (ListUtils.isNotEmpty(msgList)) {
                    TIMMessage lastMsg = msgList.get(0);

                    TIMConversation conversation = lastMsg.getConversation();
                    TIMConversationType type = conversation.getType();

                    if (type == TIMConversationType.Group) {
                        L.d(LiveManager.TAG_C, "conversation: " + conversation + " ; " +
                                "conversation" +
                                ".getPeer(): " + conversation.getPeer());
                        if (conversation == null || conversation.getPeer() == null)
                            return;
                        if (lastMsg.getElementCount() > 0) {
                            TIMElem ele = lastMsg.getElement(0);
                            //获取当前元素的类型
                            TIMElemType elemType = ele.getType();
                            L.d(LiveManager.TAG_C, " 当前元素的类型: " + elemType);
                            if (elemType == TIMElemType.Text) {//处理文本消息
                                UserInfoDto userInfoDto =
                                        LiveManager.getInstance().getUserInfoDto();
                                MessageInfoUtil.TIMMessage2MessageInfo(lastMsg,
                                        userInfoDto, true, false, new TCallback<MessageInfo>() {
                                            @Override
                                            public void callback(MessageInfo msgInfo) {
                                                if (msgInfo != null) {
                                                    mMsgInfoList.add(msgInfo);
                                                    scrollToEnd();
                                                }
                                            }
                                        });


                            }
                        }

                        L.d(LiveManager.TAG_C, "onNewMessages  lastMsg::: " + lastMsg);
                    }
                }
            }
        });
    }


    private void sendTextMsg(final String content) {

        if (StringUtils.isEmpty(LiveManager.getInstance().getUserInfoDto().getLoginIMSign())) {
            ContextUtils.showNoLoginDialog(mActivity);
            SystemUtils.hideSoftInput(mActivity);
            return;
        }

        if (StringUtils.isEmpty(content)) {
            return;
        }

        if (mActivity.isForbid) {
            T.showShort(mActivity, "您已经被禁言，无法发送消息");
            return;
        }

        LiveManager.getInstance().sendTextMessage(content, false,
                new SendMessageCallback() {
                    @Override
                    public void onError(int i, String s) {
                        mEt_say_content.setText("");
                    }

                    @Override
                    public void onSuccess(TIMMessage timMessage) {

                        mActivity.addCommunityNewsLiveLessonsComment(content,"0",0);

                        UserInfoDto userInfoDto = LiveManager.getInstance().getUserInfoDto();

                        MessageInfo msg = new MessageInfo();
                        msg.setMsgType(MessageType.MSG_TYPE_TEXT);
                        msg.setHeadImageUrl(userInfoDto.getIcon());
                        msg.setName(userInfoDto.getNickName());
                        msg.setContent(content);
                        msg.setMySendMsg(true);
                        msg.setMemberId(LiveManager.getInstance().getMemberId());

                        mMsgInfoList.add(msg);
                        scrollToEnd();

                        mEt_say_content.setText("");
                    }
                });
    }

    public void addLiveMsgList(MessageInfo info) {
        mMsgInfoList.add(info);
        scrollToEnd();
    }

    private void scrollToEnd() {
        if (mActivity == null) {
            mActivity = (LiveDetailActivity) getActivity();
        }

        mActivity.toastMsgList(mMsgInfoList);
        if (mAdapter != null && mRecyclerView != null) {
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
        }

        L.d(LiveManager.TAG_C, "scrollToEnd mMsgInfoList.size(): " + mMsgInfoList.size());
    }


}
