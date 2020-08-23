package cn.teach.equip.view.setting;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import cn.teach.equip.view.WebActivity;


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

        versionName.setText("版本：v" + AppUtils.getAppVersionName());
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
                requestPermission();
                break;
        }
    }


    @OnClick({R.id.yonghuxieyi, R.id.yinsizhengce})
    public void clickZhengce(View view) {
        String url = null;
        String title = null;
        switch (view.getId()) {
            case R.id.yonghuxieyi:
                url = "https://shjz.yingjin.pro/useragree.htm";
                title = "用户协议";
                break;
            case R.id.yinsizhengce:
                url = "https://shjz.yingjin.pro/privacy.htm";
                title = "隐私政策 ";
                break;
        }
        Intent intent = new Intent(this, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                ) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 1);
        } else {
            new UpdateUtils().checkUpdate(this, new UpdateUtils.onUpdateListener() {
                @Override
                public void noUpdate() {
                    showToast2("当前已经是最新版本！");
                }
            });
        }
    }

}
