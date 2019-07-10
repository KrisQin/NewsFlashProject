package com.blockadm.common.bean.params;

import com.blockadm.common.utils.StringUtils;

/**
 * Created by hp on 2019/1/16.
 */

public class AddReplyBean {


    /*
    * {
  "commentType": "0 主评论，1：回复论",
  "content": "string",
  "createTime": "2019-01-16T02:44:19.191Z",
  "id": 0,
  "masterMemberId": 0,
  "memberId": 0,
  "newsArticleCommentId": 0,
  "newsArticleId": 0,
  "parentId": 0,
  "zanCount": 0
}
    *
    *
    * {
  "commentType": "0 主评论，1：回复论",
  "content": "string",
  "id": 0,
  "masterMemberId": 0,
  "newsLessonsCommentId": 0,
  "parentId": 0
}
    * */

    public AddReplyBean() {
    }

    private int commentType;
    private String content;

    private int id;
    private int masterMemberId;
    private int memberId;
    private int newsArticleCommentId;
    private int newsLessonsCommentId;
    private int parentId;
    private int newsArticleId;

    public int getNewsLessonsCommentId() {
        return newsLessonsCommentId;
    }

    public void setNewsLessonsCommentId(int newsLessonsCommentId) {
        this.newsLessonsCommentId = newsLessonsCommentId;
    }

    public AddReplyBean(String content, int newsArticleId) {
        this.content = content;
        this.newsArticleId = newsArticleId;
    }

    public AddReplyBean(int commentType, String content, int id, int masterMemberId, int memberId, int newsArticleCommentId, int parentId, int newsArticleId) {
        this.commentType = commentType;
        this.content = content;
        this.id = id;
        this.masterMemberId = masterMemberId;
        this.memberId = memberId;
        this.newsArticleCommentId = newsArticleCommentId;
        this.parentId = parentId;
        this.newsArticleId = newsArticleId;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getContent() {
        return StringUtils.isEmpty(content)? "":content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMasterMemberId() {
        return masterMemberId;
    }

    public void setMasterMemberId(int masterMemberId) {
        this.masterMemberId = masterMemberId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getNewsArticleCommentId() {
        return newsArticleCommentId;
    }

    public void setNewsArticleCommentId(int newsArticleCommentId) {
        this.newsArticleCommentId = newsArticleCommentId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getNewsArticleId() {
        return newsArticleId;
    }

    public void setNewsArticleId(int newsArticleId) {
        this.newsArticleId = newsArticleId;
    }
}
