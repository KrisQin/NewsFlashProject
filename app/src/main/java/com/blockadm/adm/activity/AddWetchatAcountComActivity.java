package com.blockadm.adm.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.adm.dialog.WwtchatCodeDialog;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.QiniuTokenParams;
import com.blockadm.common.bean.params.WithdrawAccountParams;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.CmlRequestBody;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;

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
 * Created by hp on 2019/2/21.
 */

public class AddWetchatAcountComActivity extends BaseComActivity {

    private   String PHOTO_FILE_NAME = "";
    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    private QiniuTokenParams qiniuTokenDto1;
    private File tempFile;
    private WwtchatCodeDialog wwtchatCodeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_wetchat_acount);
        ButterKnife.bind(this);

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.tv_upload)
    public void onViewClicked() {

        CommonModel.getImageUploadToken(2,new MyObserver<QiniuTokenParams>() {
            @Override
            public void onSuccess(BaseResponse<QiniuTokenParams> t) {

                qiniuTokenDto1 = t.getData();
                selectPictrue();
            }


        });
    }

    private void selectPictrue() {
        RxPermissions rxPermissions = new RxPermissions(AddWetchatAcountComActivity.this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里填写所需要的权限
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //当所有权限都允许之后，返回true
                            gallery();
                        } else {
                            //只要有一个权限禁止，返回false，
                            //下一次申请只申请没通过申请的权限
                        }
                    }
                });

    }

    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            //相册
            if (data != null) {
               String path  =  parsePathByReturnData(data);
               tempFile = new File(path);
                ImageUtils.loadImagefile(tempFile,ivCode);
                try{
                    updateImage(path);
                }catch (Exception e){
                    Log.i("onActivityResult", e.toString());
                }

            }

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

    /*
  * 从相册获取
  */
    public void gallery() {
        startActivityForResult(getGalleryIntent(new Intent()), PHOTO_REQUEST_GALLERY);
    }

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




    private void updateImage(String path){

        wwtchatCodeDialog = new WwtchatCodeDialog(AddWetchatAcountComActivity.this);
        wwtchatCodeDialog.show();


        //创建RequestBody封装参数
        //创建MultipartBody,给RequestBody进行设置
        //构建body
        File  file  =  new File(path);
        RequestBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", qiniuTokenDto1.getUploadToken())
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .build();

        CmlRequestBody  cmlRequestBody = new CmlRequestBody(multipartBody) {
            @Override
            public void loading(long current, long total, boolean done) {
                if (!done) {
                    sendMessage(3, current, total, "");
                }

            }
        };
        try {
            CommonModel.uploadImageProgress( cmlRequestBody ,new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendMessage(1, 0, 0, "上传失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code()==200){
                        sendMessage(2, 0, 0,"上传完成");
                        Log.i("", "onResponse: ");
                        String saveFullPath1 = qiniuTokenDto1.getSaveFullPath();
                        WithdrawAccountParams withdrawAccountParams  = new WithdrawAccountParams();
                        withdrawAccountParams.setWithdrawUrl(saveFullPath1);
                        withdrawAccountParams.setTypeId(1+"");
                        CommonModel.addOrUpdateWithdrawAccount(new MyObserver<String> () {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                ToastUtils.showToast(t.getMsg());
                                if (t.getCode()==0){
                                    tvUpload.setText("上传完成");
                                    ivCode.setVisibility(View.VISIBLE);
                                    rl.setVisibility(View.GONE);
                                    ToastUtils.showToast(tempFile.getAbsolutePath());
                                    ImageUtils.loadImagefile(tempFile,ivCode);
                                    EventBus.getDefault().post(new UserDataEvent());
                            /*        ACache.get(AddWetchatAcountComActivity.this).put("userInfoDto",userInfoDto);
                                    Intent intent = new Intent(AddWetchatAcountComActivity.this,MyMoneyComActivity.class);
                                    startActivity(intent);*/
                                }
                              }


                        }, GsonUtil.GsonString(withdrawAccountParams));
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
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //上传失败
                    break;
                case 2:
                    //上传成功
                    tvUpload.setText((String) msg.obj);
                    wwtchatCodeDialog.dismiss();
                    break;
                case 3:
                    //上传进度
                    int current = msg.arg1;
                    int total = msg.arg2;
                    tvUpload.setText((String) msg.obj);
                    wwtchatCodeDialog.setProgress(current);

                    break;
            }
        }
    };


}
