package com.blockadm.adm.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.blockadm.adm.activity.PaySuccesComActivity;
import com.blockadm.adm.event.FinishEvent;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.common.wxpay.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WXPayEntryActivity";

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		Log.d(TAG, "WXPayEntryActivity = " + req.openId);
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "WXPayEntryActivity = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX){
			if (resp.errCode == 0){ //支付成功
				 EventBus.getDefault().post(new UserDataEvent());
				 EventBus.getDefault().post(new FinishEvent());
                 Intent intent = new Intent(this, PaySuccesComActivity.class);
                 startActivity(intent);

			}else {
				Toast.makeText(this,"支付失败，请重试",Toast.LENGTH_SHORT).show();
			}
			finish();
		}
	}
}
