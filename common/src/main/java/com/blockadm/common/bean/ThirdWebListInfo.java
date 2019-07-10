package com.blockadm.common.bean;


import java.util.List;

/**
 * Created by Kris on 2019/6/28
 *
 * @Describe TODO {  }
 */
public class ThirdWebListInfo {


    /**
     * isShowThirdWebButton : 1
     * sysThirdWebList : [{"webUrl":"https://ai.taobao.com","webName":"爱淘宝",
     * "webLogo":"https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2017-07-10
     * /16c593b3396fd2ed58ce6851ff76b2d0.png"},{"webUrl":"https://ju.taobao.com/","webName":"天猫",
     * "webLogo":"http://public.blockadm
     * .pro//image/0/0/20190617/f22269ce-05b6-48ce-a577-88f21bf8bcd3"},{"webUrl":"https://www
     * .iqiyi.com/","webName":"爱奇艺","webLogo":"http://public.blockadm
     * .pro//image/0/0/20190627/10c75768-6501-4e8c-8844-126c6aa9b1b1"},{"webUrl":"https://m.jinse
     * .com/market","webName":"财经行情","webLogo":"http://public.blockadm
     * .pro//image/0/0/20190627/4c34c1b9-494d-471c-9dc6-2712c97121e2"},{"webUrl":"https://open
     * .ximalaya.com/site/index/45/d2794edaf9b4055c72acd8d2fcd12aa5","webName":"喜马拉雅",
     * "webLogo":"http://public.blockadm
     * .pro//image/0/0/20190627/7cc12ddf-32f9-4eb3-86a7-5e6b985c581b"},{"webUrl":"https://www
     * .huobi.br.com/zh-cn/markets/","webName":"火币网","webLogo":"http://public.blockadm
     * .pro//image/0/0/20190627/e9ccf456-8b81-4ebc-889a-bc5826b7e51f"},{"webUrl":"https://www
     * .btc123.com/currency/","webName":"BTC123","webLogo":"http://public.blockadm
     * .pro//image/0/0/20190627/9570355f-dd9e-4bbe-a835-411ed27438cc"},{"webUrl":"https://m
     * .feixiaohao.com/","webName":"非小号","webLogo":"http://public.blockadm
     * .pro//image/0/0/20190627/72e3b4b6-2c8f-4946-84f8-511d33520890"}]
     */

    private int isShowThirdWebButton;
    private List<SysThirdWebListInfo> sysThirdWebList;

    public int getIsShowThirdWebButton() {
        return isShowThirdWebButton;
    }

    public void setIsShowThirdWebButton(int isShowThirdWebButton) {
        this.isShowThirdWebButton = isShowThirdWebButton;
    }

    public List<SysThirdWebListInfo> getSysThirdWebList() {
        return sysThirdWebList;
    }

    public void setSysThirdWebList(List<SysThirdWebListInfo> sysThirdWebList) {
        this.sysThirdWebList = sysThirdWebList;
    }

}
