package com.blockadm.adm.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blockadm.adm.Fragment.HotCommentFragment;
import com.blockadm.adm.Fragment.LessonSynopsisFragment1;
import com.blockadm.adm.Fragment.LessonsContentAudioFragment;
import com.blockadm.adm.MainApp;
import com.blockadm.adm.R;
import com.blockadm.adm.adapter.TabFragmentAdapter;
import com.blockadm.adm.contact.LessonDetailContract;
import com.blockadm.adm.dialog.DetailShareDialog;
import com.blockadm.adm.event.PleyerEvent;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.adm.model.LessonsDetailModel;
import com.blockadm.adm.persenter.LessonDetailPresenter;
import com.blockadm.adm.service.PlayService;
import com.blockadm.common.base.BaseActivity;
import com.blockadm.common.bean.LessonsSpecialColumnDto;
import com.blockadm.common.bean.NewsLessonsDetailDTO;
import com.blockadm.common.bean.PalyDetailDto;
import com.blockadm.common.bean.RecordsBean;
import com.blockadm.common.bean.UserInfoDto;
import com.blockadm.common.call.GetUserCallBack;
import com.blockadm.common.comstomview.MyTabLayout;
import com.blockadm.common.config.ARouterPathConfig;
import com.blockadm.common.config.ComConfig;
import com.blockadm.common.http.ApiService;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ACache;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.DimenUtils;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.ScreenUtils;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.StringUtils;
import com.blockadm.common.utils.T;
import com.blockadm.common.utils.ToastUtils;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * TODO 学习详情界面
 * Created by hp on 2019/1/22.
 */

