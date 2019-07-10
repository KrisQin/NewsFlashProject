package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/3/29.
 */

public class AllListDto {


    /**
     * navigationTypeName : 每日科技播报
     * pageSize : 3
     * pageList : {"records":[{"vipPoint":0,"nickName":"爱德媒【LCD】","icon":"http://public
     * .blockadm.com//image/3083/HYTX/20190314/b2589154-db4d-4afd-9e47-c7c9ae6f8b1f",
     * "typeName":"人工智能","vipMoney":0,"vipState":1,"title":"人工智能每日热点资讯","readCount":2942,
     * "lessonsCount":40,"point":0,"signingState":1,"sysTypeId":13,"vipLevel":0,"money":0,
     * "memeberId":3083,"introduceVideoUrl":"","isTop":0,"levelId":0,"buyPeopleCount":0,
     * "id":15,"accessStatus":0,"coverImgs":"http://public.blockadm
     * .com//image/3083/0/20190325/94b541f2-05f7-478b-9298-feda0a9162b5",
     * "convertReadCount":"2942","finishLessonsCount":40},{"vipPoint":8,
     * "nickName":"爱德媒【LCD】","icon":"http://public.blockadm
     * .com//image/3083/HYTX/20190314/b2589154-db4d-4afd-9e47-c7c9ae6f8b1f","typeName":"区块链",
     * "vipMoney":0.08,"vipState":1,"title":"区块链每日热点资讯","readCount":865,"lessonsCount":40,
     * "point":10,"signingState":1,"sysTypeId":6,"vipLevel":0,"money":0.1,"memeberId":3083,
     * "introduceVideoUrl":"","isTop":0,"levelId":0,"buyPeopleCount":0,"id":14,
     * "accessStatus":0,"coverImgs":"http://public.blockadm
     * .com//image/3083/0/20190315/c687a8a9-695e-4462-be22-829df63b8289",
     * "convertReadCount":"865","finishLessonsCount":40}],"total":2,"size":3,"current":1,
     * "pages":1}
     * nstsId : 5
     * objectType : 0
     */

    private String navigationTypeName;
    private int pageSize;
    private PageListBean pageList;
    private int nstsId;
    private int objectType;

    public String getNavigationTypeName() {
        return navigationTypeName;
    }

