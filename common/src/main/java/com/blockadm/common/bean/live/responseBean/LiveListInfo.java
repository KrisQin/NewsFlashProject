package com.blockadm.common.bean.live.responseBean;

import com.blockadm.common.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 2019/5/7
 *
 * @Describe TODO { 群聊功能，直播列表 }
 */
public class LiveListInfo {

    /**
     * records : [{"openLessonsTime":"周二 04-30 19:20","nickName":"尚美中心","typeName":"精心推荐",
     * "icon":"https://p1.pstatp.com/thumb/8111/7082912174","title":"如何做的更好","point":0,
     * "sysTypeId":20,"visitCount":545,"money":0,"createTime":"2019-04-26 19:20:26",
     * "lessonsTime":"2019-04-30 19:22:20","id":1,"accessStatus":0,"coverImgs":"https://gss1
     * .baidu.com/6ONXsjip0QIZ8tyhnq/it/u=536406406,
     * 761445230&fm=173&app=49&f=JPEG?w=218&h=146&s=F7365D6EDCA59534D9AC4DB80300C012",
     * "payStatus":1,"convertVisitCount":"545","liveStatus":0,"memberId":9,"status":0},{
     * "openLessonsTime":"周日 05-05 14:16","nickName":"厉害了我的歌","typeName":"精心推荐",
     * "icon":"https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2019-01-17
     * /93dcb700c9b4b585d3aba427de3f4b9d.png","title":"哈哈哈","point":1000,"sysTypeId":20,
     * "visitCount":283,"money":10,"createTime":"2019-04-30 14:48:46",
     * "lessonsTime":"2019-05-05 14:49:16","id":2,"accessStatus":1,"coverImgs":"https://t11
     * .baidu.com/it/u=275794891,2636666570&fm=76","payStatus":1,"convertVisitCount":"283",
     * "liveStatus":0,"memberId":5,"status":0},{"openLessonsTime":"周五 05-31 10:18",
     * "nickName":"厉害了我的歌","typeName":"精心推荐","icon":"https://gss0.bdstatic
     * .com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2019-01-17/93dcb700c9b4b585d3aba427de3f4b9d
     * .png","title":"如何做的更好","point":0,"sysTypeId":20,"visitCount":17,"money":0,
     * "createTime":"2019-05-07 09:48:25","lessonsTime":"2019-05-31 10:48:18","id":3,
     * "accessStatus":2,"coverImgs":"https://gss0.bdstatic
     * .com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2019-05-06/6d97be37c983ef712984f3108ead8538
     * .gif","payStatus":1,"convertVisitCount":"17","liveStatus":0,"memberId":5,"status":0}]
     * total : 3
     * size : 10
     * current : 1
     * pages : 1
     */

    private int total;
    private int size;
    private int current;
    private int pages;
    private List<LiveRecordsInfo> records;

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

    public List<LiveRecordsInfo> getRecords() {
        return ListUtils.isEmpty(records)? new ArrayList<LiveRecordsInfo>():records;
    }

    public void setRecords(List<LiveRecordsInfo> records) {
        this.records = records;
    }

}
