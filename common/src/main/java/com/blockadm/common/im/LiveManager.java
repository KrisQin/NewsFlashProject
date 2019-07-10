package com.blockadm.common.im;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.config.LiveConfig;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.http.RetrofitManager;
import com.blockadm.common.im.call.HistoryMessageCallback;
import com.blockadm.common.im.call.LoginCallBack;
import com.blockadm.common.im.call.LogoutCallBack;
import com.blockadm.common.im.call.SendMessageCallback;
import com.blockadm.common.im.entity.MessageInfo;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.T;
import com.blockadm.common.wxpay.Constants;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMImageElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMSoundElem;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.imsdk.session.SessionWrapper;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kris on 2019/5/21
 *
 * @Describe TODO { 直播群聊管理类 }
 */
public class LiveManager {

    public static final String TAG = "tim_test";//直播页面
    public static final String TAG_C = "tim_C"; //交流页面

    private Context mContext;
    private List<String> mManagerIdentifierList; //管理员账号列表
    private List<String> mManagerNameList; //管理员名字列表
    private UserInfoDto mUserInfoDto;
    private int memberId;
    private boolean isCurrentUserSpeakerOrManager; //判断当前用户是管理员或主讲人还是普通用户
    private int newsLiveLessonsId;
    private String groupId;
    private int liveStatus = -1; //0 直播中、1：预备中（未开始）、2：已结束

    public static final int LIVE_START = 0;
    public static final int LIVE_PRE = 1;
    public static final int LIVE_OVER = 2;

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getNewsLiveLessonsId() {
        return newsLiveLessonsId;
    }

    public void setNewsLiveLessonsId(int newsLiveLessonsId) {
        this.newsLiveLessonsId = newsLiveLessonsId;
    }

    public List<String> getManagerNameList() {
        return mManagerNameList;
    }

    public void setManagerNameList(List<String> managerNameList) {
        mManagerNameList = managerNameList;
    }

    public void setCurrentUserSpeakerOrManager(boolean speakerOrManager) {
        isCurrentUserSpeakerOrManager = speakerOrManager;
    }

    public UserInfoDto getUserInfoDto() {
        if (mUserInfoDto == null || StringUtils.isEmpty(mUserInfoDto.getLoginIMSign())) {
            RetrofitManager.
                    getService().
                    getUserData().
                    observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new MyObserver<UserInfoDto>() {
                        @Override
                        public void onSuccess(BaseResponse<UserInfoDto> userInfoDtoBaseResponse) {
                            if (userInfoDtoBaseResponse.isSuccess()) {
                                mUserInfoDto = userInfoDtoBaseResponse.getData();
                            }
                        }
                    });

        }

        if (mUserInfoDto == null) {
            return new UserInfoDto();
        }

