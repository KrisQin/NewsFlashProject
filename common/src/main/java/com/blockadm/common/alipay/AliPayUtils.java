/**
 * @dec
 */
package com.blockadm.common.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * @Title AliPayUtils
 * @dec 支付宝支付信息封装工具类
 */
public class AliPayUtils {

	private Activity act;

	public AliPayUtils(Activity act) {
		this.act = act;
	}

	/**
	 * @dec 支付宝支付
	 * @param payInfo
	 */
	public void payByAli(final String payInfo, OnResponListener onResponListener) {
		this.onResponListener = onResponListener;
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(act);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo,true);
				Log.i("msp", result.toString());
				Message msg = new Message();
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			PayResult payResult = new PayResult((String) msg.obj);
			/**
			 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
			 */
			String resultInfo = payResult.getResult();// 同步返回需要验证的信息
			String resultStatus = payResult.getResultStatus();
			// 判断resultStatus 为9000则代表支付成功
			if (TextUtils.equals(resultStatus, "9000")) {
				// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
				Toast.makeText(act, "支付成功", Toast.LENGTH_SHORT).show();
				if (onResponListener != null) {
					onResponListener.onSucceed();
				}
			} else {
				// 判断resultStatus 为非“9000”则代表可能支付失败
				// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
				onResponListener.onRespon(resultInfo);
				if (TextUtils.equals(resultStatus, "8000")) {
					Toast.makeText(act, "支付结果确认中", Toast.LENGTH_SHORT).show();

				} else if (TextUtils.equals(resultStatus, "4000")) {
					Toast.makeText(act, "未安装支付宝，请选择其它支付方式", Toast.LENGTH_SHORT)
							.show();
				} else if (TextUtils.equals(resultStatus, "6001")) {
					// Toast.makeText(R1_ResultByOrderActivity.this, "支付失败",
					// Toast.LENGTH_SHORT).show();
				} else if (TextUtils.equals(resultStatus, "6002")) {
					Toast.makeText(act, "网络连接出错", Toast.LENGTH_SHORT).show();
				} else {
					// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
					Toast.makeText(act, "支付失败", Toast.LENGTH_SHORT).show();
				}
				// 4000订单支付失败 6001 用户中途取消 6002 网络连接出错
			}
		};
	};
	/**
	 * @dec 响应监听接口
	 *
	 * */
	public OnResponListener onResponListener;

	public interface OnResponListener {
		public void onRespon(String respon);

		public void onSucceed();
	}
}
