package com.blockadm.adm.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.blockadm.adm.R;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.NewsArticleListDTO;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.ScreenUtils;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2018/12/26.
 */
public class InfomationDetailActivty1 extends BaseComActivity {

    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    @Autowired
    NewsArticleListDTO.RecordsBean recordsBean;

    private PowerManager.WakeLock wakeLock;
    private PlayerView player;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_infomation);
        ButterKnife.bind(this);

        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();
        player = new PlayerView(this, rlRoot)
                .setScaleType(PlayStateParams.fillparent)
                .forbidTouch(false)//是否禁止触摸
                .hideSteam(true)//隐藏分辨率按钮
                .hideRotation(true)//隐藏旋转按钮
                .hideMenu(true)//隐藏菜单按钮
                .hideCenterPlayer(true)
                .hideBack(true) //隐藏返回按钮
                .setProcessDurationOrientation(PlayStateParams.PROCESS_LANDSCAPE)
                .showThumbnail(new OnShowThumbnailListener() {//设置缩略图
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        ivThumbnail.setImageBitmap(ImageUtils.getFirstFrameDrawable("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4"));
                    }
                })
                .setPlaySource("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4");

    /* <include
        layout="@layout/simple_player_view_player"
        android:layout_width="match_parent"
        android:layout_height="230dp"
                />*/
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
        if (player != null) {
            player.onResume();
        }
        /**demo的内容，激活设备常亮状态*/
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }

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
                ScreenUtils.isHidenStatus(true,this);
                if (player != null) {
                    player.setScaleType(PlayStateParams.fillparent)
                            .hideBack(false); //隐藏返回按钮
                }
                break;
            case 1:
                ScreenUtils.isHidenStatus(false,this);
                if (player != null) {
                    player.setScaleType(PlayStateParams.fillparent)
                            .hideBack(true); //隐藏返回按钮
                }
                break;
        }
    }
}
