package customs.controllers;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.AddedCustomsByProductsToFeaturesDao;
import customs.models.CoreAssetDao;
import customs.models.CoreassetsAndFeaturesDao;
import customs.models.CustomizationsByFeatureDao;
import customs.models.CustomizationsGByPRandFileDao;
import customs.models.CustomizationsProductFeature;
import customs.models.CustomizationsProductFeatureDao;
import customs.models.DeletedCustomsByProductsToFeaturesDao;
import customs.models.ProductAssetDao;
import customs.models.ProductReleaseDao;
import customs.models.SPLdao;
import customs.models.VariationPointDao;

@Controller
public class FeatureProductPortfolioController {
	  @Autowired private CustomizationsProductFeatureDao featureProductDao;
	  @Autowired private SPLdao SPLdao;
	  @Autowired private ProductAssetDao paDao;
	  @Autowired private ProductReleaseDao   prDao;

	   private String pathToResource = "./src/main/resources/static/";	
	   
	   
	  @RequestMapping("featureProductPortfolioView")
	   public String getTreeMapTrafficLight(
	   				@RequestParam(value="base", required=false) String idbaseline,
	   				@RequestParam(value="fname", required=false) String featurenamemodified,
	   				Model model){
		  
		   System.out.println("THIS IS featurenamemodified: "+featurenamemodified);System.out.println("THIS IS idbaseline: "+idbaseline);
		   String csvContent= extractCSVForFeatureProductPortfolioChurn(featurenamemodified,idbaseline);
		   customs.utils.FileUtils.writeToFile(pathToResource+"featureProductPortfolio.csv",csvContent);//path and test
		  
		  
		   model.addAttribute("fname",featurenamemodified);
		   model.addAttribute("maintitle", "How is feature '"+featurenamemodified+"' being customized in products?");

		  customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureSide(featurenamemodified, "core-asset","Expression","product"); 
		  
		  return "featureProductPortfolio";
	  }


	private String extractCSVForFeatureProductPortfolioChurn(String featurenamemodified, String idbaseline) {
		String csvheader = "id,value,product_release,operation,fname";
		String csvcontent= extractCSVForFeatureProductPorfolioView(featurenamemodified,idbaseline);	
		return csvheader.concat(csvcontent);
	}


	private String extractCSVForFeatureProductPorfolioView(String featurenamemodified, String idbaseline) {
		Iterator<CustomizationsProductFeature> it = featureProductDao.findAll().iterator();
		CustomizationsProductFeature custom;
		String csvContent ="\n"+featurenamemodified+",";
		while (it.hasNext()) {
			custom = it.next();
			if(custom.getBaseline().equals(idbaseline) && custom.getFeaturemodified().equals(featurenamemodified)) {
				csvContent=csvContent.concat("\n"+featurenamemodified+"/").concat(custom.getIdrelease()+","+custom.getChurn()+","+custom.getIdrelease()+",churn,"+featurenamemodified);
			} 
		}
		return csvContent;
	}
	  
	  
}
