package cn.teach.equip.weight.web;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;

import cn.teach.equip.R;
import cn.teach.equip.base.MyApplication;
import cn.teach.equip.util.AppManager;
import cn.teach.equip.util.ShareUtils;
import cn.teach.equip.view.BigPicutreActivity;
import cn.teach.equip.view.WebActivity;
import cn.teach.equip.view.login.LoginActivity;
import cn.teach.equip.view.stypeclass.StypeClassActivity;
import cn.teach.equip.weight.ShareDialog;
import cn.teach.equip.zxing.activity.CaptureActivity;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/7/1014:35
 * desc   : 网页接口
 * version: 1.0
 */
public class WebJsInterface {

    private Activity activity;

    public WebJsInterface(Activity activity) {
        this.activity = activity;
    }

    /**
     * 扫一扫调用
     */
    @JavascriptInterface
    public void startScan() {
        Intent intent = new Intent(activity, CaptureActivity.class);
        activity.startActivityForResult(intent, 0x11);
    }


    /**
     * 去主题教室
     */
    @JavascriptInterface
    public void goThemeClass(int levelId2, String title) {
        Intent intent = new Intent(activity, StypeClassActivity.class);
        intent.putExtra("levelId2", levelId2);
        intent.putExtra("title", title);
        activity.startActivity(intent);
    }


    /**
     * 去VR
     */
    @JavascriptInterface
    public void goVR(String title, String url) {
        Intent intent = new Intent(activity, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        bundle.putInt("targetType", 0);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }


    /**
     * 分享
     */
    @JavascriptInterface
    public void share(String title, String content, String url) {
        new ShareDialog().showShareDialog(new ShareDialog.OnClickShare() {
            @Override
            public void share(int flag) {
                ShareUtils.shareHtml(title, content, url, flag);
            }
        });
    }

    /**
     * 去登陆
     */
    @JavascriptInterface
    public void goLogin() {
        MyApplication.spUtils.remove("token");
        MyApplication.token = null;
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        AppManager.getAppManager().finishAllActivity();
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
    }


    /**
     * 查看大图
     */
    @JavascriptInterface
    public void zoomBigImg(String imageUrl) {
        ArrayList<String> images = new ArrayList<>();
        images.add(imageUrl);
        Intent intent = new Intent(activity, BigPicutreActivity.class);
        intent.putStringArrayListExtra("imageBos", images);
        activity.startActivity(intent);
    }

}
