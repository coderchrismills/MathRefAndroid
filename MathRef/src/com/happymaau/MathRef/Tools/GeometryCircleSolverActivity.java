package com.happymaau.MathRef.Tools;

import com.happymaau.MathRef.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GeometryCircleSolverActivity extends Activity {
	
	private EditText 	inputR;
	private EditText 	inputT;
	private Button 		computeButton;
	private TextView 	textView;
	
	@Override
    public void onCreate(Bundle b) {
		super.onCreate(b);
		
    	setContentView(R.layout.tools_geometry_circle_solver);
    	textView = (TextView)findViewById(R.id.results_textview);
    	computeButton = (Button)findViewById(R.id.button1);
    	computeButton.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
								InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            					imm.hideSoftInputFromWindow(inputR.getWindowToken(), 0);
            					imm.hideSoftInputFromWindow(inputT.getWindowToken(), 0);
            					compute();
            				}
            			});
    	inputR = (EditText)findViewById(R.id.input_r);
    	inputT = (EditText)findViewById(R.id.input_t);
    }
    
	public void compute()
	{
		Editable sinput_r = inputR.getText(); 
		Editable sinput_t = inputT.getText(); 
		
		float fradius = sinput_r.length() > 0 ? Float.parseFloat(inputR.getText().toString()) : 0.f;
		float fdegree = sinput_t.length() > 0 ? Float.parseFloat(inputT.getText().toString()) : 0.f;
	
		float angle_rad = (float)Math.toRadians(fdegree);
		
		// Area of Circle
		float area = (float)(Math.PI * fradius * fradius);
		
		// Circumference of Circle
		float circ = (float)(2*Math.PI*fradius);
		
		// Area of Segment Triangle ABC
		float area1 = (float)(0.5*fradius*fradius*Math.sin(angle_rad));
		
		// Area of Top BDC
		float area2 = (float)(0.5*fradius*fradius*(angle_rad - Math.sin(angle_rad)));
		
		// Area of Sector ABCD
		float areasector = (float)0.5*fradius*fradius*angle_rad;
		
		// Length of Chord BC
		float cordlength = (float)(2*fradius*Math.sin(0.5*angle_rad));
		
		// Length of Arc BDC
		float arclength = fradius*angle_rad;
		
		String areastr 		= "Area: " + area;
		String circstr 		= "Circumference: " + circ;
		String area1str 	= "Area of ABC: " + area1;
		String area2str 	= "Area of BDC: " + area2;
		String sectorstr 	= "Area of Sector ABDC: " + areasector;
		String chordstr 	= "Length of Chord BC: " + cordlength;
		String arcstr 		= "Length of Arc BDC: " + arclength;
	
		String results 		= areastr + "\n" +
								circstr + "\n" +
								area1str + "\n" +
								area2str + "\n" +
								sectorstr + "\n" +
								chordstr + "\n" +
								arcstr + "\n";
	
		textView.setText(results);
	};
}
