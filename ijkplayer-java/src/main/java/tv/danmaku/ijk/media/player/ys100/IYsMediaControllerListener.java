package tv.danmaku.ijk.media.player.ys100;

import android.graphics.Bitmap;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by zhaoguoliang on 2018/4/12.
 */

public interface IYsMediaControllerListener {
    public void onReadyPlay();

    public void onPrepared(int duration);

    public void onUpdateSeekBar(int progress);

    public void onError(int what);

    public void onStartBuffering();

    public void onEndBuffering();

    public void onCompletion();

    public void isPlay();

    public void isPause();

    public void onSurfaceTextureUpdated();

    public void setFirstFrame4LocalVideo(Bitmap bitmap);

}
