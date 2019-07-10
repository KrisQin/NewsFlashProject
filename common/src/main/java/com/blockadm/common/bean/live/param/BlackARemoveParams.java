package com.blockadm.common.bean.live.param;

/**
 * Created by Kris on 2019/5/27
 *
 * @Describe TODO {  }
 */
public class BlackARemoveParams {


    /**
     * choose : 0:加入【禁言、黑名单】，1：移除【群组、禁言、黑名单】
     * memberId : 0
     * newsLiveLessonsId : 0
     * type : 0：加入群组 、1： 禁言、2：黑名单
     */

    private String choose;
    private int memberId;
    private int newsLiveLessonsId;
    private String type;

    public BlackARemoveParams(String choose, int memberId, int newsLiveLessonsId,
                              String type) {
        this.choose = choose;
        this.memberId = memberId;
        this.newsLiveLessonsId = newsLiveLessonsId;
        this.type = type;
    }

    public String getChoose() {
        return choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getNewsLiveLessonsId() {
        return newsLiveLessonsId;
    }

    public void setNewsLiveLessonsId(int newsLiveLessonsId) {
        this.newsLiveLessonsId = newsLiveLessonsId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }





}
