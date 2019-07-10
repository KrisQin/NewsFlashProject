package com.blockadm.common.bean;

/**
 * Created by hp on 2019/3/4.
 */

public class PayDTO {

    /**
     * outTradeNo : 2019030511251551756329428723527
     * wxPayParam : {"appid":"wxc9b4ef7a71143f2a","noncestr":"a6aibyrvhjycynxd","packageValue":"Sign=WXPay","partnerid":"1526190351","prepayid":"wx05112529653235c974011e033855339650","sign":"22796092B3C4336A605F7882B09B2384","timestamp":"1551756329"}
     * zfbPayParam :
     */

    private String outTradeNo;
    private WxPayParamBean wxPayParam;
    private String zfbPayParam;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public WxPayParamBean getWxPayParam() {
        return wxPayParam;
    }

    public void setWxPayParam(WxPayParamBean wxPayParam) {
        this.wxPayParam = wxPayParam;
    }

    public String getZfbPayParam() {
        return zfbPayParam;
    }

    public void setZfbPayParam(String zfbPayParam) {
        this.zfbPayParam = zfbPayParam;
    }

    public static class WxPayParamBean {
        /**
         * appid : wxc9b4ef7a71143f2a
         * noncestr : a6aibyrvhjycynxd
         * packageValue : Sign=WXPay
         * partnerid : 1526190351
         * prepayid : wx05112529653235c974011e033855339650
         * sign : 22796092B3C4336A605F7882B09B2384
         * timestamp : 1551756329
         */

        private String appid;
        private String noncestr;
        private String packageValue;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageValue() {
            return packageValue;
        }

        public void setPackageValue(String packageValue) {
            this.packageValue = packageValue;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
