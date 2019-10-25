package com.happymaau.MathRefFree;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

public class EquationDetailView extends LinearLayout {
	
	private View 		equationRoot;
	private ViewGroup   notesRoot;
	private View 		exampleRoot;
	private String      noteString;

	public EquationDetailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ViewInit(context);
    }

    public EquationDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewInit(context);
    }
    
    public EquationDetailView(Context context) {
        super(context);
        ViewInit(context);
    }

    private void ViewInit(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.addView(inflater.inflate(R.layout.detail_view, null));

        equationRoot	= findViewById(R.id.equation_view);
        notesRoot    	= (ViewGroup)findViewById(R.id.notes_view);
        exampleRoot  	= findViewById(R.id.example_view);
    }

    public void updateWithEquation(Equation equation) {

        if(equationRoot == null)
            return;

        TouchImageView equation_image_view  = (TouchImageView)equationRoot.findViewById(R.id.equation_image);
        WebView notes_web_view          	= (WebView)notesRoot.findViewById(R.id.notes_fields);
        TouchImageView example_image_view   = (TouchImageView)exampleRoot.findViewById(R.id.example_image);

        equation_image_view.setImageBitmap(equation.getEquationImageForType(Equation.FullScreenType));
        equation_image_view.setMaxWidth((int) equation.eqImageWidth);
        equation_image_view.setMaxZoom(1.0f);
        int eq_vis = equation.eqImageWidth > 1.0 ? View.VISIBLE : View.GONE;
        equationRoot.setVisibility(eq_vis);

        example_image_view.setImageBitmap(equation.getExampleImageForType(Equation.FullScreenType));
        example_image_view.setMaxWidth((int)equation.exImageWidth);
        example_image_view.setMaxZoom(1.0f);
        int ex_vis = equation.exImageWidth > 1.0 ? View.VISIBLE : View.GONE;
        exampleRoot.setVisibility(ex_vis);

        int note_vis = (equation.note.length() > 0 && !equation.note.equalsIgnoreCase("null")) ? View.VISIBLE : View.GONE;
        notesRoot.setVisibility(note_vis);

        noteString = "<html><body>"+equation.note+"</body></html>";
        notes_web_view.clearView();
        notes_web_view.loadUrl("file:///android_asset/empty.html");
        WebSettings webSettings = notes_web_view.getSettings();
        webSettings.setDefaultFontSize(18);
        notes_web_view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.setVisibility(View.GONE);
                view.loadDataWithBaseURL(null, noteString, "text/html", "utf-8", null);
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
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            GlobalHelpers.mActivity.startActivity(browserIntent);
            return true;
        }
    }
}
