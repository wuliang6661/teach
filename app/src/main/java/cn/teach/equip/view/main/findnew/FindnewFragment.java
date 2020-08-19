package cn.teach.equip.view.main.findnew;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.base.MyApplication;
import cn.teach.equip.bean.pojo.VideoFeiLeiBO;
import cn.teach.equip.bean.pojo.VideoListBO;
import cn.teach.equip.mvp.MVPBaseFragment;
import cn.teach.equip.view.SearchActivity;
import cn.teach.equip.view.login.LoginActivity;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;
import cn.teach.equip.zxing.activity.CaptureActivity;

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

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if(levelId3 != 0){
            getVideoList();
        }
    }

    @OnClick({R.id.saoma, R.id.sousuo})
    public void clickTitle(View view) {
        if (StringUtils.isEmpty(MyApplication.token)) {
            gotoActivity(LoginActivity.class, false);
            return;
        }
        switch (view.getId()) {
            case R.id.saoma:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        ) {

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                            }, 1);
                } else {
                    gotoActivity(CaptureActivity.class, false);
                }
                break;
            case R.id.sousuo:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("isCollect", 0);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    gotoActivity(CaptureActivity.class, false);
                } else {
                    showWaringDialog();
                }
                break;
            }
        }
    }

    private void showWaringDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("警告！")
                .setMessage("请前往设置->应用->教育装备->权限中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确定", null).show();
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
                holder.setImageUrl(getActivity(), R.id.video_img, s.getListIcon());
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
