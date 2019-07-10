package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/2/22.
 */

public class MyStudyPageDTO {


    /**
     * records : [{"catalog":"0","lessonsHour":0,"typeName":"测试3","lessonsType":0,"title":"时间的朋友","readCount":14,"lessonsCount":20,"sysTypeId":12,"money":0,"createTime":"2019-02-22 15:15:33","subtitle":"时间是谁","lessonsTime":null,"buyPeopleCount":0,"id":28,"accessStatus":0,"coverImgs":"http://public.blockadm.pro//image/10/0/20190222/a8b9976e-e45e-4319-9adb-927476933078","finishLessonsCount":0,"status":0},{"catalog":"0","lessonsHour":0,"typeName":"火爆1","lessonsType":0,"title":"第三届戛纳电影节入围影片","readCount":93,"lessonsCount":12,"sysTypeId":7,"money":12234,"createTime":"2019-02-21 15:41:58","subtitle":"","lessonsTime":null,"buyPeopleCount":0,"id":27,"accessStatus":0,"coverImgs":"http://public.blockadm.pro//image/10/0/20190221/26b43bbf-4ec9-41bb-a6e8-9d4a41767d75","finishLessonsCount":0,"status":0}]
     * total : 42
     * size : 2
     * current : 1
     * pages : 21
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
         * catalog : 0
         * lessonsHour : 0
         * typeName : 测试3
         * lessonsType : 0
         * title : 时间的朋友
         * readCount : 14
         * lessonsCount : 20
         * sysTypeId : 12
         * money : 0
         * createTime : 2019-02-22 15:15:33
         * subtitle : 时间是谁
         * lessonsTime : null
         * buyPeopleCount : 0
         * id : 28
         * accessStatus : 0
         * coverImgs : http://public.blockadm.pro//image/10/0/20190222/a8b9976e-e45e-4319-9adb-927476933078
         * finishLessonsCount : 0
         * status : 0
         */

        private String catalog;
        private int lessonsHour;
        private String typeName;
        private int lessonsType;
        private String title;
        private int readCount;
        private int lessonsCount;
        private int sysTypeId;
        private double money;
        private String createTime;
        private String subtitle;
        private Object lessonsTime;
        private int buyPeopleCount;
        private int id;
        private int accessStatus;
        private String coverImgs;
        private int finishLessonsCount;
        private int status;

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public int getLessonsHour() {
            return lessonsHour;
        }

        public void setLessonsHour(int lessonsHour) {
            this.lessonsHour = lessonsHour;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getLessonsType() {
            return lessonsType;
        }

        public void setLessonsType(int lessonsType) {
            this.lessonsType = lessonsType;
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

        public int getSysTypeId() {
            return sysTypeId;
        }

        public void setSysTypeId(int sysTypeId) {
            this.sysTypeId = sysTypeId;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public Object getLessonsTime() {
            return lessonsTime;
        }

        public void setLessonsTime(Object lessonsTime) {
            this.lessonsTime = lessonsTime;
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

        public int getFinishLessonsCount() {
            return finishLessonsCount;
        }

        public void setFinishLessonsCount(int finishLessonsCount) {
            this.finishLessonsCount = finishLessonsCount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
