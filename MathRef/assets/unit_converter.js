var currentToType 	= 0;
var currentFromType	= 0;
var currentCategory	= 0;
var obj = $.parseJSON('{
	"Units":[
		{
			"Data":
			[
				{"shortName":"bit","longName":"Bit","value":8192.0},
				{"shortName":"B","longName":"Byte","value":1024.0},
				{"shortName":"KB","longName":"Kilobyte","value":1.0},
				{"shortName":"MB","longName":"Megabyte","value":0.0009765625},
				{"shortName":"GB","longName":"Gigabyte","value":9.53674316e-07},
				{"shortName":"TB","longName":"Terabyte","value":9.31322575e-10},
				{"shortName":"PB","longName":"Petabyte","value":9.31322575e-13},
				{"shortName":"EB","longName":"Exabyte","value":8.881784200000001e-16}
			]
		},
		{
			"Data":
			[
				{"shortName":"\u00B0","longName":"Degrees","value":1.0},			
				{"shortName":"Rad","longName":"Radians","value":0.01745329},			
				{"shortName":"g","longName":"Gradians","value":1.1111111}
			]
		},
		{
			"Data":
			[
				{"shortName":"A","longName":"Angstroms","value":10000000000.0},			
				{"shortName":"\u03BCm","longName":"Microns","value":1000000.0},			
				{"shortName":"mm","longName":"Millimeters","value":1000.0},			
				{"shortName":"cm","longName":"Centimeters","value":100.0},			
				{"shortName":"m","longName":"Meters","value":1.0},			
				{"shortName":"km","longName":"Kilometers","value":0.001},			
				{"shortName":"in","longName":"Inches","value":39.37008},			
				{"shortName":"ft","longName":"Feet","value":3.28084},			
				{"shortName":"yd","longName":"Yard","value":1.093613},			
				{"shortName":"mi","longName":"Miles","value":0.0006213712},			
				{"shortName":"nmi","longName":"Nautical Miles","value":0.0005399568},			
				{"shortName":"AU","longName":"Astronomical Units","value":6.684587e-12},			
				{"shortName":"ly","longName":"Light Years","value":1.057001e-16},			
				{"shortName":"pc","longName":"Parsecs","value":3.241491e-17}
			]
		},
		{
			"Data":
			[
				{"shortName":"in\u00B2","longName":"Square Inches","value":1.0},			
				{"shortName":"ft\u00B2","longName":"Square Feet","value":0.006944444},			
				{"shortName":"yd\u00B2","longName":"Square Yard","value":0.0007716053},			
				{"shortName":"mi\u00B2","longName":"Square Miles","value":2.490977e-10},			
				{"shortName":"mm\u00B2","longName":"Square Millimeters","value":645.16},			
				{"shortName":"cm\u00B2","longName":"Square Centimeters","value":6.4516},			
				{"shortName":"m\u00B2","longName":"Square Meters","value":0.00064516},			
				{"shortName":"km\u00B2","longName":"Square Kilometers","value":6.4516e-10},			
				{"shortName":"ac","longName":"Acres","value":1.594225e-07},			
				{"shortName":"ha","longName":"Hectares","value":6.451599999999999e-08}
			]
		},
		{
			"Data":
			[
				{"shortName":"gal","longName":"Gallon (US)","value":1.0},			
				{"shortName":"gal","longName":"Gallon (Imperial)","value":0.8326742},			
				{"shortName":"L","longName":"Liter","value":3.785412},			
				{"shortName":"tbsp","longName":"Tablespoon (US)","value":256.0},			
				{"shortName":"tsp","longName":"Teaspoon (US)","value":768.0},			
				{"shortName":"fl oz","longName":"Fluid Ounces","value":128.0},			
				{"shortName":"cu in","longName":"Cubic Inches","value":0.1336806},			
				{"shortName":"cu ft","longName":"Cubic Feet","value":231.0},			
				{"shortName":"cu cm","longName":"Cubic Centimeters","value":3785.412},			
				{"shortName":"cu m","longName":"Cubic Meters","value":0.003785412},			
				{"shortName":"dram","longName":"Drams (US)","value":1024.0},			
				{"shortName":"pints","longName":"Pints","value":8.0},			
				{"shortName":"cup","longName":"Cups","value":16.0},			
				{"shortName":"qt","longName":"Quarts (US)","value":4.0}
			]
		},
		{
			"Data":
			[
				{"shortName":"oz","longName":"Ounces (US)","value":1.0},			
				{"shortName":"lb","longName":"Pounds (US)","value":0.0625},			
				{"shortName":"S/T","longName":"Short Tons (US)","value":3.125e-05},			
				{"shortName":"L/T","longName":"Long Tons (UK)","value":2.790179e-05},			
				{"shortName":"mg","longName":"Milligrams","value":28349.52},			
				{"shortName":"g","longName":"Grams","value":28.34952},			
				{"shortName":"kg","longName":"Kilograms","value":0.02834952},			
				{"shortName":"st","longName":"Stones","value":0.004464286}
			]
		},
		{
			"Data":
			[
				{"shortName":"N","longName":"Newtons","value":1.0},			
				{"shortName":"dyn","longName":"Dynes","value":100000.0},			
				{"shortName":"kg","longName":"Kilograms","value":0.1019716},			
				{"shortName":"lb","longName":"Pounds","value":0.224809},			
				{"shortName":"pdl","longName":"Poundals","value":7.233011}
			]
		},
		{
			"Data":
			[
				{"shortName":"ft/s","longName":"Feet/Second","value":1.0},			
				{"shortName":"ft/min","longName":"Feet/Minute","value":60.0},			
				{"shortName":"mipmin","longName":"Miles/Minute","value":0.01136364},			
				{"shortName":"mph","longName":"Miles/Hour","value":0.6818182},			
				{"shortName":"m/s","longName":"Meters/Second","value":0.3048},			
				{"shortName":"km/min","longName":"Kilometers/Minute","value":0.018288},			
				{"shortName":"km/h","longName":"Kilometers/Hour","value":1.09728},			
				{"shortName":"kn","longName":"Knots","value":0.5924838}
			]
		},
		{
			"Data":
			[
				{"shortName":"BTU","longName":"British Thermal Units","value":1.0},			
				{"shortName":"ft lb","longName":"Foot Pounds","value":778.1693},			
				{"shortName":"erg","longName":"Ergs","value":10550560000.0},			
				{"shortName":"kW-h","longName":"Kilowatt Hours","value":0.0002930711},			
				{"shortName":"W-h","longName":"Watt Hours","value":0.2930711},			
				{"shortName":"kg-cal","longName":"Kilo-Calories","value":0.2519958},			
				{"shortName":"cal","longName":"Calories","value":251.9958},			
				{"shortName":"kg-m","longName":"Kilogram Meters","value":107.5858},			
				{"shortName":"n-m","longName":"Newton Meters","value":1055.056},			
				{"shortName":"J","longName":"Joules Meters","value":1055.056}
			]
		},
		{
			"Data":
			[
				{"shortName":"BTU/min","longName":"BTU/Minute","value":1.0},			
				{"shortName":"ft-lb/s","longName":"Foot Pounds/Second","value":12.969490},			
				{"shortName":"ft-lb/m","longName":"Foot Pounds/Minute","value":778.16920},			
				{"shortName":"hp","longName":"Hoursepower","value":0.023580890},			
				{"shortName":"w","longName":"Watts","value":17.584260},			
				{"shortName":"kw","longName":"Kilowatts","value":0.01758426}
			]
		},
		{
			"Data":
			[
				{"shortName":"psi","longName":"Pounds/Square Inch","value":1.0},			
				{"shortName":"pft","longName":"Pounds/Square Foot","value":144.0},			
				{"shortName":"at","longName":"Atmospheres","value":0.068045960},			
				{"shortName":"bars","longName":"Bars","value":0.068947570},			
				{"shortName":"inHg","longName":"Inches of Mercury","value":2.036021},			
				{"shortName":"cmHg","longName":"Centimeters of Mercury","value":5.171493},			
				{"shortName":"kg/m\u00B2","longName":"Kilograms/Square Meter","value":703.0696},			
				{"shortName":"pa","longName":"Pascals","value":6894.757}
			]
		},
		{
			"Data":
			[
				{"shortName":"\u00B0f","longName":"Fahrenheit","value":-999.0},			
				{"shortName":"\u00B0C","longName":"Celsius","value":-999.0},			
				{"shortName":"K","longName":"Kelvin","value":-272.15}
			]
		},
		{
			"Data":
			[
				{"shortName":"ms","longName":"Millisecond","value":1.0},			
				{"shortName":"s","longName":"Second","value":0.001},			
				{"shortName":"min","longName":"Minute","value":1.666667e-05},			
				{"shortName":"h","longName":"Hour","value":2.777778e-07},			
				{"shortName":"d","longName":"Day","value":1.157407e-08},			
				{"shortName":"wk","longName":"Week","value":1.653439e-09},			
				{"shortName":"y","longName":"Year","value":3.170979e-11}
			]
		}
	]
}');
var Units = obj.Units;

