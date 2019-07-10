package com.blockadm.adm.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blockadm.adm.R;
import com.blockadm.adm.event.UserDataEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.JsonBean;
import com.blockadm.common.bean.QiniuTokenParams;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.UserParams;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.dialog.SexDialog;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.NetworkUtils;
import com.blockadm.common.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by hp on 2019/1/30.
 */

public class UpdateMyActitivity extends BaseComActivity {


    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.civ_headimage)
    CircleImageView civHeadimage;
    @BindView(R.id.tv_nickName)
    TextView tvNickName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_sign)
    TextView tvSign;

    private UserInfoDto userInfoDto;
    private TimePickerView pvTime;
    private SexDialog sexDialog;
    private OptionsPickerView pvOptions;
    private QiniuTokenParams qiniuTokenDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_update_my);

        Button bt_test = findViewById(R.id.bt_test);

        bt_test.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    L.d("xxx"," MotionEvent.ACTION_DOWN --- ");

                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    L.d("xxx"," MotionEvent.ACTION_MOVE --- ");

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    L.d("xxx"," MotionEvent.ACTION_UP --- ");
                }
                return false;
            }
        });

        ButterKnife.bind(this);
        initTimePicker();
        tilte.setOnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getUserData();
    }

    private void getUserData() {
        showDefaultLoadingDialog();
        CommonModel.getUserData(this,new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfo) {
                dismissLoadingDialog();
                userInfoDto = userInfo;
                if (userInfoDto != null) {
                    ImageUtils.loadImageView(userInfoDto.getIcon(),civHeadimage);
                    tvNickName.setText(userInfoDto.getNickName());
                    tvAge.setText(userInfoDto.getAge()+"");
                    tvArea.setText(userInfoDto.getArea());
                    tvSex.setText(userInfoDto.getSex()==1?"男":"女");
                    tvSign.setText(userInfoDto.getSign());
                    initJsonData();
                }
            }

            @Override
            public void error(int code, String msg) {
                dismissLoadingDialog();
            }

        });
    }

    @OnClick({R.id.civ_headimage, R.id.tv_nickName, R.id.tv_sex, R.id.tv_age, R.id.tv_area, R.id.tv_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_headimage:
                ShowPopupWindow();

                break;
            case R.id.tv_nickName:
                Intent intent = new Intent(this, EditNickNameComActivity.class);
                startActivityForResult(intent, ConstantUtils.REQUEST_CODE_NICKNAEM);
                break;
            case R.id.tv_sex:
                if (sexDialog==null){
                    sexDialog = new SexDialog(this,userInfoDto.getSex());
                }
                sexDialog.setSexSaveListner(new SexDialog.SexSaveListner() {
                    @Override
                    public void onSaveClick(final String textType, final int   type) {
                        UserParams userParams  = new UserParams();
                        userParams.setSex(type);
                        CommonModel.updateUserMember(GsonUtil.GsonString(userParams), new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                ToastUtils.showToast(t.getMsg());
                                if (t.getCode()==0){
                                    tvSex.setText(textType);
                                    EventBus.getDefault().post(new UserDataEvent());
                                    sexDialog.dismiss();
                                }

                            }

                        });
                    }
                });
                sexDialog.show();

                break;
            case R.id.tv_age:
                pvTime.show();

                break;
            case R.id.tv_area:
                showPickerView();
                break;
            case R.id.tv_sign:
                Intent intent5 = new Intent(this, EditSignComActivity.class);
                startActivityForResult(intent5, ConstantUtils.REQUEST_CODE_SIGN);
                break;
        }
    }



    private void initTimePicker() { //Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(final Date date, View v) {
                UserParams userParams  = new UserParams();
                userParams.setBirthday( getTime(date));
                CommonModel.updateUserMember(GsonUtil.GsonString(userParams), new MyObserver<String>() {
                    @Override
                    public void onSuccess(BaseResponse<String> t) {
                        ToastUtils.showToast(t.getMsg());
                        if (t.getCode()==0){

                            CommonModel.getUserData(UpdateMyActitivity.this,new GetUserCallBack() {
                                @Override
                                public void backUserInfo(UserInfoDto userInfo) {
                                    userInfoDto = userInfo;
                                    tvAge.setText( userInfoDto.getAge());
                                }

                                @Override
                                public void error(int code, String msg) {

                                }

                            });

                            pvTime.dismiss();
                        }

                    }

                });

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
    }
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData =  GsonUtil.getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }


    }

    private void showPickerView() {// 弹出选择器

        //返回的分别是三个级别的选中位置
/* String opt3tx = options2Items.size() > 0
         && options3Items.get(options1).size() > 0
         && options3Items.get(options1).get(options2).size() > 0 ?
         options3Items.get(options1).get(options2).get(options3) : "";*///设置选中项文字颜色
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

               /* String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";*/

                final String tx = opt1tx + opt2tx ;

                UserParams userParams  = new UserParams();
                userParams.setArea( tx);
                CommonModel.updateUserMember(GsonUtil.GsonString(userParams), new MyObserver<String>() {
                    @Override
                    public void onSuccess(BaseResponse<String> t) {
                        ToastUtils.showToast(t.getMsg());
                        if (t.getCode()==0){
                            EventBus.getDefault().post(new UserDataEvent());
                            tvArea.setText(tx);
                            pvOptions.dismiss();
                        }

                    }

                });
                Toast.makeText(UpdateMyActitivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        //pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        //pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sexDialog=null;
    }
    private PopupWindow mPopupWindow;
    private void ShowPopupWindow() {
        View popView = LayoutInflater.from(UpdateMyActitivity.this).inflate(R.layout.private_show_update_personal_pop, null);
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
                RxPermissions rxPermissions = new RxPermissions(UpdateMyActitivity.this);
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


    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "photo.jpg";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private Uri userImageUri;

    private void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 判断存储卡是否可以用，可用进行存储
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(UpdateMyActitivity.this,
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
   // private String photo;
    private String iconUrl;
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
        if (requestCode ==  ConstantUtils.REQUEST_CODE_NICKNAEM&&resultCode==RESULT_OK&& !TextUtils.isEmpty(data.getStringExtra("nickname"))){
            tvNickName.setText(data.getStringExtra("nickname"));
            EventBus.getDefault().post(new UserDataEvent());
        }

        if( requestCode ==  ConstantUtils.REQUEST_CODE_SIGN &&resultCode==RESULT_OK&& !TextUtils.isEmpty(data.getStringExtra("sign"))){

            UserParams userParams  = new UserParams();
            userParams.setSign( data.getStringExtra("sign"));
            CommonModel.updateUserMember(GsonUtil.GsonString(userParams), new MyObserver<String>() {
                @Override
                public void onSuccess(BaseResponse<String> t) {
                    ToastUtils.showToast(t.getMsg());
                    if (t.getCode()==0){
                        tvSign.setText(data.getStringExtra("sign"));
                        EventBus.getDefault().post(new UserDataEvent());
                    }

                }

            });

        }

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
                    crop(FileProvider.getUriForFile(UpdateMyActitivity.this, "com.adm.fileprovider_app", tempFile));
                } else {
                    crop(Uri.fromFile(tempFile));
                }
            } else {
                ToastUtils.showToast("未找到存储卡，无法存储照片！");
            }


        } else if (requestCode == PHOTO_REQUEST_CUT && resultCode != 0) {
            try {
                if (NetworkUtils.checkNetworkState(UpdateMyActitivity.this)) {
                    bitmap = data.getParcelableExtra("data");
                    if (bitmap!=null){
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bytes = baos.toByteArray();
                        Glide.with(UpdateMyActitivity.this).load(bytes).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(civHeadimage);
                        if (tempFile!=null){
                            updateImage(tempFile.getAbsolutePath());
                        }else {
                            updateImage(userImageUri.getPath());
                        }
                    }else  if (userImageUri!=null){

                        Bitmap  bitmap =  getNativeImage(userImageUri.getPath());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bytes = baos.toByteArray();
                        Glide.with(UpdateMyActitivity.this).load(bytes).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(civHeadimage);
                        /**
                         * 上传头像
                         */
                       updateImage(userImageUri.getPath());
                    }
                } else {
                    ToastUtils.showToast("网络或服务器异常");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PHOTO_REQUEST_CUT && resultCode == 0) {
            Glide.with(UpdateMyActitivity.this).load(iconUrl).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(civHeadimage);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateImage(String path){
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
                        UserParams userParams  = new UserParams();
                        userParams.setIcon( qiniuTokenDto.getSaveFullPath());
                        CommonModel.updateUserMember(GsonUtil.GsonString(userParams), new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                ToastUtils.showToast(t.getMsg());
                                if (t.getCode()==0){
                                    EventBus.getDefault().post(new UserDataEvent());
                                }
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


        startActivityForResult(intent, PHOTO_REQUEST_CUT);
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
