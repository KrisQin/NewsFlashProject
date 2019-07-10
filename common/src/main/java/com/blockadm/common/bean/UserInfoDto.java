package com.blockadm.common.bean;

import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ListUtils;
import com.blockadm.common.utils.SharedpfTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2019/1/24.
 */

public class UserInfoDto implements Serializable {

    /**
     * area : null
     * follower : 0
     * nickName : hello
     * sex : 2
     * sign : 暂无签名
     * icon : https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2018-10-08
     * /927d286d776f9b9c4eff787a6cf93e43.png
     * vipState : 0
     * point : 0
     * concern : 0
     * vipLevel : 0
     * phone : 13265957514
     * msgSize : 0
     * age : null
     */

    private String area;
    private int follower;
    private String nickName;
    private int sex;
    private String sign;
    private String icon; //头像
    private int vipState;
    private double point;
    private int concern;
    private int vipLevel;
    private String phone;
    private int msgSize;
    private String age;
    private String diamond;
    private String serviceWX;
    private String withdrawAccountNumber;
    private String withdrawName;
    private int isBindWXWithdrawAccount;
    private String levelDiamondIcon;
    private int memberId;
    private int isSetLoginPwd;
    private int isBindWX; //  0 未绑定 1 已绑定
    private int isBindPhone; //是否已经绑定手机号： 0 未绑定 1 已绑定

    private int personalCredentialsSate;
    private int organizationCredentialsSate;
    private int isBindZFBWithdrawAccount;

    private String birthday;
    private int credentialsState;
    private int levelId; //会员等级 --> 0：青铜 1：白银 2：黄金 3：钻石 4：节点合伙人  5：董事合伙人
    private int isSetPayPwd; //0没有
    private String loginIMAccount;
    private String levelNameIcon;
    private String recommendImage;
    private int signingState;
    private String recommendCode;
    private int openLiveLessonStatus; //开课状态： 0 未开过课【一键开课】，1：已开过课【社群管理】
    private String loginIMSign;
    private String recommendUrl;
    private String diamondWithdrawFeeRate;

    private String rewardPointToDiamondRate; //奖金A点兑换A钻手续费
    private double rewardPoint; //奖金A点
    private String rewardPointToPointRate; //奖金A点兑换现金A点兑换手续费
    private String rewardPointToDiamondExchangeRate; //奖金A点兑换A钻比例
    private List<UserSubscribeLableListInfo> userSubscribeLableList;

    public int getIsBindPhone() {
        return isBindPhone;
    }

    public void setIsBindPhone(int isBindPhone) {
        this.isBindPhone = isBindPhone;
    }

    public String getDiamondWithdrawFeeRate() {
        return diamondWithdrawFeeRate;
    }

    public void setDiamondWithdrawFeeRate(String diamondWithdrawFeeRate) {
        this.diamondWithdrawFeeRate = diamondWithdrawFeeRate;
    }

    public int getIsBindWX() {
        return isBindWX;
    }

    public void setIsBindWX(int isBindWX) {
        this.isBindWX = isBindWX;
    }

    public String getRewardPointToDiamondRate() {
        return rewardPointToDiamondRate;
    }

    public void setRewardPointToDiamondRate(String rewardPointToDiamondRate) {
        this.rewardPointToDiamondRate = rewardPointToDiamondRate;
    }

