package com.happymaau.MathRef.Tools;

import com.happymaau.MathRef.GlobalHelpers;
import com.happymaau.MathRef.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AlgebraExponentialSolverActivity extends Activity {
	
	private final static float EPS = 0.00000001f;
	
	private TextView 	xSquared;
	private EditText 	inputA;
	private EditText 	inputB;
	private EditText 	inputC;
	private Button 		computeButton;
	private TextView 	textView;
	
	@Override
    public void onCreate(Bundle b) {
		super.onCreate(b);
		
    	setContentView(R.layout.tools_algebra_exponential_solver);
    	textView = (TextView)findViewById(R.id.results_textview);
    	computeButton = (Button)findViewById(R.id.button1);
    	computeButton.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
								InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            					imm.hideSoftInputFromWindow(inputA.getWindowToken(), 0);
            					imm.hideSoftInputFromWindow(inputB.getWindowToken(), 0);
            					imm.hideSoftInputFromWindow(inputC.getWindowToken(), 0);
            					compute();
            				}
            			});
    	inputA = (EditText)findViewById(R.id.input_a);
    	inputB = (EditText)findViewById(R.id.input_b);
    	inputC = (EditText)findViewById(R.id.input_c);
		xSquared = (TextView)findViewById(R.id.textView2);
		xSquared.setText(Html.fromHtml("<sup><small>2</small></sup> + "));
    }
    
	public void compute()
	{
		Editable sinput_a = inputA.getText(); 
		Editable sinput_b = inputB.getText(); 
		Editable sinput_c = inputC.getText(); 
		
		float exp_a = sinput_a.length() > 0 ? Float.parseFloat(inputA.getText().toString()) : 0.f;
		float exp_b = sinput_b.length() > 0 ? Float.parseFloat(inputB.getText().toString()) : 0.f;
		float exp_c = sinput_c.length() > 0 ? Float.parseFloat(inputC.getText().toString()) : 0.f;
	
		if(exp_b < EPS)
		{
			new AlertDialog.Builder(GlobalHelpers.mDialogContext).setTitle("Error").setTitle("Base must be a positive number greater than 0").setNeutralButton("OK",
					new DialogInterface.OnClickListener() { 
						public void onClick(DialogInterface dlg, int id) { 
						}
			}).show();
			return;
		}
		
		float y = (float) (exp_a * Math.pow(exp_b, 2.f) + exp_c);
		
		String hasm = "Horizontal Asymptote : " + exp_c;
		String yict = "Y-Intercept : "  + (exp_a + exp_c);
		String anpt = "Another Point : (" + 2.0f + " , " + y + ")";
		String results		= hasm+"\n"+yict+"\n"+anpt;
		textView.setText(results);
	};
}
