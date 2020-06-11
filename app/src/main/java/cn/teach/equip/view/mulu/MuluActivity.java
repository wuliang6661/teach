package cn.teach.equip.view.mulu;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import cn.teach.equip.R;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.weight.lgrecycleadapter.LGRecycleViewAdapter;
import cn.teach.equip.weight.lgrecycleadapter.LGViewHolder;


/**
 * MVPPlugin
 * 目录下载页面
 */

public class MuluActivity extends MVPBaseActivity<MuluContract.View, MuluPresenter> implements MuluContract.View {

    @BindView(R.id.right_img)
    LinearLayout rightImg;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @Override
    protected int getLayout() {
        return R.layout.act_mulu;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("目录下载");
        rightImg.setVisibility(View.VISIBLE);

        recycleView.setLayoutManager(new LinearLayoutManager(this));
        setAdapter();
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        LGRecycleViewAdapter<String> adapter = new LGRecycleViewAdapter<String>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_mulu;
            }

            @Override
            public void convert(LGViewHolder holder, String s, int position) {
                if (position == 0) {
                    holder.setImageResurce(R.id.item_img, R.drawable.xiazaiwancheng);
                    holder.setText(R.id.progress_text, "下载完成");
                }
            }
        };
        recycleView.setAdapter(adapter);
    }


}
