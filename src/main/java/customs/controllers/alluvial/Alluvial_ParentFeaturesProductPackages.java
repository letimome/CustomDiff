package customs.controllers.alluvial;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.Churn_Features_ComponentPackages;
import customs.models.Churn_Features_ComponentPackagesDao;
import customs.models.Churn_ParentFeatures_ProductComponents;
import customs.models.Churn_ParentFeatures_ProductComponentsDao;
import customs.models.ComponentPackage;
import customs.models.ComponentPackageDao;
import customs.models.CoreAsset;
import customs.models.CoreAssetDao;
import customs.models.ParentFeature;
import customs.models.ParentFeatureDao;
import customs.models.ProductRelease;
import customs.models.ProductReleaseDao;

@Controller
public class Alluvial_ParentFeaturesProductPackages {

	
	@Autowired private Churn_ParentFeatures_ProductComponentsDao parentFeaturesPackagesDao;
	@Autowired private Churn_Features_ComponentPackagesDao featuresPackagesDao;
	@Autowired private ProductReleaseDao prDao;
	@Autowired private ParentFeatureDao parentFeatureDao;
	@Autowired private CoreAssetDao caDao;
	@Autowired private ComponentPackageDao componentDao;
	
	
	private String lastFrom=null;
	private String pathToResource = "./src/main/resources/static/";
	
	
	 @RequestMapping("diff_parent_features_product_packages")
	   public String getTreeMapForProductRelease(
			   @RequestParam(value="idproductrelease", required=true) int idproductrelease, 
			   Model model){
		 
		   System.out.println("The productrelease: "+idproductrelease);
           
		   ProductRelease pr = prDao.getProductReleaseByIdproductrelease(idproductrelease);
           
		   String csvheader="source,target,value,idparentfeature,idpackage,idproductrelease";
		   String csvContent  = computeForCustomizationsForPRassetsToFeaturesChurn(pr);
		  
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvheader+csvContent);//path and test// + csvInitialPaths
		  
		   model.addAttribute("pr",pr.getName());
		   model.addAttribute("idproductrelease",idproductrelease);
		   model.addAttribute("maintitle", "Which packages is '"+pr.getName()+"' customizing?");
		   model.addAttribute("difftitle", "diff(Baseline-v1.0, "+pr.getName()+")");
		  
		   customs.utils.NavigationMapGenerator.generateNavigationMapForProductSide("features","core-asset","Expression",pr.getName());
		   
		   return "alluvials/diff_parent_features_product_packages"; 
	 	}
