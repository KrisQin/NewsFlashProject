package com.blockadm.common.bean;

/**
 * Created by hp on 2019/2/21.
 */

public class VipDto {


    /**
     * hasBuyUrl : http://public.blockadm.pro/image/privilege/one_open.png
     * noBuyUrl : http://public.blockadm.pro/image/privilege/one_close.png
     * noBuyRemark : 开通即享权益
     * title : 点亮终身会员身份
     * hasBuyRemark : 尊享权益
     */

    private String hasBuyUrl;
    private String noBuyUrl;
    private String noBuyRemark;
    private String title;
    private String hasBuyRemark;

    public String getHasBuyUrl() {
        return hasBuyUrl;
    }

    public void setHasBuyUrl(String hasBuyUrl) {
        this.hasBuyUrl = hasBuyUrl;
    }

    public String getNoBuyUrl() {
        return noBuyUrl;
    }

    public void setNoBuyUrl(String noBuyUrl) {
        this.noBuyUrl = noBuyUrl;
    }

    public String getNoBuyRemark() {
        return noBuyRemark;
    }

    public void setNoBuyRemark(String noBuyRemark) {
        this.noBuyRemark = noBuyRemark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHasBuyRemark() {
        return hasBuyRemark;
    }

    public void setHasBuyRemark(String hasBuyRemark) {
        this.hasBuyRemark = hasBuyRemark;
    }
}
