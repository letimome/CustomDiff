package customs.controllers.alluvial;


import java.util.ArrayList;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import customs.models.CoreAsset;
import customs.models.CoreAssetDao;
import customs.models.CoreassetsAndFeatures;
import customs.models.Feature;
import customs.models.FeatureDao;
import customs.models.Churn_CoreAssetsAndFeaturesByPR;
import customs.models.Churn_CoreAssetsAndFeaturesByPRDao;
import customs.models.Churn_PoductPortfolioAndFeatures;
import customs.models.Churn_PoductPortfolioAndFeaturesDao;
import customs.models.NewProductAsset;
import customs.models.NewProductAssetDao;
import customs.models.ProductRelease;
import customs.models.ProductReleaseDao;



@Controller
public class Alluvial_Platform_Product_Controller {
	
	@Autowired private NewProductAssetDao paDao;
	 @Autowired private FeatureDao fDao;
	@Autowired private CoreAssetDao caDao;
	@Autowired private ProductReleaseDao prDao;
	 @Autowired private Churn_CoreAssetsAndFeaturesByPRDao customstoCAAndFeatures;
	 private String pathToResource = "./src/main/resources/static/";

	 @RequestMapping("diff_platform_product")
	   public String getTreeMapForProductRelease(@RequestParam(value="pr", required=false) String productrelease,
	   				Model model){
		 
		   System.out.println("The productrelease: "+productrelease);

		   String csvheader="source,target,id,value,pr,id_pa,id_ca,fname";//TODO remove id_pa!!
		   
		   String csvContent  = computeForCustomizationsForPRassetsToFeaturesChurn(productrelease);
		  
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvheader+csvContent);//path and test// + csvInitialPaths
		  
		   model.addAttribute("pr",productrelease);
		   model.addAttribute("maintitle", "Which assets are customized by '"+productrelease+"'?");
		   model.addAttribute("difftitle", "diff(Baseline-v1.0, "+productrelease+")");
		  
		   customs.utils.NavigationMapGenerator.generateNavigationMapForProductSide("features","core-asset","Expression",productrelease);
		   
		   return "alluvials/diff_platform_product"; 
	 	}

	private String computeForCustomizationsForPRassetsToFeaturesChurn(String productrelease) {
		//	  String csvheader="source,target,id,value,pr,id_pa,id_ca,fname";
		   String csvCustoms="";
		   Iterable<ProductRelease> prs = prDao.getProductReleaseByName(productrelease);
		   ProductRelease pr=prs.iterator().next();
		   System.out.println("The productrelease to analyze is: "+pr.getName());
		   Iterable<Churn_CoreAssetsAndFeaturesByPR>  customizedAssets = customstoCAAndFeatures.getCustomsByIdproductrelease(pr.getId_product());
		   Iterator<Churn_CoreAssetsAndFeaturesByPR> it= customizedAssets.iterator();
		   
		   Churn_CoreAssetsAndFeaturesByPR custo;
		   ArrayList<String> listcustomizedCas = new ArrayList<String>();
		  String append="";
		   while(it.hasNext()) {//getting lines for Customized Assets
			   custo = it.next();
			   
			   Feature feature = fDao.getFeatureByName(custo.getIdfeature());
			   if (feature.getIsNew()==1) append=" [NEW]"; 
			   else append="";
			   listcustomizedCas.add(custo.getCa_path());
			   csvCustoms = csvCustoms.concat("\n" +custo.getIdfeature()+append+","+custo.getCa_name()+","
					   +custo.getCa_path()+","+custo.getChurn()+","+custo.getPr_name()+","+custo.getId_coreasset()
					   +","+custo.getId_coreasset()+","+custo.getIdfeature()); 
		   }
		   
		   csvCustoms = addNotCustomizedCoreAssetsTotheCSV(listcustomizedCas,csvCustoms,productrelease);
		   csvCustoms= 	addNewProductAssetsTotheCSV(csvCustoms,productrelease);
		return csvCustoms;
		
	}
	
	//GET CORE ASSETS THAT HAVE NOT BEEN CUSTOMIZED BY THE PRODUCT!! 
	private String addNotCustomizedCoreAssetsTotheCSV(ArrayList<String> listcustomizedCas, String csvContent,String productrelease) {
		//String csvheader = source,target,id,value,pr,id_pa,id_ca,fname";
		System.out.println("addNotCustomizedCoreAssetsTotheCSV");
		Iterator<CoreAsset> it = caDao.findAll().iterator();
		System.out.println(it.toString());
		CoreAsset ca;
		
		
		while (it.hasNext()) {
			ca = it.next();
			if (!listcustomizedCas.contains(ca.getPath())) {
				csvContent = csvContent.concat("\nNOT_CUSTOMIZED,"+ca.getName()+","+ca.getPath()+",0.2,"+productrelease);
			}
		}
		
		return csvContent;
	}
	  
	

//GET NEW ASSETS IN PRODUCTS!!
	private String addNewProductAssetsTotheCSV(String csvContent,String productrelease) {
		//String csvheader = source,target,id,value,pr,id_pa,id_ca,fname";
		System.out.println("addNewProductAssetsTotheCSV");
		Iterator<NewProductAsset> it = paDao.findAll().iterator();
		
		System.out.println(it.toString());
		NewProductAsset ca;
		
		while (it.hasNext()) {
			ca = it.next();
			if (ca.getPr_name().equals(productrelease)) {
				csvContent = csvContent.concat("\nNEW_ASSET,"+ca.getAsset_name()+","+ca.getPath()+",0.2,"+productrelease);
			}
		}
		return csvContent;
	}


}
