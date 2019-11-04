package com.ts.media.player.manage;

/**
 * 音频播放回调监听
 * Created by lang.chen on 2019/10/29
 */
public interface OnAudioPlayListener {

    /**
     * 播放完成
     */
    void onCompletion();

    /**
     * 播放错误
     */
    void onError();

    /**
     * 停止播放
     */
    void onStop();

    /**
     * 开始播放
     */
    void onStart();

}
