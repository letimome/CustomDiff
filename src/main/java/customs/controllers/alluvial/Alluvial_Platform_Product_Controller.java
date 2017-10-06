package customs.controllers.alluvial;


import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import customs.models.CustomsByProductAssetsToFeatures;
import customs.models.CustomsByProductAssetsToFeaturesDao;



@Controller
public class Alluvial_Platform_Product_Controller {
	

	 @Autowired private CustomsByProductAssetsToFeaturesDao customsPAtoFeatures;
	 private String pathToResource = "./src/main/resources/static/";

	 @RequestMapping("diff_platform_product")
	   public String getTreeMapForProductRelease(@RequestParam(value="pr", required=false) String productrelease,
	   				Model model){
		 
		   System.out.println("The productrelease: "+productrelease);

		   String csvheader="source,target,id,value,operation,pr,p_asset_id";
		   
		   String csvContent  = computeForCustomizationsForPRassetsToFeaturesChurn(productrelease);
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvheader+csvContent);//path and test// + csvInitialPaths
		  
		   model.addAttribute("pr",productrelease);
		   model.addAttribute("maintitle", "Which assets are customized by '"+productrelease+"'?");
		   model.addAttribute("difftitle", "diff(Baseline-v1.0, "+productrelease+")");
		  
		   customs.utils.NavigationMapGenerator.generateNavigationMapForProductSide("features","core-asset","Expression",productrelease);
		   
		   return "alluvials/diff_platform_product"; 
	 	}

	private String computeForCustomizationsForPRassetsToFeaturesChurn(String productrelease) {
		//	   String csvheader="source,target,id,value,operation,pr,p_asset_id";
		   String csvCustoms="";
		   System.out.println("The productrelease to analyze is: "+productrelease);

		   Iterable<CustomsByProductAssetsToFeatures>  customizedAssets = customsPAtoFeatures.getCustomsByInproduct(productrelease);
		   Iterator<CustomsByProductAssetsToFeatures> it= customizedAssets.iterator();
		   CustomsByProductAssetsToFeatures custo;
		   
		   while(it.hasNext()) {//getting lines for Customized Assets
			   custo = it.next();
			   csvCustoms = csvCustoms.concat("\n" +custo.getFeaturechanged()+","+custo.getName()+","
					   +custo.getPath() + ","+custo.getChurn()+","+custo.getInproduct()+","+custo.getIdproductasset());
			   
		   }
		return csvCustoms;
		
	}

}
