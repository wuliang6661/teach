package cn.teach.equip.view.main.find;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.base.BaseFragment;
import cn.teach.equip.bean.pojo.VideoListBO;
import cn.teach.equip.util.ShareUtils;

/**
 * 作者： ch
 * 时间： 2018/7/30 0030-下午 2:55
 * 描述：
 * 来源：
 */


public class VideoFragment extends BaseFragment {

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer videoPlayer;
    Unbinder unbinder;
    @BindView(R.id.bt_fenxiang)
    ImageView btFenxiang;
    @BindView(R.id.bt_dianzan)
    ImageView btDianzan;
    @BindView(R.id.dianzan_num)
    TextView dianzanNum;

//    private OrientationUtils orientationUtils;

    private VideoListBO.PageListBean video;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_video, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        video = (VideoListBO.PageListBean) getArguments().getSerializable("video");
        dianzanNum.setText(video.getLikeNum() + "");
        inviVideo();
//        video();
    }


    /**
     * 设置视频开始播放
     */
    private void video() {
        //增加封面
        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(getActivity()).load(video.getImgUrl()).into(imageView);
        videoPlayer.setThumbImageView(imageView);
        videoPlayer.setUp(video.getUrl(), true, "");
        videoPlayer.startPlayLogic();
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
        videoPlayer.getFullscreenButton().setVisibility(View.GONE);
//        ENPlayView startImg = (ENPlayView) videoPlayer.getStartButton();
//        startImg.setImageResource(R.drawable.video_start);
        if (video.getIsUp() == 1) {
            btDianzan.setImageResource(R.drawable.dianzan);
        } else {
            btDianzan.setImageResource(R.drawable.un_dianzan);
        }
    }


    @OnClick({R.id.bt_dianzan, R.id.bt_fenxiang})
    public void clickVideo(View view) {
        switch (view.getId()) {
            case R.id.bt_dianzan:
                upVideo();
                break;
            case R.id.bt_fenxiang:
                ShareUtils.shareVideo(video.getTitle(), video.getDesc(), video.getUrl());
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
                    btDianzan.setImageResource(R.drawable.dianzan);
                    video.setLikeNum(video.getLikeNum() + 1);
                } else {
                    btDianzan.setImageResource(R.drawable.un_dianzan);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (FindFragment.isVisable) {
            video();
            if (videoPlayer != null) {
                videoPlayer.onVideoResume();
            }
        }
    }


    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        videoPlayer.onVideoPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
