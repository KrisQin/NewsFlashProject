package com.blockadm.adm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.common.bean.ActivitysDetailDto;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.http.ApiService;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.ScreenUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/19.
 */
public class LiveLessonsShareDialog extends Dialog {


    private Context context;

    private UMImage image;

    public LiveLessonsShareDialog(final Context context, UMImage image) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        this.image = image;
        setContentView(R.layout.dialog_live_lessons_share);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = ScreenUtils.getScreenWidth(context);
        Window dialogWindow = getWindow();
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);


    }



    @OnClick({ R.id.view_share_weixin, R.id.view_share_weixinfriend,
            R.id.view_share_qq, R.id.share_weibo})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.view_share_weixin:

                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withMedia(image)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_weixinfriend:
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withMedia(image)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_qq:
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.QQ)
                        .withMedia(image)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.share_weibo:
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.SINA)
                        .withMedia(image)
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
            Toast.makeText((Activity) context,"成功了",Toast.LENGTH_LONG).show();
            LiveLessonsShareDialog.this.dismiss();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText((Activity) context,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
            LiveLessonsShareDialog.this.dismiss();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText((Activity) context,"取消了",Toast.LENGTH_LONG).show();
            LiveLessonsShareDialog.this.dismiss();

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
