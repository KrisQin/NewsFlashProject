package tv.danmaku.ijk.media.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import java.io.File;
import java.util.List;

import tv.danmaku.ijk.media.player.utils.Utils;
import tv.danmaku.ijk.media.player.ys100.IYsMediaControllerListener;
import tv.danmaku.ijk.media.player.ys100.YsTextureView;

/**
 * Created by zhaoguoliang on 2018/4/4.
 */

public class YsMediaViewType1 extends RelativeLayout implements IYsMediaControllerListener, SeekBar.OnSeekBarChangeListener,View.OnClickListener{

    public YsMediaViewType1(Context context) {
        super(context);
    }

    public YsMediaViewType1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YsMediaViewType1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ImageButton play ;
    private ImageButton zoom ;
    private TextView loading;
    private TextView txt_max_time;
    private TextView txt_current_time;
    private SeekBar seekBar ;
    private boolean isFullscreenFlag = false;
    // 全屏切换按钮
    private ImageButton btn_fullscreen;
    // 暂停时显示的大播放按钮
    private ImageButton btn_player_max;
    private ImageView audio_disk_page,image;
    // 暂停时，是否显示遮罩层标识位
    private boolean enableVideoMark;
    private boolean showBtnFullScreen = true;

    //遮罩层和大播放按钮的父布局
    private View video_mask_layout;
    private View videoPlayer_container;
    // 播放器控制条--在点击后判断是否显示控制条时使用
    private View videoControl;
    private boolean seekEnableFlag = false;

    private YsTextureView ysMediaView;
    private FullScreenListener fullScreenListener;
    public static final int FULL_SCREEN_ID = R.id.fullscreen;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        loadLayout();
        initView();
        setOnListener();
        initData();
    }
    protected void initView() {
        play = (ImageButton)findViewById(R.id.start);
        zoom = (ImageButton)findViewById(R.id.fullscreen);
        txt_max_time = (TextView)findViewById(R.id.txt_max_time);
        txt_current_time = (TextView)findViewById(R.id.txt_current_time);
        loading = (TextView)findViewById(R.id.audio_loading);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        ysMediaView = (YsTextureView)findViewById(R.id.my_texture_view);
        btn_player_max = (ImageButton) findViewById(R.id.btn_player_max);
        videoPlayer_container = findViewById(R.id.videoPlayer_container);
        audio_disk_page = (ImageView) findViewById(R.id.audio_disk_page);
        image = (ImageView) findViewById(R.id.image);
        videoControl = findViewById(R.id.videoControl);
        video_mask_layout = findViewById(R.id.video_mask_layout);
        btn_fullscreen = (ImageButton) findViewById(R.id.fullscreen);

    }
    private void initData() {
        setDrawingCacheEnabled(true);
        play.setClickable(false);
        ysMediaView.setClickable(false);
        btn_player_max.setClickable(false);
        loading.setVisibility(View.VISIBLE);
        video_mask_layout.setVisibility(VISIBLE);
    }
    public void setVisibility(int id ,int show){
        findViewById(id).setVisibility(show);
    }

    public void setOnFullScreenClickListener(FullScreenListener fullScreenListener) {
        this.fullScreenListener = fullScreenListener;
    }

    private void loadLayout() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.ys_textrueview, this, false);
        addView(view);
    }
    private void setOnListener() {
        ysMediaView.setIYsMediaController(this);
        seekBar.setOnSeekBarChangeListener(this);
        btn_fullscreen.setOnClickListener(this);
        btn_player_max.setOnClickListener(this);
        ysMediaView.setOnClickListener(this);
        play.setOnClickListener(this);
    }
    public void setPath(List<File> files) {
        ysMediaView.setPath(files);
    }
    public void setPath(File file) {
        ysMediaView.setPath(file);
    }
    public void setPath(String url) {
        ysMediaView.setPath(url);
    }

    @Override
    public void onReadyPlay() {
        image.setVisibility(VISIBLE);
    }

    @Override
    public void onPrepared(int duration) {
        play.setClickable(true);
        ysMediaView.setClickable(true);
        btn_player_max.setClickable(true);
        loading.setVisibility(View.GONE);
        txt_max_time.setText(Utils.timeFormat(duration));
        seekBar.setMax(duration);
    }

    @Override
    public void onUpdateSeekBar(int progress) {
        seekBar.setProgress(progress);
    }

    @Override
    public void onError(int what) {

    }

    @Override
    public void onStartBuffering() {
        if(ysMediaView.mediaStatus == YsTextureView.STARTED){
            loading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onEndBuffering() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onCompletion() {
        seekBar.setProgress(seekBar.getMax());
        video_mask_layout.setVisibility(VISIBLE);
        loading.setVisibility(View.GONE);
        play.setSelected(false);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        txt_current_time.setText(Utils.timeFormat(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    public void destroy(){
        if(null != ysMediaView){
            ysMediaView.destroy();
        }
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        ysMediaView.seekTo(seekBar);
    }

    @Override
    public void isPlay() {
        play.setSelected(true);
        video_mask_layout.setVisibility(GONE);
    }

    @Override
    public void isPause() {
        video_mask_layout.setVisibility(VISIBLE);
        play.setSelected(false);
    }

    @Override
    public void onSurfaceTextureUpdated() {
        loading.setVisibility(GONE);
        image.setVisibility(GONE);
    }

    @Override
    public void setFirstFrame4LocalVideo(Bitmap bitmap) {
        image.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fullscreen) {
            fullScreen();

        } else if (i == R.id.videoPlayer_container) {
            clickScreen();

        } else if (i == R.id.btn_player_max) {
            ysMediaView.play();

        } else if (i == R.id.start) {
            ysMediaView.play();

        } else if (i == R.id.my_texture_view) {
            ysMediaView.play();

        }
    }

    private void clickScreen() {
        if (isFullscreenFlag) {
            // 全屏状态下，控制点击
            if (videoControl.getVisibility() == View.GONE) {
                // 控制栏不可见时，点击显示
                videoControl.setVisibility(View.VISIBLE);
            } else {
                // 控制栏可见时，点击隐藏
                videoControl.setVisibility(View.GONE);
            }
        }
    }

    public void fullScreen(){
        if (isFullscreenFlag){
            btn_fullscreen.setSelected(false);
            isFullscreenFlag = false;
        }else{
            btn_fullscreen.setSelected(true);
            isFullscreenFlag = true;
        }
        if(null != fullScreenListener) {
            fullScreenListener.onFullscreenChanged(isFullscreenFlag);
        }
    }
    public void pause(){
        if(ysMediaView.isPlaying()) {
            ysMediaView.pause();
            play.setSelected(false);
        }
    }
}
