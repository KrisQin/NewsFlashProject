package com.blockadm.common.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.blockadm.common.R;


public class LoadingProgressDialog extends ProgressDialog {
	
	private Context mContext;
	// ProgressDialog.STYLE_SPINNER 园形

	public LoadingProgressDialog(Context context, String message,
                                 int progressStyle) {
		super(context);
		super.setMessage(message);
		super.setCancelable(true);
		super.setIndeterminate(false);//不明确进度条
		super.setProgressStyle(progressStyle);
		
	}
	public LoadingProgressDialog(Context context, String message,
                                 int progressStyle, boolean cancelable) {
		super(context);
		super.setMessage(message);
		super.setCancelable(cancelable);
		super.setIndeterminate(false);
		super.setProgressStyle(progressStyle);
		
	}


	public LoadingProgressDialog(Context context, int resId,
                                 int progressStyle, boolean cancelable) {
		super(context);
		super.setMessage(context.getString(resId));
		super.setIndeterminate(false);
		super.setProgressStyle(progressStyle);
		super.setCancelable(cancelable);
	}
	
	public LoadingProgressDialog(Context context) {
		super(context);
		this.mContext = context;
		super.setIndeterminate(false);
		super.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		super.setMessage(mContext.getString(R.string.dialog_data_loading));
		super.setCancelable(true);
	}
	
	public void setMessage(int resID) {
		super.setMessage(mContext.getString(resID));
	}
	
	public void setMessage(String msg) {
		super.setMessage(msg);
	}
	
}
