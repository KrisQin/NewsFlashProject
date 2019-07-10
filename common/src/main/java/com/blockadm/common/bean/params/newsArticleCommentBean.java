package com.blockadm.common.bean.params;

/**
 * Created by hp on 2019/1/15.
 */

public class newsArticleCommentBean {

/*
*{

  "newsArticleId": 1,
  "pageNum": 0,
  "pageSize": 10
}
*
* */

    public newsArticleCommentBean(long newsArticleId, int pageNum, int pageSize) {
        this.newsArticleId = newsArticleId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    private long newsArticleId;

  private int pageNum;

  private int pageSize;


    public long getNewsArticleId() {
        return newsArticleId;
    }

    public void setNewsArticleId(long newsArticleId) {
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
}
