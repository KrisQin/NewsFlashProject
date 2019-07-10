package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/2/27.
 */

public class GetMyLevelAppDto {


    /**
     * levelNameIcon : http://public.blockadm.pro/image/level/mc0.png
     * advanceBase : 10
     * levelList : [{"advanceBase":10,"receiveUrl":"http://public.blockadm.pro/image/level/lingqu100.png","receiveState":0,"levelId":0,"levelName":"青铜节点","advanceSize":0},{"advanceBase":20,"receiveUrl":"http://public.blockadm.pro/image/level/lingqu200.png","receiveState":0,"levelId":1,"levelName":"白银节点","advanceSize":0},{"advanceBase":30,"receiveUrl":"http://public.blockadm.pro/image/level/lingqu400.png","receiveState":0,"levelId":2,"levelName":"黄金节点","advanceSize":0},{"advanceBase":50,"receiveUrl":"http://public.blockadm.pro/image/level/lingqu1000.png","receiveState":0,"levelId":3,"levelName":"钻石节点","advanceSize":0},{"advanceBase":80,"receiveUrl":"http://public.blockadm.pro/image/level/lingqu1500.png","receiveState":0,"levelId":4,"levelName":"节点合伙人","advanceSize":0},{"advanceBase":100,"receiveUrl":"http://public.blockadm.pro/image/level/lingqu2500.png","receiveState":0,"levelId":5,"levelName":"董事合伙人","advanceSize":0}]
     * levelId : 0
     * levelDiamondIcon : http://public.blockadm.pro/image/level/zuan0.png
     * advanceSize : 0
     */

    private String levelNameIcon;
    private int advanceBase;
    private int levelId;
    private String levelDiamondIcon;
    private int advanceSize;
    private List<LevelListBean> levelList;

    public String getLevelNameIcon() {
        return levelNameIcon;
    }

    public void setLevelNameIcon(String levelNameIcon) {
        this.levelNameIcon = levelNameIcon;
    }

    public int getAdvanceBase() {
        return advanceBase;
    }

    public void setAdvanceBase(int advanceBase) {
        this.advanceBase = advanceBase;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getLevelDiamondIcon() {
        return levelDiamondIcon;
    }

    public void setLevelDiamondIcon(String levelDiamondIcon) {
        this.levelDiamondIcon = levelDiamondIcon;
    }

    public int getAdvanceSize() {
        return advanceSize;
    }

    public void setAdvanceSize(int advanceSize) {
        this.advanceSize = advanceSize;
    }

    public List<LevelListBean> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<LevelListBean> levelList) {
        this.levelList = levelList;
    }

    public static class LevelListBean {
        /**
         * advanceBase : 10
         * receiveUrl : http://public.blockadm.pro/image/level/lingqu100.png
         * receiveState : 0
         * levelId : 0
         * levelName : 青铜节点
         * advanceSize : 0
         */

        private int advanceBase;
        private String receiveUrl;
        private int receiveState;
        private int levelId;
        private String levelName;
        private int advanceSize;

        public int getAdvanceBase() {
            return advanceBase;
        }

        public void setAdvanceBase(int advanceBase) {
            this.advanceBase = advanceBase;
        }

        public String getReceiveUrl() {
            return receiveUrl;
        }

        public void setReceiveUrl(String receiveUrl) {
            this.receiveUrl = receiveUrl;
        }

        public int getReceiveState() {
            return receiveState;
        }

        public void setReceiveState(int receiveState) {
            this.receiveState = receiveState;
        }

        public int getLevelId() {
            return levelId;
        }

        public void setLevelId(int levelId) {
            this.levelId = levelId;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public int getAdvanceSize() {
            return advanceSize;
        }

        public void setAdvanceSize(int advanceSize) {
            this.advanceSize = advanceSize;
        }
    }
}
