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
import customs.models.CoreAssetDao;
import customs.models.CoreassetsAndFeatures;
import customs.models.CoreassetsAndFeaturesDao;
import customs.models.Feature;
import customs.models.FeatureDao;
import customs.models.ProductReleaseDao;

@Controller
public class Alluvial_FeaturePackage_PP {

	private String pathToResource = "./src/main/resources/static/";
	 @Autowired private ProductReleaseDao prDao;
	 @Autowired private ComponentPackageDao packageDao;
	 @Autowired private FeatureDao fDao;
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
		   
		   model.addAttribute("idparentfeature",f.getIdparent());   
		   model.addAttribute("maintitle", "Which packages from feature '"+idfeature+"' are customized by the products?");
		   
		   return "alluvials/diff_feature_packages_pp"; 
	 	}


	 @RequestMapping("diff_core_assets_pp")
	   public String getDiffCoreAssetsPP( @RequestParam(value="idfeature", required=true) String idfeature,  
			   @RequestParam(value="idpackage", required=true) int idpackage,  Model model){
		  
		   
		   String csvContent= computeForCustomizationsForFeaturePackageAssetsToPPChurn(idfeature,idpackage);
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvContent);//path and test		  
		  
		   model.addAttribute("idfeature",idfeature);
		   model.addAttribute("idpackage",idpackage);
		   model.addAttribute("maintitle", "How are feature '"+idfeature+"' assets customized by the product portfolio?");

		  customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureSide(idfeature, "core-asset","Expression","product"); 
		  
		  return "alluvials/diff_feature_pp";
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
			listcustomizedComps.add(custom.getId_coreasset());
			csvContent = csvContent.concat("\n"+custom.getCa_name()+","+custom.getPr_name()+","+custom.getChurn()
			+","+custom.getId_coreasset()+","+idpackage+","+idfeature+","+custom.getIdproductrelease());
		}
		csvContent=	addNotCustomizedComponentPackagesTotheCSV(listcustomizedComps,csvContent,idfeature);
		return csvheader+csvContent;
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
				csvContent = csvContent.concat("\n"+ca.getCa_name()+",NOT_CUSTOMIZED,0.2");
			}
			addedComponents.add(comp_package);
		}
				
		return csvContent;
	}
}
