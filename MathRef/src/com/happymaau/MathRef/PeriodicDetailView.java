package com.happymaau.MathRef;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import com.happymaau.MathRef.GlobalHelpers;
import com.happymaau.MathRef.R;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

public class PeriodicDetailView extends RelativeLayout {
	
	public WebView browser;
	
	public PeriodicDetailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ViewInit(context);
    }

    public PeriodicDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewInit(context);
    }
    
    public PeriodicDetailView(Context context) {
        super(context);
        ViewInit(context);
    }

    private void ViewInit(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.addView(inflater.inflate(R.layout.periodic_table_view, null));
    }

    public void loadPeriodic() {
        browser = (WebView)findViewById(R.id.webkit_periodic);
        if(browser == null)
            return;

        browser.setWebViewClient(new Callback());
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        browser.getSettings().setLightTouchEnabled(true);
        browser.getSettings().setBuiltInZoomControls(true);

        browser.setWebViewClient(new Callback());
        browser.clearView();
        browser.measure(10,10);
        browser.loadUrl("file:///android_asset/empty.html");
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.setVisibility(View.GONE);
                view.loadUrl("file:///android_asset/periodic_table.html");
                view.setWebViewClient(new Callback());
            }
        });
    }

    private class Callback extends WebViewClient {

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.setVisibility(View.VISIBLE);
            view.requestLayout();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}
