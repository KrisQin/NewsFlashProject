package com.blockadm.common.bean;

/**
 * Created by hp on 2019/1/17.
 */

public class OperateArticleCountDTO {


    /**
     * commentType : 1
     * content : 流量监控
     * createTime : 2019-01-17 10:18:12
     * id : 48
     * isZan : 0
     * masterMemberId : 11
     * masterNickName : hello
     * memberId : 11
     * newsArticleCommentId : 7
     * newsArticleId : 1
     * nickName : hello
     * parentId : 47
     * vipLevel : 0
     * vipLevelName : 青铜
     * zanCount : 0
     */

    private int commentType;
    private String content;
    private String createTime;
    private int id;
    private int isZan;
    private int masterMemberId;
    private String masterNickName;
    private int memberId;
    private int newsArticleCommentId;
    private int newsArticleId;
    private String nickName;
    private int parentId;
    private int vipLevel;
    private String vipLevelName;
    private int zanCount;

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsZan() {
        return isZan;
    }

    public void setIsZan(int isZan) {
        this.isZan = isZan;
    }

    public int getMasterMemberId() {
        return masterMemberId;
    }

    public void setMasterMemberId(int masterMemberId) {
        this.masterMemberId = masterMemberId;
    }

    public String getMasterNickName() {
        return masterNickName;
    }

    public void setMasterNickName(String masterNickName) {
        this.masterNickName = masterNickName;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getNewsArticleCommentId() {
        return newsArticleCommentId;
    }

    public void setNewsArticleCommentId(int newsArticleCommentId) {
        this.newsArticleCommentId = newsArticleCommentId;
    }

    public int getNewsArticleId() {
        return newsArticleId;
    }

    public void setNewsArticleId(int newsArticleId) {
        this.newsArticleId = newsArticleId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getVipLevelName() {
        return vipLevelName;
    }

    public void setVipLevelName(String vipLevelName) {
        this.vipLevelName = vipLevelName;
    }

    public int getZanCount() {
        return zanCount;
    }

    public void setZanCount(int zanCount) {
        this.zanCount = zanCount;
    }
}
