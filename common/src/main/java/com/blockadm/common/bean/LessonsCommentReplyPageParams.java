package com.blockadm.common.bean;

/**
 * Created by hp on 2019/4/12.
 */

public class LessonsCommentReplyPageParams {


    /*
    *
    *
    * {
  "newsLessonsCommentId": 200,
  "pageNum": 0,
  "pageSize": 0
}
    * */

    private int newsLessonsCommentId;
    private int pageNum;
    private int pageSize;

    public LessonsCommentReplyPageParams(int newsLessonsCommentId, int pageNum, int pageSize) {
        this.newsLessonsCommentId = newsLessonsCommentId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getNewsLessonsCommentId() {
        return newsLessonsCommentId;
    }

    public void setNewsLessonsCommentId(int newsLessonsCommentId) {
        this.newsLessonsCommentId = newsLessonsCommentId;
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
}
