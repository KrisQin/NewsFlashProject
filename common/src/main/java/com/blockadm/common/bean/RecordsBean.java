package com.blockadm.common.bean;

import java.io.Serializable;

/**
 * Created by hp on 2019/3/21.
 */

public class RecordsBean  implements Serializable{


    private int vipPoint;
    private int catalog;
    private String typeName;
    private String icon;
    private String title;
    private int readCount;
    private int point;
    private int nlscType;
    private String audioUrl;
    private String videoUrl;
    private int nlscId;
    private int popularity;
    private int levelId;
    private int id;
    private int accessStatus;
    private String coverImgs;
    private String convertReadCount;
    private int contentType;
    private int memberId;
    private String nickName;
    private int dataType;
    private String pictureLiveUrl;
    private double vipMoney;
    private int vipState;
    private int sort;
    private int signingState;
    private int sysTypeId;
    private int playCount;
    private double money;
    private String createTime;
    private String subtitle;
    private int status;

    private int lessonsHour;
    private String lessonsTime;
    private int position;
    private boolean isNowPlay;

    public int getLessonsHour() {
        return lessonsHour;
    }

    public void setLessonsHour(int lessonsHour) {
        this.lessonsHour = lessonsHour;
    }

    public String getLessonsTime() {
        return lessonsTime;
    }

    public void setLessonsTime(String lessonsTime) {
        this.lessonsTime = lessonsTime;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isNowPlay() {
        return isNowPlay;
    }

    public void setNowPlay(boolean nowPlay) {
        isNowPlay = nowPlay;
    }

    public int getVipPoint() {
        return vipPoint;
    }

    public void setVipPoint(int vipPoint) {
        this.vipPoint = vipPoint;
    }

    public int getCatalog() {
        return catalog;
    }

    public void setCatalog(int catalog) {
        this.catalog = catalog;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getNlscType() {
        return nlscType;
    }

    public void setNlscType(int nlscType) {
        this.nlscType = nlscType;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getNlscId() {
        return nlscId;
    }

    public void setNlscId(int nlscId) {
        this.nlscId = nlscId;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
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

    public String getConvertReadCount() {
        return convertReadCount;
    }

    public void setConvertReadCount(String convertReadCount) {
        this.convertReadCount = convertReadCount;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getPictureLiveUrl() {
        return pictureLiveUrl;
    }

    public void setPictureLiveUrl(String pictureLiveUrl) {
        this.pictureLiveUrl = pictureLiveUrl;
    }

    public double getVipMoney() {
        return vipMoney;
    }

    public void setVipMoney(double vipMoney) {
        this.vipMoney = vipMoney;
    }

    public int getVipState() {
        return vipState;
    }

    public void setVipState(int vipState) {
        this.vipState = vipState;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /*
    private int accessStatus;
    private String audioUrl;
    private int contentType;
    private String coverImgs;
    private String createTime;
    private String icon;
    private int id;
    private int lessonsHour;
    private String lessonsTime;
    private int levelId;
    private int memberId;
    private double money;
    private String nickName;
    private int nlscId;
    private int nlscType;
    private String pictureLiveUrl;
    private int playCount;
    private int popularity;
    private int readCount;
    private int signingState;
    private int sort;
    private int status;
    private String subtitle;
    private int sysTypeId;
    private String title;
    private String typeName;
    private String videoUrl;
    private int vipState;
    private int position;
    private String catalog;
    private boolean isNowPlay;




    public boolean isNowPlay() {
        return isNowPlay;
    }

    public void setNowPlay(boolean nowPlay) {
        isNowPlay = nowPlay;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(int accessStatus) {
        this.accessStatus = accessStatus;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getCoverImgs() {
        return coverImgs;
    }

    public void setCoverImgs(String coverImgs) {
        this.coverImgs = coverImgs;
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

    public int getLessonsHour() {
        return lessonsHour;
    }

    public void setLessonsHour(int lessonsHour) {
        this.lessonsHour = lessonsHour;
    }

    public String getLessonsTime() {
        return lessonsTime;
    }

    public void setLessonsTime(String lessonsTime) {
        this.lessonsTime = lessonsTime;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getName() {
        return nickName;
    }

    public void setName(String nickName) {
        this.nickName = nickName;
    }

    public int getNlscId() {
        return nlscId;
    }

    public void setNlscId(int nlscId) {
        this.nlscId = nlscId;
    }

    public int getNlscType() {
        return nlscType;
    }

    public void setNlscType(int nlscType) {
        this.nlscType = nlscType;
    }

    public String getPictureLiveUrl() {
        return pictureLiveUrl;
    }

    public void setPictureLiveUrl(String pictureLiveUrl) {
        this.pictureLiveUrl = pictureLiveUrl;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getSigningState() {
        return signingState;
    }

    public void setSigningState(int signingState) {
        this.signingState = signingState;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getSysTypeId() {
        return sysTypeId;
    }

    public void setSysTypeId(int sysTypeId) {
        this.sysTypeId = sysTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getVipState() {
        return vipState;
    }

    public void setVipState(int vipState) {
        this.vipState = vipState;
    }
    */
}
