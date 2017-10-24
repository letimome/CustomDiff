package customs.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import customs.models.NewProductAsset;
import customs.models.VariationPoint;
import customs.models.VariationPointDao;

public class VPDiffUtils {
	
	
	
/*	public static String getEnhancedDiffWithVPs(ProductAsset pa, String diffvalue, VariationPointDao variationPointDao) {
		 String enhancedDiffValue="";
		 List<String> diffList = customs.utils.FileComparator.fileToLines(diffvalue);
		 Iterator<String> it = diffList.iterator();
		 String line;

		String expression="";
		Iterable<VariationPoint> vps = variationPointDao.getVariationPointsByIdproductasset(pa.getIdProductasset());
		System.out.println(vps.toString());
		int line_number=-100;
		 while (it.hasNext()) {
			
			 line_number++;
			 line = it.next();
			 System.out.println(line.toString());
			 if(line.startsWith("@@")) {
				 System.out.println("split: "+line.split(" +")[2].toString());
				 line_number = Integer.parseInt (line.split(" +")[2].split(",")[0]);
				 System.out.println("The diff starts in: "+line_number);
				 enhancedDiffValue=enhancedDiffValue.concat(line+"\n");
			 }
			 else{
				 if(line_number<=0) {
					 enhancedDiffValue=enhancedDiffValue.concat(line+"\n");
				 }else {
					 if(line.startsWith("-") || line.startsWith("+")) {
						 enhancedDiffValue=enhancedDiffValue.concat(line+"\n");
					 }
					 else {//this line is a context line - we need to add the VP it belongs to.
						 expression =VPDiffUtils.extractVPExpressionByLineNumber(line_number,vps);
						 if(line.contains("PV:IFCOND")) 
								 enhancedDiffValue=enhancedDiffValue.concat(" "+Formatting.decodeFromBase64(expression)+"\n" );//+"--> Autogenerated VP expression \n");
						 else  if((line.contains("PV:ENDCOND")))
							 	enhancedDiffValue=enhancedDiffValue.concat(line+"\n");
						 		else
						 			enhancedDiffValue=enhancedDiffValue.concat(line +" "+Formatting.decodeFromBase64(expression)+"\n");// +"--> Autogenerated VP expression \n");
					 }
				 }
				 
			 }
		 }
		 System.out.println(enhancedDiffValue);
		return enhancedDiffValue;
	}*/

	public static String getFilteredDiffForVPExpression(String diffvalue, String expression){
	    ArrayList<String> featureids = extractAllFeaturesFromTheExpression(expression);
	    System.out.println("Get Filtered diff for expression: "+expression);
	    System.out.println("featureids: "+featureids.toString());
		return getFilteredDiffForFeatures(diffvalue,featureids);
	}
	
	public static boolean isStringInArrayList(ArrayList<String> list, String value) {
		Iterator<String> it = list.iterator();
		while(it.hasNext())
			if(it.next().equals(value))
				return true;
		return false;
	}
	
