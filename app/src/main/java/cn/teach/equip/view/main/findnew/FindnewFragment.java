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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
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
        setVideoAdapter();
        getVideoTypeList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 获取视频分类列表
     */
    private void getVideoTypeList() {
        HttpServerImpl.getVideoTypeList().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    private void setVideoAdapter() {
        ArrayList<String> videos = new ArrayList<>();
        videos.add("aaaaa");
        videos.add("aaaaa");
        videos.add("aaaaa");
        videos.add("aaaaa");
        videos.add("aaaaa");
        LGRecycleViewAdapter<String> adapter = new LGRecycleViewAdapter<String>(videos) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_find_video;
            }

            @Override
            public void convert(LGViewHolder holder, String s, int position) {
                holder.setText(R.id.video_title, s);
            }
        };
        recycleView.setAdapter(adapter);
    }

}
