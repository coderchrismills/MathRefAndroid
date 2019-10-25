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
import android.widget.RadioButton;
import android.widget.TextView;

public class TrigRefAngleSolverActivity extends Activity {

	public enum RefAngleQuadrants
	{
		raq_Quad1(1),
		raq_Quad2(2),
		raq_Quad3(3),
		raq_Quad4(4),
		raq_PositiveX(5),
		raq_PositiveY(6),
		raq_NegativeX(7),
		raq_NegativeY(8);
		
		private final int val;
		private RefAngleQuadrants(int nval) { this.val = nval; }
		public int toInt() { return val; }
	};

	private RadioButton	degRadioButton;
	private RadioButton	radRadioButton;
	
	private TextView 	inputA_Name;
	private EditText 	inputA;
	
	private Button 		computeButton;
	private TextView	resultsTextView;
	
	private boolean 	bIsDeg;

	@Override
    public void onCreate(Bundle b) {
		super.onCreate(b);
		
    	setContentView(R.layout.tools_trig_refangle_solver);
		
		degRadioButton		= (RadioButton)findViewById(R.id.deg_radioButton);
		radRadioButton		= (RadioButton)findViewById(R.id.rad_radioButton);
		
		degRadioButton.setOnClickListener(radioListener);
		radRadioButton.setOnClickListener(radioListener);
		
    	inputA_Name 		= (TextView)findViewById(R.id.input_a_name_label);
    	inputA 				= (EditText)findViewById(R.id.input_a);
		
		computeButton 		= (Button)findViewById(R.id.computeButton);
		computeButton.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
								compute();
            				}
            			});
		
		resultsTextView 	= (TextView)findViewById(R.id.results_textview);
		bIsDeg 				= true;
    }
    
	private OnClickListener radioListener = new OnClickListener() {
		public void onClick(View v) {
			// Perform action on clicks
			RadioButton rb = (RadioButton) v;
			bIsDeg = (rb.equals(degRadioButton));
			String str = bIsDeg ? "Degrees" : "Radians";
			inputA_Name.setText(str);
			degRadioButton.setChecked(bIsDeg);
			radRadioButton.setChecked(!bIsDeg);
			
			compute();
		}
	};

	public void compute()
	{
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(inputA.getWindowToken(), 0);
		
		Editable sinput_a = inputA.getText(); 
		
		float angle = sinput_a.length() > 0 ? Float.parseFloat(inputA.getText().toString()) : 0.f;
		
		String outstr = "The reference angle is ";

		float ref_angle = 0.f;
		int quadrant = -1;
		float[] values = { angle, ref_angle, quadrant };
		
		if(bIsDeg)	{ findDegRefAngle(values); }
		else 		{ findRadRefAngle(values); }
		
		angle 		= values[0];
		ref_angle	= values[1];
		quadrant 	= (int)values[2];

		if(quadrant > RefAngleQuadrants.raq_Quad4.toInt()) // quadrantal
		{
			outstr = "The angle is quadrantal on the "; // negative y axis
			if(quadrant == RefAngleQuadrants.raq_PositiveX.toInt()) {
				outstr += "positive x axis";
			}
			else if(quadrant == RefAngleQuadrants.raq_PositiveY.toInt()) {
					outstr += "positive y axis";
			}
			else if(quadrant == RefAngleQuadrants.raq_NegativeX.toInt()) {
					outstr += "negative x axis";
			}
			else if(quadrant == RefAngleQuadrants.raq_NegativeY.toInt()) {
					outstr += "negative y axis";
			}
		}
		else
		{
			if(bIsDeg)	{ outstr += (ref_angle + "" + (char)0x00B0); }
			else 		{ outstr += (ref_angle + " radians"); }
			
			outstr += " in quadrant " + (int)quadrant;
		}
		
		resultsTextView.setText(outstr);
	}

	public void findDegRefAngle(float[] values)
	{
		// values[0] = angle
		// values[1] = ref_angle
		// values[2]  = quadrant

		float angle 	= values[0];
		float ref_angle	= values[1];
		float quadrant 	= values[2];

		int num_rev = (int)(Math.ceil(angle / 360.f));
		if(num_rev > 0)
		{
			if(angle > 0.f)
				angle -= (num_rev * 360.f);
			else
				angle += (num_rev * 360.f);
		}
		
		if(angle < 0.f)
			angle += 360.f;
		
		if(angle > 0.f && angle < 90.f) // quadrant 1
		{
			ref_angle = angle;
			quadrant = RefAngleQuadrants.raq_Quad1.toInt();
		}
		else if(angle > 90.f && angle < 180.f) // quadrant 2
		{
			ref_angle = 180.f - angle;
			quadrant = RefAngleQuadrants.raq_Quad2.toInt();
		}
		else if(angle > 180.f && angle < 270) // quadrant 3
		{
			ref_angle = angle - 180.f;
			quadrant = RefAngleQuadrants.raq_Quad3.toInt();
		}
		else if(angle > 270.f && angle < 360.f) // quadrant 4
		{
			ref_angle = 360.f - angle;
			quadrant = RefAngleQuadrants.raq_Quad4.toInt();
		}
		else
		{
			ref_angle = angle;
			int nangle = Math.round(angle);
			if(nangle == 90)
				quadrant = RefAngleQuadrants.raq_PositiveY.toInt();
			else if(nangle == 180)
				quadrant = RefAngleQuadrants.raq_NegativeX.toInt();
			else if(nangle == 270)
				quadrant = RefAngleQuadrants.raq_NegativeY.toInt();
			else 
				quadrant = RefAngleQuadrants.raq_PositiveX.toInt();
		}

		values[0] = angle;
		values[1] = ref_angle;
		values[2] = quadrant;
	}
	
	public void findRadRefAngle(float[] values)
	{
		// values[0] = angle
		// values[1] = ref_angle
		// values[2]  = quadrant

		float angle 	= values[0];
		float ref_angle	= values[1];
		float quadrant 	= values[2];
		
		float twopi = (float)(2.f*Math.PI);
		float piotwo = (float)(Math.PI / 2.f);

		int num_rev = (int)Math.ceil(angle / twopi);
		if(num_rev > 0)
		{
			if(angle > 0.f)
				angle -= (num_rev * twopi);
			else
				angle += (num_rev * twopi);
		}
		
		if(angle < 0.f)
			angle += twopi;
		
		
		if(angle > 0.f && angle < piotwo) // quadrant 1
		{
			ref_angle = angle;
			quadrant = RefAngleQuadrants.raq_Quad1.toInt();
		}
		else if(angle > piotwo && angle < Math.PI) // quadrant 2
		{
			ref_angle = (float)Math.PI - angle;
			quadrant = RefAngleQuadrants.raq_Quad2.toInt();
		}
		else if(angle > (float)Math.PI && angle < 1.5*(float)Math.PI) // quadrant 3
		{
			ref_angle = angle - (float)Math.PI;
			quadrant = RefAngleQuadrants.raq_Quad3.toInt();
		}
		else if(angle > 1.5*(float)Math.PI && angle < twopi) // quadrant 4
		{
			ref_angle = twopi - angle;
			quadrant = RefAngleQuadrants.raq_Quad4.toInt();
		}
		else
		{
			ref_angle = angle;
			int nangle = (int)(Math.round(Math.toDegrees(angle)));
			if(nangle == 90)
				quadrant = RefAngleQuadrants.raq_PositiveY.toInt();
			else if(nangle == 180)
				quadrant = RefAngleQuadrants.raq_NegativeX.toInt();
			else if(nangle == 270)
				quadrant = RefAngleQuadrants.raq_NegativeY.toInt();
			else 
				quadrant = RefAngleQuadrants.raq_PositiveX.toInt();
		}

		values[0] = angle;
		values[1] = ref_angle;
		values[2] = quadrant;
	}
}
