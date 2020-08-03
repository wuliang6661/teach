package cn.teach.equip.view.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.base.BaseActivity;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;

/**
 * 引导页2
 */
public class SplashPage2  extends BaseActivity {


    @BindView(R.id.point_recycle)
    RecyclerView pointRecycle;

    @Override
    protected int getLayout() {
        return R.layout.act_splash2_page;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        pointRecycle.setLayoutManager(manager);
        pointRecycle.setNestedScrollingEnabled(false);
        setBannerPointAdapter();
    }


    /**
     * 设置banner点适配器
     */
    private void setBannerPointAdapter() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        LGRecycleViewAdapter<String> adapter = new LGRecycleViewAdapter<String>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_banner_point;
            }

            @Override
            public void convert(LGViewHolder holder, String bannerBO, int position) {
                if (1 == position) {
                    holder.getView(R.id.un_select).setVisibility(View.GONE);
                    holder.getView(R.id.select_point).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.un_select).setVisibility(View.VISIBLE);
                    holder.getView(R.id.select_point).setVisibility(View.GONE);
                }
            }
        };
        pointRecycle.setAdapter(adapter);
    }


    @OnClick(R.id.content_view)
    public void onClick(){
        gotoActivity(SplashPage3.class,true);
    }
}
