/*
 * Copyright (C) 2012 Happy Maau Studios, LLC
 */
package com.happymaau.MathRefFree;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;
import android.view.Display;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.conn.scheme.HostNameResolver;

import java.util.concurrent.Callable;

interface OnFinishedCB {
    void OnFinished();
}

public class MainActivity extends FragmentActivity implements OnHeadlineSelectedListener {

    final static String SECTION_ID      = "SectionIndex";
    final static String SUBSECTION_ID   = "SubSectionIndex";
    final static String EQUATION_ID     = "EquationIndex";
    final static String BACKSTACK_TAG   = "BackStack";

    private Fragment mDetailViewFragment;

    private String _section;
    private String _subsection;
    private int _equation;
    
    public static void ShowError(String error)
    {
    	AlertDialog show = new AlertDialog.Builder(GlobalHelpers.mDialogContext).setTitle("Error").setTitle("A and B cannot be 0").setNeutralButton("OK",
				new DialogInterface.OnClickListener() { 
					public void onClick(DialogInterface dlg, int id) { 
					}
		}).show();	
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_container);

        GlobalHelpers.mContext = getApplicationContext();
        GlobalHelpers.mActivity = this;
        GlobalHelpers.mDialogContext = MainActivity.this;
        
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point sz = new Point();
        try {
            display.getSize(sz);
        }
        catch(NoSuchMethodError ignore)
        {
            sz.x = display.getWidth();
            sz.y = display.getHeight();
        }

        int width = sz.x;
        int height = sz.y;
        GlobalHelpers.width = (float)width;
        GlobalHelpers.height = (float)height;

