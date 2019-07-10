package com.blockadm.common.bean;

/**
 * Created by hp on 2019/3/18.
 */

public class UpdataVersionDto {


    /**
     * updateState : 0
     * updateUrl : http://download
     * updateMsg : 已是最新版本
     * versionName : 1.0.0
     */

    private String updateState;
    private String updateUrl;
    private String updateMsg;
    private String versionName;

    public String getUpdateState() {
        return updateState;
    }

    public void setUpdateState(String updateState) {
        this.updateState = updateState;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getUpdateMsg() {
        return updateMsg;
    }

    public void setUpdateMsg(String updateMsg) {
        this.updateMsg = updateMsg;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }


}
