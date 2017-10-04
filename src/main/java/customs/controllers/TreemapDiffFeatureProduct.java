 package customs.controllers;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.AddedCustomsByProductsToFeatures;
import customs.models.AddedCustomsByProductsToFeaturesDao;
import customs.models.CoreAsset;
import customs.models.CoreAssetDao;
import customs.models.CoreassetsAndFeatures;
import customs.models.CoreassetsAndFeaturesDao;
import customs.models.CustomizationsByFeature;
import customs.models.CustomizationsByFeatureDao;
import customs.models.CustomizationsGByOperation;
import customs.models.CustomizationsGByOperationDao;
import customs.models.DeletedCustomsByProductsToFeatures;
import customs.models.DeletedCustomsByProductsToFeaturesDao;
import customs.models.ProductAsset;
import customs.models.ProductAssetDao;
import customs.models.SPLdao;
import customs.models.VariationPointDao;
import customs.utils.VPDiffUtils;


@Controller
public class TreemapDiffFeatureProduct {
	
	  @Autowired private CustomizationsGByOperationDao customsByOperationTypeDaa;
	  @Autowired private AddedCustomsByProductsToFeaturesDao addedTofeaturesDao;
	  @Autowired private DeletedCustomsByProductsToFeaturesDao deletedTofeaturesDao;
	  @Autowired private CustomizationsByFeatureDao featureCustomsDao;
	  @Autowired private CoreassetsAndFeaturesDao coreassetsForFeature;
	  @Autowired private VariationPointDao variationPointDao;
	  @Autowired private SPLdao SPLdao;
	  @Autowired private ProductAssetDao paDao;
	  @Autowired private CoreAssetDao caDao;

		 
	   private String pathToResource = "./src/main/resources/static/";
	
	
	  @RequestMapping("treemapfeatureproductview")
	   public String getTreeMapTrafficLight(
	   				@RequestParam(value="base", required=false) String idbaseline,
	  				@RequestParam(value="fname", required=false) String featurenamemodified,
	   				@RequestParam(value="pr", required=false) String pr,
	   				@RequestParam(value="idfile", required=false) int idfile,
	   				Model model){
		  System.out.println("THIS IS base: "+idbaseline);
		   System.out.println("THIS IS fname: "+featurenamemodified);
		   System.out.println("THIS IS pr: "+pr);
		   System.out.println("THIS IS idfile: "+idfile);
		   
		   String csvContent = extractCSVForTreeMapFeatureProduct( featurenamemodified, pr, idbaseline);
		   customs.utils.FileUtils.writeToFile(pathToResource+"treemapfeatureproduct.csv",csvContent);//path and test
		   
		   if( pr!=null && idfile!=0)
		     addDiffViewForCoreAssetId(model,idfile,pr, featurenamemodified);
		   model.addAttribute("pr",pr);
		   model.addAttribute("fname",featurenamemodified);
		   model.addAttribute("maintitle", "How is feature '"+featurenamemodified+"' being customized in product "+pr+"?");
		   model.addAttribute("difftitle", "diff(Feature: '" +featurenamemodified+"', "+pr+")");
		  
		   if(idfile!=0)
		      customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureProduct(featurenamemodified,pr,caDao.getCoreAssetByIdcoreasset(idfile).getName(),"hasFeature("+featurenamemodified+")");
		   else customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureProduct(featurenamemodified,pr,"core-asset","Expression");
		   return "treemapFeatureProduct"; 
	 	}



	private String extractCSVForTreeMapFeatureProduct(String featurenamemodified, String pr,String idbaseline) {
		String csvheader = "id,value,frequency,id_core_asset,fname,product_release,operation";
		String csvContentLines = extractCSVForFeatureProduct(featurenamemodified,idbaseline,pr);
	
		return csvheader.concat(csvContentLines);
	}