public class ColumnOneDetailComActivity extends BaseActivity<LessonDetailPresenter,
        LessonsDetailModel> implements LessonDetailContract.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_shard)
    ImageView ivShard;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tl_tab)
    MyTabLayout tlTab;
    @BindView(R.id.vp)
    ViewPager vp;
    ImageView iv;


    @BindView(R.id.iv_pictrue)
    ImageView ivPictrue;


    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.cl_root)
    CoordinatorLayout clRoot;
    @BindView(R.id.ll_foot)
    LinearLayout ll_foot;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    @BindView(R.id.app_video_box)//D:\adm3\ADMshare\jjdxm-ijkplayer\src\main\res\layout
            // \simple_player_view_player.xml
            RelativeLayout rlVideoBox;
    @BindView(R.id.rl_nowbuy)
    RelativeLayout rlNowbuy;


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_try)
    TextView tvTry;

    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_price2)
    TextView tvPrice2;

    @BindView(R.id.tv_lesson_count)
    TextView tvLessonCount;
    @BindView(R.id.tv_liulan)
    TextView tvLiulan;

    @BindView(R.id.rl_author)
    LinearLayout rlAuthor;

    @BindView(R.id.civ_headimage_author)
    ImageView civHeadImageAuthor;


    @BindView(R.id.tv_author_info_type)
    TextView tvInfoType;


    @BindView(R.id.tv_author_res)
    TextView tvAuthorRes;


    @BindView(R.id.tv_attention)
    TextView tvAttention;


    private LessonSynopsisFragment1 lessonSynopsisFragment;
    private LessonsContentAudioFragment lessonsContentAudioFragment;
    private int id;
    private PowerManager.WakeLock wakeLock;
    private PlayerView player;
    //private LessonsContentPictrueFragment lessonsContentPictrueFragment;
    private RecordsBean recordsBeanSave;
    private HotCommentFragment hotCommentFragment;

    private boolean isFirstQueryDetail = true;
    private int mImageMaxWidth;

    public static final String className = "ColumnOneDetailComActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStatee) {
        super.onCreate(savedInstanceStatee);

        setContentView(R.layout.act_lesson_one_detail);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);

        id = getIntent().getIntExtra("id", -1);
        initView();

        click();

        //scrollView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private void click() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recordsBeans != null && recordsBeans.size() > 0 && MainApp.getPlayerRes() != null) {
                    for (int i = 0; i < recordsBeans.size(); i++) {
                        RecordsBean recordsBean = recordsBeans.get(i);
                        if (recordsBean.getId() == MainApp.getPlayerRes().getId()) {
                            recordsBeanSave = recordsBean;
                        }
                    }
                }

                if (recordsBeanSave != null) {
                    Log.i("audiocontentres", recordsBeanSave.getId() + "");
                    MainApp.setRecordsBean(recordsBeanSave);
                    MainApp.setPalyerId(-1);
                }
                finish();
            }
        });

        ivShard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lessonsDto != null) {
                    share();
                }
            }
        });

        ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lessonsDto != null) {
                    if (lessonsDto.getIsCollection() == 1) {
                        CommonModel.confirmOrCancel(lessonsDto.getId(), 1, 0, 0,
                                new MyObserver<String>() {
                                    @Override
                                    public void onSuccess(BaseResponse<String> baseResponse) {
                                        lessonsDto.setIsCollection(0);
                                        Drawable dra =
                                                getResources().getDrawable(R.mipmap.study_like_default_white);
                                        ivCollect.setBackground(dra);
                                    }


                                });
                    } else {
                        CommonModel.confirmOrCancel(lessonsDto.getId(), 1, 0, 1,
                                new MyObserver<String>() {
                                    @Override
                                    public void onSuccess(BaseResponse<String> baseResponse) {
                                        lessonsDto.setIsCollection(1);
                                        Drawable dra =
                                                getResources().getDrawable(R.mipmap.activity_like_press);
                                        ivCollect.setBackground(dra);
                                    }


                                });
                    }

                }
            }
        });
        civHeadImageAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lessonsDto != null) {
                    Intent intent = new Intent(ColumnOneDetailComActivity.this,
                            PersonnalIndexComActivity.class);
                    intent.putExtra(ConstantUtils.MENBERID, lessonsDto.getMemberId());
                    startActivity(intent);
                }
            }
        });

        tvAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lessonsDto != null) {
                    if (lessonsDto.getIsFollow() == 0) {
                        CommonModel.addUserFollow(lessonsDto.getMemberId(),
                                new MyObserver<String>() {
                                    @Override
                                    public void onSuccess(BaseResponse t) {
                                        lessonsDto.setIsFollow(1);
                                        tvAttention.setBackgroundResource(R.mipmap.attention_on);
                                    }


                                });
                    } else {
                        CommonModel.deleteUserFollow(lessonsDto.getMemberId(),
                                new MyObserver<String>() {
                                    @Override
                                    public void onSuccess(BaseResponse<String> baseResponse) {
                                        lessonsDto.setIsFollow(0);
                                        tvAttention.setBackgroundResource(R.mipmap.attention);
                                    }


                                });
                    }
                }
            }
        });

    }


    private void share() {
        DetailShareDialog detailShareDialog = new DetailShareDialog(this);
        detailShareDialog.setShareTypeListener(new DetailShareDialog.ShareTypeListener() {
            @Override
            public void shareType(int type) {
                UMImage image = new UMImage(ColumnOneDetailComActivity.this,
                        lessonsDto.getCoverImgs());//网络图片
                UserInfoDto userInfoDto =
                        (UserInfoDto) ACache.get(ColumnOneDetailComActivity.this).getAsObject(
                                "userInfoDto");
                //contentType=0&objectId=1&memberId=1
                int memberId = 0;
                if (userInfoDto != null) {
                    memberId = userInfoDto.getMemberId();
                }
                String clickPath =
                        ApiService.BASR_URL_RELEASE + "/news/visitor/share/shareUrl" +
                                "?contentType=3&objectId=" + lessonsDto.getId() + "&memberId=" + memberId;
                UMWeb web = new UMWeb(clickPath);
                web.setTitle(lessonsDto.getTitle());//标题
                web.setThumb(image);  //缩略图
                if (TextUtils.isEmpty(lessonsDto.getSubtitle())) {
                    web.setDescription("让价值的创造者拥有价值");//描述
                } else {
                    web.setDescription(lessonsDto.getSubtitle());//描述
                }


                switch (type) {
                    case 1:
                        new ShareAction(ColumnOneDetailComActivity.this).setPlatform(SHARE_MEDIA.WEIXIN).withMedia(web).setCallback(umShareListener).share();

                        break;
                    case 2:
                        new ShareAction(ColumnOneDetailComActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).withMedia(web).setCallback(umShareListener).share();
                        break;
                    case 3:
                        new ShareAction(ColumnOneDetailComActivity.this).setPlatform(SHARE_MEDIA.QQ).withMedia(web).setCallback(umShareListener).share();
                        break;
                    case 4:
                        new ShareAction(ColumnOneDetailComActivity.this).setPlatform(SHARE_MEDIA.SINA).withMedia(web).setCallback(umShareListener).share();
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
            Toast.makeText(ColumnOneDetailComActivity.this, "成功了", Toast.LENGTH_LONG).show();
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

    private void initView() {
        mImageMaxWidth = ScreenUtils.getScreenWidth(getApplicationContext());

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> tabNames = new ArrayList<>();
        lessonSynopsisFragment = new LessonSynopsisFragment1();
        fragments.add(lessonSynopsisFragment);
        tabNames.add("专栏简介");

        lessonsContentAudioFragment = new LessonsContentAudioFragment();
        fragments.add(lessonsContentAudioFragment);
        tabNames.add("课程列表");

        hotCommentFragment = new HotCommentFragment(id, appbar);
        fragments.add(hotCommentFragment);
        tabNames.add("评论热议");

        setViewpageAdapter(fragments, tabNames);

        mPersenter.findNewsLessonsSpecialColumnById(id, DimenUtils.px2dip(this, mImageMaxWidth));

    }




    private int dataType = -1;

    private void setViewpageAdapter(ArrayList<Fragment> fragments, ArrayList<String> tabNames) {
        try {
            TabFragmentAdapter tabFragmentAdapter =
                    new TabFragmentAdapter<Fragment>(getSupportFragmentManager(), fragments,
                            tabNames);
            if (vp.getAdapter() == null) {
                vp.setAdapter(tabFragmentAdapter);
                vp.setOffscreenPageLimit(fragments.size());
                tlTab.setupWithViewPager(vp);
                tlTab.setTabsFromPagerAdapter(tabFragmentAdapter);
            }
            if (tlTab == null) {
                return;
            }
            TextView title =
                    (TextView) (((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(0)).getChildAt(1));
            title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tlTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    vp.setCurrentItem(tab.getPosition());
                    TextView title =
                            (TextView) (((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                    title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    title.setText(tab.getText());
                    if (tab.getText().equals("课程列表")) {
                        dataType = 1;
                        mPersenter.pageNewsLessons(id + "", "1");
                    } else if (tab.getText().equals("评论热议")) {
                        dataType = 2;
                        mPersenter.pageNewsLessons(id + "", "2");
                    }
                /*    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            scrollView.scrollTo(0,0);
                        }
                    });*/


                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    vp.setCurrentItem(tab.getPosition());
                    TextView title =
                            (TextView) (((LinearLayout) ((LinearLayout) tlTab.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                    title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    title.setText(tab.getText());
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        } catch (Exception e) {

        }

        dataType = 1;
    }


    private LessonsSpecialColumnDto lessonsDto;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void showFindNewsLessonsSpecialColumnById(BaseResponse<LessonsSpecialColumnDto> data) {
        if (data.getCode() == 0) {
            this.lessonsDto = data.getData();

            L.d(" ----------------> isSeeStatus : "+lessonsDto.getIsSeeStatus());
            // 需要先获取lessonsDto信息，再更新课程列表
            queryPageNewsLessons();

            String iconPath = lessonsDto.getCoverImgs();
            tvTitle.setText(lessonsDto.getTitle());
            tvLessonCount.setText(lessonsDto.getLessonsCount() + "节");
            tvLiulan.setText(lessonsDto.getConvertReadCount() + "浏览");

            rlAuthor.setVisibility(View.VISIBLE);

            if (lessonsDto.getIsCollection() == 1) {
                Drawable dra = getResources().getDrawable(R.mipmap.activity_like_press);
                ivCollect.setBackground(dra);
            } else {
                Drawable dra = getResources().getDrawable(R.mipmap.study_like_default_white);
                ivCollect.setBackground(dra);
            }

            ImageUtils.loadImageView(lessonsDto.getIcon(), civHeadImageAuthor);


            switch (lessonsDto.getCredentialsState()) {
                case 0:
                    tvInfoType.setVisibility(View.GONE);
                    break;
                case 1:
                    tvInfoType.setVisibility(View.GONE);
                    break;
                case 2:

                    tvInfoType.setText("机构· ");
                    break;
                case 3:
                    tvInfoType.setText("机构·");
                    break;
            }
            tvAuthorRes.setText(lessonsDto.getNickName());

            // tvPrice.setText("￥" + money);
            // tvPriceVip.setText("￥" +  lessonsDto.getVipMoney());

            if (lessonsDto.getIsFollow() == 1) {
                tvAttention.setBackgroundResource(R.mipmap.attention_on);
            } else {
                tvAttention.setBackgroundResource(R.mipmap.attention);
            }


            if (lessonSynopsisFragment != null) {
                lessonSynopsisFragment.updateView(lessonsDto);
            }

            getUserData();

            if (!TextUtils.isEmpty(lessonsDto.getIntroduceVideoUrl())) {
                rlVideoBox.setVisibility(View.VISIBLE);
                ivPictrue.setVisibility(View.GONE);
                /**常亮*/
                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
                wakeLock.acquire();
                player =
                        new PlayerView(this, rlRoot).setScaleType(PlayStateParams.fillparent).forbidTouch(false)//是否禁止触摸
                                .hideSteam(true)//隐藏分辨率按钮
                                .hideRotation(true)//隐藏旋转按钮
                                .hideMenu(false)//隐藏菜单按钮
                                .hideCenterPlayer(true).hideBack(true) //隐藏返回按钮
                                .setProcessDurationOrientation(PlayStateParams.PROCESS_LANDSCAPE).showThumbnail(new OnShowThumbnailListener() {//设置缩略图
                            @Override
                            public void onShowThumbnail(ImageView ivThumbnail) {
                          /*   Glide.with(ivThumbnail.getContext())
                                     .load(ImageUtils.getFirstFrameDrawable(lessonsDto
                                     .getIntroduceVideoUrl()))
                                     .crossFade()
                                     .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                     .error(R.mipmap.picture_default)
                                     .into(ivThumbnail);*/
                                ivThumbnail.setImageBitmap(ImageUtils.getFirstFrameDrawable(lessonsDto.getIntroduceVideoUrl()));
                            }
                        }).setPlaySource(lessonsDto.getIntroduceVideoUrl());
            } else if (!TextUtils.isEmpty(iconPath)) {
                ImageUtils.loadImageView(iconPath, ivPictrue);
                ivPictrue.setVisibility(View.VISIBLE);
                rlVideoBox.setVisibility(View.GONE);
            }

        } else {
            ToastUtils.showToast(data.getMsg());
        }
    }

    /**
     * @param isShow true:展示bottom view
     */
    private void showBottomView(boolean isShow) {
        if (isShow) {
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) clRoot.getLayoutParams();
            layoutParams.bottomMargin = DimenUtils.dip2px(this, 60);
            clRoot.setLayoutParams(layoutParams);
            ll_foot.setVisibility(View.VISIBLE);
        } else {
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) clRoot.getLayoutParams();
            layoutParams.bottomMargin = DimenUtils.dip2px(this, 0);
            clRoot.setLayoutParams(layoutParams);
            ll_foot.setVisibility(View.GONE);
        }
    }

    /**
     * @param isTry true:可试听
     */
    private void canTry(boolean isTry) {
        if (isTry) {
            showBottomView(true);
            tvTry.setText("试听");
            tvTry.setVisibility(View.VISIBLE);
        } else {
            tvTry.setVisibility(View.GONE);
        }

    }

    private void getUserData() {
        CommonModel.getUserData(this,new GetUserCallBack() {
            @Override
            public void backUserInfo(UserInfoDto userInfo) {
                bottomViewShowData(userInfo.getVipState());
            }

            @Override
            public void error(int code, String msg) {

            }

        });
    }


    /**
     （1）、免费 ：  memberId【创建者】 = memberId 【登录账户】 或者    isSeeStatus = 0   【注释：课程列表全部免费】

     （2）、已购买 ： isSeeStatus = 2 【注释：课程列表全部可看、可听、可读】

     （3）、会员免费 ： isSeeStatus = 1 并且 vipMoney =0 并且  vipState  = 1 【注释：课程列表全部可看、可听、可读】

     （4）、几种付费情况 :
     1、isSeeStatus = 1 并且 vipMoney = 0  并且 vipState  = 0 【注释：全价购买 money 或 point】
     2、isSeeStatus = 1 并且 vipMoney >0  并且 vipState  = 0  【注释：全价购买 money 或 point】
     3、isSeeStatus = 1 并且 vipMoney >0  并且 vipState  = 1  【注释：会员价购买 vipMoney 或 vipPoint】
     4、isSeeStatus = 1 并且 未登录 【注释：全价购买 money 或 point】

     课程列表
     若  accessStatus   = 0  当前课程可看、可听、可读
     若  accessStatus   = 1  需购买

     * 地下价格栏的数据显示
     */
    private void bottomViewShowData(int vipState) {
        L.d("返回的数据 ： "+lessonsDto.toString());
        //全部免费
        if (lessonsDto.getIsSeeStatus() == 0 || lessonsDto.getIsSeeStatus() == 2
                || (lessonsDto.getIsSeeStatus() == 1
                && lessonsDto.getVipMoney() == 0 && vipState == 1)) {
            showBottomView(false);
        }
        //需要付费
        else {
            if (lessonsDto.getIsSeeStatus() == 1) {
                showBottomView(true);
                String token = (String) SharedpfTools.getInstance().get(ConstantUtils.TOKEN, "");
                //isSeeStatus = 1 ，未登录 或 vipState  = 0 ，全价购买 money 或 point
                if (TextUtils.isEmpty(token) || vipState == 0) {
                    tvPrice.setText("购买: " +StringUtils.getUnitAmount()+ lessonsDto.getMoney());
                }
                //会员价购买 vipMoney 或 vipPoint
                else if (lessonsDto.getIsSeeStatus() == 1
                        && vipState == 1 && lessonsDto.getVipMoney() > 0){
                    tvPrice.setText("会员: " + lessonsDto.getVipMoney() +"/");
                    tvPrice2.setText(StringUtils.getUnitAmount()+lessonsDto.getMoney());
                    tvPrice2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
                }

                if (lessonsDto.getDataType() == 1 || lessonsDto.getDataType() == 2) {
                    canTry(true);
                }else {
                    canTry(false);
                }
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isFirstQueryDetail) {
            mPersenter.findNewsLessonsSpecialColumnById(id, DimenUtils.px2dip(this, mImageMaxWidth));
        }

        isFirstQueryDetail = false;

        if (player != null) {
            player.onResume();
        }
        /**demo的内容，激活设备常亮状态*/
        if (wakeLock != null) {
            wakeLock.acquire();
        }


    }

    private void queryPageNewsLessons() {
        if (dataType == 1) {
            mPersenter.pageNewsLessons(id + "", "1");
        }else if (dataType == 2){
            mPersenter.pageNewsLessons(id + "", "2");
        }
    }

    //解绑服务
    private void unbindPlayService() {
        if (isBound == true) {
            isBound = false;
            unbindService(conn);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
        EventBus.getDefault().unregister(this);

        unbindPlayService();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            handleConfigurationChanged(1);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            handleConfigurationChanged(0);
        }
        super.onConfigurationChanged(newConfig);


    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
        /**demo的内容，恢复设备亮度状态*/
        if (wakeLock != null) {
            wakeLock.release();
        }
    }


    /**
     * 切换播放界面
     *
     * @param state 0 横屏播放 1竖屏播放
     */
    private void handleConfigurationChanged(int state) {
        switch (state) {
            case 0:
                ScreenUtils.isHidenStatus(true, this);
                if (player != null) {
                    player.setScaleType(PlayStateParams.fillparent).hideBack(false); //隐藏返回按钮
                }
                break;
            case 1:
                ScreenUtils.isHidenStatus(false, this);
                if (player != null) {
                    player.setScaleType(PlayStateParams.fillparent).hideBack(true); //隐藏返回按钮
                }
                break;
        }
    }

    @Override
    public void showFindNewsLessonsById(BaseResponse<NewsLessonsDetailDTO> data) {

    }

    private ArrayList<RecordsBean> recordsBeans;

    @Override
    public void showPageNewsLessonsById(BaseResponse<ArrayList<RecordsBean>> lessonsDTOBaseResponse) {
        if (lessonsDto != null && lessonsContentAudioFragment != null && dataType == 1) {
            recordsBeans = lessonsDTOBaseResponse.getData();
            lessonsContentAudioFragment.updateView(lessonsDTOBaseResponse,
                    lessonsDto.getIsSeeStatus());
        }
    }


    @OnClick({R.id.rl_nowbuy, R.id.tv_try})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_nowbuy:
                String token =
                        (String) SharedpfTools.getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN, "");
                if (TextUtils.isEmpty(token)) {
                    ContextUtils.showNoLoginDialog(this);
                }
                else {
                    Intent intent = new Intent(this, BuyDetailComActivity.class);
                    intent.putExtra(BuyDetailComActivity.LESSONS_DTO, lessonsDto);
                    startActivity(intent);
                }
                break;

            case R.id.tv_try:
                if (lessonsDto != null) {
                    if (lessonsDto.getDataType() == 1) {
                        Intent intent = new Intent(this, AudioContentComActivity.class);
                        intent.putExtra("isSeeStatus", lessonsDto.getIsSeeStatus());
                        intent.putExtra("id", lessonsDto.getNewsLessonsId());

                        startActivity(intent);
                    } else if (lessonsDto.getDataType() == 2) {
                        Intent intent = new Intent(this, PictrueContentComActivity.class);
                        intent.putExtra("isSeeStatus", lessonsDto.getIsSeeStatus());
                        intent.putExtra("id", lessonsDto.getNewsLessonsId());
                        startActivity(intent);
                    }

                }

                break;
        }


    }

    private boolean isBound = false;

    //绑定服务
    private void bindPlayService(String path) {
        if (isBound == false) {
            Intent intent = new Intent(this, PlayService.class);
            intent.putExtra("url", path);
            bindService(intent, conn, BIND_AUTO_CREATE);
            isBound = true;
        }
    }

    //连接Activity和Service
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayService.PlayBinder playBinder = (PlayService.PlayBinder) service;
            PlayService playService = playBinder.getPlayService();
            MainApp.setPlayService1(playService);
            MainApp.setServiceConnection(conn);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            MainApp.setPlayService1(null);
        }
    };


    public void playList(PalyDetailDto palyDetailDto) {

        MainApp.setPlayerRes(palyDetailDto);
        if (MainApp.getPlayService1() != null) {
            MainApp.getPlayService1().play(palyDetailDto.getAudioUrl());
        } else {
            try {
                bindPlayService(palyDetailDto.getAudioUrl());
            } catch (Exception e) {

            }

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(PleyerEvent messageEvent) {
        if (MainApp.getPlayService1() != null) {
            MainApp.getPlayService1().start();
        }
    }
}
