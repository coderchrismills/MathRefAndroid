package com.happymaau.MathRefFree;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.widget.*;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ToolsView extends ListFragment implements OnItemClickListener {
    OnHeadlineSelectedListener mHeadlineSelectedListener = null;
    List<String> mHeadlinesList         = new ArrayList<String>();
    List<Integer> mIconList             = new ArrayList<Integer>();
    MainViewAdapter mListAdapter        = null;
    String currentTool                  = "";

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

    public ToolsView() {
        super();
    }

    @Override
    public void onStart() {
        super.onStart();

        setListAdapter(mListAdapter);
        getListView().setOnItemClickListener(this);
        loadData();
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new MainViewAdapter(getActivity(), R.layout.simple_list_item_main, mHeadlinesList);
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

    public void loadData() {

        mHeadlinesList.clear();
        mIconList.clear();

        mHeadlinesList.add("Quadratic Solver");
        mHeadlinesList.add("Exponential Solver");
        mHeadlinesList.add("Circle Solver");
        mHeadlinesList.add("Angle Converter");
        mHeadlinesList.add("Reference Angle Solver");
        mHeadlinesList.add("Z Solver");

        // Quadratic Solver
        mIconList.add(R.drawable.main_icons_0);
        // Exponential Solver
        mIconList.add(R.drawable.main_icons_0);

        // Geometry
        // Circle Solver
        mIconList.add(R.drawable.main_icons_1);

        // Trig
        // Angle Converter
        mIconList.add(R.drawable.main_icons_2);
        // Reference Angle Solver
        mIconList.add(R.drawable.main_icons_2);

        // Stats
        // Z Solver
        mIconList.add(R.drawable.main_icons_11);

        mListAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mHeadlineSelectedListener) {
            mHeadlineSelectedListener.onHeadlineSelected("Tools", mHeadlinesList.get(position), position);
        }
    }
}
