package com.blockadm.common.bean.params;

/**
 * Created by hp on 2019/1/9.
 */

public class NewsFlashBean {
  /*
  * {
  "content": "string",
  "pageNum": 0,
  "pageSize": 0,
  "showStatus": "0 显示，1：不显示",
  "title": "string"
}
  * */

    public NewsFlashBean(int sortType, int pageNum, int pageSize, String title) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.title = title;
        this.sortType = sortType;
    }

  private int pageNum;
  private int pageSize;
  private String title;
    private int sortType;
private String memberId;
private int sysTypeId;
    public NewsFlashBean(int type, int pageNum, int pageSize, String title, String memberId,int sysTypeId) {

        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.title = title;
        this.sortType = type;
        this.memberId = memberId;
        this.sysTypeId = sysTypeId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
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
}
