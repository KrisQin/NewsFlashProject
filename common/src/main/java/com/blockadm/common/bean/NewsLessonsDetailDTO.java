package com.blockadm.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hp on 2019/1/22.
 */

public class NewsLessonsDetailDTO  implements Serializable{


    /**
     * accessStatus : 1
     * articleCount : 13
     * content : <p>单课3单课3单课3单课3单课3单课3单课3单课3</p>
     * coverImgs : http://image.blockadm.pro//image/10/0/20190213/59e5f331-9a46-4934-935d-4bebd53c7b69
     * createTime : 2019-02-13 16:55:06
     * icon : https://p1.pstatp.com/thumb/83490000d07422d5fedb
     * id : 8
     * isCollection : 0
     * isDownload : 0
     * isFollow : 0
     * isSeeStatus : 1
     * isShare : 0
     * lessonsTime : 2019-02-20 16:00:00
     * lessonsTotal : 26
     * levelDiamondIcon : http://public.blockadm.pro/image/level/zuan0.png
     * levelId : 0
     * levelName : 青铜节点
     * levelNameIcon : http://public.blockadm.pro/image/level/mc0.png
     * memberId : 10
     * money : 5.0
     * nickName : 呵呵
     * nlscCount : 16
     * nlscId : 0
     * nlscType : 0
     * point : 500
     * popularity : 0
     * readCount : 14
     * signingState : 0
     * sort : 2
     * status : 0
     * subtitle : 单课3
     * sysLableList : [{"id":47,"objectId":8,"objectType":1,"sysLableId":45,"sysLableName":"直播"}]
     * sysTypeId : 9
     * title : 单课3
     * typeName : 火爆345
     * vipMoney : 4.5
     * vipPoint : 450
     * vipState : 0
     */

    private int accessStatus;
    private int articleCount;
    private String content;
    private String coverImgs;
    private String createTime;
    private String icon;
    private int id;
    private int isCollection;
    private int isDownload;
    private int isFollow;
    private int isSeeStatus;
    private int isShare;
    private String lessonsTime;
    private int lessonsTotal;
    private String levelDiamondIcon;
    private int levelId;
    private String levelName;
    private String levelNameIcon;
    private int memberId;
    private double money;
    private String nickName;
    private int nlscCount;
    private int nlscId;
    private int nlscType;
    private int point;
    private int popularity;
    private int readCount;
    private int signingState;
    private int sort;
    private int status;
    private String subtitle;
    private int sysTypeId;
    private String title;
    private String typeName;
    private double vipMoney;
    private int vipPoint;
    private String audioUrl;
    private String pictureLiveUrl;
    private String videoUrl;

    private String showContentHtmlUrl;

    public String getShowContentHtmlUrl() {
        return showContentHtmlUrl;
    }

    public void setShowContentHtmlUrl(String showContentHtmlUrl) {
        this.showContentHtmlUrl = showContentHtmlUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getPictureLiveUrl() {
        return pictureLiveUrl;
    }

    public void setPictureLiveUrl(String pictureLiveUrl) {
        this.pictureLiveUrl = pictureLiveUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    private int vipState;
    private List<SysLableListBean> sysLableList;

    public int getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(int accessStatus) {
        this.accessStatus = accessStatus;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImgs() {
        return coverImgs;
    }

    public void setCoverImgs(String coverImgs) {
        this.coverImgs = coverImgs;
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

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public int getIsDownload() {
        return isDownload;
    }

    public void setIsDownload(int isDownload) {
        this.isDownload = isDownload;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public int getIsSeeStatus() {
        return isSeeStatus;
    }

    public void setIsSeeStatus(int isSeeStatus) {
        this.isSeeStatus = isSeeStatus;
    }

    public int getIsShare() {
        return isShare;
    }

    public void setIsShare(int isShare) {
        this.isShare = isShare;
    }

    public String getLessonsTime() {
        return lessonsTime;
    }

    public void setLessonsTime(String lessonsTime) {
        this.lessonsTime = lessonsTime;
    }

    public int getLessonsTotal() {
        return lessonsTotal;
    }

    public void setLessonsTotal(int lessonsTotal) {
        this.lessonsTotal = lessonsTotal;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getNlscCount() {
        return nlscCount;
    }

    public void setNlscCount(int nlscCount) {
        this.nlscCount = nlscCount;
    }

    public int getNlscId() {
        return nlscId;
    }

    public void setNlscId(int nlscId) {
        this.nlscId = nlscId;
    }

    public int getNlscType() {
        return nlscType;
    }

    public void setNlscType(int nlscType) {
        this.nlscType = nlscType;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getSigningState() {
        return signingState;
    }

    public void setSigningState(int signingState) {
        this.signingState = signingState;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getSysTypeId() {
        return sysTypeId;
    }

    public void setSysTypeId(int sysTypeId) {
        this.sysTypeId = sysTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getVipMoney() {
        return vipMoney;
    }

    public void setVipMoney(double vipMoney) {
        this.vipMoney = vipMoney;
    }

    public int getVipPoint() {
        return vipPoint;
    }

    public void setVipPoint(int vipPoint) {
        this.vipPoint = vipPoint;
    }

    public int getVipState() {
        return vipState;
    }

    public void setVipState(int vipState) {
        this.vipState = vipState;
    }

    public List<SysLableListBean> getSysLableList() {
        return sysLableList;
    }

    public void setSysLableList(List<SysLableListBean> sysLableList) {
        this.sysLableList = sysLableList;
    }

    public static class SysLableListBean implements Serializable  {
        /**
         * id : 47
         * objectId : 8
         * objectType : 1
         * sysLableId : 45
         * sysLableName : 直播
         */

        private int id;
        private int objectId;
        private int objectType;
        private int sysLableId;
        private String sysLableName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getSysLableId() {
            return sysLableId;
        }

        public void setSysLableId(int sysLableId) {
            this.sysLableId = sysLableId;
        }

        public String getSysLableName() {
            return sysLableName;
        }

        public void setSysLableName(String sysLableName) {
            this.sysLableName = sysLableName;
        }
    }
}
