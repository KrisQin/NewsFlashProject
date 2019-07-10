package com.blockadm.common.bean;

/**
 * Created by hp on 2019/4/17.
 */

public class AwardDto {


    /**
     * awardDiamond : 0.1
     * awardExplain : 图片分享快讯奖励
     */

    private double awardDiamond;
    private String awardExplain;
    private  int isTrue;

    public int getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(int isTrue) {
        this.isTrue = isTrue;
    }

    public double getAwardDiamond() {
        return awardDiamond;
    }

    public void setAwardDiamond(double awardDiamond) {
        this.awardDiamond = awardDiamond;
    }

    public String getAwardExplain() {
        return awardExplain;
    }

    public void setAwardExplain(String awardExplain) {
        this.awardExplain = awardExplain;
    }
}
