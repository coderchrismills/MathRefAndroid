package com.happymaau.MathRefFree;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class PrimeViewController extends Fragment {

    WebView browser                 = null;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.webview, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        browser = (WebView)(getActivity().findViewById(R.id.webkit));
        browser.loadUrl("file:///android_asset/primes.html");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
