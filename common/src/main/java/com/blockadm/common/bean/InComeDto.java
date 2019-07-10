package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/2/16.
 */

public class InComeDto {


    /**
     * yesterdayEarned : 0
     * allEarned : 220
     * sevenDataList : [{"dayDate":"2019-02-13","orderSum":1,"earnedSum":200},{"dayDate":"2019-02-16","orderSum":1,"earnedSum":20}]
     * dayEarned : 20
     */

    private int yesterdayEarned;
    private int allEarned;
    private int dayEarned;

    private int daySize;
    private List<SevenDataListBean> sevenDataList;

    public int getYesterdayEarned() {
        return yesterdayEarned;
    }

    public void setYesterdayEarned(int yesterdayEarned) {
        this.yesterdayEarned = yesterdayEarned;
    }

    public int getAllEarned() {
        return allEarned;
    }

    public void setAllEarned(int allEarned) {
        this.allEarned = allEarned;
    }

    public int getDayEarned() {
        return dayEarned;
    }

    public void setDayEarned(int dayEarned) {
        this.dayEarned = dayEarned;
    }

    public int getDaySize() {
        return daySize;
    }

    public void setDaySize(int daySize) {
        this.daySize = daySize;
    }

    public List<SevenDataListBean> getSevenDataList() {
        return sevenDataList;
    }

    public void setSevenDataList(List<SevenDataListBean> sevenDataList) {
        this.sevenDataList = sevenDataList;
    }

    public static class SevenDataListBean {
        /**
         * dayDate : 2019-02-13
         * orderSum : 1
         * earnedSum : 200
         */

        private String dayDate;
        private int orderSum;
        private int earnedSum;

        public String getDayDate() {
            return dayDate;
        }

        public void setDayDate(String dayDate) {
            this.dayDate = dayDate;
        }

        public int getOrderSum() {
            return orderSum;
        }

        public void setOrderSum(int orderSum) {
            this.orderSum = orderSum;
        }

        public int getEarnedSum() {
            return earnedSum;
        }

        public void setEarnedSum(int earnedSum) {
            this.earnedSum = earnedSum;
        }
    }
}
