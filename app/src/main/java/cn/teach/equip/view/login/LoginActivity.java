package cn.teach.equip.view.login;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.view.main.MainActivity;
import cn.teach.equip.view.register.RegisterActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yanzhengma)
    EditText etYanzhengma;
    @BindView(R.id.login_bt)
    TextView loginBt;
    @BindView(R.id.tx_registest)
    TextView txRegistest;

    @Override
    protected int getLayout() {
        return R.layout.act_login;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txRegistest.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);  //下划线
        txRegistest.getPaint().setAntiAlias(true);//设置抗锯齿，使线条平滑
    }


    @OnClick(R.id.login_bt)
    public void login() {
        gotoActivity(MainActivity.class, true);
    }


    @OnClick(R.id.tx_registest)
    public void register() {
        gotoActivity(RegisterActivity.class, false);
    }

}
