package com.blockadm.adm.im_module.widget;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.im_module.call.LiveClikcJiaToastCallback;
import com.blockadm.common.config.AppConfig;
import com.blockadm.common.im.BaseUIKitConfigs;
import com.blockadm.common.im.LiveManager;
import com.blockadm.common.im.UIKitAudioArmMachine;
import com.blockadm.common.im.call.ComCallback;
import com.blockadm.common.im.call.SendMessageCallback;
import com.blockadm.common.im.entity.MessageInfo;
import com.blockadm.common.im.utils.MessageInfoUtil;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.SystemUtils;
import com.blockadm.common.utils.T;
import com.contrarywind.timer.MessageHandler;
import com.tencent.imsdk.TIMMessage;

/**
 * Created by Kris on 2019/5/20
 *
 * @Describe TODO {  }
 */
public class SpeakerBottomInputGroup extends LinearLayout implements View.OnClickListener,
        TextWatcher, UIKitAudioArmMachine.AudioRecordCallback {

    private static final String xxx = "xxx";

    private Activity activity;
    private ImageView mIv_voice;
    private ImageView mIv_show_toast;
    private EditText mEt_text_input;
    private TextView voiceBtn;
    //    private ImageView mIv_setting;
    //    private ImageView mIv_select_pic;
    private ImageView mIv_jia;


    private static final int STATE_NONE_INPUT = -1;
    private static final int STATE_SOFT_INPUT = 0;
    private static final int STATE_VOICE_INPUT = 1;
    private boolean sendAble, audioCancel;
    private int currentState = STATE_SOFT_INPUT;
    private int lastMsgLineCount;
    private float startRecordY;

    private ChatInputHandler inputHandler;
    private MessageHandler msgHandler;
    private AlertDialog mPermissionDialog;
    private String mPackName = AppConfig.APP_NAME;

    private Activity mActivity;
    private Context mContext;
    private View mBottom_line;
    private TextView mTv_send;
    private LinearLayout mLayout_image;
    //    private ImageView mIv_send_hb;


    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    //关闭对话框
    private void cancelPermissionDialog() {
        mPermissionDialog.cancel();
    }

    public void setInputHandler(ChatInputHandler handler) {
        this.inputHandler = handler;
    }

    public void setMsgHandler(MessageHandler handler) {
        this.msgHandler = handler;
    }

    public interface ChatInputHandler {

        void popupAreaShow();

        void popupAreaHide();

        void startRecording();

        void stopRecording();

        void tooShortRecording();

        void cancelRecording();

        void endRecord();

        void endTime(int time);

    }

    public SpeakerBottomInputGroup(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SpeakerBottomInputGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        activity = (Activity) getContext();
        inflate(getContext(), R.layout.chat_bottom_group, this);
        mIv_voice = findViewById(R.id.iv_voice);
        mEt_text_input = findViewById(R.id.et_text_input);
        mBottom_line = findViewById(R.id.bottom_line);
        mTv_send = findViewById(R.id.tv_send);
        voiceBtn = findViewById(R.id.bt_voice_input);
        mIv_show_toast = findViewById(R.id.iv_show_toast);
        //        mIv_send_hb = findViewById(R.id.iv_send_hb);
        //        mIv_setting = findViewById(R.id.iv_setting);
        //        mIv_select_pic = findViewById(R.id.iv_select_pic);
        mIv_jia = findViewById(R.id.iv_jia);
        mLayout_image = findViewById(R.id.layout_image);

        mIv_voice.setOnClickListener(this);
        //        mIv_setting.setOnClickListener(this);
        //        mIv_send_hb.setOnClickListener(this);
        //        mIv_select_pic.setOnClickListener(this);
        mIv_jia.setOnClickListener(this);
        mIv_show_toast.setOnClickListener(this);


        mEt_text_input.addTextChangedListener(this);
        mEt_text_input.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showSoftInput();
                return false;
            }
        });

        tvSendTouch();

        voiceBtnTouch();
    }

    private void tvSendTouch() {
        mTv_send.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        L.d(xxx, " mTv_send MotionEvent.ACTION_DOWN --- ");
                        sendTextMsg();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        L.d(xxx, " mTv_send MotionEvent.ACTION_MOVE --- ");
                        break;
                    case MotionEvent.ACTION_UP:
                        L.d(xxx, " mTv_send MotionEvent.ACTION_UP --- ");
                        break;
                }

                return false;
            }
        });
    }

    private boolean checkVoicePers() {
        if (checkStoragePermisson(activity, Manifest.permission.RECORD_AUDIO) != true) {
            return false;
        }
        if (checkStoragePermisson(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != true) {
            return false;
        }
        if (checkStoragePermisson(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != true) {
            return false;
        }
        return true;
    }


    private boolean isShowCancleView = false;

    private void voiceBtnTouch() {
        voiceBtn.setOnTouchListener(new OnTouchListener() {
            private long start;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!checkVoicePers()) {
                    return false;
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    voiceBtn.setBackgroundResource(R.drawable.bg_r2_cl_dddddd);
                    voiceBtn.setText("松开结束");
                    audioCancel = false;
                    startRecordY = motionEvent.getY();
                    if (inputHandler != null)
                        inputHandler.startRecording();
                    start = System.currentTimeMillis();
                    UIKitAudioArmMachine.getInstance().startRecord(SpeakerBottomInputGroup.this);

                    L.d(xxx, " MotionEvent.ACTION_DOWN --- audioCancel: " + audioCancel);

                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {

                    if (motionEvent.getY() - startRecordY < -100) {
                        audioCancel = true;
                        isShowCancleView = true;
                        if (inputHandler != null)
                            inputHandler.cancelRecording();
                    } else {
                        audioCancel = false;
                        if (inputHandler != null && isShowCancleView)
                            inputHandler.startRecording();
                        isShowCancleView = false;
                    }

                    L.d(xxx, " MotionEvent.ACTION_MOVE --- audioCancel: " + audioCancel);

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP
                        || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {

                    voiceBtn.setBackgroundResource(R.drawable.bg_r2_white);
                    voiceBtn.setText("按住说话");
                    L.d(xxx, " MotionEvent.ACTION_UP --- ");
                    if (motionEvent.getY() - startRecordY < -100) {
                        audioCancel = true;
                    } else {
                        audioCancel = false;
                    }

                    L.d(xxx, " MotionEvent.ACTION_UP --- audioCancel: " + audioCancel);

                    if (inputHandler != null)
                        inputHandler.endRecord();

                    UIKitAudioArmMachine.getInstance().stopRecord();
                }
                return true;
            }
        });
    }

    @Override
    public void recordComplete(long duration) {

        L.d("tim_test",
                "sdsdsd inputHandler : " + inputHandler + " ; audioCancel: " + audioCancel + " ; " +
                        "duration: " + duration + " ; msgHandler: " + msgHandler);
        if (inputHandler != null) {
            if (audioCancel && duration < BaseUIKitConfigs.audioRecordMaxTime * 1000) {
                inputHandler.stopRecording();
                return;
            }
            if (duration < 500) {
                inputHandler.tooShortRecording();
                return;
            }
            inputHandler.stopRecording();
        }

        MessageInfo messageInfo = MessageInfoUtil
                .buildAudioMessage(activity,
                        UIKitAudioArmMachine.getInstance().getRecordAudioPath(), (int) duration);

        /**
         * 发送语音
         */
        if (msgHandler != null)
            msgHandler.sendVoiceMessage(messageInfo);
    }

    @Override
    public void recording(int endTime) {
        L.d("time_test", "endTime: " + endTime);
        if (inputHandler != null) {
            inputHandler.endTime(endTime);
        }
    }


    private void showSoftInput() {
        currentState = STATE_SOFT_INPUT;
        mIv_voice.setImageResource(R.drawable.ic_action_audio_bg);
        mEt_text_input.requestFocus();
        InputMethodManager imm =
                (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mBottom_line, 0);
        if (inputHandler != null)
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    inputHandler.popupAreaShow();
                }
            }, 200);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_voice:
                if (LiveManager.getInstance().getLiveStatus() == LiveManager.LIVE_PRE) {
                    T.showShort(this.getContext(), "直播未开始，不能发送语音");
                    return;
                } else if (LiveManager.getInstance().getLiveStatus() == LiveManager.LIVE_OVER) {
                    T.showShort(this.getContext(), "直播已结束，不能发送语音");
                    return;
                }
                sendVoice();
                break;
            //            case R.id.iv_setting:
            //                clickSetting();
            //                break;
            //            case R.id.iv_select_pic:
            //                if (LiveManager.getInstance().getLiveStatus() == LiveManager
            //                .LIVE_PRE) {
            //                    T.showShort(this.getContext(),"直播未开始，不能发送图片");
            //                    return;
            //                }else if (LiveManager.getInstance().getLiveStatus() == LiveManager
            //                .LIVE_OVER) {
            //                    T.showShort(this.getContext(),"直播已结束，不能发送图片");
            //                    return;
            //                }
            //                sendPic();
            //                break;
            //            case R.id.iv_send_hb:
            //                sendHb();
            //                break;
            case R.id.iv_jia:
                if (mLiveClikcJiaToastCallback != null) {
                    mLiveClikcJiaToastCallback.clickJiaBtCallback();
                }
                break;
            case R.id.iv_show_toast:
                if (mLiveClikcJiaToastCallback != null) {
                    mLiveClikcJiaToastCallback.clickToastBtCallback();
                }
                showToastImageBg();
                break;

        }
    }

    private boolean isShow = false;

    private void showToastImageBg() {
        if (!isShow) {
            mIv_show_toast.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_show_text));
        } else {
            mIv_show_toast.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_show_text_no));
        }
        isShow = !isShow;
    }


    private LiveClikcJiaToastCallback mLiveClikcJiaToastCallback;

    public void setOnClickJiaButtonListener(LiveClikcJiaToastCallback callback) {
        mLiveClikcJiaToastCallback = callback;
    }


    //    private void sendHb() {
    //        if (mSettingListener != null) {
    //            mSettingListener.sendHB();
    //        }
    //
    //    }

    private SendMessageCallback mSendMessageCallback;

    public void setOnSendTextMessageListener(SendMessageCallback callback) {
        mSendMessageCallback = callback;
    }

    /**
     * 主讲人或者管理员发送文字消息
     */
    private void sendTextMsg() {

        if (StringUtils.isEmpty(LiveManager.getInstance().getUserInfoDto().getLoginIMSign())) {
            ContextUtils.showNoLoginDialog(mActivity);
            SystemUtils.hideSoftInput(mActivity);
            return;
        }

        if (LiveManager.getInstance().getLiveStatus() == LiveManager.LIVE_PRE) {
            T.showShort(this.getContext(), "直播未开始，不能发送文字");
            return;
        } else if (LiveManager.getInstance().getLiveStatus() == LiveManager.LIVE_OVER) {
            T.showShort(this.getContext(), "直播已结束，不能发送文字");
            return;
        }

        final String content = mEt_text_input.getText().toString().trim();

        if (StringUtils.isEmpty(content)) {
            return;
        }

        LiveManager.getInstance().sendTextMessage(content, true, new SendMessageCallback() {
            @Override
            public void onError(int i, String s) {
                mEt_text_input.setText("");
                if (mSendMessageCallback != null)
                    mSendMessageCallback.onError(i, s);
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                if (mSendMessageCallback != null)
                    mSendMessageCallback.onSuccess(timMessage);

                mEt_text_input.setText("");
            }
        });


    }

