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
import customs.models.ProductRelease;
import customs.models.ProductReleaseDao;


@Controller
public class Alluvial_Feature_Product_Packages {
	@Autowired private Churn_Features_ComponentPackagesDao featuresPackagesDao;
	@Autowired private ProductReleaseDao prDao;
	@Autowired private CoreassetsAndFeaturesDao caFeaturesDao;
	@Autowired private FeatureDao fDao;
	@Autowired private CoreAssetDao caDao;
	@Autowired private ComponentPackageDao componentDao;
	@Autowired private Churn_CoreAssetsAndFeaturesByPRDao churnCAforPrDao;
	
	
	private String pathToResource = "./src/main/resources/static/";
	
	
	 @RequestMapping("diff_feature_and_product_packages")
	   public String getTreeMapForProductRelease(
			   @RequestParam(value="idproductrelease", required=true) int idproductrelease, 
			   @RequestParam(value="idfeature", required=true) String idfeature, 
			   Model model){
		 
		   System.out.println("The productrelease: "+idproductrelease);
           
		   ProductRelease pr = prDao.getProductReleaseByIdproductrelease(idproductrelease);
		   Feature f = fDao.getFeatureByIdfeature(idfeature);
           
		   String csvheader="source,target,value,idparentfeature,idpackage,idproductrelease,idfeature";
		   String csvContent  = computeCustomizationsForPRFeaturePackages(pr,f);
		   
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvheader+csvContent);//path and test// + csvInitialPaths
		  
		   model.addAttribute("pr",pr.getName());
		   model.addAttribute("idproductrelease", idproductrelease);
		   model.addAttribute("idfeature", idfeature);
		   model.addAttribute("idparentfeature", f.getIdparent());
		   model.addAttribute("maintitle", "Which packages is '"+pr.getName()+"' customizing?");
		   model.addAttribute("difftitle", "diff(Baseline-v1.0, "+pr.getName()+")");
		  
		   customs.utils.NavigationMapGenerator.generateNavigationMapForProductSide("features","core-asset","Expression",pr.getName());
		   
		   return "alluvials/diff_feature_and_product_packages"; 
	 	}

		
	
	 
	 
	 



	private String computeCustomizationsForPRFeaturePackages(ProductRelease pr, Feature f) {
		String csvContent="";
		Iterator<Churn_Features_ComponentPackages> it = featuresPackagesDao.getCustomsByIdproductrelease(pr.getId_productrelease()).iterator();
		Churn_Features_ComponentPackages custom;
		ArrayList<Integer> customizedPackages = new ArrayList<Integer>();
		while(it.hasNext()) {
			custom = it.next();//	   String csvheader="source,target,value,idparentfeature,idpackage,idproductrelease";
			if ( f.getIdfeature().equals(custom.getIdfeature())) {
				
				customizedPackages.add(custom.getIdpackage());
				csvContent = csvContent.concat("\n"+custom.getPackage_name()+" ["+f.getIdfeature()+"]"+","
						+ custom.getPackage_name()+" ["+pr.getName()+"],"
				+custom.getChurn()+","+custom.getIdparentfeature()+","+custom.getIdpackage()+","
						+custom.getIdproductrelease()+","+custom.getIdfeature());
			}
					
		}
		
		csvContent = csvContent +computeCSVForNotCustomizedFeaturePackages(pr, f, customizedPackages);
		return csvContent;
	}
	
	
	
	
	private String computeCSVForNotCustomizedPRPackages(ProductRelease pr, Feature f, ArrayList<Integer> customizedPackages) {
		String csvContent="";

		return csvContent;
	}
	
	private String computeCSVForNotCustomizedFeaturePackages(ProductRelease pr, Feature f,  ArrayList<Integer> customizedPackages) {
		String csvContent="";
		Iterator<CoreassetsAndFeatures> it = caFeaturesDao.getFeatureCoreAssetsByIdfeature(f.getIdfeature()).iterator();
		CoreassetsAndFeatures caf;
		ArrayList<Integer> listpackages = new ArrayList<Integer>();
		ComponentPackage pack;
		while(it.hasNext()) {
			caf = it.next();
			if(!customizedPackages.contains(caf.getIdpackage()) && (!listpackages.contains(caf.getIdpackage()))) {
				listpackages.add(caf.getIdpackage());
				pack=componentDao.getComponentPackageByIdpackage(caf.getIdpackage());
				csvContent = csvContent.concat("\n"+pack.getName()+",NOT_CUSTOMIZED,0.2");
			}
				
		}
		

		return csvContent;
	}
	
	
}
