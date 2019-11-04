package com.ts.media.player.manage;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ts.media.player.GSYVideoManager;
import com.ts.media.player.builder.GSYVideoOptionBuilder;
import com.ts.media.player.listener.GSYSampleCallBack;
import com.ts.media.player.video.StandardGSYVideoPlayer;

/**
 * 视频播放工具类
 * Created by lang.chen on 2019/10/25
 * <p>
 * 1.提供是否自动播放
 * 2.提供视频的一张图片
 * <p>
 * 通过Glide完全可以加载视频第一帧
 * *
 * 3.提供远程视频播放还是本地视频播放
 * <p>
 * 远程资源 直接传url
 * 本地视频 & 本地视频播放需要file:///开头+视频路径
 *
 *
 * </p>
 * 4.提供释放资源
 * 调用 release();
 * 5.播放视频回调
 * GSYSampleCallBack类
 * </p>
 *
 *
 *
 * <p>
 * 6.列表播放视频点击切换到大屏幕 || 横竖屏播放且阿虎 播放进度设置*
 * 7. 缓冲比例显示
 *
 * </p>
 */
public class VideoPlayerHelper {


    private static VideoPlayerHelper instance;

    //封面
    private ImageView imageView;
    //是否全屏播放
    private boolean isFullWindowPlay;


    private VideoPlayerHelper() {

    }

    public static VideoPlayerHelper getInstance() {
        if (null == instance) {
            synchronized (VideoPlayerHelper.class) {
                if (null == instance) {
                    instance = new VideoPlayerHelper();
                }
            }
        }
        return instance;

    }

    /**
     * 播放视频提供回调
     *
     * @param context          上下文
     * @param url              视频资源url   & 本地视频播放需要file:///开头+视频路径
     * @param isFullWidnowPlay 是否全屏播放   If true底部进度条，操作按钮隐藏
     * @param gsyVideoPlayer   播放器
     * @param callBack         视频播放回调
     */
    public void play(Context context, String url, boolean isFullWidnowPlay, String title, StandardGSYVideoPlayer gsyVideoPlayer, GSYSampleCallBack callBack) {
        this.isFullWindowPlay = isFullWidnowPlay;
        //gsyVideoPlayer.setShowOperationView(!isFullWidnowPlay);
        //全屏播放
        if (isFullWidnowPlay) {
            gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
            gsyVideoPlayer.getBottomContainer().setVisibility(View.GONE);
        }

        GSYVideoOptionBuilder gsyVideoOption = getGSYVideoOptionBuilder(context, url, title, isFullWidnowPlay);

        gsyVideoOption.build(gsyVideoPlayer);
        if (null != callBack) {
            gsyVideoPlayer.setVideoAllCallBack(callBack);
        }
    }


    private GSYVideoOptionBuilder getGSYVideoOptionBuilder(Context context, String url, String title, boolean isAuto) {
        //内置封面可参考SampleCoverVideo
        imageView = new ImageView(context);

        return new GSYVideoOptionBuilder().setThumbImageView(imageView)
                .setIsTouchWiget(false)
                .setIsTouchWigetFull(false)
                .setRotateViewAuto(false)
                .setLockLand(true)
                .setAutoFullWithSize(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(url)
                .setCacheWithPlay(false)
                .setVideoTitle(title);
    }


    /**
     * 返回视频封面ImageView 对象
     */
    public ImageView getCoverImg() {
        return imageView;
    }

    /**
     * 释放资源
     */
    public void release() {
        GSYVideoManager.releaseAllVideos();
    }
}
