package com.blockadm.common.bean;

/**
 * Created by hp on 2019/3/6.
 */

public class LoginByThirdPartyParams {

    /*
    * {
  "icon": "string",
  "nickName": "string",
  "sex": 0,
  "thirdPartyId": "string",
  "type": 0
}
    * */

    public LoginByThirdPartyParams(String icon, String nickName, int sex, String thirdPartyId, int type) {
        this.icon = icon;
        this.nickName = nickName;
        this.sex = sex;
        this.thirdPartyId = thirdPartyId;
        this.type = type;
    }

    private String icon;
    private  String nickName;
    private int sex;
    private String thirdPartyId;
    private int  type;

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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
