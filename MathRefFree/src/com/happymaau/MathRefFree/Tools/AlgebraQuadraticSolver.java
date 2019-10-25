package com.happymaau.MathRefFree.Tools;

import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import android.widget.*;
import com.happymaau.MathRefFree.GlobalHelpers;
import com.happymaau.MathRefFree.R;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

public class AlgebraQuadraticSolver extends RelativeLayout {
	
	private TextView 	xSquared;
	private EditText 	inputA;
	private EditText 	inputB;
	private EditText 	inputC;
	private Button 		computeButton;
	private TextView 	textView;
	
	public AlgebraQuadraticSolver(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.addView(inflater.inflate(R.layout.tools_algebra_quadratic_solver, null));

        textView = (TextView)findViewById(R.id.results_textview);
    	computeButton = (Button)findViewById(R.id.button1);
    	computeButton.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
            					compute();
            				}
            			});
    	inputA = (EditText)findViewById(R.id.input_a);
    	inputB = (EditText)findViewById(R.id.input_b);
    	inputC = (EditText)findViewById(R.id.input_c);
		xSquared = (TextView)findViewById(R.id.textView2);
		xSquared.setText(Html.fromHtml("x<sup><small>2</small></sup> + "));

    }
    
    public AlgebraQuadraticSolver(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public AlgebraQuadraticSolver(Context context) {
        this(context, null);
    }
    
    public void dismissKeyboard() {
    	InputMethodManager imm = (InputMethodManager)GlobalHelpers.mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(inputA.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(inputB.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(inputC.getWindowToken(), 0);
    }
    
	public void compute()
	{
		dismissKeyboard();
		
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