	public static String getFilteredDiffForFeatures(String diffvalue, ArrayList<String> featureids) {//select any VP house contains any of the listed VPs
		 String filteredDiff="";
		 List<String> diffList = customs.utils.FileComparator.fileToLines(diffvalue);
		 Iterator<String> it = diffList.iterator();
		 String line;
		 ArrayList<String> currentContextFeatures=null;
		
		boolean selectVP=false;	 
		while (it.hasNext()) {	
			 line = it.next();
			 if (line.contains("PV:IFCOND")){//this is a context line
				 currentContextFeatures = extractAllFeaturesFromTheExpression(line.split("PV:IFCOND")[1]);
				 System.out.println("current context:"+currentContextFeatures.toString());
				 selectVP=false;	 
				 for(int i=0; i<featureids.size();i++) {
					 if(isStringInArrayList(currentContextFeatures,featureids.get(i))) {
						 System.out.println("select the context TRUE");
						 selectVP=true;
					 }
					   
				  }
		
				  if(!selectVP) {
					System.out.println("line to delete");
					;//filteredDiff=filteredDiff.concat(line).concat( " Line to be DELETED\n");
				  } else{
					  System.out.println("context line to insert: "+line);
					  filteredDiff=filteredDiff.concat( line +"\n");
				  }
			 }//end of context line
			 else {
				 if(line.startsWith("---")||line.startsWith("+++")||line.startsWith("diff")||line.startsWith("index")||line.startsWith("@@")){
						System.out.println("initial diff line match");
						filteredDiff=filteredDiff.concat(line+"\n");//diff initial lines
				  }//diff initial lines;
				 else {//this is a line that has a change: starts with"+" "-"
						System.out.println("IN ELSE");
						System.out.println(line); 
						System.out.println(currentContextFeatures);
						System.out.println(selectVP);
						if(currentContextFeatures!=null && selectVP){
							System.out.println("change to insert in else: "+line);
							filteredDiff=filteredDiff.concat(line+"\n");
						}
						else {//changed lines not for the feature at hand. Convert to context.
							if(line.startsWith("+")) 
						     	;//filteredDiff=filteredDiff.concat(line.replace("+", "")).concat(" //Context;Line to be DELETED\n");
							else if (line.startsWith("-")) 
									;// filteredDiff=filteredDiff.concat(line.split("-")[0]).concat(" //Line to be DELETED\n"); //do not show
						}
						
				}
			 }
		 }
		//System.out.println("filterd diff\n:"+filteredDiff);
		return  filteredDiff;
	}
	
	
	public static String getFilteredDiffForFeature(String diffvalue, String featureid) {
		 
		 String filteredDiff="";
		 List<String> diffList = customs.utils.FileComparator.fileToLines(diffvalue);
		 Iterator<String> it = diffList.iterator();
		 String line;
		 ArrayList<String> currentContextFeatures=null;
		
		 int line_number=-100;
		while (it.hasNext()) {
			
			 line_number++;
			 line = it.next();
			// System.out.println(line.toString());
			 if(line.startsWith("@@")) {
				 System.out.println("split: "+line.split(" +")[2].toString());
				 line_number = Integer.parseInt (line.split(" +")[2].split(",")[0]);
				 System.out.println("The diff starts in: "+line_number);
				 filteredDiff=filteredDiff.concat(line+"\n");
			 }
	
			 else {
			    	//this is a the context
					 if (line.contains("PV:IFCOND")) {
						currentContextFeatures = extractAllFeaturesFromTheExpression(line.split("PV:IFCOND")[1]);
						System.out.println("Comparing:"+featureid);
						System.out.println("VSs:"+currentContextFeatures.toString());
						if(!currentContextFeatures.contains(featureid)) {
							System.out.println("line to delete");
							;//filteredDiff=filteredDiff.concat(line).concat( " Line to be DELETED\n");
						}
						else filteredDiff=filteredDiff.concat( line +"\n");
					 }else {
						 System.out.println("IN ELSE");
						 System.out.println(line);
						 if(currentContextFeatures!=null && currentContextFeatures.contains(featureid)){
							 filteredDiff=filteredDiff.concat(line+"\n");
						 }
						 else {//changed lines not for the feature at hand. Convert to context.
							 if(line.startsWith("+")) 
									;//filteredDiff=filteredDiff.concat(line.replace("+", "")).concat(" //Context;Line to be DELETED\n");
								else if (line.startsWith("-")) 
								;//	 filteredDiff=filteredDiff.concat(line.split("-")[0]).concat(" //Line to be DELETED\n"); //do not show
									else filteredDiff=filteredDiff.concat(line+"\n");//diff initial lines
						 }
						
					 } //these are changed lines
			 	}  
		 }
		//System.out.println("filterd diff\n:"+filteredDiff);
		return  filteredDiff;
	}
	


	public static ArrayList<String> extractAllFeaturesFromTheExpression(String expression) {
		//System.out.println("expression:  "+expression);
		ArrayList<String> listfeatures = new ArrayList<String>();
		String[] pieces = expression.split("'"); //Expression example //PV:IFCOND(pv:hasFeature('Fa') and pv:hasFeature('FB'))
		for (int i=0; i< pieces.length;i++){
			if ( (i/2)*2 != i ){ //if it is odd
				listfeatures.add(pieces[i]);
			}
		}
		System.out.println("extractAllFeaturesFromTheExpression: "+expression+"\nAnd listfeatures:  "+listfeatures.toString());
	return listfeatures;
	}
	
	/*public static String extractVPExpressionByLineNumber(int line_number, Iterable<VariationPoint> vps) {
		VariationPoint vp=null;
		String expression= Formatting.encodeToBase64(" // a_mandatory_feature");
		Iterator<VariationPoint> it = vps.iterator();
		while (it.hasNext()) {
			vp=it.next();
			//System.out.println(vp.getExpression());
			System.out.println("Init line:"+vp.getLine_init());
			System.out.println("End line:"+vp.getLine_end());
			System.out.println("line to search:"+line_number);
			System.out.println(vp.toString());
			if(vp.getLine_init()<= line_number && vp.getLine_end()>=line_number)//get this expression
				expression = vp.getExpression();//Note that the nested vps have already their parents expression concated.
		}
		System.out.println(expression);
		return expression;
	}*/
}
