package com.blockadm.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.common.R;
import com.blockadm.common.utils.StringUtils;

/**
 * Created by Kris on 2019/5/5
 *
 * @Describe TODO {  }
 */
public class ComLoadingDialog extends Dialog {


    public ComLoadingDialog(@NonNull Context context) {
        super(context);
    }

    public ComLoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static class Builder{

        Context context;
        boolean isShowMessage;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder isShowMessage(boolean isShowMessage) {
            this.isShowMessage = isShowMessage;
            return this;
        }


        public ComLoadingDialog onCreate() {

            final ComLoadingDialog dialog = new ComLoadingDialog(context,R.style.dialog_base);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.layout_loading_dialog,null);

            TextView tv_text_desc = layout.findViewById(R.id.tv_text_desc);

            if (isShowMessage) {
                tv_text_desc.setVisibility(View.VISIBLE);
            }else {
                tv_text_desc.setVisibility(View.GONE);
            }

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            layout.setLayoutParams(lp);

            dialog.setContentView(layout);

            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);

            return dialog;


        }

    }
}

