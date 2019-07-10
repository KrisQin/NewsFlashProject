package com.blockadm.adm.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.dialog.IdListDialog;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.QiniuTokenParams;
import com.blockadm.common.bean.params.AuthenticationParams;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.RegularuUtil;
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
import okhttp3.Response;

/**
 * Created by hp on 2019/2/18.
 */

public class AuthenticationPersonnalComActivity extends BaseComActivity {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_cardnum)
    EditText etCardnum;

    @BindView(R.id.iv_two)
    ImageView ivTwo;

    @BindView(R.id.iv_one)
    ImageView ivOne;
    private String PHOTO_FILE_NAME ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_authentication_personal);
        ButterKnife.bind(this);

        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tilte.setOnRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 submit();
            }
        });
        tilte.setTitle("个人认证");
       // etCardnum.setInputType(InputType.TYPE_CLASS_NUMBER);

    }
    private void submit() {

        String  nameString  = etName.getText().toString();
        if (TextUtils.isEmpty(nameString)){
            return ;
        }
       String  cardNum =  etCardnum.getText().toString();
        if (TextUtils.isEmpty(cardNum)){
            return ;
        }
        if (!RegularuUtil.idCardNumberRegex(cardNum,this)){
           showPopwindow();
           return;
        }
        if (typeSelected==-1){
            return;
        }

        if (TextUtils.isEmpty(saveFullPath1)){
            return;
        }

        if (TextUtils.isEmpty(saveFullPath2)){
            return;
        }
        AuthenticationParams authenticationParams = new AuthenticationParams();
        authenticationParams.setApplyType(0);
        authenticationParams.setFrontPhoto(saveFullPath1);
        authenticationParams.setBackPhoto(saveFullPath2);
        authenticationParams.setCardId(cardNum);
        authenticationParams.setName(nameString);


        CommonModel.addUserCredentials(new MyObserver<String>() {
            @Override
            public void onSuccess(BaseResponse<String> t) {
                if (t.getCode()==0){
                    EventBus.getDefault().post(new UserDataEvent());
                    Intent intent = new Intent(AuthenticationPersonnalComActivity.this, AuthenticationComActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(AuthenticationPersonnalComActivity.this,t.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }


        }, GsonUtil.GsonString(authenticationParams));
    }

    private void showPopwindow() {

        final PopupWindow  popupWindow = new PopupWindow(this,null,R.style.Transparent_Dialog);
       View view =  LayoutInflater.from(this).inflate(R.layout.pop_layout,null);
        ImageView popView =view.findViewById(R.id.iv_image);

        popupWindow.setContentView(view);
        popupWindow.showAsDropDown( etCardnum);
        popView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private IdListDialog idListDialog ;
    private int typeSelected=-1 ;

    @OnClick({R.id.tv_type,R.id.iv_two,R.id.iv_one})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_type:
                if (idListDialog==null){
                    idListDialog  = new IdListDialog(this);
                }
                idListDialog.show();
                idListDialog.setSexSaveListner(new IdListDialog.TypeSaveListner() {
                    @Override
                    public void onSaveClick(String textType, int type) {
                        typeSelected = type;
                        tvType.setText(textType);
                        idListDialog.dismiss();
                    }
                });
                break;


            case R.id.iv_two:
                type =2;
                CommonModel.getImageUploadToken(1,new MyObserver<QiniuTokenParams>() {
                    @Override
                    public void onSuccess(BaseResponse<QiniuTokenParams> t) {
                        qiniuTokenDto2 = t.getData();
                        ShowPopupWindow();
                    }


                });

                break;

            case R.id.iv_one:
                type =1;
                CommonModel.getImageUploadToken(1,new MyObserver<QiniuTokenParams>() {
                    @Override
                    public void onSuccess(BaseResponse<QiniuTokenParams> t) {
                        qiniuTokenDto1 = t.getData();
                        ShowPopupWindow();
                    }


                });

                break;
        }
    }

    private PopupWindow mPopupWindow;
    private void ShowPopupWindow() {
        View popView = LayoutInflater.from(AuthenticationPersonnalComActivity.this).inflate(R.layout.private_show_update_personal_pop, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setContentView(popView);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.popupwindow_background));
        mPopupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        mPopupWindow.setOnDismissListener(new AuthenticationPersonnalComActivity.poponDismissListener());
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

    private View.OnClickListener itemclick = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {

            RxPermissions rxPermissions = new RxPermissions(AuthenticationPersonnalComActivity.this);
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)//这里填写所需要的权限
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(@NonNull Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                //当所有权限都允许之后，返回true
                                if (v.getId() == R.id.camera_text) {

                                    camera();
                                }
                                if (v.getId() == R.id.album_text) {
                                    //调用系统相册
                                    gallery();
                                }


                            } else {
                                //只要有一个权限禁止，返回false，
                                //下一次申请只申请没通过申请的权限
                            }
                        }
                    });


            dissmisspop();
        }
    };

    /*
* 从相册获取
*/
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    public void gallery() {
        startActivityForResult(getGalleryIntent(new Intent()), PHOTO_REQUEST_GALLERY);
    }
    public void dissmisspop() {
        if (mPopupWindow != null && mPopupWindow.isShowing())
            mPopupWindow.dismiss();

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
     private int type = -1;
    private QiniuTokenParams qiniuTokenDto1;
    private QiniuTokenParams qiniuTokenDto2;


    private void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 判断存储卡是否可以用，可用进行存储
        PHOTO_FILE_NAME =System.currentTimeMillis()+".jpg";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(AuthenticationPersonnalComActivity.this,
                                "com.adm.fileprovider_app",
                                new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
            }
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_CAMERA && resultCode == -1) {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
               File  tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
              if (type==1){
                  ImageUtils.loadImagefileWithError(tempFile,R.mipmap.idphoto01,ivOne);
              }else if (type==2){
                  ImageUtils.loadImagefileWithError(tempFile,R.mipmap.idphoto02,ivTwo);
              }

                updateImage(tempFile.getAbsolutePath(),type);

            } else {
                ToastUtils.showToast("未找到存储卡，无法存储照片！");
            }

        }  else if (requestCode == PHOTO_REQUEST_GALLERY) {
            //相册
            if (data != null) {
                String path  =  parsePathByReturnData(data);
//                BitmapUtil.getOptimalBitmapFromLocal(path);

                Bitmap bitmap = ImageUtils.compressPixel(path);
                File tempFile =  ImageUtils.getFile(bitmap);
                if (type==1){
                    ImageUtils.loadImagefileWithError(tempFile,R.mipmap.idphoto01,ivOne);
                }else if (type==2){
                    ImageUtils.loadImagefileWithError(tempFile,R.mipmap.idphoto02,ivTwo);
                }
                updateImage(tempFile.getAbsolutePath(),type);
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

    private  String  saveFullPath1;
    private  String  saveFullPath2;
    private void updateImage(String path, final int type){
     String token = "";
        if (type==1&&qiniuTokenDto1!=null){
            token  = qiniuTokenDto1.getUploadToken();
        }else if (type==2&&qiniuTokenDto2!=null){
            token  = qiniuTokenDto2.getUploadToken();
        }
        try {
            CommonModel.uploadImage(new File(path), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("uploadImage", "onFailure: ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code()==200){
                        if (type==1){
                            saveFullPath1  = qiniuTokenDto1.getSaveFullPath();
                        }else if (type==2){
                            saveFullPath2  = qiniuTokenDto2.getSaveFullPath();
                        }
                    }
                }
            },token);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
