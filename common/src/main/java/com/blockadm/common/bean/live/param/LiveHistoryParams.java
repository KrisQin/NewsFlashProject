package com.blockadm.common.bean.live.param;

/**
 * Created by Kris on 2019/5/24
 *
 * @Describe TODO {  }
 */
public class LiveHistoryParams {


    /**
     * content : string
     * dataType : 0：查询当前直播课程聊天信息（直播课程ID不能为空）,1：查询自己创建的所有直播课程聊天信息 、2：查询自己加入的所有直播课程聊天信息
     * newsLiveLessonsId : 0
     * nickName : string
     * pageNum : 0
     * pageSize : 0
     * title : string
     */

    private String content;
    private String dataType;
    private int newsLiveLessonsId;
    private String nickName;
    private int pageNum;
    private int pageSize;
    private String title;

    public LiveHistoryParams(String dataType, int newsLiveLessonsId,
                             int pageNum, int pageSize) {
        this.dataType = dataType;
        this.newsLiveLessonsId = newsLiveLessonsId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getNewsLiveLessonsId() {
        return newsLiveLessonsId;
    }

    public void setNewsLiveLessonsId(int newsLiveLessonsId) {
        this.newsLiveLessonsId = newsLiveLessonsId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
