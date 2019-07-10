package com.blockadm.common.bean.live.responseBean;

import com.blockadm.common.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 2019/6/14
 *
 * @Describe TODO {  }
 */
public class LiveHBInfo {
    /**
     * msg : 红包抢成功
     * size : 3
     * money : 10000.0
     * receiveState : 1
     * rewardType : 1
     * receiveList : [{"id":461,"memberId":2197,"icon":"http://public.blockadm
     * .pro//image/2197/HYTX/20190524/74bfe107-5f97-445b-b927-a91c8ad4166a",
     * "nickName":"Kris","redpacketId":24,"money":6499,"rewardType":1,
     * "createDate":"2019-06-14 15:14:21","receiveDate":"2019-06-14 15:27:29",
     * "endDate":"2019-06-15 15:14:21","state":1}]
     * remainSize : 2
     */

    private String msg;
    private int size;
    private double money;
    private int receiveState;
    private int rewardType;
    private int remainSize;
    private List<ReceiveListBean> receiveList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getReceiveState() {
        return receiveState;
    }

    public void setReceiveState(int receiveState) {
        this.receiveState = receiveState;
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    public int getRemainSize() {
        return remainSize;
    }

    public void setRemainSize(int remainSize) {
        this.remainSize = remainSize;
    }

    public List<ReceiveListBean> getReceiveList() {
        return ListUtils.isEmpty(receiveList)? new ArrayList<ReceiveListBean>():receiveList;
    }

    public void setReceiveList(List<ReceiveListBean> receiveList) {
        this.receiveList = receiveList;
    }

    public static class ReceiveListBean {
        /**
         * id : 461
         * memberId : 2197
         * icon : http://public.blockadm.pro//image/2197/HYTX/20190524/74bfe107-5f97-445b
         * -b927-a91c8ad4166a
         * nickName : Kris
         * redpacketId : 24
         * money : 6499.0
         * rewardType : 1
         * createDate : 2019-06-14 15:14:21
         * receiveDate : 2019-06-14 15:27:29
         * endDate : 2019-06-15 15:14:21
         * state : 1
         */

        private int id;
        private int memberId;
        private String icon;
        private String nickName;
        private int redpacketId;
        private double money;
        private int rewardType;
        private String createDate;
        private String receiveDate;
        private String endDate;
        private int state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getRedpacketId() {
            return redpacketId;
        }

        public void setRedpacketId(int redpacketId) {
            this.redpacketId = redpacketId;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getRewardType() {
            return rewardType;
        }

        public void setRewardType(int rewardType) {
            this.rewardType = rewardType;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getReceiveDate() {
            return receiveDate;
        }

        public void setReceiveDate(String receiveDate) {
            this.receiveDate = receiveDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
