package com.blockadm.common.bean;

/**
 * Created by hp on 2019/1/25.
 */

public class PageBean {

    /*
    * {
  "pageNum": 0,
  "pageSize":10
}
    * */
    private String condition;
    private int pageNum;
    private int pageSize;
    private String title;

    private String  userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PageBean(int pageNum, int pageSize,int userId1) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        if (userId1==-1){
            this.userId = "";
        }else{
            this.userId = userId1+"";
        }

    }
    public PageBean(int pageNum, int pageSize,String title ) {
        this.title = title;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    private Integer memberId;
    public PageBean(int pageNum, int pageSize,String title,Integer memberId ) {
        this.title = title;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.memberId = memberId;
    }
    public PageBean(String condition, int pageNum, int pageSize) {
        this.condition = condition;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}
