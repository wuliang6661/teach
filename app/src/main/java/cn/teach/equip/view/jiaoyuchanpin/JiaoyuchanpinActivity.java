package cn.teach.equip.view.jiaoyuchanpin;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.FragmentUtils;

import butterknife.BindView;
import cn.teach.equip.R;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.view.main.NoneFragment;
import cn.teach.equip.view.main.shoucang.PlayingFragment;
import cn.teach.equip.weight.TabLinerLayout;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class JiaoyuchanpinActivity extends MVPBaseActivity<JiaoyuchanpinContract.View, JiaoyuchanpinPresenter>
        implements JiaoyuchanpinContract.View {

    @BindView(R.id.tab_linerlayout)
    TabLinerLayout tabLinerlayout;

    @Override
    protected int getLayout() {
        return R.layout.act_jiaoyu_chanpin;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("教学设备");

        FragmentUtils.replace(getSupportFragmentManager(), NoneFragment.getInstanse("暂无产品"), R.id.fragment_container);
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
    }
}
