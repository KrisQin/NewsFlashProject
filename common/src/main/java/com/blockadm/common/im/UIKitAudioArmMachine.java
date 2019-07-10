package com.blockadm.common.im;


import android.media.MediaPlayer;

import com.blockadm.common.im.call.ComCallback;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.T;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;

import java.io.File;


public class UIKitAudioArmMachine {

    public static final String time_tag = "time_tag";

    private boolean playing, innerRecording;
    private volatile Boolean recording = false;
    public static String CURRENT_RECORD_FILE = UIKitConstants.RECORD_DIR + "auto_";
    private static AudioRecordCallback mRecordCallback;
    private AudioPlayCallback mPlayCallback;

    private static String recordAudioPath;
    private static long startTime, endTime;
    private MediaPlayer mPlayer;
    private static UIKitAudioArmMachine instance = new UIKitAudioArmMachine();


    private UIKitAudioArmMachine() {

    }

    public static UIKitAudioArmMachine getInstance() {
        RecordManager.getInstance().setRecordResultListener(new RecordResultListener() {
            @Override
            public void onResult(File result) {
                String path = result.getAbsolutePath();
                L.d("tim_test","sdsdsd mRecordCallback : "+mRecordCallback+" ; recordAudioPath: "+path);

                recordAudioPath = path;

                if (mRecordCallback != null)
                    mRecordCallback.recordComplete(endTime - startTime);

            }
        });

        return instance;
    }

    public String getRecordAudioPath() {
        return recordAudioPath;
    }

    public int getDuration() {
        return (int) (endTime - startTime);
    }

    public interface AudioRecordCallback {
        void recordComplete(long duration);

        void recording(int endTime);
    }

    public interface AudioPlayCallback {
        void playComplete();
    }

    public void startRecord(AudioRecordCallback callback) {

        mRecordCallback = callback;
        recording = true;
        synchronized (recording) {
            try {

                startTime = System.currentTimeMillis();
                L.d(time_tag, "startTime: " + startTime);
                final int[] historyTime = {-1};

                RecordManager.getInstance().start();

                innerRecording = true;
                new Thread() {
                    @Override
                    public void run() {
                        while (recording && innerRecording) {

                            if (mRecordCallback != null) {
                                long time = System.currentTimeMillis() - startTime;

                                if (time > (TUIKit.getBaseConfigs().getAudioRecordMaxTime()-10) * 1000) {
                                    int second = (int) ((time - (TUIKit.getBaseConfigs().getAudioRecordMaxTime()-10) * 1000) / 1000);
                                    if (historyTime[0] != second) {
                                        mRecordCallback.recording(10 - second);
                                    }
                                    historyTime[0] = second;
                                }
                            }

                            if (System.currentTimeMillis() - startTime >= TUIKit.getBaseConfigs().getAudioRecordMaxTime() * 1000) {
                                stopRecord();
                                return;
                            }
                        }
                    }
                }.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void stopRecord() {
        synchronized (recording) {
            if (recording) {

                RecordManager.getInstance().stop();

                innerRecording = false;
                recording = false;
                endTime = System.currentTimeMillis();

                L.d(time_tag,"endTime: "+endTime+" ; endTime - startTime: "+(endTime - startTime));



            }
        }
    }

//    private class PlayThread extends Thread {
//        String audioPath;
//
//        PlayThread(String filePath) {
//            audioPath = filePath;
//        }
//
//        public void run() {
//            try {
//                mPlayer = new MediaPlayer();
//                mPlayer.setDataSource(audioPath);
//                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        mPlayCallback.playComplete();
//                        playing = false;
//                    }
//                });
//                mPlayer.prepare();
//                mPlayer.start();
//                playing = true;
//            } catch (Exception e) {
//                T.showShort(ContextUtils.getBaseApplication(),"语音文件已损坏或不存在");
//                e.printStackTrace();
//                mPlayCallback.playComplete();
//                playing = false;
//            }
//        }
//    }



    //    public void playRecord(String filePath, AudioPlayCallback callback) {
    //        this.mPlayCallback = callback;
    //        new PlayThread(filePath).start();
    //
    //
    //    }

    //
    //    public void stopPlayRecord() {
    //        if (mPlayer != null) {
    //            mPlayer.stop();
    //            playing = false;
    //            mPlayCallback.playComplete();
    //        }
    //    }
    //
    //    public boolean isPlayingRecord() {
    //        return playing;
    //    }

}
