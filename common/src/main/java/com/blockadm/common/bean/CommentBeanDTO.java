package com.blockadm.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hp on 2019/1/28.
 */

public class CommentBeanDTO {


    /**
     * current : 1
     * pages : 1
     * records : [{"commentReplyList":[{"commentType":1,"content":"VS DVD三百 ","createTime":"2019-04-03 17:50:07","icon":"http://image.blockadm.com/image/robot/20190311/610.jpg","id":305,"isZan":0,"levelDiamondIcon":"http://public.blockadm.com/image/level/user_lv1.png","levelId":0,"levelName":"青铜节点","levelNameIcon":"http://public.blockadm.com/image/level/lv1.png","masterIcon":"http://image.blockadm.com/image/robot/20190311/1540.jpg","masterMemberId":1630,"masterNickName":"すすり泣","memberId":700,"newsLessonsCommentId":158,"nickName":"少女心英雄梦","objectId":74,"objectType":1,"parentId":304,"vipLevelName":"","vipState":0,"zanCount":0},{"commentType":0,"content":"吃饭撒v但是","createTime":"2019-04-03 17:50:01","icon":"http://image.blockadm.com/image/robot/20190311/1540.jpg","id":304,"isZan":0,"levelDiamondIcon":"http://public.blockadm.com/image/level/user_lv1.png","levelId":0,"levelName":"青铜节点","levelNameIcon":"http://public.blockadm.com/image/level/lv1.png","masterMemberId":0,"memberId":1630,"newsLessonsCommentId":158,"nickName":"すすり泣","objectId":74,"objectType":1,"parentId":0,"vipLevelName":"","vipState":0,"zanCount":0}],"content":"测试","createTime":"2019-04-03 17:18","icon":"http://image.blockadm.com/image/robot/20190311/174.jpg","id":158,"isTop":0,"isZan":0,"levelDiamondIcon":"http://public.blockadm.com/image/level/user_lv1.png","levelId":0,"levelName":"青铜节点","levelNameIcon":"http://public.blockadm.com/image/level/lv1.png","memberId":264,"nickName":"笑说再见","objectId":74,"objectType":1,"replyCount":2,"title":"个人个人","updateTime":"2019-04-03 17:18:05","vipState":0,"zanCount":0},{"commentReplyList":[{"commentType":0,"content":"大师傅哈","createTime":"2019-04-03 17:18:28","icon":"http://image.blockadm.com/image/robot/20190311/1279.jpg","id":303,"isZan":0,"levelDiamondIcon":"http://public.blockadm.com/image/level/user_lv1.png","levelId":0,"levelName":"青铜节点","levelNameIcon":"http://public.blockadm.com/image/level/lv1.png","masterMemberId":0,"memberId":1369,"newsLessonsCommentId":157,"nickName":"风月薄凉","objectId":74,"objectType":1,"parentId":0,"vipLevelName":"","vipState":0,"zanCount":0},{"commentType":1,"content":"按时间从拉萨九零","createTime":"2019-04-03 17:18:23","icon":"http://image.blockadm.com/image/robot/20190311/627.jpg","id":302,"isZan":0,"levelDiamondIcon":"http://public.blockadm.com/image/level/user_lv1.png","levelId":0,"levelName":"青铜节点","levelNameIcon":"http://public.blockadm.com/image/level/lv1.png","masterIcon":"http://image.blockadm.com/image/robot/20190311/1078.jpg","masterMemberId":1168,"masterNickName":"青春，要拼搏°","memberId":717,"newsLessonsCommentId":157,"nickName":"穷追一个梦","objectId":74,"objectType":1,"parentId":301,"vipLevelName":"","vipState":0,"zanCount":0},{"commentType":0,"content":"哈市","createTime":"2019-04-03 17:18:19","icon":"http://image.blockadm.com/image/robot/20190311/1078.jpg","id":301,"isZan":0,"levelDiamondIcon":"http://public.blockadm.com/image/level/user_lv1.png","levelId":0,"levelName":"青铜节点","levelNameIcon":"http://public.blockadm.com/image/level/lv1.png","masterMemberId":0,"memberId":1168,"newsLessonsCommentId":157,"nickName":"青春，要拼搏°","objectId":74,"objectType":1,"parentId":0,"vipLevelName":"","vipState":0,"zanCount":0}],"content":"专栏课程评论","createTime":"2019-04-03 17:12","icon":"http://image.blockadm.com/image/robot/20190311/1700.jpg","id":157,"isTop":0,"isZan":0,"levelDiamondIcon":"http://public.blockadm.com/image/level/user_lv1.png","levelId":0,"levelName":"青铜节点","levelNameIcon":"http://public.blockadm.com/image/level/lv1.png","memberId":1790,"nickName":"ご緑wμ子怪亽","objectId":74,"objectType":1,"replyCount":3,"title":"个人个人","updateTime":"2019-04-03 17:12:49","vipState":0,"zanCount":0}]
     * size : 20
     * total : 2
     */

