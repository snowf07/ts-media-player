package com.ts.media.player.manage;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 音频播放类
 * <p>
 * <p>
 * Created by lang.chen on 2019/10/29
 */
public class AudioPlayerHelper implements AudioPlayListener {


    private static String TAG = "AudioPlayerHelper.class";

    private static volatile AudioPlayerHelper instance;


    private IjkMediaPlayer ijkMediaPlayer;
    private OnAudioPlayListener onAudioPlayListener;

    //音频资源url
    private String url;

    private AudioPlayerHelper() {

    }

    public static AudioPlayerHelper getInstance() {
        if (instance == null) {
            synchronized (AudioPlayerHelper.class) {
                if (instance == null) {
                    instance = new AudioPlayerHelper();
                }
            }
        }
        return instance;
    }


    /**
     * 播放
     *
     * @param url      支持网络url或者本地文件路径
     * @param listener 音频播放回调，不需要回调可以传入null
     */
    @Override
    public void play(String url, OnAudioPlayListener listener) {
        this.url = url;
        this.onAudioPlayListener = listener;
        if (null == ijkMediaPlayer) {
            ijkMediaPlayer = new IjkMediaPlayer();
        }
        try {
            ijkMediaPlayer.setDataSource(url);
            ijkMediaPlayer.prepareAsync();
            //装备完成
            ijkMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer iMediaPlayer) {
                    start();
                }
            });
            //播放完成
            ijkMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(IMediaPlayer iMediaPlayer) {
                    if (null != onAudioPlayListener) {
                        onAudioPlayListener.onCompletion();
                    }
                }
            });
            //播放错误
            ijkMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                    if (null != onAudioPlayListener) {
                        onAudioPlayListener.onError();
                    }
                    return false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void pause() {
        if (null != ijkMediaPlayer && isPlaying()) {
            ijkMediaPlayer.pause();

        }
        if (null != onAudioPlayListener) {
            onAudioPlayListener.onStop();
        }
    }

    @Override
    public void start() {
        if (null != ijkMediaPlayer && !isPlaying()) {
            ijkMediaPlayer.start();
        }
        if (null != onAudioPlayListener) {
            onAudioPlayListener.onStart();
        }
    }


    /**
     * 重新播放
     */
    @Override
    public void restart() {
        if (TextUtils.isEmpty(url)) {
            Log.e(TAG, "audio uninitialized,call play() please");
        }
        if (null != ijkMediaPlayer) {
            ijkMediaPlayer.reset();
            play(url,onAudioPlayListener);
        }
    }

    @Override
    public boolean isPlaying() {
        if (null != ijkMediaPlayer) {
            return ijkMediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void getOnlyDuration(String sourceUrl, final OnAudioPlayListener2 listener) {
        final IjkMediaPlayer  ijkMediaPlayer = new IjkMediaPlayer();
        try {
            ijkMediaPlayer.setDataSource(sourceUrl);
            ijkMediaPlayer.prepareAsync();
            //装备完成
            ijkMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer iMediaPlayer) {
                    if(null!=listener){
                        listener.getDuration(ijkMediaPlayer.getDuration());
                        ijkMediaPlayer.release();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取音频时长，只要在音频准备完后才会显示
     * @return
     */
    @Override
    public long getDuration() {
        if (null != ijkMediaPlayer) {
            return ijkMediaPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public void release() {
        if (isPlaying()) {
            ijkMediaPlayer.stop();
        }
        if (null != ijkMediaPlayer) {
            ijkMediaPlayer.release();
        }
        ijkMediaPlayer = null;
        onAudioPlayListener = null;
        url = null;
    }

    @Override
    public long getCurrentPosition() {
        if (null != ijkMediaPlayer) {
            ijkMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public void setLooping(boolean isLoop) {
       ijkMediaPlayer.setLooping(isLoop);
    }


}
