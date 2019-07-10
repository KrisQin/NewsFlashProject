package com.blockadm.common.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2019/3/6.
 */

public class LoginByThirdPartyDTO  implements Serializable{


    /**
     * isBindPhone : 0
     * subscribeLableList : []
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJ1c2VyX2lkIjoiODIiLCJpc3MiOiJTZXJ2aWNlIiwiZXh0ZW5kX2ZpZWxkIjoiMzk0ZTE0ZTFlYjhhNGNiZDgwZWJiMThkMjBhZjMwOTkiLCJleHAiOjE1NTI3MTY3NzksImlhdCI6MTU1MTg1Mjc3OX0.x0OzgErZjw_Eck9AbmSUX0ZHPiUxiz9F_xaecpLBz1M
     */

    private int isBindPhone;
    private String token;
    private ArrayList<TagBeanDto> subscribeLableList;

    public int getIsBindPhone() {
        return isBindPhone;
    }

    public void setIsBindPhone(int isBindPhone) {
        this.isBindPhone = isBindPhone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<TagBeanDto> getSubscribeLableList() {
        return subscribeLableList;
    }

    public void setSubscribeLableList(ArrayList<TagBeanDto> subscribeLableList) {
        this.subscribeLableList = subscribeLableList;
    }
}
