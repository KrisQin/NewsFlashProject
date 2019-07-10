package com.blockadm.adm.im_module.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.R;
import com.blockadm.common.bean.QiniuTokenParams;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ImageUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Kris on 2019/6/4
 *
 * @Describe TODO {  }
 */
public class EditTextLinearLayout extends LinearLayout implements View.OnClickListener {

    private Activity activity;

    EditText etTilte;

    ImageView ivAddpic;
    ImageView ivPictrue1;
    ImageView ivDelete1;
    RelativeLayout rl1;
    EditText etTitle2;
    ImageView ivPictrue2;
    ImageView ivDelete2;
    RelativeLayout rl2;
    EditText etTitle3;
    ImageView ivPictrue3;
    ImageView ivDelete3;
    RelativeLayout rl3;
    EditText etTitle4;
    ImageView ivPictrue4;
    ImageView ivDelete4;
    RelativeLayout rl4;
    EditText etTitle5;
    ImageView ivPictrue5;
    ImageView ivDelete5;
    RelativeLayout rl5;
    LinearLayout llGroup;
    ScrollView sv;
    RelativeLayout rlRoot;
    TextView mTv_count;

    private QiniuTokenParams qiniuTokenDto1;

    public EditTextLinearLayout(Context context) {
        this(context, null);
        init();
    }

    public EditTextLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        activity = (Activity) getContext();
        inflate(getContext(), R.layout.edittext_layout, this);

        initView();

