package com.blockadm.adm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.DimenUtils;
import com.blockadm.common.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/2/27.
 */

public class MyComstomDialogView extends Dialog {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_child_msg)
    TextView tvChildMsg;

    @BindView(R.id.tv_child_msg2)
    TextView tvChildMsg2;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    private Context context;

    public MyComstomDialogView(Context context) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        setContentView(R.layout.dialog_common_view);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(context)*0.8f);
        Window dialogWindow = getWindow();
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);


    }

    public  MyComstomDialogView setTvTitle(String title,int isHide){
        tvTitle.setText(title);
        tvTitle.setVisibility(isHide);
        return this;
    }

    public  MyComstomDialogView setThisCanceledOnTouchOutside(boolean isOutside){
        setCanceledOnTouchOutside(isOutside);
        return this;
    }

    public  MyComstomDialogView setChildMsg(String msg,int isHide){
        tvChildMsg.setText(msg);
        tvChildMsg.setVisibility(isHide);
        return this;
    }

    public  MyComstomDialogView setChildMsg2(String msg,int isHide){
        tvChildMsg2.setText(msg);
        tvChildMsg2.setVisibility(isHide);
        return this;
    }
    public  MyComstomDialogView setConcelMsg(String msg,int isHide){
        tvCancel.setText(msg);
        tvCancel.setVisibility(isHide);
        line.setVisibility(isHide);
        return this;
    }
    public  MyComstomDialogView setRootBg(int re){
        llRoot.setBackgroundResource(re);
        return this;
    }
    public  MyComstomDialogView setConfirmMsg(String msg,int isHide){
        tvConfirm.setText(msg);
        tvConfirm.setVisibility(isHide);
        return this;
    }

    public  MyComstomDialogView setConfirmMsg(String msg,int isHide,int color){
        tvConfirm.setText(msg);
        tvConfirm.setVisibility(isHide);
        tvConfirm.setTextColor(context.getResources().getColor(color));
        return this;
    }

    public  MyComstomDialogView setConfirmSize(int size){
        tvConfirm.setTextSize(DimenUtils.dip2px(ContextUtils.getBaseApplication(),size));
        return this;
    }




    public  MyComstomDialogView setCancelLisenner(View.OnClickListener  onClickListener){
        tvCancel.setOnClickListener(onClickListener);
        return this;
    }
    public  MyComstomDialogView setConfirmLisenner(View.OnClickListener  onClickListener){
        tvConfirm.setOnClickListener(onClickListener);
        return this;
    }

    public MyComstomDialogView setConcelButtonEable(boolean concelButtonEable) {
        tvCancel.setEnabled(concelButtonEable);
        return this;
    }
}