$("#swapButton").click(function() {
	var to = $("#toUnit").val();
	var from = $("#fromUnit").val();
	$("#toUnit").val(from);
	$("#fromUnit").val(to);
	currentToType = $("#toUnit")[0].selectedIndex;
	currentFromType = $("#fromUnit")[0].selectedIndex;
	compute();
});

$("#solveButton").click(function() {
	compute();
});

$("#toUnit").change(function() {
	currentToType = $("#toUnit")[0].selectedIndex;
	compute();
});
$("#fromUnit").change(function() {
	currentFromType = $("#fromUnit")[0].selectedIndex;
	compute();
});
$(".tool-button").click(function() {
	currentCategory = $(this).index();
	setupView(currentCategory);
	compute();
});

function setupView(type)
{
	currentToType = 0;
	currentFromType = 0;
	currentCategory = type;

	$("#fromUnit").empty();
	$("#toUnit").empty();

	var data = Units[type].Data;

	var option = '';
	for (var i = 0 ; i < data.length; i++) 
	{
   		option += '<option>' + data[i].longName + '</option>';
	}
	$('#fromUnit').append(option);
	$('#toUnit').append(option);
}

function compute()
{

	var input_a = $("#inputA").val();
	var input = input_a.length > 0 ? parseFloat(input_a) : 0.0;
	var factor0 = Units[currentCategory].Data[currentFromType].value;
	var factor1 = Units[currentCategory].Data[currentToType].value;
	var result = (input / factor0)*factor1;

	var result_str = "";
	
	if(currentCategory == 11)
	{
		// If row0 = cel && row1 = feh
		if(currentFromType == 1 && currentToType == 0) // Cel -> Feh
		{
			result = ((9.0/5.0)*input + 32.0);
		}
		// If row0 = cel && row1 = kel
		else if(currentFromType == 1 && currentToType == 2) // Cel -> Kel
		{
			result = (input + 273.15);
		}
		// If row0 = feh && row1 = cel
		else if(currentFromType == 0 && currentToType == 1) // Feh -> Cel
		{
			result = ((5.0/9.0)*(input - 32.0));
		}
		// If row0 = feh && row1 = Kel
		else if(currentFromType == 0 && currentToType == 2) // Feh -> Kel
		{
			result = ((5.0/9.0)*(input - 32.0) + 273.15);
		}
		// If row0 = kel && row1 = cel
		else if(currentFromType == 2 && currentToType == 1) // Kel -> Cel
		{
			result = (input - 273.15);
		}
		// If row0 = kel && row1 = feh 
		else if(currentFromType == 2 && currentToType == 0) // Kel -> Feh
		{
			result = ((9.0/5.0)*(input - 273.15) + 32.0);
		}
	}
	result_str += "" + result + " " + Units[currentCategory].Data[currentToType].shortName;
	$("#inputB").val(result_str);
}