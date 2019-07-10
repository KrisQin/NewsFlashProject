package com.blockadm.adm.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.CreateColumnParams;
import com.blockadm.common.bean.QiniuTokenParams;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.EditInputFilter;
import com.blockadm.common.comstomview.RoundImageView;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

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
 * Created by hp on 2019/2/22.
 */

public class CreateColumnComActivity extends BaseComActivity {

    private static final int REQUEST_VIDEO_CODE =  2046;
    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.et_name)
    AppCompatEditText etName;


    @BindView(R.id.et_price)
    AppCompatEditText etPrice;
    @BindView(R.id.banner_view)
    RoundImageView bannerView;
    @BindView(R.id.banner_view2)
    RoundImageView bannerVideo;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_select_pictrue)
    TextView tvSelectPictrue;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.iv_free)
    ImageView  ivFree;

    @BindView(R.id.tv_select_video)
    TextView tvSelectVideo;
    private QiniuTokenParams qiniuTokenDto1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_create_column);
        ButterKnife.bind(this);
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        InputFilter[] filters = {new EditInputFilter()};
        etPrice.setFilters(filters);
    }
   private boolean isFree  =false;
    CreateColumnParams createColumnParams = new CreateColumnParams();
    int type;
    @OnClick({R.id.tv_select_pictrue, R.id.tv_edit,R.id.iv_free,R.id.tv_select_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_pictrue:
                type =0;
                CommonModel.getImageUploadToken(3,new MyObserver<QiniuTokenParams>() {
                    @Override
                    public void onSuccess(BaseResponse<QiniuTokenParams> t) {
                        if (t.getCode()==0){
                            qiniuTokenDto1 = t.getData();
                            selectPictrue();
                        }

                    }

                });
               break;
            case R.id.tv_select_video:
                type =1;
                selectPictrue();
                break;
            case R.id.tv_edit:
                String name =  etName.getText().toString().trim();
                String price =  etPrice.getText().toString().trim();
                if (TextUtils.isEmpty(createColumnParams.getCoverImgs())){
                    ToastUtils.showToast("请选择图片");
                    return;
                }
                if (TextUtils.isEmpty(name)){
                    ToastUtils.showToast("输入要创建的专栏名称");
                    return;
                }
                if (!isFree){
                    if (TextUtils.isEmpty(price)){
                        ToastUtils.showToast("输入你的收费价格");
                        return;
                    }
                }

                if (!isFree){
                    if (Double.parseDouble(price)>0){
                        createColumnParams.setAccessStatus(1);
                        createColumnParams.setPrice(price);
                    }else{
                        createColumnParams.setAccessStatus(0);
                    }
                }
                String  m = "/^\\d+(\\.\\d{0,2})?$/";
                if (price.matches(m)){
                    showToast(this,"请保留小数点两位");
                    return;
                }
                if (!TextUtils.isEmpty(price)){
                    if (Double.parseDouble(price)>999999.99){
                        showToast(this,"专栏价格不能超过999999.99");
                        return;
                    }
                }


                createColumnParams.setName(name);
                Intent intent = new Intent(this, EditDetailComActivity.class);
                intent.putExtra("SaveColumnParams",createColumnParams);
                startActivity(intent);
                break;

            case R.id.iv_free:
                if (!isFree){
                    ivFree.setImageResource(R.mipmap.free_press);
                    etPrice.setText("");
                    etPrice.setHint("免费中");
                    etPrice.setEnabled(false);
                    isFree = true;

                }else{
                    etPrice.setEnabled(true);
                    etPrice.setHint("输入你的收费价格");
                    ivFree.setImageResource(R.mipmap.free_press_def);
                    isFree =false;
                }
                break;
        }

        etPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    String  m = "/^\\d+(\\.\\d{0,2})?$/";
                    etPrice.getText().toString().trim().matches(m);
                }
            }
        });
    }


    private void selectPictrue( ) {
        RxPermissions rxPermissions = new RxPermissions(CreateColumnComActivity.this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里填写所需要的权限
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //当所有权限都允许之后，返回true
                            switch (type){
                                case 0:
                                    startActivityForResult(getGalleryIntent(new Intent()), PHOTO_REQUEST_GALLERY);
                                break;

                                case 1:
                                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(intent, REQUEST_VIDEO_CODE);
                                    break;
                            }

                        } else {
                            //只要有一个权限禁止，返回false，
                            //下一次申请只申请没通过申请的权限
                        }
                    }
                });
    }
    private File tempFile;
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            //相册
            if (data != null) {
                String path  =  parsePathByReturnData(data);
                Bitmap bitmap = ImageUtils.compressPixel(path);
                tempFile =  ImageUtils.getFile(bitmap);
                updateImage(tempFile.getAbsolutePath());
            }

        } else if (requestCode == REQUEST_VIDEO_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                /** 数据库查询操作。
                 * 第一个参数 uri：为要查询的数据库+表的名称。
                 * 第二个参数 projection ： 要查询的列。
                 * 第三个参数 selection ： 查询的条件，相当于SQL where。
                 * 第三个参数 selectionArgs ： 查询条件的参数，相当于 ？。
                 * 第四个参数 sortOrder ： 结果排序。
                 */
                Cursor cursor = cr.query(uri, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        // 视频ID:MediaStore.Audio.Media._ID
                      //  int videoId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                        // 视频名称：MediaStore.Audio.Media.TITLE
                     //   String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                        // 视频路径：MediaStore.Audio.Media.DATA

                        // 视频大小：MediaStore.Audio.Media.SIZE
                        long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                        if (size>314572800){
                            Toast.makeText(this,"请您选择300M以内的视频",Toast.LENGTH_SHORT).show();
                            return;
                        }
                         String videoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                        createColumnParams.setVideoPath(videoPath);
                        // 视频时长：MediaStore.Audio.Media.DURATION
                        int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));



                        // 视频缩略图路径：MediaStore.Images.Media.DATA
                        String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                        // 缩略图ID:MediaStore.Audio.Media._ID
                        int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                        // 方法一 Thumbnails 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
                        // 第一个参数为 ContentResolver，第二个参数为视频缩略图ID， 第三个参数kind有两种为：MICRO_KIND和MINI_KIND 字面意思理解为微型和迷你两种缩略模式，前者分辨率更低一些。
                        Bitmap bitmap1 = MediaStore.Video.Thumbnails.getThumbnail(cr, imageId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
                        // 方法二 ThumbnailUtils 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
                        // 第一个参数为 视频/缩略图的位置，第二个依旧是分辨率相关的kind
                        Bitmap bitmap2 = ThumbnailUtils.createVideoThumbnail(imagePath, MediaStore.Video.Thumbnails.MICRO_KIND);

                        bannerVideo.setImageBitmap(bitmap2);

                    }
                    cursor.close();
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
    }}


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
                       handler.sendEmptyMessage(1);
                    }
                }
            },qiniuTokenDto1.getUploadToken());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ImageUtils.loadImagefile(tempFile,bannerView);
            createColumnParams.setCoverImgs(qiniuTokenDto1.getSaveFullPath());
        }
    };

}
