package cn.teach.equip.view.peitao;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.ChanPinBO;
import cn.teach.equip.bean.pojo.TagBO;
import cn.teach.equip.mvp.MVPBaseFragment;
import cn.teach.equip.util.ShareUtils;
import cn.teach.equip.view.WebActivity;
import cn.teach.equip.weight.PopDeleteWindow;
import cn.teach.equip.weight.ShaiXuanPopWindwo;
import cn.teach.equip.weight.ShareDialog;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;

/**
 * MVPPlugin
 * 配套材料界面
 */

public class PeitaoFragment extends MVPBaseFragment<PeitaoContract.View, PeitaoPresenter>
        implements PeitaoContract.View {


    @BindView(R.id.shaixuan_layout)
    LinearLayout shaixuanLayout;
    @BindView(R.id.refresh_layout)
    LinearLayout refreshLayout;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;
    @BindView(R.id.none_layout)
    LinearLayout noneLayout;
    @BindView(R.id.right_img)
    ImageView rightImg;
    @BindView(R.id.right_text)
    TextView rightText;
    @BindView(R.id.xuanze_layout)
    RelativeLayout xuanzeLayout;
    @BindView(R.id.none_text)
    TextView noneText;

    private int pageNum = 1;

    private int type = 0;  //默认是收藏   1是产品

    Map<Integer, Set<Integer>> sessionMap;

    private String tagIds = "";

    private boolean isEdit = false;

    private List<ChanPinBO.PageListBean> pageListBeans;

    private Map<String, ChanPinBO.PageListBean> selectMaps = new HashMap<>();

    private PopDeleteWindow popDeleteWindow;

    public static PeitaoFragment getInstanse(int type) {
        PeitaoFragment fragment = new PeitaoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_peitao, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        type = getArguments().getInt("type");
    }


    @OnClick({R.id.refresh_layout, R.id.shaixuan_layout})
    public void clickMenu(View view) {
        switch (view.getId()) {
            case R.id.refresh_layout:
                if (type == 0) {
                    if (isEdit) {  //正在修改
                        isEdit = false;
                        rightText.setText("编   辑");
                        popDeleteWindow.dismiss();
                        selectMaps.clear();
                    } else {
                        isEdit = true;
                        rightText.setText("完成");
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
                                    rightText.setText("编   辑");
                                    popDeleteWindow.dismiss();
                                    selectMaps.clear();
                                }
                            }
                        });
                        popDeleteWindow.showAtLocation(getActivity().getWindow().getDecorView());
                    }
                    setAdapter();
                } else {
                    pageNum++;
                    getPeiTao();
                }
                break;
            case R.id.shaixuan_layout:
                getProductTagList();
                break;
        }
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (type == 0) {   //收藏
            rightImg.setImageResource(R.drawable.bianji);
            rightText.setText("编   辑");
            noneText.setText("还未收藏产品");
            getShouCang();
        } else {
            rightImg.setImageResource(R.drawable.huanyipi);
            rightText.setText("下一页");
            getPeiTao();
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (type == 0) {
            isEdit = false;
            rightText.setText("编   辑");
            setAdapter();
        }
        if(popDeleteWindow != null){
            popDeleteWindow.dismiss();
        }
    }

    /**
     * 获取筛选标签
     */
    private void getProductTagList() {
        HttpServerImpl.getProductTagList(7).subscribe(new HttpResultSubscriber<List<TagBO>>() {
            @Override
            public void onSuccess(List<TagBO> s) {
                showShaiXuan(s);
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    /**
     * 显示筛选项的popwindow
     */
    private void showShaiXuan(List<TagBO> s) {
        ShaiXuanPopWindwo popWindow = new ShaiXuanPopWindwo(getActivity(), s);
        popWindow.setCommitListener(new ShaiXuanPopWindwo.onCommitListener() {
            @Override
            public void onCommit(Map<Integer, Set<Integer>> session, Map<String, Integer> ids) {
                sessionMap = session;
                if (ids.isEmpty()) {
                    tagIds = "";
                } else {
                    tagIds = "";
                    for (int id : ids.values()) {
                        tagIds += (id + ",");
                    }
                    tagIds = tagIds.substring(0, tagIds.length() - 1);
                }
                pageNum = 1;
                if (type == 0) {
                    getShouCang();
                } else {
                    getPeiTao();
                }
            }
        });
        if (sessionMap != null && !sessionMap.isEmpty()) {
            popWindow.setSelectTags(sessionMap);
        }
        popWindow.showAsDropDown(xuanzeLayout);
    }


    /**
     * 获取配套商品
     */
    private void getPeiTao() {
        HttpServerImpl.getPeiTao(7, StringUtils.isEmpty(tagIds) ? null : tagIds,
                pageNum).subscribe(new HttpResultSubscriber<ChanPinBO>() {
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
                pageListBeans = chanPinBO.getPageList();
                setAdapter();
            }

            @Override
            public void onFiled(String message) {
//                showToast2(message);
            }
        });
    }


    /**
     * 获取收藏商品
     */
    private void getShouCang() {
        HttpServerImpl.getPeiTaoShouCang(7, StringUtils.isEmpty(tagIds) ? null : tagIds,
                pageNum).subscribe(new HttpResultSubscriber<ChanPinBO>() {
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
                pageListBeans = chanPinBO.getPageList();
                setAdapter();
            }

            @Override
            public void onFiled(String message) {
//                showToast2(message);
            }
        });
    }


    /**
     * 设置产品列表
     */
    private void setAdapter() {
        try {
            if (pageListBeans.isEmpty()) {
                noneLayout.setVisibility(View.VISIBLE);
                recycleView.setVisibility(View.GONE);
            } else {
                noneLayout.setVisibility(View.GONE);
                recycleView.setVisibility(View.VISIBLE);
            }
        }catch (Exception ex){

        }
        LGRecycleViewAdapter<ChanPinBO.PageListBean> adapter =
                new LGRecycleViewAdapter<ChanPinBO.PageListBean>(pageListBeans) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_peitao;
                    }

                    @Override
                    public void convert(LGViewHolder holder, ChanPinBO.PageListBean pageListBean, int position) {
//                        if (isEdit) {
//                            holder.getView(R.id.content_layout).setVisibility(View.GONE);
//                            holder.getView(R.id.edit_shoucang_layout).setVisibility(View.VISIBLE);
//                        } else {
//                            holder.getView(R.id.content_layout).setVisibility(View.VISIBLE);
//                            holder.getView(R.id.edit_shoucang_layout).setVisibility(View.GONE);
//                        }
                        holder.setText(R.id.shop_title, pageListBean.getTitle());
                        holder.setText(R.id.shop_message, pageListBean.getDesc());
                        holder.setImageUrl(getActivity(), R.id.shop_img, pageListBean.getSmallImgUrl());
                        CheckBox checkBox = (CheckBox) holder.getView(R.id.checkbox);
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    selectMaps.put(pageListBean.getCode(), pageListBean);
                                } else {
                                    selectMaps.remove(pageListBean.getCode());
                                }
                            }
                        });
                        checkBox.setVisibility(isEdit ? View.VISIBLE : View.GONE);
                    }
                };
        adapter.setOnItemClickListener(R.id.fenxiang, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                share(adapter.getItem(position).getTitle(), adapter.getItem(position).getDesc(),
                        adapter.getItem(position).getUrl());
            }
        });
        adapter.setOnItemClickListener(R.id.shanchu, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                productCollect(adapter.getItem(position).getCode());
            }
        });
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


    /**
     * 取消收藏
     */
    private void productCollect(String code) {
        HttpServerImpl.productCollect(code).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                getShouCang();
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    /**
     * 分享
     */
    public void share(String title, String content, String url) {
        new ShareDialog().showShareDialog(new ShareDialog.OnClickShare() {
            @Override
            public void share(int flag) {
                ShareUtils.shareHtml(title, content, url, flag);
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
