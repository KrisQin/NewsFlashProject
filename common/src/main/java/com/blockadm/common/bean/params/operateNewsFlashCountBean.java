package com.blockadm.common.bean.params;

/**
 * Created by hp on 2019/1/11.
 */

public class operateNewsFlashCountBean {


    /*
    *
    * Name	Description
Authorization
string
(header)
token

id *
integer
(query)
快讯id

operateCount
ref
(query)
操作类型(0 点赞，1：倒赞)

choose
ref
(query)
选择类型[0 确定(点赞) ，1：取消(点赞)]
    *
    * */

    private int id;
    private int operateType;
    private int  choose;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public int getChoose() {
        return choose;
    }

    public void setChoose(int choose) {
        this.choose = choose;
    }
}
