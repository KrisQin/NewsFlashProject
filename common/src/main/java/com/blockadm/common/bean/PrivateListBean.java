package com.blockadm.common.bean;

import java.util.List;

/**
 * Created by hp on 2019/1/30.
 */

public class PrivateListBean {


    /**
     * name : 谁可以看我的主页
     * list : [{"name":"所有人","isSelect":1,"id":2},{"name":"我关注的人","isSelect":0,"id":3},{"name":"关注我的人","isSelect":0,"id":4},{"name":"隐藏我的主页动态","isSelect":0,"id":5}]
     */

    private String name;
    private List<ListBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 所有人
         * isSelect : 1
         * id : 2
         */

        private String name;
        private int isSelect;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIsSelect() {
            return isSelect;
        }

        public void setIsSelect(int isSelect) {
            this.isSelect = isSelect;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
