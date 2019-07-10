package com.blockadm.common.bean;

import java.util.ArrayList;

/**
 * Created by hp on 2019/1/14.
 */

public class NewsArticleCommentDTO {


        /**
         * newsArticle : {"id":310,"url":"https://www.jinse.com/news/bitcoin/303864.html","title":"1.10数字货币午间行情：时间节点将至","summary":"今天是时间节点，如果还不能选择方向，对于双方来说都会是进入高危阶段。","content":"<p> <img  max-width=\"1080px\"    width= \"80%\"  src=\"https://img.jinse.com/1468468_image3.png\" title=\"S1QDyNxGR8EUMVYg0igYkLW0Sb4uY3IxDqloP1Z1.png\" alt=\"S1QDyNxGR8EUMVYg0igYkLW0Sb4uY3IxDqloP1Z1.png\"/><\/p><p>文章系金色财经专栏作者供稿，发表言论仅代表其个人观点，仅供学习交流！金色盘面不会主动提供任何交易指导，亦不会收取任何费用指导交易，请读者仔细甄别，谨防上当。<\/p><p>&nbsp;<\/p><p>这种被6天线托着走的局面不常见，理论上早就该向上突破了，但是主力并不着急，我们看到作为先锋的trx、ada等已经完成了突破走势，而接下来主力是不是会发动攻势呢？密切关注6天线的支撑作用吧。<\/p><p>&nbsp;<\/p><p> <img  max-width=\"1080px\"  src=\"https://img.jinse.com/1494384_image3.png\" title=\"XNd8ENbg2aIn3atbduD3Yr4EPa1q6HRYTtbu3D4d.png\" alt=\"XNd8ENbg2aIn3atbduD3Yr4EPa1q6HRYTtbu3D4d.png\"/><\/p><p style=\"text-align: center;\">Xbt-usd &nbsp;日线图<\/p><p>&nbsp;<\/p><p>多周期图，1、2小时为时间反弹，因为指标已经到了高位，所以这里面临方向选择了，从4小时看，这里如果向上突破，符合多头主力预期，但是我们不做赌博。日线kdj也到了高位，除非分时向上突破，否则市场会转弱。我们要做的是顺势而为，突破了跟进就好，跌破了离场，或者套保。<\/p><p> <img  max-width=\"1080px\"  src=\"https://img.jinse.com/1494385_image3.png\" title=\"oNLbXadqYNnQqFIrAUmeJ8Np8SJQnXgqaRyXnEe9.png\" alt=\"oNLbXadqYNnQqFIrAUmeJ8Np8SJQnXgqaRyXnEe9.png\"/><\/p><p style=\"text-align: center;\">Xbt-usd &nbsp;多周期图<\/p><p>&nbsp;<\/p><p> <img  max-width=\"1080px\"  class=\"loadingclass\" id=\"loading_jqq3il8d\" src=\"http://boss.jinse.com/static/boss/js/lib/themes/default/images/spacer.gif\" title=\"正在上传...\"/><\/p><p>来源：<a href=\"https://www.jinse.com/news/bitcoin/303864.html\" target=\"_blank\">金色财经<\/a><\/p>","publishedAt":"9分钟前","publishedTime":"2019-01-10 12:10:20","resource":"金色财经","resourceUrl":"","author":"金色盘面","thumbnail":"https://img.jinse.com/1228388_image1.png","topicId":293864,"articleType":0,"checkStatus":1,"checkSysUserId":0,"checkTime":"2019-01-10 12:20:01","memberId":0,"createType":0,"recommendCount":0,"zanCount":0,"readCount":15,"commentCount":0,"forwardCount":0,"collectCount":0,"reportCount":0,"sysTypeId":1,"isTop":1,"updateTime":"2019-01-15 10:47:06"}
         * operateArticleData : {"isZan":0,"isRecommend":0,"isReportCount":0,"isCollect":0,"isForward":0}
         */

        private NewsArticleBean newsArticle;
        private OperateArticleDataBean operateArticleData;

        private CreateUserBean createUser;

        private String showContentHtmlUrl;




    public String getShowContentHtmlUrl() {
        return showContentHtmlUrl;
    }

    public void setShowContentHtmlUrl(String showContentHtmlUrl) {
        this.showContentHtmlUrl = showContentHtmlUrl;
    }

    public CreateUserBean getCreateUser() {
        return createUser;
    }

    public void setCreateUser(CreateUserBean createUser) {
        this.createUser = createUser;
    }

    public static class CreateUserBean {

        /*
        *   "credentialsState": 1,
            "icon": "https://p1.pstatp.com/thumb/8111/7082912174",
            "levelDiamondIcon": "http://public.blockadm.com/image/level/user_lv1.png",
            "levelId": 0,
            "levelName": "青铜节点",
            "levelNameIcon": "http://public.blockadm.com/image/level/lv1.png",
            "memberId": 9,
            "nickName": "尚美中心",
            "vipState": 1
        *
        * */

