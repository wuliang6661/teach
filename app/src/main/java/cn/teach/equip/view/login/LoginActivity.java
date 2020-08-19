package cn.teach.equip.view.login;


import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.base.MyApplication;
import cn.teach.equip.bean.pojo.UserBO;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.util.AppManager;
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
    @BindView(R.id.get_version)
    TextView getVersion;

    @Override
    protected int getLayout() {
        return R.layout.act_login;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();

        txRegistest.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);  //下划线
        txRegistest.getPaint().setAntiAlias(true);//设置抗锯齿，使线条平滑
    }


    @OnClick(R.id.login_bt)
    public void login() {
        String phone = etPhone.getText().toString().trim();
        String smsCode = etYanzhengma.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            showToast2("请输入手机号码");
            return;
        }
        if (!RegexUtils.isMobileSimple(phone)) {
            showToast2("请输入正确的手机号码！");
            return;
        }
        if (StringUtils.isEmpty(smsCode)) {
            showToast2("请输入验证码！");
            return;
        }
        mPresenter.login(phone, smsCode);
//        gotoActivity(MainActivity.class, true);
    }


    @OnClick(R.id.get_version)
    public void getVersion() {
        String phone = etPhone.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            showToast2("请输入手机号码");
            return;
        }
        if (!RegexUtils.isMobileSimple(phone)) {
            showToast2("请输入正确的手机号码！");
            return;
        }
        getSyncVersion(phone);
    }


    /**
     * 获取验证码
     */
    private void getSyncVersion(String phone) {
        showProgress();
        HttpServerImpl.sendSmsCode(phone, 2).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                stopProgress();
                timer.start();
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast2(message);
            }
        });
    }


    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            getVersion.setEnabled(false);
            getVersion.setText("重新获取" + (millisUntilFinished / 1000) + "S");
        }

        @Override
        public void onFinish() {
            getVersion.setEnabled(true);
            getVersion.setText("重新获取");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }


    @OnClick(R.id.tx_registest)
    public void register() {
        gotoActivity(RegisterActivity.class, false);
    }


    @Override
    public void onRequestError(String msg) {
        showToast2(msg);
    }

    @Override
    public void loginSourcess(UserBO userBO) {
        MyApplication.token = userBO.getUserToken();
        MyApplication.userBO = userBO;
        MyApplication.spUtils.put("token",MyApplication.token);
        AppManager.getAppManager().finishAllActivity();
        gotoActivity(MainActivity.class, true);
    }
}
