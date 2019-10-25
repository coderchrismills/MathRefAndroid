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

public class GeometryTriangleSolverActivity extends Activity {

	public enum TriangleSolverTypes {
		tst_SSS,
		tst_SAS,
		tst_AAS, 
		tst_ASA,
		tst_SSA,
		tst_NumTypes
	};
	
	private final static float EPS = 0.00000001f;
	private final static float MAX_VAL = 360.f;
	
	private RadioButton	sssRadioButton;
	private RadioButton	sasRadioButton;
	private RadioButton	aasRadioButton;
	private RadioButton	asaRadioButton;
	private RadioButton	ssaRadioButton;
	
	private TextView 	inputA_Name;
	private TextView 	inputA_Type;
	private EditText 	inputA;
	
	private TextView 	inputB_Name;
	private TextView 	inputB_Type;
	private EditText 	inputB;
	
	private TextView 	inputC_Name;
	private TextView 	inputC_Type;
	private EditText 	inputC;
	
	private Button 		computeButton;
	private TextView	resultsTextView;
	
	private TriangleSolverTypes	nCurrentSolverType;
	
	@Override
    public void onCreate(Bundle b) {
		super.onCreate(b);
		
    	setContentView(R.layout.tools_geometry_triangle_solver);
		
		sssRadioButton		= (RadioButton)findViewById(R.id.sss_radioButton);
		sasRadioButton		= (RadioButton)findViewById(R.id.sas_radioButton);
		aasRadioButton		= (RadioButton)findViewById(R.id.aas_radioButton);
		asaRadioButton		= (RadioButton)findViewById(R.id.asa_radioButton);
		ssaRadioButton		= (RadioButton)findViewById(R.id.ssa_radioButton);
		
		sssRadioButton.setOnClickListener(radioListener);
		sasRadioButton.setOnClickListener(radioListener);
		aasRadioButton.setOnClickListener(radioListener);
		asaRadioButton.setOnClickListener(radioListener);
		ssaRadioButton.setOnClickListener(radioListener);
		
    	inputA_Name 		= (TextView)findViewById(R.id.input_a_name_label);
    	inputA_Type 		= (TextView)findViewById(R.id.input_a_type_label);
    	inputA 				= (EditText)findViewById(R.id.input_a);
		
    	inputB_Name 		= (TextView)findViewById(R.id.input_b_name_label);
    	inputB_Type 		= (TextView)findViewById(R.id.input_b_type_label);
    	inputB 				= (EditText)findViewById(R.id.input_b);
		
		inputC_Name 		= (TextView)findViewById(R.id.input_c_name_label);
    	inputC_Type 		= (TextView)findViewById(R.id.input_c_type_label);
    	inputC 				= (EditText)findViewById(R.id.input_c);
		
		computeButton 		= (Button)findViewById(R.id.computeButton);
		computeButton.setOnClickListener( new OnClickListener() {
            				public void onClick(View v) {
								compute();
            				}
            			});
		
		resultsTextView 	= (TextView)findViewById(R.id.results_textview);
		
		nCurrentSolverType = TriangleSolverTypes.tst_SSS;
    }
    
