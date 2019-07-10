package com.blockadm.common.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by hp on 2019/2/16.
 */

public class MyPlayerUtils  implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener  {

    private MediaPlayer mPlayer;
    private boolean hasPrepared;

    private void initIfNecessary() {
        if (null == mPlayer) {
            mPlayer = new MediaPlayer();
            mPlayer.setOnErrorListener(this);
            mPlayer.setOnCompletionListener(this);
            mPlayer.setOnPreparedListener(this);
        }
    }

    public void play(Context context, Uri dataSource) {
        hasPrepared = false; // 开始播放前讲Flag置为不可操作
        initIfNecessary(); // 如果是第一次播放/player已经释放了，就会重新创建、初始化
        try {
            mPlayer.reset();
            mPlayer.setDataSource(context, dataSource); // 设置曲目资源
            mPlayer.prepareAsync(); // 异步的准备方法


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        // release()会释放player、将player置空，所以这里需要判断一下
        if (null != mPlayer && hasPrepared) {
            mPlayer.start();
        }
    }

    public void pause() {
        if (null != mPlayer && hasPrepared) {
            mPlayer.pause();
        }
    }

    public boolean IsPause() {
        if (null != mPlayer) {
            return !mPlayer.isPlaying();
        }
        return  false;
    }

    public void seekTo(int position) {
        if (null != mPlayer && hasPrepared) {
            mPlayer.seekTo(position);
        }
    }

    // 对于播放视频来说，通过设置SurfaceHolder来设置显示Surface。这个方法不需要判断状态、也不会改变player状态
    public void setDisplay(SurfaceHolder holder) {
        if (null != mPlayer) {
            mPlayer.setDisplay(holder);
        }
    }
    public void release() {
        hasPrepared = false;
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        hasPrepared = true; // 准备完成后回调到这里
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        hasPrepared = false;
        // 通知调用处，调用play()方法进行下一个曲目的播放
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        hasPrepared = false;
        return false;
    }


    public int getDuration() {
        return mPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mPlayer.getCurrentPosition();
    }
}
