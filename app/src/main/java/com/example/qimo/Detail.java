package com.example.qimo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Detail extends AppCompatActivity {
    private String url;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        webView = (WebView) findViewById(R.id.webview);    //获取布局管理器中添加的WebView组件

        Intent intent = getIntent();
        url = intent.getStringExtra("urls");
//支持javascript
        webView.getSettings().setJavaScriptEnabled(true);

        //这两句很关键，不写显示不出来
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);//开启DOM


        //如果不设置WebViewClient，请求会跳转系统浏览器
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(url);
    }

}
