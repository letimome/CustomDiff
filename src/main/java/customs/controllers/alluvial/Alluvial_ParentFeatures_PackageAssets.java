package customs.controllers.alluvial;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import customs.models.Churn_ParentFeatures_PackageAssets;
import customs.models.Churn_ParentFeatures_PackageAssetsDao;
import customs.models.ComponentPackage;
import customs.models.ComponentPackageDao;
import customs.models.CoreAsset;
import customs.models.CoreAssetDao;
import customs.models.ParentFeatureDao;
import customs.models.ProductRelease;
import customs.models.ProductReleaseDao;

@Controller
public class Alluvial_ParentFeatures_PackageAssets {
	


	 @Autowired private ProductReleaseDao prDao;
	 @Autowired private ComponentPackageDao packageDao;
	 @Autowired private ParentFeatureDao parentFeatureDao;
	 @Autowired private CoreAssetDao caDao;
		
	 @Autowired private Churn_ParentFeatures_PackageAssetsDao parentFeaturesPackagesDao;
		
	 private String pathToResource = "./src/main/resources/static/";
	
	 @RequestMapping("diff_parent_features_package_assets")
	   public String getTreeMapForProductRelease(
			   @RequestParam(value="idproductrelease", required=true) int idproductrelease, 
			   @RequestParam(value="idpackage", required=true) int idpackage, 
			   @RequestParam(value="from", required=false) String from,
			   Model model){
		 
		   System.out.println("The productrelease: "+idproductrelease);
         
		   ProductRelease pr = prDao.getProductReleaseByIdproductrelease(idproductrelease);
		   ComponentPackage comp_package =  packageDao.getComponentPackageByIdpackage(idpackage);
         
		   String csvheader="source,target,value,idparentfeature,idcoreasset,idproductrelease,idpackage";
		   String csvContent  = computeForCustomizationsForPRassetsToFeaturesChurn(pr, comp_package);
		  
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvheader+csvContent);//path and test// + csvInitialPaths
		  
		   model.addAttribute("idproductrelease",idproductrelease);
		   model.addAttribute("idpackage",idpackage);
		   model.addAttribute("from",from);
		   
		   model.addAttribute("maintitle", "Which  assets from package '"+comp_package.getName()+"' is "+pr.getName()+" customizing?");
		   model.addAttribute("difftitle", "diff(Baseline-v1.0, "+pr.getName()+")");
		  
		   customs.utils.NavigationMapGenerator.generateNavigationMapForProductSide("features","core-asset","Expression",pr.getName());
		   
		   return "alluvials/diff_parent_features_package_assets"; 
	 	}

	 @RequestMapping("diff_parent_features_all_assets")
	   public String getAlluvialdiff_parent_features_all_assets(
			   @RequestParam(value="idproductrelease", required=true) int idproductrelease, 
			   @RequestParam(value="from", required=false) String from,
			   Model model){
		 
		   System.out.println("The productrelease: "+idproductrelease);
         
		   ProductRelease pr = prDao.getProductReleaseByIdproductrelease(idproductrelease);
		  // ComponentPackage comp_package =  packageDao.getComponentPackageByIdpackage(idpackage);
         
		   String csvheader="source,target,value,idparentfeature,idcoreasset,idproductrelease,idpackage";
		   String csvContent  = computeForCustomizationsForPRassetsToFeaturesChurn(pr, null);
		  
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvheader+csvContent);//path and test// + csvInitialPaths
		  
		   model.addAttribute("idproductrelease",idproductrelease);
		//   model.addAttribute("idpackage",idpackage);
		   model.addAttribute("from",from);
		   
		   model.addAttribute("maintitle", "Which  assets is "+pr.getName()+" customizing?");
		   model.addAttribute("difftitle", "diff(Baseline-v1.0, "+pr.getName()+")");
		  
		   customs.utils.NavigationMapGenerator.generateNavigationMapForProductSide("features","core-asset","Expression",pr.getName());
		   
		   return "alluvials/diff_parent_features_all_assets"; 
	 	}
	 
	 
	 
	private String computeForCustomizationsForPRassetsToFeaturesChurn(ProductRelease pr,
			ComponentPackage comp_package) {
		  
		String csvCustoms="";
			  
		   System.out.println("The productrelease to analyze is: "+pr.getName());
		   Iterable<Churn_ParentFeatures_PackageAssets>  customizedAssets = parentFeaturesPackagesDao.findAll();
		   Iterator<Churn_ParentFeatures_PackageAssets> it= customizedAssets.iterator();
		   
		   Churn_ParentFeatures_PackageAssets custo;
		   ArrayList<Integer> listcustomizedAssets = new ArrayList<Integer>();
		   int idpack = -1;
		   if (comp_package!=null)
			   comp_package.getIdpackage();
		   while(it.hasNext()) {//getting lines for Customized assets in the component
			   custo = it.next();
			   if( (custo.getIdpackage()==idpack || idpack==-1) && custo.getIdproductrelease()==pr.getId_productrelease()) {
				   listcustomizedAssets.add(custo.getIdcoreasset());//source,target,value,idparentfeature,idasset,idproductrelease,idpackage
				   
				   csvCustoms = csvCustoms.concat("\n"+custo.getParentfeaturename()+","+custo.getCaname()+","
						   +custo.getChurn()+","+custo.getId_parentfeature()+","+custo.getIdcoreasset() 
						   +","+custo.getIdproductrelease()+","+custo.getIdpackage()); 
			   }
		   }
		   csvCustoms = addNotCustomizedComponentPackagesTotheCSV(listcustomizedAssets,csvCustoms,pr.getId_productrelease(),comp_package);
		  //csvCustoms= 	addNewAssetsTotheCSV(csvCustoms,pr.getName());
		return csvCustoms;
	}

	private String addNotCustomizedComponentPackagesTotheCSV(ArrayList<Integer> listcustomizedAssets, String csvContent, int id_productrelease, ComponentPackage comp_package) {
		Iterator<CoreAsset> it = caDao.findAll().iterator();
		System.out.println(it.toString());
		CoreAsset ca;
		ArrayList<Integer> listaddedAssets = new ArrayList<Integer>();
		int idpack=-1;
		if (comp_package!=null)
		 idpack=comp_package.getIdpackage();
		while (it.hasNext()) {
			ca = it.next();
			if (!listcustomizedAssets.contains(ca.getIdcoreasset()) 
					
					&& (ca.getIdpackage()==idpack || idpack==-1)
					&& (listaddedAssets.contains(ca.getIdcoreasset()))
				) {
				csvContent = csvContent.concat("\nNOT_CUSTOMIZED,"+ca.getName()+",0.01,");
				listaddedAssets.add(ca.getIdcoreasset());
			}
		}
		
		return csvContent;
}


}
