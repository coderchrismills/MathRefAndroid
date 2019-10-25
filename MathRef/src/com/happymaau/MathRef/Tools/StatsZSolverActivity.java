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

public class StatsZSolverActivity extends Activity {
	
	private EditText 	inputZLeft;
	private EditText 	inputZRight;
	private Button 		leftButton;
	private Button 		betweenButton;
	private Button 		rightButton;
	private TextView 	resultsTextView;
	
	@Override
    public void onCreate(Bundle b) {
		super.onCreate(b);
		
    	setContentView(R.layout.tools_stats_z_solver);

    	resultsTextView = (TextView)findViewById(R.id.results_textview);
    	
    	leftButton = (Button)findViewById(R.id.left_button);
    	leftButton.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
								InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            					imm.hideSoftInputFromWindow(inputZLeft.getWindowToken(), 0);
            					imm.hideSoftInputFromWindow(inputZRight.getWindowToken(), 0);
            					computeLeft();
            				}
            			});
    	
    	betweenButton = (Button)findViewById(R.id.between_button);
    	betweenButton.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
								InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            					imm.hideSoftInputFromWindow(inputZLeft.getWindowToken(), 0);
            					imm.hideSoftInputFromWindow(inputZRight.getWindowToken(), 0);
            					computeBetween();
            				}
            			});

        rightButton = (Button)findViewById(R.id.right_button);
    	rightButton.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
								InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            					imm.hideSoftInputFromWindow(inputZLeft.getWindowToken(), 0);
            					imm.hideSoftInputFromWindow(inputZRight.getWindowToken(), 0);
            					computeRight();
            				}
            			});

    	inputZLeft = (EditText)findViewById(R.id.input_zleft);
    	inputZRight = (EditText)findViewById(R.id.input_zright);
    }
    
    public void computeLeft()
    {
    	Editable input_str = inputZLeft.getText();
    	float x = input_str.length() > 0 ? Float.parseFloat(input_str.toString()) : 0.f;
	
		x = (float)Math.max(-5, x);
		x = (float)Math.min(5, x);
		
		x = computeZ(x);

		x = (x < 0.000001) ? 0.f : x;
		x = (float)Math.min(x, 1.0);
		
		resultsTextView.setText("Area / Probability: " + x);
    }

    public void computeRight()
    {
    	Editable input_str = inputZRight.getText();
    	float x = input_str.length() > 0 ? Float.parseFloat(input_str.toString()) : 0.f;
	
		x = (float)Math.max(-5, x);
		x = (float)Math.min(5, x);
		
		x = computeZ(x);
		
		float result = 1.0f - x;
		
		result = (result < 0.000001) ? 0.f : result;
		result = (float)Math.min(result, 1.0);
		
		resultsTextView.setText("Area / Probability: " + result);
    }

    public void computeBetween()
    {
    	Editable input_left = inputZLeft.getText();
    	Editable input_right = inputZRight.getText();

    	float x = input_left.length() > 0 ? Float.parseFloat(input_left.toString()) : 0.f;
		float y = input_right.length() > 0 ? Float.parseFloat(input_right.toString()) : 0.f;
		
		x = computeZ(x);
		y = computeZ(y);
		
		x = (float)Math.max(-5, x);
		x = (float)Math.min(5, x);
		
		y = (float)Math.max(-5, y);
		y = (float)Math.min(5, y);
		
		if(x > y)
		{
			float tmp = x;
			x = y;
			y = tmp;
		}
			
		float result = y - x;
		
		result = (result < 0.000001) ? 0.f : result;
		result = (float)Math.min(result, 1.0);
		
		resultsTextView.setText("Area / Probability: " + result);	
    }

    public float computeZ(float x)
	{
		// constants
	    double a1 =  0.254829592;
	    double a2 = -0.284496736;
	    double a3 =  1.421413741;
	    double a4 = -1.453152027;
	    double a5 =  1.061405429;
	    double p  =  0.3275911;
		
	    // Save the sign of x
	    int sign = 1;
	    if (x < 0)
	        sign = -1;
	    x = (float)(Math.abs(x)/Math.sqrt(2.0));
		
	    // A&S formula 7.1.26
	    double t = 1.0/(1.0 + p*x);
	    double y = (float)(1.0 - (((((a5*t + a4)*t) + a3)*t + a2)*t + a1)*t*Math.exp(-x*x));
		
		return (float)(0.5*(1.0 + sign*y));
	}
}
