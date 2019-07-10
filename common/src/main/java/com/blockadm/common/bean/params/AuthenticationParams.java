package com.blockadm.common.bean.params;

import java.io.Serializable;

/**
 * Created by hp on 2019/2/19.
 */

public class AuthenticationParams implements Serializable {

    /*
    *
    * {
  "applyType": "0 个人认证申请 1 企业认证申请",
  "backPhoto": "base64图片/图片url",
  "cardId": "string",
  "frontPhoto": "base64图片/图片url",
  "id": 0,
  "name": "string",
  "organizationCode": "string",
  "organizationLicence": "base64图片/图片url",
  "organizationName": "string",
  "typeId": 0
}
    * */

    private int applyType;
    private String cardId;
    private int  id;
    private String name;
    private String backPhoto;
    private String frontPhoto;
    private String organizationLicence;
    private String organizationName;
    private int  typeId;
    private String organizationCode;

    public int getApplyType() {
        return applyType;
    }

    public void setApplyType(int applyType) {
        this.applyType = applyType;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackPhoto() {
        return backPhoto;
    }

    public void setBackPhoto(String backPhoto) {
        this.backPhoto = backPhoto;
    }

    public String getFrontPhoto() {
        return frontPhoto;
    }

    public void setFrontPhoto(String frontPhoto) {
        this.frontPhoto = frontPhoto;
    }

    public String getOrganizationLicence() {
        return organizationLicence;
    }

    public void setOrganizationLicence(String organizationLicence) {
        this.organizationLicence = organizationLicence;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }
}
