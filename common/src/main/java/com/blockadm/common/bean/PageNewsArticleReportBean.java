package com.blockadm.common.bean;

/**
 * Created by hp on 2019/1/17.
 */

public class PageNewsArticleReportBean {

    /*
    * {
  "newsArticleCommentId": 13,
  "newsArticleId": 1,
  "pageNum": 0,
  "pageSize": 10
}
    *
    * */

    private int newsArticleCommentId;
    private int newsArticleId;
    private int pageNum;
    private int pageSize;
   private int newsLessonsCommentId;

    public int getNewsLessonsCommentId() {
        return newsLessonsCommentId;
    }

    public void setNewsLessonsCommentId(int newsLessonsCommentId) {
        this.newsLessonsCommentId = newsLessonsCommentId;
    }

    public int getNewsArticleCommentId() {
        return newsArticleCommentId;
    }

    public void setNewsArticleCommentId(int newsArticleCommentId) {
        this.newsArticleCommentId = newsArticleCommentId;
    }

    public int getNewsArticleId() {
        return newsArticleId;
    }

    public void setNewsArticleId(int newsArticleId) {
        this.newsArticleId = newsArticleId;
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

    public PageNewsArticleReportBean(int newsArticleCommentId, int newsArticleId, int pageNum, int pageSize) {
        this.newsArticleCommentId = newsArticleCommentId;
        this.newsArticleId = newsArticleId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageNewsArticleReportBean(int pageNum, int pageSize, int newsLessonsCommentId) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.newsLessonsCommentId = newsLessonsCommentId;
    }
}