//    private void sendPic() {
//        //        startSendPhoto();
//        if (mPicSelectListener != null) {
//            mPicSelectListener.sendPic();
//        }
//    }

//    BtnClickListener mSettingListener;
//
//    public void setOnBtnClickListener(BtnClickListener listener) {
//        mSettingListener = listener;
//    }
//
//    public interface BtnClickListener {
//        void setting(View mIv_setting);
//
//        void sendHB();
//    }

    //    private void clickSetting() {
    //
    //        if (mSettingListener != null) {
    //            mSettingListener.setting(mIv_setting);
    //        }
    //    }

    private void sendVoice() {
        if (currentState == STATE_SOFT_INPUT) {
            mIv_voice.setImageResource(R.drawable.bottom_action_input_normal);
            mEt_text_input.setVisibility(GONE);
            voiceBtn.setVisibility(VISIBLE);
            hideSoftInput();
            currentState = STATE_VOICE_INPUT;
        } else {
            mIv_voice.setImageResource(R.drawable.ic_action_audio_bg);
            mEt_text_input.setVisibility(VISIBLE);
            voiceBtn.setVisibility(GONE);
            currentState = STATE_SOFT_INPUT;
        }
    }

//    PicSelectListener mPicSelectListener;
//
//    public void setOnSendPicListener(PicSelectListener listener) {
//        mPicSelectListener = listener;
//    }


    public interface MessageHandler {
        void sendVoiceMessage(MessageInfo msg);
    }


//    public interface PicSelectListener {
//        void sendPic();
//    }


    private boolean checkStoragePermisson(Activity activity, String permisson) {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permission = ActivityCompat.checkSelfPermission(activity, permisson);
            if (PackageManager.PERMISSION_GRANTED != permission) {
                //2.没有权限
                showPermissionDialog();
                flag = false;
            }
        }
        return flag;
    }

    private void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(activity)
                    .setMessage("使用该功能，需要开启权限，鉴于您禁用相关权限，请手动设置开启权限")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();
                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent =
                                    new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            packageURI);
                            activity.startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }


    public void hideSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEt_text_input.getWindowToken(), 0);
        mEt_text_input.clearFocus();
        if (inputHandler != null)
            inputHandler.popupAreaHide();
        currentState = STATE_NONE_INPUT;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (editable.toString().length() > 0) {
            mLayout_image.setVisibility(View.GONE);
            mTv_send.setVisibility(View.VISIBLE);
        } else {
            mLayout_image.setVisibility(View.VISIBLE);
            mTv_send.setVisibility(View.GONE);
        }
    }
}
