package com.blockadm.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.common.R;
import com.blockadm.common.utils.ScreenUtils;

/**
 * Created by Kris on 2019/5/5
 *
 * @Describe TODO { 发布公告 }
 */
public class AnnouncementDialog extends Dialog {


    public AnnouncementDialog(@NonNull Context context) {
        super(context);
    }

    public AnnouncementDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static class Builder{

        Context context;
        Activity mActivity;
        EditCallback mEditCallback;


        public Builder(Activity context) {
            this.context = context;
            mActivity = context;
        }

        public Builder setOnSubmitCallback(EditCallback editCallback) {
            mEditCallback = editCallback;
            return this;
        }

        public AnnouncementDialog onCreate() {

            final AnnouncementDialog dialog = new AnnouncementDialog(context,R.style.dialog_base);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.dialog_announcement_layout,null);




            dialog.setContentView(layout);

            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = ScreenUtils.getScreenWidth(context);
            params.gravity = Gravity.BOTTOM;
            dialog.getWindow().setAttributes(params);

            final EditText et_content = layout.findViewById(R.id.et_content);
            final TextView tv_count = layout.findViewById(R.id.tv_count);
            Button bt_submit = layout.findViewById(R.id.bt_submit);

            et_content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int count = editable.toString().length();
                    String countStr = count + "/30";
                    if (count > 30) {
                        SpannableString spannableString = new SpannableString(countStr);
                        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
                        spannableString.setSpan(foregroundColorSpan, 0, String.valueOf(count).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        tv_count.setText(spannableString);
                    }else {
                        tv_count.setText(countStr);
                    }
                }
            });

            bt_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mEditCallback != null) {
                        mEditCallback.callback(et_content.getText().toString().trim());
                    }
                }
            });

            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);

            return dialog;


        }

    }


    public interface EditCallback {
        void callback(String text);
    }
}

