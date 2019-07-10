package com.blockadm.adm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
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
import com.blockadm.common.bean.ActivitysDetailDto;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.GeneralUtil;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.StringUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/19.
 * 生成图片预览
 */
public class ActivitysSharePicDialog extends Dialog {


    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_save_local)
    TextView tvSaveLocal;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_zhuban)
    TextView tvZhuban;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.v)
    View v;
    @BindView(R.id.share_icon)
    ImageView shareIcon;
    @BindView(R.id.view_share_weixin)
    RelativeLayout viewShareWeixin;
    @BindView(R.id.share_icon2)
    ImageView shareIcon2;
    @BindView(R.id.view_share_weixinfriend)
    RelativeLayout viewShareWeixinfriend;
    @BindView(R.id.share_icon3)
    ImageView shareIcon3;
    @BindView(R.id.view_share_qq)
    RelativeLayout viewShareQq;

    @BindView(R.id.sl)
    ScrollView svContent;
    @BindView(R.id.share_icon4)
    ImageView shareIcon4;
    @BindView(R.id.share_weibo)
    RelativeLayout shareWeibo;
    @BindView(R.id.iv_scan_image)
    ImageView ivScanImage;
    private Context context;
    private ActivitysDetailDto activitysDetailDto;


    public ActivitysSharePicDialog(final Context context, ActivitysDetailDto activitysDetailDto) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        this.activitysDetailDto = activitysDetailDto;
        setContentView(R.layout.dialog_activitys_share1);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = ScreenUtils.getScreenWidth(context);
        params.height = ScreenUtils.getScreenHeight(context) - ScreenUtils.getStatusHeight(context);
        Window dialogWindow = getWindow();
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.BOTTOM);
        initView();


    }


    private void initView() {
        tvTitle.setText(activitysDetailDto.getTitle());
        tvTime.setText(activitysDetailDto.getCreateTime());

        tvAddress.setText(activitysDetailDto.getAddress());
        tvZhuban.setText(activitysDetailDto.getSponsor());
        tvTime.setText(activitysDetailDto.getShowTime() + "\n" + activitysDetailDto.getStartToEndTime());


        String scanUrl = (String) SharedpfTools.getInstance().get(ConstantUtils.RecommendUrl,"");

        if (StringUtils.isNotEmpty(scanUrl)) {
            Bitmap qrCodeBitmap = GeneralUtil.createImage(scanUrl,
                    ScreenUtils.dip2px(context,60), ScreenUtils.dip2px(context,60));
            ivScanImage.setImageBitmap(qrCodeBitmap);
        }


        if (activitysDetailDto.getCreateType() == 1) {
            tvZhuban.setVisibility(View.VISIBLE);
        } else {
            tvZhuban.setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.tv_cancel, R.id.tv_save_local, R.id.view_share_weixin,
            R.id.view_share_weixinfriend, R.id.view_share_qq, R.id.share_weibo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_save_local:
                Bitmap bitmap = ImageUtils.scrollViewScreenShot(svContent, R.color.color_ffffff);
                ImageUtils.saveBmp2Gallery(bitmap, "news_share" + System.currentTimeMillis());
                break;
            case R.id.view_share_weixin:

                Bitmap bitmap1 = ImageUtils.scrollViewScreenShot(svContent, R.color.color_ffffff);
                //   String path =     ImageUtils.saveBmp2Gallery(bitmap1,"news_share"+System
                //   .currentTimeMillis());

                UMImage image = new UMImage((Activity) context, bitmap1);//本地文件
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withMedia(image)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_weixinfriend:
                Bitmap bitmap2 = ImageUtils.scrollViewScreenShot(svContent, R.color.color_ffffff);
                // String path2 =  ImageUtils.saveBmp2Gallery(bitmap2,"news_share"+System
                // .currentTimeMillis());

                UMImage image2 = new UMImage((Activity) context, bitmap2);//本地文件
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withMedia(image2)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_qq:
                Bitmap bitmap3 = ImageUtils.scrollViewScreenShot(svContent, R.color.color_ffffff);
                // String path3 =  ImageUtils.saveBmp2Gallery(bitmap3,"news_share"+System
                // .currentTimeMillis());

                UMImage image3 = new UMImage((Activity) context, bitmap3);//本地文件
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.QQ)
                        .withMedia(image3)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.share_weibo:
                Bitmap bitmap4 = ImageUtils.scrollViewScreenShot(svContent, R.color.color_ffffff);
                //  String path4 = ImageUtils.saveBmp2Gallery(bitmap4,"news_share"+System
                //  .currentTimeMillis());

                UMImage image4 = new UMImage((Activity) context, bitmap4);//本地文件
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.SINA)
                        .withMedia(image4)
                        .setCallback(umShareListener)
                        .share();
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
            Toast.makeText((Activity) context, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText((Activity) context, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText((Activity) context, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    public interface ShareTypeListner {
        void onSaveClick(int type);
    }


    private ShareTypeListner shareTypeListner;

    public void setSexSaveListner(ShareTypeListner sexSaveListner) {
        this.shareTypeListner = sexSaveListner;
    }
}
