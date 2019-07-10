package com.blockadm.common.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2019/1/15.
 */

public class PageNewsArticleCommentDTO implements Serializable{


    /**
     * records : [{"newsArticleId":1,"nickName":"4444","icon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","title":"金色早报- 印度政府或通过有力的监管使加密货币合法化 | 元界赞助","zanCount":0,"content":"nn","vipLevel":0,"replyCount":0,"createTime":"2019-01-15 09:24:28","reportCount":0,"vipLevelName":"青铜","id":10,"memberId":3},{"newsArticleId":1,"nickName":"4444","icon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","title":"金色早报- 印度政府或通过有力的监管使加密货币合法化 | 元界赞助","zanCount":0,"content":"玩玩","vipLevel":0,"replyCount":0,"createTime":"2019-01-15 09:24:18","reportCount":0,"vipLevelName":"青铜","id":9,"memberId":3},{"newsArticleId":1,"nickName":"4444","icon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","title":"金色早报- 印度政府或通过有力的监管使加密货币合法化 | 元界赞助","zanCount":0,"content":"呵呵","vipLevel":0,"replyCount":0,"createTime":"2019-01-15 09:24:05","reportCount":0,"vipLevelName":"青铜","id":8,"memberId":3},{"newsArticleId":1,"nickName":"4444","icon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","title":"金色早报- 印度政府或通过有力的监管使加密货币合法化 | 元界赞助","zanCount":0,"content":"哈哈","vipLevel":0,"replyCount":4,"commentReplyList":[{"newsArticleId":1,"nickName":"呵呵","icon":"https://p1.pstatp.com/thumb/83490000d07422d5fedb","masterNickName":"4444","newsArticleCommentId":7,"zanCount":0,"masterMemberId":3,"content":"哈哈哈3333","parentId":12,"vipLevel":0,"masterIcon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","createTime":"2019-01-15 09:44:59","commentType":1,"vipLevelName":"青铜","id":17,"memberId":10},{"newsArticleId":1,"nickName":"呵呵","icon":"https://p1.pstatp.com/thumb/83490000d07422d5fedb","masterNickName":"4444","newsArticleCommentId":7,"zanCount":0,"masterMemberId":3,"content":"哈哈哈","parentId":12,"vipLevel":0,"masterIcon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","createTime":"2019-01-15 09:44:53","commentType":1,"vipLevelName":"青铜","id":16,"memberId":10},{"newsArticleId":1,"nickName":"4444","icon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","masterNickName":null,"newsArticleCommentId":7,"zanCount":0,"masterMemberId":0,"content":"回復你11222211","parentId":0,"vipLevel":0,"masterIcon":null,"createTime":"2019-01-15 09:43:59","commentType":0,"vipLevelName":"青铜","id":15,"memberId":3}],"createTime":"2019-01-15 09:23:53","reportCount":0,"vipLevelName":"青铜","id":7,"memberId":3}]
     * total : 4
     * size : 10
     * current : 1
     * pages : 1
     */

    private int total;
    private int size;
    private int current;
    private int pages;
    private List<RecordsBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean  implements Serializable {
        /**
         * newsArticleId : 1
         * nickName : 4444
         * icon : https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg
         * title : 金色早报- 印度政府或通过有力的监管使加密货币合法化 | 元界赞助
         * zanCount : 0
         * content : nn
         * vipLevel : 0
         * replyCount : 0
         * createTime : 2019-01-15 09:24:28
         * reportCount : 0
         * vipLevelName : 青铜
         * id : 10
         * memberId : 3
         * commentReplyList : [{"newsArticleId":1,"nickName":"呵呵","icon":"https://p1.pstatp.com/thumb/83490000d07422d5fedb","masterNickName":"4444","newsArticleCommentId":7,"zanCount":0,"masterMemberId":3,"content":"哈哈哈3333","parentId":12,"vipLevel":0,"masterIcon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","createTime":"2019-01-15 09:44:59","commentType":1,"vipLevelName":"青铜","id":17,"memberId":10},{"newsArticleId":1,"nickName":"呵呵","icon":"https://p1.pstatp.com/thumb/83490000d07422d5fedb","masterNickName":"4444","newsArticleCommentId":7,"zanCount":0,"masterMemberId":3,"content":"哈哈哈","parentId":12,"vipLevel":0,"masterIcon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","createTime":"2019-01-15 09:44:53","commentType":1,"vipLevelName":"青铜","id":16,"memberId":10},{"newsArticleId":1,"nickName":"4444","icon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","masterNickName":null,"newsArticleCommentId":7,"zanCount":0,"masterMemberId":0,"content":"回復你11222211","parentId":0,"vipLevel":0,"masterIcon":null,"createTime":"2019-01-15 09:43:59","commentType":0,"vipLevelName":"青铜","id":15,"memberId":3}]
         */

        private int newsArticleId;
        private String nickName;
        private String icon;
        private String title;
        private int zanCount;
        private String content;
        private int vipLevel;
        private int replyCount;
        private String createTime;
        private int reportCount;
        private String vipLevelName;
        private int id;
        private int memberId;
        private int isZan;

        private int vipState;
        private String levelDiamondIcon;
        public String getLevelDiamondIcon() {
            return levelDiamondIcon;
        }

        public void setLevelDiamondIcon(String levelDiamondIcon) {
            this.levelDiamondIcon = levelDiamondIcon;
        }

        public int getVipState() {
            return vipState;
        }

        public void setVipState(int  vipState) {
            this.vipState = vipState;
        }

        public int getIsZan() {
            return isZan;
        }

        public void setIsZan(int isZan) {
            this.isZan = isZan;
        }

        private List<CommentReplyListBean> commentReplyList =new ArrayList<CommentReplyListBean>();

        public int getNewsArticleId() {
            return newsArticleId;
        }

        public void setNewsArticleId(int newsArticleId) {
            this.newsArticleId = newsArticleId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getZanCount() {
            return zanCount;
        }

        public void setZanCount(int zanCount) {
            this.zanCount = zanCount;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(int vipLevel) {
            this.vipLevel = vipLevel;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getReportCount() {
            return reportCount;
        }

        public void setReportCount(int reportCount) {
            this.reportCount = reportCount;
        }

        public String getVipLevelName() {
            return vipLevelName;
        }

        public void setVipLevelName(String vipLevelName) {
            this.vipLevelName = vipLevelName;
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

        public List<CommentReplyListBean> getCommentReplyList() {
            return commentReplyList;
        }

        public void setCommentReplyList(List<CommentReplyListBean> commentReplyList) {
            this.commentReplyList = commentReplyList;
        }


    }
}
