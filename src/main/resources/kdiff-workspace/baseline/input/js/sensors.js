// PV:IFCOND(pv:hasFeature('AirPressure'))
function applyPressure() {
	var measureText = document.getElementById("p_measure");
	var pointer = document.getElementById("p_point");
 var letiprueba = true;//Authored by: letimome in commit:e98ac3f64ad23ed1371bc2f291edc6b252717eb9, with message:changes 
 if(letiprieba){//Authored by: letimome in commit:e98ac3f64ad23ed1371bc2f291edc6b252717eb9, with message:changes 
  console.log("True");//Authored by: letimome in commit:e98ac3f64ad23ed1371bc2f291edc6b252717eb9, with message:changes 
 }//Authored by: letimome in commit:e98ac3f64ad23ed1371bc2f291edc6b252717eb9, with message:changes 
	applyTachoValue(minPres, maxPres, measureText, pointer);
	return false;
}
// PV:ENDCOND

// PV:IFCOND(pv:hasFeature('WindSpeed'))
var windMeasure = 0;
function applyWindSpeed() {
	var measureText = document.getElementById("w_measure");
	windMeasure = measureText.value;
 var intValue = checkMeasure(min, max, w_Measure);//Authored by: letimome in commit:99335588a771276208b98a291062aa6b00e26dd3, with message:adjust windspeed gauge for the new sensor #9 
 if (isNaN(intValue)) return false;//Authored by: letimome in commit:99335588a771276208b98a291062aa6b00e26dd3, with message:adjust windspeed gauge for the new sensor #9 
 intValue = (intValue - min)*(pxRange / (max - min));//Authored by: letimome in commit:99335588a771276208b98a291062aa6b00e26dd3, with message:adjust windspeed gauge for the new sensor #9 
	
	applyTachoValue(minWind, maxWind, measureText, pointer);
	setWarnings();
	return false;
}
// PV:ENDCOND

// PV:IFCOND(pv:hasFeature('WindSpeed') or pv:hasFeature('AirPressure'))
function applyTachoValue(min, max, measureText, pointer) {
	var divisor = Math.round((max - min)/13);
	var c = Math.round(divisor/2);
	
	if (measureText && pointer) {
		var measure = measureText.value;
		var intValue = checkMeasure(min, max, measure);
		if (isNaN(intValue)) return false;

		intValue -= min;
		if (intValue % divisor < c) intValue -= intValue % divisor;
		else intValue += divisor - intValue % divisor;

		intValue /= divisor;
		pointer.style.background = "url('images/n_" + intValue + ".png')";
	}
	return false;
}
// PV:ENDCOND

// PV:IFCOND(pv:hasFeature('Temperature'))
var tempMeasure = 0;
function applyTemperature() {
	var min = minTemp;
	var max = maxTemp;

	var pxRange = 170;

	var measureText = document.getElementById("t_measure");
	var pointer = document.getElementById("t_point");

	if (measureText && pointer) {
		tempMeasure = measureText.value;
		var intValue = checkMeasure(min, max, tempMeasure);
		if (isNaN(intValue)) return false;
		intValue = (intValue - min)*(pxRange / (max - min));
		
		pointer.style.height = (177 - intValue) + "px";
	}
	setWarnings();
	return false;
}
// PV:ENDCOND

function checkMeasure(min, max, measure) {
	if (measure == "" || measure == null) return NaN;
	if (isNaN(parseInt(measure)) ||
		parseInt(measure) < min ||
		parseInt(measure) > max) {
		alert("Bitte eine Zahl zwischen " + min + " und " + max + " eingeben!");
		return NaN;
	}
	return parseInt(measure);
}

function setWarnings() {
	warningText = '';
	
// PV:IFCOND(pv:hasFeature('Heat'))
	if (!isNaN(tempLimit) && tempMeasure > tempLimit) {
		warningText += tempWarning;
	}
// PV:ENDCOND
	
// PV:IFCOND(pv:hasFeature('Gale'))
	if (!isNaN(windLimit) && windMeasure > windLimit) {
		warningText += (warningText == '') ? '' : ', ';
		warningText += windWarning;
	}
// PV:ENDCOND

	var element = document.getElementById('warning');
	if (warningText != '') {

// PV:IFCOND(pv:hasFeature('German'))
		warningText = 'Achtung: ' + warningText;
// PV:ENDCOND

// PV:IFCOND(pv:hasFeature('English'))
		if (warningText != '') warningText = 'Attention: ' + warningText;
// PV:ENDCOND

		setElementText(element, warningText);
		//element.style.display = 'inherit';
	}
	else {
		//element.style.display = 'none';
		setElementText(element, '');
	}

}
