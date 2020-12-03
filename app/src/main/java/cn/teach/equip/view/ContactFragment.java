package cn.teach.equip.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import butterknife.BindView;
import cn.teach.equip.R;
import cn.teach.equip.base.BaseActivity;

/**
 * 联系我们
 */
public class ContactFragment extends BaseActivity {


    @BindView(R.id.back)
    LinearLayout back;
//    Unbinder unbinder;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fra_contact, null);
//        unbinder = ButterKnife.bind(this, view);
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pop();
//            }
//        });
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }

    @Override
    protected int getLayout() {
        return R.layout.fra_contact;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
    }
}
