package com.blockadm.common.im.call;

import com.tencent.imsdk.TIMMessage;

/**
 * Created by Kris on 2019/5/22
 *
 * @Describe TODO {  }
 */
public interface SendTextMessageCallback {

    void onError(int code, String desc);

    void onSuccess(String content);

}
