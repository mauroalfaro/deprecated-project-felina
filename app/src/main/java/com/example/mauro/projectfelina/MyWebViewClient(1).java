package com.example.mauro.projectfelina;

import android.app.Activity;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Mauro on 05/06/2015.
 */
public class MyWebViewClient extends WebViewClient {

    private MainActivity a;

    public MyWebViewClient(MainActivity a){
        this.a=a;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        this.a.editText.setText(url);
        a.progressBar.setVisibility(view.VISIBLE);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        a.progressBar.setVisibility(view.GONE);
        a.tv.setText(view.getTitle());
    }


}
