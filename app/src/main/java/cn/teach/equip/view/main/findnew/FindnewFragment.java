package cn.teach.equip.view.main.findnew;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.VideoFeiLeiBO;
import cn.teach.equip.bean.pojo.VideoListBO;
import cn.teach.equip.mvp.MVPBaseFragment;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;

/**
 * MVPPlugin
 * 新的发现视频 页面
 */

public class FindnewFragment extends MVPBaseFragment<FindnewContract.View, FindnewPresenter>
        implements FindnewContract.View {


    @BindView(R.id.sousuo)
    LinearLayout sousuo;
    @BindView(R.id.saoma)
    ImageView saoma;
    @BindView(R.id.left_menu)
    ExpandableListView leftMenu;
    @BindView(R.id.refresh_layout)
    LinearLayout refreshLayout;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.none_layout)
    LinearLayout noneLayout;
    Unbinder unbinder;

    private int pageNum = 1;
    private int levelId3;   //二级视频id

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_find_new, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getVideoTypeList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.refresh_layout)
    public void refresh() {
        pageNum++;
        getVideoList();
    }


    /**
     * 获取视频分类列表
     */
    private void getVideoTypeList() {
        HttpServerImpl.getVideoTypeList().subscribe(new HttpResultSubscriber<List<VideoFeiLeiBO>>() {
            @Override
            public void onSuccess(List<VideoFeiLeiBO> s) {
                setMenuAdapter(s);
                levelId3 = s.get(0).getSubList().get(0).getVideoTypeId();
                getVideoList();
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    /**
     * 设置视频分类适配器
     */
    private void setMenuAdapter(List<VideoFeiLeiBO> fenLeiBOS) {
        VideoMenuAdapter adapter = new VideoMenuAdapter(getActivity(), fenLeiBOS);
        leftMenu.setAdapter(adapter);
        leftMenu.expandGroup(0);
        leftMenu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                adapter.setSelectChild(childPosition);
                if (!fenLeiBOS.get(groupPosition).getSubList().isEmpty()) {
                    levelId3 = fenLeiBOS.get(groupPosition).getSubList().get(childPosition).getVideoTypeId();
                    pageNum = 1;
                    getVideoList();
                }
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        leftMenu.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                adapter.setSelectGroup(groupPosition);
                adapter.setSelectChild(0);
                if (!fenLeiBOS.get(groupPosition).getSubList().isEmpty()) {
                    levelId3 = fenLeiBOS.get(groupPosition).getSubList().get(0).getVideoTypeId();
                    pageNum = 1;
                    getVideoList();
                }
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        leftMenu.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                expandOnlyOne(groupPosition);
            }
        });
    }

    // 每次展开一个分组后，关闭其他的分组
    private boolean expandOnlyOne(int expandedPosition) {
        boolean result = true;
        int groupLength = leftMenu.getExpandableListAdapter().getGroupCount();
        for (int i = 0; i < groupLength; i++) {
            if (i != expandedPosition && leftMenu.isGroupExpanded(i)) {
                result &= leftMenu.collapseGroup(i);
            }
        }
        return result;
    }


    /**
     * 获取视频列表
     */
    private void getVideoList() {
        HttpServerImpl.getVideoList(pageNum, levelId3).subscribe(new HttpResultSubscriber<VideoListBO>() {
            @Override
            public void onSuccess(VideoListBO s) {
                if (s.getPageList().isEmpty()) {
                    pageNum = 0;
                }
                setVideoAdapter(s.getPageList());
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    private void setVideoAdapter(List<VideoListBO.PageListBean> videos) {
        if (videos.isEmpty()) {
            noneLayout.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
        } else {
            noneLayout.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
        }
        LGRecycleViewAdapter<VideoListBO.PageListBean> adapter = new LGRecycleViewAdapter<VideoListBO.PageListBean>(videos) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_find_video;
            }

            @Override
            public void convert(LGViewHolder holder, VideoListBO.PageListBean s, int position) {
                holder.setText(R.id.video_title, s.getTitle());
                holder.setImageUrl(getActivity(), R.id.video_img, s.getImgUrl());
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("video", videos.get(position));
                gotoActivity(VideoActivity.class, bundle, false);
            }
        });
        recycleView.setAdapter(adapter);
    }

}
