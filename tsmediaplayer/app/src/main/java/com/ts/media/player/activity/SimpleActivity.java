package com.ts.media.player.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ts.media.player.GSYVideoManager;
import com.ts.media.player.R;
import com.ts.media.player.listener.GSYSampleCallBack;
import com.ts.media.player.listener.LockClickListener;
import com.ts.media.player.manage.VideoPlayerHelper;
import com.ts.media.player.utils.OrientationUtils;
import com.ts.media.player.video.SampleCoverVideo;
import com.ts.media.player.video.StandardGSYVideoPlayer;

/**
 * 视频播放demo详情
 *
 * 隐藏底部进度条
 */
public class SimpleActivity extends AppCompatActivity {


    SampleCoverVideo detailPlayer;

    private boolean isPlay;
    private boolean isPause;

    private OrientationUtils orientationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detailPlayer =  findViewById(R.id.player);

        String url = "http://image.qingyinlive.com/201910222336/voice/cmt/articleVideo/151395476340595220286.mp4";


        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        orientationUtils.setRotateWithSystem(false);

        VideoPlayerHelper.getInstance().play(getApplicationContext(), url, true, "", detailPlayer, new GSYSampleCallBack() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(false);
                isPlay = true;
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                //objects[标题]
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }
        });

        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(SimpleActivity.this, true, true);
            }
        });


        detailPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        detailPlayer.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        detailPlayer.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            detailPlayer.getCurrentPlayer().release();
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }




}
