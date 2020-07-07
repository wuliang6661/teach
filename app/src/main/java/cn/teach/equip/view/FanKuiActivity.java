package cn.teach.equip.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.base.BaseActivity;

/**
 * 反馈界面
 */
public class FanKuiActivity extends BaseActivity {

    @BindView(R.id.et_fankui)
    EditText etFankui;
    @BindView(R.id.commit)
    TextView commit;
    @BindView(R.id.cancle)
    TextView cancle;

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


    @OnClick({R.id.commit, R.id.cancle})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.commit:
                String fankui = etFankui.getText().toString().trim();
                if (StringUtils.isEmpty(fankui)) {
                    showToast2("请填写反馈！");
                    return;
                }
                HttpServerImpl.feedback(fankui).subscribe(new HttpResultSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        showToast2("提交成功！");
                    }

                    @Override
                    public void onFiled(String message) {
                        showToast2(message);
                    }
                });
                break;
            case R.id.cancle:
                finish();
                break;
        }
    }

}
