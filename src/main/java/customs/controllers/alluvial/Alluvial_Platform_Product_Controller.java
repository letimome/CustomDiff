package customs.controllers.alluvial;


import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.CoreAsset;
import customs.models.CoreAssetDao;
import customs.models.CustomsByProductAssetsToFeatures;
import customs.models.CustomsByProductAssetsToFeaturesDao;
import customs.models.NewProductAsset;
import customs.models.NewProductAssetDao;



@Controller
public class Alluvial_Platform_Product_Controller {
	
	@Autowired private NewProductAssetDao paDao;
	@Autowired private CoreAssetDao caDao;
	 @Autowired private CustomsByProductAssetsToFeaturesDao customsPAtoFeatures;
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
		//	  String csvheader="source,target,id,value,operation,pr,id_pa,id_ca,fname";
		   String csvCustoms="";
		   System.out.println("The productrelease to analyze is: "+productrelease);

		   Iterable<CustomsByProductAssetsToFeatures>  customizedAssets = customsPAtoFeatures.getCustomsByInproduct(productrelease);
		   Iterator<CustomsByProductAssetsToFeatures> it= customizedAssets.iterator();
		   CustomsByProductAssetsToFeatures custo;
		   
		   while(it.hasNext()) {//getting lines for Customized Assets
			   custo = it.next();
			   csvCustoms = csvCustoms.concat("\n" +custo.getFeaturechanged()+","+custo.getName()+","
					   +custo.getPath() + ","+custo.getChurn()+","+custo.getInproduct()+","+custo.getIdproductasset()
					   +","+custo.getIdproductasset()+","+custo.getFeaturechanged()); 
		   }
		return csvCustoms;
		
	}

	/*private int getCaIdFromPa(String pr, int idproductasset) {	
			
			//I need to get the absolute diff  that modifies the idcoreasset in release pr
			 System.out.println("diff for idproductasset: "+idproductasset); System.out.println("diff for pr: "+pr);
			 
			 CoreAsset ca=null;
			 NewProductAsset pa=null;
			 
			  pa = paDao.getProductAssetByIdproductasset(idproductasset) ;
			 
			  Iterator <CoreAsset> ite = caDao.findAll().iterator();
			 while(ite.hasNext()) {
				 ca=ite.next();
				 if(pa.getProductrelease_idrelease().equals(pr) && pa.getPath().equals(ca.getPath()))
					 break;
			 }
			 System.out.println();
			 return ca.getIdcoreasset();
	}*/

}
