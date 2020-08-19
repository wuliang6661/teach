package cn.teach.equip.view.jiaoyuchanpin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.FragmentUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.base.MyApplication;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.view.SearchActivity;
import cn.teach.equip.view.main.NoneFragment;
import cn.teach.equip.view.main.shoucang.PlayingFragment;
import cn.teach.equip.view.mulu.MuluActivity;
import cn.teach.equip.weight.TabLinerLayout;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class JiaoyuchanpinActivity extends MVPBaseActivity<JiaoyuchanpinContract.View, JiaoyuchanpinPresenter>
        implements JiaoyuchanpinContract.View {

    @BindView(R.id.tab_linerlayout)
    TabLinerLayout tabLinerlayout;
    @BindView(R.id.down_load_layout)
    LinearLayout downLoadLayout;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.right_img)
    LinearLayout rightImg;

    private int type;

    @Override
    protected int getLayout() {
        return R.layout.act_jiaoyu_chanpin;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("教学设备");
        rightImg.setVisibility(View.VISIBLE);
        type = getIntent().getIntExtra("type", Integer.MAX_VALUE);
//        FragmentUtils.replace(getSupportFragmentManager(), NoneFragment.getInstanse("暂无产品"), R.id.fragment_container);
        tabLinerlayout.setListener(new TabLinerLayout.onClickBarListener() {
            @Override
            public void clickBar(int position) {
                switch (position) {
                    case 0:
                        FragmentUtils.replace(getSupportFragmentManager(), NoneFragment.getInstanse("暂无产品"), R.id.fragment_container);
                        break;
                    case 1:
                        FragmentUtils.replace(getSupportFragmentManager(), NoneFragment.getInstanse("暂无产品"), R.id.fragment_container);
                        break;
                    case 2:
                        FragmentUtils.replace(getSupportFragmentManager(), PlayingFragment.getInstance(1), R.id.fragment_container);
                        break;
                    case 3:
                        FragmentUtils.replace(getSupportFragmentManager(), NoneFragment.getInstanse("暂无产品"), R.id.fragment_container);
                        break;
                }
            }
        });
        if (type == Integer.MAX_VALUE) {
            tabLinerlayout.setSelectTab(2);
        } else {
            tabLinerlayout.setSelectTab(type);
        }
        if (MyApplication.userBO.getUserType() == 2) {  //企业用户
            layoutBottom.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.right_img)
    public void search() {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("type", 0);
        intent.putExtra("isCollect", 0);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.down_load_layout)
    public void downLoad() {
        gotoActivity(MuluActivity.class, false);
    }
}
