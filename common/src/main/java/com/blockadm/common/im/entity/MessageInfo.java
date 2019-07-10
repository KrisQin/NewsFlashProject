package com.blockadm.common.im.entity;

import android.net.Uri;

import com.blockadm.common.utils.StringUtils;

import java.io.Serializable;
import java.util.UUID;


public class MessageInfo implements Serializable {

    private String peer;
    private String msgId = UUID.randomUUID().toString();
    private String fromUser;
    private int msgType;
    private int status = MessageType.MSG_STATUS_NORMAL;
    private boolean self;
    private boolean read;
    private boolean group;
    private Uri dataUri;
    private String dataPath;
    private Object extra;
    private long msgTime;
    private int imgWithd;
    private int imgHeight;
    private String name; //名字
    private String headImageUrl;//头像url
    private String content;//发送的文字内容
    private int memberId;
    private boolean isNormalMsg; //true:打赏或者禁言
    private boolean isReward; //打赏
    private boolean isLimit; //禁言
    private boolean isSendHongbao; //发红包
    private boolean isReceiveHongbao; //领红包
    private boolean isAskQuestion; //提问
    private boolean isMySendMsg; //本人发出的消息
    private int playTime; // 语音时长
    private int hongbaoId; //红包id
    private boolean isSpeaker;
    private boolean isHistoryMsg; //直播界面的历史消息是后台返回，不是通过聊天SDK返回

    public boolean isSpeaker() {
        return isSpeaker;
    }

    public void setSpeaker(boolean speaker) {
        isSpeaker = speaker;
    }

    public int getHongbaoId() {
        return hongbaoId;
    }

    public void setHongbaoId(int hongbaoId) {
        this.hongbaoId = hongbaoId;
    }

    public boolean isMySendMsg() {
        return isMySendMsg;
    }

    public void setMySendMsg(boolean mySendMsg) {
        isMySendMsg = mySendMsg;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }



    public boolean isHistoryMsg() {
        return isHistoryMsg;
    }

    public void setHistoryMsg(boolean historyMsg) {
        isHistoryMsg = historyMsg;
    }

    public boolean isAskQuestion() {
        return isAskQuestion;
    }

    public void setAskQuestion(boolean askQuestion) {
        isAskQuestion = askQuestion;
    }

    public boolean isReward() {
        return isReward;
    }

    public void setReward(boolean reward) {
        isReward = reward;
    }

    public boolean isLimit() {
        return isLimit;
    }

    public void setLimit(boolean limit) {
        isLimit = limit;
    }

    public boolean isSendHongbao() {
        return isSendHongbao;
    }

    public void setSendHongbao(boolean sendHongbao) {
        isSendHongbao = sendHongbao;
    }

    public boolean isReceiveHongbao() {
        return isReceiveHongbao;
    }

    public void setReceiveHongbao(boolean receiveHongbao) {
        isReceiveHongbao = receiveHongbao;
    }

    public boolean isNormalMsg() {
        return isNormalMsg;
    }

    public void setNormalMsg(boolean normalMsg) {
        isNormalMsg = normalMsg;
    }


    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return StringUtils.isEmpty(content) ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadImageUrl() {
        return StringUtils.isEmpty(headImageUrl) ? "" : headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public MessageInfo() {

    }

    public MessageInfo(String nickName) {
        this.name = nickName;
    }

    public String getName() {
        return StringUtils.isEmpty(name) ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private com.tencent.imsdk.TIMMessage TIMMessage;

    public String getPeer() {
        return StringUtils.isEmpty(peer) ? "" : peer;
    }

    public void setPeer(String peer) {
        this.peer = peer;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getFromUser() {
        return StringUtils.isEmpty(fromUser) ? "" : fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public int getMsgType() {
        return msgType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }


    public boolean isSelf() {
        return self;
    }

    public void setSelf(boolean self) {
        this.self = self;
    }


    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }


    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }


    public Uri getDataUri() {
        return dataUri;
    }

    public void setDataUri(Uri dataUri) {
        this.dataUri = dataUri;
    }

    public String getDataPath() {
        return StringUtils.isEmpty(dataPath) ? "" : dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public com.tencent.imsdk.TIMMessage getTIMMessage() {
        return TIMMessage;
    }

    public void setTIMMessage(com.tencent.imsdk.TIMMessage TIMMessage) {
        this.TIMMessage = TIMMessage;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }


    public int getImgWithd() {
        return imgWithd;
    }

    public void setImgWithd(int imgWithd) {
        this.imgWithd = imgWithd;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }


    public boolean isSame(MessageInfo other) {
        if (TIMMessage != null && other.TIMMessage != null) {
            if (TIMMessage.getMsgId().equals(other.TIMMessage.getMsgId())) {
                return true;
            } else {
                return TIMMessage.timestamp() == other.TIMMessage.timestamp();
            }
        }

        return false;
    }


    @Override
    public String toString() {
        return "MessageInfo{" +
                "peer='" + peer + '\'' +
                ", msgId='" + msgId + '\'' +
                ", fromUser='" + fromUser + '\'' +
                ", msgType=" + msgType +
                ", status=" + status +
                ", self=" + self +
                ", read=" + read +
                ", group=" + group +
                ", dataUri=" + dataUri +
                ", dataPath='" + dataPath + '\'' +
                ", extra=" + extra +
                ", msgTime=" + msgTime +
                ", imgWithd=" + imgWithd +
                ", imgHeight=" + imgHeight +
                ", name='" + name + '\'' +
                ", headImageUrl='" + headImageUrl + '\'' +
                ", content='" + content + '\'' +
                ", isNormalMsg=" + isNormalMsg +
                ", isReward=" + isReward +
                ", isLimit=" + isLimit +
                ", isAskQuestion=" + isAskQuestion +
                ", playTime=" + playTime +
                ", isSpeaker=" + isSpeaker +
                ", isHistoryMsg=" + isHistoryMsg +
                ", TIMMessage=" + TIMMessage +
                '}';
    }
}
