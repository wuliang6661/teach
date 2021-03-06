package cn.teach.equip.view.selectcity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.ProvinceBO;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.view.SearchActivity;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SelectCityActivity extends MVPBaseActivity<SelectCityContract.View, SelectCityPresenter>
        implements SelectCityContract.View {

    @BindView(R.id.left_menu)
    RecyclerView leftMenu;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    private int selectProvince = 0;

    ContactAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.act_select_city;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        leftMenu.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        getLocationList(null);
    }


    public void initAdapter() {
//        mAdapter = new ContactAdapter(this);
//        recycleView.setAdapter(mAdapter);
//        recycleView.setOverlayStyle_Center();
////        indexableLayout.setOverlayStyle_MaterialDesign(Color.RED);
//        // 全字母排序。  排序规则设置为：每个字母都会进行比较排序；速度较慢
//        recycleView.setCompareMode(IndexableLayout.MODE_FAST);

    }


    @OnClick(R.id.back_img)
    public void back() {
        finish();
    }


    @OnClick(R.id.right_img)
    public void search() {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("type", 1);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ProvinceBO.CityListBean cityListBean) {
        finish();
    }

    /**
     * 获取省市区
     */
    private void getLocationList(String shengId) {
        HttpServerImpl.getLocationList(shengId, null, null, null,
                null, null).subscribe(new HttpResultSubscriber<List<ProvinceBO>>() {
            @Override
            public void onSuccess(List<ProvinceBO> s) {
                setProvinceAdapter(s);
                setCityAdapter(s.get(0));
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    private void setProvinceAdapter(List<ProvinceBO> s) {
        LGRecycleViewAdapter<ProvinceBO> adapter = new LGRecycleViewAdapter<ProvinceBO>(s) {

            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_shengfen;
            }

            @Override
            public void convert(LGViewHolder holder, ProvinceBO provinceBO, int position) {
                TextView provinceText = (TextView) holder.getView(R.id.proce_name);
                provinceText.setText(provinceBO.getProvinceName());
                if (selectProvince == position) {
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
                selectProvince = position;
                adapter.notifyDataSetChanged();
                setCityAdapter(s.get(position));
            }
        });
        leftMenu.setAdapter(adapter);
    }


    private void setCityAdapter(ProvinceBO provinceBO) {
        LGRecycleViewAdapter<ProvinceBO.CityListBean> adapter = new LGRecycleViewAdapter<ProvinceBO.CityListBean>(provinceBO.getCityList()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_city;
            }

            @Override
            public void convert(LGViewHolder holder, ProvinceBO.CityListBean cityListBean, int position) {
                holder.setText(R.id.city_name, cityListBean.getCityName());
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("provinceId", provinceBO.getProvinceId());
                intent.putExtra("cityId", adapter.getItem(position).getCityId());
                intent.putExtra("provinceName", provinceBO.getProvinceName());
                intent.putExtra("cityName", adapter.getItem(position).getCityName());
                intent.putExtra("hasUnit", adapter.getItem(position).getHasUnit());
                setResult(0x11, intent);
                finish();
            }
        });
        recycleView.setAdapter(adapter);
//        mAdapter.setDatas(provinceBO.getCityList());
//        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<ProvinceBO.CityListBean>() {
//            @Override
//            public void onItemClick(View v, int originalPosition, int currentPosition, ProvinceBO.CityListBean entity) {
//                if (originalPosition >= 0) {
//                    Intent intent = new Intent();
//                    intent.putExtra("provinceId", provinceBO.getProvinceId());
//                    intent.putExtra("cityId", entity.getCityId());
//                    intent.putExtra("provinceName", provinceBO.getProvinceName());
//                    intent.putExtra("cityName", entity.getCityName());
//                    intent.putExtra("hasUnit", entity.getHasUnit());
//                    setResult(0x11, intent);
//                    finish();
//                }
//            }
//        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
