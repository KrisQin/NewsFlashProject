package com.blockadm.common.bean.live.responseBean;

import java.io.Serializable;

/**
 * Created by Kris on 2019/5/27
 *
 * @Describe TODO { 管理员实体类 }
 */
public class LiveManagerInfo implements Serializable {


    /**
     * managerNickName : 尚美中心
     * managerIcon : https://p1.pstatp.com/thumb/8111/7082912174
     * managerLoginIMAccount : blockAdm_IM_UserMemberId_9
     * managerMemberId : 9
     */

    private String managerNickName;
    private String managerIcon;
    private String managerLoginIMAccount;
    private int managerMemberId;

    private boolean isSpeaker; // true:主讲人  false：普通管理员
    private boolean isSelect; // true:被选中了


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSpeaker() {
        return isSpeaker;
    }

    public void setSpeaker(boolean speaker) {
        isSpeaker = speaker;
    }

    public String getManagerNickName() {
        return managerNickName;
    }

    public void setManagerNickName(String managerNickName) {
        this.managerNickName = managerNickName;
    }

    public String getManagerIcon() {
        return managerIcon;
    }

    public void setManagerIcon(String managerIcon) {
        this.managerIcon = managerIcon;
    }

    public String getManagerLoginIMAccount() {
        return managerLoginIMAccount;
    }

    public void setManagerLoginIMAccount(String managerLoginIMAccount) {
        this.managerLoginIMAccount = managerLoginIMAccount;
    }

    public int getManagerMemberId() {
        return managerMemberId;
    }

    public void setManagerMemberId(int managerMemberId) {
        this.managerMemberId = managerMemberId;
    }
}
