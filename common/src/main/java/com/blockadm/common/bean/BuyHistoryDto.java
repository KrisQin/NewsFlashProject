package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/3/15.
 */

public class BuyHistoryDto {


    /**
     * current : 1
     * pages : 1
     * records : [{"accessStatus":1,"buyState":1,"catalog":"0","coverImgs":"http://image.blockadm.pro//image/10/0/20190218/5d9066c5-04f0-40be-8e1a-cf92ffba7bab","createTime":"2019-03-07 11:57:35","finishLessonsCount":2,"id":26,"lessonsCount":25,"lessonsHour":0,"lessonsType":0,"money":10,"readCount":341,"status":0,"subtitle":"新专栏  完整测试","sysTypeId":9,"title":"新专栏  完整测试","typeName":"火爆啦"},{"accessStatus":1,"buyState":1,"catalog":"0","coverImgs":"http://public.blockadm.pro//image/10/0/20190225/959356d1-4105-4968-b36e-782f9eec926c","createTime":"2019-03-07 11:52:18","finishLessonsCount":0,"id":22,"lessonsCount":2,"lessonsHour":0,"lessonsType":0,"money":10,"readCount":132,"status":0,"subtitle":"通证经济","sysTypeId":8,"title":"通证经济","typeName":"火爆咔"},{"accessStatus":1,"buyState":1,"catalog":"0","coverImgs":"http://public.blockadm.pro//image/10/0/20190225/40c2aa98-b995-452d-b40d-d08d28367b52","createTime":"2019-03-07 11:53:05","finishLessonsCount":0,"id":21,"lessonsCount":4,"lessonsHour":0,"lessonsType":0,"money":22,"readCount":121,"status":0,"subtitle":"热门套餐","sysTypeId":8,"title":"热门套餐","typeName":"火爆咔"},{"accessStatus":1,"buyState":1,"catalog":"0","coverImgs":"http://image.blockadm.pro//image/10/0/20190213/846dc821-8060-4425-b1f2-2718edc97759","createTime":"2019-03-07 11:55:21","finishLessonsCount":0,"id":15,"lessonsCount":3,"lessonsHour":0,"lessonsType":0,"money":20,"readCount":30,"status":0,"subtitle":"测试专栏2","sysTypeId":8,"title":"测试专栏2","typeName":"火爆咔"},{"accessStatus":1,"buyState":1,"catalog":"0","coverImgs":"https://p1.pstatp.com/list/190x124/pgc-image/3b4e317cbf3b4b9698bff686c3bc8b49","createTime":"2019-03-07 12:20:25","finishLessonsCount":0,"id":2,"lessonsCount":1,"lessonsHour":0,"lessonsType":0,"money":88,"readCount":531,"status":0,"subtitle":"哈哈如何","sysTypeId":7,"title":"淡定如何member_id=9","typeName":"火爆哈"}]
     * size : 10
     * total : 5
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

    public static class RecordsBean {
        /**
         * accessStatus : 1
         * buyState : 1
         * catalog : 0
         * coverImgs : http://image.blockadm.pro//image/10/0/20190218/5d9066c5-04f0-40be-8e1a-cf92ffba7bab
         * createTime : 2019-03-07 11:57:35
         * finishLessonsCount : 2
         * id : 26
         * lessonsCount : 25
         * lessonsHour : 0
         * lessonsType : 0
         * money : 10.0
         * readCount : 341
         * status : 0
         * subtitle : 新专栏  完整测试
         * sysTypeId : 9
         * title : 新专栏  完整测试
         * typeName : 火爆啦
         */

        private int accessStatus;
        private int buyState;
        private String catalog;
        private String coverImgs;
        private String createTime;
        private int finishLessonsCount;
        private int id;
        private int lessonsCount;
        private int lessonsHour;
        private int lessonsType;
        private double money;
        private int readCount;
        private int status;
        private String subtitle;
        private int sysTypeId;
        private String title;
        private String typeName;

        public int getAccessStatus() {
            return accessStatus;
        }

        public void setAccessStatus(int accessStatus) {
            this.accessStatus = accessStatus;
        }

        public int getBuyState() {
            return buyState;
        }

        public void setBuyState(int buyState) {
            this.buyState = buyState;
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
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

        public int getFinishLessonsCount() {
            return finishLessonsCount;
        }

        public void setFinishLessonsCount(int finishLessonsCount) {
            this.finishLessonsCount = finishLessonsCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLessonsCount() {
            return lessonsCount;
        }

        public void setLessonsCount(int lessonsCount) {
            this.lessonsCount = lessonsCount;
        }

        public int getLessonsHour() {
            return lessonsHour;
        }

        public void setLessonsHour(int lessonsHour) {
            this.lessonsHour = lessonsHour;
        }

        public int getLessonsType() {
            return lessonsType;
        }

        public void setLessonsType(int lessonsType) {
            this.lessonsType = lessonsType;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
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
    }
}