        initData();


    }

    private void initView() {
        etTilte = findViewById(R.id.et_title1);
        mTv_count = findViewById(R.id.tv_count);
        ivAddpic = findViewById(R.id.iv_addpic);
        ivPictrue1 = findViewById(R.id.iv_pictrue1);
        ivDelete1 = findViewById(R.id.iv_delete1);
        rl1 = findViewById(R.id.rl_1);
        etTitle2 = findViewById(R.id.et_title2);
        ivPictrue2 = findViewById(R.id.iv_pictrue2);
        ivDelete2 = findViewById(R.id.iv_delete2);
        rl2 = findViewById(R.id.rl_2);
        etTitle3 = findViewById(R.id.et_title3);
        ivPictrue3 = findViewById(R.id.iv_pictrue3);
        ivDelete3 = findViewById(R.id.iv_delete3);
        rl3 = findViewById(R.id.rl_3);
        etTitle4 = findViewById(R.id.et_title4);
        ivPictrue4 = findViewById(R.id.iv_pictrue4);
        ivDelete4 = findViewById(R.id.iv_delete4);
        rl4 = findViewById(R.id.rl_4);
        etTitle5 = findViewById(R.id.et_title5);
        ivPictrue5 = findViewById(R.id.iv_pictrue5);
        ivDelete5 = findViewById(R.id.iv_delete5);
        rl5 = findViewById(R.id.rl_5);
        llGroup = findViewById(R.id.ll_group);

        ivAddpic.setOnClickListener(this);
        ivDelete1.setOnClickListener(this);
        ivDelete2.setOnClickListener(this);
        ivDelete3.setOnClickListener(this);
        ivDelete4.setOnClickListener(this);
        ivDelete5.setOnClickListener(this);

        etTilte.addTextChangedListener(new MyTextWatcher());
        etTitle2.addTextChangedListener(new MyTextWatcher());
        etTitle3.addTextChangedListener(new MyTextWatcher());
        etTitle4.addTextChangedListener(new MyTextWatcher());
        etTitle5.addTextChangedListener(new MyTextWatcher());
    }

    private int photoCount = 0; //图片个数
    private int textCount = 0; //文字个数
    private int totalCount = 0;

    public int getTotalCount() {
        return totalCount;
    }

    private void showTextCountByEdit() {
        String etText1 = etTilte.getText().toString().trim();
        String etText2 = etTitle2.getText().toString().trim();
        String etText3 = etTitle3.getText().toString().trim();
        String etText4 = etTitle4.getText().toString().trim();
        String etText5 = etTitle5.getText().toString().trim();

        textCount = etText1.length() + etText2.length() +etText3.length() +etText4.length() +etText5.length() ;

        showTextCount();
    }

    private void showTextCountByPhoto(boolean isAddPhoto) {
        if (isAddPhoto) {
            photoCount++;
        }else {
            photoCount--;
        }

        showTextCount();
    }


    private void showTextCount() {
        totalCount = photoCount + textCount;

        if (totalCount > 0) {
            etTilte.setHint("");
        }else {
            etTilte.setHint("请填写直播课程介绍哦");
        }

        String countStr = totalCount + "/500（图片代表一个文字）";
        if (totalCount > 500) {
            SpannableString spannableString = new SpannableString(countStr);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
            spannableString.setSpan(foregroundColorSpan, 0,
                    String.valueOf(totalCount).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mTv_count.setText(spannableString);
        } else {
            mTv_count.setText(countStr);
        }
    }



    private class  MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            showTextCountByEdit();
        }
    }

    private void initData() {
        rl2.setVisibility(View.GONE);
//        setEditTextState(etTilte);
    }

    private String content = "<body>";

    public String getContent() {

        if (ivDelete1.getTag() != null) {
            QiniuTokenParams qiniuTokenParams = (QiniuTokenParams) ivDelete1.getTag();
            if (!TextUtils.isEmpty(qiniuTokenParams.getSaveFullPath())) {

                content = content + "<p ><span >" + qiniuTokenParams.getTitle() + "</span><br" +
                        "/><span ><img src=" + "\"" + qiniuTokenParams.getSaveFullPath() + "\"" + "  height=\"200\"></span></br></p>";
                
            } else if (!TextUtils.isEmpty(qiniuTokenParams.getTitle())) {
                content = content + "<p ><span >" + qiniuTokenParams.getTitle() + "</span><br" +
                        "/></p>";
            }

        }
        if (ivDelete2.getTag() != null) {
            QiniuTokenParams qiniuTokenParams = (QiniuTokenParams) ivDelete2.getTag();
            if (!TextUtils.isEmpty(qiniuTokenParams.getSaveFullPath())) {

                content = content + "<p ><span >" + qiniuTokenParams.getTitle() + "</span><br" +
                        "/><span ><img src=" + "\"" + qiniuTokenParams.getSaveFullPath() + "\"" + "  height=\"200\"></span></br></p>";
            } else if (!TextUtils.isEmpty(qiniuTokenParams.getTitle())) {
                content = content + "<p ><span >" + qiniuTokenParams.getTitle() + "</span><br" +
                        "/></p>";
            }
        }
        if (ivDelete3.getTag() != null) {
            QiniuTokenParams qiniuTokenParams = (QiniuTokenParams) ivDelete3.getTag();
            if (!TextUtils.isEmpty(qiniuTokenParams.getSaveFullPath())) {

                content = content + "<p ><span >" + qiniuTokenParams.getTitle() + "</span><br" +
                        "/><span ><img src=" + "\"" + qiniuTokenParams.getSaveFullPath() + "\"" + "  height=\"200\"></span></br></p>";
            } else if (!TextUtils.isEmpty(qiniuTokenParams.getTitle())) {
                content = content + "<p ><span >" + qiniuTokenParams.getTitle() + "</span><br" +
                        "/></p>";
            }
        }
        if (ivDelete4.getTag() != null) {
            QiniuTokenParams qiniuTokenParams = (QiniuTokenParams) ivDelete4.getTag();
            if (!TextUtils.isEmpty(qiniuTokenParams.getSaveFullPath())) {

                content = content + "<p ><span >" + qiniuTokenParams.getTitle() + "</span><br" +
                        "/><span ><img src=" + "\"" + qiniuTokenParams.getSaveFullPath() + "\"" + " height=\"200\" ></span></br></p>";
            } else if (!TextUtils.isEmpty(qiniuTokenParams.getTitle())) {
                content = content + "<p ><span >" + qiniuTokenParams.getTitle() + "</span><br" +
                        "/></p>";
            }
        }
        if (ivDelete5.getTag() != null) {
            QiniuTokenParams qiniuTokenParams = (QiniuTokenParams) ivDelete5.getTag();
            if (!TextUtils.isEmpty(qiniuTokenParams.getSaveFullPath())) {

                content = content + "<p ><span >" + qiniuTokenParams.getTitle() + "</span><br" +
                        "/><span ><img src=" + "\"" + qiniuTokenParams.getSaveFullPath() + "\"" + "  height=\"230\"></span></br></p>";
            } else if (!TextUtils.isEmpty(qiniuTokenParams.getTitle())) {
                content = content + "<p ><span >" + qiniuTokenParams.getTitle() + "</span><br" +
                        "/></p>";
            }
        }
        content = content + "</body>";


        return content;

    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_delete1) {
            showTextCountByPhoto(false);
            ivDelete1.setTag(null);
            rl1.setVisibility(View.GONE);
        } else if (id == R.id.iv_delete2) {
            showTextCountByPhoto(false);
            ivDelete2.setTag(null);
            rl2.setVisibility(View.GONE);
        } else if (id == R.id.iv_delete3) {
            showTextCountByPhoto(false);
            ivDelete3.setTag(null);
            rl3.setVisibility(View.GONE);
        } else if (id == R.id.iv_delete4) {
            showTextCountByPhoto(false);
            ivDelete4.setTag(null);
            rl4.setVisibility(View.GONE);
        } else if (id == R.id.iv_delete5) {
            showTextCountByPhoto(false);
            ivDelete5.setTag(null);
            rl5.setVisibility(View.GONE);
        } else if (id == R.id.iv_addpic) {
            CommonModel.getImageUploadToken(3, new MyObserver<QiniuTokenParams>() {
                @Override
                public void onSuccess(BaseResponse<QiniuTokenParams> t) {
                    qiniuTokenDto1 = t.getData();
                    Log.i("iv_addpic",
                            "onSuccess: " + (ivDelete2.getTag() == null) + "    " + (rl2.getVisibility() == View.GONE));
                    if (ivDelete1.getTag() == null && rl1.getVisibility() == View.GONE) {
                        ivDelete1.setTag(qiniuTokenDto1);
                    } else if (ivDelete2.getTag() == null && rl2.getVisibility() == View.GONE) {
                        ivDelete2.setTag(qiniuTokenDto1);
                    } else if (ivDelete3.getTag() == null && rl3.getVisibility() == View.GONE) {
                        ivDelete3.setTag(qiniuTokenDto1);
                    } else if (ivDelete4.getTag() == null && rl4.getVisibility() == View.GONE) {
                        ivDelete4.setTag(qiniuTokenDto1);
                    } else if (ivDelete5.getTag() == null && rl5.getVisibility() == View.GONE) {
                        ivDelete5.setTag(qiniuTokenDto1);
                    }
                    selectPictrue();
                }
            });
        }

    }


    private void selectPictrue() {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里填写所需要的权限
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //当所有权限都允许之后，返回true
                            activity.startActivityForResult(getGalleryIntent(new Intent()),
                                    PHOTO_REQUEST_GALLERY);

                        } else {
                            //只要有一个权限禁止，返回false，
                            //下一次申请只申请没通过申请的权限
                        }
                    }
                });
    }

    public static final int PHOTO_REQUEST_GALLERY = 22;// 从相册中选择

    /**
     * 跳转到系统相册：适配19前后的系统
     */
    public static Intent getGalleryIntent(Intent intent) {
        /**19之后的系统相册的图片都存在于MediaStore数据库中；19之前的系统相册中可能包含不存在与数据库中的图片，所以如果是19之上的系统
         * 跳转到19之前的系统相册选择了一张不存在与数据库中的图片，解析uri时就可能会出现null*/
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        }
        return intent;
    }

    //    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //        if (requestCode == PHOTO_REQUEST_GALLERY) {
    //            //相册
    //            if (data != null) {
    //                String path  =  parsePathByReturnData(data);
    //                updateImage(path);
    //            }
    //
    //        }
    //    }

    public void setPhotoData(Intent data) {
        //相册
        if (data != null) {
            String path = parsePathByReturnData(data);
            updateImage(path);
        }
    }

    public void updateImage(String path) {
        try {
            CommonModel.uploadImage(new File(path), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("uploadImage", "onFailure: ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200) {
                        handler.sendEmptyMessage(2);
                    }
                }
            }, qiniuTokenDto1.getUploadToken());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过返回数据解析路径
     *
     * @param data
     * @return
     */
    private String parsePathByReturnData(Intent data) {
        if (data == null) {
            return null;
        }

        String localSelectPath = null;
        Cursor cursor = null;
        try {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {

                cursor = activity.getContentResolver().query(selectedImage, null, null, null, null);
                if (cursor.moveToFirst() && cursor != null) {
                    int columnIndex = cursor.getColumnIndex("_data");//图片过大的时候会返回 -1
                    localSelectPath = cursor.getString(columnIndex);//获取到的路径为空，或者就直接崩掉
                }

                if (cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return localSelectPath;

    }

    private void setEditTextState(final EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(editText, 0);
                               handler.sendEmptyMessage(1);
                           }
                       },
                200);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
//                    etTilte.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//                        //当键盘弹出隐藏的时候会 调用此方法。
//                        @Override
//                        public void onGlobalLayout() {
//                            Rect r = new Rect();
//                            //获取当前界面可视部分
//                            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
//                            //获取屏幕的高度
//                            int screenHeight =
//                                    activity.getWindow().getDecorView().getRootView().getHeight();
//                            //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
//                            int heightDifference = screenHeight - r.bottom;
//                            RelativeLayout.LayoutParams layoutParams =
//                                    (RelativeLayout.LayoutParams) ivAddpic.getLayoutParams();
//                            layoutParams.bottomMargin = heightDifference - 20;
//                            ivAddpic.setLayoutParams(layoutParams);
//                        }
//
//                    });
                    break;


                case 2:
                    if (ivDelete1.getTag() != null && rl1.getVisibility() == View.GONE) {
                        showTextCountByPhoto(true);
                        rl1.setVisibility(View.VISIBLE);
                        QiniuTokenParams qiniuTokenParams = (QiniuTokenParams) ivDelete1.getTag();
                        ImageUtils.loadImageView(qiniuTokenParams.getSaveFullPath(), ivPictrue1);
                        qiniuTokenParams.setTitle(etTilte.getText().toString().trim());
                        etTitle2.setVisibility(View.VISIBLE);
                        setEditTextState(etTitle2);
                    } else if (ivDelete2.getTag() != null && rl2.getVisibility() == View.GONE) {
                        showTextCountByPhoto(true);
                        rl2.setVisibility(View.VISIBLE);
                        QiniuTokenParams qiniuTokenParams = (QiniuTokenParams) ivDelete2.getTag();
                        qiniuTokenParams.setTitle(etTitle2.getText().toString().trim());
                        ImageUtils.loadImageView(qiniuTokenParams.getSaveFullPath(), ivPictrue2);
                        etTitle3.setVisibility(View.VISIBLE);
                        setEditTextState(etTitle3);
                    } else if (ivDelete3.getTag() != null && rl3.getVisibility() == View.GONE) {
                        showTextCountByPhoto(true);
                        rl3.setVisibility(View.VISIBLE);
                        QiniuTokenParams qiniuTokenParams = (QiniuTokenParams) ivDelete3.getTag();
                        qiniuTokenParams.setTitle(etTitle3.getText().toString().trim());
                        ImageUtils.loadImageView(qiniuTokenParams.getSaveFullPath(), ivPictrue3);
                        etTitle4.setVisibility(View.VISIBLE);
                        setEditTextState(etTitle4);
                    } else if (ivDelete4.getTag() != null && rl4.getVisibility() == View.GONE) {
                        showTextCountByPhoto(true);
                        rl4.setVisibility(View.VISIBLE);
                        QiniuTokenParams qiniuTokenParams = (QiniuTokenParams) ivDelete4.getTag();
                        qiniuTokenParams.setTitle(etTitle4.getText().toString().trim());
                        ImageUtils.loadImageView(qiniuTokenParams.getSaveFullPath(), ivPictrue4);
                        etTitle5.setVisibility(View.VISIBLE);
                        setEditTextState(etTitle5);
                    } else if (ivDelete5.getTag() != null && rl5.getVisibility() == View.GONE) {
                        showTextCountByPhoto(true);
                        rl5.setVisibility(View.VISIBLE);
                        QiniuTokenParams qiniuTokenParams = (QiniuTokenParams) ivDelete5.getTag();
                        ImageUtils.loadImageView(qiniuTokenParams.getSaveFullPath(), ivPictrue5);
                        qiniuTokenParams.setTitle(etTitle5.getText().toString().trim());
                    }
                    break;

            }


        }
    };


}
