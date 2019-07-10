package com.blockadm.common.bean.params;

/**
 * Created by hp on 2019/3/1.
 */

public class SaveColumnParams {


    /*
    *
    *
    *
    * {
  {
  "accessStatus": "0 免费、1：付费",
  "content": "string",
  "coverImgs": "string",
  "introduceVideoUrl": "string",
  "lessonsCount": 0,
  "money": 0,
  "title": "string"，            contentImagesArray:null
}
}
    *
    * */

    private  int accessStatus;
    private String content;
    private String introduceVideoUrl;
    private int lessonsCount;
    private String coverImgs;
    private String title;
    private String money;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(int accessStatus) {
        this.accessStatus = accessStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIntroduceVideoUrl() {
        return introduceVideoUrl;
    }

    public void setIntroduceVideoUrl(String introduceVideoUrl) {
        this.introduceVideoUrl = introduceVideoUrl;
    }

    public int getLessonsCount() {
        return lessonsCount;
    }

    public void setLessonsCount(int lessonsCount) {
        this.lessonsCount = lessonsCount;
    }

    public String getCoverImgs() {
        return coverImgs;
    }

    public void setCoverImgs(String coverImgs) {
        this.coverImgs = coverImgs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
