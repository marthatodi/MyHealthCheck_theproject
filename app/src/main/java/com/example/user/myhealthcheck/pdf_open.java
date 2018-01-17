package com.example.user.myhealthcheck;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class pdf_open extends AppCompatActivity {
    private String value;
    public String getValue(){
       // @SuppressLint("JavascriptInterface")
        return this.value;
    }
    public void setV(String v){
        this.value=v;
    }

    @Override
    @JavascriptInterface
    @SuppressLint("JavascriptInterface")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_open);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setAppCacheEnabled(true);
        WebSettings webSettings = myWebView.getSettings();

        class JsObject {
            @JavascriptInterface
            public String toString() { return "injectedObject"; }
        }

        webSettings.setJavaScriptEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            //The key argument here must match that used in the other activity
            setV(value);
       //     myWebView.addJavascriptInterface(new JsObject(), "injectedObject");

            myWebView.loadUrl("http://ba31f2d0.ngrok.io/mypraxis/MyHealthCheck/src/"+value+"/");
        }
        myWebView.setWebViewClient(new MyWebViewClient());

    }
    @SuppressLint("JavascriptInterface")
    private class MyWebViewClient extends WebViewClient {
        @JavascriptInterface
        @Override
        @SuppressLint("JavascriptInterface")
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (Uri.parse(url).getHost().equals("http://ba31f2d0.ngrok.io/mypraxis/MyHealthCheck/src/uploads/"+getValue()+"/")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
}