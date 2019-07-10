package com.blockadm.common.bean.params;

/**
 * Created by Kris on 2019/5/17
 *
 * @Describe TODO {  }
 */
public class OpenLessonsParams {


    /**
     * accessPwd : string
     * accessStatus : 0 免费、1：付费、2：密码
     * content : string
     * coverImgs : string
     * createTime : 2019-05-17T03:05:46.190Z
     * id : 0
     * lessonsTime : 2019-05-17T03:05:46.190Z
     * liveStatus : 0 直播中、1：预备中（未开始）、2：已结束
     * memberId : 0
     * money : 0
     * payStatus : 0：未付费，1：已付费（允许上架、直播）
     * status : 0 上架、1：下架、2：删除
     * sysTypeId : 0
     * title : string
     * visitCount : 0
     */

    private String accessPwd;
    private String accessStatus;
    private String content;
    private String coverImgs;
    private String createTime;
    private int id;
    private String lessonsTime;
    private String liveStatus;
    private int memberId;
    private int money;
    private String payStatus;
    private String status;
    private int sysTypeId;
    private String title;
    private int visitCount;

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

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }
}
