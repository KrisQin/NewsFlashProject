package com.blockadm.common.bean.params;

/**
 * Created by Kris on 2019/5/15
 *
 * @Describe TODO {  }
 */
public class RewardParams {


    /**
     * authorId : 0
     * rewardType : 0:A钻  1：A点
     * typeId : 0
     */

    private int authorId;
    private int rewardType;
    private int typeId;
    private int newsLiveLessonsId;

    public RewardParams(int authorId, int rewardType, int typeId,int newsLiveLessonsId) {
        this.authorId = authorId;
        this.rewardType = rewardType;
        this.typeId = typeId;
        this.newsLiveLessonsId = newsLiveLessonsId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
