package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/1/28.
 */

public class LessonsAndNlscDTO {


    /**
     * records : [{"nickName":"小星星","icon":"https://p1.pstatp.com/thumb/8111/7082912174","vipState":0,"title":"淡定如何member_id=9","readCount":304,"lessonsCount":1,"vipLevel":0,"money":88,"memeberId":9,"typeId":2,"id":2,"accessStatus":1,"coverImgs":"https://p1.pstatp.com/list/190x124/pgc-image/3b4e317cbf3b4b9698bff686c3bc8b49"},{"nickName":"小星星","icon":"https://p1.pstatp.com/thumb/8111/7082912174","vipState":0,"title":"呵呵呵呵member_id=9","readCount":164,"lessonsCount":0,"vipLevel":0,"money":10,"memeberId":9,"typeId":1,"id":1,"accessStatus":1,"coverImgs":"https://sf1-ttcdn-tos.pstatp.com/img/web.business.image/201811165d0d8caec16bc6da491e92e6~172x120_cs.jpeg"},{"nickName":"奔跑的蜗牛","icon":"https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2019-01-17/93dcb700c9b4b585d3aba427de3f4b9d.png","vipState":0,"title":"成功如何member_id=3","readCount":160,"lessonsCount":1,"vipLevel":0,"money":0,"memeberId":3,"typeId":2,"id":1,"accessStatus":0,"coverImgs":"https://p3.pstatp.com/list/190x124/pgc-image/1526058861473793186455a"},{"nickName":"奔跑的蜗牛","icon":"https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2019-01-17/93dcb700c9b4b585d3aba427de3f4b9d.png","vipState":0,"title":"测试中member_id=3","readCount":31,"lessonsCount":0,"vipLevel":0,"money":0,"memeberId":3,"typeId":1,"id":2,"accessStatus":0,"coverImgs":"https://p3.pstatp.com/list/190x124/pgc-image/RFj4vT34VVQqLH"},{"nickName":"奔跑的蜗牛","icon":"https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2019-01-17/93dcb700c9b4b585d3aba427de3f4b9d.png","vipState":0,"title":"吞吞ggg吐吐","readCount":17,"lessonsCount":0,"vipLevel":0,"money":10,"memeberId":3,"typeId":1,"id":3,"accessStatus":1,"coverImgs":"https://p1.pstatp.com/list/190x124/pgc-image/RFh4Vz9F7vaeLs"},{"nickName":"奔跑的蜗牛","icon":"https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2019-01-17/93dcb700c9b4b585d3aba427de3f4b9d.png","vipState":0,"title":"非常fff好","readCount":1,"lessonsCount":0,"vipLevel":0,"money":10,"memeberId":3,"typeId":1,"id":4,"accessStatus":1,"coverImgs":"https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2018-04-27/32fef589ddf49074825cad8460db4f54.jpg"}]
     * total : 6
     * size : 100
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
         * nickName : 小星星
         * icon : https://p1.pstatp.com/thumb/8111/7082912174
         * vipState : 0
         * title : 淡定如何member_id=9
         * readCount : 304
         * lessonsCount : 1
         * vipLevel : 0
         * money : 88
         * memeberId : 9
         * typeId : 2
         * id : 2
         * accessStatus : 1
         * coverImgs : https://p1.pstatp.com/list/190x124/pgc-image/3b4e317cbf3b4b9698bff686c3bc8b49
         */

        private String nickName;
        private String icon;
        private int vipState;
        private String title;
        private int readCount;
        private int lessonsCount;
        private int vipLevel;
        private double money;
        private int memeberId;
        private int typeId;
        private int id;
        private int accessStatus;
        private String coverImgs;

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

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
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
    }
}
