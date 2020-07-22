package cn.teach.equip.view.selectunit;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.UnitBO;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SelectUnitActivity extends MVPBaseActivity<SelectUnitContract.View, SelectUnitPresenter>
        implements SelectUnitContract.View {

    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.sousuo)
    ImageView sousuo;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @Override
    protected int getLayout() {
        return R.layout.act_select_unit;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recycleView.setLayoutManager(new LinearLayoutManager(this));
        getUnitList("");
    }


    @OnClick({R.id.close, R.id.sousuo})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                break;
            case R.id.sousuo:
                String text = editSearch.getText().toString().trim();
                getUnitList(text);
                break;
        }
    }


    private void getUnitList(String name) {
        HttpServerImpl.getUnitList(name).subscribe(new HttpResultSubscriber<List<UnitBO>>() {
            @Override
            public void onSuccess(List<UnitBO> s) {
                setAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }

    private void setAdapter(List<UnitBO> list) {
        LGRecycleViewAdapter<UnitBO> adapter = new LGRecycleViewAdapter<UnitBO>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_unit;
            }

            @Override
            public void convert(LGViewHolder holder, UnitBO unitBO, int position) {
                holder.setText(R.id.unit_name, unitBO.getUnitName());
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("data", adapter.getItem(position));
                setResult(0x22, intent);
                finish();
            }
        });
        recycleView.setAdapter(adapter);
    }


}