	private OnClickListener radioListener = new OnClickListener() {
		public void onClick(View v) {
			// Perform action on clicks
			RadioButton rb = (RadioButton) v;
			
			if(rb == sssRadioButton) 
			{ 
				nCurrentSolverType = TriangleSolverTypes.tst_SSS;
				inputA_Name.setText("Side");
				inputA_Type.setText("");
				inputB_Name.setText("Side");
				inputB_Type.setText("");
				inputC_Name.setText("Side");
				inputC_Type.setText("");
				
			}
			else if(rb == sasRadioButton) 
			{ 
				nCurrentSolverType = TriangleSolverTypes.tst_SAS; 
				inputA_Name.setText("Side");
				inputA_Type.setText("");
				inputB_Name.setText("Angle");
				inputB_Type.setText("Degrees");
				inputC_Name.setText("Side");
				inputC_Type.setText("");
			}
			else if(rb == aasRadioButton) 
			{ 
				nCurrentSolverType = TriangleSolverTypes.tst_AAS; 
				inputA_Name.setText("Angle");
				inputA_Type.setText("Degrees");
				inputB_Name.setText("Angle");
				inputB_Type.setText("Degrees");
				inputC_Name.setText("Side");
				inputC_Type.setText("");
			}
			else if(rb == asaRadioButton) 
			{ 
				nCurrentSolverType = TriangleSolverTypes.tst_ASA; 
				inputA_Name.setText("Angle");
				inputA_Type.setText("Degrees");
				inputB_Name.setText("Side");
				inputB_Type.setText("");
				inputC_Name.setText("Angle");
				inputC_Type.setText("Degrees");
			}
			else if(rb == ssaRadioButton) 
			{ 
				nCurrentSolverType = TriangleSolverTypes.tst_SSA; 
				inputA_Name.setText("Side");
				inputA_Type.setText("");
				inputB_Name.setText("Side");
				inputB_Type.setText("");
				inputC_Name.setText("Angle");
				inputC_Type.setText("Degrees");
			}
			
			sssRadioButton.setChecked(nCurrentSolverType == TriangleSolverTypes.tst_SSS);
			sasRadioButton.setChecked(nCurrentSolverType == TriangleSolverTypes.tst_SAS);
			aasRadioButton.setChecked(nCurrentSolverType == TriangleSolverTypes.tst_AAS);
			asaRadioButton.setChecked(nCurrentSolverType == TriangleSolverTypes.tst_ASA);
			ssaRadioButton.setChecked(nCurrentSolverType == TriangleSolverTypes.tst_SSA);
			
			compute();
		}
	};

