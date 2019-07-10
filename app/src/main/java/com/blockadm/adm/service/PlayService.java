package com.blockadm.adm.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.blockadm.adm.R;
import com.blockadm.adm.event.MessageEvent;
import com.blockadm.adm.event.PleyerEvent;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * Created by zqt on 2016/5/23.
 */
public class PlayService extends Service implements MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayer;
    private int duration;
    public boolean isPlaying = false;

    @Override
    public IBinder onBind(Intent intent) {
       String url =  intent.getStringExtra("url");
       if (mediaPlayer==null){
           mediaPlayer = new MediaPlayer();
       }
        mediaPlayer.reset();

       if (StringUtils.isNotEmpty(url)) {
           Uri uri = Uri.parse(url);
           try {
               mediaPlayer.setDataSource(getApplicationContext(), uri ); // 设置曲目资源
           } catch (IOException e) {
               e.printStackTrace();
           }

           mediaPlayer.prepareAsync();
           mediaPlayer.setOnCompletionListener(this);
           mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
               @Override
               public void onPrepared(MediaPlayer mp) {
                   if (mediaPlayer!=null){
                       duration = mediaPlayer.getDuration();
                       EventBus.getDefault().post(new PleyerEvent());
                   }
               }
           });
       }




        return new PlayBinder();
    }

    public void onDestroyPalyer() {
        mediaPlayer=null;
    }
    Handler handler = new Handler();

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public class PlayBinder extends Binder {
        public PlayService getPlayService() {
            return PlayService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


    public void playByUri(Uri uri) {
        try {
            try {
                mediaPlayer.setDataSource(getApplicationContext(), uri ); // 设置曲目资源
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mediaPlayer!=null){
                        duration = mediaPlayer.getDuration();

                        L.d("xxx","duration: "+duration);
                        EventBus.getDefault().post(new PleyerEvent());
                        mediaPlayer.start();
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface PlayCompletionCallback {
        void onCompletion();
    }

    private PlayCompletionCallback mCompletionCallback;
    public void play(String url,PlayCompletionCallback completionCallback) {
        mCompletionCallback = completionCallback;
        try {
            if (mediaPlayer==null){
                mediaPlayer = new MediaPlayer();
            }
            mediaPlayer.reset();
            Uri uri = Uri.parse(url);

            playByUri(uri);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(String url) {

        try {
            if (mediaPlayer==null){
                mediaPlayer = new MediaPlayer();
            }
            mediaPlayer.reset();
            Uri uri = Uri.parse(url);

            playByUri(uri);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mediaPlayer!=null){
            mediaPlayer.pause();
            isPlaying = false;
        }

    }

    public void start() {
        if (mediaPlayer!=null){
            mediaPlayer.start();
            isPlaying = true;
        }

    }

    public boolean isPlaying() {
        if (null != mediaPlayer) {
            return mediaPlayer.isPlaying();
        }
        return  false;
    }
   private int msec1;
    public void setTo(int msec) {
        this.msec1=msec;
        handler.post(new Runnable() {
         @Override
         public void run() {
             mediaPlayer.seekTo(msec1);
         }
     });

    }

    public int getDuration() {
        return duration;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mCompletionCallback != null) {
            mCompletionCallback.onCompletion();
        }
        isPlaying = false;
    }
}
