package com.blockadm.common.bean;

/**
 * Created by hp on 2019/1/31.
 */

public class AddFeedBackBean {

    /*
    *  "address": "string",
  "contact": "string",
  "content": "string",
  "id": 0,
  "typeId": 0
    * */

    private String address;
    private String contact;
    private String content;
    private String id;
    private String typeId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }


}
