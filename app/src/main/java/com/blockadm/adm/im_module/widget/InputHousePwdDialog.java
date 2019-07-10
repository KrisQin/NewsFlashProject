package com.blockadm.adm.im_module.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.T;

/**
 * Created by Kris on 2019/5/9
 *
 * @Describe TODO {  }
 */
public class InputHousePwdDialog extends Dialog {

    private PutPwdCallback mPutPwdCallback;
    private TextView mTv_pwd_error;
    private EditText mEt_pwd;

    public InputHousePwdDialog(@NonNull Context context,PutPwdCallback callback) {
        this(context,0);
        mPutPwdCallback = callback;
    }

    public InputHousePwdDialog(@NonNull final Context context, int themeResId) {
        super(context, R.style.dialog_base);
        setContentView(R.layout.dialog_input_pwd_layout);

        LinearLayout layout_root = findViewById(R.id.layout_root);
        mEt_pwd = findViewById(R.id.et_pwd);
        TextView tv_submit = findViewById(R.id.tv_submit);
        mTv_pwd_error = findViewById(R.id.tv_pwd_error);

        mEt_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEt_pwd.getText().toString().trim().length() > 0) {
                    mTv_pwd_error.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


       int width = (int) (ScreenUtils.getScreenWidth(context) * 0.8);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        layout_root.setLayoutParams(params);

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPutPwdCallback.callback(mEt_pwd.getText().toString().trim());

            }
        });
    }

    public void showErrorPwd() {
        mEt_pwd.setText("");
        mTv_pwd_error.setVisibility(View.VISIBLE);
    }

    public interface PutPwdCallback {
        void callback(String pwd);
    }
}
