package com.blockadm.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hp on 2019/1/21.
 */

public class NewsLessonsDTO  implements Serializable {


    /**
     * records : [{"nickName":"4444","typeName":"火爆1","icon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","pictureLiveUrl":"string","title":"ggg","readCount":8,"content":"哈哈哈121","sysTypeId":7,"nlscType":0,"audioUrl":"","money":0,"videoUrl":"string","createTime":"2019-01-15 15:15:51","nlscId":0,"subtitle":"string","lessonsTime":"2019-01-24 03:33:54","popularity":0,"id":2,"accessStatus":0,"coverImgs":"https://p3.pstatp.com/list/190x124/pgc-image/RFj4vT34VVQqLH","memberId":3,"status":0},{"nickName":"4444","typeName":"火爆1","icon":"https://n.sinaimg.cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","pictureLiveUrl":"嘎嘎嘎嘎嘎过过","title":"ggg","readCount":31,"content":"哈哈哈","sysTypeId":7,"nlscType":0,"audioUrl":"各过各的多多多多","money":0,"videoUrl":"多个梵蒂冈对对对","createTime":"2019-01-15 15:08:34","nlscId":0,"subtitle":"string","lessonsTime":"2019-01-24 11:33:54","popularity":0,"id":1,"accessStatus":1,"coverImgs":"https://sf1-ttcdn-tos.pstatp.com/img/web.business.image/201811165d0d8caec16bc6da491e92e6~172x120_cs.jpeg","memberId":3,"status":0}]
     * total : 2
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


}
