package com.blockadm.common.bean;

import java.io.Serializable;

/**
 * Created by hp on 2019/4/11.
 */

public class CommentReplyListBean  implements Serializable {
    /**
     * commentType : 1
     * content : VS DVD三百
     * createTime : 2019-04-03 17:50:07
     * icon : http://image.blockadm.com/image/robot/20190311/610.jpg
     * id : 305
     * isZan : 0
     * levelDiamondIcon : http://public.blockadm.com/image/level/user_lv1.png
     * levelId : 0
     * levelName : 青铜节点
     * levelNameIcon : http://public.blockadm.com/image/level/lv1.png
     * masterIcon : http://image.blockadm.com/image/robot/20190311/1540.jpg
     * masterMemberId : 1630
     * masterNickName : すすり泣
     * memberId : 700
     * newsLessonsCommentId : 158
     * nickName : 少女心英雄梦
     * objectId : 74
     * objectType : 1
     * parentId : 304
     * vipLevelName :
     * vipState : 0
     * zanCount : 0
     */

    private int commentType;
    private String content;
    private String createTime;
    private String icon;
    private int id;
    private int isZan;
    private String levelDiamondIcon;
    private int levelId;
    private String levelName;
    private String levelNameIcon;
    private String masterIcon;
    private int masterMemberId;
    private String masterNickName;
    private int memberId;
    private int newsLessonsCommentId;
    private String nickName;
    private int objectId;
    private int objectType;
    private int parentId;
    private String vipLevelName;
    private int vipState;
    private int zanCount;
    private int newsArticleCommentId;
    private int newsArticleId;
    private int vipLevel;

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public int getNewsArticleId() {
        return newsArticleId;
    }

    public void setNewsArticleId(int newsArticleId) {
        this.newsArticleId = newsArticleId;
    }

    public int getNewsArticleCommentId() {
        return newsArticleCommentId;
    }

    public void setNewsArticleCommentId(int newsArticleCommentId) {
        this.newsArticleCommentId = newsArticleCommentId;
    }

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public String getLevelDiamondIcon() {
        return levelDiamondIcon;
    }

    public void setLevelDiamondIcon(String levelDiamondIcon) {
        this.levelDiamondIcon = levelDiamondIcon;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelNameIcon() {
        return levelNameIcon;
    }

    public void setLevelNameIcon(String levelNameIcon) {
        this.levelNameIcon = levelNameIcon;
    }

    public String getMasterIcon() {
        return masterIcon;
    }

    public void setMasterIcon(String masterIcon) {
        this.masterIcon = masterIcon;
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

    public int getNewsLessonsCommentId() {
        return newsLessonsCommentId;
    }

    public void setNewsLessonsCommentId(int newsLessonsCommentId) {
        this.newsLessonsCommentId = newsLessonsCommentId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getVipLevelName() {
        return vipLevelName;
    }

    public void setVipLevelName(String vipLevelName) {
        this.vipLevelName = vipLevelName;
    }

    public int getVipState() {
        return vipState;
    }

    public void setVipState(int vipState) {
        this.vipState = vipState;
    }

    public int getZanCount() {
        return zanCount;
    }

    public void setZanCount(int zanCount) {
        this.zanCount = zanCount;
    }
}
