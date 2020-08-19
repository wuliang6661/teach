package cn.teach.equip.view.stypeclass;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.ChanPinBO;
import cn.teach.equip.bean.pojo.FenLeiBO;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.view.SearchActivity;
import cn.teach.equip.view.WebActivity;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class StypeClassActivity extends MVPBaseActivity<StypeClassContract.View, StypeClassPresenter>
        implements StypeClassContract.View {

    @BindView(R.id.right_img)
    LinearLayout rightImg;
    @BindView(R.id.edit_layout)
    LinearLayout editLayout;
    @BindView(R.id.class_recycle)
    RecyclerView classRecycle;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private FenLeiBO classFenlei;

    private int selectFenlei = 0;

    private int levelId2;

    private int pageNum = 1;

    private int levelId3;

    @Override
    protected int getLayout() {
        return R.layout.act_style_class;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("主题教室");
        rightImg.setVisibility(View.VISIBLE);
        classRecycle.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setLayoutManager(new GridLayoutManager(this, 2));
        levelId2 = getIntent().getIntExtra("levelId2", 1);
        String title = getIntent().getStringExtra("title");
        setTitleText(title);
        getProductList();
    }


    @OnClick({R.id.right_img, R.id.edit_layout})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.right_img:
                Intent intent = new Intent(this, SearchActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("isCollect", 0);
                startActivity(intent);
                break;
            case R.id.edit_layout:
                pageNum++;
                getMsg();
                break;
        }
    }


    /**
     * 获取主题教室分类
     */
    private void getProductList() {
        HttpServerImpl.getProductList(6, levelId2, 1).subscribe(new HttpResultSubscriber<List<FenLeiBO>>() {
            @Override
            public void onSuccess(List<FenLeiBO> fenLeiBOS) {
                if (!fenLeiBOS.isEmpty()) {
                    classFenlei = fenLeiBOS.get(0);
                    setClassAdapter();
                }
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    /**
     * 显示分类
     */
    private void setClassAdapter() {
        LGRecycleViewAdapter<FenLeiBO.SubListBean> adapter = new LGRecycleViewAdapter<FenLeiBO.SubListBean>(classFenlei.getSubList()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_shengfen;
            }

            @Override
            public void convert(LGViewHolder holder, FenLeiBO.SubListBean subListBean, int position) {
                TextView provinceText = (TextView) holder.getView(R.id.proce_name);
                provinceText.setText(subListBean.getLevelName3());
                if (selectFenlei == position) {
                    holder.getView(R.id.select_img).setVisibility(View.VISIBLE);
                    provinceText.setTextColor(Color.parseColor("#F7931E"));
                } else {
                    holder.getView(R.id.select_img).setVisibility(View.GONE);
                    provinceText.setTextColor(Color.parseColor("#7F7F7F"));
                }
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                selectFenlei = position;
                adapter.notifyDataSetChanged();
                levelId3 = adapter.getItem(position).getLevelId3();
                pageNum = 1;
                getMsg();
            }
        });
        classRecycle.setAdapter(adapter);
        if (!classFenlei.getSubList().isEmpty()) {
            levelId3 = classFenlei.getSubList().get(0).getLevelId3();
            pageNum = 1;
            getMsg();
        }
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
                setMsgAdapter(chanPinBO.getPageList());
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }

    /**
     * 设置内容适配器
     */
    private void setMsgAdapter(List<ChanPinBO.PageListBean> listBeans) {
        LGRecycleViewAdapter<ChanPinBO.PageListBean> adapter =
                new LGRecycleViewAdapter<ChanPinBO.PageListBean>(listBeans) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_style_wenzhang;
                    }

                    @Override
                    public void convert(LGViewHolder holder, ChanPinBO.PageListBean productListBean, int position) {
                        holder.setText(R.id.wenzhang_title, productListBean.getTitle());
                        holder.setImageUrl(StypeClassActivity.this, R.id.wenzhang_img, productListBean.getSmallImgUrl());
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

}
