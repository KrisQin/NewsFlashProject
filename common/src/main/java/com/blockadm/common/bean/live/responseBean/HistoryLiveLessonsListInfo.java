package com.blockadm.common.bean.live.responseBean;

import com.blockadm.common.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kris on 2019/5/29
 *
 * @Describe TODO {  }
 */
public class HistoryLiveLessonsListInfo {

    private int total;
    private int size;
    private int current;
    private int pages;
    private List<HistoryLiveLessonsInfo> records;

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

    public List<HistoryLiveLessonsInfo> getRecords() {
        return ListUtils.isEmpty(records) ? new ArrayList<HistoryLiveLessonsInfo>() : records;
    }

    public void setRecords(List<HistoryLiveLessonsInfo> records) {
        this.records = records;
    }
}
