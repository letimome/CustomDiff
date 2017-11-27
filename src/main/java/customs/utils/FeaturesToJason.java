package customs.utils;

import java.util.Iterator;

import customs.models.Feature;
import customs.models.ParentFeature;

public class FeaturesToJason {

	public static String getJsonForParentFeatures(Iterable<ParentFeature> findAll) {
		String json = "[ ";
		Iterator<ParentFeature> it = findAll.iterator();
		ParentFeature parent;
		while (it.hasNext()){
			parent = it.next();
			json+="{ \"id\":\""+parent.getName()+ "\", \"FullName\":\""+parent.getName()
						+"\", \"expanded\": \"true\", \"hasChildren\": \"false\"  }";
			if(it.hasNext()) json+=",";
		}
		json+= "]";
		return json;
	}	

	
	public static String getJsonForFeatures(Iterable<Feature> findAll) {
		String json = "[ ";
		Iterator<Feature> it = findAll.iterator();
		Feature parent;
		while (it.hasNext()){
			parent = it.next();
			json+="{ \"id\":\""+parent.getName()+ "\", \"FullName\":\""+parent.getName()
						+"\", \"expanded\": \"true\", \"hasChildren\": \"false\"  }";
			if(it.hasNext()) json+=",";
		}
		json+= "]";
		return json;
		}	
	}




/****
 * 
 * [{id: "WeatherStation", FullName: "WeatherStation", expanded: true,hasChildren:true, spriteCssClass: "rootfolder", items: [
   { id: "Sensors", FullName: "Sensors", expanded: false, hasChildren:false},
    {id: "Languages", FullName: "Languages", expanded: false, hasChildren:false},
    {id: "Warnings", FullName: "Warnings", expanded: false,hasChildren:false}
	]}];
 * 
 * 
 * 
 * [{id: "All", FullName: "All", expanded: true,hasChildren:true, spriteCssClass: "rootfolder", items: [
	{id:"No Feature", FullName:"No Feature", expanded: false, hasChildren:false},
	{id:"Sensors", FullName:"Sensors", expanded: false, hasChildren:false},
	{id:"Warnings", FullName:"Warnings", expanded: false, hasChildren:false},
	{id:"Languages", FullName:"Languages", expanded: false, hasChildren:false}
	]}]
 * 
 * **/


