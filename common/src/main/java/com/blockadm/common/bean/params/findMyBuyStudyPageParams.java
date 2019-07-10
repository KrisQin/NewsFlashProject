package com.blockadm.common.bean.params;

/**
 * Created by hp on 2019/3/15.
 */

public class findMyBuyStudyPageParams {

    /*
    *
    *  "lessonsType": 1,
  "pageNum": 0,
  "pageSize": 10,
    *
    * */

    private int lessonsType;
    private int pageNum;
    private int pageSize;


    public findMyBuyStudyPageParams(int lessonsType, int pageNum, int pageSize) {
        this.lessonsType = lessonsType;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getLessonsType() {
        return lessonsType;
    }

    public void setLessonsType(int lessonsType) {
        this.lessonsType = lessonsType;
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
}
