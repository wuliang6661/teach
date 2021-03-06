package cn.teach.equip.view.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.TreeSet;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.teach.equip.R;
import cn.teach.equip.base.BaseActivity;
import cn.teach.equip.base.MyApplication;
import cn.teach.equip.bean.event.SwitchEvent;
import cn.teach.equip.util.AppManager;
import cn.teach.equip.util.UpdateUtils;
import cn.teach.equip.view.login.LoginActivity;
import cn.teach.equip.view.main.none.NoneFragment1;
import cn.teach.equip.view.main.none.NoneFragment2;
import cn.teach.equip.view.main.none.NoneFragment3;
import cn.teach.equip.view.main.none.NoneFragment4;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity {


    @BindView(R.id.main1_cli)
    RelativeLayout main1Cli;
    @BindView(R.id.main1)
    LinearLayout main1;
    @BindView(R.id.main2_cli)
    RelativeLayout main2Cli;
    @BindView(R.id.main2)
    LinearLayout main2;
    @BindView(R.id.main3_cli)
    RelativeLayout main3Cli;
    @BindView(R.id.main3)
    LinearLayout main3;
    @BindView(R.id.main4_cli)
    RelativeLayout main4Cli;
    @BindView(R.id.main4)
    LinearLayout main4;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private RelativeLayout[] buttoms;
    private LinearLayout[] linears;

    private int selectPosition = 0;

    @Override
    protected int getLayout() {
        return R.layout.act_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        buttoms = new RelativeLayout[]{main1Cli, main2Cli, main3Cli, main4Cli};
        linears = new LinearLayout[]{main1, main2, main3, main4};
        initFragment();
        clickButtom(0);
        if (!StringUtils.isEmpty(MyApplication.token)) {
            registerPush();
        }
        checkPrission();
    }


    private void checkUpdate() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new UpdateUtils().checkUpdate(MainActivity.this, new UpdateUtils.onUpdateListener() {
                    @Override
                    public void noUpdate() {

                    }
                });
            }
        }, 2000);
    }


    private void checkPrission() {
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
            checkUpdate();
        }
    }


    /**
     * 注册极光
     */
    private void registerPush() {
        JPushInterface.setAlias(this, 1, MyApplication.userBO.getPhone());
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add(MyApplication.userBO.getPhone());
        JPushInterface.setTags(this, 1, treeSet);
    }


    /**
     * 初始化fragment
     */
    private void initFragment() {
        SupportFragment firstFragment = findFragment(NoneFragment1.class);
        if (firstFragment == null) {
            mFragments[0] = NoneFragment1.newInstance();
            mFragments[1] = new NoneFragment2();
            mFragments[2] = new NoneFragment3();
            mFragments[3] = new NoneFragment4();

            loadMultipleRootFragment(R.id.fragment_container, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(NoneFragment2.class);
            mFragments[2] = findFragment(NoneFragment3.class);
            mFragments[3] = findFragment(NoneFragment4.class);
        }
    }


    @OnClick({R.id.main1, R.id.main1_cli, R.id.main2, R.id.main2_cli,
            R.id.main3, R.id.main3_cli, R.id.main4, R.id.main4_cli})
    public void click(View view) {
        if (StringUtils.isEmpty(MyApplication.token)) {
            gotoActivity(LoginActivity.class, false);
            overridePendingTransition(R.anim.bottom_in,R.anim.bottom_silent);
            return;
        }
        switch (view.getId()) {
            case R.id.main1:
            case R.id.main1_cli:
                showHideFragment(mFragments[0], mFragments[selectPosition]);
                selectPosition = 0;
                clickButtom(0);
                break;
            case R.id.main2:
            case R.id.main2_cli:
                showHideFragment(mFragments[1], mFragments[selectPosition]);
                selectPosition = 1;
                clickButtom(1);
                break;
            case R.id.main3:
            case R.id.main3_cli:
                showHideFragment(mFragments[2], mFragments[selectPosition]);
                selectPosition = 2;
                clickButtom(2);
                break;
            case R.id.main4:
            case R.id.main4_cli:
                showHideFragment(mFragments[3], mFragments[selectPosition]);
                selectPosition = 3;
                clickButtom(3);
                break;
        }
    }


    private void clickButtom(int position) {
        for (int i = 0; i < buttoms.length; i++) {
            if (i == position) {
                buttoms[i].setVisibility(View.VISIBLE);
                linears[i].setVisibility(View.GONE);
            } else {
                buttoms[i].setVisibility(View.GONE);
                linears[i].setVisibility(View.VISIBLE);
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SwitchEvent event) {
        showHideFragment(mFragments[event.position], mFragments[selectPosition]);
        selectPosition = event.position;
        clickButtom(event.position);
    }


    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showToast2("再按一次退出程序");
                    firstTime = secondTime;
                    return true;
                } else {
                    AppManager.getAppManager().finishAllActivity();
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
