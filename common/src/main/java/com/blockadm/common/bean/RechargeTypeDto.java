package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/2/13.
 */

public class RechargeTypeDto {


    /**
     * rechargeTypeList : [{"money":10,"typeId":0,"point":1000},{"money":20,"typeId":1,"point":2000},{"money":30,"typeId":2,"point":3000},{"money":118,"typeId":3,"point":11800},{"money":300,"typeId":4,"point":30000},{"money":488,"typeId":5,"point":48800},{"money":618,"typeId":6,"point":61800},{"money":998,"typeId":7,"point":99800}]
     * bannerList : [{"id":1,"bannerUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547643828515&di=9fadb1e4b9d628248a40e17c54013559&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d714554b393b000001bf72d062ba.jpg%401280w_1l_2o_100sh.jpg","available":1,"typeId":1,"createDate":"2019-01-23 11:23:15"}]
     * explainList : ["1、A点充值后无法退款；","2、如果存在无法充值、充值失败等问题，添加客服微信解决。复制客服微信："]
     * wxCustomService : wx18773199188
     */

    private String wxCustomService;
    private List<RechargeTypeListBean> rechargeTypeList;
    private List<BannerListBean> bannerList;
    private List<String> explainList;
    private String wxCustomExplain;


    public String getWxCustomExplain() {
        return wxCustomExplain;
    }

    public void setWxCustomExplain(String wxCustomExplain) {
        this.wxCustomExplain = wxCustomExplain;
    }

    public String getWxCustomService() {
        return wxCustomService;
    }

    public void setWxCustomService(String wxCustomService) {
        this.wxCustomService = wxCustomService;
    }

    public List<RechargeTypeListBean> getRechargeTypeList() {
        return rechargeTypeList;
    }

    public void setRechargeTypeList(List<RechargeTypeListBean> rechargeTypeList) {
        this.rechargeTypeList = rechargeTypeList;
    }

    public List<BannerListBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerListBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<String> getExplainList() {
        return explainList;
    }

    public void setExplainList(List<String> explainList) {
        this.explainList = explainList;
    }

    public static class RechargeTypeListBean {
        /**
         * money : 10
         * typeId : 0
         * point : 1000
         */

        private double money;
        private int typeId;
        private int point;
        private boolean check;

        public boolean isCheck() {
            return check;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }
    }

    public static class BannerListBean {
        /**
         * id : 1
         * bannerUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547643828515&di=9fadb1e4b9d628248a40e17c54013559&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d714554b393b000001bf72d062ba.jpg%401280w_1l_2o_100sh.jpg
         * available : 1
         * typeId : 1
         * createDate : 2019-01-23 11:23:15
         */

        private int id;
        private String bannerUrl;
        private int available;
        private int typeId;
        private String createDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public int getAvailable() {
            return available;
        }

        public void setAvailable(int available) {
            this.available = available;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
