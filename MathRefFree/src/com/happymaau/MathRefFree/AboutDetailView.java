package com.happymaau.MathRefFree;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import com.happymaau.MathRefFree.GlobalHelpers;
import com.happymaau.MathRefFree.R;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

public class AboutDetailView extends RelativeLayout {
	
	public WebView browser;

	public AboutDetailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ViewInit(context);
    }

    public AboutDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewInit(context);
    }

    public AboutDetailView(Context context) {
        super(context);
        ViewInit(context);
    }

    private void ViewInit(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.addView(inflater.inflate(R.layout.about_view, null));
    }

    public void loadAbout() {
        browser = (WebView)findViewById(R.id.webkit_about);
        if(browser == null)
            return;

        browser.clearView();
        browser.loadUrl("file:///android_asset/empty.html");
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.setVisibility(View.GONE);
                view.loadUrl("file:///android_asset/about.html");
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
            if(url.contains("mailto"))
            {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                String[] recipients = new String[]{"info@happymaau.com", "",};
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Math Ref Android Comment");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                emailIntent.setType("text/plain");
                GlobalHelpers.mActivity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
            else
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                GlobalHelpers.mActivity.startActivity(browserIntent);
            }
            return true;
        }
    }

    
}
