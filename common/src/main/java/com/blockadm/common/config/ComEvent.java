package com.blockadm.common.config;


import android.content.Context;
import android.content.SharedPreferences;

import com.blockadm.common.utils.SharedpfTools;

import org.litepal.util.SharedUtil;

/**
 * Created by Kris on 2019/5/9
 *
 * @Describe TODO {  }
 */
public class ComEvent {


    public static final String ReceiveHBSuccess = "ReceiveHBSuccess"; //抢红包成功

    private String action;

    public ComEvent(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
