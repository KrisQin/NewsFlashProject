package com.blockadm.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hp on 2019/2/14.
 */

public class AcountListDto implements Serializable{


    /**
     * current : 1
     * pages : 2
     * records : [{"channelName":"A钻","createDate":"2019-02-28 12:04:35","detailIcon":"http://image.blockadm.pro/sys/account/png/detail_success.png","icon":"http://image.blockadm.pro/sys/account/png/type_award.png","id":149,"io":0,"orderNumber":"2019022812041551326675158703927","orderState":1,"payType":2,"point":100,"relatedIsShow":0,"remark":"推荐会员认证A钻奖励","typeId":1007},{"channelName":"A点","createDate":"2019-02-28 10:52:43","detailIcon":"http://image.blockadm.pro/sys/account/png/detail_success.png","icon":"http://image.blockadm.pro/sys/account/png/type_award.png","id":141,"io":0,"orderNumber":"2019022810521551322363015227327","orderState":1,"payType":0,"point":188,"relatedIsShow":0,"relatedNickName":"187****9001","remark":"*侠推荐注册奖励","typeId":1003},{"createDate":"2019-02-14 17:54:08","id":94,"payType":0,"point":0,"relatedIsShow":0,"typeId":1005},{"createDate":"2019-02-16 17:54:01","id":92,"payType":0,"point":0,"relatedIsShow":0,"typeId":1005},{"channelName":"微信","createDate":"2019-02-25 12:01:27","detailIcon":"http://image.blockadm.pro/sys/account/png/detail_withdraw_ongoing.png","icon":"http://image.blockadm.pro/sys/account/png/type_trade.png","id":88,"io":1,"orderNumber":"454545","orderState":0,"payType":0,"point":45,"relatedIsShow":0,"relatedNickName":"hello","remark":"A点兑换","typeId":2001},{"channelName":"支付宝","createDate":"2019-02-25 12:01:27","detailIcon":"http://image.blockadm.pro/sys/account/png/detail_withdraw_fail.png","icon":"http://image.blockadm.pro/sys/account/png/type_trade.png","id":87,"io":1,"orderNumber":"777335","orderState":2,"payType":0,"point":320,"relatedIsShow":0,"relatedNickName":"hello","remark":"A点兑换","typeId":2001},{"channelName":"A点","createDate":"2019-02-24 12:01:27","detailIcon":"http://image.blockadm.pro/sys/account/png/detail_success.png","icon":"http://image.blockadm.pro/sys/account/png/type_buy.png","id":86,"io":1,"orderNumber":"555888","orderState":1,"payType":0,"point":120,"relatedIsShow":1,"relatedNickName":"我就","remark":"专栏购买","typeId":2004},{"channelName":"微信","createDate":"2019-02-24 12:01:27","detailIcon":"http://image.blockadm.pro/sys/account/png/detail_success.png","icon":"http://image.blockadm.pro/sys/account/png/type_trade.png","id":85,"io":1,"orderNumber":"444777","orderState":1,"payType":1,"point":1200,"relatedIsShow":0,"relatedNickName":"hello","remark":"开通VIP","typeId":2003},{"channelName":"微信","createDate":"2019-02-23 12:01:27","detailIcon":"http://image.blockadm.pro/sys/account/png/detail_success.png","icon":"http://image.blockadm.pro/sys/account/png/type_buy.png","id":84,"io":1,"orderNumber":"111000","orderState":1,"payType":1,"point":7500,"relatedIsShow":1,"relatedNickName":"我就","remark":"课程购买","typeId":2002},{"channelName":"网银","createDate":"2019-02-22 12:01:27","detailIcon":"http://image.blockadm.pro/sys/account/png/detail_success.png","icon":"http://image.blockadm.pro/sys/account/png/type_trade.png","id":83,"io":1,"orderNumber":"333111","orderState":1,"payType":0,"point":5000,"relatedIsShow":0,"relatedNickName":"hello","remark":"A点兑换","typeId":2001}]
     * size : 10
     * total : 18
     */

    private int current;
    private int pages;
    private int size;
    private int total;
    private List<AcountListDtoRecordBean> records;

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

    public List<AcountListDtoRecordBean> getRecords() {
        return records;
    }

    public void setRecords(List<AcountListDtoRecordBean> records) {
        this.records = records;
    }


}
