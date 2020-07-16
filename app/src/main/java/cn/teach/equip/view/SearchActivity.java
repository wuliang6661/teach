package cn.teach.equip.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.base.BaseActivity;
import cn.teach.equip.bean.pojo.ProvinceBO;
import cn.teach.equip.bean.pojo.WenZhangListBo;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.et_sousuo)
    EditText etSousuo;
    @BindView(R.id.bt_sousuo)
    ImageView btSousuo;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.lishi_text)
    TextView lishiText;

    private int type;  //搜索内容

    private int isCollect = 0;// 默认搜索全部

    @Override
    protected int getLayout() {
        return R.layout.act_search;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recycleView.setLayoutManager(new LinearLayoutManager(this));
        type = getIntent().getIntExtra("type", 0);   //默认搜索文章和产品
        isCollect = getIntent().getIntExtra("isCollect", 0);
    }


    @OnClick({R.id.back_img, R.id.bt_sousuo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.bt_sousuo:
                String content = etSousuo.getText().toString().trim();
                if (type == 0) {
                    search(content);
                } else {
                    searchAddress(content);
                }
                break;
        }
    }

    /**
     * 搜索地址
     */
    private void searchAddress(String content) {
        HttpServerImpl.getLocationList(null, null, null, null, null, content)
                .subscribe(new HttpResultSubscriber<List<ProvinceBO>>() {
                    @Override
                    public void onSuccess(List<ProvinceBO> provinceBOS) {
                        setSeachAdapter(provinceBOS);
                    }

                    @Override
                    public void onFiled(String message) {
                        showToast2(message);
                    }
                });
    }


    /**
     * 设置地址适配器
     */
    private void setSeachAdapter(List<ProvinceBO> provinceBOS) {

    }


    /**
     * 搜索文章
     */
    private void search(String content) {
        HttpServerImpl.search(0, isCollect, content).subscribe(new HttpResultSubscriber<WenZhangListBo>() {
            @Override
            public void onSuccess(WenZhangListBo s) {
                setAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    private void setAdapter(WenZhangListBo wenZhangListBo) {
        LGRecycleViewAdapter<WenZhangListBo.PageListBean> adapter =
                new LGRecycleViewAdapter<WenZhangListBo.PageListBean>(wenZhangListBo.getPageList()) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_unit;
                    }

                    @Override
                    public void convert(LGViewHolder holder, WenZhangListBo.PageListBean pageListBean, int position) {
                        holder.setText(R.id.unit_name, pageListBean.getTitle());
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
