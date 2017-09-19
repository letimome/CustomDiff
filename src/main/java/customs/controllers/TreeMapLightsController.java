package customs.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.CoreAsset;
import customs.models.CoreAssetDao;
import customs.models.CustomizationsByFeature;
import customs.models.CustomizationsByFeatureDao;
import customs.models.CustomizationsByVPandPR;
import customs.models.CustomizationsByVPandPRDao;
import customs.models.SPLdao;
import customs.models.VariationPoint;
import customs.models.VariationPointDao;
import customs.utils.Formatting;
import customs.models.CustomizationsGByFile;
import customs.models.CustomizationsGByFileDao;
import customs.models.ProductAsset;
import customs.models.ProductAssetDao;
import customs.models.ProductRelease;
import customs.models.ProductReleaseDao;

@Controller
public class TreeMapLightsController {
	  @Autowired
	   private CustomizationsGByFileDao treemapLightDao;
	  @Autowired private CustomizationsByFeatureDao featureCustomsDao;
	  @Autowired private SPLdao SPLdao;
	  @Autowired private ProductAssetDao paDao;
	  @Autowired private VariationPointDao variationPointDao;
	  @Autowired private CustomizationsByVPandPRDao prsAndVps;
	  @Autowired private CoreAssetDao caDao;
	@Autowired private ProductAssetDao productAssetDao;
		 
	   private String pathToResource = "./src/main/resources/static/";
	
	
	  @RequestMapping("treemapLightsView")
	   public String getTreeMapTrafficLight(
	   			//	@RequestParam(value="base", required=false) String idbaseline,
	   				@RequestParam(value="fname", required=false) String featurenamemodified,
	   				@RequestParam(value="idfile", required=false) int idfile,
	   				@RequestParam(value="pr", required=false) String pr,
	   				Model model){
		  
		   System.out.println("THIS IS featurenamemodified: "+featurenamemodified);
		   System.out.println("THIS IS idfile: "+idfile);
		   
		   String csvContent = extractCSVForTreeMapLightsByProductRelease(idfile, featurenamemodified);
		   customs.utils.FileUtils.writeToFile(pathToResource+"treemapLights.csv",csvContent);//path and test
		   
		   if(idfile!=0 && pr!=null)
		     addDiffViewForCoreAssetId(model,idfile,pr, featurenamemodified);
		   
		  return "treemapLights2"; 
	 	}


	private void addDiffViewForCoreAssetId(Model model, int idcoreasset,String pr, String featureid) {
		if(pr==null) return;
		//I need to get the absolute diff  that modifies the idcoreasset in release pr
		 System.out.println("diff for idcoreasset: "+idcoreasset); System.out.println("diff for pr: "+pr);
		 ProductAsset pa=null;
		 
		 CoreAsset ca = caDao.getCoreAssetByIdcoreasset(idcoreasset);
		 Iterator <ProductAsset> ite = paDao.findAll().iterator();
		 while(ite.hasNext()) {
			 pa=ite.next();
			 if(pa.getProductrelease_idrelease().equals(pr) && ca.getPath().equals(pa.getPath()))
				 break;
		 }
		 System.out.println("pa: "+pa);
		 System.out.println("pa: "+pa.getPath());
		 String diffvalue =  customs.utils.Formatting.decodeFromBase64(pa.getRelative_diff());
		 //process here the content of the relative diff
		 System.out.println(diffvalue);
		 
		
		 String enhancedDiffValue = getEnhancedDiffWithVPs(pa, diffvalue);
		 String filteredDiff= getFilteredDiffForFeature(enhancedDiffValue,featureid);
		 model.addAttribute("diffvalue",filteredDiff); 
		 model.addAttribute("pr",pr);
		 model.addAttribute("fname",featureid);
		 model.addAttribute("cavalue",ca.getName());
		 String header= "Differences between product '"+pr+"' and core-asset '"+ca.getName()+"' as for feature '"+featureid+"'";
		 model.addAttribute("diffHeader", header);
		 
		 model.addAttribute("maintitle", "How is feature '"+featureid+"' being customized in products?");
		 model.addAttribute("difftitle", "diff(feature '" +featureid+"', product-portfolio)");
		
	}




