package com.blockadm.adm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blockadm.adm.R;
import com.blockadm.common.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 直播界面  底部发送红包/图片/设置/联系平台 的dialog
 */
public class LiveSendMsgDialog extends Dialog {


    @BindView(R.id.layout_hb)
    LinearLayout layoutHb;
    @BindView(R.id.layout_pic)
    LinearLayout layoutPic;
    @BindView(R.id.layout_setting)
    LinearLayout layoutSetting;
    @BindView(R.id.layout_pt)
    LinearLayout layoutPt;
    @BindView(R.id.layout_cancel)
    RelativeLayout layoutCancel;
    private Context context;
    private ClickBtListener clickBtListener;

    public LiveSendMsgDialog(final Context context,ClickBtListener clickBtListener) {

        super(context, R.style.MyAlertDialog);
        this.context = context;
        this.clickBtListener = clickBtListener;
        setContentView(R.layout.dialog_live_send_msg);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = ScreenUtils.getScreenWidth(context);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Window dialogWindow = getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.BOTTOM);
    }


    @OnClick({R.id.layout_hb, R.id.layout_pic, R.id.layout_setting, R.id.layout_pt,
            R.id.layout_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_hb:
                if (clickBtListener != null) {
                    clickBtListener.sendHB();
                    LiveSendMsgDialog.this.dismiss();
                }
                break;
            case R.id.layout_pic:
                if (clickBtListener != null) {
                    clickBtListener.sendPic();
                    LiveSendMsgDialog.this.dismiss();
                }
                break;
            case R.id.layout_setting:
                if (clickBtListener != null) {
                    clickBtListener.setting();
                    LiveSendMsgDialog.this.dismiss();
                }
                break;
            case R.id.layout_pt:
                if (clickBtListener != null) {
                    clickBtListener.connectPT();
                    LiveSendMsgDialog.this.dismiss();
                }
                break;
            case R.id.layout_cancel:
                LiveSendMsgDialog.this.dismiss();
                break;
        }
    }


    public interface ClickBtListener {
        void sendHB();
        void sendPic();
        void setting();
        void connectPT();
    }


}
