package cn.teach.equip.view.register;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.mvp.MVPBaseActivity;


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
    @BindView(R.id.commit)
    TextView commit;
    @BindView(R.id.cancle)
    TextView cancle;

    @Override
    protected int getLayout() {
        return R.layout.act_register;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitleText("新用户注册");

    }


    @OnClick({R.id.two_text, R.id.three_text})
    public void clickMenu(View view) {
        switch (view.getId()) {
            case R.id.two_text:
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

}
