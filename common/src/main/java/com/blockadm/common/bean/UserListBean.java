package com.blockadm.common.bean;

/**
 * Created by hp on 2019/1/26.
 */

public class UserListBean {

    /**
     * nickName : null
     * icon : https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2019-01-17/93dcb700c9b4b585d3aba427de3f4b9d.png
     * mutualStatus : 1
     * id : 7
     */

    private String nickName;
    private String icon;
    private int mutualStatus;
    private int id;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMutualStatus() {
        return mutualStatus;
    }

    public void setMutualStatus(int mutualStatus) {
        this.mutualStatus = mutualStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
