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
package com.happymaau.MathRefFree;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class LeafViewController extends ListFragment implements
		OnItemClickListener {
	OnHeadlineSelectedListener mHeadlineSelectedListener = null;
	EquationArrayAdapter mAdapter;
	private ListView mListView;

	int currentLayout;
	String section;
	String subsection;

	public LeafViewController() {
		super();
		section = "";
		subsection = "";
	}

	@Override
	public void onStart() {
		super.onStart();
		setListAdapter(mAdapter);
		getListView().setOnItemClickListener(this);

		loadCategory(0);
		
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
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentLayout = R.layout.list_item_icon_text;
		section = getArguments().getString(ArticleFragment.SECTION);
		subsection = getArguments().getString(ArticleFragment.SUB_SECTION);
		mAdapter = new EquationArrayAdapter(getActivity(), currentLayout,
				section, subsection);
	}
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mHeadlineSelectedListener = (OnHeadlineSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	public void setOnHeadlineSelectedListener(
			OnHeadlineSelectedListener listener) {
		mHeadlineSelectedListener = listener;
	}

	public void loadCategory(int categoryIndex) {
		mAdapter.notifyDataSetChanged();
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (null != mHeadlineSelectedListener) {
			mHeadlineSelectedListener.onHeadlineSelected(section, subsection,
					mAdapter.mEquations.get(position).eqIdDB);
		}
	}
}
