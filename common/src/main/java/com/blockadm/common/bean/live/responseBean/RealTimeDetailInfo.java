package com.blockadm.common.bean.live.responseBean;

import java.util.List;

/**
 * Created by Kris on 2019/5/27
 *
 * @Describe TODO {  }
 */
public class RealTimeDetailInfo {

    /*
    1、isForbid : 是否被禁言[0 否、1：是]
    2、joinedLiveGroupMemberCount ：当前已加入直播课程群组总人数
    3、forbidMemberCount ：禁言总人数
    4、blacklistMemberCount ：黑名单总人数
    5、convertVisitCount ：到访总人数
    6、liveStatus ：直播状态（0 直播中、1：预备中（未开始）、2：已结束）
    7、newsLiveLessonsManagerList ：当前直播课程所有管理员
     */

    private int isForbid;
    private int visitCount;
    private int joinedLiveGroupMemberCount;
    private int forbidMemberCount;
    private int blacklistMemberCount;
    private String convertVisitCount;
    private int liveStatus;
    private List<NewsLiveLessonsManagerListBean> newsLiveLessonsManagerList;

    public int getIsForbid() {
        return isForbid;
    }

    public void setIsForbid(int isForbid) {
        this.isForbid = isForbid;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getJoinedLiveGroupMemberCount() {
        return joinedLiveGroupMemberCount;
    }

    public void setJoinedLiveGroupMemberCount(int joinedLiveGroupMemberCount) {
        this.joinedLiveGroupMemberCount = joinedLiveGroupMemberCount;
    }

    public int getForbidMemberCount() {
        return forbidMemberCount;
    }

    public void setForbidMemberCount(int forbidMemberCount) {
        this.forbidMemberCount = forbidMemberCount;
    }

    public int getBlacklistMemberCount() {
        return blacklistMemberCount;
    }

    public void setBlacklistMemberCount(int blacklistMemberCount) {
        this.blacklistMemberCount = blacklistMemberCount;
    }

    public String getConvertVisitCount() {
        return convertVisitCount;
    }

    public void setConvertVisitCount(String convertVisitCount) {
        this.convertVisitCount = convertVisitCount;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public List<NewsLiveLessonsManagerListBean> getNewsLiveLessonsManagerList() {
        return newsLiveLessonsManagerList;
    }

    public void setNewsLiveLessonsManagerList(List<NewsLiveLessonsManagerListBean> newsLiveLessonsManagerList) {
        this.newsLiveLessonsManagerList = newsLiveLessonsManagerList;
    }

    public static class NewsLiveLessonsManagerListBean {
        /**
         * managerNickName : 尚美中心
         * managerIcon : https://p1.pstatp.com/thumb/8111/7082912174
         * managerLoginIMAccount : blockAdm_IM_UserMemberId_9
         * managerMemberId : 9
         */

        private String managerNickName;
        private String managerIcon;
        private String managerLoginIMAccount;
        private int managerMemberId;

        public String getManagerNickName() {
            return managerNickName;
        }

        public void setManagerNickName(String managerNickName) {
            this.managerNickName = managerNickName;
        }

        public String getManagerIcon() {
            return managerIcon;
        }

        public void setManagerIcon(String managerIcon) {
            this.managerIcon = managerIcon;
        }

        public String getManagerLoginIMAccount() {
            return managerLoginIMAccount;
        }

        public void setManagerLoginIMAccount(String managerLoginIMAccount) {
            this.managerLoginIMAccount = managerLoginIMAccount;
        }

        public int getManagerMemberId() {
            return managerMemberId;
        }

        public void setManagerMemberId(int managerMemberId) {
            this.managerMemberId = managerMemberId;
        }
    }

}
