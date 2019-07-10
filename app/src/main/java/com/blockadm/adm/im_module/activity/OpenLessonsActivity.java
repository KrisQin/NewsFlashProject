package com.blockadm.adm.im_module.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blockadm.adm.utils.DialogUtils;
import com.blockadm.adm.R;
import com.blockadm.adm.base.BaseSelectPictureActivity;
import com.blockadm.adm.im_module.widget.EditTextLinearLayout;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.StudyTypeInfo;
import com.blockadm.common.bean.params.AddLiveLessonsParams;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.ComObserver;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.KeyboardUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.T;
import com.blockadm.common.utils.TimeUtils;
import com.blockadm.common.widget.RoundRectLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kris on 2019/5/17
 *
 * @Describe TODO { 创建直播课程 }
 */
public class OpenLessonsActivity extends BaseSelectPictureActivity implements View.OnClickListener {

    private EditText mEt_title;
    private EditTextLinearLayout mEditTextLinearLayout;
    private TextView mTitle_limit;
    private TextView mTv_count;
    private ImageView mIv_add_pic;
    private ImageView mIv_select_pic;
    private TextView mTv_time;
    private TextView mTv_status;
    private RoundRectLayout mLayout_free;
    private RoundRectLayout mLayout_pwd;
    private RoundRectLayout mLayout_pay;
    private RelativeLayout mLayout_submit;
    private TextView mTv_free;
    private TextView mTv_pwd;
    private TextView mTv_pay;

    private final int FREE_TYPE = 0;
    private final int PWD_TYPE = 2;
    private final int PAY_TYPE = 1;
    private int mCurrentType = -1;

    private TimePickerView dateTimeDialog, dateMinuteDialog;

    private String dateTimeStr = "";
    private String minuteTimeStr = "";
    ArrayList<StudyTypeInfo> mList = new ArrayList<>();
    private ImageView mIv_arrow_right;
    private int mSysTypeId;
    private String mCoverImgs;
    private RoundRectLayout mLayout_money;
    private TextView mTv_text;
    private TextView mTv_desc;
    private EditText mEt_priceOrPwd;

    public static final String COM_FROM_CommunityManagerActivity =
            "COM_FROM_CommunityManagerActivity";
    private boolean isComFCommunityListManagerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomView(R.layout.activity_open_lessons);
        setTitle("创建直播课程");

