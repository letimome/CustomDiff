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
import customs.models.CoreassetsAndFeatures;
import customs.models.CoreassetsAndFeaturesDao;
import customs.models.CustomsForCaPaFeature;
import customs.models.CustomsForCaPaFeatureDao;


@Controller
public class Alluvial_Feature_Product_Obsolete {

	 @Autowired private CustomsForCaPaFeatureDao customscapaDao;
	 @Autowired private CoreassetsAndFeaturesDao coreassetsForFeature;
	 @Autowired private Churn_CoreAssetsAndFeaturesByPRDao customstoCAandFeatures;

		 
	   private String pathToResource = "./src/main/resources/static/";
	
	 @RequestMapping("diff_feature_product")
	   public String getDiffFeatureProduct(@RequestParam(value="fname", required=false) String featurenamemodified,
								@RequestParam(value="pr", required=false) String pr,
								Model model){
			
		 	System.out.println("THIS IS featurenamemodified: "+featurenamemodified);
			
			
			String csvContent = extractCSVForAlluvialFeatureProduct( featurenamemodified, pr);
			customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvContent);//path and test

			model.addAttribute("pr",pr);
			model.addAttribute("fname",featurenamemodified);
			model.addAttribute("maintitle", "How is feature '"+featurenamemodified+"' being customized in product "+pr+"?");
			model.addAttribute("difftitle", "diff(Feature: '" +featurenamemodified+"', "+pr+")");
			
			customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureProduct(featurenamemodified,pr,"core-asset","Expression");
					 	 
			return "/alluvials/diff_ca_pa";
	 }



	private String extractCSVForAlluvialFeatureProduct(String featurenamemodified, String pr) {
		String customsCSV = "source,target,value,pr,fname,id_ca,id_pa";
		
		Iterable<Churn_CoreAssetsAndFeaturesByPR> all = customstoCAandFeatures.findAll();//findCustomsByFeaturemodifiedAndIdbaseline(featurenamemodified,idbaseline);
		System.out.println(all.toString());
		
		Iterator<Churn_CoreAssetsAndFeaturesByPR> it = all.iterator();
		Churn_CoreAssetsAndFeaturesByPR custom;
		ArrayList<String> featureCAsCustomized = new ArrayList<String>();
		
		while (it.hasNext()) {
			custom = it.next();	
			if( (custom.getIdfeature().equals(featurenamemodified))	&& (custom.getPr_name().equals(pr))){ 
				featureCAsCustomized.add(custom.getCa_path());
				customsCSV = customsCSV.concat("\n"+custom.getCa_name()+" ["+featurenamemodified+"],"+custom.getCa_name()+","+custom.getChurn()
				+","+custom.getPr_name()+","+custom.getIdfeature()+","+custom.getId_coreasset()+","+custom.getId_coreasset());
			}
		}
		//adding not customized Feature's Core assets
		customsCSV = customsCSV.concat(getCSVForNotCustomizedCAsForFeature(featurenamemodified,featureCAsCustomized));
		
		//adding newly created assets in the product to that feature!
		customsCSV =customsCSV.concat(getCSVForNewlyCreatedProductAssetsForFeature());
		
		System.out.println(customsCSV);
		return customsCSV;
	}

	private String getCSVForNotCustomizedCAsForFeature(String featurenamemodified, ArrayList<String> featureCAsCustomized) {
		//String csvheader = source,target,value,pr,fname,id_ca,id_pa;
		String csvContent="";
		Iterator<CoreassetsAndFeatures> it = coreassetsForFeature.findAll().iterator();
		System.out.println(it.toString());
		CoreassetsAndFeatures caf;
		
		
		while (it.hasNext()) {
			caf = it.next();
			//caf.getIdcoreasset();
			if ((caf.getId_feature().equals(featurenamemodified)) &&(!featureCAsCustomized.contains(caf.getCa_path()))) {
				csvContent = csvContent.concat("\n"+caf.getCa_name()+",NOT_CUSTOMIZED,0.1");
			}
		}
		System.out.println("In getCSVForNotCustomizedCAsForFeature");
		System.out.println("In csvContent");
		return csvContent;
	}

	private String getCSVForNewlyCreatedProductAssetsForFeature() {
		String csvContent="";
		
		//csvContent = "\nNEW_ASSET,properties.js,2";
		
		return csvContent;
	}


}
