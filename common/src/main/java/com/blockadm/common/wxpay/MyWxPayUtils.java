package com.blockadm.common.wxpay;

import android.content.Context;

import com.blockadm.common.bean.PayDTO;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by hp on 2019/3/5.
 */

public class MyWxPayUtils {
    public static void sendReq(PayDTO.WxPayParamBean wxPayParam, Context context) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        msgApi.registerApp(wxPayParam.getAppid());
        PayReq request = new PayReq();
        request.appId = wxPayParam.getAppid();
        request.partnerId = wxPayParam.getPartnerid();
        request.prepayId= wxPayParam.getPrepayid();
        request.packageValue = wxPayParam.getPackageValue();
        request.nonceStr= wxPayParam.getNoncestr();
        request.timeStamp=wxPayParam.getTimestamp();
        request.sign= wxPayParam.getSign();
        msgApi.sendReq(request);
    }
}
