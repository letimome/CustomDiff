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
import customs.utils.VPDiffUtils;
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
	  @Autowired private CoreAssetDao caDao;

		 
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
		   
		   String csvContent = extractCSVForTreeMapLightsByFeature(idfile, featurenamemodified);
		   customs.utils.FileUtils.writeToFile(pathToResource+"treemapLights.csv",csvContent);//path and test
		   
		   if( pr!=null)
		     addDiffViewForCoreAssetId(model,idfile,pr, featurenamemodified);
		   
		   model.addAttribute("pr",pr);
		   model.addAttribute("fname",featurenamemodified);
			 
		   model.addAttribute("maintitle", "How is feature '"+featurenamemodified+"' being customized in products?");
		   model.addAttribute("difftitle", "diff(Feature: '" +featurenamemodified+"', Product-Portfolio)");
		   
		  return "treemapLights2"; 
	 	}


	private void addDiffViewForCoreAssetId(Model model, int idcoreasset,String pr, String featureid) {
		
		if(pr==null) return;
		//I need to get the absolute diff  that modifies the idcoreasset in release pr
		 System.out.println("diff for idcoreasset: "+idcoreasset); System.out.println("diff for pr: "+pr);
		 ProductAsset pa=null;
		 CoreAsset ca=null;
		 
		 if(idcoreasset==0) {//then select the first one
			 Iterator<CustomizationsByFeature>  it= featureCustomsDao.getCustomsByFeatureid(featureid).iterator();
			 CustomizationsByFeature aux;
			 while(it.hasNext()) {
				 aux= it.next();
				 if(aux.getPr().equals(pr)) {
					 ca =  caDao.getCoreAssetByIdcoreasset(aux.getCoreassetid());
					 break;
				 }
			 }
		 }
		 else ca = caDao.getCoreAssetByIdcoreasset(idcoreasset);
		 
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
		 
		
		 String enhancedDiffValue = VPDiffUtils.getEnhancedDiffWithVPs(pa, diffvalue, variationPointDao);
		/*/ ArrayList<String> featureList= new ArrayList<>();
		 featureList.add(featureid);*/
		 String filteredDiff= VPDiffUtils.getFilteredDiffForFeature(enhancedDiffValue,featureid);
		
		 model.addAttribute("diffvalue",filteredDiff); 
		 model.addAttribute("pr",pr);
		 model.addAttribute("fname",featureid);
		 model.addAttribute("cavalue",ca.getIdcoreasset());
// model.addAttribute("diffHeader", "diff (core-asset:'"+pa.getName()+"', product-asset:'"+pa.getName()+"' [file.getVPExpression('"+expression+")]");
		 String header= "diff( Baseline-v1.0.getAsset('"+ca.getName()+"'),  "+pr+".getAsset('"+ca.getName()+"') [VP.expression.contains('"+featureid+"')]";
		 		
		 model.addAttribute("diffHeader", header);
		 model.addAttribute("maintitle", "How is feature '"+featureid+"' being customized in products?");
		 model.addAttribute("difftitle", "diff(Feature: '" +featureid+"', Product-Portfolio)");
	}

	//this is for the "diagram part" of the view
	private String extractCSVForTreeMapLightsByFeature(int idcoreasset, String featureid) {
		//idcoreasset   
		 System.out.println("Id file ="+ idcoreasset);
		 //get all core-assets by featureid
		 
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
	

}
