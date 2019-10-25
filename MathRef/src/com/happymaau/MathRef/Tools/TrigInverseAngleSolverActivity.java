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

public class TrigInverseAngleSolverActivity extends Activity {

	public enum TrigInverseFunctionTypes
	{
		tift_asin(0),
		tift_acos(1),
		tift_atan(2),
		tift_NumInverseFunctionTypes(3);
		
		private final int val;
		private TrigInverseFunctionTypes(int nval) { this.val = nval; }
		public int toInt() { return val; }
	};

	private final static float EPS = 0.00000001f;

	private RadioButton	arcsinRadioButton;
	private RadioButton	arccosRadioButton;
	private RadioButton	arctanRadioButton;
	
	private TextView 	inputA_Name;
	private EditText 	inputA;
	
	private Button 		computeButton;
	private TextView	degreesResultsTextView;
	private TextView 	radiansResultsTextView;
	
	private TrigInverseFunctionTypes 	eFunctionType;

	@Override
    public void onCreate(Bundle b) {
		super.onCreate(b);
		
    	setContentView(R.layout.tools_trig_inverse_angle_solver);
		
		arcsinRadioButton	= (RadioButton)findViewById(R.id.arcsin_radioButton);
		arccosRadioButton	= (RadioButton)findViewById(R.id.arccos_radioButton);
		arctanRadioButton	= (RadioButton)findViewById(R.id.arctan_radioButton);
		
		arcsinRadioButton.setOnClickListener(radioListener);
		arccosRadioButton.setOnClickListener(radioListener);
		arctanRadioButton.setOnClickListener(radioListener);
		
    	inputA_Name 		= (TextView)findViewById(R.id.input_a_name_label);
    	inputA 				= (EditText)findViewById(R.id.input_a);
		
		computeButton 		= (Button)findViewById(R.id.computeButton);
		computeButton.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
								compute();
            				}
            			});
		
		eFunctionType 		= TrigInverseFunctionTypes.tift_asin;

		degreesResultsTextView 	= (TextView)findViewById(R.id.results_degrees_textview);
		radiansResultsTextView	= (TextView)findViewById(R.id.results_radians_textview);
    }
    
	private OnClickListener radioListener = new OnClickListener() {
		public void onClick(View v) {
			// Perform action on clicks
			RadioButton rb = (RadioButton) v;
			boolean bisasin = (rb.equals(arcsinRadioButton));
			boolean bisacos = (rb.equals(arccosRadioButton));
			boolean bisatan = (rb.equals(arctanRadioButton));
			String str = bisasin ? "asin(" : (bisacos ? "acos" : "atan");
			inputA_Name.setText(str);

			eFunctionType = bisasin ? TrigInverseFunctionTypes.tift_asin : 
							(bisacos ? TrigInverseFunctionTypes.tift_acos : TrigInverseFunctionTypes.tift_atan);
			
			arcsinRadioButton.setChecked(bisasin);
			arccosRadioButton.setChecked(bisacos);
			arctanRadioButton.setChecked(bisatan);

			compute();
		}
	};

	public void compute()
	{
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(inputA.getWindowToken(), 0);
		
		Editable str_x = inputA.getText(); 
		
		float rad2deg_ratio = (float)(180.f / Math.PI);
		float x = str_x.length() > 0 ? Float.parseFloat(str_x.toString()) : 0.f;
		
		float rad = 0.f;
		float deg = 0.f;
		if(eFunctionType == TrigInverseFunctionTypes.tift_asin)
		{
			rad = (float)Math.asin(x);
		}
		else if(eFunctionType == TrigInverseFunctionTypes.tift_acos)
		{
			rad = (float)Math.acos(x);
		}
		else
		{
			rad = (float)Math.atan(x);
		}
		deg = rad * rad2deg_ratio;
		
		float rad_round = (float)Math.round(rad);
		float deg_round = (float)Math.round(deg);
		
		float rad_err = (float)Math.abs(rad - rad_round);
		float deg_err = (float)Math.abs(deg - deg_round);
		
		if(rad_err < EPS)
			rad = rad_round;
		if(deg_err < EPS)
			deg = deg_round;
		
		String deg_str = "";
		String rad_str = "";

		if(Float.isNaN(rad))
		{
			deg_str = "No Solution";
			rad_str = "No Solution";
		}
		else
		{
			if(eFunctionType == TrigInverseFunctionTypes.tift_atan)
			{
				deg_str = "Theta = "+deg+" "+(char)0x00B1+" n180"+(char)0x00B0; 
				rad_str = "Theta = "+rad+" "+(char)0x00B1+" n2"+(char)0x03C0+" radians";
			}
			else
			{
				deg_str = "Theta = "+deg+" "+(char)0x00B1+" n180"+(char)0x00B0+"\n"+
								"Theta = -"+deg+" "+(char)0x00B1+" n180"+(char)0x00B0; 
				rad_str = "Theta = "+rad+" "+(char)0x00B1+" n2"+(char)0x03C0+" radians\n"+
								"Theta = -"+rad+" "+(char)0x00B1+" n2"+(char)0x03C0+" radians";
			}
		}

		degreesResultsTextView.setText(deg_str);
		radiansResultsTextView.setText(rad_str);
	}
}
