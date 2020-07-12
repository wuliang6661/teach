package cn.teach.equip.view.main.shoucang;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.base.BaseFragment;
import cn.teach.equip.bean.pojo.FenLeiBO;
import cn.teach.equip.view.WebActivity;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/7/913:45
 * desc   :
 * version: 1.0
 */
public class PlayingFragment extends BaseFragment {


    @BindView(R.id.edit_layout)
    LinearLayout editLayout;
    @BindView(R.id.left_menu)
    ExpandableListView leftMenu;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;

    List<FenLeiBO> fenLeiBOS;

    private int type = 0;

    public static PlayingFragment getInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        PlayingFragment fragment = new PlayingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_playing, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (type == 0) {
            getFenlei();
        } else {
            getChanPing();
        }
    }

    /**
     * 获取收藏
     */
    private void getFenlei() {
        HttpServerImpl.getProductCollectList(6, 1).subscribe(new HttpResultSubscriber<List<FenLeiBO>>() {
            @Override
            public void onSuccess(List<FenLeiBO> s) {
                fenLeiBOS = s;
                setClassAdapter();
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }

    /**
     * 获取产品
     */
    private void getChanPing() {
        HttpServerImpl.getProductList(6, 1).subscribe(new HttpResultSubscriber<List<FenLeiBO>>() {
            @Override
            public void onSuccess(List<FenLeiBO> s) {
                fenLeiBOS = s;
                setClassAdapter();
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    private void setClassAdapter() {
        ExpandAdapter adapter = new ExpandAdapter(getActivity(), fenLeiBOS);
        leftMenu.setAdapter(adapter);
        leftMenu.expandGroup(0);
        setMsgAdapter(fenLeiBOS.get(0).getSubList().get(0).getProductList().getPageList());
        leftMenu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                adapter.setSelectChild(childPosition);
                if (fenLeiBOS.get(groupPosition).getSubList().get(childPosition).getProductList() != null) {
                    setMsgAdapter(fenLeiBOS.get(groupPosition).getSubList().get(childPosition).getProductList().getPageList());
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
                if (fenLeiBOS.get(groupPosition).getSubList().get(0).getProductList() != null) {
                    setMsgAdapter(fenLeiBOS.get(groupPosition).getSubList().get(0).getProductList().getPageList());
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
     * 设置内容适配器
     */
    private void setMsgAdapter(List<FenLeiBO.SubListBean.ProductListBean.PageListBean> listBeans) {
        LGRecycleViewAdapter<FenLeiBO.SubListBean.ProductListBean.PageListBean> adapter =
                new LGRecycleViewAdapter<FenLeiBO.SubListBean.ProductListBean.PageListBean>(listBeans) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_shoucang;
                    }

                    @Override
                    public void convert(LGViewHolder holder, FenLeiBO.SubListBean.ProductListBean.PageListBean productListBean, int position) {
                        holder.setText(R.id.wenzhang_title, productListBean.getTitle());
                        holder.setText(R.id.wenzhang_time, productListBean.getContent());
                        holder.setImageUrl(getActivity(), R.id.wenzhang_img, productListBean.getSmallImgUrl());
                    }
                };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", adapter.getItem(position).getUrl());
                bundle.putString("title", adapter.getItem(position).getTitle());
                gotoActivity(WebActivity.class, bundle, false);
            }
        });
        recycleView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
