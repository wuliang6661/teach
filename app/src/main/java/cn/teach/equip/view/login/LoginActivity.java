package cn.teach.equip.view.login;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.WindowManager;
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
import cn.teach.equip.view.WebActivity;
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
    @BindView(R.id.biaoti)
    TextView biaoti;

    @Override
    protected int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.act_login;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();

        txRegistest.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);  //下划线
        txRegistest.getPaint().setAntiAlias(true);//设置抗锯齿，使线条平滑

        setButtomView();
    }


    private void setButtomView(){
        String str = "登录即表明您同意上海教装《用户协议》和《隐私政策》";
        SpannableStringBuilder spannableBuilder1 = new SpannableStringBuilder(str);
        // 在设置点击事件、同时设置字体颜色
        ClickableSpan clickableSpanTwo = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://shjz.yingjin.pro/useragree.htm");
                bundle.putString("title", "用户协议");
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint paint) {
//                paint.setColor(Color.parseColor("#3072F6"));
                // 设置下划线 true显示、false不显示
                paint.setUnderlineText(true);
                // paint.setStrikeThruText(true);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://shjz.yingjin.pro/privacy.htm");
                bundle.putString("title", "隐私政策");
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint paint) {
//                paint.setColor(Color.parseColor("#3072F6"));
                // 设置下划线 true显示、false不显示
                paint.setUnderlineText(true);
                // paint.setStrikeThruText(true);
            }
        };
        spannableBuilder1.setSpan(clickableSpanTwo, 13, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableBuilder1.setSpan(clickableSpan2, 20, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 不设置点击不生效
        biaoti.setMovementMethod(LinkMovementMethod.getInstance());
        biaoti.setText(spannableBuilder1);
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
        MyApplication.spUtils.put("token", MyApplication.token);
        AppManager.getAppManager().finishAllActivity();
        gotoActivity(MainActivity.class, true);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent,R.anim.bottom_out);
    }
}
