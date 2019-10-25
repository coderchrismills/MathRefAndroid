package com.happymaau.MathRef.Tools;

import android.util.AttributeSet;
import android.view.LayoutInflater;

import android.widget.*;
import com.happymaau.MathRef.GlobalHelpers;
import com.happymaau.MathRef.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

public class AlgebraLine2Solver extends RelativeLayout {
	
	private final static float EPS = 0.00000001f;
	
	private EditText 	line1A;
	private EditText 	line1B;
	private EditText 	line1C;
	private EditText 	line2A;
	private EditText 	line2B;
	private EditText 	line2C;
	private Button 		computeButton;
	private TextView 	textView;
	private Context 	activityContext;
	
	public AlgebraLine2Solver(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.addView(inflater.inflate(R.layout.tools_algebra_line2_solver, null));

        textView = (TextView)findViewById(R.id.results_textview);
    	computeButton = (Button)findViewById(R.id.button1);
    	computeButton.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
            					compute();
            				}
            			});
    	line1A = (EditText)findViewById(R.id.editText01);
    	line1B = (EditText)findViewById(R.id.editText02);
    	line1C = (EditText)findViewById(R.id.editText03);
    	line2A = (EditText)findViewById(R.id.editText04);
    	line2B = (EditText)findViewById(R.id.editText05);
    	line2C = (EditText)findViewById(R.id.editText06);
    	
    	activityContext = context;
    }

    public AlgebraLine2Solver(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public AlgebraLine2Solver(Context context) {
        this(context, null);
    }

    public void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager)GlobalHelpers.mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(line1A.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(line1B.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(line1C.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(line2A.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(line2B.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(line2C.getWindowToken(), 0);
    }

	public void compute()
	{
		dismissKeyboard();
		
		Editable sinput_a1 = line1A.getText(); 
		Editable sinput_b1 = line1B.getText(); 
		Editable sinput_c1 = line1B.getText(); 
		Editable sinput_a2 = line2A.getText(); 
		Editable sinput_b2 = line2B.getText(); 
		Editable sinput_c2 = line2C.getText(); 
		
		float l1x = sinput_a1.length() > 0 ? Float.parseFloat(line1A.getText().toString()) : 0.f;
		float l1y = sinput_b1.length() > 0 ? Float.parseFloat(line1B.getText().toString()) : 0.f;
		float l1z = sinput_c1.length() > 0 ? Float.parseFloat(line1C.getText().toString()) : 0.f;
		float l2x = sinput_a2.length() > 0 ? Float.parseFloat(line2A.getText().toString()) : 0.f;
		float l2y = sinput_b2.length() > 0 ? Float.parseFloat(line2B.getText().toString()) : 0.f;
		float l2z = sinput_c2.length() > 0 ? Float.parseFloat(line2C.getText().toString()) : 0.f;
		
		if(Math.abs(l1x) < EPS && Math.abs(l1y) < EPS)
		{
			@SuppressWarnings("unused")
			AlertDialog show = new AlertDialog.Builder(GlobalHelpers.mDialogContext).setTitle("Error").setTitle("A and B cannot be 0").setNeutralButton("OK",
					new DialogInterface.OnClickListener() { 
						public void onClick(DialogInterface dlg, int id) { 
						}
			}).show();
			return;
		}
		
		if(Math.abs(l2x) < EPS && Math.abs(l2y) < EPS)
		{
			@SuppressWarnings("unused")
			AlertDialog show = new AlertDialog.Builder(GlobalHelpers.mDialogContext).setTitle("Error").setTitle("C and D cannot be 0").setNeutralButton("OK",
					new DialogInterface.OnClickListener() { 
						public void onClick(DialogInterface dlg, int id) { 
						}
			}).show();
			return;
		}
		
		float line1_pt0x = 1.0f; 
		float line1_pt1x = 2.0f;
		float line1_pt0y = 0.f; 
		float line1_pt1y = 0.f;
		float line2_pt0x = 1.f; 
		float line2_pt1x = 2.0f;
		float line2_pt0y = 0.f; 
		float line2_pt1y = 0.f;
		
		// compute each slope
		float slope1 = 0.f;
		if(Math.abs(l1y) < EPS)
		{
			slope1 = Float.MAX_VALUE;
			line1_pt0y = 1.0f; line1_pt1y = 2.f;
			float x1 = (l1z - (1)*l1y)/l1x;
			float x2 = (l1z - (2)*l1y)/l1x;
			line1_pt0x = x1; line1_pt1x = x2;
		}
		else
		{
			float y1 = (l1z - (1)*l1x)/l1y;
			float y2 = (l1z - (2)*l1x)/l1y;
			slope1 = y2 - y1;
			line1_pt0y = y1; line1_pt1y = y2;
		}
		
		float slope2 = 0.f;
		if(Math.abs(l2y) < EPS)
		{
			slope2 = Float.MAX_VALUE;
			line2_pt0y = 1.0f; line2_pt1y = 2.f;
			float x1 = (l2z - (1)*l2y)/l2x;
			float x2 = (l2z - (2)*l2y)/l2x;
			line2_pt0x = x1; line2_pt1x = x2;
		}
		else
		{
			float y1 = (l2z - (1)*l2x)/l2y;
			float y2 = (l2z - (2)*l2x)/l2y;
			line2_pt0y = y1; line2_pt1y = y2;
			slope2 = y2 - y1;
		}
		
		// compute Intersections (All points, no points, 1 point)
		float x1 = line1_pt0x; float y1 = line1_pt0y;
		float x2 = line1_pt1x; float y2 = line1_pt1y;
		float x3 = line2_pt0x; float y3 = line2_pt0y;
		float x4 = line2_pt1x; float y4 = line2_pt1y;
		
		float denom  = (y4-y3) * (x2-x1) - (x4-x3) * (y2-y1);
		float numera = (x4-x3) * (y1-y3) - (y4-y3) * (x1-x3);
		float numerb = (x2-x1) * (y1-y3) - (y2-y1) * (x1-x3);
		
		float xicept = 0.f;
		float yicept = 0.f;
		
		boolean bcoincident = false;
		boolean bparallel = false;
		
		/* Are the line coincident? */
		if (Math.abs(numera) < EPS && Math.abs(numerb) < EPS && Math.abs(denom) < EPS) 
		{
			xicept = (x1 + x2) / 2;
			yicept = (y1 + y2) / 2;
			bcoincident = true;
			bparallel = true;
		}
		else if (Math.abs(denom) < EPS) /* Are the line parallel */
		{ 
			xicept = 0;
			yicept = 0;
			bparallel = true;
		}
		else
		{
			/* Is the intersection along the the segments */
			float mua = numera / denom;
			xicept = x1 + mua * (x2 - x1);
			yicept = y1 + mua * (y2 - y1);
		}
		// compute Acute angle
		float theta = 0.f;
		if(slope1 == Float.MAX_VALUE && slope2 == Float.MAX_VALUE) // both vertical 
		{
			theta = 0.f;
		}
		if(slope1 == Float.MAX_VALUE || slope2 == Float.MAX_VALUE) // only one is vertical
		{
			theta = (float) Math.atan(1/Math.abs(Math.min(slope1, slope2)));
		}
		else 
		{
			theta = (float) Math.atan2(slope2 - slope1, (1 + slope1*slope2));
		}
		
		float dist = 0.f;
		// If they're parallel then compute the distance between the two lines
		if(bparallel && !bcoincident)
		{
			if(x1 == x3) // vertical line
			{
				dist = y3 - y1;
			}
			else // horizontal
			{
				dist = x3 - x1;
			}
		}
		
		String slope1_str	= slope1 == Float.MAX_VALUE ? "Slope 1 is undefined (vertical line)" : slope1 == 0.f ? "Slope 1 : 0.0 (horizontal line)" : ("Slope 1 : " + slope1);
		String slope2_str	= slope2 == Float.MAX_VALUE ? "Slope 2 is undefined (vertical line)" : slope2 == 0.f ? "Slope 2 : 0.0 (horizontal line)" : ("Slope 2 : " + slope2);
		String nisects		= bparallel ? (bcoincident ? "Infinite number of intersections" : ("Distance between lines : %0.6f" + dist)) : "Lines intersect at 1 point";
		String isect		= bparallel ? (bcoincident ? "Lines are coincident" : "Lines are parallel") : "Lines Intersect At : ("+xicept+","+yicept+")";
		String angle		= "Acute Angle : " + (theta * 180.f/Math.PI) + (char)0x00B0;
		String results		= slope1_str + "\n"+slope2_str+"\n"+nisects+"\n"+isect+"\n"+angle;
		textView.setText(results);
	};
}