        if(savedInstanceState != null) {
            _section = savedInstanceState.getString(SECTION_ID);
            _section = (_section == null) ? "" : _section;
            _subsection = savedInstanceState.getString(SUBSECTION_ID);
            _subsection = (_subsection == null) ? "" : _subsection;
            _equation = savedInstanceState.getInt(EQUATION_ID, -1);
            BuildView();
        }
        else {
            _section = "";
            _subsection = "";
            _equation = -1;

            OnFinishedCB cb = new OnFinishedCB() {
                @Override
                public void OnFinished() {
                    BuildView();
                }
            };
            new Progress(cb).execute();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        startActivity(getIntent());
        finish();
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SECTION_ID, _section);
        outState.putString(SUBSECTION_ID, _subsection);
        outState.putInt(EQUATION_ID, _equation);
        super.onSaveInstanceState(outState);
    }

    private void BuildView() {
        mDetailViewFragment = null;
        if (getResources().getBoolean(R.bool.has_two_panes)) {
            // Create an instance of ExampleFragment
            mDetailViewFragment = getSupportFragmentManager().findFragmentById(R.id.article_fragment);
        }

        FragmentManager fm = getSupportFragmentManager();

        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        HandleSelection("", "", -1, false, false);
        if(_section.length() > 1)
            HandleSelection(_section, "", -1, false);
        if(_subsection.length() > 1)
            HandleSelection(_section, _subsection, -1, false);
        if(_equation != -1)
            HandleSelection(_section, _subsection, _equation, false);

    }

    private void HandleSelection(String section, String subsection, int equation) {
        HandleSelection(section, subsection, equation, true);
    }
    private void HandleSelection(String section, String subsection, int equation, boolean frompress) {
        HandleSelection(section, subsection, equation, frompress, true);
    }

    private void HandleSelection(String section, String subsection, int equation, boolean frompress, boolean addback) {

        if(frompress) {
            _section = section;
            _subsection = subsection;
            _equation = equation;
        }

        Fragment mMasterViewFragment;
        // Sections
        if(section.equalsIgnoreCase("about")) {
            if(mDetailViewFragment != null)
            {
                ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.About);
                return;
            }

            mMasterViewFragment = new ArticleFragment(ArticleFragment.ArticleViews.About);
        }
        else if(section.equalsIgnoreCase("search") && equation == -1) {
            mMasterViewFragment = new SearchViewController();
        }
        else if(section.equalsIgnoreCase("prime numbers")) {
            mMasterViewFragment = new PrimeViewController();
        }
        else if(section.equalsIgnoreCase("greek alphabet") && equation == -1) {
            mMasterViewFragment = new LeafViewController();
            Bundle args = new Bundle();
            args.putString(ArticleFragment.SECTION, section);
            args.putString(ArticleFragment.SUB_SECTION, section);
            mMasterViewFragment.setArguments(args);
        }
        else if(section.equalsIgnoreCase("tools")) {
            if(subsection.length() < 1)
            {
                mMasterViewFragment = new ToolsView();
            }
            else {
                if(mDetailViewFragment != null) {
                    setDetailViewWithFragmentId(subsection);
                    return;
                }
                else {
                    mMasterViewFragment = getFragmentWithFragmentId(subsection);
                }
            }
        }
        // equation selected
        else if(equation != -1) {
            EquationDatasource ds = new EquationDatasource(GlobalHelpers.mContext);
            ds.open();
            Equation eq = ds.getEquationForID(equation);
            if(mDetailViewFragment != null)
            {
                ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.Equation, eq);
                return;
            }

            mMasterViewFragment = new ArticleFragment(ArticleFragment.ArticleViews.Equation);
            Bundle args = new Bundle();
            args.putParcelable(ArticleFragment.EQUATION, eq);
            mMasterViewFragment.setArguments(args);
        }
        // Sub sections
        else if (subsection.length() > 0) {
            if(subsection.equalsIgnoreCase("periodic table")) {

                if(mDetailViewFragment != null)
                {
                    ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.Periodic);
                    return;
                }

                mMasterViewFragment = new ArticleFragment(ArticleFragment.ArticleViews.Periodic);
            }
            else {
                mMasterViewFragment = new LeafViewController();
                Bundle args = new Bundle();
                args.putString(ArticleFragment.SECTION, section);
                args.putString(ArticleFragment.SUB_SECTION, subsection);
                mMasterViewFragment.setArguments(args);
            }
        }
        else if(section.length() > 0 && subsection.length() < 1) {
            mMasterViewFragment = new Level2ViewController();
            Bundle args = new Bundle();
            args.putString(ArticleFragment.SECTION, section);
            args.putString(ArticleFragment.SUB_SECTION, subsection);
            mMasterViewFragment.setArguments(args);
        }
        else {
            mMasterViewFragment = new Level1ViewController();
            if(mDetailViewFragment != null)
            {
                ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.About);
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container, mMasterViewFragment);
        if(addback)
            transaction.addToBackStack(null);
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void onHeadlineSelected(String section, String subsection, int equation) {
        HandleSelection(section, subsection, equation);
    }

    private void setDetailViewWithFragmentId(String subsection) {
        if(subsection.equalsIgnoreCase("two line solver"))
           ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.TwoLine);
        else if(subsection.equalsIgnoreCase("quadratic solver"))
            ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.Quadratic);
        else if(subsection.equalsIgnoreCase("exponential solver"))
            ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.Exponential);
        else if(subsection.equalsIgnoreCase("complex solver"))
            ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.Complex);
        else if(subsection.equalsIgnoreCase("triangle solver"))
            ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.Triangle);
        else if(subsection.equalsIgnoreCase("circle solver"))
            ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.Circle);
        else if(subsection.equalsIgnoreCase("angle converter"))
            ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.AngleConverter);
        else if(subsection.equalsIgnoreCase("inverse solver"))
            ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.InverseSolver);
        else if(subsection.equalsIgnoreCase("reference angle solver"))
            ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.RefAngle);
        else if(subsection.equalsIgnoreCase("z solver"))
            ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.ZSolver);
        else if(subsection.equalsIgnoreCase("unit converter"))
            ((ArticleFragment)mDetailViewFragment).setWithView(ArticleFragment.ArticleViews.UnitConverter);
    }

    private Fragment getFragmentWithFragmentId(String subsection) {
        Fragment frag = null;
        if(subsection.equalsIgnoreCase("two line solver"))
            frag = new ArticleFragment(ArticleFragment.ArticleViews.TwoLine);
        else if(subsection.equalsIgnoreCase("quadratic solver"))
            frag = new ArticleFragment(ArticleFragment.ArticleViews.Quadratic);
        else if(subsection.equalsIgnoreCase("exponential solver"))
            frag = new ArticleFragment(ArticleFragment.ArticleViews.Exponential);
        else if(subsection.equalsIgnoreCase("complex solver"))
            frag = new ArticleFragment(ArticleFragment.ArticleViews.Complex);
        else if(subsection.equalsIgnoreCase("triangle solver"))
            frag = new ArticleFragment(ArticleFragment.ArticleViews.Triangle);
        else if(subsection.equalsIgnoreCase("circle solver"))
            frag = new ArticleFragment(ArticleFragment.ArticleViews.Circle);
        else if(subsection.equalsIgnoreCase("angle converter"))
            frag = new ArticleFragment(ArticleFragment.ArticleViews.AngleConverter);
        else if(subsection.equalsIgnoreCase("inverse solver"))
            frag = new ArticleFragment(ArticleFragment.ArticleViews.InverseSolver);
        else if(subsection.equalsIgnoreCase("reference angle solver"))
            frag = new ArticleFragment(ArticleFragment.ArticleViews.RefAngle);
        else if(subsection.equalsIgnoreCase("z solver"))
            frag = new ArticleFragment(ArticleFragment.ArticleViews.ZSolver);
        else if(subsection.equalsIgnoreCase("unit converter"))
            frag = new ArticleFragment(ArticleFragment.ArticleViews.UnitConverter);
        return frag;
    }

    private class Progress extends AsyncTask<String, Integer, String> {
        ProgressDialog pd;
        int count=0;
        OnFinishedCB cb;
        public Progress(OnFinishedCB finished)
        {
            cb = finished;
        }

        public void onPreExecute(){
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait while database loads\nFirst time may take awhile.");
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
        }
        @Override
        protected String doInBackground(String... params) {
            EquationDatasource ds = new EquationDatasource(GlobalHelpers.mContext);
            ds.open();
            if(pd.isShowing())
            {
                try
                {
                    pd.dismiss();
                }
                catch(Exception e) {

                }
            }
            return null;
        }
        protected void onProgressUpdate(Integer... values){
        }
        protected void onPostExecute(String result) {
            if(cb != null)
                cb.OnFinished();
            Toast.makeText(MainActivity.this, "Database loading completed!", Toast.LENGTH_LONG).show();
        }

    }
}
