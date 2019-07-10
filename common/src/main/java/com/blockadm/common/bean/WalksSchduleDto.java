package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/2/12.
 */

public class WalksSchduleDto {


    /**
     * records : [{"address":"广东-广州","endDate":"2019-02-05 10:00:00","city":"广州",
     * "typeName":"劲爆活动","title":"区块链十年回顾历程","url":"https://www.hao123.com/","sysTypeId":13,
     * "province":"广东","createTime":"2019-01-31 10:55:52","isTop":0,"logo":"https://gss0.bdstatic
     * .com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2018-04-27/32fef589ddf49074825cad8460db4f54.jpg",
     * "id":2,"startDate":"2019-02-25 10:00:00"}]
     * total : 1
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
         * address : 广东-广州
         * endDate : 2019-02-05 10:00:00
         * city : 广州
         * typeName : 劲爆活动
         * title : 区块链十年回顾历程
         * url : https://www.hao123.com/
         * sysTypeId : 13
         * province : 广东
         * createTime : 2019-01-31 10:55:52
         * isTop : 0
         * logo : https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2018-04-27
         * /32fef589ddf49074825cad8460db4f54.jpg
         * id : 2
         * startDate : 2019-02-25 10:00:00
         */

        private String address;
        private String endDate;
        private String city;
        private String typeName;
        private String title;
        private String url;
        private int sysTypeId;
        private String province;
        private String createTime;
        private int isTop;
        private String logo;
        private int id;
        private String startDate;
        private String showTime;
        private int isCollection;
        private int position;

        private int createType;
        private int readCount;
        private int activityStatus;
        private String convertReadCount;

        public int getPosition() {
            return position;
        }

        public int getCreateType() {
            return createType;
        }

        public void setCreateType(int createType) {
            this.createType = createType;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public int getActivityStatus() {
            return activityStatus;
        }

        public void setActivityStatus(int activityStatus) {
            this.activityStatus = activityStatus;
        }

        public String getConvertReadCount() {
            return convertReadCount;
        }

        public void setConvertReadCount(String convertReadCount) {
            this.convertReadCount = convertReadCount;
        }

        public int getIsCollection() {
            return isCollection;
        }

        public void setIsCollection(int isCollection) {
            this.isCollection = isCollection;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getSysTypeId() {
            return sysTypeId;
        }

        public void setSysTypeId(int sysTypeId) {
            this.sysTypeId = sysTypeId;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getIsTop() {
            return isTop;
        }

        public void setIsTop(int isTop) {
            this.isTop = isTop;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
