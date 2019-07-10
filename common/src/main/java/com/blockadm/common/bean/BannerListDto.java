package com.blockadm.common.bean;

/**
 * Created by hp on 2019/2/25.
 */

public class BannerListDto {


    /**
     * id : 16
     * bannerUrl : http://public.blockadm.pro//image/0/0/20190225/7741c3fe-73e0-4a21-90d9-9ad00e3ba3eb
     * redirectUrl : http://element-cn.eleme.io/#/zh-CN/component/tooltip
     * available : 1
     * typeId : 3
     * createDate : 2019-02-25 15:08:24
     * sort : 0
     */

    private int id;
    private String bannerUrl;
    private String redirectUrl;
    private int available;
    private int typeId;
    private String createDate;
    private int sort;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
