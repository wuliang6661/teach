package cn.teach.equip.view.wenzhanglist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.WenZhangListBo;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.view.WebActivity;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WenzhangListActivity extends MVPBaseActivity<WenzhangListContract.View, WenzhangListPresenter>
        implements WenzhangListContract.View {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.srl_page)
    SmartRefreshLayout srlPage;
    @BindView(R.id.et_sousuo)
    EditText etSousuo;
    @BindView(R.id.bt_sousuo)
    ImageView btSousuo;
    @BindView(R.id.sousuo)
    LinearLayout sousuo;

    private int selectType = 1; // 默认装备指南

    private int pageNum = 1;

    private List<WenZhangListBo.PageListBean> list;

    LGRecycleViewAdapter<WenZhangListBo.PageListBean> adapter;

    private String key;

    @Override
    protected int getLayout() {
        return R.layout.act_wenzhang_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        int type = getIntent().getExtras().getInt("type", 0);
        switch (type) {
            case 0:   //装备指南
                setTitleText("装备指南");
                selectType = 1;
                break;
            case 1:   //融合案例
                setTitleText("融合案例");
                selectType = 2;
                break;
            case 2:   //过期风采
                setTitleText("国企风采");
                selectType = 3;
                break;
        }
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        addListener();
        getWenZhangList(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void addListener() {
        srlPage.setEnableAutoLoadMore(true);
        srlPage.setEnableLoadMore(true);
//        srlPage.setRefreshHeader(new FunG)
        srlPage.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srlPage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNum++;
                        getWenZhangList(key);
                    }
                }, 1000);

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlPage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        key = null;
                        pageNum = 1;
                        getWenZhangList(key);
                    }
                }, 1000);
            }
        });
    }


    /**
     * 搜索
     */
    @OnClick(R.id.bt_sousuo)
    public void sousuo() {
        key = etSousuo.getText().toString().trim();
        pageNum = 1;
        getWenZhangList(key);
    }


    private void getWenZhangList(String searchContent) {
        showProgress();
        HttpServerImpl.getArticleList(selectType, pageNum, searchContent).subscribe(new HttpResultSubscriber<WenZhangListBo>() {
            @Override
            public void onSuccess(WenZhangListBo wenZhangListBo) {
                stopProgress();
                if (pageNum == 1) {
                    list = wenZhangListBo.getPageList();
                    setAdapter();
                } else {
                    if (wenZhangListBo.getPageList().isEmpty()) {
                        pageNum--;
                    }
                    list.addAll(wenZhangListBo.getPageList());
                    adapter.setData(list);
                }
                srlPage.finishLoadMore();
                srlPage.finishRefresh();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast2(message);
            }
        });
    }


    private void setAdapter() {
        adapter = new LGRecycleViewAdapter<WenZhangListBo.PageListBean>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_wenzhang;
            }

            @Override
            public void convert(LGViewHolder holder, WenZhangListBo.PageListBean pageListBean, int position) {
                holder.setImageUrl(WenzhangListActivity.this, R.id.wenzhang_img, pageListBean.getSmallImgUrl());
                holder.setText(R.id.wenzhang_title, pageListBean.getTitle());
                holder.setText(R.id.wenzhang_time, pageListBean.getAddTime());
                if (pageListBean.getReadStatus() == 0) {
                    holder.getView(R.id.zhuangbei_point).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.zhuangbei_point).setVisibility(View.GONE);
                }
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", adapter.getItem(position).getUrl());
                bundle.putString("title", adapter.getItem(position).getTitle());
                bundle.putInt("targetType", adapter.getItem(position).getTargetType());
                list.get(position).setReadStatus(1);
                gotoActivity(WebActivity.class, bundle, false);
            }
        });
        recycleView.setAdapter(adapter);
    }


}
