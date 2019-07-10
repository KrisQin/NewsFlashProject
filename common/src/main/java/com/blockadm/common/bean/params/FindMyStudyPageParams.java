package com.blockadm.common.bean.params;

/**
 * Created by hp on 2019/2/22.
 */

public class FindMyStudyPageParams {


    /*


    {
  "accessStatus": "0 免费、1：付费",
  "pageNum": 0,
  "pageSize": 0,
  "status": "0 上架、1：下架",
  "title": "string"
}
    * */


    private String accessStatus;
    private int  pageNum;
    private int pageSize;
    private String status;
    private String title;

    public FindMyStudyPageParams(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public String getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(String accessStatus) {
        this.accessStatus = accessStatus;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
