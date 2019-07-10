package com.blockadm.common.call;

import com.blockadm.common.bean.UserInfoDto;

/**
 * Created by Kris on 2019/6/21
 *
 * @Describe TODO {  }
 */
public interface GetUserCallBack {

    void backUserInfo(UserInfoDto userInfo);

    void error(int code,String msg);
}
