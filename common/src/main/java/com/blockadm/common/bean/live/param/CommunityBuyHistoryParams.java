package com.blockadm.common.bean.live.param;

/**
 * Created by Kris on 2019/6/28
 *
 * @Describe TODO {  }
 */
public class CommunityBuyHistoryParams {


    /**
     * pageNum : 0
     * pageSize : 0
     */

    private int pageNum;
    private int pageSize;

    public CommunityBuyHistoryParams(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
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
