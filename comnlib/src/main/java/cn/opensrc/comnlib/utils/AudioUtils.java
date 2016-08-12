package cn.opensrc.comnlib.utils;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.opensrc.comnlib.constants.AudioSizeConstants;
import cn.opensrc.comnlib.constants.DirConstants;

/**
 * Author:       sharp
 * Created on:   8/12/16 9:38 AM
 * Description:  录音
 * Revisions:
 */
public final class AudioUtils {
    private AudioUtils(){}

    private static MediaPlayer mMediaPlayer;
    private static MediaRecorder mMediaRecorder;
    private static String audioFilePath;
    private static boolean mStartRecording;
    private static boolean mStartPlaying;
    private static Timer mImgTimer;
    private static TimerTask mImgTimerTask;

    //////////////////////////////////////////
    //
    // 录音区
    //
    //////////////////////////////////////////

    /**
     * 获取录音的文件路径
     */
    public static String getAudioFilePath() {
        return audioFilePath;
    }

    /**
     * 设置录音的保存路径
     */
    public static void setAudioFilePath(String savePath) {

        audioFilePath = "";
        FileUtils.makeDir(new File(DirConstants.DIR_AUDIO));
        if (savePath == null || "".equals(savePath)) {

            audioFilePath = DirConstants.DIR_AUDIO + "audio_" + System.currentTimeMillis() + ".amr";

        } else {

            audioFilePath = savePath;

        }

    }

    /**
     * 开始录音（普通录音）
     */
    public static void startRecording() {

        startRecording(null);

    }

    /**
     * 开始录音（带录音图片）
     */
    public static void startRecording(final Handler rcdingImgHandler) {

        if (mStartRecording) return;

        mStartRecording = true;

        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        if (audioFilePath == null || "".equals(audioFilePath)) setAudioFilePath("");
        mMediaRecorder.setOutputFile(getAudioFilePath());

        try {

            mMediaRecorder.prepare();

        } catch (IOException e) {

            e.printStackTrace();

        }

        mMediaRecorder.start();

        if (rcdingImgHandler == null || mMediaRecorder == null) return;

        if (mImgTimer == null)
            mImgTimer = new Timer();
        if (mImgTimerTask == null)
            mImgTimerTask = new TimerTask() {
                @Override
                public void run() {

                    listenVolume(rcdingImgHandler);

                }
            };

        mImgTimer.schedule(mImgTimerTask,1000,200);

    }

    /**
     * 音量监听
     */
    private static void listenVolume(Handler rcdingImgHandler) {

        if (mMediaRecorder == null) return;

        int ratio = mMediaRecorder.getMaxAmplitude();

        if (ratio == 0) {
            rcdingImgHandler.sendEmptyMessage(AudioSizeConstants.AUDIO_0);
        } else if (ratio > 1) {

            int volume = (int) (10 * Math.log(ratio) / Math.log(10));

            if (volume <= AudioSizeConstants.AUDIO_0)
                rcdingImgHandler.sendEmptyMessage(AudioSizeConstants.AUDIO_0);
            else if (volume < AudioSizeConstants.AUDIO_1)
                rcdingImgHandler.sendEmptyMessage(AudioSizeConstants.AUDIO_1);
            else if (volume < AudioSizeConstants.AUDIO_2)
                rcdingImgHandler.sendEmptyMessage(AudioSizeConstants.AUDIO_2);
            else if (volume < AudioSizeConstants.AUDIO_3)
                rcdingImgHandler.sendEmptyMessage(AudioSizeConstants.AUDIO_3);
            else if (volume < AudioSizeConstants.AUDIO_4)
                rcdingImgHandler.sendEmptyMessage(AudioSizeConstants.AUDIO_4);
            else if (volume < AudioSizeConstants.AUDIO_5)
                rcdingImgHandler.sendEmptyMessage(AudioSizeConstants.AUDIO_5);
            else if (volume < AudioSizeConstants.AUDIO_6)
                rcdingImgHandler.sendEmptyMessage(AudioSizeConstants.AUDIO_6);
            else if (volume < AudioSizeConstants.AUDIO_7)
                rcdingImgHandler.sendEmptyMessage(AudioSizeConstants.AUDIO_7);
            else if (volume < AudioSizeConstants.AUDIO_8)
                rcdingImgHandler.sendEmptyMessage(AudioSizeConstants.AUDIO_8);
            else if (volume < AudioSizeConstants.AUDIO_9)
                rcdingImgHandler.sendEmptyMessage(AudioSizeConstants.AUDIO_9);
            else if (volume < AudioSizeConstants.AUDIO_10)
                rcdingImgHandler.sendEmptyMessage(AudioSizeConstants.AUDIO_10);
            else if (volume < AudioSizeConstants.AUDIO_11)
                rcdingImgHandler.sendEmptyMessage(AudioSizeConstants.AUDIO_11);

        }

    }

    /**
     * 停止录音
     */
    public static void stopRecording() {

        if (mImgTimer != null){

            mImgTimer.cancel();
            mImgTimer = null;

        }

        if (mImgTimerTask != null){

            mImgTimerTask.cancel();
            mImgTimerTask = null;

        }

        mStartRecording = false;

        if (mMediaRecorder == null) return;

        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;

    }

    //////////////////////////////////////////
    //
    // 语音播放区
    //
    //////////////////////////////////////////

    /**
     * 开始播放音频
     */
    public static void startPlaying(String audioFilePath) {

        if (mStartPlaying) return;

        if (audioFilePath == null || "".equals(audioFilePath)) return;


        mMediaPlayer = new MediaPlayer();
        mStartPlaying = true;

        try {

            mMediaPlayer.setDataSource(audioFilePath);
            mMediaPlayer.prepare();
            mMediaPlayer.start();

        } catch (IOException e) {

            e.printStackTrace();

        }

        // 监听
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                stopPlaying();

            }
        });

    }

    /**
     * 停止播放音频
     */
    public static void stopPlaying() {

        if (mMediaPlayer == null) return;

        mMediaPlayer.release();
        mMediaPlayer = null;
        mStartPlaying = false;

    }

    /**
     * 暂停播放音频
     */
    public static void pausePlaying() {

        if (mMediaPlayer == null) return;

        mMediaPlayer.pause();
        mStartPlaying = false;

    }

    //////////////////////////////////////////
    //
    // 其它
    //
    //////////////////////////////////////////

    /**
     * 释放所有资源
     */
    public static void releaseRes() {

        stopRecording();
        stopPlaying();
        mStartRecording = false;
        mStartPlaying = false;

    }

    /**
     * 获取音频是否播放完毕状态
     */
    public static boolean isPlaying() {

        return mStartPlaying;

    }

}
