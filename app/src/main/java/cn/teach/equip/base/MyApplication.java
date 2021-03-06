package cn.teach.equip.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cn.jpush.android.api.JPushInterface;
import cn.teach.equip.bean.pojo.UserBO;

/**
 * 作者 by wuliang 时间 16/10/26.
 * <p>
 * 程序的application
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    public static SPUtils spUtils;

    public static boolean AppInBack = false;  //App 是否在后台

    public static String token;

    public static UserBO userBO;

    @Override
    public void onCreate() {
        super.onCreate();
        CustomActivityOnCrash.install(this);
        /***初始化工具类*/
        Utils.init(this);
        spUtils = SPUtils.getInstance(TAG);

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        registerActivityLifecycleCallbacks(new AppLifecycleHandler());
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
