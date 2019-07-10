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
import android.widget.RelativeLayout;
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

public class CommonShareDialog extends Dialog{

    @BindView(R.id.share_weibo)
    RelativeLayout shareWeibo;
    @BindView(R.id.view_share_weixin)
    RelativeLayout viewShareWeixin;
    @BindView(R.id.view_share_qq)
    RelativeLayout viewShareQq;
    @BindView(R.id.view_share_weixinfriend)
    RelativeLayout viewShareWeixinfriend;


    @BindView(R.id.view_share_linkurl)
    RelativeLayout viewShareLink;
    @BindView(R.id.view_share_yaoqingma)
    RelativeLayout viewShareYanzhengma;
    private  Context context;
    private AllRecommendCodeDto allRecommendCodeDto;

    private   ClipboardManager  myClipboard;
    private RelativeLayout  llroot;
    private ScrollView svContent1;
    private  Bitmap bitmap1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CommonShareDialog(final Context context,
                             AllRecommendCodeDto allRecommendCodeDto, 
                             ClipboardManager  clipboardManager) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        this.allRecommendCodeDto = allRecommendCodeDto;
        this.myClipboard = clipboardManager;
        setContentView(R.layout.dialog_common_share);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(context)*0.9f);
        Window dialogWindow = getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.BOTTOM);



        initView();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() {
        llroot = (RelativeLayout) View.inflate(context,R.layout.dialog_yaoqing_code,null);
        ImageView ivCode = llroot.findViewById(R.id.iv_code);
        TextView tvCode = llroot.findViewById(R.id.tv_code);
        svContent1  =  llroot.findViewById(R.id.sv);
        if (context instanceof BountyHunterComActivity){
            svContent1.setBackgroundResource(R.mipmap.codeshare_bg_money);
        }
        String image =  allRecommendCodeDto.getRecommendImage();
        File tempFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
        boolean is =  Base64Utils.getInstance().base64ToFile(image,tempFile);
        if (is){
            Bitmap bitmap = ImageUtils.getLoacalBitmap(tempFile.getAbsolutePath());
            ivCode .setImageBitmap(bitmap); //设置Bitmap
        }
        tvCode.setText(allRecommendCodeDto.getRecommendCode());
        int screenHight = (ScreenUtils.getScreenHeight(context));
        int statusHeight = ScreenUtils.getStatusHeight(context);
        int dp =  DimenUtils.dip2px(context,100);
        int h =  screenHight-statusHeight- dp;
        bitmap1 = ImageUtils.createBitmap2(svContent1,ScreenUtils.getScreenWidth(context),h );
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.view_share_linkurl, R.id.view_share_yaoqingma, R.id.view_share_weixin, R.id.view_share_weixinfriend, R.id.view_share_qq, R.id.share_weibo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_share_linkurl:

                ClipData myClip = ClipData.newPlainText("text", allRecommendCodeDto.getRecommendUrl());
                myClipboard.setPrimaryClip(myClip);
                ToastUtils.showToast("已经复制到剪切板");
                dismiss();
                break;
            case R.id.view_share_yaoqingma:

                if (allRecommendCodeDto!=null){
                    YaoqingmaShareDialog yaoqingmaShareDialog = new YaoqingmaShareDialog(context,allRecommendCodeDto,myClipboard);
                    yaoqingmaShareDialog.show();
                }
                break;
            case R.id.view_share_weixin:
                UMImage image2 = new UMImage((Activity) context, bitmap1);
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withMedia(image2)
                        .setCallback(umShareListener)
                        .share();
                dismiss();
                break;
            case R.id.view_share_weixinfriend:
                UMImage image3 = new UMImage((Activity) context, bitmap1);
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withMedia(image3)
                        .setCallback(umShareListener)
                        .share();
                dismiss();
                break;
            case R.id.view_share_qq:
                UMImage image4 = new UMImage((Activity) context, bitmap1);
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withMedia(image4)
                        .setCallback(umShareListener)
                        .share();
                dismiss();
                break;
            case R.id.share_weibo:
                UMImage image5 = new UMImage((Activity) context, bitmap1);
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withMedia(image5)
                        .setCallback(umShareListener)
                        .share();
                dismiss();
                break;
        }
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
