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
import com.blockadm.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/19.
 */
public class IdListDialog extends Dialog {


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


    public IdListDialog(final Context context) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        setContentView(R.layout.dialog_id_list);
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

    @OnClick({R.id.tv_idcard, R.id.tv_gangao, R.id.tv_taiwan, R.id.tv_huzhao, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_idcard:
                sexSaveListner.onSaveClick(tvIdcard.getText().toString(),0);
                break;
            case R.id.tv_gangao:
                ToastUtils.showToast("暂不支持该认证");
                break;
            case R.id.tv_taiwan:
                ToastUtils.showToast("暂不支持该认证");
                break;
            case R.id.tv_huzhao:
                ToastUtils.showToast("暂不支持该认证");
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    public interface TypeSaveListner {
        void onSaveClick(String textType,int type);
    };

    private TypeSaveListner sexSaveListner;

    public void setSexSaveListner(TypeSaveListner sexSaveListner) {
        this.sexSaveListner = sexSaveListner;
    }
}
