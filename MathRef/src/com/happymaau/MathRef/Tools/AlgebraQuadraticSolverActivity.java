package com.happymaau.MathRef.Tools;

import com.happymaau.MathRef.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AlgebraQuadraticSolverActivity extends Activity {
	
	private TextView 	xSquared;
	private EditText 	inputA;
	private EditText 	inputB;
	private EditText 	inputC;
	private Button 		computeButton;
	private TextView 	textView;
	
	@Override
    public void onCreate(Bundle b) {
		super.onCreate(b);
		
    	setContentView(R.layout.tools_algebra_quadratic_solver);
    	textView = (TextView)findViewById(R.id.results_textview);
    	computeButton = (Button)findViewById(R.id.button1);
    	computeButton.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
            					getApplicationContext();
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
		xSquared.setText(Html.fromHtml("x<sup><small>2</small></sup> + "));
    }
    
	public void compute()
	{
		Editable sinput_a = inputA.getText(); 
		Editable sinput_b = inputB.getText(); 
		Editable sinput_c = inputC.getText();
		
		float a = sinput_a.length() > 0 ? Float.parseFloat(inputA.getText().toString()) : 0.f;
		float b = sinput_b.length() > 0 ? Float.parseFloat(inputB.getText().toString()) : 0.f;
		float c = sinput_c.length() > 0 ? Float.parseFloat(inputC.getText().toString()) : 0.f;
	
		boolean bimaginary = false;
		float discriminant = b*b - 4*a*c;
		float realdisc = discriminant;
		if(discriminant < 0.f)
		{
			discriminant = -discriminant;
			bimaginary = true;
		}
	
		float sqdis = (float) Math.abs((0.5f)*Math.sqrt(discriminant)/a);
		float coeff = (float) ((-0.5*b)/a);
	
		String disstr = "";
		String x1str = "";
		String x2str = "";
	
		if(bimaginary)
		{
			//img = @"i";
			disstr = "Discriminant : " + realdisc;
			x1str = "Root 1 = " + coeff + " + " + sqdis + "i";
			x2str = "Root 2 = " + coeff + " - " + sqdis + "i";
		}
		else
		{
			disstr = "Discriminant : " + realdisc;
			x1str = "Root 1 = " + (coeff+sqdis);
			x2str = "Root 2 = " + (coeff-sqdis);
			
		}
		
		String results		= disstr + "\n"+x1str+"\n"+x2str;
		textView.setText(results);
	};
}
