package com.blockadm.common.bean;

/**
 * Created by hp on 2019/1/30.
 */

public class UserParams {

    /*
    *
    *
    *   "area": "string",
  "birthday": "string",
  "icon": "string",
  "nickName": "string",
  "qqid": "string",
  "recommendCode": "string",
  "sex": "0 女 1 男",
  "sign": "string",
  "signingState": "0 未签约，1：已签约",
  "wbid": "string",
  "wxid": "string"
    *
    *
    * */

    private String area;
    private String birthday;
    private String icon;
    private String nickName;
    private String qqid;
    private String recommendCode;
    private int sex;
    private String sign;
    private String signingState;
    private String wbid;
    private String wxid;


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getQqid() {
        return qqid;
    }

    public void setQqid(String qqid) {
        this.qqid = qqid;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public int  getSex() {
        return sex;
    }

    public void setSex(int  sex) {
        this.sex = sex;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSigningState() {
        return signingState;
    }

    public void setSigningState(String signingState) {
        this.signingState = signingState;
    }

    public String getWbid() {
        return wbid;
    }

    public void setWbid(String wbid) {
        this.wbid = wbid;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }
}
