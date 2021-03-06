package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/1/26.
 */

public class FansListDTO {


    /**
     * records : [{"nickName":null,"icon":"https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2019-01-17/93dcb700c9b4b585d3aba427de3f4b9d.png","mutualStatus":1,"id":7},{"nickName":"144****4545","icon":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547643828515&di=9fadb1e4b9d628248a40e17c54013559&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d714554b393b000001bf72d062ba.jpg%401280w_1l_2o_100sh.jpg","mutualStatus":0,"id":14},{"nickName":"130****7455","icon":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547643828515&di=9fadb1e4b9d628248a40e17c54013559&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d714554b393b000001bf72d062ba.jpg%401280w_1l_2o_100sh.jpg","mutualStatus":0,"id":8}]
     * total : 3
     * size : 10
     * current : 1
     * pages : 1
     */

    private int total;
    private int size;
    private int current;
    private int pages;
    private List<UserListBean> records;

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

    public List<UserListBean> getRecords() {
        return records;
    }

    public void setRecords(List<UserListBean> records) {
        this.records = records;
    }


}
