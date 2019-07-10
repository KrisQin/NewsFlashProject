package com.blockadm.common.bean;

import java.io.Serializable;

/**
 * Created by hp on 2019/3/7.
 */

public class AcountListDtoRecordBean   implements Serializable {

    /**
     * channelName : A钻
     * createDate : 2019-02-28 12:04:35
     * detailIcon : http://image.blockadm.pro/sys/account/png/detail_success.png
     * icon : http://image.blockadm.pro/sys/account/png/type_award.png
     * id : 149
     * io : 0
     * orderNumber : 2019022812041551326675158703927
     * orderState : 1
     * payType : 2
     * point : 100.0
     * relatedIsShow : 0
     * remark : 推荐会员认证A钻奖励
     * typeId : 1007
     * relatedNickName : 187****9001
     */

    private String channelName;
    private String createDate;
    private String detailIcon;
    private String icon;
    private int id;
    private int io;
    private String orderNumber;
    private int orderState;
    private int payType;
    private double point;
    private int relatedIsShow;
    private String remark;
    private int typeId;
    private String relatedNickName;
    private String withdrawEnter;
    private String withdrawCharge;

    public String getWithdrawEnter() {
        return withdrawEnter;
    }

    public void setWithdrawEnter(String withdrawEnter) {
        this.withdrawEnter = withdrawEnter;
    }

    public String getWithdrawCharge() {
        return withdrawCharge;
    }

    public void setWithdrawCharge(String withdrawCharge) {
        this.withdrawCharge = withdrawCharge;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDetailIcon() {
        return detailIcon;
    }

    public void setDetailIcon(String detailIcon) {
        this.detailIcon = detailIcon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIo() {
        return io;
    }

    public void setIo(int io) {
        this.io = io;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public int getRelatedIsShow() {
        return relatedIsShow;
    }

    public void setRelatedIsShow(int relatedIsShow) {
        this.relatedIsShow = relatedIsShow;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getRelatedNickName() {
        return relatedNickName;
    }

    public void setRelatedNickName(String relatedNickName) {
        this.relatedNickName = relatedNickName;
    }
}
