package com.blockadm.adm.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.UpdateMyActitivity;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.SystemUtils;
import com.blockadm.common.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * Created by Administrator on 2016/9/19.
 */
public class BeishuDialog extends Dialog {


    @BindView(R.id.tv_idcard)
    TextView tvIdcard;
    @BindView(R.id.tv_gangao)
    TextView tvGangao;
    @BindView(R.id.tv_taiwan)
    TextView tvTaiwan;
    @BindView(R.id.tv_huzhao)
    TextView tvHuzhao;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    private Context context;


    public BeishuDialog(final Context context) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        setContentView(R.layout.dialog_beishu_list);
        setCanceledOnTouchOutside(true);
        ButterKnife.bind(this);
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = ScreenUtils.getScreenWidth(context);
        Window dialogWindow = getWindow();
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.BOTTOM);
        initView();


    }


    private void initView() {
    }

    @OnClick({R.id.tv_idcard, R.id.tv_gangao, R.id.tv_taiwan, R.id.tv_huzhao, R.id.tv_cancel,R.id.tv_two})
    public void onViewClicked(View view) {

      String deviceBrand =  SystemUtils.getDeviceBrand();
        String systemVersion =  SystemUtils.getSystemVersion();
        String manufacturer = SystemUtils.getManufacturer();
        if (("HONOR".equalsIgnoreCase(deviceBrand)||"HUAWEI".equalsIgnoreCase(manufacturer))&&Integer.parseInt(systemVersion)>=9){
            Toast.makeText(context,"当前手机系统不支持该功能",Toast.LENGTH_SHORT).show();
            dismiss();
            return;
        }

        switch (view.getId()) {
            case R.id.tv_idcard:
                sexSaveListner.onSaveClick(tvIdcard.getText().toString(),1);
                break;
            case R.id.tv_gangao:
                sexSaveListner.onSaveClick(tvIdcard.getText().toString(),2);
                break;
            case R.id.tv_taiwan:
                sexSaveListner.onSaveClick(tvIdcard.getText().toString(),3);
                break;
            case R.id.tv_huzhao:
                sexSaveListner.onSaveClick(tvIdcard.getText().toString(),4);
                break;
            case R.id.tv_cancel:
                dismiss();
            case R.id.tv_two:
                sexSaveListner.onSaveClick(tvIdcard.getText().toString(),5);
                break;
        }
    }

    public interface TypeSaveListner {
        void onSaveClick(String textType, int type);
    };

    private TypeSaveListner sexSaveListner;

    public void setSexSaveListner(TypeSaveListner sexSaveListner) {
        this.sexSaveListner = sexSaveListner;
    }
}
