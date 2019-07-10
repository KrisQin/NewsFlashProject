package com.blockadm.common.bean.live.param;

/**
 * Created by Kris on 2019/6/14
 *
 * @Describe TODO {  }
 */
public class SendHBParam {


    /**
     * content : string
     * money : 0
     * newsLiveLessonsId : 0
     * rewardType : 0
     * size : 0
     */

    private String content;
    private double money;
    private int newsLiveLessonsId;
    private int rewardType;
    private int size;

    public SendHBParam(String content, double money, int newsLiveLessonsId, int rewardType, int size) {
        this.content = content;
        this.money = money;
        this.newsLiveLessonsId = newsLiveLessonsId;
        this.rewardType = rewardType;
        this.size = size;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getNewsLiveLessonsId() {
        return newsLiveLessonsId;
    }

    public void setNewsLiveLessonsId(int newsLiveLessonsId) {
        this.newsLiveLessonsId = newsLiveLessonsId;
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
