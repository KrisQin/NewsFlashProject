package com.blockadm.common.bean;

import java.io.Serializable;

/**
 * Created by hp on 2019/2/12.
 */

public class TagBeanDto   implements Serializable {


    /**
     * id : 1
     * createTime : 2019-01-08 17:40:47
     * name : 热门的
     * status : 0
     * parentId : 0
     * sort : 0
     * createSysUserId : 10
     */

    private int id;
    private String createTime;
    private String name;
    private int status;
    private int parentId;
    private int sort;
    private int createSysUserId;

    private int isSubscribe;

    public int getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(int isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public TagBeanDto(int id, String createTime, String name, int status, int parentId, int sort, int createSysUserId) {
        this.id = id;
        this.createTime = createTime;
        this.name = name;
        this.status = status;
        this.parentId = parentId;
        this.sort = sort;
        this.createSysUserId = createSysUserId;
    }

    public TagBeanDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getCreateSysUserId() {
        return createSysUserId;
    }

    public void setCreateSysUserId(int createSysUserId) {
        this.createSysUserId = createSysUserId;
    }
}
