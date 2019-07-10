package com.blockadm.common.bean.params;

/**
 * Created by hp on 2019/1/28.
 */

public class CommentBeanParams {

    /*
    * {
  "objectId": 1,
  "objectType": "0",
  "pageNum": 0,
  "pageSize": 10
}
    * */

    private int objectId;
    private int objectType;
    private int pageNum;
    private int pageSize;

    public CommentBeanParams(int objectId, int objectType, int pageNum, int pageSize) {
        this.objectId = objectId;
        this.objectType = objectType;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
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



