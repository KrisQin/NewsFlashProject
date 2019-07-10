package com.blockadm.adm.base;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.R;
import com.blockadm.common.base.BaseTitleActivity;
import com.blockadm.common.bean.QiniuTokenParams;
import com.blockadm.common.bean.UserParams;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.NetworkUtils;
import com.blockadm.common.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Kris on 2019/5/17
 *
 * @Describe TODO { 选择图片基类 }
 */
public abstract class BaseSelectPictureActivity extends BaseTitleActivity {

    private QiniuTokenParams qiniuTokenDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private PopupWindow mPopupWindow;
    protected void ShowPopupWindow() {
        View popView = LayoutInflater.from(this).inflate(R.layout.private_show_update_personal_pop, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setContentView(popView);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.popupwindow_background));
        mPopupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        mPopupWindow.setOnDismissListener(new poponDismissListener());
        mPopupWindow.setOutsideTouchable(false);  //设置点击屏幕其它地方弹出框消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        TextView shot_layout = (TextView) popView.findViewById(R.id.camera_text);//拍摄照片
        TextView album_layout = (TextView) popView.findViewById(R.id.album_text);//相册选取
        Button cancel_btn = (Button) popView.findViewById(R.id.cancel_pop_btn);//取消
        shot_layout.setOnClickListener(itemclick);
        album_layout.setOnClickListener(itemclick);
        cancel_btn.setOnClickListener(itemclick);
        popView.setFocusable(false);
        mPopupWindow.showAtLocation(popView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        backgroundAlpha(0.5f);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private View.OnClickListener itemclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CommonModel.getImageUploadToken(0,new MyObserver<QiniuTokenParams>() {
                @Override
                public void onSuccess(BaseResponse<QiniuTokenParams> t) {
                    qiniuTokenDto = t.getData();
                }
            });
            if (v.getId() == R.id.camera_text) {
                RxPermissions rxPermissions = new RxPermissions(BaseSelectPictureActivity.this);
                rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)//这里填写所需要的权限
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(@NonNull Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    //当所有权限都允许之后，返回true
                                    camera();
                                } else {
                                    //只要有一个权限禁止，返回false，
                                    //下一次申请只申请没通过申请的权限
                                }
                            }
                        });

            }
            if (v.getId() == R.id.album_text) {
                //调用系统相册
                gallery();
            }
            if (v.getId() == R.id.cancel_pop_btn) {

            }

            dissmisspop();
        }
    };

    public void dissmisspop() {
        if (mPopupWindow != null && mPopupWindow.isShowing())
            mPopupWindow.dismiss();

    }


    /* 图片名称 */
    private static final String PHOTO_FILE_NAME = "picture.jpg";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_RESULT = 3;// 结果
    private Uri userImageUri;

    private void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 判断存储卡是否可以用，可用进行存储
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(this,
                                "com.adm.fileprovider_app",
                                new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
            }
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }


    private File tempFile;
    private Bitmap bitmap;
    /*
     * 从相册获取
     */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        if (requestCode == PHOTO_REQUEST_GALLERY) {
            //相册
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA && resultCode == -1) {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    crop(FileProvider.getUriForFile(this, "com.adm.fileprovider_app", tempFile));
                } else {
                    crop(Uri.fromFile(tempFile));
                }
            } else {
                ToastUtils.showToast("未找到存储卡，无法存储照片！");
            }


        } else if (requestCode == PHOTO_REQUEST_RESULT && resultCode != 0) {
            try {
                if (NetworkUtils.checkNetworkState(this)) {
                    bitmap = data.getParcelableExtra("data");
                    if (bitmap!=null){
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bytes = baos.toByteArray();

//                        Glide.with(this).load(bytes).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .into(civHeadimage);
                        if (tempFile!=null){
                            updateImage(bytes,tempFile.getAbsolutePath());
                        }else {
                            updateImage(bytes,userImageUri.getPath());
                        }
                    }else  if (userImageUri!=null){

                        Bitmap bitmap =  getNativeImage(userImageUri.getPath());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bytes = baos.toByteArray();
//                        Glide.with(this).load(bytes).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .into(civHeadimage);
                        /**
                         * 上传头像
                         */
                        updateImage(bytes,userImageUri.getPath());
                    }
                } else {
                    ToastUtils.showToast("网络或服务器异常");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    protected abstract void showImageByByte(byte[] bytes,String imageServiceUrl);


    private void updateImage(final byte[] bytes,String path){
        try {
            Log.i("updateImage",path);
            CommonModel.uploadImage(new File(path), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("uploadImage", "onFailure: ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code()==200){
                        final String imageServiceUrl = qiniuTokenDto.getSaveFullPath(); // 服务器生成的图片url
                        BaseSelectPictureActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showImageByByte(bytes,imageServiceUrl);
                            }
                        });

                    }

                }
            },qiniuTokenDto.getUploadToken());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //调用系统裁剪
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        //intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri

        //intent.putExtra("output", Uri.fromFile(new File("/mnt/sdcard/temp")));//保存路径
        userImageUri = Uri.parse("file:///"+ Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, userImageUri);


        startActivityForResult(intent, PHOTO_REQUEST_RESULT);
    }

    public static Bitmap getNativeImage(String imagePath)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        Bitmap myBitmap = BitmapFactory.decodeFile(imagePath, options); //此时返回myBitmap为空
        //计算缩放比
        int be = (int)(options.outHeight / (float)200);
        int ys = options.outHeight % 200;//求余数
        float fe = ys / (float)200;
        if (fe >= 0.5)
            be = be + 1;
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;
        //重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
        options.inJustDecodeBounds = false;
        myBitmap = BitmapFactory.decodeFile(imagePath, options);
        return myBitmap;
    }

}