        private int credentialsState;
        private  String icon;
        private String levelDiamondIcon;
        private int levelId;
        private String levelName;
        private String levelNameIcon;
        private String nickName;
        private int memberId;
        private int vipState;
        private int isFollowUserMember;

        public int getIsFollowUserMember() {
            return isFollowUserMember;
        }

        public void setIsFollowUserMember(int isFollowUserMember) {
            this.isFollowUserMember = isFollowUserMember;
        }

        public int getCredentialsState() {
            return credentialsState;
        }

        public void setCredentialsState(int credentialsState) {
            this.credentialsState = credentialsState;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getVipState() {
            return vipState;
        }

        public void setVipState(int vipState) {
            this.vipState = vipState;
        }
    }
        public NewsArticleBean getNewsArticle() {
            return newsArticle;
        }

        public void setNewsArticle(NewsArticleBean newsArticle) {
            this.newsArticle = newsArticle;
        }

        public OperateArticleDataBean getOperateArticleData() {
            return operateArticleData;
        }

        public void setOperateArticleData(OperateArticleDataBean operateArticleData) {
            this.operateArticleData = operateArticleData;
        }

        public static class NewsArticleBean {
            /**
             * id : 310
             * url : https://www.jinse.com/news/bitcoin/303864.html
             * title : 1.10数字货币午间行情：时间节点将至
             * summary : 今天是时间节点，如果还不能选择方向，对于双方来说都会是进入高危阶段。
             * content : <p> <img  max-width="1080px"    width= "80%"  src="https://img.jinse.com/1468468_image3.png" title="S1QDyNxGR8EUMVYg0igYkLW0Sb4uY3IxDqloP1Z1.png" alt="S1QDyNxGR8EUMVYg0igYkLW0Sb4uY3IxDqloP1Z1.png"/></p><p>文章系金色财经专栏作者供稿，发表言论仅代表其个人观点，仅供学习交流！金色盘面不会主动提供任何交易指导，亦不会收取任何费用指导交易，请读者仔细甄别，谨防上当。</p><p>&nbsp;</p><p>这种被6天线托着走的局面不常见，理论上早就该向上突破了，但是主力并不着急，我们看到作为先锋的trx、ada等已经完成了突破走势，而接下来主力是不是会发动攻势呢？密切关注6天线的支撑作用吧。</p><p>&nbsp;</p><p> <img  max-width="1080px"  src="https://img.jinse.com/1494384_image3.png" title="XNd8ENbg2aIn3atbduD3Yr4EPa1q6HRYTtbu3D4d.png" alt="XNd8ENbg2aIn3atbduD3Yr4EPa1q6HRYTtbu3D4d.png"/></p><p style="text-align: center;">Xbt-usd &nbsp;日线图</p><p>&nbsp;</p><p>多周期图，1、2小时为时间反弹，因为指标已经到了高位，所以这里面临方向选择了，从4小时看，这里如果向上突破，符合多头主力预期，但是我们不做赌博。日线kdj也到了高位，除非分时向上突破，否则市场会转弱。我们要做的是顺势而为，突破了跟进就好，跌破了离场，或者套保。</p><p> <img  max-width="1080px"  src="https://img.jinse.com/1494385_image3.png" title="oNLbXadqYNnQqFIrAUmeJ8Np8SJQnXgqaRyXnEe9.png" alt="oNLbXadqYNnQqFIrAUmeJ8Np8SJQnXgqaRyXnEe9.png"/></p><p style="text-align: center;">Xbt-usd &nbsp;多周期图</p><p>&nbsp;</p><p> <img  max-width="1080px"  class="loadingclass" id="loading_jqq3il8d" src="http://boss.jinse.com/static/boss/js/lib/themes/default/images/spacer.gif" title="正在上传..."/></p><p>来源：<a href="https://www.jinse.com/news/bitcoin/303864.html" target="_blank">金色财经</a></p>
             * publishedAt : 9分钟前
             * publishedTime : 2019-01-10 12:10:20
             * resource : 金色财经
             * resourceUrl :
             * author : 金色盘面
             * thumbnail : https://img.jinse.com/1228388_image1.png
             * topicId : 293864
             * articleType : 0
             * checkStatus : 1
             * checkSysUserId : 0
             * checkTime : 2019-01-10 12:20:01
             * memberId : 0
             * createType : 0
             * recommendCount : 0
             * zanCount : 0
             * readCount : 15
             * commentCount : 0
             * forwardCount : 0
             * collectCount : 0
             * reportCount : 0
             * sysTypeId : 1
             * isTop : 1
             * updateTime : 2019-01-15 10:47:06
             *
             * "sysLableList": [
             {
             "id": 1577,
             "newsArticleId": 65,
             "sysLableId": 61,
             "sysLableName": "热门推荐"
             },
             {
             "id": 3063,
             "newsArticleId": 65,
             "sysLableId": 62,
             "sysLableName": "币圈那点事"
             }
             ]
             */

