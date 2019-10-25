package com.happymaau.MathRef.Tools;

import com.happymaau.MathRef.GlobalHelpers;
import com.happymaau.MathRef.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AlgebraComplexSolverActivity extends Activity {
	
	private EditText 	inputA;
	private EditText 	inputB;
	private EditText 	inputR;
	private EditText 	inputT;
	private Button 		computeButton1;
	private Button 		computeButton2;
	private TextView 	resultsTextView1;
	private TextView 	resultsTextView2;
	
	@Override
    public void onCreate(Bundle b) {
		super.onCreate(b);
		
    	setContentView(R.layout.tools_algebra_complex_solver);
    	
		resultsTextView1 = (TextView)findViewById(R.id.results_textview1);
		resultsTextView2 = (TextView)findViewById(R.id.results_textview2);
		
    	computeButton1 = (Button)findViewById(R.id.convert_button1);
    	computeButton1.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
            					getApplicationContext();
								InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            					imm.hideSoftInputFromWindow(inputA.getWindowToken(), 0);
            					imm.hideSoftInputFromWindow(inputB.getWindowToken(), 0);
            					imm.hideSoftInputFromWindow(inputR.getWindowToken(), 0);
								imm.hideSoftInputFromWindow(inputT.getWindowToken(), 0);
            					computeRectToPolar();
            				}
            			});
		
		computeButton2 = (Button)findViewById(R.id.convert_button2);
    	computeButton2.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
            					getApplicationContext();
								InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            					imm.hideSoftInputFromWindow(inputA.getWindowToken(), 0);
            					imm.hideSoftInputFromWindow(inputB.getWindowToken(), 0);
            					imm.hideSoftInputFromWindow(inputR.getWindowToken(), 0);
								imm.hideSoftInputFromWindow(inputT.getWindowToken(), 0);
            					computePolarToRect();
            				}
            			});
		
    	inputA = (EditText)findViewById(R.id.input_a);
    	inputB = (EditText)findViewById(R.id.input_b);
    	inputR = (EditText)findViewById(R.id.input_r);
		inputT = (EditText)findViewById(R.id.input_t);
    }
    
	public void computeRectToPolar()
	{
		Editable sinput_a = inputA.getText(); 
		Editable sinput_b = inputB.getText(); 
		
		float rect_a = sinput_a.length() > 0 ? Float.parseFloat(inputA.getText().toString()) : 0.f;
		float rect_b = sinput_b.length() > 0 ? Float.parseFloat(inputB.getText().toString()) : 0.f;
	
		float r = (float) Math.sqrt(rect_a*rect_a + rect_b*rect_b);
		float t = (float) ((180.f/Math.PI)*Math.atan2(rect_b, rect_a));
		
		String results = "z = " + r + "" + (char)0x2220 + "" + t + "" + (char)0x00B0;
		resultsTextView1.setText(results);
	};
	
	public void computePolarToRect()
	{
		Editable sinput_r = inputR.getText(); 
		Editable sinput_t = inputT.getText();
		
		float polar_r = sinput_r.length() > 0 ? Float.parseFloat(inputR.getText().toString()) : 0.f;
		float polar_t = sinput_t.length() > 0 ? Float.parseFloat(inputT.getText().toString()) : 0.f;
		
		if(polar_t < -180.f || polar_t > 180.f)
		{
			new AlertDialog.Builder(GlobalHelpers.mDialogContext).setTitle("Error").setTitle("Angle must be in the range of -180 to 180").setNeutralButton("OK",
					new DialogInterface.OnClickListener() { 
						public void onClick(DialogInterface dlg, int id) { 
						}
			}).show();
			return;
		}
		
		float ra = (float) (polar_r*Math.cos(polar_t*(Math.PI/180.f)));
		float rb = (float) (polar_r*Math.sin(polar_t*(Math.PI/180.f)));
	
		String results = ra + " + " + rb + "i";
		resultsTextView2.setText(results);
	};
}
