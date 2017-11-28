package customs.controllers.simple;


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
import customs.models.ComponentPackage;
import customs.models.ComponentPackageDao;
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
	@Autowired private ComponentPackageDao packDao;
	@Autowired private Churn_CoreAssetsAndFeaturesByPRDao customstoCAAndFeatures;
	 private String pathToResource = "./src/main/resources/static/";

	 @RequestMapping("simple_diff_features_product_assets")
	   public String getTreeMapForProductRelease
	   (@RequestParam(value="idproductrelease", required=false) int idproductrelease,
	    @RequestParam(value="filter", required=false) String filter,
	   				Model model){
		 
		   ArrayList<String> componentsToInclude ;
		   if (filter==null || filter.equals("undefined"))   componentsToInclude=null;
		   else {
			   componentsToInclude = customs.utils.Formatting.stringToArrayList(filter, ",");
			   System.out.println(componentsToInclude.toString());
		   }

		   String csvheader="source,target,id,value,idproductrelease,id_pa,idcoreasset,idfeature";//TODO remove id_pa!!
		   ProductRelease pr = prDao.getProductReleaseByIdproductrelease(idproductrelease);
		
		   String csvContent  = computeForCustomizationsForPRassetsToFeaturesChurn(pr, componentsToInclude);
		  
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvheader+csvContent);//path and test// + csvInitialPaths
		  
		   model.addAttribute("pr",pr.getName());
		   model.addAttribute("maintitle", "Which assets are customized by '"+pr.getName()+"'?");
		   model.addAttribute("difftitle", "diff(Platform, "+pr.getName()+")");
		   model.addAttribute("idproductrelease",idproductrelease);
		   customs.utils.NavigationMapGenerator.generateNavigationMapForProductSide("features","core-asset","Expression",pr.getName());
		   
		   return "alluvials/simple_diff_features_product_assets"; 
	 	}

	private String computeForCustomizationsForPRassetsToFeaturesChurn(ProductRelease pr, ArrayList<String> componentsToInclude) {
		//	  String csvheader="source,target,id,value,pr,id_pa,id_ca,fname";
		   String csvCustoms="";
	
		   System.out.println("The productrelease to analyze is: "+pr.getName());
		   Iterable<Churn_CoreAssetsAndFeaturesByPR>  customizedAssets = customstoCAAndFeatures.getCustomsByIdproductrelease(pr.getId_productrelease());
		   Iterator<Churn_CoreAssetsAndFeaturesByPR> it= customizedAssets.iterator();
		   
		   Churn_CoreAssetsAndFeaturesByPR custo;
		   ArrayList<String> listcustomizedCas = new ArrayList<String>();
		  String append="", newCA="";
		  CoreAsset ca;
		  
		  
		   while(it.hasNext()) {//getting lines for Customized Assets
			   custo = it.next();
			   ca = caDao.getCoreAssetByIdcoreasset(custo.getId_coreasset());
			   
			   ComponentPackage cp =packDao.getComponentPackageByIdpackage(ca.getIdpackage());
			   if (componentsToInclude!=null && (! componentsToInclude.contains(cp.getName())))
				   continue;
			   if(ca.getIsnewasset()==1) newCA=" [NEW]";
			   else newCA= "";
			   Feature feature = fDao.getFeatureByName(custo.getIdfeature());
			   
			   //  if (feature.getIsNew()==1) append=" [NEW]"; 
			   //else append="";
			   listcustomizedCas.add(custo.getCa_path());
			   
			   csvCustoms = csvCustoms.concat("\n" +custo.getIdfeature()+","+custo.getCa_name()+newCA+","
					   +custo.getCa_path()+","+custo.getChurn()+","+custo.getIdproductrelease()+","+custo.getId_coreasset()
					   +","+custo.getId_coreasset()+","+custo.getIdfeature()); 
		   }
		   
		   csvCustoms = addNotCustomizedCoreAssetsTotheCSV(listcustomizedCas,csvCustoms,pr.getName(),  componentsToInclude);
		 //  csvCustoms= 	addNewProductAssetsTotheCSV(csvCustoms,pr.getName());
		return csvCustoms;
		
	}
	
	//GET CORE ASSETS THAT HAVE NOT BEEN CUSTOMIZED BY THE PRODUCT!! 
	private String addNotCustomizedCoreAssetsTotheCSV(ArrayList<String> listcustomizedCas, String csvContent,String productrelease, ArrayList<String> componentsToInclude) {
		//String csvheader = source,target,id,value,pr,id_pa,id_ca,fname";
		System.out.println("addNotCustomizedCoreAssetsTotheCSV");
		Iterator<CoreAsset> it = caDao.findAll().iterator();
		System.out.println(it.toString());
		CoreAsset ca;
		
		String append="";
		while (it.hasNext()) {
			ca = it.next();
			
			 ComponentPackage cp =packDao.getComponentPackageByIdpackage(ca.getIdpackage());
			   if (componentsToInclude !=null && (!componentsToInclude.contains(cp.getName())))
				   continue;
			
			if (!listcustomizedCas.contains(ca.getPath())) {
				csvContent = csvContent.concat("\nNOT_CUSTOMIZED,"+ca.getName()+","+ca.getPath()+",0.01,"+productrelease);
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
				//csvContent = csvContent.concat("\nNEW_ASSET,"+ca.getAsset_name()+","+ca.getPath()+",0.2,"+productrelease);
			}
		}
		return csvContent;
	}


}