	private String extractCSVForFeatureProduct(String featurenamemodified, String idbaseline, String pr) {
	
		Iterable<CoreassetsAndFeatures> all = coreassetsForFeature.findAll();
		System.out.println(all.toString());
		CoreassetsAndFeatures caf;
		Iterator<CoreassetsAndFeatures> it = all.iterator();
		ArrayList<String> capaths = new ArrayList<String>();
		String csvCAcontent="";
		while (it.hasNext()) { 
			caf = it.next();
			if (caf.getBaseline().equals(idbaseline) && caf.getFeatureid().equals(featurenamemodified)) {
				capaths.add(caf.getCapath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", ""));
				//include the whole line for the core-assets + its size
				csvCAcontent = csvCAcontent.concat("\n"+caf.getCapath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","")
						+",1,"//(caf.getSize()
								);//TODO cambiarlo
				//include the lines for the product that has customized the assets
			//	csvCAcontent = csvCAcontent.concat(extractCSVForProductCustomizingCoreAsset(pr, caf.getIdcoreasset(),featurenamemodified));
				csvCAcontent = csvCAcontent.concat(extractCSVForProductChurnCustomizingCoreAsset(pr, caf.getIdcoreasset(),featurenamemodified));
			}
		}
		//we know have all the paths for the core asset.
		 ArrayList<String>  mainPaths = customs.utils.Formatting.extractPathsFromPathListWitoutFilePath(capaths);//extract parsed paths to header.
		 String headerpaths = extractCSVFromArrayListPaths(mainPaths);
		
		return headerpaths+csvCAcontent;
	}

	private String extractCSVForProductChurnCustomizingCoreAsset(String pr, int idcoreasset, String featureid){
		//String csvheader = "id,value,frequency,id_core_asset,fname,product_release,operation";
			ArrayList<String> paths = new ArrayList<>();
			   String csvCustoms= "";
			   Iterable <CustomizationsByFeature> featureCustoms = featureCustomsDao.getCustomsByFeatureid(featureid); //.findAll();
			   Iterator<CustomizationsByFeature> ite=featureCustoms.iterator();
			   CustomizationsByFeature featureCusto;
			   System.out.println("featureCustoms:" +featureCustoms);
			   while (ite.hasNext()) {
				   featureCusto= ite.next();
				  if(featureCusto.getFeatureId().equals(featureid) && featureCusto.getCoreassetid()==idcoreasset && featureCusto.getPr().equals(pr)) {
					//  csvCustoms = csvCustoms.concat("\n"+featureCusto.getCapath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","").concat("/"+featureCusto.getPr()));
					  csvCustoms = csvCustoms.concat(
				    		 "\n"+featureCusto.getCapath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","").concat("/"+featureCusto.getPr())//+"/Churn")
				    		 +","+featureCusto.getAmount()
				    		 +",0"////treemap.getNumberofproductscustomizing()--Frequency; 
				    		 +"," +featureCusto.getCoreassetid()
				    		 +","+featureid
				    		 +","+featureCusto.getPr()
				    		 +",churn"
				    		 );
				     paths.add(featureCusto.getCapath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", ""));
				  	 }
			   }
			return csvCustoms;
		}
	
	private String extractCSVForProductCustomizingCoreAsset(String pr, int idcoreasset, String featureid) {
		// For each customization that pr does to featuremodified introduce a line like

		//...sensors.js/productR-v1.0/deleted, 10
		String csvforpr="";
		DeletedCustomsByProductsToFeatures deletedCustoms;
		AddedCustomsByProductsToFeatures addedCustoms;
		Iterator<AddedCustomsByProductsToFeatures> it = addedTofeaturesDao.findAll().iterator();
		Iterator<DeletedCustomsByProductsToFeatures> itd = deletedTofeaturesDao.findAll().iterator();
		ArrayList<String> prheaders = new ArrayList<String>();
		
		while (it.hasNext()) {//for additions to the feature
			addedCustoms = it.next();
			if (addedCustoms.getIdcoreasset()==idcoreasset && addedCustoms.getPr().equals(pr) && addedCustoms.getIdfeature().equals(featureid)) {
				//add the header for the product;e.g. //...sensors.js/productR-v1.0,
				csvforpr=csvforpr.concat("\n"
						+addedCustoms.getAssetpath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","").concat("/"+pr+","));
				//add the line with the number of added lines: eg. ...sensors.js/productR-v1.0/added, 20
				csvforpr=csvforpr.concat("\n"+addedCustoms.getAssetpath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","").concat("/"+pr+"/added,"
						+ ""+addedCustoms.getLinesadded()+",0,"+idcoreasset+","+featureid+","+pr+",add"));
				prheaders.add(addedCustoms.getAssetpath());
			}
		}//en while for additions
		
		while(itd.hasNext()){//while for deletions
			deletedCustoms=itd.next();
			if (deletedCustoms.getIdcoreasset()==idcoreasset && deletedCustoms.getPr().equals(pr) && deletedCustoms.getIdfeature().equals(featureid)) {
				if(!prheaders.contains(deletedCustoms.getAssetpath()))
					//add the header for the product;e.g. //...sensors.js/productR-v1.0,
					csvforpr=csvforpr.concat("\n"
							+deletedCustoms.getAssetpath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","").concat("/"+pr+","));
				
				csvforpr=csvforpr.concat("\n"+deletedCustoms.getAssetpath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","").concat("/"+pr+"/deleted,"
						+ ""+deletedCustoms.getDeletedlines()+",0,"+idcoreasset+","+featureid+","+pr+",delete"));
			}
				
		}
		return csvforpr;
	}
	
	
	private String extractCSVFromArrayListPaths(ArrayList<String> mainPaths) {
		String headerpaths="";
		 System.out.println("mainPaths:"+mainPaths+"\n\n");
		   for (int i=0; i< mainPaths.size();i++) {
			   if(!mainPaths.get(i).equals(""))
				   headerpaths=headerpaths.concat("\n"+mainPaths.get(i)+",");
		   }
		   return headerpaths;
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
		 String header= "diff( Baseline-v1.0."+ca.getName()+",  "+pr+"."+ca.getName()+" [VP.contains(hasFeature('"+featureid+"'))]";
		 		
		 model.addAttribute("diffHeader", header);
		 model.addAttribute("maintitle", "How is feature '"+featureid+"' being customized in products?");
		 model.addAttribute("difftitle", "diff(Feature: '" +featureid+"', Product-Portfolio)");
	}

}
