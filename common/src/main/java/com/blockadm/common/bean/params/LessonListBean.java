package com.blockadm.common.bean.params;

/**
 * Created by hp on 2019/1/21.
 */

public class LessonListBean {

    /*
    *
    *  "nlscType": 0,
  "pageNum": 0,
  "pageSize": 10
    * */

    private int nlscType ;
    private int pageNum ;
    private int pageSize ;
    private int nlscId;

    private  String memberId;

    /**
     精选专栏   sysTypeId  和 memberId 传空值    accessStatus =1
     限时免费   sysTypeId  和 memberId 传空值    accessStatus =0
     3人未读

     根据类型查询    memberId 和 accessStatus  传空值    sysTypeId  传对应的类型Id
     毛丹

     */

    private  String accessStatus;

    private  String sysTypeId;


    public String getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(String accessStatus) {
        this.accessStatus = accessStatus;
    }


    public String getSysTypeId() {
        return sysTypeId;
    }

    public void setSysTypeId(String sysTypeId) {
        this.sysTypeId = sysTypeId;
    }

    public LessonListBean(int nlscType, int pageNum, int pageSize, int nlscId) {
        this.nlscType = nlscType;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.nlscId = nlscId;
    }

    public LessonListBean(int nlscType, int pageNum, int pageSize) {
        this.nlscType = nlscType;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public LessonListBean(int nlscType, int pageNum, int pageSize, String sysTypeId) {
        this.nlscType = nlscType;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sysTypeId = sysTypeId;
    }

    public LessonListBean(String sysTypeId, int pageNum, int pageSize) {
        this.sysTypeId = sysTypeId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }


    public int getNlscType() {
        return nlscType;
    }

    public void setNlscType(int nlscType) {
        this.nlscType = nlscType;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public LessonListBean(int nlscId) {
        this.nlscId = nlscId;
    }
}