        initView();
        initData();

    }

    private void initView() {
        mEt_title = findContentViewById(R.id.et_title);
        mEditTextLinearLayout = findContentViewById(R.id.et_content);
        mTitle_limit = findContentViewById(R.id.title_limit);
        mTv_count = findContentViewById(R.id.tv_count);
        mIv_add_pic = findContentViewById(R.id.iv_add_pic);
        mIv_select_pic = findContentViewById(R.id.iv_select_pic);
        mTv_time = findContentViewById(R.id.tv_time);
        mIv_arrow_right = findContentViewById(R.id.iv_arrow_right);
        mTv_status = findContentViewById(R.id.tv_status);
        mLayout_free = findContentViewById(R.id.layout_free);
        mLayout_pwd = findContentViewById(R.id.layout_pwd);
        mLayout_pay = findContentViewById(R.id.layout_pay);
        mLayout_submit = findContentViewById(R.id.layout_submit);

        mLayout_money = findContentViewById(R.id.layout_money);
        mTv_text = findContentViewById(R.id.tv_text);
        mTv_desc = findContentViewById(R.id.tv_desc);
        mEt_priceOrPwd = findContentViewById(R.id.et_price);

        mEt_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() >20) {
                    mTitle_limit.setVisibility(View.VISIBLE);
                }else {
                    mTitle_limit.setVisibility(View.GONE);
                }
            }
        });


        mTv_free = findContentViewById(R.id.tv_free);
        mTv_pwd = findContentViewById(R.id.tv_pwd);
        mTv_pay = findContentViewById(R.id.tv_pay);

        mIv_arrow_right.setOnClickListener(this);
        mTv_status.setOnClickListener(this);
        mIv_add_pic.setOnClickListener(this);
        mLayout_free.setOnClickListener(this);
        mLayout_pwd.setOnClickListener(this);
        mLayout_pay.setOnClickListener(this);
        mLayout_submit.setOnClickListener(this);
        mTv_time.setOnClickListener(this);
        mIv_select_pic.setOnClickListener(this);

        mTv_time.setText(TimeUtils.getCurrentTimeInString(TimeUtils.DATE_FORMAT_MINIUTE));
    }

    private void initData() {
        queryStudyTypeList();

        isComFCommunityListManagerActivity =
                getIntent().getBooleanExtra(COM_FROM_CommunityManagerActivity, false);
    }


    @Override
    protected void showImageByByte(byte[] bytes, String imageServiceUrl) {
        Glide.with(this).load(bytes).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mIv_add_pic);

        mCoverImgs = imageServiceUrl;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_add_pic:

                KeyboardUtils.hideSoftInput(this);
                ShowPopupWindow();
                break;
            case R.id.iv_select_pic:

                break;
            case R.id.iv_arrow_right:
            case R.id.tv_status:
                startActivityForResult(new Intent(this, LessonsTypeActivity.class), 101);
                break;
            case R.id.tv_time:
                showDateTimeDialog();
                break;
            case R.id.layout_free:
                showType(FREE_TYPE);
                break;
            case R.id.layout_pwd:
                showType(PWD_TYPE);
                mEt_priceOrPwd.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case R.id.layout_pay:
                showType(PAY_TYPE);
                mEt_priceOrPwd.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case R.id.layout_submit:
                submit();
                break;
        }
    }


    private void submit() {
        String accessPwd = "";
        int money = 0;

        if (mEditTextLinearLayout.getTotalCount() > 500) {
            T.showShort(this, "课程内容字数超过限制");
            return;
        }

        if (mCurrentType == PWD_TYPE) {
            accessPwd = mEt_priceOrPwd.getText().toString().trim();
        } else if (mCurrentType == PAY_TYPE) {
            String ms = mEt_priceOrPwd.getText().toString().trim();
            if (ms.startsWith("0")) {
                T.showShort(this, "请输入正确的金额");
                return;
            }
            if (StringUtils.isNotEmpty(ms)) {
                money = Integer.valueOf(mEt_priceOrPwd.getText().toString().trim());
            }
        }

        //        String content = mEt_content.getText().toString().trim();
        String content = mEditTextLinearLayout.getContent();
        String lessonsTime = mTv_time.getText().toString().trim();
        String title = mEt_title.getText().toString().trim();

        boolean flag = false;

        if ((mCurrentType == PWD_TYPE && StringUtils.isEmpty(accessPwd))
                || (mCurrentType == PAY_TYPE && StringUtils.isEmpty(money + ""))
                || mCurrentType == -1) {
            flag = true;
        }

        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(mCoverImgs)
                || StringUtils.isEmpty(lessonsTime) || flag) {

            DialogUtils.showOpenLessonEmptyContentDialog(this);
            return;
        }

        AddLiveLessonsParams params = new AddLiveLessonsParams(accessPwd, mCurrentType + "",
                content, mCoverImgs, lessonsTime + ":00", "1", money, "1", mSysTypeId + "", title);

        String json = GsonUtil.GsonString(params);

        showDefaultLoadingDialog();
        L.d("json 入参： " + json);
        CommonModel.addLiveLessons(json, new MyObserver() {
            @Override
            public void onSuccess(BaseResponse t) {

                dismissLoadingDialog();
                if (t.isSuccess()) {
                    T.showShort(OpenLessonsActivity.this, "创建成功");
                    if (isComFCommunityListManagerActivity) {
                        setResult(RESULT_OK);
                    } else {
                        startActivity(new Intent(OpenLessonsActivity.this,
                                CommunityListManagerActivity.class));
                    }
                    OpenLessonsActivity.this.finish();
                } else {
                    T.showShort(OpenLessonsActivity.this, t.getMsg());
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mEditTextLinearLayout.PHOTO_REQUEST_GALLERY) {
            //相册
            if (data != null) {
                mEditTextLinearLayout.setPhotoData(data);
            }
        } else if (requestCode == 101 && resultCode == RESULT_OK) {
            if (data != null) {
                StudyTypeInfo studyTypeInfo = (StudyTypeInfo) data.getSerializableExtra(
                        "StudyTypeInfo");
                if (studyTypeInfo != null) {
                    mSysTypeId = studyTypeInfo.getId();
                    mTv_status.setText(studyTypeInfo.getTypeName());
                }
            }
        }
    }

    public void queryStudyTypeList() {
        CommonModel.queryStudyTypeList("3", new ComObserver<ArrayList<StudyTypeInfo>>() {

            @Override
            public void onSuccess(BaseResponse<ArrayList<StudyTypeInfo>> t, String errorMsg) {
                L.d("-------------- queryStudyTypeList  --> onSuccess code: " + t.getCode());
                if (t.getData() != null && t.getData().size() > 0) {
                    mList.addAll(t.getData());
                    StudyTypeInfo studyTypeInfo = mList.get(0);
                    mSysTypeId = studyTypeInfo.getId();
                    mTv_status.setText(studyTypeInfo.getTypeName());
                }
            }
        });
    }


    private void showTypeLayout(int type) {
        if (type == FREE_TYPE) {
            mLayout_money.setVisibility(View.GONE);
        } else {
            mLayout_money.setVisibility(View.VISIBLE);

            if (type == PAY_TYPE) {
                mTv_text.setText("A点");
                mEt_priceOrPwd.setHint("请输入价格");
                mTv_desc.setText("请输入1-5000000A点，不支持小数点后数字");
            } else {
                mTv_text.setText("123456");
                mEt_priceOrPwd.setHint("请输入密码");
                mTv_desc.setText("密码仅支持英文和数字，不区分大小写");
            }
        }
    }

    private void showType(int type) {
        showTypeLayout(type);
        mCurrentType = type;
        mLayout_free.setBackgroundColor(getResources().getColor(R.color.color_fff2f3f4));
        mLayout_pwd.setBackgroundColor(getResources().getColor(R.color.color_fff2f3f4));
        mLayout_pay.setBackgroundColor(getResources().getColor(R.color.color_fff2f3f4));
        mTv_free.setTextColor(getResources().getColor(R.color.color_333333));
        mTv_pwd.setTextColor(getResources().getColor(R.color.color_333333));
        mTv_pay.setTextColor(getResources().getColor(R.color.color_333333));

        if (type == FREE_TYPE) {
            mLayout_free.setBackgroundColor(getResources().getColor(R.color.color_blue));
            mTv_free.setTextColor(getResources().getColor(R.color.white));
        } else if (type == PWD_TYPE) {
            mLayout_pwd.setBackgroundColor(getResources().getColor(R.color.color_blue));
            mTv_pwd.setTextColor(getResources().getColor(R.color.white));
        } else if (type == PAY_TYPE) {
            mLayout_pay.setBackgroundColor(getResources().getColor(R.color.color_blue));
            mTv_pay.setTextColor(getResources().getColor(R.color.white));
        }
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private String getMinuteTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }


    /**
     * 选择年月日
     *
     * @return
     */
    private void showDateTimeDialog() { //Dialog 模式下，在底部弹出
        if (dateTimeDialog == null) {
            dateTimeDialog = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(final Date date, View v) {
                    dateTimeStr = getTime(date);
                    L.d("xxx",
                            " dateTimeStr : " + dateTimeStr + " ; minuteTimeStr : " + minuteTimeStr);
                    dateTimeDialog.dismiss();
                    showMinuteTimeDialog();

                }
            })
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                    .build();

            Dialog mDialog = dateTimeDialog.getDialog();
            if (mDialog != null) {

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM);

                params.leftMargin = 0;
                params.rightMargin = 0;
                dateTimeDialog.getDialogContainerLayout().setLayoutParams(params);

                Window dialogWindow = mDialog.getWindow();
                if (dialogWindow != null) {
                    dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                    dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                    dialogWindow.setDimAmount(0.1f);
                }
            }
        }

        dateTimeDialog.show();

    }


    /**
     * 选择时分
     *
     * @return
     */
    private void showMinuteTimeDialog() { //Dialog 模式下，在底部弹出

        if (dateMinuteDialog == null) {
            dateMinuteDialog = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(final Date date, View v) {
                    minuteTimeStr = getMinuteTime(date);
                    dateMinuteDialog.dismiss();

                    if (TimeUtils.smallThenCurrentTime(dateTimeStr,minuteTimeStr)) {
                        showSmallTimeDialog();
                    }else {
                        String time = dateTimeStr + " " + minuteTimeStr;
                        mTv_time.setText(time);
                    }

                    L.d("xxx",
                            " dateTimeStr : " + dateTimeStr + " ; minuteTimeStr : " + minuteTimeStr);

                }
            })
                    .setType(new boolean[]{false, false, false, true, true, false})
                    .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                    .build();

            Dialog mDialog = dateMinuteDialog.getDialog();
            if (mDialog != null) {

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM);

                params.leftMargin = 0;
                params.rightMargin = 0;
                dateMinuteDialog.getDialogContainerLayout().setLayoutParams(params);

                Window dialogWindow = mDialog.getWindow();
                if (dialogWindow != null) {
                    dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                    dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                    dialogWindow.setDimAmount(0.1f);
                }
            }
        }

        dateMinuteDialog.show();

    }

    private void showSmallTimeDialog() {

        DialogUtils.showSmallTimeDialog(this);
    }


}