    public void setNavigationTypeName(String navigationTypeName) {
        this.navigationTypeName = navigationTypeName;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public PageListBean getPageList() {
        return pageList;
    }

    public void setPageList(PageListBean pageList) {
        this.pageList = pageList;
    }

    public int getNstsId() {
        return nstsId;
    }

    public void setNstsId(int nstsId) {
        this.nstsId = nstsId;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public static class PageListBean {
        /**
         * records : [{"vipPoint":0,"nickName":"爱德媒【LCD】","icon":"http://public.blockadm
         * .com//image/3083/HYTX/20190314/b2589154-db4d-4afd-9e47-c7c9ae6f8b1f",
         * "typeName":"人工智能","vipMoney":0,"vipState":1,"title":"人工智能每日热点资讯","readCount":2942,
         * "lessonsCount":40,"point":0,"signingState":1,"sysTypeId":13,"vipLevel":0,
         * "money":0,"memeberId":3083,"introduceVideoUrl":"","isTop":0,"levelId":0,
         * "buyPeopleCount":0,"id":15,"accessStatus":0,"coverImgs":"http://public.blockadm
         * .com//image/3083/0/20190325/94b541f2-05f7-478b-9298-feda0a9162b5",
         * "convertReadCount":"2942","finishLessonsCount":40},{"vipPoint":8,
         * "nickName":"爱德媒【LCD】","icon":"http://public.blockadm
         * .com//image/3083/HYTX/20190314/b2589154-db4d-4afd-9e47-c7c9ae6f8b1f",
         * "typeName":"区块链","vipMoney":0.08,"vipState":1,"title":"区块链每日热点资讯","readCount":865,
         * "lessonsCount":40,"point":10,"signingState":1,"sysTypeId":6,"vipLevel":0,"money":0
         * .1,"memeberId":3083,"introduceVideoUrl":"","isTop":0,"levelId":0,
         * "buyPeopleCount":0,"id":14,"accessStatus":0,"coverImgs":"http://public.blockadm
         * .com//image/3083/0/20190315/c687a8a9-695e-4462-be22-829df63b8289",
         * "convertReadCount":"865","finishLessonsCount":40}]
         * total : 2
         * size : 3
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
             * vipPoint : 0
             * nickName : 爱德媒【LCD】
             * icon : http://public.blockadm.com//image/3083/HYTX/20190314/b2589154-db4d-4afd
             * -9e47-c7c9ae6f8b1f
             * typeName : 人工智能
             * vipMoney : 0.0
             * vipState : 1
             * title : 人工智能每日热点资讯
             * readCount : 2942
             * lessonsCount : 40
             * point : 0
             * signingState : 1
             * sysTypeId : 13
             * vipLevel : 0
             * money : 0.0
             * memeberId : 3083
             * introduceVideoUrl :
             * isTop : 0
             * levelId : 0
             * buyPeopleCount : 0
             * id : 15
             * accessStatus : 0
             * coverImgs : http://public.blockadm
             * .com//image/3083/0/20190325/94b541f2-05f7-478b-9298-feda0a9162b5
             * convertReadCount : 2942
             * finishLessonsCount : 40
             */

            private int vipPoint;
            private String nickName;
            private String icon;
            private String typeName;
            private double vipMoney;
            private int vipState;
            private String title;
            private int readCount;
            private int lessonsCount;
            private int point;
            private int signingState;
            private int sysTypeId;
            private int vipLevel;
            private double money;
            private int memeberId;
            private String introduceVideoUrl;
            private int isTop;
            private int levelId;
            private int buyPeopleCount;
            private int id;
            private int accessStatus;
            private String coverImgs;
            private String convertReadCount;
            private int finishLessonsCount;

            public int getVipPoint() {
                return vipPoint;
            }

            public void setVipPoint(int vipPoint) {
                this.vipPoint = vipPoint;
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

            public int getVipState() {
                return vipState;
            }

            public void setVipState(int vipState) {
                this.vipState = vipState;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getReadCount() {
                return readCount;
            }

            public void setReadCount(int readCount) {
                this.readCount = readCount;
            }

            public int getLessonsCount() {
                return lessonsCount;
            }

            public void setLessonsCount(int lessonsCount) {
                this.lessonsCount = lessonsCount;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public int getSigningState() {
                return signingState;
            }

            public void setSigningState(int signingState) {
                this.signingState = signingState;
            }

            public int getSysTypeId() {
                return sysTypeId;
            }

            public void setSysTypeId(int sysTypeId) {
                this.sysTypeId = sysTypeId;
            }

            public int getVipLevel() {
                return vipLevel;
            }

            public void setVipLevel(int vipLevel) {
                this.vipLevel = vipLevel;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public int getMemeberId() {
                return memeberId;
            }

            public void setMemeberId(int memeberId) {
                this.memeberId = memeberId;
            }

            public String getIntroduceVideoUrl() {
                return introduceVideoUrl;
            }

            public void setIntroduceVideoUrl(String introduceVideoUrl) {
                this.introduceVideoUrl = introduceVideoUrl;
            }

            public int getIsTop() {
                return isTop;
            }

            public void setIsTop(int isTop) {
                this.isTop = isTop;
            }

            public int getLevelId() {
                return levelId;
            }

            public void setLevelId(int levelId) {
                this.levelId = levelId;
            }

            public int getBuyPeopleCount() {
                return buyPeopleCount;
            }

            public void setBuyPeopleCount(int buyPeopleCount) {
                this.buyPeopleCount = buyPeopleCount;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAccessStatus() {
                return accessStatus;
            }

            public void setAccessStatus(int accessStatus) {
                this.accessStatus = accessStatus;
            }

            public String getCoverImgs() {
                return coverImgs;
            }

            public void setCoverImgs(String coverImgs) {
                this.coverImgs = coverImgs;
            }

            public String getConvertReadCount() {
                return convertReadCount;
            }

            public void setConvertReadCount(String convertReadCount) {
                this.convertReadCount = convertReadCount;
            }

            public int getFinishLessonsCount() {
                return finishLessonsCount;
            }

            public void setFinishLessonsCount(int finishLessonsCount) {
                this.finishLessonsCount = finishLessonsCount;
            }
        }
    }
}
