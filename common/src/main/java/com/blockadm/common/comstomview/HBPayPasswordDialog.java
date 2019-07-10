package com.blockadm.common.comstomview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.common.R;
import com.blockadm.common.utils.StringUtils;

/**
 * Created by Administrator on 2018/3/19.
 */

public class HBPayPasswordDialog extends Dialog implements View.OnClickListener {
    Context context;
    private RelativeLayout layout_close;
    private PayPasswordView payPassword;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private TextView tv;
    private TextView tvDel;
    private TextView mTv_amount;
    private TextView mTv_unit;
    private TextView mTv_desc;

    public HBPayPasswordDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_hb_pay_pwd, null);
        setContentView(view);
        initView();

        Window window = getWindow();
        WindowManager.LayoutParams mParams = window.getAttributes();
        mParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(mParams);
        setCanceledOnTouchOutside(true);

        mTv_amount.setText(mMoney);

        if (StringUtils.isEmpty(mUnit)) {
            mTv_unit.setVisibility(View.GONE);
        }else {
            mTv_unit.setVisibility(View.VISIBLE);
            mTv_unit.setText(mUnit);
        }

        if (mTextSize != -1) {
            mTv_amount.setTextSize(mTextSize);
        }

        if (StringUtils.isNotEmpty(mDesc)) {
            mTv_desc.setText(mDesc);
            mTv_desc.setVisibility(View.VISIBLE);
        }else {
            mTv_desc.setVisibility(View.GONE);
        }

        layout_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payPassword.delLastPassword();
            }
        });
        payPassword.setPayPasswordEndListener(new PayPasswordView.PayEndListener() {
            @Override
            public void doEnd(String password) {
                if (dialogClick!=null){
                    dialogClick.doConfirm(password);
                }
            }
        });

        tv.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);

    }

    private void initView() {
        layout_close = (RelativeLayout) findViewById(R.id.layout_close);
        payPassword = (PayPasswordView) findViewById(R.id.pay_password);
        mTv_amount = (TextView) findViewById(R.id.tv_amount);
        mTv_desc = (TextView) findViewById(R.id.tv_desc);
        mTv_unit = (TextView) findViewById(R.id.tv_unit);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv8);
        tv9 = (TextView) findViewById(R.id.tv9);
        tv = (TextView) findViewById(R.id.tv);
        tvDel = (TextView) findViewById(R.id.tv_del);
    }

    DialogClick dialogClick;
    public void setDialogClick(DialogClick dialogClick){
        this.dialogClick=dialogClick;
    }

    private String mDesc;
    public void setDesc(String desc) {
        mDesc = desc;
    }

    private String mMoney;
    private String mUnit;
    private int mTextSize = -1;
    public void setMoney(String money,String unit) {
        mMoney = money;
        mUnit = unit;
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv) {
            payPassword.addPassword("0");

        } else if (i == R.id.tv1) {
            payPassword.addPassword("1");

        } else if (i == R.id.tv2) {
            payPassword.addPassword("2");

        } else if (i == R.id.tv3) {
            payPassword.addPassword("3");

        } else if (i == R.id.tv4) {
            payPassword.addPassword("4");

        } else if (i == R.id.tv5) {
            payPassword.addPassword("5");

        } else if (i == R.id.tv6) {
            payPassword.addPassword("6");

        } else if (i == R.id.tv7) {
            payPassword.addPassword("7");

        } else if (i == R.id.tv8) {
            payPassword.addPassword("8");

        } else if (i == R.id.tv9) {
            payPassword.addPassword("9");

        }
    }

    public interface DialogClick{
        void doConfirm(String password);
    }

}
