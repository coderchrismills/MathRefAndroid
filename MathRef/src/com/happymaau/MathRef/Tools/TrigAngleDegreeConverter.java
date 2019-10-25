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

public class TrigAngleDegreeConverter extends RelativeLayout {

	private RadioButton	degToRadRadioButton;
	private RadioButton	radToDegRadioButton;
	
	private TextView 	inputA_Name;
	private EditText 	inputA;
	
	private Button 		computeButton;
	private TextView	resultsTextView;
	
	private boolean 		bIsRadToDeg;

	public TrigAngleDegreeConverter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.addView(inflater.inflate(R.layout.tools_trig_angledeg_convter, null));

        degToRadRadioButton		= (RadioButton)findViewById(R.id.deg_to_rad_radioButton);
		radToDegRadioButton		= (RadioButton)findViewById(R.id.rad_to_deg_radioButton);
		
		degToRadRadioButton.setOnClickListener(radioListener);
		radToDegRadioButton.setOnClickListener(radioListener);
		
    	inputA_Name 		= (TextView)findViewById(R.id.input_a_name_label);
    	inputA 				= (EditText)findViewById(R.id.input_a);
		
		computeButton 		= (Button)findViewById(R.id.computeButton);
		computeButton.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
								compute();
            				}
            			});
		
		resultsTextView 	= (TextView)findViewById(R.id.results_textview);
		bIsRadToDeg 		= true;
    }

    public TrigAngleDegreeConverter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public TrigAngleDegreeConverter(Context context) {
        this(context, null);
    }
    
    public void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager)GlobalHelpers.mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputA.getWindowToken(), 0);
    }

	private OnClickListener radioListener = new OnClickListener() {
		public void onClick(View v) {
			// Perform action on clicks
			RadioButton rb = (RadioButton) v;
			bIsRadToDeg = (rb.equals(degToRadRadioButton));
			String str = bIsRadToDeg ? "Degrees" : "Radians";
			inputA_Name.setText(str);
			degToRadRadioButton.setChecked(bIsRadToDeg);
			radToDegRadioButton.setChecked(!bIsRadToDeg);
			
			compute();
		}
	};

	public void compute()
	{	
		dismissKeyboard();
		
		Editable sinput_a = inputA.getText(); 
		
		float angle = sinput_a.length() > 0 ? Float.parseFloat(inputA.getText().toString()) : 0.f;
		
		String outstr = "";

		if(bIsRadToDeg)
		{
			outstr = angle + "" + (char)0x00B0 + "\n = " + 
					(float)Math.toRadians(angle) + " rad \n = " +
					(float)(Math.toRadians(angle)/Math.PI) + "" + (char)0x03C0 + "rad";
		}
		else
		{
			outstr = angle + " rad\n = " 
					+ (float)Math.toDegrees(angle) + "" + (char)0x00B0;
		}
		resultsTextView.setText(outstr);
	}
}
