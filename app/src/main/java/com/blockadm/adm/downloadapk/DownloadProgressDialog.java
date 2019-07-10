package com.blockadm.adm.downloadapk;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.call.ComCallback;

/**
 * Created by Kris on 2019/6/14
 *
 * @Describe TODO {  }
 */
public class DownloadProgressDialog extends Dialog {


    private Context mContext;
    private final ProgressBar mProgressBar;
    private final TextView mTv_per;
    private final TextView mTv_m;
    private final TextView mTv_cancel;
    private final RelativeLayout mLayout_cancel;

    public DownloadProgressDialog(Context context) {
        super(context, R.style.dialog_base);
        this.mContext = context;
        setContentView(R.layout.dialog_progress_down_load);

        setCanceledOnTouchOutside(true);
        setCancelable(true);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        params.width = width / 5 * 4;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        Window dialogWindow = getWindow();
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug

        mTv_cancel = findViewById(R.id.tv_cancel);
        mLayout_cancel = findViewById(R.id.layout_cancel);
        mTv_per = findViewById(R.id.tv_per);
        mTv_m = findViewById(R.id.tv_m);
        mProgressBar = findViewById(R.id.progressBar);


        mLayout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mCancelListener != null) {
                    mCancelListener.callback();
                }
            }
        });

    }

    private CancelListener mCancelListener;
    public void setOnCancelListener(CancelListener listener) {
        mCancelListener = listener;
    }

    public interface CancelListener {
        void callback();
    }

    public void setDownComplete(boolean isDownComplete, final ComCallback callback) {
        if (isDownComplete) {
            if (mTv_cancel != null)
                mTv_cancel.setText("安装");
            if (mLayout_cancel != null)
                mLayout_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null) {
                            callback.callback();
                        }
                    }
                });
        }
    }

    public void setProgressBar(int progressBar, int max) {
        if (mProgressBar != null) {
            mProgressBar.setProgress(progressBar);
            mProgressBar.setMax(max);
        }
    }

    public void setProgressData(String per, String mm) {
        if (mTv_per != null)
            mTv_per.setText(per);
        if (mTv_m != null)
            mTv_m.setText(mm);
    }

}
