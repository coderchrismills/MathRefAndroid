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
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class Level1ViewController extends ListFragment implements OnItemClickListener {

    OnHeadlineSelectedListener mHeadlineSelectedListener = null;
    List<String> mHeadlinesList = new ArrayList<String>();
    List<Integer> mIconList = new ArrayList<Integer>();
    MainViewAdapter mListAdapter;
    int currentLayout;


    public class MainViewAdapter extends ArrayAdapter<String> {
        Context mContext = null;
        public MainViewAdapter(Context context, int textViewResourceId, List<String> sectionTitles) {
            super(context, textViewResourceId, sectionTitles);
            mContext = context;
        }

        public View getView(int position, View viewconverter, ViewGroup parent) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            View row = inflater.inflate(R.layout.simple_list_item_main, parent, false);
            TextView label = (TextView)row.findViewById(R.id.text);
            label.setText(mHeadlinesList.get(position));
            ImageView icon = (ImageView)row.findViewById(R.id.image);
            icon.setImageResource(mIconList.get(position));
            return row;
        }
    }

    public Level1ViewController() {
        super();
    }

    @Override
    public void onStart() {
        super.onStart();

        setListAdapter(mListAdapter);
        getListView().setOnItemClickListener(this);
        loadCategory(0);
        getListView().setStackFromBottom(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        currentLayout = R.layout.simple_list_item_main;

        //setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item_1, mHeadlinesList));
        mListAdapter = new MainViewAdapter(getActivity(), currentLayout, mHeadlinesList);

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
        mHeadlinesList.clear();
        try {
            XmlPullParser xpp = getResources().getXml(R.xml.sectiontitles);

            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("section")) {
                        mHeadlinesList.add(xpp.getAttributeValue(0));
                    }
                }
                xpp.next();
            }

        } catch (Throwable t) {
            
        }

        mIconList.add(R.drawable.main_icons_0);
        mIconList.add(R.drawable.main_icons_1);
        mIconList.add(R.drawable.main_icons_2);
        mIconList.add(R.drawable.main_icons_4);
        mIconList.add(R.drawable.main_icons_5);
        mIconList.add(R.drawable.main_icons_6);
        mIconList.add(R.drawable.main_icons_11);
        mIconList.add(R.drawable.main_icons_12);
        mIconList.add(R.drawable.main_icons_14);
        mIconList.add(R.drawable.main_icons_15);
        mIconList.add(R.drawable.main_icons_16);
        mIconList.add(R.drawable.main_icons_17);
        mIconList.add(R.drawable.main_icons_18);
        mIconList.add(R.drawable.main_icons_19);
        mIconList.add(R.drawable.main_icons_20);

        mListAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mHeadlineSelectedListener) {
            mHeadlineSelectedListener.onHeadlineSelected(mHeadlinesList.get(position), "", -1);
        }
        // Set the item as checked to be highlighted when in two-pane layout
        //getListView().setItemChecked(position, true);
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