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
import customs.models.SPLdao;
import customs.models.VariationPointDao;
import customs.utils.VPDiffUtils;
import customs.models.CustomizationsGByFile;
import customs.models.CustomizationsGByFileDao;
import customs.models.DeletedCustomsByProductsToFeatures;
import customs.models.DeletedCustomsByProductsToFeaturesDao;
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
	  @Autowired private AddedCustomsByProductsToFeaturesDao addedTofeaturesDao;
	  @Autowired private DeletedCustomsByProductsToFeaturesDao deletedTofeaturesDao;
	  @Autowired private CoreassetsAndFeaturesDao coreassetsForFeature;
	  @Autowired private ProductReleaseDao   prDao;

	   private String pathToResource = "./src/main/resources/static/";	
	  @RequestMapping("treemapLightsView")
	   public String getTreeMapTrafficLight(
	   				@RequestParam(value="base", required=false) String idbaseline,
	   				@RequestParam(value="fname", required=false) String featurenamemodified,
	   				@RequestParam(value="idfile", required=false) int idfile,
	   				@RequestParam(value="pr", required=false) String pr,
	   				Model model){
		  
		   System.out.println("THIS IS featurenamemodified: "+featurenamemodified);
		   System.out.println("THIS IS idbaseline: "+idbaseline);
		   System.out.println("THIS IS idfile: "+idfile);
		   System.out.println("THIS IS  pr: "+ pr);
		   
		  //String csvContent = extractCSVForTreeMapLightsByFeature(idfile, featurenamemodified); //this depicts the churn for products
		 //  String csvContent= extractCSVForTreeMapFeatureProducts(featurenamemodified,idbaseline);//this depicts the added/deleted lines per product to files.
		   
		   String csvContent= extractCSVForTreeMapFeatureProductsChurn(featurenamemodified,idbaseline);
		   customs.utils.FileUtils.writeToFile(pathToResource+"treemapLights.csv",csvContent);//path and test
		   
		   if( pr!=null && idfile!=0)
		     addDiffViewForCoreAssetId(model,idfile,pr, featurenamemodified);
		   
		   model.addAttribute("pr",pr);
		   model.addAttribute("fname",featurenamemodified);
		   model.addAttribute("maintitle", "How is feature '"+featurenamemodified+"' being customized in products?");
		   model.addAttribute("difftitle", "diff(Feature: '" +featurenamemodified+"', Product-Portfolio)");
		   
		  return "treemapLights2"; 
	 	}
	  private String extractCSVForTreeMapFeatureProductsChurn (String featurenamemodified,String idbaseline) {
			String csvheader = "id,value,frequency,id_core_asset,fname,product_release,operation";
			String csvcontent="";
			csvcontent= extractCSVForFeatureProductChurn(featurenamemodified,idbaseline);	
			return csvheader.concat(csvcontent);
	  }
	  
	  private String extractCSVForTreeMapFeatureProducts(String featurenamemodified,String idbaseline) {
			String csvheader = "id,value,frequency,id_core_asset,fname,product_release,operation";
			String csvcontent="";
			csvcontent= extractCSVForFeatureProduct(featurenamemodified,idbaseline);	
			return csvheader.concat(csvcontent);
		}
	  
	  private String extractCSVForFeatureProductChurn(String featurenamemodified, String idbaseline) {
		  System.out.println("In extractCSVForFeatureProductChurn");
		  System.out.println("In featurenamemodified:"+featurenamemodified);
		  System.out.println("In idbaseline:"+idbaseline);
		  Iterable<CoreassetsAndFeatures> all = coreassetsForFeature.findAll();
			System.out.println(all.toString());
			CoreassetsAndFeatures caf;
			Iterator<CoreassetsAndFeatures> it = all.iterator();
			ArrayList<String> capaths = new ArrayList<String>();
			String csvCAcontent="";
			while (it.hasNext()) { 
				caf = it.next();
				if (caf.getBaseline().equals(idbaseline) && caf.getFeatureid().equals(featurenamemodified)) {
					System.out.println("paths!:"+caf.getCapath());
					capaths.add(caf.getCapath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", ""));
					//include the whole line for the core-assets + its size
					csvCAcontent = csvCAcontent.concat("\n"+caf.getCapath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","")+",1");//TODO reused CAs are left to minimun size
					
					//for each core asset see if any product release customizes it
					Iterator<ProductRelease> ite = prDao.findAll().iterator();
					while(ite.hasNext()) {//para cada release ver si customiza el core-assetID
						csvCAcontent = csvCAcontent.concat(extractCSVForProductChurnCustomizingCoreAsset(ite.next().getIdrelease(), caf.getIdcoreasset(),featurenamemodified));
					}
				}
			}
			//we know have all the paths for the core asset.
			 ArrayList<String>  mainPaths = customs.utils.Formatting.extractPathsFromPathListWitoutFilePath(capaths);//extract parsed paths to header.
			 String headerpaths = extractCSVFromArrayListPaths(mainPaths);
			return headerpaths+csvCAcontent;
	  }

		private String extractCSVForFeatureProduct(String featurenamemodified, String idbaseline) {
			
			Iterable<CoreassetsAndFeatures> all = coreassetsForFeature.findAll();
			System.out.println(all.toString());
			CoreassetsAndFeatures caf;
			Iterator<CoreassetsAndFeatures> it = all.iterator();
			ArrayList<String> capaths = new ArrayList<String>();
			String csvCAcontent="";
			while (it.hasNext()) { 
				caf = it.next();
				//System.out.println(caf.getBaseline()+"  Vs  "+ idbaseline);
				//System.out.println(caf.getFeatureid()+"  Vs  "+ featurenamemodified);
				
				if (caf.getBaseline().equals(idbaseline) && caf.getFeatureid().equals(featurenamemodified)) {
					System.out.println("paths!:"+caf.getCapath());
					capaths.add(caf.getCapath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", ""));
					//include the whole line for the core-assets + its size
					csvCAcontent = csvCAcontent.concat("\n"+caf.getCapath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","")
							+",1");//TODO reused CAs are left to minimun size
					
					//incluir los products!!
					Iterator<ProductRelease> ite = prDao.findAll().iterator();
					while(ite.hasNext()) {
						csvCAcontent = csvCAcontent.concat(extractCSVForProductCustomizingCoreAsset(ite.next().getIdrelease(), caf.getIdcoreasset(),featurenamemodified));
					}
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

		
		/**old diagram**/
	//Old one ------------- this is for the "diagram part" of the view
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

		   
		  
		   String csvContent="id,value,frequency,id_core_asset,fname,product_release"; 
		   ArrayList<String> paths = new ArrayList<>();
		   String csvCustoms= "";
		   
		   Iterable <CustomizationsByFeature> featureCustoms = featureCustomsDao.getCustomsByFeatureid(featureid); //.findAll();
		   Iterator<CustomizationsByFeature> ite=featureCustoms.iterator();
		   CustomizationsByFeature featureCusto;
		   System.out.println("featureCustoms:" +featureCustoms);
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
	
	
	
	
	
	/** Diff view part**/
	
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
