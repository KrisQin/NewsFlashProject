package com.blockadm.adm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.utils.ScreenUtils;


/**
 * Created by Administrator on 2016/9/19.
 */
public class PayTypeDialog extends Dialog {


  //  private final View tvSave;
    private Context context;
    private final TextView tvMan;
    private final TextView tvWoMan;
    private int  type=-1;

    public PayTypeDialog(final Context context) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        setContentView(R.layout.dialog_paytype_select);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = ScreenUtils.getScreenWidth(context);
        Window dialogWindow = getWindow();
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.BOTTOM);
        initView();

        tvMan = findViewById(R.id.tv_man);

        tvWoMan = findViewById(R.id.tv_woman);

        TextView tvCancel =  findViewById(R.id.tv_cancel);
        tvWoMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvWoMan.setTextAppearance(context, R.style.TabLayoutTextStyle2);
                tvMan.setTextAppearance(context, R.style.TabLayoutTextStyle3);
                sexSaveListner.onSaveClick("支付宝支付",0);
            }
        });

        tvMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvWoMan.setTextAppearance(context, R.style.TabLayoutTextStyle3);
                tvMan.setTextAppearance(context, R.style.TabLayoutTextStyle2);
                sexSaveListner.onSaveClick("微信支付",1);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }


    private void initView() {
    }

    public interface  SexSaveListner {
        void onSaveClick(String textType, int type);
    };

    private SexSaveListner sexSaveListner;

    public void setSexSaveListner(SexSaveListner sexSaveListner) {
        this.sexSaveListner = sexSaveListner;
    }
}
