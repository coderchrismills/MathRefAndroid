package com.happymaau.MathRef;

import com.happymaau.MathRef.R;
import com.happymaau.MathRef.Tools.UnitConverterDetailView.Categories;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

public class UnitConverterWebWidget extends Activity {
    
	public class InputOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            currentInputType = pos;
            compute();
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }

    public class OutputOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            currentOutputType = pos;
            compute();
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
    
    public enum Categories 
    {
        COMPUTE,
        ANGLE,
        LENGTH,
        AREA,
        VOLUME,
        MASS,
        FORCE,
        SPEED,
        ENERGY,
        POWER,
        PRESSURE,
        TEMPERATURE,
        TIME
    }

    private int currentCategory;
    private int currentInputType;
    private int currentOutputType;

    private EditText inputText;
    private TextView outputText;
    private ImageButton computeButton;
    private ImageButton swapButton;
    private Spinner inputSpinner;
    private Spinner outputSpinner;

    private ImageButton bitByteButton;
    private ImageButton angleButton;
    private ImageButton lengthButton;
    private ImageButton areaButton;
    private ImageButton volumeButton;
    private ImageButton massButton;
    private ImageButton forceButton;
    private ImageButton speedButton;
    private ImageButton energyButton;
    private ImageButton powerButton;
    private ImageButton pressureButton;
    private ImageButton temperatureButton;
    private ImageButton timeButton;
    
    ArrayList<String> spinnerValues = new ArrayList<String>();
    ArrayList<Float> converterValues = new ArrayList<Float>();
    ArrayList<String> converterNames = new ArrayList<String>();

    ArrayAdapter inputAdapter   = null;
    ArrayAdapter ouputAdapter   = null;

    public UnitConverterWebWidget() {
    	super();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.unit_view);
        loadView();
    }
    
    public void loadView() {

        inputText = (EditText)findViewById(R.id.input_text);
        outputText = (TextView)findViewById(R.id.output_text);

    	computeButton = (ImageButton)findViewById(R.id.solve_button);
        computeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                compute();
            }
        });
        swapButton = (ImageButton)findViewById(R.id.swap_button);
        swapButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	int temp = currentInputType;
            	currentInputType = currentOutputType;
            	currentOutputType = temp;

                inputSpinner.setSelection(currentInputType);
                outputSpinner.setSelection(currentOutputType);
                
                compute();
            }
        });

        inputSpinner = (Spinner)findViewById(R.id.input_spinner);
        outputSpinner = (Spinner)findViewById(R.id.output_spinner);
        
        bitByteButton  = (ImageButton)findViewById(R.id.imageButton01);
        bitByteButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetToCategory(Categories.COMPUTE);
            }
        });
        angleButton  = (ImageButton)findViewById(R.id.imageButton02);
        angleButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetToCategory(Categories.ANGLE);
            }
        });
        lengthButton  = (ImageButton)findViewById(R.id.imageButton03);
        lengthButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetToCategory(Categories.LENGTH);
            }
        });
        areaButton  = (ImageButton)findViewById(R.id.imageButton04);
        areaButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetToCategory(Categories.AREA);
            }
        });
        volumeButton  = (ImageButton)findViewById(R.id.imageButton05);
        volumeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetToCategory(Categories.VOLUME);
            }
        });
        massButton  = (ImageButton)findViewById(R.id.imageButton06);
        massButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetToCategory(Categories.MASS);
            }
        });
        forceButton  = (ImageButton)findViewById(R.id.imageButton07);
        forceButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetToCategory(Categories.FORCE);
            }
        });
        speedButton  = (ImageButton)findViewById(R.id.imageButton08);
        speedButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetToCategory(Categories.SPEED);
            }
        });
        energyButton  = (ImageButton)findViewById(R.id.imageButton09);
        energyButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetToCategory(Categories.ENERGY);
            }
        });
        powerButton  = (ImageButton)findViewById(R.id.imageButton10);
        powerButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetToCategory(Categories.POWER);
            }
        });
        pressureButton  = (ImageButton)findViewById(R.id.imageButton11);
        pressureButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetToCategory(Categories.PRESSURE);
            }
        });
        temperatureButton  = (ImageButton)findViewById(R.id.imageButton12);
        temperatureButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetToCategory(Categories.TEMPERATURE);
            }
        });
        timeButton  = (ImageButton)findViewById(R.id.imageButton13);
        timeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ResetToCategory(Categories.TIME);
            }
        });
        
        setupView(Categories.COMPUTE.ordinal());
    }    

    public void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputText.getWindowToken(), 0);
    }

    public void setupView(int compute)
    {
        currentCategory = compute;

        spinnerValues.clear();
        converterValues.clear();
        converterNames.clear();
        
        Categories category = Categories.values()[compute];
        
        switch(category)
        {
            case COMPUTE :
            {
                converterNames.add("bit");
                spinnerValues.add("Bit");
                converterValues.add(8192.0f);
                
                converterNames.add("B");
                spinnerValues.add("Byte");
                converterValues.add(1024.0f);
                
                converterNames.add("KB");
                spinnerValues.add("Kilobyte");
                converterValues.add(1.0f);
                
                converterNames.add("MB");
                spinnerValues.add("Megabyte");
                converterValues.add(0.0009765625f);
                
                converterNames.add("GB");
                spinnerValues.add("Gigabyte");
                converterValues.add(9.53674316e-07f);
                
                converterNames.add("TB");
                spinnerValues.add("Terabyte");
                converterValues.add(9.31322575e-10f);
                
                converterNames.add("PB");
                spinnerValues.add("Petabyte");
                converterValues.add(9.31322575e-13f);
                
                converterNames.add("EB");
                spinnerValues.add("Exabyte");
                converterValues.add(8.881784200000001e-16f);
                
                break;
            }
            case ANGLE :
            {
                converterNames.add(""+(char)0x00B0);
                spinnerValues.add("Degrees");
                converterValues.add(1.f);
                
                converterNames.add("Rad");
                spinnerValues.add("Radians");
                converterValues.add(0.01745329f);
                
                converterNames.add("g");
                spinnerValues.add("Gradians");
                converterValues.add(1.1111111f);
                
                break;
            }
            case LENGTH :
            {
                converterNames.add("A");
                spinnerValues.add("Angstroms");
                converterValues.add(10000000000.0f);
                
                converterNames.add(""+(char)0x03BC+"m");
                spinnerValues.add("Microns");
                converterValues.add(1000000.0f);
                
                converterNames.add("mm");
                spinnerValues.add("Millimeters");
                converterValues.add(1000.0f);
                
                converterNames.add("cm");
                spinnerValues.add("Centimeters");
                converterValues.add(100.0f);
                
                converterNames.add("m");
                spinnerValues.add("Meters");
                converterValues.add(1.0f);
                
                converterNames.add("km");
                spinnerValues.add("Kilometers");
                converterValues.add(0.001f);
                
                converterNames.add("in");
                spinnerValues.add("Inches");
                converterValues.add(39.37008f);
                
                converterNames.add("ft");
                spinnerValues.add("Feet");
                converterValues.add(3.28084f);
                
                converterNames.add("yd");
                spinnerValues.add("Yard");
                converterValues.add(1.093613f);
                
                converterNames.add("mi");
                spinnerValues.add("Miles");
                converterValues.add(0.0006213712f);
                
                converterNames.add("nmi");
                spinnerValues.add("Nautical Miles");
                converterValues.add(0.0005399568f);
                
                converterNames.add("AU");
                spinnerValues.add("Astronomical Units");
                converterValues.add(6.684587e-12f);
                
                converterNames.add("ly");
                spinnerValues.add("Light Years");
                converterValues.add(1.057001e-16f);
                
                converterNames.add("pc");
                spinnerValues.add("Parsecs");
                converterValues.add(3.241491e-17f);
                
                break;
            }
            case AREA : 
            {
                converterNames.add("in"+(char)0x00B2);
                spinnerValues.add("Square Inches");
                converterValues.add(1.0f);
                
                converterNames.add("ft"+(char)0x00B2);
                spinnerValues.add("Square Feet");
                converterValues.add(0.006944444f);
                
                converterNames.add("yd"+(char)0x00B2);
                spinnerValues.add("Square Yard");
                converterValues.add(0.0007716053f);
                
                converterNames.add("mi"+(char)0x00B2);
                spinnerValues.add("Square Miles");
                converterValues.add(2.490977e-10f);
                
                converterNames.add("mm"+(char)0x00B2);
                spinnerValues.add("Square Millimeters");
                converterValues.add(645.16f);
                
                converterNames.add("cm"+(char)0x00B2);
                spinnerValues.add("Square Centimeters");
                converterValues.add(6.4516f);
                
                converterNames.add("m"+(char)0x00B2);
                spinnerValues.add("Square Meters");
                converterValues.add(0.00064516f);
                
                converterNames.add("km"+(char)0x00B2);
                spinnerValues.add("Square Kilometers");
                converterValues.add(6.4516e-10f);
                
                converterNames.add("ac");
                spinnerValues.add("Acres");
                converterValues.add(1.594225e-07f);
                
                converterNames.add("ha");
                spinnerValues.add("Hectares");
                converterValues.add(6.451599999999999e-08f);
                
                break;
            }
            case VOLUME : 
            {
                converterNames.add("gal");
                spinnerValues.add("Gallon (US)");
                converterValues.add(1.0f);
                
                converterNames.add("gal");
                spinnerValues.add("Gallon (Imperial)");
                converterValues.add(0.8326742f);
                
                converterNames.add("L");
                spinnerValues.add("Liter");
                converterValues.add(3.785412f);
                
                converterNames.add("tbsp");
                spinnerValues.add("Tablespoon (US)");
                converterValues.add(256.0f);
                
                converterNames.add("tsp");
                spinnerValues.add("Teaspoon (US)");
                converterValues.add(768.0f);
                
                converterNames.add("fl oz");
                spinnerValues.add("Fluid Ounces");
                converterValues.add(128.0f);
                
                converterNames.add("cu in");
                spinnerValues.add("Cubic Inches");
                converterValues.add(231.0f);
                
                converterNames.add("cu ft");
                spinnerValues.add("Cubic Feet");
                converterValues.add(0.133681f);
                
                converterNames.add("cu cm");
                spinnerValues.add("Cubic Centimeters");
                converterValues.add(3785.412f);
                
                converterNames.add("cu m");
                spinnerValues.add("Cubic Meters");
                converterValues.add(0.003785412f);
                
                converterNames.add("dram");
                spinnerValues.add("Drams (US)");
                converterValues.add(1024.0f);
                
                converterNames.add("pints");
                spinnerValues.add("Pints");
                converterValues.add(8.0f);
                
                converterNames.add("cup");
                spinnerValues.add("Cups");
                converterValues.add(16.0f);
                
                converterNames.add("qt");
                spinnerValues.add("Quarts (US)");
                converterValues.add(4.0f);
                
                break;
            }
            case MASS :
            {
                converterNames.add("oz");
                spinnerValues.add("Ounces (US)");
                converterValues.add(1.0f);
                
                converterNames.add("lb");
                spinnerValues.add("Pounds (US)");
                converterValues.add(0.0625f);
                
                converterNames.add("S/T");
                spinnerValues.add("Short Tons (US)");
                converterValues.add(3.125e-05f);
                
                converterNames.add("L/T");
                spinnerValues.add("Long Tons (UK)");
                converterValues.add(2.790179e-05f);
                
                converterNames.add("mg");
                spinnerValues.add("Milligrams");
                converterValues.add(28349.52f);
                
                converterNames.add("g");
                spinnerValues.add("Grams");
                converterValues.add(28.34952f);
                
                converterNames.add("kg");
                spinnerValues.add("Kilograms");
                converterValues.add(0.02834952f);
                
                converterNames.add("st");
                spinnerValues.add("Stones");
                converterValues.add(0.004464286f);

                break;
            }
            case FORCE :
            {
                converterNames.add("N");
                spinnerValues.add("Newtons");
                converterValues.add(1.0f);
                
                converterNames.add("dyn");
                spinnerValues.add("Dynes");
                converterValues.add(100000.0f);
                
                converterNames.add("kg");
                spinnerValues.add("Kilograms");
                converterValues.add(0.1019716f);
                
                converterNames.add("lb");
                spinnerValues.add("Pounds");
                converterValues.add(0.224809f);
                
                converterNames.add("pdl");
                spinnerValues.add("Poundals");
                converterValues.add(7.233011f);

                break;
            }
            case SPEED :
            {
                converterNames.add("ft/s");
                spinnerValues.add("Feet/Second");
                converterValues.add(1.0f);
                
                converterNames.add("ft/min");
                spinnerValues.add("Feet/Minute");
                converterValues.add(60.0f);
                
                converterNames.add("mipmin");
                spinnerValues.add("Miles/Minute");
                converterValues.add(0.01136364f);
                
                converterNames.add("mph");
                spinnerValues.add("Miles/Hour");
                converterValues.add(0.6818182f);
                
                converterNames.add("m/s");
                spinnerValues.add("Meters/Second");
                converterValues.add(0.3048f);
                
                converterNames.add("km/min");
                spinnerValues.add("Kilometers/Minute");
                converterValues.add(0.018288f);
                
                converterNames.add("km/h");
                spinnerValues.add("Kilometers/Hour");
                converterValues.add(1.09728f);
                
                converterNames.add("kn");
                spinnerValues.add("Knots");
                converterValues.add(0.5924838f);

                break;
            }
            case ENERGY :
            {
                converterNames.add("BTU");
                spinnerValues.add("British Thermal Units");
                converterValues.add(1.0f);
                
                converterNames.add("ft lb");
                spinnerValues.add("Foot Pounds");
                converterValues.add(778.1693f);
                
                converterNames.add("erg");
                spinnerValues.add("Ergs");
                converterValues.add(10550560000.0f);
                
                converterNames.add("kW-h");
                spinnerValues.add("Kilowatt Hours");
                converterValues.add(0.0002930711f);
                
                converterNames.add("W-h");
                spinnerValues.add("Watt Hours");
                converterValues.add(0.2930711f);
                
                converterNames.add("kg-cal");
                spinnerValues.add("Kilo-Calories");
                converterValues.add(0.2519958f);
                
                converterNames.add("cal");
                spinnerValues.add("Calories");
                converterValues.add(251.9958f);
                
                converterNames.add("kg-m");
                spinnerValues.add("Kilogram Meters");
                converterValues.add(107.5858f);
                
                converterNames.add("n-m");
                spinnerValues.add("Newton Meters");
                converterValues.add(1055.056f);
                
                converterNames.add("J");
                spinnerValues.add("Joules Meters");
                converterValues.add(1055.056f);

                break;
            }
            case POWER :
            {
                converterNames.add("BTU/min");
                spinnerValues.add("BTU/Minute");
                converterValues.add(1.0f);
                
                converterNames.add("ft-lb/s");
                spinnerValues.add("Foot Pounds/Second");
                converterValues.add(12.969490f);
                
                converterNames.add("ft-lb/m");
                spinnerValues.add("Foot Pounds/Minute");
                converterValues.add(778.16920f);
                
                converterNames.add("hp");
                spinnerValues.add("Hoursepower");
                converterValues.add(0.023580890f);
                
                converterNames.add("w");
                spinnerValues.add("Watts");
                converterValues.add(17.584260f);
                
                converterNames.add("kw");
                spinnerValues.add("Kilowatts");
                converterValues.add(0.01758426f);
                
                break;
            }
            case PRESSURE :
            {
                converterNames.add("psi");
                spinnerValues.add("Pounds/Square Inch");
                converterValues.add(1.0f);
                
                converterNames.add("pft");
                spinnerValues.add("Pounds/Square Foot");
                converterValues.add(144.0f);
                
                converterNames.add("at");
                spinnerValues.add("Atmospheres");
                converterValues.add(0.068045960f);
                
                converterNames.add("bars");
                spinnerValues.add("Bars");
                converterValues.add(0.068947570f);
                
                converterNames.add("inHg");
                spinnerValues.add("Inches of Mercury");
                converterValues.add(2.036021f);
                
                converterNames.add("cmHg");
                spinnerValues.add("Centimeters of Mercury");
                converterValues.add(5.171493f);
                
                converterNames.add("kg/m"+(char)0x00B2);
                spinnerValues.add("Kilograms/Square Meter");
                converterValues.add(703.0696f);
                
                converterNames.add("pa");
                spinnerValues.add("Pascals");
                converterValues.add(6894.757f);

                break;
            }
            case TEMPERATURE :
            {
                converterNames.add(""+(char)0x00B0+"f");
                spinnerValues.add("Fahrenheit");
                converterValues.add(-999.0f);
                
                converterNames.add(""+(char)0x00B0+"C");
                spinnerValues.add("Celsius");
                converterValues.add(-999.0f);
                
                converterNames.add("K");
                spinnerValues.add("Kelvin");
                converterValues.add(-272.15f);

                break;
            }
            case TIME :
            {
                converterNames.add("ms");
                spinnerValues.add("Millisecond");
                converterValues.add(1.0f);
                
                converterNames.add("s");
                spinnerValues.add("Second");
                converterValues.add(0.001f);
                
                converterNames.add("min");
                spinnerValues.add("Minute");
                converterValues.add(1.666667e-05f);
                
                converterNames.add("h");
                spinnerValues.add("Hour");
                converterValues.add(2.777778e-07f);
                
                converterNames.add("d");
                spinnerValues.add("Day");
                converterValues.add(1.157407e-08f);
                
                converterNames.add("wk");
                spinnerValues.add("Week");
                converterValues.add(1.653439e-09f);
                
                converterNames.add("y");
                spinnerValues.add("Year");
                converterValues.add(3.170979e-11f);

                break;
            }
        }

        inputAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerValues);
        inputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ouputAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerValues);
        ouputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        inputSpinner.setAdapter(inputAdapter);
        inputSpinner.setOnItemSelectedListener(new InputOnItemSelectedListener());

        outputSpinner.setAdapter(inputAdapter);
        outputSpinner.setOnItemSelectedListener(new OutputOnItemSelectedListener());

    }
    
    public void compute()
    {
        dismissKeyboard();
        
        Editable input_a = inputText.getText(); 
        float input = input_a.length() > 0 ? Float.parseFloat(input_a.toString()) : 0.f;
        
        float factor0 = converterValues.get(currentInputType);
        float factor1 = converterValues.get(currentOutputType);
        float result = (input / factor0)*factor1;
        
        // Freaking fehrenheit...
        if(currentCategory == Categories.TEMPERATURE.ordinal())
        {
            // If row0 = cel && row1 = feh
            if(currentInputType == 1 && currentOutputType == 0) // Cel -> Feh
            {
                result = (float) ((9.0/5.0)*input + 32.f);
            }
            // If row0 = cel && row1 = kel
            else if(currentInputType == 1 && currentOutputType == 2) // Cel -> Kel
            {
                result = (float) (input + 273.15);
            }
            // If row0 = feh && row1 = cel
            else if(currentInputType == 0 && currentOutputType == 1) // Feh -> Cel
            {
                result = (float) ((5.0/9.0)*(input - 32.f));
            }
            // If row0 = feh && row1 = Kel
            else if(currentInputType == 0 && currentOutputType == 2) // Feh -> Kel
            {
                result = (float) ((5.0/9.0)*(input - 32.f) + 273.15);
            }
            // If row0 = kel && row1 = cel
            else if(currentInputType == 2 && currentOutputType == 1) // Kel -> Cel
            {
                result = (float) (input - 273.15);
            }
            // If row0 = kel && row1 = feh 
            else if(currentInputType == 2 && currentOutputType == 0) // Kel -> Feh
            {
                result = (float) ((9.0/5.0)*(input - 273.15) + 32.f);
            }
        }
        String result_str = "" + result + " " + converterNames.get(currentOutputType);
    
        outputText.setText(result_str);
    }
    
    public void ResetToCategory(Categories category) {
        currentInputType = 0;
        currentOutputType = 0;
        setupView(category.ordinal());
    }
  
}
