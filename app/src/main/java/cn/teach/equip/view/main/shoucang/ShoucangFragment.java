package cn.teach.equip.view.main.shoucang;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.FenLeiBO;
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
    ExpandableListView leftMenu;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;
    @BindView(R.id.tab_linerlayout)
    TabLinerLayout tabLinerlayout;


    private int selectPosition = 4;


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

        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tabLinerlayout.setListener(new TabLinerLayout.onClickBarListener() {
            @Override
            public void clickBar(int position) {
                selectPosition = position + 4;
                getFenlei(position + 4, 1);
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        getFenlei(selectPosition, 1);
    }

    private void getFenlei(int levelType, int page) {
        HttpServerImpl.getProductCollectList(levelType, page).subscribe(new HttpResultSubscriber<List<FenLeiBO>>() {
            @Override
            public void onSuccess(List<FenLeiBO> s) {

            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
