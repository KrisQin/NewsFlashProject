package com.blockadm.common.bean.params;

/**
 * Created by hp on 2019/1/17.
 */

public class ReportBean {


    /*
    * {

  "reason": "string",
  "objectId": 1,
  "objectType": 0

}
    *
    *
    * */

    public ReportBean(int objectId, int objectType) {
        this.objectId = objectId;
        this.objectType = objectType;
    }

    private  int objectId;
    private int objectType;

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

    @Override
    public String toString() {
        return "ReportBean{" +
                "objectId=" + objectId +
                ", objectType=" + objectType +
                '}';
    }
}
