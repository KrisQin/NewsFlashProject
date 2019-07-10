package com.blockadm.adm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.bean.AwardDto;
import com.blockadm.common.bean.NewsFlashBeanDto;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
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
 * TODO 快讯图片预览
 */
public class NewsFlashShareDialog extends Dialog {


    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_save_local)
    TextView tvSaveLocal;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.share_icon)
    ImageView shareIcon;

    @BindView(R.id.share_icon2)
    ImageView shareIcon2;

    @BindView(R.id.share_icon3)
    ImageView shareIcon3;


    @BindView(R.id.sl)
    ScrollView svContent;
    @BindView(R.id.share_icon4)
    ImageView shareIcon4;
    @BindView(R.id.share_weibo)
    RelativeLayout shareWeibo;
    @BindView(R.id.view_share_weixin)
    RelativeLayout viewShareWeixin;
    @BindView(R.id.view_share_qq)
    RelativeLayout viewShareQq;
    @BindView(R.id.view_share_weixinfriend)
    RelativeLayout viewShareWeixinfriend;
    @BindView(R.id.iv_scan_image)
    ImageView ivScanImage;
    private Context context;
    private NewsFlashBeanDto.RecordsBean recordsBean;


    public NewsFlashShareDialog(final Context context, NewsFlashBeanDto.RecordsBean recordsBean) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        this.recordsBean = recordsBean;
        setContentView(R.layout.dialog_news_flash_share);
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
        tvTitle.setText(recordsBean.getTitle());
        tvContent.setText(recordsBean.getContent());
        tvTime.setText(recordsBean.getCreateTime());

        String scanUrl = (String) SharedpfTools.getInstance().get(ConstantUtils.RecommendUrl,"");
        if (StringUtils.isNotEmpty(scanUrl)) {
            Bitmap qrCodeBitmap = GeneralUtil.createImage(scanUrl,
                    ScreenUtils.dip2px(context,82), ScreenUtils.dip2px(context,82));
            ivScanImage.setImageBitmap(qrCodeBitmap);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.tv_cancel, R.id.tv_save_local, R.id.view_share_weixin,
            R.id.view_share_weixinfriend, R.id.view_share_qq, R.id.share_weibo})
    public void onViewClicked(View view) {

        int contentDrawableBgId = R.drawable.ic_news_flash_blue_bg;

        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_save_local:
                Bitmap bitmap = ImageUtils.scrollViewScreenShot(svContent, contentDrawableBgId);
                ImageUtils.saveBmp2Gallery(bitmap, "news_share" + System.currentTimeMillis());
                break;
            case R.id.view_share_weixin:

                Bitmap bitmap1 = ImageUtils.scrollViewScreenShot(svContent, contentDrawableBgId);
                //   String path =     ImageUtils.saveBmp2Gallery(bitmap1,"news_share"+System
                //   .currentTimeMillis());

                UMImage image = new UMImage((Activity) context, bitmap1);//本地文件
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withMedia(image)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_weixinfriend:
                Bitmap bitmap2 = ImageUtils.scrollViewScreenShot(svContent, contentDrawableBgId);
                // String path2 =  ImageUtils.saveBmp2Gallery(bitmap2,"news_share"+System
                // .currentTimeMillis());

                UMImage image2 = new UMImage((Activity) context, bitmap2);//本地文件
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withMedia(image2)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_qq:
                Bitmap bitmap3 = ImageUtils.scrollViewScreenShot(svContent, contentDrawableBgId);
                // String path3 =  ImageUtils.saveBmp2Gallery(bitmap3,"news_share"+System
                // .currentTimeMillis());

                UMImage image3 = new UMImage((Activity) context, bitmap3);//本地文件
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.QQ)
                        .withMedia(image3)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.share_weibo:
                Bitmap bitmap4 = ImageUtils.scrollViewScreenShot(svContent, contentDrawableBgId);
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
            dismiss();
            String tokenString =
                    (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
            if (!TextUtils.isEmpty(tokenString)) {
                CommonModel.addEncourage(101, recordsBean.getId(), new MyObserver<AwardDto>() {
                    @Override
                    public void onSuccess(BaseResponse<AwardDto> t) {
                        if (t.getCode() == 0) {
                            if (t.getData().getIsTrue() == 0) {
                                ShowPopupWindow(t.getData());
                            }
                        } else {
                            Toast.makeText(context, t.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }


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

    public interface ShareTypeListner {
        void onSaveClick(int type);
    }

    private PopupWindow mPopupWindow;
    private View popView;
    private TextView tvType;

    private void ShowPopupWindow(final AwardDto awardDto) {
        popView = LayoutInflater.from(context).inflate(R.layout.layout_share_award, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setContentView(popView);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // PopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, com.blockadm.adm
        // .R.drawable.popupwindow_background));
        mPopupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        tvType = (TextView) popView.findViewById(R.id.tv_type);//拍摄照片
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        mPopupWindow.setOutsideTouchable(false);  //设置点击屏幕其它地方弹出框消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        popView.setFocusable(false);
        mPopupWindow.showAtLocation(popView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPopupWindow.dismiss();
                tvType.setText("+" + awardDto.getAwardDiamond() + "A钻");
                mPopupWindow.showAtLocation(popView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
                        0, 0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPopupWindow.dismiss();

                    }
                }, 2000);


            }
        }, 2000);
    }


    Runnable r = new Runnable() {
        @Override
        public void run() {

        }
    };
    private Handler handler = new Handler();
    private ShareTypeListner shareTypeListner;

    public void setSexSaveListner(ShareTypeListner sexSaveListner) {
        this.shareTypeListner = sexSaveListner;
    }
}
