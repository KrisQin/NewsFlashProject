package com.blockadm.common.bean;

import com.blockadm.common.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 2019/5/15
 *
 * @Describe TODO { 打赏金额列表实体类 }
 */
public class RewardListInfo {

    /**
     {"msg":"成功！","code":0,"data":{"pointList":[{"typeId":0,"rewardMoney":100},
     {"typeId":1,"rewardMoney":300},{"typeId":2,"rewardMoney":500},{"typeId":3,"rewardMoney":1000},
     {"typeId":4,"rewardMoney":2000},{"typeId":5,"rewardMoney":5000}],
     "diamondList":[{"typeId":0,"rewardMoney":5},{"typeId":1,"rewardMoney":15},{"typeId":2,"rewardMoney":25},
     {"typeId":3,"rewardMoney":50},{"typeId":4,"rewardMoney":100},{"typeId":5,"rewardMoney":250}]}}

     */

    private List<RewardInfo> pointList;
    private List<RewardInfo> diamondList;

    public List<RewardInfo> getPointList() {
        return ListUtils.isEmpty(pointList)? new ArrayList<RewardInfo>():pointList;
    }

    public void setPointList(List<RewardInfo> pointList) {
        this.pointList = pointList;
    }

    public List<RewardInfo> getDiamondList() {
        return ListUtils.isEmpty(diamondList)? new ArrayList<RewardInfo>():diamondList;
    }

    public void setDiamondList(List<RewardInfo> diamondList) {
        this.diamondList = diamondList;
    }
}
