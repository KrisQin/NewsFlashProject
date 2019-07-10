package com.blockadm.common.bean;

import com.blockadm.common.utils.ListUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2019/1/21.
 */

public class NewsLessonsSpecialColumnDto implements Serializable {


    /**
     * records : [{"nickName":"4444","typeName":"火爆1","icon":"https://n.sinaimg
     * .cn/news/transform/590/w240h350/20190114/3nXu-hrpcmqw4953106.jpg","title":"淡定如何222",
     * "readCount":2,"content":"<p><img src=\"https://img.jinse.com/1414492\"
     * title=\"vKmIWfzmkt2iykxRTt1nezBbTFQbh36XaJyW9KND.jpeg\"
     * alt=\"vKmIWfzmkt2iykxRTt1nezBbTFQbh36XaJyW9KND
     * .jpeg\"/><\/p><p>2小时出现了一个背离，虽然级别一般，但这是下跌以来比较明显的一次筑底行为，所以我们要给予重视，如果形成，有可能是2浪结束的信号。<\/p><p
     * style=\"text-align: center;\"><img src=\"https://img.jinse.com/1460736_watermarknone.png\"
     * title=\"U2hlhWN2Sg0SKE1hPRiIwcP7YArdR9nTP3JBPKpB.png\"
     * alt=\"U2hlhWN2Sg0SKE1hPRiIwcP7YArdR9nTP3JBPKpB.png\"/>xbt-usd&nbsp;&nbsp;
     * 2小时图<\/p><p>60分钟到240分钟的kdj都调整到低位了，唯一的缺陷是均线的压制，我们主要观察2小时的背离反弹能否形成。而日线我们看到kdj开始发散向下，对于2
     * 浪调整来说，日线止跌就是信号。<\/p><p><img src=\"https://img.jinse.com/1460737_watermarknone.png\"
     * title=\"vdj9gC1Yv0xGodG6dOyRaodrnrkHzcFRWHSNDPMn.png\"
     * alt=\"vdj9gC1Yv0xGodG6dOyRaodrnrkHzcFRWHSNDPMn.png\"/><\/p><p style=\"text-align: center;
     * \">xbt-usd&nbsp; 多周期图<\/p><p><img src=\"https://img.jinse.com/1436411_watermarknone.png\"
     * title=\"MubjKYtMduiffOwA187R5QUQ9WFlYmyXdWkMrPQh.png\"
     * alt=\"MubjKYtMduiffOwA187R5QUQ9WFlYmyXdWkMrPQh.png\"/><\/p><\/p>","sysTypeId":7,
     * "lessonCount":1,"money":0,"createTime":"2019-01-16 11:38:44","subtitle":"哈哈如何",
     * "popularity":0,"id":2,"accessStatus":1,"coverImgs":"https://p1.pstatp
     * .com/list/190x124/pgc-image/3b4e317cbf3b4b9698bff686c3bc8b49","memberId":3,"status":0}]
     * total : 1
     * size : 10
     * current : 1
     * pages : 1
     */

    private int total;
    private int size;
    private int current;
    private int pages;
    private List<StudyRecordsBean> records;


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

    public List<StudyRecordsBean> getRecords() {
        return ListUtils.isEmpty(records)? new ArrayList<StudyRecordsBean>():records;
    }

    public void setRecords(List<StudyRecordsBean> records) {
        this.records = records;
    }
}
