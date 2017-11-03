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
import customs.models.Churn_Features_PackageAssets;
import customs.models.Churn_Features_PackageAssetsDao;
import customs.models.Churn_ParentFeatures_PackageAssets;
import customs.models.ComponentPackage;
import customs.models.ComponentPackageDao;
import customs.models.CoreAsset;
import customs.models.CoreAssetDao;
import customs.models.ParentFeature;
import customs.models.ParentFeatureDao;
import customs.models.ProductRelease;
import customs.models.ProductReleaseDao;



@Controller
public class Alluvial_Features_PackageAssets {
	
	private String pathToResource = "./src/main/resources/static/";
	 @Autowired private ProductReleaseDao prDao;
	 @Autowired private ComponentPackageDao packageDao;
	 @Autowired private Churn_Features_PackageAssetsDao churnFeaturesPackageAssetsDao;
	 @Autowired private ParentFeatureDao parentFeatureDao;
	 @Autowired private CoreAssetDao caDao;
	
	 private String lastFrom=null;
	
	@RequestMapping("diff_features_package_assets")
	   public String getDiffFeatureProduct(@RequestParam(value="idparentfeature", required=true) int idparentfeature,
								@RequestParam(value="idproductrelease", required=true) int  idproductrelease, 
								@RequestParam(value="idpackage", required=true) int  idpackage, 
								@RequestParam(value="from", required=false) String  from, 
								Model model){
			
		 	ParentFeature parentFeature = parentFeatureDao.getParentFeatureByIdparentfeature(idparentfeature);
			ProductRelease pr = prDao.getProductReleaseByIdproductrelease(idproductrelease);
			
			if (from==null || from.equals("undefined"))
					from=lastFrom;
			else lastFrom=from;
			
			String csvContent = extractCSVForAlluvialFeaturesPackageAssets( parentFeature, pr, idpackage);
			customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvContent);//path and test

			model.addAttribute("idparentfeature",idparentfeature);
			model.addAttribute("idproductrelease",idproductrelease);
			model.addAttribute("idpackage",idpackage);
			model.addAttribute("from",from);
			model.addAttribute("pr",pr);
			model.addAttribute("fname",parentFeature.getName());
			model.addAttribute("maintitle", "How is parent-feature '"+parentFeature.getName()+"' being customized in '"+pr.getName()+"'?");
			
			customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureProduct(parentFeature.getName(),pr.getName(),"core-asset","Expression");
					 	 
			return "/alluvials/diff_features_package_assets";
	 }


	private String extractCSVForAlluvialFeaturesPackageAssets(ParentFeature parentFeature, ProductRelease pr, int idpackage) {
		String csvCustoms="source,target,value,idfeature,idcoreasset,idproductrelease,idpackage";
		  
		System.out.println("The productrelease to analyze is: "+pr.getName());
		   Iterable<Churn_Features_PackageAssets>  customizedPackages = churnFeaturesPackageAssetsDao.findAll();
		   Iterator<Churn_Features_PackageAssets> it= customizedPackages.iterator();
		   
		   Churn_Features_PackageAssets custo;
		   ArrayList<Integer> listcustomizedAssets = new ArrayList<Integer>();

		   while(it.hasNext()) {//getting lines for Customized components
			   custo = it.next();
			   if(custo.getIdparentfeature()==parentFeature.getIdparentFeature() 
					   && custo.getIdproductrelease()==pr.getId_productrelease()
					   && custo.getIdpackage()==idpackage) {
				   
				   listcustomizedAssets.add(custo.getIdcoreasset());
				   
				   csvCustoms = csvCustoms.concat("\n" +custo.getFeaturename()+","+custo.getCaname()+","
						   +custo.getChurn()+","+custo.getIdfeature() +","+custo.getIdcoreasset()+","+custo.getIdproductrelease()+","+custo.getIdpackage()); 
				   
				 //   csvCustoms = addNotCustomizedAssetsTotheCSV(listcustomizedAssets,csvCustoms,pr);
				 //  csvCustoms= 	addNewAssetsTotheCSV(csvCustoms,pr.getName());
			   }
		   }
		  
		return csvCustoms;
	}


	
	
	
	
	
}
