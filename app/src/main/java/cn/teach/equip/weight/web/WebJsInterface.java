package cn.teach.equip.weight.web;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import cn.teach.equip.view.WebActivity;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/7/1014:35
 * desc   : 网页接口
 * version: 1.0
 */
public class WebJsInterface {

    private Activity activity;

    public WebJsInterface(Activity activity){
        this.activity = activity;
    }

    /**
     * 扫一扫调用
     */
    @JavascriptInterface
    public void startScan() {

    }


    /**
     * 去主题教室
     */
    @JavascriptInterface
    public void goThemeClass() {

    }


    /**
     * 去VR
     */
    @JavascriptInterface
    public void goVR(String title, String url) {
        Intent intent = new Intent(activity,WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

}
