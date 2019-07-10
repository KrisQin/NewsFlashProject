package com.blockadm.common.bean;

import java.io.Serializable;

/**
 * Created by Kris on 2019/6/17
 *
 * @Describe TODO {  }
 */
public class UserSubscribeLableListInfo implements Serializable {

    /**
     * sysLableId : 3
     * createTime : 2019-05-10 18:08:45
     * name : 推荐
     * id : 463
     * memberId : 2197
     */

    private int sysLableId;
    private String createTime;
    private String name;
    private int id;
    private int memberId;

    public int getSysLableId() {
        return sysLableId;
    }

    public void setSysLableId(int sysLableId) {
        this.sysLableId = sysLableId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
