/**
 * @dec
 */
package com.blockadm.common.wxpay;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;


import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *@Title WXPayUtils
 *@dec 微信支付工具
 *@Author YANGQIYUN
 *@date 2015年10月22日
 */
public class WXPayUtils {
/*	private Context context;
	private IWXAPI msgApi;
	private PayReq req;
	private ProgressDialog dialog;
	private Map<String,String> resultunifiedorder;
	private StringBuffer sb;
	private String outTradeNo;
	private String subject;
	private String body;
	private String price;
	private String wxpayAddress;
	private String redUrl;
	private String openUrl;
	private String wxpayTime;
	public WXPayUtils(Context context, String outTradeNo, String subject, String body, String price, String wxpayAddress, String wxpayTime){
		this.context = context;
		sb=new StringBuffer();
		this.outTradeNo = outTradeNo;
		this.subject = subject;
		this.body = body;
//		this.price = price;
		this.wxpayAddress=wxpayAddress;
		this.wxpayTime=wxpayTime;
		BigDecimal value = new BigDecimal(price);
		value=value.multiply(new BigDecimal(100));
		this.price = value.intValue()+"";
	}
	public ProgressDialog getProgressDialog(){
		return dialog;
	}
	*//**
	 * @dec 生成订单
	 * @param context
	 * @param params
	 * @return
	 *//*
	public String initPayOrder(Context context, Map<String,Object> params){

		return "";
	}
	*//**
	 * @dec 微信支付
	 * @param context
	 *//*
	public void payByWXchat(Context context){
		req = new PayReq();
		msgApi = WXAPIFactory.createWXAPI(context, null);
		msgApi.registerApp(Constants.WX_APP_ID);
		GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
		getPrepayId.execute();
	}
	private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String,String>> {

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(context, "提示", "正在玩命加载...");
		}

		@Override
		protected void onPostExecute(Map<String,String> result) {
			if((result.get("result_code") + "").equals("SUCCESS")){
				//成功签名
			}else{
				if(onResultListener != null){
					onResultListener.onResult();
				}
			}
			sb.append("prepay_id\n"+result.get("prepay_id")+"\n\n");
			resultunifiedorder=result;
			//签名
			genPayReq();
			//请求
			sendPayReq();
			if(dialog != null && dialog.isShowing()){
				dialog.dismiss();
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Map<String,String> doInBackground(Void... params) {

			String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
			String entity = genProductArgs();

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Map<String,String> xml=decodeXml(content);
			return xml;
		}
	}
	@SuppressLint("UseValueOf")
	private String genProductArgs() {
		StringBuffer xml = new StringBuffer();
		try {
			String nonceStr = genNonceStr();

			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("appid", Constants.WX_APP_ID));
			packageParams.add(new BasicNameValuePair("body", body));
			packageParams.add(new BasicNameValuePair("input_charset", "UTF-8"));
			packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url", wxpayAddress));
			packageParams.add(new BasicNameValuePair("out_trade_no","2019030510151551752141940247896"));
			//packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));
			//packageParams.add(new BasicNameValuePair("time_expire",wxpayTime));
			packageParams.add(new BasicNameValuePair("total_fee",price));
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));


			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));


			String xmlstring = toXml(packageParams);

			return new String(xmlstring.toString().getBytes(), "ISO8859-1");
//			return xmlstring;
		} catch (Exception e) {
			return null;
		}
	}
	public Map<String,String> decodeXml(String content) {
		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName=parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT:

						break;
					case XmlPullParser.START_TAG:

						if("xml".equals(nodeName)==false){
							//实例化student对象
							xml.put(nodeName,parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion",e.toString());
		}
		return null;
	}
	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		this.sb.append("sign str\n"+sb.toString()+"\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion--genAppSign",appSign);
		return appSign;
	}
	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<"+params.get(i).getName()+">");


			sb.append(params.get(i).getValue());
			sb.append("</"+params.get(i).getName()+">");
		}
		sb.append("</xml>");
		Log.e("orion--toXml",sb.toString());
		return sb.toString();
	}
	*//**
	 生成签名
	 *//*
	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion",packageSign);
		return packageSign;
	}
	private void genPayReq() {

		req.appId = Constants.WX_APP_ID;
		req.partnerId = Constants.MCH_ID;
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());//时间过期


		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);
		sb.append("sign\n"+req.sign+"\n\n");

	}
	private void sendPayReq() {
    	//msgApi.registerApp(Constants.WX_APP_ID);
		msgApi.sendReq(req);
	}
	*//**
	 *
	 * 启动进程监听笑傲过处理
	 * *//*
	public OnResultListener onResultListener;
	public interface OnResultListener{
		public void onResult();
	}
	public void setOnResultListener(OnResultListener onResultListener){
		this.onResultListener = onResultListener;

	}*/
}
