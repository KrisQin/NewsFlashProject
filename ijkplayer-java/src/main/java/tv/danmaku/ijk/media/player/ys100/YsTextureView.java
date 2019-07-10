package tv.danmaku.ijk.media.player.ys100;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.utils.Utils;

/**
 * Created by zhaoguoliang on 2018/3/23.
 */

public class YsTextureView extends TextureView implements IjkMediaPlayer.OnInfoListener,//
        IjkMediaPlayer.OnPreparedListener,//
        IjkMediaPlayer.OnErrorListener, TextureView.SurfaceTextureListener,//
        IjkMediaPlayer.OnCompletionListener, IjkMediaPlayer.OnVideoSizeChangedListener {
    private IjkMediaPlayer ijkMediaPlayer;
    private Surface surface;
    private int mediaNum = 0;
    private List<File> files;
    private File file;
    private Context context;
    private SurfaceTexture surfaceTexture;
    private IYsMediaControllerListener iYsMediaControllerListener;
    public final static int IDLE = 0;
    public final static int STARTED = 1;
    public final static int PAUSED = 2;
    public final static int PREPARED = 3;
    public int mediaStatus = 0;
    private long position = 0;

    public YsTextureView(Context context) {
        super(context);
    }

    public YsTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YsTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIYsMediaController(IYsMediaControllerListener iYsMediaControllerListener) {
        this.iYsMediaControllerListener = iYsMediaControllerListener;
    }

    private void loadLibrary() {
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        loadLibrary();
        initView();
        onListener();
    }

    private void initView() {
        context = getContext();
        ijkMediaPlayer = new IjkMediaPlayer();
    }

    private void optionMediaPlayer() {
        //以下配置是经过多次调试的，请谨慎修改
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
        ijkMediaPlayer.setScreenOnWhilePlaying(true);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec_avc", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "min-frames", 500);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 10);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-fps", 30);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);
    }

    private void onListener() {
        setSurfaceTextureListener(this);
        setIjkMediaPlayerListener();
    }

    public void pause() {
        ijkMediaPlayer.pause();
        iYsMediaControllerListener.isPause();
    }

    private void setIjkMediaPlayerListener() {
        optionMediaPlayer();
        ijkMediaPlayer.setOnPreparedListener(this);
        ijkMediaPlayer.setOnErrorListener(this);
        ijkMediaPlayer.setOnInfoListener(this);
        ijkMediaPlayer.setOnCompletionListener(this);
        ijkMediaPlayer.setOnVideoSizeChangedListener(this);
    }

    public void setPath(List<File> files) {
        if (files.size() == 0) {
            iYsMediaControllerListener.onError(12);
        }
        this.files = files;
        file = files.get(mediaNum);
        position = 0;
        if (mediaStatus == STARTED || mediaStatus == PAUSED || mediaStatus == PREPARED) {
            resetData();
        }
        setDataSource();
        prepareAsync();
    }

    public void setPath(File file) {
        List<File> files = new ArrayList<File>();
        files.add(file);
        setPath(files);
    }

    public void setPath(String url) {
        File file = new File(url);
        setPath(file);
    }

    public void setSurface(SurfaceTexture surfaceTexture) {
        if (null != surface) {
            setFirstFrame4LocalVideo();
            surface.release();
        }
        if (null != surfaceTexture) {
            surface = new Surface(surfaceTexture);
            ijkMediaPlayer.setSurface(surface);
        }
    }

    private void prepareAsync() {
        mediaStatus = PREPARED;
        setSurface(surfaceTexture);
        setFirstFrame4LocalVideo();
        ijkMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        ijkMediaPlayer.prepareAsync();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        this.surfaceTexture = surfaceTexture;
        setSurface(surfaceTexture);
        iYsMediaControllerListener.onReadyPlay();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
        Log.d("neu", "onSurfaceTextureSizeChanged");
        updateTextureViewSize(mVideoMode);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        this.surfaceTexture = surfaceTexture;
        iYsMediaControllerListener.onSurfaceTextureUpdated();
        Log.d("neu", "onSurfaceTextureUpdated");
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
        mVideoHeight = ijkMediaPlayer.getVideoHeight();
        mVideoWidth = ijkMediaPlayer.getVideoWidth();
        updateTextureViewSize(mVideoMode);
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            updateSeekBar();
            sendEmptyMessageDelayed(0, 200);
        }
    };

    @Override
    public void onPrepared(IMediaPlayer mp) {
        iYsMediaControllerListener.onPrepared((int) mp.getDuration());
    }

    private void updateSeekBar() {
        if (ijkMediaPlayer.getDuration() > 0) {
            position = ijkMediaPlayer.getCurrentPosition();
            iYsMediaControllerListener.onUpdateSeekBar((int)position);
        }
    }

    public boolean isPlaying() {
        return ijkMediaPlayer.isPlaying();
    }

    public void seekTo(SeekBar seekBar) {
        ijkMediaPlayer.seekTo(seekBar.getProgress());
    }


    public void destroy() {
        mediaStatus = IDLE;
        if (null != handler) {
            handler.removeMessages(0);
        }
        if (null != surface) {
            surface.release();
        }
        if (null != surfaceTexture) {
            surfaceTexture.release();
        }
        ijkMediaPlayer.release();
        IjkMediaPlayer.native_profileEnd();
    }

    private void startSeekBarLooper() {
        if (null != handler && !handler.hasMessages(0)) {
            handler.sendEmptyMessageDelayed(0, 200);
        }
    }

    public void play() {
        startSeekBarLooper();
        if (ijkMediaPlayer.isPlaying()) {
            mediaStatus = PAUSED;
            ijkMediaPlayer.pause();
            iYsMediaControllerListener.isPause();
        } else {
            mediaStatus = STARTED;
            ijkMediaPlayer.start();
            iYsMediaControllerListener.isPlay();
        }
    }

    public void next() {
        mediaNum++;
        if (mediaNum >= files.size()) {
            mediaNum = 0;
        }
        file = files.get(mediaNum);
        resetData();
        setDataSource();
    }

    public void prev() {
        mediaNum--;
        if (mediaNum < 0) {
            mediaNum = files.size() - 1;
        }
        file = files.get(mediaNum);
        resetData();
        setDataSource();
    }

    private void resetData() {
        mediaStatus = IDLE;
        if (null != handler) {
            handler.removeMessages(0);
        }
        ijkMediaPlayer.reset();
        ijkMediaPlayer.setSurface(null);
        ijkMediaPlayer.release();
        ijkMediaPlayer = new IjkMediaPlayer();
        setIjkMediaPlayerListener();
    }

    private void setDataSource() {
        try {
            ijkMediaPlayer.setDataSource(file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setFirstFrame4LocalVideo(){
        if(null == file || null == file.getPath()) {
            return;
        }
        int index = file.getPath().indexOf("http");
        if(index == -1 && Utils.isVideo(file)) {
            try {
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(file.getPath());
                Bitmap bitmap = mmr.getFrameAtTime(position * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                if (null != bitmap) {
                    iYsMediaControllerListener.setFirstFrame4LocalVideo(bitmap);
                } else {
                    iYsMediaControllerListener.onError(14);
                }
            } catch (Exception c) {
                iYsMediaControllerListener.onError(13);
            }
        }
    }
    @Override
    public boolean onError(IMediaPlayer mp, int what, int extra) {
        iYsMediaControllerListener.onError(what);
        return false;
    }

    @Override
    public boolean onInfo(IMediaPlayer mp, int what, int extra) {
        switch (what) {
            case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                iYsMediaControllerListener.onStartBuffering();
                ijkMediaPlayer.pause();
                break;
            case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                iYsMediaControllerListener.onEndBuffering();
                if (mediaStatus == STARTED) {
                    ijkMediaPlayer.start();
                }
                break;
        }
        return false;
    }

    @Override
    public void onCompletion(IMediaPlayer mp) {
        if (handler!=null){
            handler.removeMessages(0);
        }
        iYsMediaControllerListener.onCompletion();
    }

    private int mVideoWidth;//视频宽度
    private int mVideoHeight;//视频高度
    public static final int CENTER_CROP_MODE = 1;//中心裁剪模式
    public static final int CENTER_MODE = 2;//一边中心填充模式
    public int mVideoMode = 2;

    //重新计算video的显示位置，裁剪后全屏显示
    private void updateTextureViewSizeCenterCrop() {

        float sx = (float) getWidth() / (float) mVideoWidth;
        float sy = (float) getHeight() / (float) mVideoHeight;

        Matrix matrix = new Matrix();
        float maxScale = Math.max(sx, sy);

        //第1步:把视频区移动到View区,使两者中心点重合.
        matrix.preTranslate((getWidth() - mVideoWidth) / 2, (getHeight() - mVideoHeight) / 2);

        //第2步:因为默认视频是fitXY的形式显示的,所以首先要缩放还原回来.
        matrix.preScale(mVideoWidth / (float) getWidth(), mVideoHeight / (float) getHeight());

        //第3步,等比例放大或缩小,直到视频区的一边超过View一边, 另一边与View的另一边相等. 因为超过的部分超出了View的范围,所以是不会显示的,相当于裁剪了.
        matrix.postScale(maxScale, maxScale, getWidth() / 2, getHeight() / 2);//后两个参数坐标是以整个View的坐标系以参考的

        setTransform(matrix);
        postInvalidate();
    }

    public void updateTextureViewSize(int mode) {
        if (mode == CENTER_MODE) {
            updateTextureViewSizeCenter();
        } else if (mode == CENTER_CROP_MODE) {
            updateTextureViewSizeCenterCrop();
        }
    }

    //重新计算video的显示位置，让其全部显示并据中
    private void updateTextureViewSizeCenter() {

        float sx = (float) getWidth() / (float) mVideoWidth;
        float sy = (float) getHeight() / (float) mVideoHeight;

        Matrix matrix = new Matrix();

        //第1步:把视频区移动到View区,使两者中心点重合.
        matrix.preTranslate((getWidth() - mVideoWidth) / 2, (getHeight() - mVideoHeight) / 2);

        //第2步:因为默认视频是fitXY的形式显示的,所以首先要缩放还原回来.
        matrix.preScale(mVideoWidth / (float) getWidth(), mVideoHeight / (float) getHeight());

        //第3步,等比例放大或缩小,直到视频区的一边和View一边相等.如果另一边和view的一边不相等，则留下空隙
        if (sx >= sy) {
            matrix.postScale(sy, sy, getWidth() / 2, getHeight() / 2);
        } else {
            matrix.postScale(sx, sx, getWidth() / 2, getHeight() / 2);
        }

        setTransform(matrix);
        postInvalidate();
    }
}