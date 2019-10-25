package com.happymaau.MathRef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.happymaau.MathRef.Tools.*;

public class ToolsViewActivity extends ListActivity {
    //SubsectionListView subsection = new SubsectionListView();

    ArrayList<String> sectionTitles = new ArrayList<String>();

    public class MainViewAdapter extends ArrayAdapter<String> {

		public MainViewAdapter(Context context, int textViewResourceId, ArrayList<String> sectionTitles) {
			super(context, textViewResourceId, sectionTitles);
		}

		 public View getView(int position, View convertView, ViewGroup parent) {
		    	LayoutInflater inflater = getLayoutInflater();
		    	View row = inflater.inflate(R.layout.simple_list_item_main, parent, false);
		    	TextView label = (TextView)row.findViewById(R.id.text);
		    	label.setText(sectionTitles.get(position));
		    	ImageView icon = (ImageView)row.findViewById(R.id.image);
		    	switch (position) {
		    	// Algebra
		    	case 0: // 2 Line Solver
		    		icon.setImageResource(R.drawable.main_icons_0);
		    		break;
		    	case 1: // Quadratic Solver
		    		icon.setImageResource(R.drawable.main_icons_0);
		    		break;
		    	case 2: // Exponential Solver
		    		icon.setImageResource(R.drawable.main_icons_0);
		    		break;
		    	case 3: // Complex Solver
		    		icon.setImageResource(R.drawable.main_icons_0);
		    		break;
		    	// Geometry
		    	case 4: // Triangle Solver
		    		icon.setImageResource(R.drawable.main_icons_1);
		    		break;
		    	case 5: // Circle Solver
		    		icon.setImageResource(R.drawable.main_icons_1);
		    		break;
		    	// Trig
		    	case 6: // Angle Converter
		    		icon.setImageResource(R.drawable.main_icons_2);
		    		break;
		    	case 7: // Inverse Solver
		    		icon.setImageResource(R.drawable.main_icons_2);
		    		break;
		    	case 8: // Reference Angle Solver
		    		icon.setImageResource(R.drawable.main_icons_2);
		    		break;
		    	// Stats
		    	case 9: // Z Solver
		    		icon.setImageResource(R.drawable.main_icons_11);
		    		break;
		    	// Physics
		    	case 10: // Unit Converter
		    		icon.setImageResource(R.drawable.main_icons_12);
		    		break;
		    	}
			return row;
		}
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        sectionTitles.add("Two Line Solver");
        sectionTitles.add("Quadratic Solver");
        sectionTitles.add("Exponential Solver");
        sectionTitles.add("Complex Solver");
        sectionTitles.add("Triangle Solver");
        sectionTitles.add("Circle Solver");
        sectionTitles.add("Angle Converter");
        sectionTitles.add("Inverse Solver");
        sectionTitles.add("Reference Angle Solver");
        sectionTitles.add("Z Solver");
        sectionTitles.add("Unit Converter");
 
        setListAdapter(new MainViewAdapter(this,R.layout.simple_list_item_main, sectionTitles));
        getListView().setTextFilterEnabled(true);
    }

    protected List getData(String prefix) {
        List<Map> myData = new ArrayList<Map>();
        
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory("android.intent.category.SECTION");

        PackageManager pm = getPackageManager();
        //List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        final AssetManager mgr = getResources().getAssets();
        String assets[] = null;
        try {
            assets = mgr.list("");
            for (int i = 0; i < assets.length; i++) {
                Map<String, String> obj = new HashMap<String, String>();
                obj.put("title", assets[i]);
                myData.add(obj);
            }
        } catch (IOException ex) {
            //Log.v("List error:", "can't list assets");
        }

        return myData;
    }

    @Override
    protected void onListItemClick(ListView parent, View v, int position, long id) {
        
        Intent intent = new Intent(this, AlgebraLine2SolverActivity.class);
        switch (position) {
	        case 0 : 
	        {
	        	intent = new Intent(this, AlgebraLine2SolverActivity.class);
	        	break;
	        }
	        case 1 : 
	        {
	        	intent = new Intent(this, AlgebraQuadraticSolverActivity.class);
	        	break;
	        }
	        case 2 : 
	        {
	        	intent = new Intent(this, AlgebraExponentialSolverActivity.class);
	        	break;
	        }
	        case 3 : 
	        {
	        	intent = new Intent(this, AlgebraComplexSolverActivity.class);
	        	break;
	        }
	        case 4 : 
	        {
	        	intent = new Intent(this, GeometryTriangleSolverActivity.class);
	        	break;
	        }
	        case 5 : 
	        {
	        	intent = new Intent(this, GeometryCircleSolverActivity.class);
	        	break;
	        }
	        case 6 : 
	        {
	        	intent = new Intent(this, TrigAngleDegreeConverterActivity.class);
	        	break;
	        }
	        case 7 : 
	        {
	        	intent = new Intent(this, TrigInverseAngleSolverActivity.class);
	        	break;
	        }
	        case 8 : 
	        {
	        	intent = new Intent(this, TrigRefAngleSolverActivity.class);
	        	break;
	        }
	        case 9 : 
	        {
	        	intent = new Intent(this, StatsZSolverActivity.class);
	        	break;
	        }
	        case 10 : 
	        {
	        	intent = new Intent(this, UnitConverterDetailViewActivity.class);
	        	break;
	        }
        } 
        startActivity(intent);
    }
}
