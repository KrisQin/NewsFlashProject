package com.blockadm.adm.im_module.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.blockadm.adm.dialog.DetailShareDialog;
import com.blockadm.adm.dialog.LiveShareDialog;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.http.ApiService;
import com.blockadm.common.im.call.ComCallback;
import com.blockadm.common.utils.ACache;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by Kris on 2019/5/8
 *
 * @Describe TODO {  }
 */
public class ShareDialogUtils {

    private static Activity mActivity;
    private static DetailShareDialog detailShareDialog;
    private static LiveShareDialog liveShareDialog;

    /**
     * 直播课程分享
     * @param activity
     * @param coverImgs
     * @param contentType
     * @param id
     * @param title
     * @param subtitle
     * @param callback
     */
    public static void shareLive(final Activity activity, final String coverImgs, final int contentType,
                             final String id, final String title, final String subtitle, final ComCallback callback) {

        mActivity = activity;
        liveShareDialog = new LiveShareDialog(activity);
        liveShareDialog.setShareTypeListener(new LiveShareDialog.ShareTypeListener() {
            @Override
            public void shareType(int type) {
                UMImage image = new UMImage(activity,coverImgs);//网络图片
                UserInfoDto userInfoDto =
                        (UserInfoDto) ACache.get(activity).getAsObject(
                                "userInfoDto");
                //contentType=0&objectId=1&memberId=1
                int memberId = 0;
                if (userInfoDto != null) {
                    memberId = userInfoDto.getMemberId();
                }
                String clickPath =
                        ApiService.BASR_URL_RELEASE + "/news/visitor/share/shareUrl" +
                                "?contentType="+contentType+"&objectId=" + id + "&memberId=" + memberId;
                UMWeb web = new UMWeb(clickPath);
                web.setTitle(title);//标题
                web.setThumb(image);  //缩略图
                if (TextUtils.isEmpty(subtitle)) {
                    web.setDescription("让价值的创造者拥有价值");//描述
                } else {
                    web.setDescription(subtitle);//描述
                }


                switch (type) {
                    case 1:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN).withMedia(web).setCallback(umShareListener).share();

                        break;
                    case 2:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).withMedia(web).setCallback(umShareListener).share();
                        break;
                    case 3:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.QQ).withMedia(web).setCallback(umShareListener).share();
                        break;
                    case 4:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.SINA).withMedia(web).setCallback(umShareListener).share();
                        break;
                    case 5:
                        if (callback != null) {
                            callback.callback();
                            liveShareDialog.dismiss();
                        }
                        break;
                }

            }
        });
        liveShareDialog.show();
    }

    /**
     *
     * @param activity
     * @param coverImgs
     * @param title
     * @param subtitle
     */
    public static void shareLive2(final Activity activity, final String coverImgs,
                                 final String id, final String title, final String subtitle, final ComCallback callback) {

        //        final String coverImgs = lessonsDto.getCoverImgs();
        //        final String id = lessonsDto.getId();
        //        final String title = lessonsDto.getTitle();
        //        final String subtitle = lessonsDto.getSubtitle();

        mActivity = activity;
        DetailShareDialog detailShareDialog = new DetailShareDialog(activity);
        detailShareDialog.setShareTypeListener(new DetailShareDialog.ShareTypeListener() {
            @Override
            public void shareType(int type) {
                UMImage image = new UMImage(activity,coverImgs);//网络图片
                UserInfoDto userInfoDto =
                        (UserInfoDto) ACache.get(activity).getAsObject(
                                "userInfoDto");
                //contentType=0&objectId=1&memberId=1
                int memberId = 0;
                if (userInfoDto != null) {
                    memberId = userInfoDto.getMemberId();
                }
                String clickPath =
                        ApiService.BASR_URL_RELEASE + "/news/visitor/share/shareUrl" +
                                "?contentType=3&objectId=" + id + "&memberId=" + memberId;
                UMWeb web = new UMWeb(clickPath);
                web.setTitle(title);//标题
                web.setThumb(image);  //缩略图
                if (TextUtils.isEmpty(subtitle)) {
                    web.setDescription("让价值的创造者拥有价值");//描述
                } else {
                    web.setDescription(subtitle);//描述
                }


                switch (type) {
                    case 1:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN).withMedia(web).setCallback(umShareListener).share();

                        break;
                    case 2:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).withMedia(web).setCallback(umShareListener).share();
                        break;
                    case 3:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.QQ).withMedia(web).setCallback(umShareListener).share();
                        break;
                    case 4:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.SINA).withMedia(web).setCallback(umShareListener).share();
                        break;
                    case 5:
                        if (callback != null) {
                            callback.callback();
                        }
                        break;
                }

            }
        });
        detailShareDialog.show();
    }



    /**
     * 直播管理员分享
     * @param activity
     * @param coverImgs
     * @param title
     * @param subtitle
     * @param shareUrl
     */
    public static void shareManage(final Activity activity, final String coverImgs,
                                   final String title,final String subtitle,final String shareUrl) {

        //        final String coverImgs = lessonsDto.getCoverImgs();
        //        final String id = lessonsDto.getId();
        //        final String title = lessonsDto.getTitle();
        //        final String subtitle = lessonsDto.getSubtitle();

        mActivity = activity;
        detailShareDialog = new DetailShareDialog(activity);
        detailShareDialog.setShareTypeListener(new DetailShareDialog.ShareTypeListener() {
            @Override
            public void shareType(int type) {
                UMImage image = new UMImage(activity,coverImgs);//网络图片
                UMWeb web = new UMWeb(shareUrl);
                web.setTitle(title);//标题
                web.setThumb(image);  //缩略图
                if (TextUtils.isEmpty(subtitle)) {
                    web.setDescription("点击绑定成为直播管理员");//描述
                } else {
                    web.setDescription(subtitle);//描述
                }


                switch (type) {
                    case 1:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN).withMedia(web).setCallback(umShareListener).share();

                        break;
                    case 2:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).withMedia(web).setCallback(umShareListener).share();
                        break;
                    case 3:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.QQ).withMedia(web).setCallback(umShareListener).share();
                        break;
                    case 4:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.SINA).withMedia(web).setCallback(umShareListener).share();
                        break;
                }

            }
        });
        detailShareDialog.show();
    }



    public static void share(final Activity activity, final String coverImgs, final int contentType,
                             final String id, final String title, final String subtitle) {

        mActivity = activity;
        detailShareDialog = new DetailShareDialog(activity);
        detailShareDialog.setShareTypeListener(new DetailShareDialog.ShareTypeListener() {
            @Override
            public void shareType(int type) {
                UMImage image = new UMImage(activity,coverImgs);//网络图片
                UserInfoDto userInfoDto =
                        (UserInfoDto) ACache.get(activity).getAsObject(
                                "userInfoDto");
                //contentType=0&objectId=1&memberId=1
                int memberId = 0;
                if (userInfoDto != null) {
                    memberId = userInfoDto.getMemberId();
                }
                String clickPath =
                        ApiService.BASR_URL_RELEASE + "/news/visitor/share/shareUrl" +
                                "?contentType="+contentType+"&objectId=" + id + "&memberId=" + memberId;
                UMWeb web = new UMWeb(clickPath);
                web.setTitle(title);//标题
                web.setThumb(image);  //缩略图
                if (TextUtils.isEmpty(subtitle)) {
                    web.setDescription("让价值的创造者拥有价值");//描述
                } else {
                    web.setDescription(subtitle);//描述
                }


                switch (type) {
                    case 1:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN).withMedia(web).setCallback(umShareListener).share();

                        break;
                    case 2:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).withMedia(web).setCallback(umShareListener).share();
                        break;
                    case 3:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.QQ).withMedia(web).setCallback(umShareListener).share();
                        break;
                    case 4:
                        new ShareAction(activity).setPlatform(SHARE_MEDIA.SINA).withMedia(web).setCallback(umShareListener).share();
                        break;
                }

            }
        });
        detailShareDialog.show();
    }

    private static UMShareListener umShareListener = new UMShareListener() {
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
            if (mActivity != null)
                Toast.makeText(mActivity, "成功了", Toast.LENGTH_LONG).show();

            if (detailShareDialog != null) {
                detailShareDialog.dismiss();
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
}
