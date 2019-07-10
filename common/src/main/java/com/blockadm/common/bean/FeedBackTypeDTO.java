package com.blockadm.common.bean;

/**
 * Created by hp on 2019/1/31.
 */

public class FeedBackTypeDTO {


    /**
     * name : 功能建议
     * typeId : 0
     */

    private String name;
    private int typeId;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
   private int position=-1;
    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
