package com.blockadm.common.bean;

import java.util.ArrayList;

/**
 * Created by hp on 2019/1/30.
 */

public class PrivateListDTO {

    private ArrayList<PrivateListBean>  privateListBeans = new ArrayList<>();

    public ArrayList<PrivateListBean> getPrivateListBeans() {
        return privateListBeans;
    }

    public void setPrivateListBeans(ArrayList<PrivateListBean> privateListBeans) {
        this.privateListBeans = privateListBeans;
    }
}
