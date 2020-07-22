package cn.teach.equip.view.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.base.BaseFragment;

public class NoneFragment extends BaseFragment {


    @BindView(R.id.none_text)
    TextView noneText;
    Unbinder unbinder;

    public static NoneFragment getInstanse(String text) {
        NoneFragment noneFragment = new NoneFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", text);
        noneFragment.setArguments(bundle);
        return noneFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.none_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            noneText.setText(getArguments().getString("title", "还未收藏产品"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
