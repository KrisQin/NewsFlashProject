package com.blockadm.common.bean.live.responseBean;

/**
 * Created by Kris on 2019/5/7
 *
 * @Describe TODO {  }
 */
public class LiveRecordsInfo {

    /**
     * openLessonsTime : 周二 04-30 19:20
     * nickName : 尚美中心
     * typeName : 精心推荐
     * icon : https://p1.pstatp.com/thumb/8111/7082912174
     * title : 如何做的更好
     * point : 0
     * sysTypeId : 20
     * visitCount : 545
     * money : 0
     * createTime : 2019-04-26 19:20:26
     * lessonsTime : 2019-04-30 19:22:20
     * id : 1
     * accessStatus : 0
     * coverImgs : https://gss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=536406406,
     * 761445230&fm=173&app=49&f=JPEG?w=218&h=146&s=F7365D6EDCA59534D9AC4DB80300C012
     * payStatus : 1
     * convertVisitCount : 545
     * liveStatus : 0
     * memberId : 9
     * status : 0
     */

    private String openLessonsTime;
    private String nickName;
    private String typeName;
    private String icon;
    private String title;
    private int point;
    private int sysTypeId;
    private int visitCount;
    private int money;
    private String createTime;
    private String lessonsTime;
    private int id;
    private int accessStatus; //accessStatus  付费状态   0 免费、1：付费   、2 ：密码
    private String coverImgs;
    private int payStatus;
    private String convertVisitCount;
    private int liveStatus; //  0 直播中、1：预备中（未开始）、2：已结束

    private int memberId;
    private int status; //status",value = "状态",example = "0 上架、1：下架

    private String finishTime;
    private int sortIndex;


    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getOpenLessonsTime() {
        return openLessonsTime;
    }

    public void setOpenLessonsTime(String openLessonsTime) {
        this.openLessonsTime = openLessonsTime;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getSysTypeId() {
        return sysTypeId;
    }

    public void setSysTypeId(int sysTypeId) {
        this.sysTypeId = sysTypeId;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLessonsTime() {
        return lessonsTime;
    }

    public void setLessonsTime(String lessonsTime) {
        this.lessonsTime = lessonsTime;
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

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
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

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
