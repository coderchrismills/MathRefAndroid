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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class Level2ViewController extends ListFragment implements OnItemClickListener {
    OnHeadlineSelectedListener mHeadlineSelectedListener = null;
    List<String> mHeadlinesList = new ArrayList<String>();

    ArrayAdapter<String> mListAdapter;
    int currentLayout;
    String section;

    public class MainViewAdapter extends ArrayAdapter<String> {
        Context mContext = null;
        public MainViewAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId, mHeadlinesList);
            mContext = context;
            section = "";
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            View row = inflater.inflate(R.layout.simple_list_item_1, parent, false);
            TextView label = (TextView)row.findViewById(R.id.text);
            label.setText(mHeadlinesList.get(position));
            return row;
        }
    }

    public Level2ViewController() {
        super();
        section = "";
    }

    @Override
    public void onStart() {
        super.onStart();
        setListAdapter(mListAdapter);
        getListView().setOnItemClickListener(this);
        getListView().setStackFromBottom(false);
        loadCategory(0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        currentLayout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        //setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item_1, mHeadlinesList));
        mListAdapter = new MainViewAdapter(getActivity(), currentLayout);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mHeadlineSelectedListener = (OnHeadlineSelectedListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public void setOnHeadlineSelectedListener(OnHeadlineSelectedListener listener) {
        mHeadlineSelectedListener = listener;
    }

    public void loadCategory(int categoryIndex) {
        EquationDatasource ds = new EquationDatasource();
        ds.open();
        mHeadlinesList.clear();
        section = getArguments().getString(ArticleFragment.SECTION);
        mHeadlinesList.addAll(ds.getSubSectionNamesFor(section));
        mListAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mHeadlineSelectedListener) {
            mHeadlineSelectedListener.onHeadlineSelected(section, mHeadlinesList.get(position), -1);
        }
    }

    public void setSelectable(boolean selectable) {
        if (selectable) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
        else {
            getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
        }
    }
}