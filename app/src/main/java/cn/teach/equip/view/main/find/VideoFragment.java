package cn.teach.equip.view.main.find;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.bean.pojo.VideoListBO;

/**
 * 作者： ch
 * 时间： 2018/7/30 0030-下午 2:55
 * 描述：
 * 来源：
 */


public class VideoFragment extends Fragment {

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer videoPlayer;
    Unbinder unbinder;

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
        inviVideo();
        video();
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
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (videoPlayer == null) {
            return;
        }
        if (isVisibleToUser) {
            videoPlayer.onVideoResume();
        } else {
            videoPlayer.onVideoPause();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoPlayer != null) {
            videoPlayer.onVideoResume();
        }

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
