package cn.teach.equip.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import cn.teach.equip.R;
import cn.teach.equip.base.BaseActivity;
import cn.teach.equip.view.login.LoginActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.act_splash;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoActivity(LoginActivity.class, true);
            }
        }, 2000);
    }
}