            private int id;
            private String url;
            private String title;
            private String summary;
            private String content;
            private String publishedAt;
            private String publishedTime;
            private String resource;
            private String resourceUrl;
            private String author;
            private String thumbnail;
            private int topicId;
            private int articleType;
            private int checkStatus;
            private int checkSysUserId;
            private String checkTime;
            private int memberId;
            private int createType;
            private int recommendCount;
            private int zanCount;
            private int readCount;
            private String convertReadCount;
            private int commentCount;
            private int forwardCount;
            private int collectCount;
            private int reportCount;
            private int sysTypeId;
            private int isTop;
            private String updateTime;
            private String admStatement;
            public String getAdmStatement() {
                return admStatement;
            }

            public void setAdmStatement(String admStatement) {
                this.admStatement = admStatement;
            }

            private ArrayList<SysLableListBean> sysLableList = new ArrayList<>();

            public ArrayList<SysLableListBean> getSysLableList() {
                return sysLableList;
            }

            public void setSysLableList(ArrayList<SysLableListBean> sysLableList) {
                this.sysLableList = sysLableList;
            }

            public static class SysLableListBean {
                private int id;
                private int newsArticleId;
                private int sysLableId;
                private String  sysLableName;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getNewsArticleId() {
                    return newsArticleId;
                }

                public void setNewsArticleId(int newsArticleId) {
                    this.newsArticleId = newsArticleId;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPublishedAt() {
                return publishedAt;
            }

            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            public String getPublishedTime() {
                return publishedTime;
            }

            public void setPublishedTime(String publishedTime) {
                this.publishedTime = publishedTime;
            }

            public String getResource() {
                return resource;
            }

            public void setResource(String resource) {
                this.resource = resource;
            }

            public String getResourceUrl() {
                return resourceUrl;
            }

            public void setResourceUrl(String resourceUrl) {
                this.resourceUrl = resourceUrl;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public int getTopicId() {
                return topicId;
            }

            public void setTopicId(int topicId) {
                this.topicId = topicId;
            }

            public int getArticleType() {
                return articleType;
            }

            public void setArticleType(int articleType) {
                this.articleType = articleType;
            }

            public int getCheckStatus() {
                return checkStatus;
            }

            public void setCheckStatus(int checkStatus) {
                this.checkStatus = checkStatus;
            }

            public int getCheckSysUserId() {
                return checkSysUserId;
            }

            public void setCheckSysUserId(int checkSysUserId) {
                this.checkSysUserId = checkSysUserId;
            }

            public String getCheckTime() {
                return checkTime;
            }

            public void setCheckTime(String checkTime) {
                this.checkTime = checkTime;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getCreateType() {
                return createType;
            }

            public void setCreateType(int createType) {
                this.createType = createType;
            }

            public int getRecommendCount() {
                return recommendCount;
            }

            public void setRecommendCount(int recommendCount) {
                this.recommendCount = recommendCount;
            }

            public int getZanCount() {
                return zanCount;
            }

            public void setZanCount(int zanCount) {
                this.zanCount = zanCount;
            }

            public String getConvertReadCount() {
                return convertReadCount;
            }

            public void setConvertReadCount(String convertReadCount) {
                this.convertReadCount = convertReadCount;
            }

            public int getReadCount() {
                return readCount;
            }

            public void setReadCount(int readCount) {
                this.readCount = readCount;
            }

            public int getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(int commentCount) {
                this.commentCount = commentCount;
            }

            public int getForwardCount() {
                return forwardCount;
            }

            public void setForwardCount(int forwardCount) {
                this.forwardCount = forwardCount;
            }

            public int getCollectCount() {
                return collectCount;
            }

            public void setCollectCount(int collectCount) {
                this.collectCount = collectCount;
            }

            public int getReportCount() {
                return reportCount;
            }

            public void setReportCount(int reportCount) {
                this.reportCount = reportCount;
            }

            public int getSysTypeId() {
                return sysTypeId;
            }

            public void setSysTypeId(int sysTypeId) {
                this.sysTypeId = sysTypeId;
            }

            public int getIsTop() {
                return isTop;
            }

            public void setIsTop(int isTop) {
                this.isTop = isTop;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }

        public static class OperateArticleDataBean {
            /**
             * isZan : 0
             * isRecommend : 0
             * isReportCount : 0
             * isCollect : 0
             * isForward : 0
             */

            private int isZan;
            private int isRecommend;
            private int isReportCount;
            private int isCollect;
            private int isForward;

            public int getIsZan() {
                return isZan;
            }

            public void setIsZan(int isZan) {
                this.isZan = isZan;
            }

            public int getIsRecommend() {
                return isRecommend;
            }

            public void setIsRecommend(int isRecommend) {
                this.isRecommend = isRecommend;
            }

            public int getIsReportCount() {
                return isReportCount;
            }

            public void setIsReportCount(int isReportCount) {
                this.isReportCount = isReportCount;
            }

            public int getIsCollect() {
                return isCollect;
            }

            public void setIsCollect(int isCollect) {
                this.isCollect = isCollect;
            }

            public int getIsForward() {
                return isForward;
            }

            public void setIsForward(int isForward) {
                this.isForward = isForward;
            }
        }
    }
