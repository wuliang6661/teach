package cn.teach.equip.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import butterknife.BindView;
import cn.teach.equip.R;
import cn.teach.equip.base.BaseWebActivity;

public class WebActivity extends BaseWebActivity {

    @BindView(R.id.wen_view)
    WebView wenView;

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
        wenView.loadUrl("https://www.kuleiman.com/122283/index.html?from=groupmessage&isappinstalled=0");
    }





}
