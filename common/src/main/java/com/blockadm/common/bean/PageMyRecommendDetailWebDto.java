package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/2/27.
 */

public class PageMyRecommendDetailWebDto {


    /**
     * records : [{"awardRemark":"*3推荐注册奖励","awardIcon":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547643828515&di=9fadb1e4b9d628248a40e17c54013559&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d714554b393b000001bf72d062ba.jpg%401280w_1l_2o_100sh.jpg","awardPoint":188,"createDate":"2019-02-20 10:54:02"}]
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
         * awardRemark : *3推荐注册奖励
         * awardIcon : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547643828515&di=9fadb1e4b9d628248a40e17c54013559&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d714554b393b000001bf72d062ba.jpg%401280w_1l_2o_100sh.jpg
         * awardPoint : 188
         * createDate : 2019-02-20 10:54:02
         */

        private String awardRemark;
        private String awardIcon;
        private int awardPoint;
        private String createDate;

        public String getAwardRemark() {
            return awardRemark;
        }

        public void setAwardRemark(String awardRemark) {
            this.awardRemark = awardRemark;
        }

        public String getAwardIcon() {
            return awardIcon;
        }

        public void setAwardIcon(String awardIcon) {
            this.awardIcon = awardIcon;
        }

        public int getAwardPoint() {
            return awardPoint;
        }

        public void setAwardPoint(int awardPoint) {
            this.awardPoint = awardPoint;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
