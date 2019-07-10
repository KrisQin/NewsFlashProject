package com.blockadm.common.bean.params;

/**
 * Created by hp on 2019/2/27.
 */

public class PageMyRecommendDetailWebParams {


    /*
    *
    * {
  "level": 1,
  "pageNum": 0,
  "pageSize": 10
}
    * */

    private int level;
    private int pageNum;
    private int pageSize;

    public PageMyRecommendDetailWebParams(int level, int pageNum, int pageSize) {
        this.level = level;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
