package cn.teach.equip.view.wenzhanglist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.WenZhangListBo;
import cn.teach.equip.mvp.MVPBaseActivity;
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

    private int selectType = 1; // 默认装备指南

    private int pageNum = 1;

    private List<WenZhangListBo.PageListBean> list;

    LGRecycleViewAdapter<WenZhangListBo.PageListBean> adapter;

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
        getWenZhangList();
    }


    private void addListener() {
        srlPage.setEnableAutoLoadMore(true);
        srlPage.setEnableLoadMore(true);
        srlPage.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srlPage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNum++;
                        getWenZhangList();
                    }
                }, 1000);

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                srlPage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNum = 1;
                        getWenZhangList();
                    }
                }, 1000);
            }
        });
    }

    private void getWenZhangList() {
        HttpServerImpl.getArticleList(selectType, pageNum).subscribe(new HttpResultSubscriber<WenZhangListBo>() {
            @Override
            public void onSuccess(WenZhangListBo wenZhangListBo) {
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
            }
        };
        recycleView.setAdapter(adapter);
    }


}
