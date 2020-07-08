package cn.teach.equip.view.main.find;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.VideoListBO;
import cn.teach.equip.mvp.MVPBaseFragment;
import cn.teach.equip.weight.VerticalViewPager2;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FindFragment extends MVPBaseFragment<FindContract.View, FindPresenter> implements FindContract.View {


    @BindView(R.id.video_pager)
    VerticalViewPager2 videoPager;
    @BindView(R.id.srl_page)
    SmartRefreshLayout srlPage;
    Unbinder unbinder;

    private VerticalViewPagerAdapter pagerAdapter;

    private int pageNum = 1;

    private List<VideoListBO.PageListBean> videoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_video_find, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addListener();
        getVideoList();
    }


    /**
     * 获取视频列表
     */
    private void getVideoList() {
        HttpServerImpl.getVideoList(pageNum).subscribe(new HttpResultSubscriber<VideoListBO>() {
            @Override
            public void onSuccess(VideoListBO s) {
                if (pageNum == 1) {
                    videoList = s.getPageList();
                    initView();
                } else {
                    videoList.addAll(s.getPageList());
                    pagerAdapter.setUrlList(videoList);
                    pagerAdapter.notifyDataSetChanged();
                    srlPage.finishLoadMore();
                }
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    private void addListener() {
        srlPage.setEnableAutoLoadMore(false);
        srlPage.setEnableLoadMore(false);
        srlPage.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srlPage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNum++;
                        getVideoList();
                    }
                }, 2000);

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    private void initView() {
        pagerAdapter = new VerticalViewPagerAdapter(getActivity().getSupportFragmentManager());
//        videoPager.setVertical(true);
        videoPager.setOffscreenPageLimit(10);
        pagerAdapter.setUrlList(videoList);
        videoPager.setAdapter(pagerAdapter);

        videoPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == videoList.size() - 1) {
                    srlPage.setEnableAutoLoadMore(true);
                    srlPage.setEnableLoadMore(true);
                } else {
                    srlPage.setEnableAutoLoadMore(false);
                    srlPage.setEnableLoadMore(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
