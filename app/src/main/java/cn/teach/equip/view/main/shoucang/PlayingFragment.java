package cn.teach.equip.view.main.shoucang;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.base.BaseFragment;
import cn.teach.equip.base.MyApplication;
import cn.teach.equip.bean.pojo.ChanPinBO;
import cn.teach.equip.bean.pojo.FenLeiBO;
import cn.teach.equip.view.WebActivity;
import cn.teach.equip.weight.PopDeleteWindow;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/7/913:45
 * desc   : 玩教具
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
    @BindView(R.id.tianchong)
    View tianchong;

    private int type = 0;
    private int levelId3;
    private int pageNum = 1;

    private boolean isEdit = false;
    private ChanPinBO shoucangList;
    private PopDeleteWindow popDeleteWindow;
    private Map<String, ChanPinBO.PageListBean> selectMaps = new HashMap<>();

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

        if (getArguments() != null) {
            type = getArguments().getInt("type");
            if (type == 1) {
                optionImg.setImageResource(R.drawable.huanyipi);
                optionText.setText("下一页");
            }
        }
        if (type == 1) {
            recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
            tianchong.setVisibility(View.VISIBLE);
        }
        recycleView.setNestedScrollingEnabled(false);
        if (MyApplication.userBO.getUserType() != 2) {
            tianchong.setVisibility(View.VISIBLE);
        } else {
            tianchong.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        getChanPing();
    }

    /**
     * 获取收藏产品列表
     */
    private void getFenlei() {
        HttpServerImpl.getProductCollectList(levelId3, 1).subscribe(new HttpResultSubscriber<ChanPinBO>() {
            @Override
            public void onSuccess(ChanPinBO s) {
                shoucangList = s;
                setMsgAdapter(s.getPageList());
            }

            @Override
            public void onFiled(String message) {
//                showToast2(message);
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
                    if (pageNum > 1) {
                        showToast("这是最后一页！");
                        pageNum = 0;
                        return;
                    }
                    pageNum = 0;
                }
                if (type == 1) {
                    setGridAdapter(chanPinBO.getPageList());
                } else {
                    setMsgAdapter(chanPinBO.getPageList());
                }
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
        } else {
            if (isEdit) {  //正在修改
                isEdit = false;
                optionText.setText("编   辑");
                popDeleteWindow.dismiss();
                selectMaps.clear();
            } else {
                isEdit = true;
                optionText.setText("完成");
                popDeleteWindow = new PopDeleteWindow(getActivity());
                popDeleteWindow.setListener(new PopDeleteWindow.onClickListener() {
                    @Override
                    public void onClick() {
                        if (selectMaps.isEmpty()) {
                            showToast2("请选择要删除的收藏！");
                        } else {
                            StringBuilder builder = new StringBuilder();
                            for (String code : selectMaps.keySet()) {
                                builder.append(code);
                                builder.append(",");
                            }
                            productCollect(builder.substring(0, builder.length() - 1));
                            popDeleteWindow.dismiss();
                            isEdit = false;
                            optionText.setText("编   辑");
                            popDeleteWindow.dismiss();
                            selectMaps.clear();
                        }
                    }
                });
                popDeleteWindow.showAtLocation(getActivity().getWindow().getDecorView());
            }
            setMsgAdapter(shoucangList.getPageList());
        }
    }


    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (isEdit) {  //正在修改
            isEdit = false;
            optionText.setText("编   辑");
            popDeleteWindow.dismiss();
            selectMaps.clear();
            setMsgAdapter(shoucangList.getPageList());
        }
        if (popDeleteWindow != null) {
            popDeleteWindow.dismiss();
        }
    }

    /**
     * 取消收藏
     */
    private void productCollect(String code) {
        HttpServerImpl.productCollect(code).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                getChanPing();
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
                setMsgAdapter(shoucangList.getPageList());
                selectMaps.clear();
            }
        });
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
                    if (levelId3 == 0) {
                        levelId3 = fenLeiBOS.get(0).getSubList().get(0).getLevelId3();
                    }
                    getFenlei();
                } else {
                    if (levelId3 == 0) {
                        levelId3 = fenLeiBOS.get(0).getSubList().get(0).getLevelId3();
                        pageNum = 1;
                    }

                    getMsg();
                }
            }

            @Override
            public void onFiled(String message) {
//                showToast2(message);
            }
        });
    }

    ExpandAdapter adapter;

    private void setClassAdapter() {
        if (adapter != null) {
            adapter.setData(fenLeiBOS);
            return;
        }
        adapter = new ExpandAdapter(getActivity(), fenLeiBOS);
        try {
            leftMenu.setAdapter(adapter);
            leftMenu.expandGroup(0);
            if (type == 0) {
                adapter.setIsShouCang(1);
            }
        } catch (Exception ex) {

        }
        leftMenu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                adapter.setSelectChild(childPosition);
                if (isEdit) {  //正在修改
                    isEdit = false;
                    optionText.setText("编   辑");
                    popDeleteWindow.dismiss();
                    selectMaps.clear();
                }
                if (type == 0) {
                    if (!fenLeiBOS.get(groupPosition).getSubList().isEmpty()) {
                        levelId3 = fenLeiBOS.get(groupPosition).getSubList().get(childPosition).getLevelId3();
                        getFenlei();
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
                if (isEdit) {  //正在修改
                    isEdit = false;
                    optionText.setText("编   辑");
                    popDeleteWindow.dismiss();
                    selectMaps.clear();
                }
                if (type == 0) {
                    if (!fenLeiBOS.get(groupPosition).getSubList().isEmpty()) {
                        levelId3 = fenLeiBOS.get(groupPosition).getSubList().get(0).getLevelId3();
                        getFenlei();
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


    LGRecycleViewAdapter<ChanPinBO.PageListBean> shoucangAdapter;

    /**
     * 设置内容适配器
     */
    private void setMsgAdapter(List<ChanPinBO.PageListBean> listBeans) {
        try {
            if (listBeans.isEmpty()) {
                noneLayout.setVisibility(View.VISIBLE);
                recycleView.setVisibility(View.GONE);
            } else {
                noneLayout.setVisibility(View.GONE);
                recycleView.setVisibility(View.VISIBLE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (shoucangAdapter != null) {
            shoucangAdapter.setData(listBeans);
            return;
        }
        shoucangAdapter =
                new LGRecycleViewAdapter<ChanPinBO.PageListBean>(listBeans) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_shoucang;
                    }

                    @Override
                    public void convert(LGViewHolder holder, ChanPinBO.PageListBean productListBean, int position) {
                        holder.setText(R.id.wenzhang_title, productListBean.getTitle());
                        holder.setText(R.id.wenzhang_time, productListBean.getDesc());
                        holder.setImageUrl(getActivity(), R.id.wenzhang_img, productListBean.getSmallImgUrl());
                        CheckBox checkBox = (CheckBox) holder.getView(R.id.checkbox);
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    selectMaps.put(productListBean.getCode(), productListBean);
                                } else {
                                    selectMaps.remove(productListBean.getCode());
                                }
                            }
                        });
                        checkBox.setVisibility(isEdit ? View.VISIBLE : View.GONE);
                    }
                };
        shoucangAdapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", shoucangAdapter.getItem(position).getUrl());
                bundle.putString("title", shoucangAdapter.getItem(position).getTitle());
                bundle.putInt("targetType", 0);
                gotoActivity(WebActivity.class, bundle, false);
            }
        });
        recycleView.setAdapter(shoucangAdapter);
    }


    LGRecycleViewAdapter<ChanPinBO.PageListBean> chanpinAdapter;

    /**
     * 设置内容适配器 ,产品列表的时候显示这种样式
     */
    private void setGridAdapter(List<ChanPinBO.PageListBean> listBeans) {
        if (listBeans.isEmpty()) {
            noneLayout.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);
        } else {
            noneLayout.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
        }
        if (chanpinAdapter != null) {
            chanpinAdapter.setData(listBeans);
            return;
        }
        chanpinAdapter =
                new LGRecycleViewAdapter<ChanPinBO.PageListBean>(listBeans) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_style_wenzhang;
                    }

                    @Override
                    public void convert(LGViewHolder holder, ChanPinBO.PageListBean productListBean, int position) {
                        holder.setText(R.id.wenzhang_title, productListBean.getTitle());
                        holder.setImageUrl(getActivity(), R.id.wenzhang_img, productListBean.getSmallImgUrl());
                    }
                };
        chanpinAdapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", chanpinAdapter.getItem(position).getUrl());
                bundle.putString("title", chanpinAdapter.getItem(position).getTitle());
                bundle.putInt("targetType", 0);
                gotoActivity(WebActivity.class, bundle, false);
            }
        });
        recycleView.setAdapter(chanpinAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
