package com.blockadm.common.bean.params;

/**
 * Created by Kris on 2019/5/15
 *
 * @Describe TODO { 提问接口，入参 }
 */
public class QuestionParams {


    /**
     * authorId : 0
     * questionMoney : 0
     * rewardType : 0
     */

    private int authorId;
    private int questionMoney;
    private int rewardType;
    private int newsLiveLessonsId;
    private String questionContent;

    public QuestionParams(int authorId, int questionMoney, int rewardType,int newsLiveLessonsId,String questionContent) {
        this.authorId = authorId;
        this.questionMoney = questionMoney;
        this.rewardType = rewardType;
        this.newsLiveLessonsId = newsLiveLessonsId;
        this.questionContent = questionContent;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getQuestionMoney() {
        return questionMoney;
    }

    public void setQuestionMoney(int questionMoney) {
        this.questionMoney = questionMoney;
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }
}