    private int current;
    private int pages;
    private int size;
    private int total;
    private List<RecordsBean> records;

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean   implements Serializable {
        /**
         * commentReplyList : [{"commentType":1,"content":"VS DVD三百 ","createTime":"2019-04-03 17:50:07","icon":"http://image.blockadm.com/image/robot/20190311/610.jpg","id":305,"isZan":0,"levelDiamondIcon":"http://public.blockadm.com/image/level/user_lv1.png","levelId":0,"levelName":"青铜节点","levelNameIcon":"http://public.blockadm.com/image/level/lv1.png","masterIcon":"http://image.blockadm.com/image/robot/20190311/1540.jpg","masterMemberId":1630,"masterNickName":"すすり泣","memberId":700,"newsLessonsCommentId":158,"nickName":"少女心英雄梦","objectId":74,"objectType":1,"parentId":304,"vipLevelName":"","vipState":0,"zanCount":0},{"commentType":0,"content":"吃饭撒v但是","createTime":"2019-04-03 17:50:01","icon":"http://image.blockadm.com/image/robot/20190311/1540.jpg","id":304,"isZan":0,"levelDiamondIcon":"http://public.blockadm.com/image/level/user_lv1.png","levelId":0,"levelName":"青铜节点","levelNameIcon":"http://public.blockadm.com/image/level/lv1.png","masterMemberId":0,"memberId":1630,"newsLessonsCommentId":158,"nickName":"すすり泣","objectId":74,"objectType":1,"parentId":0,"vipLevelName":"","vipState":0,"zanCount":0}]
         * content : 测试
         * createTime : 2019-04-03 17:18
         * icon : http://image.blockadm.com/image/robot/20190311/174.jpg
         * id : 158
         * isTop : 0
         * isZan : 0
         * levelDiamondIcon : http://public.blockadm.com/image/level/user_lv1.png
         * levelId : 0
         * levelName : 青铜节点
         * levelNameIcon : http://public.blockadm.com/image/level/lv1.png
         * memberId : 264
         * nickName : 笑说再见
         * objectId : 74
         * objectType : 1
         * replyCount : 2
         * title : 个人个人
         * updateTime : 2019-04-03 17:18:05
         * vipState : 0
         * zanCount : 0
         */

        private String content;
        private String createTime;
        private String icon;
        private int id;
        private int isTop;
        private int isZan;
        private String levelDiamondIcon;
        private int levelId;
        private String levelName;
        private String levelNameIcon;
        private int memberId;
        private String nickName;
        private int objectId;
        private int objectType;
        private int replyCount;
        private String title;
        private String updateTime;
        private int vipState;
        private int zanCount;
        private List<CommentReplyListBean> commentReplyList;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
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

        public int getIsZan() {
            return isZan;
        }

        public void setIsZan(int isZan) {
            this.isZan = isZan;
        }

        public String getLevelDiamondIcon() {
            return levelDiamondIcon;
        }

        public void setLevelDiamondIcon(String levelDiamondIcon) {
            this.levelDiamondIcon = levelDiamondIcon;
        }

        public int getLevelId() {
            return levelId;
        }

        public void setLevelId(int levelId) {
            this.levelId = levelId;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getLevelNameIcon() {
            return levelNameIcon;
        }

        public void setLevelNameIcon(String levelNameIcon) {
            this.levelNameIcon = levelNameIcon;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
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

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
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

        public int getVipState() {
            return vipState;
        }

        public void setVipState(int vipState) {
            this.vipState = vipState;
        }

        public int getZanCount() {
            return zanCount;
        }

        public void setZanCount(int zanCount) {
            this.zanCount = zanCount;
        }

        public List<CommentReplyListBean> getCommentReplyList() {
            return commentReplyList;
        }

        public void setCommentReplyList(List<CommentReplyListBean> commentReplyList) {
            this.commentReplyList = commentReplyList;
        }

    }
}
