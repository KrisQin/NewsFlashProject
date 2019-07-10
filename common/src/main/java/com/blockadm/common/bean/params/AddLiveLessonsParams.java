package com.blockadm.common.bean.params;

/**
 * Created by Kris on 2019/5/20
 *
 * @Describe TODO {  }
 */
public class AddLiveLessonsParams {
    /**
     * accessPwd : 访问密码
     * accessStatus : 0 免费、1：付费、2：密码
     * content : 简介
     * coverImgs : 封面图
     * lessonsTime : 开课时间
     * liveStatus : 0 直播中、1：预备中（未开始）、2：已结束 默认1
     * money : 0
     * status : 0 上架、1：下架、2：删除 默认 1
     * sysTypeId : 类型
     * title : 标题
     */

    private String accessPwd;
    private String accessStatus;
    private String content;
    private String coverImgs;
    private String lessonsTime;
    private String liveStatus;
    private int money;
    private String status;
    private String sysTypeId;
    private String title;

    public AddLiveLessonsParams(String accessPwd, String accessStatus, String content,
                                String coverImgs, String lessonsTime, String liveStatus,
                                int money, String status, String sysTypeId, String title) {
        this.accessPwd = accessPwd;
        this.accessStatus = accessStatus;
        this.content = content;
        this.coverImgs = coverImgs;
        this.lessonsTime = lessonsTime;
        this.liveStatus = liveStatus;
        this.money = money;
        this.status = status;
        this.sysTypeId = sysTypeId;
        this.title = title;
    }

    public String getAccessPwd() {
        return accessPwd;
    }

    public void setAccessPwd(String accessPwd) {
        this.accessPwd = accessPwd;
    }

    public String getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(String accessStatus) {
        this.accessStatus = accessStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImgs() {
        return coverImgs;
    }

    public void setCoverImgs(String coverImgs) {
        this.coverImgs = coverImgs;
    }

    public String getLessonsTime() {
        return lessonsTime;
    }

    public void setLessonsTime(String lessonsTime) {
        this.lessonsTime = lessonsTime;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSysTypeId() {
        return sysTypeId;
    }

    public void setSysTypeId(String sysTypeId) {
        this.sysTypeId = sysTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




}
