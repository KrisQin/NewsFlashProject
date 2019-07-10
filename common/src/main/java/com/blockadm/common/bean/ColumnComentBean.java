package com.blockadm.common.bean;

/**
 * Created by hp on 2019/4/12.
 */

public class ColumnComentBean {

    /*
    * {
  "dataType": "2",
  "objectId":12,
  "objectType": "1",
  "pageNum": 1,
  "pageSize": 10,

}
    *
    *
    * */

    private  int dataType;
    private  int objectId;
    private  int objectType;
    private  int pageNum;
    private  int pageSize;

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
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