	private String getEnhancedDiffWithVPs(ProductAsset pa, String diffvalue) {
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
						 expression =extractVPExpressionByLineNumber(line_number,vps);
						 if(line.contains("PV:IFCOND")) 
								 enhancedDiffValue=enhancedDiffValue.concat(" "+Formatting.decodeFromBase64(expression) +"--> Autogenerated VP expression \n");
						 else  if((line.contains("PV:ENDCOND")))
							 	enhancedDiffValue=enhancedDiffValue.concat(line+"\n");
						 		else
						 			enhancedDiffValue=enhancedDiffValue.concat(line +" "+Formatting.decodeFromBase64(expression) +"--> Autogenerated VP expression \n");
					 }
				 }
				 
			 }
		 }
		 System.out.println(enhancedDiffValue);
		return enhancedDiffValue;
	}

	private String getFilteredDiffForFeature(String diffvalue, String featureid) {
		 
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

	private static ArrayList<String> extractAllFeaturesFromTheExpression(String expression) {
		//System.out.println("expression:  "+expression);
		ArrayList<String> listfeatures = new ArrayList<String>();
		String[] pieces = expression.split("'"); //Expression example //PV:IFCOND(pv:hasFeature('Fa') and pv:hasFeature('FB'))
		for (int i=0; i< pieces.length;i++){
			if ( (i/2)*2 != i ){ //if it is odd
				listfeatures.add(pieces[i]);
			}
		}
		System.out.println("Expression: "+expression+"\nAnd listfeatures:  "+listfeatures.toString());
	return listfeatures;
	}

	//this is for the "diagram part" of the view
	private String extractCSVForTreeMapLightsByProductRelease(int idcoreasset, String featureid) {
		//idcoreasset   
		 System.out.println("Id file ="+ idcoreasset);
		 
		 CustomizationsGByFile treemap = treemapLightDao.getCustomsByIdcoreasset(idcoreasset);
		
		 
		 Iterable<CustomizationsGByFile> customsByFile = treemapLightDao.findAll();
		 Iterator<CustomizationsGByFile> it = customsByFile.iterator();
		 while(it.hasNext()) {
			 treemap = it.next();
			 if(treemap.getIdcoreasset()==idcoreasset) {
				 System.out.println("treemap found");
				 System.out.println(treemap);
				 break;	
			 }
						 
		 }

		   Iterable <CustomizationsByFeature> featureCustoms = featureCustomsDao.getCustomsByFeatureid(featureid); //.findAll();
		   Iterator<CustomizationsByFeature> ite=featureCustoms.iterator();
		   System.out.println("featureCustoms:" +featureCustoms);
		   String csvContent="id,value,frequency,id_core_asset,fname,product_release"; 
		   ArrayList<String> paths = new ArrayList<>();
	
		   String csvCustoms= "";
		   CustomizationsByFeature featureCusto;
		   
		   while (ite.hasNext()) {
			   featureCusto= ite.next();
			  if(featureCusto.getFeatureId().equals(featureid) ) //NO HACE FALTA! ||  custo.getFeaturemodified().equals("undefined")) 
			  {
			     csvCustoms = csvCustoms.concat(
			    		 "\n"+featureCusto.getCapath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","").concat("/"+featureCusto.getPr())
			    		 +","+featureCusto.getAmount()
			    		 +","+treemap.getNumberofproductscustomizing()
			    		 +"," +featureCusto.getCoreassetid()
			    		 +","+featureid
			    		 +","+featureCusto.getPr()
			    		 );
			     paths.add(featureCusto.getCapath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", ""));
			  	// System.out.println(custo.getPath()+","+custo.getFeaturemodified()+","+custo.getChurn());
			  	 }
		   }
		   ArrayList<String>  paths2 = customs.utils.Formatting.extractPathsFromPathList(paths);
		   System.out.println("MiniPaths:"+paths2+"\n\n");
		   for (int i=0; i< paths2.size();i++) {
			   if(!paths2.get(i).equals(""))
			     csvContent=csvContent.concat("\n"+paths2.get(i)+",");
		   }
		   csvContent=csvContent.concat(csvCustoms);
		   System.out.println(paths2+"\n\n");
		return csvContent;
	}
	
	
	//no longer used
	private String extractCSVForTreeMapLights(String idbaseline, String featurenamemodified) {
		Iterable<CustomizationsGByFile> customsObj = treemapLightDao.getCustomsByIdbaseline(idbaseline);
		   Iterator<CustomizationsGByFile> it = customsObj.iterator();
		   
		   String csvContent="id,value,frequency,id_core_asset,fname";//id,value,Freq.\ninput,
		   ArrayList<String> paths = new ArrayList<>();
		   CustomizationsGByFile custo;
		   String csvCustoms= "";
		   while (it.hasNext()) {
			  custo= it.next();
			 // if(custo.getFeaturemodified().equals(featurenamemodified) )
					  //||  custo.getFeaturemodified().equals("undefined")) 
			  {
			     csvCustoms = csvCustoms.concat("\n"+custo.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","")+","
			  +custo.getChurn()+","
			    		 +custo.getNumberofproductscustomizing()+","
			    		 +custo.getIdcoreasset()+","+featurenamemodified
			    		 );
			     paths.add(custo.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", ""));
			  	// System.out.println(custo.getPath()+","+custo.getFeaturemodified()+","+custo.getChurn());
			  	 }
		   }
		   ArrayList<String>  paths2 = customs.utils.Formatting.extractMiniPaths(paths);
		   for (int i=0; i< paths2.size();i++) {
			   if(!paths2.get(i).equals(""))
			     csvContent=csvContent.concat("\n"+paths2.get(i)+",");
		   }
		   csvContent=csvContent.concat(csvCustoms);
		   System.out.println(paths2+"\n\n");
		return csvContent;
	}
	
	private String extractVPExpressionByLineNumber(int line_number, Iterable<VariationPoint> vps) {
		VariationPoint vp=null;
		String expression= Formatting.encodeToBase64(" // a_mandatory_feature");
		Iterator<VariationPoint> it = vps.iterator();
		while (it.hasNext()) {
			vp=it.next();
			/*System.out.println(vp.getExpression());
			System.out.println("Init line:"+vp.getLine_init());
			System.out.println("End line:"+vp.getLine_end());
			System.out.println("line to search:"+line_number);
			System.out.println(vp.toString());*/
			if(vp.getLine_init()<= line_number && vp.getLine_end()>=line_number)//get this expression
				expression = vp.getExpression();//Note that the nested vps have already their parents expression concated.
		}
		System.out.println(expression);
		return expression;
	}
}
