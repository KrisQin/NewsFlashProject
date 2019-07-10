package com.blockadm.common.im.call;

import com.tencent.imsdk.TIMMessage;

import java.util.List;

/**
 * Created by Kris on 2019/5/22
 *
 * @Describe TODO {  }
 */
public interface HistoryMessageCallback {

    void onError(int code, String desc);

    void onSuccess(List<TIMMessage> msgList);
}
