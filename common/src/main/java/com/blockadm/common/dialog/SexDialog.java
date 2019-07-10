package com.blockadm.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blockadm.common.R;
import com.blockadm.common.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/19.
 */
public class SexDialog extends Dialog {


  //  private final View tvSave;
    private Context context;
    private final TextView tvMan;
    private final TextView tvWoMan;
    private int  type=-1;

    public SexDialog(final Context context, int sex) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        setContentView(R.layout.dialog_sex_select);
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
       // tvSave = findViewById(R.id.tv_save);
        if ( sex==1){
            tvMan.setTextAppearance(context, R.style.TabLayoutTextStyle2);
        }else{
            tvWoMan.setTextAppearance(context, R.style.TabLayoutTextStyle2);
        }
        TextView tvCancel =  findViewById(R.id.tv_cancel);
        tvWoMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvWoMan.setTextAppearance(context, R.style.TabLayoutTextStyle2);
                tvMan.setTextAppearance(context, R.style.TabLayoutTextStyle3);
                sexSaveListner.onSaveClick("女",0);
            }
        });

        tvMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvWoMan.setTextAppearance(context, R.style.TabLayoutTextStyle3);
                tvMan.setTextAppearance(context, R.style.TabLayoutTextStyle2);
                sexSaveListner.onSaveClick("男",1);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

      /*  tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sexSaveListner!=null){
                  switch (type){
                      case 1:
                          sexSaveListner.onSaveClick("男",1);
                          break;
                      case 2:
                          sexSaveListner.onSaveClick("女",0);
                          break;

                  }

                }
            }
        });*/
    }


    private void initView() {
    }

    public interface  SexSaveListner {
        void onSaveClick(String  textType,int type);
    };

    private SexSaveListner sexSaveListner;

    public void setSexSaveListner(SexSaveListner sexSaveListner) {
        this.sexSaveListner = sexSaveListner;
    }
}
