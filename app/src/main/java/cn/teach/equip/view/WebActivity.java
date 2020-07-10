package cn.teach.equip.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.Toast;

import butterknife.BindView;
import cn.teach.equip.R;
import cn.teach.equip.base.BaseWebActivity;
import cn.teach.equip.weight.web.ChromeClient;
import cn.teach.equip.weight.web.MyWebClient;

public class WebActivity extends BaseWebActivity {

    @BindView(R.id.wen_view)
    WebView wenView;

    private ChromeClient chooseClient;

    @Override
    protected int getLayout() {
        return R.layout.act_web;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        initWebView(wenView);

        String title = getIntent().getExtras().getString("title");
        String url = getIntent().getExtras().getString("url");
        setTitleText(title);
        wenView.setWebChromeClient(new ChromeClient(this));
        wenView.setWebViewClient(new MyWebClient(this));
        wenView.loadUrl("https://www.kuleiman.com/122283/index.html?from=groupmessage&isappinstalled=0");
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
        }
    }

}
