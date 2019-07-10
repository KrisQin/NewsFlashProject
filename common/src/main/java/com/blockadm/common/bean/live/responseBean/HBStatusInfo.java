package com.blockadm.common.bean.live.responseBean;

/**
 * Created by Kris on 2019/6/14
 *
 * @Describe TODO { 红包状态  }
 */
public class HBStatusInfo {


    /**
     * msg : 红包已经领取
     * receiveState : 2
     */

    private String msg;

    //0 红包抢完了  1 红包抢成功   2 红包已经领取  3 红包已过期  4 可领取
    private int receiveState;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getReceiveState() {
        return receiveState;
    }

    public void setReceiveState(int receiveState) {
        this.receiveState = receiveState;
    }
}
