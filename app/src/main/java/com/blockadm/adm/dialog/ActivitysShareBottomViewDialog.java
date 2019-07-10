package com.blockadm.adm.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
public class ActivitysShareBottomViewDialog extends Dialog {


 /*   @BindView(R.id.tv_cancel)
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
    RelativeLayout shareWeibo;*/
    private Context context;
    private ActivitysDetailDto activitysDetailDto;


    public ActivitysShareBottomViewDialog(final Context context, ActivitysDetailDto activitysDetailDto) {
        super(context, R.style.MyAlertDialog);
        this.context = context;
        this.activitysDetailDto = activitysDetailDto;
        setContentView(R.layout.dialog_activitys_share);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = ScreenUtils.getScreenWidth(context);
        Window dialogWindow = getWindow();
        dialogWindow.setAttributes(params);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        initView();


    }


    private void initView() {
     /*   tvTitle.setText(activitysDetailDto.getTitle());
        tvTime.setText(activitysDetailDto.getCreateTime());

        tvAddress.setText(activitysDetailDto.getAddress());
        tvZhuban.setText(activitysDetailDto.getSponsor());
        tvTime.setText(activitysDetailDto.getShowTime()+"\n"+activitysDetailDto.getStartToEndTime());


        if (activitysDetailDto.getCreateType()==1){
            tvZhuban.setVisibility(View.VISIBLE);
        }else{
            tvZhuban.setVisibility(View.GONE);
        }*/
    }

    @OnClick({ R.id.view_share_weixin, R.id.view_share_weixinfriend,
            R.id.view_share_qq, R.id.share_weibo,R.id.view_gerner_bitmap})
    public void onViewClicked(View view) {

        UMImage image = new UMImage(context, activitysDetailDto.getLogo());//网络图片
        UserInfoDto userInfoDto = (UserInfoDto) ACache.get(context).getAsObject("userInfoDto");
        //contentType=0&objectId=1&memberId=1
        int memberId =0;
        if (userInfoDto!=null){
            memberId = userInfoDto.getMemberId();
        }
        String clickPath = ApiService.BASR_URL_RELEASE+"/news/visitor/share/shareUrl?contentType=1&objectId="+activitysDetailDto.getId()+"&memberId="+memberId;
        UMWeb web = new UMWeb(clickPath);
        web.setTitle(activitysDetailDto.getTitle());//标题
        web.setThumb(image);  //缩略图
        web.setDescription("让价值的创造者拥有价值");//描述
        switch (view.getId()) {


            case R.id.view_gerner_bitmap:
                //当所有权限都允许之后，返回true
                ActivitysSharePicDialog newsFlashDialog = new ActivitysSharePicDialog(context,activitysDetailDto);
                newsFlashDialog.show();
                break;

            case R.id.view_share_weixin:

                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withMedia(web)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_weixinfriend:
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withMedia(web)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.view_share_qq:
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.QQ)
                        .withMedia(web)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.share_weibo:
                new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.SINA)
                        .withMedia(web)
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
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText((Activity) context,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText((Activity) context,"取消了",Toast.LENGTH_LONG).show();

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
