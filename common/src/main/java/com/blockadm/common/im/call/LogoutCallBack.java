package com.blockadm.common.im.call;

/**
 * Created by Kris on 2019/5/8
 *
 * @Describe TODO {  }
 */
public interface LogoutCallBack {

    void onError(int code, String desc);

    void onSuccess();
}
