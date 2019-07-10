package com.blockadm.common.http;

/**
 * Created by hp on 2018/12/11.
 */

public class ApiException extends Throwable {

    private int code;
    private String msg;

    public ApiException (int code ,String msg){
        this.code  = code ;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
