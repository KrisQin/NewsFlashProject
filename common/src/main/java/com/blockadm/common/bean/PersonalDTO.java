package com.blockadm.common.bean;

/**
 * Created by hp on 2019/1/25.
 */

public class PersonalDTO {


    /**
     * area : null
     * vipLevel : 0
     * follower : 0
     * nickName : 呵呵
     * sex : 2
     * concernSate : 0
     * sign : 暂无签名
     * icon : https://p1.pstatp.com/thumb/83490000d07422d5fedb
     * vipState : 0
     * credentialsState : 0
     * age : null
     * concern : 0
     */

    private String area;
    private int vipLevel;
    private int follower;
    private String nickName;
    private int sex;
    private int concernSate;
    private String sign;
    private String icon;
    private int vipState;
    private int credentialsState;
    private int age;
    private int concern;
    private String vipLevelIcon;
    private String vipLevelName;
    private String levelDiamondIcon;
    private String levelNameIcon;

    public String getLevelDiamondIcon() {
        return levelDiamondIcon;
    }

    public void setLevelDiamondIcon(String levelDiamondIcon) {
        this.levelDiamondIcon = levelDiamondIcon;
    }

    public String getLevelNameIcon() {
        return levelNameIcon;
    }

    public void setLevelNameIcon(String levelNameIcon) {
        this.levelNameIcon = levelNameIcon;
    }

    public String getVipLevelIcon() {
        return vipLevelIcon;
    }

    public void setVipLevelIcon(String vipLevelIcon) {
        this.vipLevelIcon = vipLevelIcon;
    }

    public String getVipLevelName() {
        return vipLevelName;
    }

    public void setVipLevelName(String vipLevelName) {
        this.vipLevelName = vipLevelName;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getConcernSate() {
        return concernSate;
    }

    public void setConcernSate(int concernSate) {
        this.concernSate = concernSate;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getVipState() {
        return vipState;
    }

    public void setVipState(int vipState) {
        this.vipState = vipState;
    }

    public int getCredentialsState() {
        return credentialsState;
    }

    public void setCredentialsState(int credentialsState) {
        this.credentialsState = credentialsState;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getConcern() {
        return concern;
    }

    public void setConcern(int concern) {
        this.concern = concern;
    }
}
