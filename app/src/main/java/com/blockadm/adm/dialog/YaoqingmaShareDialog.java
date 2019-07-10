package com.blockadm.adm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.activity.BountyHunterComActivity;
import com.blockadm.common.bean.AllRecommendCodeDto;
import com.blockadm.common.utils.Base64Utils;
import com.blockadm.common.utils.DimenUtils;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2019/3/8.
 */

public class YaoqingmaShareDialog extends Dialog{

    private  Context context;
    private AllRecommendCodeDto allRecommendCodeDto;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.tv_code)
    TextView tvCode;

    private LinearLayout  llroot;
    @BindView(R.id.sv)
     ScrollView svContent;
    private ClipboardManager  myClipboard;
    private Bitmap bitmap11;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public YaoqingmaShareDialog(final Context context, AllRecommendCodeDto allRecommendCodeDto,ClipboardManager  myClipboard) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        this.allRecommendCodeDto = allRecommendCodeDto;
        this.myClipboard = myClipboard;

        setContentView(R.layout.dialog_yaoqing_code);
        ButterKnife.bind(this);
        if (context instanceof BountyHunterComActivity){
            svContent.setBackgroundResource(R.mipmap.codeshare_bg_money);
        }
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(context));
        params.height = ScreenUtils.getScreenHeight(context)-ScreenUtils.getStatusHeight(context);
        Window dialogWindow = getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.BOTTOM);
        String image =  allRecommendCodeDto.getRecommendImage();
        File tempFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
        Base64Utils.getInstance().base64ToFile(image,tempFile);
        if (Base64Utils.getInstance().base64ToFile(image,tempFile)){
            ImageUtils.loadImagefile(tempFile,ivCode);
        }
        tvCode.setText(allRecommendCodeDto.getRecommendCode());


        initView();

    }

    private void initView() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.tv_cancel, R.id.tv_copy, R.id.iv_save, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_copy:
                ClipData myClip = ClipData.newPlainText("text", allRecommendCodeDto.getRecommendCode());
                myClipboard.setPrimaryClip(myClip);
                ToastUtils.showToast("已经复制到剪切板");

                break;
            case R.id.iv_save:
                int screenHight = (ScreenUtils.getScreenHeight(context));
                int statusHeight = ScreenUtils.getStatusHeight(context);
                int dp =  DimenUtils.dip2px(context,100);
                int h =  screenHight-statusHeight- dp;
              Bitmap  bitmap = ImageUtils.createBitmap2(svContent,ScreenUtils.getScreenWidth(context),h );
              //  Bitmap  bitmap  = ImageUtils.scrollViewScreenShot(svContent,R.mipmap.codeshare_bg_friends);
                ImageUtils.saveBmp2Gallery(bitmap,"level_share"+System.currentTimeMillis());
                break;
            case R.id.iv_share:

                share();


                break;

        }
    }

    private void share() {
        int screenHight = (ScreenUtils.getScreenHeight(context));
        int statusHeight = ScreenUtils.getStatusHeight(context);
        int dp =  DimenUtils.dip2px(context,100);
        int h =  screenHight-statusHeight- dp;
        bitmap11 = ImageUtils.createBitmap2(svContent,ScreenUtils.getScreenWidth(context),h );
        DetailShareDialog  detailShareDialog  = new DetailShareDialog(context);
        detailShareDialog.setShareTypeListener(new DetailShareDialog.ShareTypeListener() {
            @Override
            public void shareType(int type) {
                UMImage image2 = new UMImage((Activity) context, bitmap11);
                switch (type){
                    case 1:

                        new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN)
                                .withMedia(image2)
                                .setCallback(umShareListener)
                                .share();
                        dismiss();

                        break;
                    case 2:
                        new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                .withMedia(image2)
                                .setCallback(umShareListener)
                                .share();
                        dismiss();
                        break;
                    case 3:
                        new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.QQ)
                                .withMedia(image2)
                                .setCallback(umShareListener)
                                .share();
                        dismiss();
                        break;
                    case 4:
                        new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.SINA)
                                .withMedia(image2)
                                .setCallback(umShareListener)
                                .share();
                        dismiss();
                        break;
                }

            }
        });
        detailShareDialog.show();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText((Activity) context,"成功了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };


}