	public void compute()
	{
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(inputA.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(inputB.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(inputC.getWindowToken(), 0);
		
		Editable sinput_a = inputA.getText(); 
		Editable sinput_b = inputB.getText(); 
		Editable sinput_c = inputC.getText(); 
		
		float val0 = sinput_a.length() > 0 ? Float.parseFloat(inputA.getText().toString()) : 0.f;
		float val1 = sinput_b.length() > 0 ? Float.parseFloat(inputB.getText().toString()) : 0.f;
		float val2 = sinput_c.length() > 0 ? Float.parseFloat(inputC.getText().toString()) : 0.f;
		
		if(val0 < EPS && val1 < EPS && val2 < EPS)
			return;
		
		switch (nCurrentSolverType) {
			case tst_SSS :
			{
				// 3, 40, 5 is not a valid triangle... fix this.
				if(val1 < EPS || val2 < EPS)
				{
					setInvalid();
					return;
				}
				else
				{
					float angle_a = solveAngle(val1, val2, val0);
					float angle_b = solveAngle(val2, val0, val1);
					float angle_c = solveAngle(val0, val1, val2);
					if(Math.abs(angle_a) > MAX_VAL || Math.abs(angle_b) > MAX_VAL || Math.abs(angle_c) > MAX_VAL)
					{
						setInvalid();
						return;
					}
					
					String str_angle_a 	= "Angle A: " + angle_a + "" + (char)0x00B0;
					String str_angle_b 	= "Angle B: " + angle_b + "" + (char)0x00B0;
					String str_angle_c 	= "Angle C: " + angle_c + "" + (char)0x00B0;
					String results 		= str_angle_a + "\n" + str_angle_b + "\n" + str_angle_c;
					resultsTextView.setText(results);
				}
				break;
			}
			case tst_SAS :
			{
				float side_a 	= solveSide(val0, val2, val1);
				float angle_b 	= solveAngle(val2, side_a, val0);
				float angle_c 	= solveAngle(side_a, val0, val2);
				if(Math.abs(angle_b) > MAX_VAL || Math.abs(angle_c) > MAX_VAL)
				{
					setInvalid();
					return;
				}
				
				String str_side_a	= "Side a: " + side_a;
				String str_angle_b	= "Angle B: " + angle_b + "" + (char)0x00B0;
				String str_angle_c 	= "Angle C: " + angle_c + "" + (char)0x00B0;
				String results 		= str_side_a + "\n" + str_angle_b + "\n" + str_angle_c;
				resultsTextView.setText(results);
				
				break;
			}
			case tst_AAS :
			{
				float angle_a = val0;
				float angle_b = val1;
				float angle_c = 180.f - angle_b - angle_a;
				float ratio = val2 / (float)Math.sin(Math.toRadians(angle_a));
				float side_b = ratio * (float)Math.sin(Math.toRadians(angle_b));
				float side_c = ratio * (float)Math.sin(Math.toRadians(angle_c));
				
				String str_angle_c 	= "Angle C: " + angle_c + "" + (char)0x00B0;
				String str_side_b 	= "Side b: " + side_b;
				String str_side_c 	= "Side c: " + side_c;
				String results 		= str_angle_c + "\n" + str_side_b + "\n" + str_side_c;
				resultsTextView.setText(results);
				break;
			}
			case tst_ASA :
			{
				float angle_a = val0;
				float side_c = val1;
				float angle_b = val2;
				float angle_c = 180.f - angle_a - angle_b;
				float ratio = side_c / (float)Math.sin(Math.toRadians(angle_c));
				float side_a = ratio * (float)Math.sin(Math.toRadians(angle_a));
				float side_b = ratio * (float)Math.sin(Math.toRadians(angle_b));
				
				String str_angle_c 	= "Angle C: " + angle_c + "" + (char)0x00B0;
				String str_side_a 	= "Side a: " + side_a;
				String str_side_b 	= "Side b: " + side_b; 
				String results 		= str_angle_c + "\n" + str_side_a + "\n" + str_side_b;
				resultsTextView.setText(results);
				break;
			}
			case tst_SSA :
			{
				float known_side = val0;
				float known_angle = val2;
				float partial_side = val1;
				float ratio = known_side / (float)Math.sin(Math.toRadians(known_angle));
				float temp = partial_side / ratio;
				float partial_angle0 = (float)Math.toDegrees(Math.asin(temp));
				float partial_angle1 = 180 - partial_angle0;
				float unknown_angle0 = 180 - known_angle - partial_angle0;
				float unknown_angle1 = 180 - known_angle - partial_angle1;
				float unknown_side0 = ratio * (float)Math.sin(Math.toRadians(unknown_angle0));
				float unknown_side1 = ratio * (float)Math.sin(Math.toRadians(unknown_angle1));
				
				String str_partial_angle0 	= "Angle B1: " + partial_angle0 + "" + (char)0x00B0 + "";
				String str_partial_angle1 	= "Angle B2: " + partial_angle1 + "" + (char)0x00B0 + "\n";
				String str_unknown_angle0	= "Angle C1: " + unknown_angle0 + "" + (char)0x00B0 + "";
				String str_unknown_angle1 	= "Angle C2: " + unknown_angle1 + "" + (char)0x00B0 + "\n";
				String str_unknown_side0	= "Side a1: " + unknown_side0;
				String str_unknown_side1 	= "Side a2: " + unknown_side1;
				String results				= 	str_partial_angle0 + "\n" +
												str_partial_angle1 + "\n" +
												str_unknown_angle0 + "\n" +
												str_unknown_angle1 + "\n" +
												str_unknown_side0 + "\n" +
												str_unknown_side1;
				resultsTextView.setText(results);
				break;
			}
			default : break;
		}
	}
	
	public void setInvalid()
	{
		resultsTextView.setText("Not a valid triangle");
	}

	public float solveSide(float sideA, float sideB, float angleC)
	{
		return (float)Math.sqrt(sideA * sideA + sideB * sideB - 2 * sideA * sideB * Math.cos(Math.toRadians(angleC)));
	}
	
	public float solveAngle(float sideA, float sideB, float sideC)
	{
		// Do error checking!
		float temp = (sideA * sideA + sideB * sideB - sideC * sideC) / (2 * sideA * sideB);
		if(temp >= -1 && temp < 1.f)
			return (float)Math.toDegrees(Math.acos(temp));
		return Float.MAX_VALUE;
	}
	
}
