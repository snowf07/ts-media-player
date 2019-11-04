package com.ts.media.player.manage;

/**
 * Created by lang.chen on 2019/10/29
 */
public interface AudioPlayListener {


    /**
     * 播放
     * @param url 音频url 包括网络url,本地路径，raw资源文件
     */
    void play(String url,OnAudioPlayListener onAudioPlayListener);

    /**
     * 暂停
     */
    void pause();

    /**
     * 开始
     */
    void start();

    /**
     * 重新播放
     */
    void restart();


    boolean isPlaying();


    /**
     * 获取音频时长
     * @return
     */
    long getDuration();

    /**
     * 仅仅获取音频时长,由于获取是时长是异步操作，通过回调方式回去
     * @param sourceUrl  音频文件url或者本地路径
     *
     *note 只支持单个音频文件，如果要支持列表多个音频文件，是从服务器端返回总时长
     **/
    void getOnlyDuration(String sourceUrl,OnAudioPlayListener2 listener);


    /**
     * 释放播放器
     */
    void release();

    /**
     * 获取播放的位置
     */
    long getCurrentPosition();

    /**
     * 设置循环播放
     * @param isLoop
     */
    void setLooping(boolean isLoop);
}
