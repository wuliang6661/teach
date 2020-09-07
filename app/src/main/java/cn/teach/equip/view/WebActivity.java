package cn.teach.equip.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.base.BaseWebActivity;
import cn.teach.equip.base.MyApplication;
import cn.teach.equip.weight.web.ChromeClient;
import cn.teach.equip.weight.web.MyWebClient;
import cn.teach.equip.weight.web.WebJsInterface;

public class WebActivity extends BaseWebActivity {

    @BindView(R.id.wen_view)
    WebView wenView;
    @BindView(R.id.back)
    LinearLayout back;

    private ChromeClient chooseClient;

    @Override
    protected int getLayout() {
        return R.layout.act_web;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        back.setVisibility(View.VISIBLE);
        initWebView(wenView);

        String title = getIntent().getExtras().getString("title");
        String url = getIntent().getExtras().getString("url");
        int targetType = getIntent().getExtras().getInt("targetType", 0);
        setTitleText(title);
        chooseClient = new ChromeClient(this);
        wenView.setWebChromeClient(chooseClient);
        wenView.setWebViewClient(new MyWebClient(this));
        wenView.addJavascriptInterface(new WebJsInterface(this), "app");
//        wenView.loadUrl("https://www.kuleiman.com/122283/index.html?from=groupmessage&isappinstalled=0");
        LogUtils.e(url);
        Map<String, String> headers = new HashMap<>();
        headers.put("user-token", StringUtils.isEmpty(MyApplication.token) ? "" : MyApplication.token);
        headers.put("user-deviceId", DeviceUtils.getMacAddress().replaceAll(":", ""));
        if (targetType == -1) {
            wenView.loadUrl("file:///android_asset/map/pageMap.html", headers);
        } else {
            wenView.loadUrl(url, headers);
        }
    }


    @OnClick(R.id.back)
    public void back() {
        if (wenView.canGoBack()) {
            wenView.goBack();
        } else {
            finish();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ChromeClient.REQUEST_CODE) {
            // 经过上边(1)、(2)两个赋值操作，此处即可根据其值是否为空来决定采用哪种处理方法
            if (chooseClient.mUploadMessage != null) {
                chooseClient.chooseBelow(resultCode, data);
            } else if (chooseClient.mUploadCallbackAboveL != null) {
                chooseClient.chooseAbove(resultCode, data);
            } else {
                Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 0x11) {
            if (data == null) {
                return;
            }
            String result = data.getStringExtra("result");
            wenView.loadUrl("javascript:takeScanResult(" + result + ")");
        }
    }

}
