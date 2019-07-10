package com.blockadm.adm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/9/19.
 */
public class WwtchatCodeDialog extends Dialog {


    @BindView(R.id.tv_upload)
    TextView tvUpload;
    private Context context;


    public WwtchatCodeDialog(final Context context) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        setContentView(R.layout.dialog_add_wetchat_code);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(context)*0.8f);
        Window dialogWindow = getWindow();
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(false);


    }

    public void setProgress(int progress){
        tvUpload.setText("上传中 ("+progress+")");

    }




}
