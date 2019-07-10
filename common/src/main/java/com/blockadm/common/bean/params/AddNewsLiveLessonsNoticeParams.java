package com.blockadm.common.bean.params;

/**
 * Created by Kris on 2019/5/16
 *
 * @Describe TODO { 添加公告信息 }
 */
public class AddNewsLiveLessonsNoticeParams {


    /**
     * available : 0：使用中 、1：已停用
     * createTime : 2019-05-16T09:27:18.290Z
     * id : 0
     * memberId : 0
     * newsLiveLessonsId : 0
     * noticeContent : string
     */

    private String available;
    private String createTime;
    private int id;
    private int memberId;
    private int newsLiveLessonsId;
    private String noticeContent;

    public AddNewsLiveLessonsNoticeParams(String available,  int newsLiveLessonsId,
                                          String noticeContent) {
        this.available = available;
        this.newsLiveLessonsId = newsLiveLessonsId;
        this.noticeContent = noticeContent;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
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

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getNewsLiveLessonsId() {
        return newsLiveLessonsId;
    }

    public void setNewsLiveLessonsId(int newsLiveLessonsId) {
        this.newsLiveLessonsId = newsLiveLessonsId;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
}
