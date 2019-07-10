package com.blockadm.common.bean.live.param;

import java.util.UUID;

/**
 * Created by Kris on 2019/5/28
 *
 * @Describe TODO {  }
 */
public class NewsLiveLessonsCommentParams {


    /**
     * content : string
     * contentType : 0:文本内容、1：图片URL、2：语音URL、3：视频URL
     * id : 编号:UUID+设备类型(0 android 1 ios 2 pc) 注释：必传参数
     * masterMemberId : @用户ID（被回复会员ID）
     * newsLiveLessonsId : 0
     */

    private String content;
    private String contentType;
    private String id = UUID.randomUUID().toString().replaceAll("-", "")+"0";
    private String masterMemberId = "0";
    private int newsLiveLessonsId;
    private int playTime;
    private int dataType; //"0:直播消息：1： 交流消息",



    public NewsLiveLessonsCommentParams(String content, String contentType,int playTime,int newsLiveLessonsId,int dataType) {
        this.content = content;
        this.contentType = contentType;
        this.newsLiveLessonsId = newsLiveLessonsId;
        this.playTime = playTime;
        this.dataType = dataType;
    }



    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMasterMemberId() {
        return masterMemberId;
    }

    public void setMasterMemberId(String masterMemberId) {
        this.masterMemberId = masterMemberId;
    }

    public int getNewsLiveLessonsId() {
        return newsLiveLessonsId;
    }

    public void setNewsLiveLessonsId(int newsLiveLessonsId) {
        this.newsLiveLessonsId = newsLiveLessonsId;
    }
}
