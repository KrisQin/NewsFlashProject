package com.blockadm.common.bean.params;

import com.blockadm.common.bean.TagBeanDto;

import java.util.ArrayList;

/**
 * 登录Bean
 * Created by WBY on 2016/9/28.
 */
public class LoginBean {


    /**
     * isSubscribeLable : 1
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJ1c2VyX2lkIjoiMTEiLCJpc3MiOiJTZXJ2aWNlIiwiZXhwIjoxNTUwODg5NDExLCJpYXQiOjE1NTAwMjU0MTF9.Tv-tRDIhkxBHag9fq3S1oTLlk_6cFZGDtFLfNZ_mR8E
     */

    private ArrayList<TagBeanDto> subscribeLableList;
    private String token;

    public ArrayList<TagBeanDto> getSubscribeLableList() {
        return subscribeLableList;
    }

    public void setSubscribeLableList(ArrayList<TagBeanDto> subscribeLableList) {
        this.subscribeLableList = subscribeLableList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
