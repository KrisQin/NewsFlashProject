package com.blockadm.adm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/1/31.
 */

public class PayPasswordComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_passeord2)
    EditText etPasseord2;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private String code;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_pay_password);
        ButterKnife.bind(this);
        code = getIntent().getStringExtra("code");

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        type = (int) SharedpfTools.getInstance(PayPasswordComActivity.this).get("telsetting",-1);
        if (type ==4){
            tilte.setTitle("登录密码");
            tv.setText("密码支持6-16位英文或数字");

        }else{
            etPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
            etPasseord2.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        String  password1 = etPassword.getText().toString().trim();
        String  password2 = etPasseord2.getText().toString().trim();
        if (!password1.equals(password2)){
            ToastUtils.showToast("两次密码输入不一致");return;
        }
        if (type==4){
            CommonModel.setPassword(1,code, password1, new MyObserver<String>() {
                @Override
                public void onSuccess(BaseResponse<String> t) {
                    ToastUtils.showToast(t.getMsg());
                    if (t.getCode()==0){
                        Intent intent = new Intent(PayPasswordComActivity.this, SettingComActivity.class);
                        startActivity(intent);
                    }

                }

            });
        }else{
            CommonModel.setPassword(0,code, password1, new MyObserver<String>() {
                @Override
                public void onSuccess(BaseResponse<String> t) {
                    ToastUtils.showToast(t.getMsg());
                    if (t.getCode()==0){
                        Intent intent = new Intent(PayPasswordComActivity.this, SettingComActivity.class);
                        startActivity(intent);
                    }

                }

            });
        }


    }
}
