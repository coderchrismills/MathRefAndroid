/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.happymaau.MathRef;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SearchViewController extends ListFragment implements OnItemClickListener {
    OnHeadlineSelectedListener mHeadlineSelectedListener = null;
    EquationArrayAdapter mAdapter;
    EditText searchTextView;

    int currentLayout;
    String section;
    String subsection;

    public SearchViewController() {
        super();
        section = "";
        subsection = "";
    }

    @Override
    public void onStart() {
        super.onStart();

        searchTextView = (EditText)getActivity().findViewById(R.id.SearchEditText);
        searchTextView.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);
        searchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handled = true;
                    String text = searchTextView.getText().toString();
                    mAdapter.updateWithSearchTerm(text);
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchTextView.getWindowToken(), 0);
                }
                return handled;
            }
        });
        
        getListView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

	        @Override
	        public void onGlobalLayout() {
	            // Ensure you call it only once :
	            getListView().getViewTreeObserver().removeGlobalOnLayoutListener(this);

	            // Here you can get the size :)
	            float width = getListView().getWidth();
	            mAdapter.updateWithWidth(width);
	        }
	    });
        
        searchTextView.requestFocus();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLayout = R.layout.list_item_icon_text;
        mAdapter = new EquationArrayAdapter(getActivity(), currentLayout, "i want nothing. nothing!!!!! nothing in return!");
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mHeadlineSelectedListener = (OnHeadlineSelectedListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_view, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(this);
    }

    public void setOnHeadlineSelectedListener(OnHeadlineSelectedListener listener) {
        mHeadlineSelectedListener = listener;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mHeadlineSelectedListener) {
            Equation eq = mAdapter.mEquations.get(position);
            mHeadlineSelectedListener.onHeadlineSelected(eq.section, eq.subSection, eq.eqIdDB);
        }
    }

}