        return mUserInfoDto;
    }

    public void setUserInfoDto(UserInfoDto userInfoDto) {
        mUserInfoDto = userInfoDto;
    }

    public void setManagerIdentifierList(List<String> managerIdentifierList) {
        mManagerIdentifierList = managerIdentifierList;
    }

    /**
     * 判断当前这个消息是管理员或主讲人发出，还是普通用户发出
     * @param timMessage
     * @return
     */
    public boolean isSpeakerOrManagerMsg(TIMMessage timMessage) {
        if (timMessage != null && mManagerIdentifierList != null) {
            TIMUserProfile timUserProfile = timMessage.getSenderProfile();
            if (timUserProfile != null) {
                String identifier = timUserProfile.getIdentifier();
                if (mManagerIdentifierList.contains(identifier)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    private LiveManager() {
    }

    public static LiveManager getInstance() {
        return SingletonHolder.instance;
    }

    //内部类，在装载该内部类时才会去创建单利对象
    private static class SingletonHolder {
        public static LiveManager instance = new LiveManager();
    }


    /**
     * 社群聊天SDK初始化
     * @param application
     */
    public void initSdk(Application application) {

        if (application == null) {
            return;
        }

        TUIKit.init(application,BaseUIKitConfigs.getDefaultConfigs());

        int sdkAppId =  Constants.SDKAPPID;
        //初始化 SDK 基本配置
        //判断是否是在主线程
        if (SessionWrapper.isMainProcess(application)) {
            TIMSdkConfig config = new TIMSdkConfig(sdkAppId)
                    .enableCrashReport(false)
                    .enableLogPrint(true)
                    .setLogLevel(TIMLogLevel.DEBUG)
                    .setLogPath(Environment.getExternalStorageDirectory().getPath() +
                            "/justfortest/");

            //初始化 SDK
            TIMManager.getInstance().init(application, config);
        }

        setTIMUserConfig();
    }

    private void setTIMUserConfig() {

        TIMUserConfig timUserConfig = new TIMUserConfig();
        //设置用户状态变更事件监听器
        timUserConfig.setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                //被其他终端踢下线
                isTimUserLoginSuccess = false;
            }

            @Override
            public void onUserSigExpired() {
                //用户签名过期了，需要刷新 userSig 重新登录 SDK
                isTimUserLoginSuccess = false;
            }
        })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {

                    }

                    @Override
                    public void onDisconnected(int i, String s) {
                        isTimUserLoginSuccess = false;
                    }

                    @Override
                    public void onWifiNeedAuth(String s) {

                    }
                })
                //设置群组事件监听器
                .setGroupEventListener(new TIMGroupEventListener() {
                    @Override
                    public void onGroupTipsEvent(TIMGroupTipsElem timGroupTipsElem) {

                    }
                })
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {

                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> list) {

                    }
                });

        //消息扩展用户配置
        timUserConfig = new TIMUserConfigMsgExt(timUserConfig)
                //禁用消息存储
                .enableStorage(false)
                //开启消息已读回执
                .enableReadReceipt(true);
        //将用户配置与通讯管理器进行绑定
        TIMManager.getInstance().setUserConfig(timUserConfig);

        //        //初始化本地存储
        //        TIMManagerExt.getInstance().initStorage(identifier, new TIMCallBack() {
        //            @Override
        //            public void onError(int code, String desc) {
        ////                Log.e(tag, "initStorage failed, code: " + code + "|descr: " + desc);
        //            }
        //
        //            @Override
        //            public void onSuccess() {
        ////                Log.i(tag, "initStorage succ");
        //            }
        //        });

    }



    /**
     * 获取群聊会话
     *
     * @param groupId 群主id
     * @return
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
        L.d(TAG," groupId: "+groupId);

    }

    public String getGroupId() {
        return groupId;
    }

    private TIMConversation getTIMConversation() {
        L.d(TAG," getTIMConversation groupId: "+groupId);
        TIMConversation timConversation = TIMManager.getInstance().getConversation(
                TIMConversationType.Group,      //会话类型：群组
                groupId);
        return timConversation;
    }

    private void showErrorToast(int code, String desc) {
        if (mContext != null) {
            T.showShort(mContext,"code: "+code+" errmsg: "+desc);
        }
    }

    public void getHistoryMessage(int index,final HistoryMessageCallback callback) {

        //
        //获取会话扩展实例
        TIMConversationExt conExt = new TIMConversationExt(getTIMConversation());

        //获取此会话的消息
        conExt.getMessage(index * LiveConfig.Page_Load_Num, //获取此会话最近的 Page_Load_Num 条消息
                null, //不指定从哪条消息开始获取 - null等同于从最新的消息开始往前
                new TIMValueCallBack<List<TIMMessage>>() {//回调接口
                    @Override
                    public void onError(int code, String desc) {//获取消息失败

                        showErrorToast(code,desc);

                        if (callback != null)
                        callback.onError(code, desc);
                        //接口返回了错误码 code 和错误描述 desc，可用于定位请求失败原因
                        //错误码 code 含义请参见错误码表
                        L.d(TAG, "get message failed. code: " + code + " errmsg: " + desc);
                    }

                    @Override
                    public void onSuccess(List<TIMMessage> msgs) {//获取消息成功
                        Collections.reverse(msgs);
                        //                        TIMMessage lastMsg = msgs.get(msgs.size() - 1);

                        if (callback != null)
                        callback.onSuccess(msgs);
                        //遍历取得的消息
                        //                        for(TIMMessage msg : msgs) {
                        ////                            lastMsg = msg;
                        ////                            //可以通过 timestamp()获得消息的时间戳, isSelf()
                        // 是否为自己发送的消息
                        ////                            L.d(TAG, "get msg: " + msg.timestamp() +
                        // " self: " + msg.isSelf() + " seq: " + msg.msg.seq());
                        ////
                        ////                        }
                    }
                });
    }

    /**
     * 发送语音
     * @param messageInfo
     * @param callback
     */
    public void sendVoiceMessage(MessageInfo messageInfo, final SendMessageCallback callback) {

        //String filePath
        //构造一条消息
        TIMMessage msg = new TIMMessage();

        //添加语音
        TIMSoundElem elem = new TIMSoundElem();
        elem.setPath(messageInfo.getDataPath()); //填写语音文件路径

        TIMMessage timMsg = messageInfo.getTIMMessage();

        long elementCount = timMsg.getElementCount();

        L.d(UIKitAudioArmMachine.time_tag, "elementCount : " + elementCount);
        if (elementCount > 0) {
            TIMSoundElem soundElem = (TIMSoundElem) timMsg.getElement(0);
            long duration = soundElem.getDuration();
            elem.setDuration(duration);  //填写语音时长
            L.d(UIKitAudioArmMachine.time_tag, "语音时长 : " + duration);
        }

        //将 elem 添加到消息
        if (msg.addElement(elem) != 0) {
            L.d(TAG, "addElement failed");
            return;
        }

        TIMConversation timConversation = getTIMConversation();
        if (timConversation != null) {
            //发送消息
            timConversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
                @Override
                public void onError(int code, String desc) {//发送消息失败
                    showErrorToast(code,desc);
                    //错误码code和错误描述desc，可用于定位请求失败原因
                    //错误码code含义请参见错误码表
                    L.d(TAG, "send voice failed. code: " + code + " errmsg: " + desc);
                    if (callback != null)
                    callback.onError(code, desc);
                }

                @Override
                public void onSuccess(TIMMessage msg) {//发送消息成功
                    L.d(TAG, "Send voice Msg ok");
                    if (callback != null)
                    callback.onSuccess(msg);
                }
            });
        }
    }

    /**
     * 发送图片
     *
     * @param localPath
     * @param callback
     */
    public void sendImageMessage(String localPath, final SendMessageCallback callback) {

        L.d(TAG, "localPath: " + localPath);
        //构造一条消息
        TIMMessage msg = new TIMMessage();

        //添加图片
        TIMImageElem elem = new TIMImageElem();
        //        elem.setPath(Environment.getExternalStorageDirectory() + "/DCIM/Camera/1.jpg");
        elem.setPath(localPath);


        //        String path = FileUtil.getPathFromUri(uri);
        //        ele.setPath(path);
        //        info.setDataPath(path);

        //将 elem 添加到消息
        if (msg.addElement(elem) != 0) {
            L.d(TAG, "addElement failed");
            return;
        }

        TIMConversation timConversation = getTIMConversation();
        if (timConversation != null) {
            //发送消息
            timConversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
                @Override
                public void onError(int code, String desc) {//发送消息失败
                    showErrorToast(code,desc);
                    //错误码 code 和错误描述 desc，可用于定位请求失败原因
                    //错误码 code 列表请参见错误码表
                    L.d(TAG, "send image msg failed. code: " + code + " errmsg: " + desc);
                    if (callback != null)
                    callback.onError(code, desc);
                }

                @Override
                public void onSuccess(TIMMessage msg) {//发送消息成功
                    L.d(TAG, "Send image Msg ok");

                    if (callback != null)
                    callback.onSuccess(msg);
                }
            });
        }
    }

    /**
     * 发送文字消息
     * @param content
     * @param isLiveView true:直播间发出的消息
     * @param callback
     */
    public void sendTextMessage(String content,boolean isLiveView,final SendMessageCallback callback) {

        //构造一条消息
        TIMMessage msg = new TIMMessage();

        //添加文本内容
        TIMTextElem elem = new TIMTextElem();

        if (isCurrentUserSpeakerOrManager) {
            if (isLiveView) {
                elem.setText(content+LiveConfig.BlockADM_ZBM);
            }else {
                elem.setText(content+LiveConfig.BlockADM_JLM);
            }

        }else {
            elem.setText(content);
        }

        //将elem添加到消息
        if (msg.addElement(elem) != 0) {
            L.d(TAG, "addElement failed");
            return;
        }

        TIMConversation timConversation = getTIMConversation();
        if (timConversation != null) {
            //发送消息
            timConversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
                @Override
                public void onError(int code, String desc) {//发送消息失败
                    showErrorToast(code,desc);
                    //错误码 code 和错误描述 desc，可用于定位请求失败原因
                    //错误码 code 含义请参见错误码表
                    L.d(TAG, "send text msg failed. code: " + code + " errmsg: " + desc);

                    if (callback != null)
                        callback.onError(code, desc);
                }

                @Override
                public void onSuccess(TIMMessage msg) {//发送消息成功
                    L.d(TAG, "Send text Msg ok");

                    if (callback != null)
                        callback.onSuccess(msg);
                }
            });
        }

    }


    /**
     * 发送文字消息
     *
     * @param content  消息
     * @param callback 回调
     */
    public void sendTextMessage(String content,final SendMessageCallback callback) {

        //构造一条消息
        TIMMessage msg = new TIMMessage();

        //添加文本内容
        TIMTextElem elem = new TIMTextElem();

        elem.setText(content);

        //将elem添加到消息
        if (msg.addElement(elem) != 0) {
            L.d(TAG, "addElement failed");
            return;
        }

        TIMConversation timConversation = getTIMConversation();
        if (timConversation != null) {
            //发送消息
            timConversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
                @Override
                public void onError(int code, String desc) {//发送消息失败
                    showErrorToast(code,desc);
                    //错误码 code 和错误描述 desc，可用于定位请求失败原因
                    //错误码 code 含义请参见错误码表
                    L.d(TAG, "send text msg failed. code: " + code + " errmsg: " + desc);

                    if (callback != null)
                    callback.onError(code, desc);
                }

                @Override
                public void onSuccess(TIMMessage msg) {//发送消息成功
                    L.d(TAG, "Send text Msg ok");

                    if (callback != null)
                    callback.onSuccess(msg);
                }
            });
        }

    }

    public interface MessageListener {
        void onNewMessages(List<TIMMessage> msgList);
    }

    public void setOnMessageListener(final MessageListener messageListener) {
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> msgList) {
                messageListener.onNewMessages(msgList);

                return false;
            }
        });
    }

    private boolean isTimUserLoginSuccess = false;
    public void login(final String identifier, final String userSig, final LoginCallBack callBack) {
        if (!isTimUserLoginSuccess) {
            timLogin(identifier, userSig,callBack);
        }
    }

    private void timLogin(final String identifier, final String userSig, final LoginCallBack callBack) {
        // identifier为用户名，userSig 为用户登录凭证
        TIMManager.getInstance().login(identifier, userSig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                showErrorToast(code,desc);
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                if (callBack != null) {
                    callBack.onError(code, desc);
                }
            }

            @Override
            public void onSuccess() {

                isTimUserLoginSuccess = true;
                SharedpfTools.getInstance().put(SharedpfTools.identifier, identifier);
                SharedpfTools.getInstance().put(SharedpfTools.userSig, userSig);

                if (callBack != null) {
                    callBack.onSuccess();
                }
            }
        });
    }

    /**
     * 群聊登出
     *
     * @param callBack
     */
    public void logout(final LogoutCallBack callBack) {

        //登出
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                showErrorToast(code,desc);
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                if (callBack != null) {
                    callBack.onError(code, desc);
                }
            }

            @Override
            public void onSuccess() {
                //登出成功
                if (callBack != null) {
                    callBack.onSuccess();
                }
            }
        });
    }


}
