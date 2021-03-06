package customs.utils;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import customs.models.Feature;
import customs.models.FeatureDao;
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
			+"\", \"expanded\": \"true\", \"hasChildren\": \"true\" }";
			if(it.hasNext()) json+=",";
		}
		json+= "]";
		return json;
		}	

	
	public static String getJsonForParentChildrenFeature(Iterable<ParentFeature> findAll) {
		String json = "[ ";
		Iterator<ParentFeature> it = findAll.iterator();
		ParentFeature parent;
		int i=0;
		while (it.hasNext()){
			parent = it.next();
			json+="{ \"id\":\""+parent.getName()+ "\", \"FullName\":\""+parent.getName()
				+"\", \"expanded\": \"true\", \"hasChildren\": \"true\", "
				+ " \"items\": [{ \"id\" :\"Feature"+i+"\", \"FullName\": \"Feature"+i+"\", \"expanded\": \"true\", \"hasChildren\": \"false\"}] }";
			if(it.hasNext()) json+=",";
			i++;
		}
		json+= "]";
		System.out.println(" IN getJsonForParentChildrenFeature");
		System.out.println(json);
		return json; 
	}
	
	public static String getJsonForParentAndChildFeature(Iterable<ParentFeature> findAll, FeatureDao fDao) {
		String json =  "[ ";//{ \"id\": \"WeatherStation\", \"FullName\": \"Features\", \"expanded\": \"true\",\"hasChildren\":\"true\", \"items\": [";
		Iterator<ParentFeature> it = findAll.iterator();
		ParentFeature parent;
		int i=0;
		Feature f;
		Iterator<Feature> features ;
		while (it.hasNext()){
			parent = it.next();
			if(!parent.getName().equals("No Feature")){
				json+="{ \"id\":\""+parent.getName()+ "\", \"FullName\":\""+parent.getName()+"\", \"expanded\": true, \"hasChildren\": true, \"items\": [";
				features = fDao.getFeaturesByIdparent(parent.getIdparentFeature()).iterator();
				while(features.hasNext()) {
					f = features.next();
					json+=" { \"id\" :\""+f.getIdfeature()+"\", \"FullName\": \""+f.getIdfeature()+"\", \"expanded\": true, \"hasChildren\": false}";
					if(features.hasNext()) json+=",";
				}	
				json+="]}";
				if(it.hasNext()) json+=",";
			}
			
		}
		json+= "]\n";
	//	json+= "}]";
		System.out.println(" IN getJsonForParentChildrenFeature");
		System.out.println(json);
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
	{id:"Languages", FullName:"Languages", expanded: false, hasChildren:true, items:[{id:"English",FullName:"English", expanded:true}]}
	]}]
 * 
 ***/


