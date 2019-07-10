package com.blockadm.adm.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.blockadm.adm.R;
import com.blockadm.adm.dialog.WwtchatCodeDialog;
import com.blockadm.adm.event.MyStudyEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.CreateColumnParams;
import com.blockadm.common.bean.QiniuTokenParams;
import com.blockadm.common.bean.params.SaveColumnParams;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.CmlRequestBody;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.ImageUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hp on 2019/2/22.
 */

public class EditDetailComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;

    @BindView(R.id.et_title1)
    EditText etTilte;

    @BindView(R.id.iv_addpic)
    ImageView ivAddpic;
    @BindView(R.id.iv_pictrue1)
    ImageView ivPictrue1;
    @BindView(R.id.iv_delete1)
    ImageView ivDelete1;
    @BindView(R.id.rl_1)
    RelativeLayout rl1;
    @BindView(R.id.et_title2)
    EditText etTitle2;
    @BindView(R.id.iv_pictrue2)
    ImageView ivPictrue2;
    @BindView(R.id.iv_delete2)
    ImageView ivDelete2;
    @BindView(R.id.rl_2)
    RelativeLayout rl2;
    @BindView(R.id.et_title3)
    EditText etTitle3;
    @BindView(R.id.iv_pictrue3)
    ImageView ivPictrue3;
    @BindView(R.id.iv_delete3)
    ImageView ivDelete3;
    @BindView(R.id.rl_3)
    RelativeLayout rl3;
    @BindView(R.id.et_title4)
    EditText etTitle4;
    @BindView(R.id.iv_pictrue4)
    ImageView ivPictrue4;
    @BindView(R.id.iv_delete4)
    ImageView ivDelete4;
    @BindView(R.id.rl_4)
    RelativeLayout rl4;
    @BindView(R.id.et_title5)
    EditText etTitle5;
    @BindView(R.id.iv_pictrue5)
    ImageView ivPictrue5;
    @BindView(R.id.iv_delete5)
    ImageView ivDelete5;
    @BindView(R.id.rl_5)
    RelativeLayout rl5;
    @BindView(R.id.ll_group)
    LinearLayout llGroup;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;


    private CreateColumnParams createColumnParams;
    private File tempFile;
    private QiniuTokenParams qiniuTokenDto1;
    private WwtchatCodeDialog wwtchatCodeDialog;
    private QiniuTokenParams qiniuTokenDto2;
    private QiniuTokenParams qiniuTokenDtoVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_detail);
        ButterKnife.bind(this);
        createColumnParams = (CreateColumnParams) getIntent().getSerializableExtra("SaveColumnParams");
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tilte.setOnRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 save();
            }
        });
        rl2.setVisibility(View.GONE);
        setEditTextState(etTilte);
    }

    String content = "<body>";
    private void save() {

        if (ivDelete1.getTag()!=null){
            QiniuTokenParams  qiniuTokenParams  = (QiniuTokenParams) ivDelete1.getTag();
            if (!TextUtils.isEmpty(qiniuTokenParams.getSaveFullPath())){

                content =  content+"<p ><span >"+qiniuTokenParams.getTitle()+"</span><br/><span ><img src="+"\""+qiniuTokenParams.getSaveFullPath()+"\""+"  height=\"200\"></span></br></p>";
            }else if (!TextUtils.isEmpty(qiniuTokenParams.getTitle())){
                content =  content+"<p ><span >"+qiniuTokenParams.getTitle()+"</span><br/></p>";
            }

        }
        if (ivDelete2.getTag()!=null){
            QiniuTokenParams  qiniuTokenParams  = (QiniuTokenParams) ivDelete2.getTag();
            if (!TextUtils.isEmpty(qiniuTokenParams.getSaveFullPath())){

                content =  content+"<p ><span >"+qiniuTokenParams.getTitle()+"</span><br/><span ><img src="+"\""+qiniuTokenParams.getSaveFullPath()+"\""+"  height=\"200\"></span></br></p>";
            }else if (!TextUtils.isEmpty(qiniuTokenParams.getTitle())){
                content =  content+"<p ><span >"+qiniuTokenParams.getTitle()+"</span><br/></p>";
            }
        }
        if (ivDelete3.getTag()!=null){
            QiniuTokenParams  qiniuTokenParams  = (QiniuTokenParams) ivDelete3.getTag();
            if (!TextUtils.isEmpty(qiniuTokenParams.getSaveFullPath())){

                content =  content+"<p ><span >"+qiniuTokenParams.getTitle()+"</span><br/><span ><img src="+"\""+qiniuTokenParams.getSaveFullPath()+"\""+"  height=\"200\"></span></br></p>";
            }else if (!TextUtils.isEmpty(qiniuTokenParams.getTitle())){
                content =  content+"<p ><span >"+qiniuTokenParams.getTitle()+"</span><br/></p>";
            }
        }
        if (ivDelete4.getTag()!=null){
            QiniuTokenParams  qiniuTokenParams  = (QiniuTokenParams) ivDelete4.getTag();
            if (!TextUtils.isEmpty(qiniuTokenParams.getSaveFullPath())){

                content =  content+"<p ><span >"+qiniuTokenParams.getTitle()+"</span><br/><span ><img src="+"\""+qiniuTokenParams.getSaveFullPath()+"\""+" height=\"200\" ></span></br></p>";
            }else if (!TextUtils.isEmpty(qiniuTokenParams.getTitle())){
                content =  content+"<p ><span >"+qiniuTokenParams.getTitle()+"</span><br/></p>";
            }
        }
        if (ivDelete5.getTag()!=null){
            QiniuTokenParams  qiniuTokenParams  = (QiniuTokenParams) ivDelete5.getTag();
            if (!TextUtils.isEmpty(qiniuTokenParams.getSaveFullPath())){

                content =  content+"<p ><span >"+qiniuTokenParams.getTitle()+"</span><br/><span ><img src="+"\""+qiniuTokenParams.getSaveFullPath()+"\""+"  height=\"230\"></span></br></p>";
            }else if (!TextUtils.isEmpty(qiniuTokenParams.getTitle())){
                content =  content+"<p ><span >"+qiniuTokenParams.getTitle()+"</span><br/></p>";
            }
        }
        content=content+"</body>";

        if (!TextUtils.isEmpty(createColumnParams.getVideoPath())){
            CommonModel.getImageUploadToken(4,new MyObserver<QiniuTokenParams>() {
                @Override
                public void onSuccess(BaseResponse<QiniuTokenParams> t) {
                    qiniuTokenDtoVideo = t.getData();
                    updateVideo(createColumnParams.getVideoPath());

                }

            });
        }else{
            updataContent();
        }




    }

    private void updateVideo(String path){

        wwtchatCodeDialog = new WwtchatCodeDialog(EditDetailComActivity.this);
        wwtchatCodeDialog.show();
        //创建RequestBody封装参数
        //创建MultipartBody,给RequestBody进行设置
        //构建body
        File  file  =  new File(path);
        RequestBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", qiniuTokenDtoVideo.getUploadToken())
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("video/mp4"), file))
                .build();

        CmlRequestBody cmlRequestBody = new CmlRequestBody(multipartBody) {
            @Override
            public void loading(long current, long total, boolean done) {
                if (!done) {
                    sendMessage(6, current, total, "");
                }

            }
        };
        try {
            CommonModel.uploadImageProgress( cmlRequestBody ,new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendMessage(4, 0, 0, "上传失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code()==200){
                        sendMessage(5, 0, 0,"上传完成");

                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void sendMessage(int what, long current, long total, String msg) {
        Message message = Message.obtain();
        message.what = what;
        message.arg1 = (int) current;
        message.arg2 = (int) total;
        message.obj = msg;
        handler.sendMessage(message);
    }


    @OnClick({R.id.iv_delete1, R.id.iv_delete2, R.id.iv_delete3, R.id.iv_delete4, R.id.iv_delete5, R.id.iv_addpic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_delete1:
                ivDelete1.setTag(null);
                rl1.setVisibility(View.GONE);
                break;
            case R.id.iv_delete2:
                ivDelete2.setTag(null);
                rl2.setVisibility(View.GONE);
                break;
            case R.id.iv_delete3:
                ivDelete3.setTag(null);
                rl3.setVisibility(View.GONE);
                break;
            case R.id.iv_delete4:
                ivDelete4.setTag(null);
                rl4.setVisibility(View.GONE);
                break;
            case R.id.iv_delete5:
                ivDelete5.setTag(null);
                rl5.setVisibility(View.GONE);
                break;
            case R.id.iv_addpic:
                CommonModel.getImageUploadToken(3,new MyObserver<QiniuTokenParams>() {
                    @Override
                    public void onSuccess(BaseResponse<QiniuTokenParams> t) {
                        qiniuTokenDto1 = t.getData();
                        Log.i("iv_addpic", "onSuccess: " +(ivDelete2.getTag()==null)+"    "+(rl2.getVisibility()==View.GONE));
                        if (ivDelete1.getTag()==null&&rl1.getVisibility()==View.GONE){
                            ivDelete1.setTag(qiniuTokenDto1);
                        }else if (ivDelete2.getTag()==null&&rl2.getVisibility()==View.GONE){
                            ivDelete2.setTag(qiniuTokenDto1);
                        }else if (ivDelete3.getTag()==null&&rl3.getVisibility()==View.GONE){
                            ivDelete3.setTag(qiniuTokenDto1);
                        }else if (ivDelete4.getTag()==null&&rl4.getVisibility()==View.GONE){
                            ivDelete4.setTag(qiniuTokenDto1);
                        }else if (ivDelete5.getTag()==null&&rl5.getVisibility()==View.GONE){
                            ivDelete5.setTag(qiniuTokenDto1);
                        }
                        selectPictrue();
                    }
                });
                break;
        }
    }
    private void selectPictrue( ) {
        RxPermissions rxPermissions = new RxPermissions(EditDetailComActivity.this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里填写所需要的权限
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //当所有权限都允许之后，返回true
                            startActivityForResult(getGalleryIntent(new Intent()), PHOTO_REQUEST_GALLERY);

                        } else {
                            //只要有一个权限禁止，返回false，
                            //下一次申请只申请没通过申请的权限
                        }
                    }
                });
    }
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择

    /**跳转到系统相册：适配19前后的系统*/
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            //相册
            if (data != null) {
                String path  =  parsePathByReturnData(data);
                tempFile = new File(path);
                updateImage(path);
            }

        }
    }

    private void updateImage(String path){
        try {
            CommonModel.uploadImage(new File(path), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("uploadImage", "onFailure: ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code()==200){
                        handler.sendEmptyMessage(2);
                    }
                }
            },qiniuTokenDto1.getUploadToken());
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
        Uri selectedImage = data.getData();
        if (selectedImage != null) {

            Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
            if (cursor.moveToFirst() && cursor != null) {
                int columnIndex = cursor.getColumnIndex("_data");//图片过大的时候会返回 -1
                localSelectPath = cursor.getString(columnIndex);//获取到的路径为空，或者就直接崩掉
            }
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
                               InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
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
            switch (msg.what){
                case 1:
                    etTilte.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                        //当键盘弹出隐藏的时候会 调用此方法。
                        @Override
                        public void onGlobalLayout() {
                            Rect r = new Rect();
                            //获取当前界面可视部分
                            EditDetailComActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                            //获取屏幕的高度
                            int screenHeight = EditDetailComActivity.this.getWindow().getDecorView().getRootView().getHeight();
                            //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                            int heightDifference = screenHeight - r.bottom;
                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivAddpic.getLayoutParams();
                            layoutParams.bottomMargin = heightDifference - 20;
                            ivAddpic.setLayoutParams(layoutParams);
                        }

                    });
                    break;


                    case 2:
                        if (ivDelete1.getTag()!=null&&rl1.getVisibility()==View.GONE){
                            rl1.setVisibility(View.VISIBLE);
                            QiniuTokenParams  qiniuTokenParams  = (QiniuTokenParams) ivDelete1.getTag();
                            ImageUtils.loadImageView(qiniuTokenParams.getSaveFullPath(),ivPictrue1);
                            qiniuTokenParams.setTitle(etTilte.getText().toString().trim());
                            etTitle2.setVisibility(View.VISIBLE);
                            setEditTextState(etTitle2);
                        }else if (ivDelete2.getTag()!=null&&rl2.getVisibility()==View.GONE){
                            rl2.setVisibility(View.VISIBLE);
                            QiniuTokenParams  qiniuTokenParams  = (QiniuTokenParams) ivDelete2.getTag();
                            qiniuTokenParams.setTitle(etTitle2.getText().toString().trim());
                            ImageUtils.loadImageView(qiniuTokenParams.getSaveFullPath(),ivPictrue2);
                            etTitle3.setVisibility(View.VISIBLE);
                            setEditTextState(etTitle3);
                        }else if (ivDelete3.getTag()!=null&&rl3.getVisibility()==View.GONE){
                            rl3.setVisibility(View.VISIBLE);
                            QiniuTokenParams  qiniuTokenParams  = (QiniuTokenParams) ivDelete3.getTag();
                            qiniuTokenParams.setTitle(etTitle3.getText().toString().trim());
                            ImageUtils.loadImageView(qiniuTokenParams.getSaveFullPath(),ivPictrue3);
                            etTitle4.setVisibility(View.VISIBLE);
                            setEditTextState(etTitle4);
                        }else if (ivDelete4.getTag()!=null&&rl4.getVisibility()==View.GONE){
                            rl4.setVisibility(View.VISIBLE);
                            QiniuTokenParams  qiniuTokenParams  = (QiniuTokenParams) ivDelete4.getTag();
                            qiniuTokenParams.setTitle(etTitle4.getText().toString().trim());
                            ImageUtils.loadImageView(qiniuTokenParams.getSaveFullPath(),ivPictrue4);
                            etTitle5.setVisibility(View.VISIBLE);
                            setEditTextState(etTitle5);
                        }else if (ivDelete5.getTag()!=null&&rl5.getVisibility()==View.GONE){
                            rl5.setVisibility(View.VISIBLE);
                            QiniuTokenParams  qiniuTokenParams  = (QiniuTokenParams) ivDelete5.getTag();
                            ImageUtils.loadImageView(qiniuTokenParams.getSaveFullPath(),ivPictrue5);
                            qiniuTokenParams.setTitle(etTitle5.getText().toString().trim());
                        }
                        break;



                case 4:
                    //上传失败
                    break;
                case 5:

                    updataContent();
                    //上传成功
                    wwtchatCodeDialog.dismiss();

                    break;
                case 6:
                    //上传进度
                    int current = msg.arg1;
                    int total = msg.arg2;
                    wwtchatCodeDialog.setProgress(current);

                    break;
            }


        }
    };

    private void updataContent() {
        SaveColumnParams saveColumnParams = new SaveColumnParams();
         if (qiniuTokenDtoVideo!=null){
             saveColumnParams.setIntroduceVideoUrl(qiniuTokenDtoVideo.getSaveFullPath());
         }

        saveColumnParams.setAccessStatus(createColumnParams.getAccessStatus());
        saveColumnParams.setMoney(createColumnParams.getPrice());
        saveColumnParams.setLessonsCount(0);
        saveColumnParams.setCoverImgs(createColumnParams.getCoverImgs());
        saveColumnParams.setTitle(createColumnParams.getName());
        saveColumnParams.setContent(content);
        saveColumnParams.setStatus(0);

        String params =  GsonUtil.GsonString(saveColumnParams);
        CommonModel.addNewsLessonsSpecialColumn(params, new MyObserver<String>() {
            @Override
            public void onSuccess(BaseResponse<String> t) {
                if (t.getCode()==0){
                    Intent intent = new Intent(EditDetailComActivity.this,MyStudyActiviy.class);
                    startActivity(intent);
                    EventBus.getDefault().post(new MyStudyEvent());
                }else{
                    showToast(EditDetailComActivity.this,t.getMsg());
                }

            }


        });
    }
}
