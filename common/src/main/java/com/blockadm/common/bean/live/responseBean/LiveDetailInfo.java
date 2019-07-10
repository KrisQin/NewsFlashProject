package com.blockadm.common.bean.live.responseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kris on 2019/5/8
 *
 * @Describe TODO { 直播详情 }
 */
public class LiveDetailInfo implements Serializable {

    private String showContentHtmlUrl;
    private int isSeeStatus; //isSeeStatus : 0:免费，1：未付费、2:已付费,3:请输入密码,4:密码已校验通过
    private String openLessonsTime;
    private String typeName;
    private String icon;
    private int credentialsState;
    private String title;
    private int point;
    private String content;
    private int isIMGroupManager; //是否是管理员[0 否、1：是普通管理员  2：超级管理员（主讲人）]
    private String noticeContent;
    private String imGroupId;
    private int isForbid;
    private int visitCount;
    private int joinedLiveGroupMemberCount;
    private int levelId;
    private int id;
    private int accessStatus;
    private String coverImgs;
    private String convertVisitCount;
    private int liveStatus;
    private int maxLiveGroupMemberCount;
    private int memberId;
    private String loginIMAccount;
    private String nickName;
    private int forbidMemberCount;
    private int vipState;
    private int signingState;
    private int sysTypeId;
    private int isFollow;
    private String loginIMSign;
    private int money;
    private String lessonsTime;
    private int blacklistMemberCount;
    private int isJoinIMGroup;
    private int payStatus;
    private int status;
    private String speakerIMAccount; //主讲人对应的IM账号
    private List<LiveManagerInfo> newsLiveLessonsManagerList; //当前直播课程所有管理员




    public String getSpeakerIMAccount() {
        return speakerIMAccount;
    }

    public void setSpeakerIMAccount(String speakerIMAccount) {
        this.speakerIMAccount = speakerIMAccount;
    }

    public List<LiveManagerInfo> getNewsLiveLessonsManagerList() {
        return newsLiveLessonsManagerList;
    }

    public void setNewsLiveLessonsManagerList(List<LiveManagerInfo> newsLiveLessonsManagerList) {
        this.newsLiveLessonsManagerList = newsLiveLessonsManagerList;
    }

    public String getShowContentHtmlUrl() {
        return showContentHtmlUrl;
    }

    public void setShowContentHtmlUrl(String showContentHtmlUrl) {
        this.showContentHtmlUrl = showContentHtmlUrl;
    }

    public int getIsSeeStatus() {
        return isSeeStatus;
    }

    public void setIsSeeStatus(int isSeeStatus) {
        this.isSeeStatus = isSeeStatus;
    }

    public String getOpenLessonsTime() {
        return openLessonsTime;
    }

    public void setOpenLessonsTime(String openLessonsTime) {
        this.openLessonsTime = openLessonsTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getCredentialsState() {
        return credentialsState;
    }

    public void setCredentialsState(int credentialsState) {
        this.credentialsState = credentialsState;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsIMGroupManager() {
        return isIMGroupManager;
    }

    public void setIsIMGroupManager(int isIMGroupManager) {
        this.isIMGroupManager = isIMGroupManager;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getImGroupId() {
        return imGroupId;
    }

    public void setImGroupId(String imGroupId) {
        this.imGroupId = imGroupId;
    }

    public int getIsForbid() {
        return isForbid;
    }

    public void setIsForbid(int isForbid) {
        this.isForbid = isForbid;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getJoinedLiveGroupMemberCount() {
        return joinedLiveGroupMemberCount;
    }

    public void setJoinedLiveGroupMemberCount(int joinedLiveGroupMemberCount) {
        this.joinedLiveGroupMemberCount = joinedLiveGroupMemberCount;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(int accessStatus) {
        this.accessStatus = accessStatus;
    }

    public String getCoverImgs() {
        return coverImgs;
    }

    public void setCoverImgs(String coverImgs) {
        this.coverImgs = coverImgs;
    }

    public String getConvertVisitCount() {
        return convertVisitCount;
    }

    public void setConvertVisitCount(String convertVisitCount) {
        this.convertVisitCount = convertVisitCount;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public int getMaxLiveGroupMemberCount() {
        return maxLiveGroupMemberCount;
    }

    public void setMaxLiveGroupMemberCount(int maxLiveGroupMemberCount) {
        this.maxLiveGroupMemberCount = maxLiveGroupMemberCount;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getLoginIMAccount() {
        return loginIMAccount;
    }

    public void setLoginIMAccount(String loginIMAccount) {
        this.loginIMAccount = loginIMAccount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getForbidMemberCount() {
        return forbidMemberCount;
    }

    public void setForbidMemberCount(int forbidMemberCount) {
        this.forbidMemberCount = forbidMemberCount;
    }

    public int getVipState() {
        return vipState;
    }

    public void setVipState(int vipState) {
        this.vipState = vipState;
    }

    public int getSigningState() {
        return signingState;
    }

    public void setSigningState(int signingState) {
        this.signingState = signingState;
    }

    public int getSysTypeId() {
        return sysTypeId;
    }

    public void setSysTypeId(int sysTypeId) {
        this.sysTypeId = sysTypeId;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public String getLoginIMSign() {
        return loginIMSign;
    }

    public void setLoginIMSign(String loginIMSign) {
        this.loginIMSign = loginIMSign;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getLessonsTime() {
        return lessonsTime;
    }

    public void setLessonsTime(String lessonsTime) {
        this.lessonsTime = lessonsTime;
    }

    public int getBlacklistMemberCount() {
        return blacklistMemberCount;
    }

    public void setBlacklistMemberCount(int blacklistMemberCount) {
        this.blacklistMemberCount = blacklistMemberCount;
    }

    public int getIsJoinIMGroup() {
        return isJoinIMGroup;
    }

    public void setIsJoinIMGroup(int isJoinIMGroup) {
        this.isJoinIMGroup = isJoinIMGroup;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}