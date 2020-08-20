package cn.teach.equip.view.main.shoucang;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.teach.equip.R;
import cn.teach.equip.base.MyApplication;
import cn.teach.equip.mvp.MVPBaseFragment;
import cn.teach.equip.view.SearchActivity;
import cn.teach.equip.view.main.NoneFragment;
import cn.teach.equip.view.peitao.PeitaoFragment;
import cn.teach.equip.weight.TabLinerLayout;
import cn.teach.equip.zxing.activity.CaptureActivity;

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
    PeitaoFragment peitaoFragment;

    List<Fragment> fragments;

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

        if (!StringUtils.isEmpty(MyApplication.token)) {
            fragment = new PlayingFragment();
            peitaoFragment = PeitaoFragment.getInstanse(0);
//            fragments = new ArrayList<>();
//            fragments.add(new NoneFragment());
//            fragments.add(new NoneFragment());
//            fragments.add(fragment);
//            fragments.add(peitaoFragment);
            tabLinerlayout.setListener(new TabLinerLayout.onClickBarListener() {
                @Override
                public void clickBar(int position) {
                    switch (position) {
                        case 0:
                            FragmentUtils.replace(getFragmentManager(), new NoneFragment(), R.id.fragment_container);
                            break;
                        case 1:
                            FragmentUtils.replace(getFragmentManager(), new NoneFragment(), R.id.fragment_container);
                            break;
                        case 2:
                            FragmentUtils.replace(getFragmentManager(), fragment, R.id.fragment_container);
                            break;
                        case 3:
                            FragmentUtils.replace(getFragmentManager(), peitaoFragment, R.id.fragment_container);
                            break;
                    }
//                    FragmentUtils.showHide(position,fragments);
                }
            });
            FragmentUtils.replace(getFragmentManager(), fragment, R.id.fragment_container);
        }
    }

    @OnClick({R.id.saoma, R.id.sousuo})
    public void clickTitle(View view) {
        switch (view.getId()) {
            case R.id.saoma:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        ) {

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                            }, 1);
                } else {
                    gotoActivity(CaptureActivity.class, false);
                }
                break;
            case R.id.sousuo:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("isCollect", 1);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    gotoActivity(CaptureActivity.class, false);
                } else {
                    showWaringDialog();
                }
                break;
            }
        }
    }

    private void showWaringDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("警告！")
                .setMessage("请前往设置->应用->教育装备->权限中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确定", null).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
