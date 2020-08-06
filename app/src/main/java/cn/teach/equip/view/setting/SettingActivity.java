package cn.teach.equip.view.setting;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.base.MyApplication;
import cn.teach.equip.constans.FileConfig;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.util.UpdateUtils;
import cn.teach.equip.view.FanKuiActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SettingActivity extends MVPBaseActivity<SettingContract.View, SettingPresenter>
        implements SettingContract.View {

    @BindView(R.id.jianyifankui)
    CardView jianyifankui;
    @BindView(R.id.clear_huancun)
    CardView clearHuancun;
    @BindView(R.id.version_name)
    TextView versionName;

    @Override
    protected int getLayout() {
        return R.layout.act_setting;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitleText("设置");

        versionName.setText("版本：" + AppUtils.getAppVersionName());
    }


    @OnClick({R.id.jianyifankui, R.id.clear_huancun, R.id.check_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jianyifankui:
                gotoActivity(FanKuiActivity.class, false);
                break;
            case R.id.clear_huancun:
                MyApplication.spUtils.clear();
                FileUtils.deleteDir(FileConfig.getApkFile());
                FileUtils.deleteDir(FileConfig.getImgFile());
                showToast("清理成功");
                break;
            case R.id.check_update:
                new UpdateUtils().checkUpdate(this, new UpdateUtils.onUpdateListener() {
                    @Override
                    public void noUpdate() {

                    }
                });
                break;
        }
    }


}
