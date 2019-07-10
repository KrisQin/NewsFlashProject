package com.blockadm.adm.im_module.call;

import com.blockadm.common.im.entity.MessageInfo;

/**
 * Created by Kris on 2019/7/1
 *
 * @Describe TODO {  }
 */
public interface LiveMsgCallback {

    void clickHBCallback(MessageInfo messageInfo);

    void clickHeadImageCallback(MessageInfo messageInfo);
}
