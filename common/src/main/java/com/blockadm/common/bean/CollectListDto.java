package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/2/14.
 */

public class CollectListDto {


    /**
     * records : [{"createTime":"2019-02-13 18:58:31","obejctId":1,"typeId":4,"id":1,"coverImgs":"https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2017-09-27/297f5edb1e984613083a2d3cc0c5bb36.png","title":"全球聚焦区块链活动","memberId":6},{"createTime":"2019-02-13 18:58:22","obejctId":4,"typeId":4,"id":4,"coverImgs":"http://wimg.huodongxing.com/logo/201709/7405591000900/672838549065930_v2.jpg","title":"EmTech China 全球新兴科技峰会","memberId":6},{"createTime":"2019-01-28 18:08:29","obejctId":1,"typeId":1,"id":43,"coverImgs":"https://sf1-ttcdn-tos.pstatp.com/img/web.business.image/201811165d0d8caec16bc6da491e92e6~172x120_cs.jpeg","title":"呵呵呵呵","memberId":6}]
     * total : 3
     * size : 10
     * current : 1
     * pages : 1
     */

    private int total;
    private int size;
    private int current;
    private int pages;
    private List<RecordsBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * createTime : 2019-02-13 18:58:31
         * obejctId : 1
         * typeId : 4
         * id : 1
         * coverImgs : https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2017-09-27/297f5edb1e984613083a2d3cc0c5bb36.png
         * title : 全球聚焦区块链活动
         * memberId : 6
         */

        private String createTime;
        private int obejctId;
        private int typeId;
        private int id;
        private String coverImgs;
        private String title;
        private int memberId;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getObejctId() {
            return obejctId;
        }

        public void setObejctId(int obejctId) {
            this.obejctId = obejctId;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCoverImgs() {
            return coverImgs;
        }

        public void setCoverImgs(String coverImgs) {
            this.coverImgs = coverImgs;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }
    }
}
