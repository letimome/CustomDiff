package customs.controllers.alluvial;

import java.util.ArrayList;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.Churn_CoreAssetsAndFeaturesByPR;
import customs.models.Churn_CoreAssetsAndFeaturesByPRDao;
import customs.models.Churn_Features_ComponentPackages;
import customs.models.Churn_Features_ComponentPackagesDao;
import customs.models.ComponentPackage;
import customs.models.ComponentPackageDao;
import customs.models.CoreAsset;
import customs.models.CoreAssetDao;
import customs.models.CoreassetsAndFeatures;
import customs.models.CoreassetsAndFeaturesDao;
import customs.models.Feature;
import customs.models.FeatureDao;
import customs.models.ParentFeature;
import customs.models.ParentFeatureDao;
import customs.models.ProductReleaseDao;

@Controller
public class Alluvial_FeaturePackage_PP {

	private String pathToResource = "./src/main/resources/static/";
	 @Autowired private ProductReleaseDao prDao;
	 @Autowired private ComponentPackageDao packageDao;
	 @Autowired private FeatureDao fDao;
	 @Autowired private ParentFeatureDao parentDao;
	 @Autowired private CoreassetsAndFeaturesDao coreassetsForFeature;
	  @Autowired private Churn_CoreAssetsAndFeaturesByPRDao customsPRtoCA;
	 @Autowired private CoreAssetDao caDao;
		
	@Autowired private ComponentPackageDao componentDao;
	@Autowired private CoreassetsAndFeaturesDao caFeatures;
	@Autowired private Churn_Features_ComponentPackagesDao featurePackagesDao;
	@Autowired private Churn_CoreAssetsAndFeaturesByPRDao churnAssetsPRDao; 
	
	 @RequestMapping("diff_feature_packages_pp")
	   public String getAlluvialFpackagedPP(
			   @RequestParam(value="idfeature", required=true) String idfeature, 
			   Model model){
		 
		   System.out.println("The productrelease: "+idfeature);
           Feature f =fDao.getFeatureByIdfeature(idfeature);
		   String csvheader="source,target,value,idparentfeature,idpackage,idproductrelease,idfeature";
		   String csvContent  = computeForCustomizationsForFeaturePackagesToPPChurn(idfeature);
		  
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvheader+csvContent);//path and test// + csvInitialPaths
		  //idparentfeature
		   model.addAttribute("idfeature",idfeature);
		   model.addAttribute("idparentfeature",f.getIdparent());   
		   model.addAttribute("maintitle", "Which packages from feature '"+idfeature+"' are customized by the products?");
		   ParentFeature parent = parentDao.getParentFeatureByIdparentfeature(f.getIdparent());
		   customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureSideLevel2(idfeature,"core-asset","Expression", idfeature+" packages",parent.getName());
		   
