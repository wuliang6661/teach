package cn.teach.equip.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;

import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.base.BaseActivity;
import cn.teach.equip.base.MyApplication;
import cn.teach.equip.bean.pojo.UserBO;
import cn.teach.equip.view.main.MainActivity;
import cn.teach.equip.view.splash.SplashPage1;

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
                boolean isFirst = MyApplication.spUtils.getBoolean("isFirst", true);
                if (!isFirst) {
                    String token = MyApplication.spUtils.getString("token");
                    if (StringUtils.isEmpty(token)) {
                        gotoActivity(MainActivity.class, true);
                    } else {
                        MyApplication.token = token;
                        login(null, null);
                    }
                } else {
                    MyApplication.spUtils.put("isFirst", false);
                    gotoActivity(SplashPage1.class, true);
                }
            }
        }, 3000);
    }


    public void login(String phone, String smsCode) {
        HttpServerImpl.login(phone, smsCode).subscribe(new HttpResultSubscriber<UserBO>() {
            @Override
            public void onSuccess(UserBO s) {
                MyApplication.token = s.getUserToken();
                MyApplication.userBO = s;
                MyApplication.spUtils.put("token", MyApplication.token);
                gotoActivity(MainActivity.class, true);
            }

            @Override
            public void onFiled(String message) {
                MyApplication.token = null;
                gotoActivity(MainActivity.class, true);
            }
        });
    }
}
