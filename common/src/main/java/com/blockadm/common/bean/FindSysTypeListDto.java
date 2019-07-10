package com.blockadm.common.bean;

/**
 * Created by hp on 2019/3/13.
 */

public class FindSysTypeListDto {


    /**
     * id : 1
     * createTime : 2019-01-10 14:21:47
     * typeId : 0
     * typeName : 财经
     * sort : 0
     * status : 0
     * parentId : 0
     */

    private int id;
    private String createTime;
    private int typeId;
    private String typeName;
    private int sort;
    private int status;
    private int parentId;

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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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
}
