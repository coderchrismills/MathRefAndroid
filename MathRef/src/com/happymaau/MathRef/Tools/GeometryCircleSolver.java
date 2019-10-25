package com.happymaau.MathRef.Tools;

import android.util.AttributeSet;
import android.view.LayoutInflater;

import android.widget.*;
import com.happymaau.MathRef.GlobalHelpers;
import com.happymaau.MathRef.R;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

public class GeometryCircleSolver extends RelativeLayout {
	
	private EditText 	inputR;
	private EditText 	inputT;
	private Button 		computeButton;
	private TextView 	textView;
	
	public GeometryCircleSolver(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.addView(inflater.inflate(R.layout.tools_geometry_circle_solver, null));

        textView = (TextView)findViewById(R.id.results_textview);
    	computeButton = (Button)findViewById(R.id.button1);
    	computeButton.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
								compute();
            				}
            			});
    	inputR = (EditText)findViewById(R.id.input_r);
    	inputT = (EditText)findViewById(R.id.input_t);
    }
    
    public GeometryCircleSolver(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public GeometryCircleSolver(Context context) {
        this(context, null);
    }

    public void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager)GlobalHelpers.mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputR.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(inputT.getWindowToken(), 0);
    }

	public void compute()
	{
		dismissKeyboard();
		
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
