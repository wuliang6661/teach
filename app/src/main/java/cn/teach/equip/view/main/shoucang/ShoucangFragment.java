package cn.teach.equip.view.main.shoucang;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.mvp.MVPBaseFragment;
import cn.teach.equip.weight.TabLinerLayout;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ShoucangFragment extends MVPBaseFragment<ShoucangContract.View, ShoucangPresenter>
        implements ShoucangContract.View {


    @BindView(R.id.sousuo)
    LinearLayout sousuo;
    @BindView(R.id.saoma)
    ImageView saoma;
    @BindView(R.id.edit_layout)
    LinearLayout editLayout;
    @BindView(R.id.left_menu)
    RecyclerView leftMenu;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;
    @BindView(R.id.tab_linerlayout)
    TabLinerLayout tabLinerlayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_shoucang, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        leftMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tabLinerlayout.setListener(new TabLinerLayout.onClickBarListener() {
            @Override
            public void clickBar(int position) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
