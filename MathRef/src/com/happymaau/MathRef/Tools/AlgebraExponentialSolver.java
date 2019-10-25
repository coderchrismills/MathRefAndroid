package com.happymaau.MathRef.Tools;

import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import android.widget.*;
import com.happymaau.MathRef.GlobalHelpers;
import com.happymaau.MathRef.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

public class AlgebraExponentialSolver extends RelativeLayout {
	
	private final static float EPS = 0.00000001f;
	
	private TextView 	xSquared;
	private EditText 	inputA;
	private EditText 	inputB;
	private EditText 	inputC;
	private Button 		computeButton;
	private TextView 	textView;
	
	public AlgebraExponentialSolver(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.addView(inflater.inflate(R.layout.tools_algebra_exponential_solver, null));

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
		xSquared.setText(Html.fromHtml("<sup><small>2</small></sup> + "));
	}
	
	public AlgebraExponentialSolver(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public AlgebraExponentialSolver(Context context) {
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
