package cn.teach.equip.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.teach.equip.R;
import cn.teach.equip.base.BaseActivity;

/**
 * 反馈界面
 */
public class FanKuiActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.act_fankui;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("建议反馈");
    }
}
