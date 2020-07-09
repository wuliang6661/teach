package cn.teach.equip.view.main.shoucang;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.FragmentUtils;

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
    Unbinder unbinder;
    @BindView(R.id.tab_linerlayout)
    TabLinerLayout tabLinerlayout;

    PlayingFragment fragment;


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

        fragment = new PlayingFragment();
        tabLinerlayout.setListener(new TabLinerLayout.onClickBarListener() {
            @Override
            public void clickBar(int position) {
                switch (position) {
                    case 0:
//                        FragmentUtils.replace(getActivity().getSupportFragmentManager(), fragment, R.id.fragment_container);
                        break;
                    case 1:

                        break;
                    case 2:
                        FragmentUtils.replace(getFragmentManager(), fragment, R.id.fragment_container);
                        break;
                    case 3:

                        break;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