		   return "alluvials/diff_feature_packages_pp"; 
	 	}


	 @RequestMapping("diff_core_assets_pp")
	   public String getDiffCoreAssetsPP( @RequestParam(value="idfeature", required=true) String idfeature,  
			   @RequestParam(value="idpackage", required=true) int idpackage,  Model model){
		  
		   
		   String csvContent= computeForCustomizationsForFeaturePackageAssetsToPPChurn(idfeature,idpackage);
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvContent);//path and test		  
		  
		   model.addAttribute("idfeature",idfeature);
		   Feature f = fDao.getFeatureByIdfeature(idfeature);
		   ParentFeature parent = parentDao.getParentFeatureByIdparentfeature(f.getIdparent());
		   model.addAttribute("idpackage",idpackage);
		   ComponentPackage pa = packageDao.getComponentPackageByIdpackage(idpackage);
		   model.addAttribute("maintitle", "How are feature '"+idfeature+"' assets, in '"+pa.getName()+"' package, customized by the product portfolio?");

		   ComponentPackage pack = componentDao.getComponentPackageByIdpackage(idpackage);
		  customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureSideLevel3(idfeature, idfeature+"."+pa.getName(), pa.getName()+".files",parent.getName());
		  
		  return "alluvials/diff_feature_pp";
	  }
	 
	 @RequestMapping("diff_all_core_assets_pp")
	   public String getDiff_All_CoreAssetsPP( @RequestParam(value="idfeature", required=true) String idfeature,  
			  Model model){
		    
		   String csvContent= extractCSVForFeatureProductPortfolioChurn(idfeature);
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvContent);//path and test
		  
		   model.addAttribute("idfeature",idfeature);
		   model.addAttribute("maintitle", "How are all feature '"+idfeature+"'s assets customized by the product portfolio?");
		   Feature f = fDao.getFeatureByIdfeature(idfeature);
		   ParentFeature parent = parentDao.getParentFeatureByIdparentfeature(f.getIdparent());
		   customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureSideLevel3(idfeature, idfeature," all files",parent.getName());
		  return "alluvials/diff_feature_pp";
	  }
	 
	 private String extractCSVForFeatureProductPortfolioChurn(String featurenamemodified) {
			//String csvheader = "id,value,product_release,operation,fname";
			String csvheader = "source,target,value,idproductrelease,operation,idfeature,idcoreasset,id_pa";
			String csvcontent= extractCSVForFeatureProductPorfolioView(featurenamemodified);	
			return csvheader.concat(csvcontent);
		}


		private String extractCSVForFeatureProductPorfolioView(String featurenamemodified) {
			Iterator<Churn_CoreAssetsAndFeaturesByPR> it = customsPRtoCA.findAll().iterator();
			Churn_CoreAssetsAndFeaturesByPR custom;
			ArrayList<String> listcustomizedCas= new ArrayList<String>();
			
			String csvContent ="";//"\n"+featurenamemodified+",";
			while (it.hasNext()) {
				custom = it.next();
				if(custom.getIdfeature().equals(featurenamemodified)) {
					listcustomizedCas.add(custom.getCa_path());
					csvContent=csvContent.concat("\n"+custom.getCa_name()+",").concat(custom.getPr_name()+","+custom.getChurn()+","
					+custom.getIdproductrelease()+",churn,"+featurenamemodified+","+custom.getIdcoreasset()+","+custom.getId_coreasset());
				}
				/*csvContent=csvContent.concat("\n"+featurenamemodified+"/").concat(custom.getIdrelease()+","+custom.getChurn()+","+custom.getIdrelease()+",churn,"+featurenamemodified);
				}*/ 
			}
			csvContent = addNotCustomizedCoreAssetsTotheCSV(listcustomizedCas,csvContent,featurenamemodified);
			return csvContent;
		}
	 private String computeForCustomizationsForFeaturePackageAssetsToPPChurn(String idfeature, int idpackage) {
		 String csvheader="source,target,value,idcoreasset,idpackage,idfeature,idproductrelease";
		Iterable<Churn_CoreAssetsAndFeaturesByPR> list = churnAssetsPRDao.getCustomsByIdfeature(idfeature);
		Iterator<Churn_CoreAssetsAndFeaturesByPR> it = list.iterator();
		Churn_CoreAssetsAndFeaturesByPR custom;
		String csvContent  ="";
		
		ArrayList<Integer> listcustomizedComps = new ArrayList<Integer>();
		
		while (it.hasNext()) {
			custom = it.next();
			CoreAsset ca = caDao.getCoreAssetByIdcoreasset(custom.getId_coreasset());
			if (ca.getIdpackage()!=idpackage) continue;
			listcustomizedComps.add(custom.getId_coreasset());
			csvContent = csvContent.concat("\n"+custom.getCa_name()+","+custom.getPr_name()+","+custom.getChurn()
			+","+custom.getId_coreasset()+","+idpackage+","+idfeature+","+custom.getIdproductrelease());
		}
	//	csvContent=	addNotCustomizedComponentPackagesTotheCSV(listcustomizedComps,csvContent,idfeature);
		return csvheader+csvContent;
		}
	 
	 private String addNotCustomizedCoreAssetsTotheCSV(ArrayList<String> listcustomizedCas, String csvContent,String featurenamemodified) {
			//String csvheader = "source,target,value,product_release,operation,fname";
			System.out.println("addNotCustomizedCoreAssetsTotheCSV");
			Iterator<CoreassetsAndFeatures> it = coreassetsForFeature.findAll().iterator();
			System.out.println(it.toString());
			CoreassetsAndFeatures caf;
			
			
			while (it.hasNext()) {
				caf = it.next();
				if ((caf.getId_feature().equals(featurenamemodified)) &&(!listcustomizedCas.contains(caf.getCa_path()))) {
					csvContent = csvContent.concat("\n"+caf.getCa_name()+",NOT_CUSTOMIZED,0.01");
				}
			}
			
			return csvContent;
		}
		  
	private String computeForCustomizationsForFeaturePackagesToPPChurn(String idfeature) {
		Iterable<Churn_Features_ComponentPackages> list = featurePackagesDao.getCustomsByIdfeature(idfeature);
		Iterator<Churn_Features_ComponentPackages> it = list.iterator();
		Churn_Features_ComponentPackages custom;
		String csvContent  ="";
		ArrayList<Integer> listcustomizedComps = new ArrayList<Integer>();
		while (it.hasNext()) {
			custom = it.next();
			
			listcustomizedComps.add(custom.getIdpackage());
			csvContent = csvContent.concat("\n"+custom.getPackage_name()+","+custom.getPr_name()+","+custom.getChurn()
			+","+custom.getIdparentfeature()
			+","+custom.getIdpackage()+","+custom.getIdproductrelease()+","
			+idfeature);
		}
		csvContent=	addNotCustomizedComponentPackagesTotheCSV(listcustomizedComps,csvContent,idfeature);
		return csvContent;
	}
	
	private String addNotCustomizedComponentPackagesTotheCSV(ArrayList<Integer> listcustomizedComps, String csvContent, String idfeature ) {
		// source,target,value,idparentfeature,idpackage,pr";
		Iterator<CoreassetsAndFeatures> it =	caFeatures.getFeatureCoreAssetsByIdfeature(idfeature).iterator();
		System.out.println(it.toString());
		CoreassetsAndFeatures ca;
		
			
		ComponentPackage comp_package;
		ArrayList<ComponentPackage> addedComponents=new ArrayList<ComponentPackage>();
		while (it.hasNext()) {
			ca = it.next();
			comp_package = componentDao.getComponentPackageByIdpackage(ca.getIdpackage());
			if (!listcustomizedComps.contains(ca.getIdpackage()) && (!addedComponents.contains(comp_package))
					) {
				csvContent = csvContent.concat("\n"+ca.getCa_name()+",NOT_CUSTOMIZED,0.01");
			}
			addedComponents.add(comp_package);
		}
				
		return csvContent;
	}
}
