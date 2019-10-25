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
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.*;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import com.happymaau.MathRef.Tools.*;

public class ArticleFragment extends Fragment {
    final static String SECTION     = "section";
    final static String SUB_SECTION = "subsection";
    final static String EQUATION    = "equation";
    final static String TOOL        = "tool";

    public enum ArticleViews
    {
        About(0),
        Equation(1),
        Periodic(2),
        TwoLine(3),
        Quadratic(4),
        Exponential(5),
        Complex(6),
        Triangle(7),
        Circle(8),
        AngleConverter(9),
        InverseSolver(10),
        RefAngle(11),
        ZSolver(12),
        UnitConverter(13);

        private final int val;
        private ArticleViews(int nval) { this.val = nval; }
        public int toInt() { return val; }
    }

    ArticleViews detailViewState        = ArticleViews.About;
    LayoutInflater mInflater            = null;
    Equation mCurrentEquation           = null;
    View mCurrentView                   = null;

    int mCurrentTool                    = 0;

    public ArticleFragment() {
        super();
    }
    
    public void SetArticleView(ArticleViews vs) {
    	detailViewState = vs;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.article_view, container, false);
        mInflater = inflater;

        if (savedInstanceState != null) {
            mCurrentEquation = (Equation)savedInstanceState.getParcelable(EQUATION);
        }

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        resetView();
    }

    public void setToolIndex(int toolIndex) {
        mCurrentTool = toolIndex;
    }

    public void resetView() {
        Equation equation = null;
        Bundle args = getArguments();
        if (args != null) {
            equation = (Equation)args.getParcelable(EQUATION);
            mCurrentTool = args.getInt(TOOL);
        }

        if(detailViewState == ArticleViews.Equation) {
            if (equation != null) {
                setWithView(detailViewState, equation);
            } else if (mCurrentEquation != null) {
                setWithView(detailViewState, mCurrentEquation);
            }
        }
        else {
            setWithView(detailViewState);
        }
    }

    public void setWithView(ArticleViews vs) {
        setWithView(vs, null);
    }

    public void setWithView(ArticleViews vs, Equation eq) {
        LinearLayout detailViewContainer = (LinearLayout)GlobalHelpers.mActivity.findViewById(R.id.detail_view_container);
        if(detailViewContainer == null)
            return;

        switch (vs) {
            case About:
                mCurrentView = new AboutDetailView(GlobalHelpers.mContext);
                ((AboutDetailView)mCurrentView).loadAbout();
                break;
            case Periodic:
                mCurrentView = new PeriodicDetailView(GlobalHelpers.mContext);
                ((PeriodicDetailView)mCurrentView).loadPeriodic();
                break;
            case Equation:
                mCurrentView = new EquationDetailView(GlobalHelpers.mContext);
                ((EquationDetailView)mCurrentView).updateWithEquation(eq);
                break;
            case TwoLine:
                mCurrentView = new AlgebraLine2Solver(GlobalHelpers.mContext);
                break;
            case Quadratic:
                mCurrentView = new AlgebraQuadraticSolver(GlobalHelpers.mContext);
                break;
            case Exponential:
                mCurrentView = new AlgebraExponentialSolver(GlobalHelpers.mContext);
                break;
            case Complex:
                mCurrentView = new AlgebraComplexSolver(GlobalHelpers.mContext);
                break;
            case Triangle:
                mCurrentView = new GeometryTriangleSolver(GlobalHelpers.mContext);
                break;
            case Circle:
                mCurrentView = new GeometryCircleSolver(GlobalHelpers.mContext);
                break;
            case AngleConverter:
                mCurrentView = new TrigAngleDegreeConverter(GlobalHelpers.mContext);
                break;
            case InverseSolver:
                mCurrentView = new TrigInverseAngleSolver(GlobalHelpers.mContext);
                break;
            case RefAngle:
                mCurrentView = new TrigRefAngleSolver(GlobalHelpers.mContext);
                break;
            case ZSolver:
                mCurrentView = new StatsZSolver(GlobalHelpers.mContext);
                break;
            case UnitConverter:
                mCurrentView = new UnitConverterDetailView(GlobalHelpers.mContext);
                break;
        }
        detailViewContainer.removeAllViews();
        detailViewContainer.addView(mCurrentView);
        detailViewContainer.invalidate();
        detailViewState = vs;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(detailViewState == ArticleViews.Equation) {
            outState.putParcelable(EQUATION, mCurrentEquation);
        }
    }
}