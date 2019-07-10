package com.blockadm.common.bean.params;

/**
 * Created by hp on 2019/1/28.
 */

public class AddCommentParams {

    /*
    * {
  "content": "string",
  "createLessonsMemberId": 0,
  "createTime": "2019-01-28T11:33:50.511Z",
  "id": 0,
  "isTop": "0 否，1：是",
  "memberId": 0,
  "objectId": 0,
  "objectType": "0 专栏、1 课程",
  "title": "string",
  "updateTime": "2019-01-28T11:33:50.511Z"
}
    * */

    private String content;
    private int createLessonsMemberId;
    private String createTime;
    private int id;
    private int isTop;
    private int memberId;
    private int objectId;
    private int objectType;
    private String  title;
    private String updateTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreateLessonsMemberId() {
        return createLessonsMemberId;
    }

    public void setCreateLessonsMemberId(int createLessonsMemberId) {
        this.createLessonsMemberId = createLessonsMemberId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
