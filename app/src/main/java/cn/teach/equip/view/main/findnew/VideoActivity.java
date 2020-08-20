package cn.teach.equip.view.main.findnew;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.base.BaseActivity;
import cn.teach.equip.bean.pojo.VideoListBO;
import cn.teach.equip.util.ShareUtils;
import cn.teach.equip.weight.video.SampleCoverVideo;
import cn.teach.equip.weight.ShareDialog;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/8/1811:04
 * desc   : 播放视频的界面
 * version: 1.0
 */
public class VideoActivity extends BaseActivity {


    @BindView(R.id.video_player)
    SampleCoverVideo videoPlayer;
    @BindView(R.id.bt_fenxiang)
    ImageView btFenxiang;
    @BindView(R.id.bt_dianzan)
    ImageView btDianzan;
    @BindView(R.id.dianzan_num)
    TextView dianzanNum;

    private VideoListBO.PageListBean video;

    @Override
    protected int getLayout() {
        return R.layout.act_video;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        video = (VideoListBO.PageListBean) getIntent().getExtras().getSerializable("video");
        dianzanNum.setText(video.getLikeNum() + "");
        setTitleText(video.getTitle());
        inviVideo();
    }


    /**
     * 设置视频开始播放
     */
    private void video() {
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(video.getImgUrl()).into(imageView);
        videoPlayer.setThumbImageView(imageView);
        videoPlayer.setUp(video.getUrl(), true, "");
        videoPlayer.startPlayLogic();
//        videoPlayer.setSpeedPlaying(2.0f,true);
    }


    /**
     * 初始化视频设置
     */
    private void inviVideo() {
        //外部辅助的旋转，帮助全屏
//        orientationUtils = new OrientationUtils(getActivity(), videoPlayer);
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        videoPlayer.getBackButton().setVisibility(View.GONE);
        videoPlayer.getStartButton().setVisibility(View.VISIBLE);
//        videoPlayer.getFullscreenButton().setVisibility(View.GONE);
        videoPlayer.setBackgroundColor(Color.parseColor("#ffffff"));
//        ENPlayView startImg = (ENPlayView) videoPlayer.getStartButton();
//        startImg.setImageResource(R.drawable.video_start);
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, FullVideoActivity.class);
                intent.putExtra("url", video.getUrl());
                intent.putExtra("startTime", videoPlayer.getCurrentPositionWhenPlaying());
                startActivityForResult(intent, 1);
            }
        });
        if (video.getIsUp() == 1) {
            btDianzan.setImageResource(R.drawable.un_dianzan);
        } else {
            btDianzan.setImageResource(R.drawable.dianzan);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        video();
        if (videoPlayer != null) {
            videoPlayer.onVideoResume();
        }
    }

    @OnClick({R.id.bt_dianzan, R.id.bt_fenxiang})
    public void clickVideo(View view) {
        switch (view.getId()) {
            case R.id.bt_dianzan:
                upVideo();
                break;
            case R.id.bt_fenxiang:
                new ShareDialog().showShareDialog(new ShareDialog.OnClickShare() {
                    @Override
                    public void share(int flag) {
                        ShareUtils.shareVideo(video.getTitle(), video.getDesc(), video.getUrl(), flag);
                    }
                });
                break;
        }
    }


    private void upVideo() {
        showProgress();
        HttpServerImpl.videoUp(video.getCode(), video.getIsUp() == 1 ? 0 : 1).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                video.setIsUp(video.getIsUp() == 1 ? 0 : 1);
                if (video.getIsUp() == 1) {
                    btDianzan.setImageResource(R.drawable.un_dianzan);
                    video.setLikeNum(video.getLikeNum() + 1);
                } else {
                    btDianzan.setImageResource(R.drawable.dianzan);
                    video.setLikeNum(video.getLikeNum() - 1);
                }
                dianzanNum.setText(video.getLikeNum() + "");
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        if (videoPlayer != null) {
            videoPlayer.onVideoPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoPlayer != null) {
            videoPlayer.release();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (resultCode) {
            case 1:
                long startTime = data.getIntExtra("startTime", 0);
                videoPlayer.setUp(video.getUrl(), true, "");
                videoPlayer.startPlayLogic();
                videoPlayer.setSeekOnStart(startTime);
                break;
        }
    }


}
