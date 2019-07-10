package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/1/17.
 */

public class CommentDetailDto {


    /**
     * records : [{"isZan":0,"newsArticleId":1,"nickName":"4444","icon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","masterNickName":"4444","newsArticleCommentId":13,"zanCount":0,"masterMemberId":3,"content":"fffff","parentId":32,"vipLevel":0,"masterIcon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","createTime":"2019-01-17 09:51:57","commentType":1,"vipLevelName":"青铜","id":33,"memberId":3},{"isZan":0,"newsArticleId":1,"nickName":"4444","icon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","masterNickName":null,"newsArticleCommentId":13,"zanCount":0,"masterMemberId":0,"content":"kkkkkk","parentId":0,"vipLevel":0,"masterIcon":null,"createTime":"2019-01-17 09:50:53","commentType":0,"vipLevelName":"青铜","id":32,"memberId":3}]
     * total : 2
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

    public static class RecordsBean {
        /**
         * isZan : 0
         * newsArticleId : 1
         * nickName : 4444
         * icon : https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg
         * masterNickName : 4444
         * newsArticleCommentId : 13
         * zanCount : 0
         * masterMemberId : 3
         * content : fffff
         * parentId : 32
         * vipLevel : 0
         * masterIcon : https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg
         * createTime : 2019-01-17 09:51:57
         * commentType : 1
         * vipLevelName : 青铜
         * id : 33
         * memberId : 3
         */

        private int isZan;
        private int newsArticleId;
        private String nickName;
        private String icon;
        private String masterNickName;
        private int newsArticleCommentId;
        private int zanCount;
        private int masterMemberId;
        private String content;
        private int parentId;
        private int vipLevel;
        private String masterIcon;
        private String createTime;
        private int commentType;
        private String vipLevelName;
        private int id;
        private int memberId;
        private int credentialsState;

        public int getCredentialsState() {
            return credentialsState;
        }

        public void setCredentialsState(int credentialsState) {
            this.credentialsState = credentialsState;
        }

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

        public String getMasterNickName() {
            return masterNickName;
        }

        public void setMasterNickName(String masterNickName) {
            this.masterNickName = masterNickName;
        }

        public int getNewsArticleCommentId() {
            return newsArticleCommentId;
        }

        public void setNewsArticleCommentId(int newsArticleCommentId) {
            this.newsArticleCommentId = newsArticleCommentId;
        }

        public int getZanCount() {
            return zanCount;
        }

        public void setZanCount(int zanCount) {
            this.zanCount = zanCount;
        }

        public int getMasterMemberId() {
            return masterMemberId;
        }

        public void setMasterMemberId(int masterMemberId) {
            this.masterMemberId = masterMemberId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(int vipLevel) {
            this.vipLevel = vipLevel;
        }

        public String getMasterIcon() {
            return masterIcon;
        }

        public void setMasterIcon(String masterIcon) {
            this.masterIcon = masterIcon;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getCommentType() {
            return commentType;
        }

        public void setCommentType(int commentType) {
            this.commentType = commentType;
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
    }
}
