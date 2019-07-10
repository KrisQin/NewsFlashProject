package com.blockadm.adm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blockadm.adm.R;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.bean.live.responseBean.LiveDetailInfo;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.http.ApiService;
import com.blockadm.common.im.call.ComCallback;
import com.blockadm.common.im.call.TCallback;
import com.blockadm.common.utils.GeneralUtil;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.umeng.socialize.media.UMImage;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kris on 2019/6/24
 *
 * @Describe TODO {  }
 */
public class LiveLessonsPicDialog extends Dialog {


    @BindView(R.id.iv_head_image)
    CircleImageView ivHeadImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.layout_save)
    RelativeLayout layoutSave;
    @BindView(R.id.layout_share)
    RelativeLayout layoutShare;
    @BindView(R.id.sl)
    ScrollView svContent;

    private Context context;
    private TCallback<UMImage> mCallback;
    private UserInfoDto userInfoDto;
    private LiveDetailInfo mLiveDetailInfo;

    private void initView() {

        try {
            if (mLiveDetailInfo != null) {
                tvTitle.setText(mLiveDetailInfo.getTitle());
                Glide.with(context).load(mLiveDetailInfo.getCoverImgs()).error(R.mipmap.picture_default)
                        .transform(new ImageUtils.GlideRoundTransform(context, 4)).into(ivPic);
            }
            if (userInfoDto != null) {
                tvName.setText(userInfoDto.getNickName());
                Glide.with(context).load(userInfoDto.getIcon()).error(R.mipmap.picture_default).into(ivHeadImage);

                int memberId = 0;
                if (userInfoDto != null) {
                    memberId = userInfoDto.getMemberId();
                }

                if (mLiveDetailInfo != null) {
                    String scanUrl =
                            ApiService.BASR_URL_RELEASE + "/news/visitor/share/shareUrl" +
                                    "?contentType=4&objectId=" + mLiveDetailInfo.getId() +
                                    "&memberId=" + memberId;

                    Bitmap qrCodeBitmap = GeneralUtil.createImage(scanUrl,
                            ScreenUtils.dip2px(context, 120), ScreenUtils.dip2px(context, 120));
                    ivScan.setImageBitmap(qrCodeBitmap);

                }

            }

            String time = TimeUtils.getCurrentTimeInString(TimeUtils.DATE_FORMAT_DATE)
                    + " " + TimeUtils.getWeekString(new Date()) + " " + TimeUtils.getCurrentTimeInString(TimeUtils.DATE_FORMAT_ONLY_TIME);
            tvTime.setText(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveLessonsPicDialog(final Context context, UserInfoDto userInfoDto,
                                LiveDetailInfo liveDetailInfo, TCallback<UMImage> callback) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        this.mCallback = callback;
        this.userInfoDto = userInfoDto;
        this.mLiveDetailInfo = liveDetailInfo;
        setContentView(R.layout.dialog_live_lessons_pic);
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


    @OnClick({R.id.layout_save, R.id.layout_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_save:
                Bitmap bitmap = ImageUtils.scrollViewScreenShot(svContent,
                        R.drawable.ic_live_lessons_bg);
                ImageUtils.saveBmp2Gallery(bitmap, "news_share" + System.currentTimeMillis());
                break;
            case R.id.layout_share:
                Bitmap bitmap1 = ImageUtils.scrollViewScreenShot(svContent,
                        R.drawable.ic_live_lessons_bg);
                UMImage image = new UMImage((Activity) context, bitmap1);//本地文件
                if (mCallback != null) {
                    mCallback.callback(image);
                }
                break;
        }
    }
}