    public double getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(double rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public String getRewardPointToPointRate() {
        return rewardPointToPointRate;
    }

    public void setRewardPointToPointRate(String rewardPointToPointRate) {
        this.rewardPointToPointRate = rewardPointToPointRate;
    }

    public String getRewardPointToDiamondExchangeRate() {
        return rewardPointToDiamondExchangeRate;
    }

    public void setRewardPointToDiamondExchangeRate(String rewardPointToDiamondExchangeRate) {
        this.rewardPointToDiamondExchangeRate = rewardPointToDiamondExchangeRate;
    }

    public List<UserSubscribeLableListInfo> getUserSubscribeLableList() {
        return ListUtils.isEmpty(userSubscribeLableList) ?
                new ArrayList<UserSubscribeLableListInfo>() : userSubscribeLableList;
    }

    public void setUserSubscribeLableList(List<UserSubscribeLableListInfo> userSubscribeLableList) {
        this.userSubscribeLableList = userSubscribeLableList;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getCredentialsState() {
        return credentialsState;
    }

    public void setCredentialsState(int credentialsState) {
        this.credentialsState = credentialsState;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getLoginIMAccount() {
        return loginIMAccount;
    }

    public void setLoginIMAccount(String loginIMAccount) {
        this.loginIMAccount = loginIMAccount;
    }

    public String getLevelNameIcon() {
        return levelNameIcon;
    }

    public void setLevelNameIcon(String levelNameIcon) {
        this.levelNameIcon = levelNameIcon;
    }

    public String getRecommendImage() {
        return recommendImage;
    }

    public void setRecommendImage(String recommendImage) {
        this.recommendImage = recommendImage;
    }

    public int getSigningState() {
        return signingState;
    }

    public void setSigningState(int signingState) {
        this.signingState = signingState;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public int getOpenLiveLessonStatus() {
        return openLiveLessonStatus;
    }

    public void setOpenLiveLessonStatus(int openLiveLessonStatus) {
        this.openLiveLessonStatus = openLiveLessonStatus;
    }

    public String getLoginIMSign() {
        return loginIMSign;
    }

    public void setLoginIMSign(String loginIMSign) {
        this.loginIMSign = loginIMSign;
    }

    public String getRecommendUrl() {

        SharedpfTools.getInstance().put(ConstantUtils.RecommendUrl, recommendUrl);
        return recommendUrl;
    }


    public void setRecommendUrl(String recommendUrl) {
        this.recommendUrl = recommendUrl;
    }

    public int getIsSetLoginPwd() {
        return isSetLoginPwd;
    }

    public void setIsSetLoginPwd(int isSetLoginPwd) {
        this.isSetLoginPwd = isSetLoginPwd;
    }

    public String getLevelDiamondIcon() {
        return levelDiamondIcon;
    }

    public void setLevelDiamondIcon(String levelDiamondIcon) {
        this.levelDiamondIcon = levelDiamondIcon;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getIsBindWXWithdrawAccount() {
        return isBindWXWithdrawAccount;
    }

    public void setIsBindWXWithdrawAccount(int isBindWXWithdrawAccount) {
        this.isBindWXWithdrawAccount = isBindWXWithdrawAccount;
    }

    public String getWithdrawAccountNumber() {
        return withdrawAccountNumber;
    }

    public void setWithdrawAccountNumber(String withdrawAccountNumber) {
        this.withdrawAccountNumber = withdrawAccountNumber;
    }

    public String getWithdrawName() {
        return withdrawName;
    }

    public void setWithdrawName(String withdrawName) {
        this.withdrawName = withdrawName;
    }


    public int getIsBindZFBWithdrawAccount() {
        return isBindZFBWithdrawAccount;
    }

    public void setIsBindZFBWithdrawAccount(int isBindZFBWithdrawAccount) {
        this.isBindZFBWithdrawAccount = isBindZFBWithdrawAccount;
    }

    public int getPersonalCredentialsSate() {
        return personalCredentialsSate;
    }

    public void setPersonalCredentialsSate(int personalCredentialsSate) {
        this.personalCredentialsSate = personalCredentialsSate;
    }

    public int getOrganizationCredentialsSate() {
        return organizationCredentialsSate;
    }

    public void setOrganizationCredentialsSate(int organizationCredentialsSate) {
        this.organizationCredentialsSate = organizationCredentialsSate;
    }

    public String getServiceWX() {
        return serviceWX;
    }

    public void setServiceWX(String serviceWX) {
        this.serviceWX = serviceWX;
    }

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }


    public int getIsSetPayPwd() {
        return isSetPayPwd;
    }

    public void setIsSetPayPwd(int isSetPayPwd) {
        this.isSetPayPwd = isSetPayPwd;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getVipState() {
        return vipState;
    }

    public void setVipState(int vipState) {
        this.vipState = vipState;
    }

    public int getPoint() {
        return (int) point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public int getConcern() {
        return concern;
    }

    public void setConcern(int concern) {
        this.concern = concern;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMsgSize() {
        return msgSize;
    }

    public void setMsgSize(int msgSize) {
        this.msgSize = msgSize;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
