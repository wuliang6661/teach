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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.base.BaseFragment;
import cn.teach.equip.bean.pojo.ChanPinBO;
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
    @BindView(R.id.none_layout)
    LinearLayout noneLayout;
    @BindView(R.id.option_img)
    ImageView optionImg;
    @BindView(R.id.option_text)
    TextView optionText;

    private int type = 0;

    private int levelId3;
    private int pageNum = 1;

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
            if (type == 1) {
                optionImg.setImageResource(R.drawable.huanyipi);
                optionText.setText("换一批");
            }
        }
        getChanPing();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    /**
     * 获取收藏产品列表
     */
    private void getFenlei(int levelId3) {
        HttpServerImpl.getProductCollectList(levelId3, 1).subscribe(new HttpResultSubscriber<ChanPinBO>() {
            @Override
            public void onSuccess(ChanPinBO s) {
                setMsgAdapter(s.getPageList());
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }

    /**
     * 获取产品列表
     */
    private void getMsg() {
        HttpServerImpl.getProductInfoList(levelId3, pageNum).subscribe(new HttpResultSubscriber<ChanPinBO>() {
            @Override
            public void onSuccess(ChanPinBO chanPinBO) {
                if (chanPinBO.getPageList().isEmpty()) {
                    pageNum = 0;
                }
                setMsgAdapter(chanPinBO.getPageList());
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }

    @OnClick(R.id.edit_layout)
    public void onClickImg() {
        if (type == 1) {
            pageNum++;
            getMsg();
        }
    }


    /**
     * 获取产品分类
     */
    private void getChanPing() {
        HttpServerImpl.getProductList(6, 1).subscribe(new HttpResultSubscriber<List<FenLeiBO>>() {
            @Override
            public void onSuccess(List<FenLeiBO> s) {
                fenLeiBOS = s;
                setClassAdapter();
                if (type == 0) {
                    getFenlei(fenLeiBOS.get(0).getSubList().get(0).getLevelId3());
                } else {
                    levelId3 = fenLeiBOS.get(0).getSubList().get(0).getLevelId3();
                    pageNum = 1;
                    getMsg();
                }
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
        leftMenu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                adapter.setSelectChild(childPosition);
                if (type == 0) {
                    if (!fenLeiBOS.get(groupPosition).getSubList().isEmpty()) {
                        getFenlei(fenLeiBOS.get(groupPosition).getSubList().get(childPosition).getLevelId3());
                    }
                } else {
                    if (!fenLeiBOS.get(groupPosition).getSubList().isEmpty()) {
                        levelId3 = fenLeiBOS.get(groupPosition).getSubList().get(childPosition).getLevelId3();
                        pageNum = 1;
                        getMsg();
                    }
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
                if (type == 0) {
                    if (!fenLeiBOS.get(groupPosition).getSubList().isEmpty()) {
                        getFenlei(fenLeiBOS.get(groupPosition).getSubList().get(0).getLevelId3());
                    }
                } else {
                    if (!fenLeiBOS.get(groupPosition).getSubList().isEmpty()) {
                        levelId3 = fenLeiBOS.get(groupPosition).getSubList().get(0).getLevelId3();
                        pageNum = 1;
                        getMsg();
                    }
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
    private void setMsgAdapter(List<ChanPinBO.PageListBean> listBeans) {
        if (listBeans.isEmpty()) {
            noneLayout.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
        } else {
            noneLayout.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
        }
        LGRecycleViewAdapter<ChanPinBO.PageListBean> adapter =
                new LGRecycleViewAdapter<ChanPinBO.PageListBean>(listBeans) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_shoucang;
                    }

                    @Override
                    public void convert(LGViewHolder holder, ChanPinBO.PageListBean productListBean, int position) {
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
