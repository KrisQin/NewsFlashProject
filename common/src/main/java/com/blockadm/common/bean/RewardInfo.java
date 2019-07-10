package com.blockadm.common.bean;

/**
 * Created by Kris on 2019/5/15
 *
 * @Describe TODO { 打赏实体类 }
 */
public class RewardInfo {
    /**
     * typeId : 0
     * rewardMoney : 100
     */

    private int typeId;
    private int rewardMoney;

    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(int rewardMoney) {
        this.rewardMoney = rewardMoney;
    }
}