//
	 
	
	 
	 @RequestMapping("diff_features_product_packages")
	   public String getAlluvial_Features_ProductPackages(
			   
			   @RequestParam(value="idproductrelease", required=true) int idproductrelease, 
			   @RequestParam(value="from", required=false) String from,
			   @RequestParam(value="idparentfeature", required=true) int idparentfeature,
			   Model model){
		 
		   System.out.println("The productrelease: "+idproductrelease);
         
		   ProductRelease pr = prDao.getProductReleaseByIdproductrelease(idproductrelease);
		   ParentFeature pf = parentFeatureDao.getParentFeatureByIdparentfeature(idparentfeature);
		   if(from==null || from.equals("undefined"))
			   from=lastFrom;
		   else lastFrom=from;
         	   
		   String csvheader="source,target,value,idfeature,idpackage,idproductrelease,idparentfeature";
		   String csvContent  = computeForCustomizationsForFeaturesToPackagesChurn(pr,pf);
		  
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvheader+csvContent);//path and test// + csvInitialPaths
		  

		   model.addAttribute("idparentfeature",idparentfeature);
		   model.addAttribute("from",from);
		   model.addAttribute("idproductrelease",idproductrelease);
		   model.addAttribute("maintitle", "Which component packages are customized by '"+pr.getName()+"'?");
		   model.addAttribute("difftitle", "diff(Baseline-v1.0, "+pr.getName()+")");
		  
		   customs.utils.NavigationMapGenerator.generateNavigationMapForProductSide("features","core-asset","Expression",pr.getName());
		   
		   return "alluvials/diff_features_product_packages"; 
	 	}
	 
	 
	private String computeForCustomizationsForFeaturesToPackagesChurn(ProductRelease pr, ParentFeature pf) {
		  String csvCustoms="";
		  
		   System.out.println("The productrelease to analyze is: "+pr.getName());
		   
		   Iterable<Churn_Features_ComponentPackages>  customizedComponents = featuresPackagesDao.getCustomsByIdproductrelease(pr.getId_productrelease());
		   Iterator<Churn_Features_ComponentPackages> it= customizedComponents.iterator();
		   
		   Churn_Features_ComponentPackages custo;
		   ArrayList<Integer> listcustomizedComps = new ArrayList<Integer>();
		   

		   while(it.hasNext()) {//getting lines for Customized components
			   custo = it.next();
			  if (custo.getIdparentfeature()==pf.getIdparentFeature()) {
				  listcustomizedComps.add(custo.getIdpackage());//	 source,target,value,idfeature,idpackage,idproductrelease,idparentfeature
				   csvCustoms = csvCustoms.concat("\n" 
				   +custo.getFeaturename()+","+custo.getPackage_name()+","+custo.getChurn()+","+custo.getIdfeature()
				   +","+custo.getIdpackage()+","+custo.getIdproductrelease()+","+custo.getIdparentfeature()); 
			  }
			  
		   }
		   
		   csvCustoms = addNotCustomizedComponentPackagesTotheCSV(listcustomizedComps,csvCustoms,pr.getId_productrelease());
		 //  csvCustoms= 	addNewComponentPackagesTotheCSV(csvCustoms,pr.getName());
		return csvCustoms;
	}

	private String computeForCustomizationsForPRassetsToFeaturesChurn(ProductRelease pr) {
		
		   String csvCustoms="";
		  
		   System.out.println("The productrelease to analyze is: "+pr.getName());
		   Iterable<Churn_ParentFeatures_ProductComponents>  customizedComponents = parentFeaturesPackagesDao.getCustomsByIdproductrelease(pr.getId_productrelease());
		   Iterator<Churn_ParentFeatures_ProductComponents> it= customizedComponents.iterator();
		   
		   Churn_ParentFeatures_ProductComponents custo;
		   ArrayList<Integer> listcustomizedComps = new ArrayList<Integer>();
		   

		   while(it.hasNext()) {//getting lines for Customized components
			   custo = it.next();
			
			   listcustomizedComps.add(custo.getIdpackage());//	 source,target,value,idfeature,idpackage,idproductrelease,idparentfeature
			   csvCustoms = csvCustoms.concat("\n" +custo.getParentfeaturename()+
					   ","+custo.getPackage_name()+","
					   +custo.getChurn()+","+custo.getId_parentfeature() +","+custo.getIdpackage()+","+custo.getIdproductrelease()); 
		   }
		   
		   csvCustoms = addNotCustomizedComponentPackagesTotheCSV(listcustomizedComps,csvCustoms,pr.getId_productrelease());
		 //  csvCustoms= 	addNewComponentPackagesTotheCSV(csvCustoms,pr.getName());
		return csvCustoms;
		
	}

	private String addNotCustomizedComponentPackagesTotheCSV(ArrayList<Integer> listcustomizedComps, String csvContent, int idproductrelease) {
	
				Iterator<CoreAsset> it = caDao.findAll().iterator();
				System.out.println(it.toString());
				CoreAsset ca;
				String root="";
				ComponentPackage comp_package;
				ArrayList<ComponentPackage> addedComponents=new ArrayList<ComponentPackage>();
				while (it.hasNext()) {
					ca = it.next();
					comp_package = componentDao.getComponentPackageByIdpackage(ca.getIdpackage());
					if (!listcustomizedComps.contains(ca.getIdpackage()) && (!addedComponents.contains(comp_package))) {
						if(comp_package.getIsroot()==1) root=" Root";
						else root=comp_package.getName();
						csvContent = csvContent.concat("\nNOT_CUSTOMIZED,"+root+",0.01,,,"+idproductrelease);
					}
					addedComponents.add(comp_package);
				}
				
				return csvContent;
	}
	
	
	
}
