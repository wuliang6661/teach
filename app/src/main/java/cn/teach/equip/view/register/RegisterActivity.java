package cn.teach.equip.view.register;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.bean.pojo.ProvinceBO;
import cn.teach.equip.bean.pojo.UnitBO;
import cn.teach.equip.bean.pojo.UserBO;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.view.selectcity.SelectCityActivity;
import cn.teach.equip.view.selectunit.SelectUnitActivity;


/**
 * MVPPlugin
 * 注册界面
 */

public class RegisterActivity extends MVPBaseActivity<RegisterContract.View, RegisterPresenter>
        implements RegisterContract.View {

    @BindView(R.id.one_img)
    ImageView oneImg;
    @BindView(R.id.two_text)
    TextView twoText;
    @BindView(R.id.three_text)
    TextView threeText;
    @BindView(R.id.four_img)
    ImageView fourImg;
    @BindView(R.id.jiaoyu_city)
    TextView jiaoyuCity;
    @BindView(R.id.jiaoyu_danwei)
    TextView jiaoyuDanwei;
    @BindView(R.id.et_jiaoyu_name)
    EditText etJiaoyuName;
    @BindView(R.id.et_jiaoyu_phone)
    EditText etJiaoyuPhone;
    @BindView(R.id.et_jiaoyu_version)
    EditText etJiaoyuVersion;
    @BindView(R.id.tx_jiaoyu_huoquyanzhengma)
    TextView txJiaoyuHuoquyanzhengma;
    @BindView(R.id.jiaoyu_regis_layout)
    LinearLayout jiaoyuRegisLayout;
    @BindView(R.id.et_qiye_name)
    EditText etQiyeName;
    @BindView(R.id.et_qiye_person_name)
    EditText etQiyePersonName;
    @BindView(R.id.et_qiye_person_phone)
    EditText etQiyePersonPhone;
    @BindView(R.id.et_qiye_version_code)
    EditText etQiyeVersionCode;
    @BindView(R.id.bt_qiye_huoquyanzhengma)
    TextView btQiyeHuoquyanzhengma;
    @BindView(R.id.qiye_regis_layout)
    LinearLayout qiyeRegisLayout;
    @BindView(R.id.select_unit)
    LinearLayout selectUnitLayout;

    private int userType = 1;

    private String cityId;
    private String provinceId;

    private UnitBO selectUnit;

    @Override
    protected int getLayout() {
        return R.layout.act_register;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        goBack();
        setTitleText("新用户注册");

    }


    @OnClick({R.id.two_text, R.id.three_text})
    public void clickMenu(View view) {
        switch (view.getId()) {
            case R.id.two_text:
                userType = 1;
                oneImg.setImageResource(R.drawable.rgs_one);
                twoText.setBackgroundResource(R.drawable.regis_select);
                threeText.setBackgroundResource(R.drawable.regis_three);
                fourImg.setImageResource(R.drawable.rgs_four);
                twoText.setTextColor(Color.parseColor("#25519A"));
                threeText.setTextColor(Color.parseColor("#ffffff"));
                jiaoyuRegisLayout.setVisibility(View.VISIBLE);
                qiyeRegisLayout.setVisibility(View.GONE);
                break;
            case R.id.three_text:
                userType = 2;
                oneImg.setImageResource(R.drawable.rgs_two_one);
                twoText.setBackgroundResource(R.drawable.rgs_two_two);
                threeText.setBackgroundResource(R.drawable.regis_select);
                fourImg.setImageResource(R.drawable.rgs_two_four);
                threeText.setTextColor(Color.parseColor("#25519A"));
                twoText.setTextColor(Color.parseColor("#ffffff"));
                jiaoyuRegisLayout.setVisibility(View.GONE);
                qiyeRegisLayout.setVisibility(View.VISIBLE);
                break;
        }
    }


    @OnClick({R.id.commit, R.id.cancle})
    public void clickMunu(View view) {
        switch (view.getId()) {
            case R.id.commit:
                switch (userType) {
                    case 1:
                        String strJiaoyuCity = jiaoyuCity.getText().toString().trim();
                        String strJiaoyuDanwei = jiaoyuDanwei.getText().toString().trim();
                        String strJiaoyuName = etJiaoyuName.getText().toString().trim();
                        String strJiaoyuPhone = etJiaoyuPhone.getText().toString().trim();
                        String strJiaoyuVersion = etJiaoyuVersion.getText().toString().trim();
                        if (StringUtils.isEmpty(strJiaoyuCity)) {
                            showToast2("请选择省份与城市！");
                            return;
                        }
                        if (StringUtils.isEmpty(strJiaoyuDanwei)) {
                            showToast2("请选择单位！");
                            return;
                        }
                        if (StringUtils.isEmpty(strJiaoyuName)) {
                            showToast2("请输入姓名！");
                            return;
                        }
                        if (StringUtils.isEmpty(strJiaoyuPhone)) {
                            showToast2("请输入手机号！");
                            return;
                        }
                        if (StringUtils.isEmpty(strJiaoyuVersion)) {
                            showToast2("请输入验证码！");
                            return;
                        }
                        register(strJiaoyuPhone, strJiaoyuVersion, provinceId, cityId,
                                strJiaoyuName, strJiaoyuDanwei, selectUnit.getUnitId() + "");
                        break;
                    case 2:
                        String strQiyeName = etQiyeName.getText().toString().trim();
                        String strQiyePersonName = etQiyePersonName.getText().toString().trim();
                        String strQiyePhone = etQiyePersonPhone.getText().toString().trim();
                        String strQiyeVersionPhone = etQiyeVersionCode.getText().toString().trim();
                        if (StringUtils.isEmpty(strQiyeName)) {
                            showToast2("请选择企业名称！");
                            return;
                        }
                        if (StringUtils.isEmpty(strQiyePersonName)) {
                            showToast2("请输入姓名！");
                            return;
                        }
                        if (StringUtils.isEmpty(strQiyePhone)) {
                            showToast2("请输入手机号！");
                            return;
                        }
                        if (StringUtils.isEmpty(strQiyeVersionPhone)) {
                            showToast2("请输入验证码！");
                            return;
                        }
                        register(strQiyePhone, strQiyeVersionPhone, "", "",
                                strQiyePersonName, strQiyeName, "");
                        break;
                }
                break;
            case R.id.cancle:
                finish();
                break;
        }
    }


    private void register(String phone, String smsCode, String provinceId, String cityId, String userName,
                          String unitName, String unitId) {
        HttpServerImpl.register(phone, smsCode, provinceId, cityId, userName, unitName, unitId, userType + "")
                .subscribe(new HttpResultSubscriber<UserBO>() {
                    @Override
                    public void onSuccess(UserBO s) {
                        showToast2("注册成功！");
                        finish();
                    }

                    @Override
                    public void onFiled(String message) {
                        showToast2(message);
                    }
                });
    }


    /**
     * 获取验证码
     */
    @OnClick({R.id.tx_jiaoyu_huoquyanzhengma, R.id.bt_qiye_huoquyanzhengma})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_jiaoyu_huoquyanzhengma:
                String strJiaoyuPhone = etJiaoyuPhone.getText().toString().trim();
                if (StringUtils.isEmpty(strJiaoyuPhone)) {
                    showToast2("请输入手机号！");
                    return;
                }
                if (!RegexUtils.isMobileExact(strJiaoyuPhone)) {
                    showToast2("请输入正确的手机号！");
                    return;
                }
                getVersion(userType, strJiaoyuPhone);
                break;
            case R.id.bt_qiye_huoquyanzhengma:
                String strQiyePhone = etQiyePersonPhone.getText().toString().trim();
                if (StringUtils.isEmpty(strQiyePhone)) {
                    showToast2("请输入手机号！");
                    return;
                }
                if (!RegexUtils.isMobileExact(strQiyePhone)) {
                    showToast2("请输入正确的手机号！");
                    return;
                }
                getVersion(userType, strQiyePhone);
                break;
        }
    }


    private void getVersion(int type, String phone) {
        HttpServerImpl.sendSmsCode(phone, 1).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (type == 1) {
                    jiaoyuTimer.start();
                } else {
                    gongsiTimer.start();
                }
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    @OnClick({R.id.jiaoyu_city, R.id.jiaoyu_danwei})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.jiaoyu_city:
                Intent intent = new Intent(this, SelectCityActivity.class);
                startActivityForResult(intent, 0x11);
                break;
            case R.id.jiaoyu_danwei:
                Intent intent1 = new Intent(this, SelectUnitActivity.class);
                intent1.putExtra("provinceId", provinceId);
                intent1.putExtra("cityId", cityId);
                startActivityForResult(intent1, 0x22);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0x11:
                String cityName = data.getStringExtra("cityName");
                cityId = data.getIntExtra("cityId", 0) + "";
                provinceId = data.getIntExtra("provinceId", 0) + "";
                String provinceName = data.getStringExtra("provinceName");
                int hasUnit = data.getIntExtra("hasUnit", 0);
                jiaoyuCity.setText(provinceName + cityName);
                if (hasUnit == 1) {
                    selectUnitLayout.setVisibility(View.VISIBLE);
                } else {
                    selectUnitLayout.setVisibility(View.GONE);
                }
                break;
            case 0x22:
                selectUnit = (UnitBO) data.getSerializableExtra("data");
                jiaoyuDanwei.setText(selectUnit.getUnitName());
                break;
        }
    }

    CountDownTimer jiaoyuTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txJiaoyuHuoquyanzhengma.setEnabled(false);
            txJiaoyuHuoquyanzhengma.setText("重新获取" + (millisUntilFinished / 1000) + "S");
        }

        @Override
        public void onFinish() {
            txJiaoyuHuoquyanzhengma.setEnabled(true);
            txJiaoyuHuoquyanzhengma.setText("重新获取");
        }
    };

    CountDownTimer gongsiTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            btQiyeHuoquyanzhengma.setEnabled(false);
            btQiyeHuoquyanzhengma.setText("重新获取" + (millisUntilFinished / 1000) + "S");
        }

        @Override
        public void onFinish() {
            btQiyeHuoquyanzhengma.setEnabled(true);
            btQiyeHuoquyanzhengma.setText("重新获取");
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        if (jiaoyuTimer != null) {
            jiaoyuTimer.cancel();
        }
        if (gongsiTimer != null) {
            gongsiTimer.cancel();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ProvinceBO.CityListBean cityListBean) {
        String cityName = cityListBean.getCityName();
        cityId = cityListBean.getCityId() + "";
        provinceId = cityListBean.getProvinceId() + "";
        String provinceName = cityListBean.getProvinceName();
        int hasUnit = cityListBean.getHasUnit();
        jiaoyuCity.setText(provinceName + cityName);
        if (hasUnit == 1) {
            selectUnitLayout.setVisibility(View.VISIBLE);
        } else {
            selectUnitLayout.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
