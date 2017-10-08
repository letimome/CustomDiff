package customs.controllers.alluvial;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.AddedCustomsByProductsToFeaturesDao;
import customs.models.CoreAssetDao;
import customs.models.CoreassetsAndFeatures;
import customs.models.CoreassetsAndFeaturesDao;
import customs.models.CustomizationsByFeatureDao;
import customs.models.CustomizationsGByOperationDao;
import customs.models.CustomsForCaPaFeature;
import customs.models.CustomsForCaPaFeatureDao;
import customs.models.DeletedCustomsByProductsToFeaturesDao;
import customs.models.ProductAssetDao;
import customs.models.SPLdao;
import customs.models.VariationPointDao;

@Controller
public class Alluvial_Feature_Product {

	 @Autowired private CustomsForCaPaFeatureDao customscapaDao;
	  @Autowired private CoreAssetDao caDao;
	  @Autowired private CoreassetsAndFeaturesDao coreassetsForFeature;

		 
	   private String pathToResource = "./src/main/resources/static/";
	
	 @RequestMapping("diff_feature_product")
	   public String getDiffFeatureProduct(@RequestParam(value="base", required=false) String idbaseline,
								@RequestParam(value="fname", required=false) String featurenamemodified,
								@RequestParam(value="pr", required=false) String pr,
								Model model){
			
		 	System.out.println("THIS IS base: "+idbaseline);System.out.println("THIS IS fname: "+featurenamemodified);
			
			
			String csvContent = extractCSVForAlluvialFeatureProduct( featurenamemodified, pr, idbaseline);
			customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvContent);//path and test

			model.addAttribute("pr",pr);
			model.addAttribute("fname",featurenamemodified);
			model.addAttribute("maintitle", "How is feature '"+featurenamemodified+"' being customized in product "+pr+"?");
			model.addAttribute("difftitle", "diff(Feature: '" +featurenamemodified+"', "+pr+")");
			
			customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureProduct(featurenamemodified,pr,"core-asset","Expression");
					 	 
			return "/alluvials/diff_ca_pa";
	 }



	private String extractCSVForAlluvialFeatureProduct(String featurenamemodified, String pr, String idbaseline) {
		String customsCSV = "source,target,value,pr,fname,id_ca,id_pa";
		Iterable<CustomsForCaPaFeature> all = customscapaDao.findAll();//findCustomsByFeaturemodifiedAndIdbaseline(featurenamemodified,idbaseline);
		System.out.println(all.toString());
		
		Iterator<CustomsForCaPaFeature> it = all.iterator();
		CustomsForCaPaFeature custom;
		ArrayList<String> featureCAsCustomized = new ArrayList<String>();
		while (it.hasNext()) {
			
			custom = it.next();
			
			if( (custom.getFeaturemodified().equals(featurenamemodified))	&& (custom.getIdbaseline().equals(idbaseline)) && (custom.getP_release().equals(pr))){ 
				featureCAsCustomized.add(custom.getPath());
				customsCSV = customsCSV.concat("\n" +featurenamemodified+ "."+custom.getFilename()+","+pr+"."+custom.getFilename()+","+custom.getChurn()
				+","+custom.getP_release()+","+custom.getFeaturemodified()+","+custom.getId_ca()+","+custom.getId_pa());
			}
		}
		//adding not customized Feature's Core assets
		customsCSV = customsCSV.concat(getCSVForNotCustomizedCAsForFeature(idbaseline, featurenamemodified,featureCAsCustomized));
		
		//adding newly created assets fo the product
		customsCSV =customsCSV.concat(getCSVForNewlyCreatedProductAssetsForFeature());
		
		System.out.println(customsCSV);
		return customsCSV;
	}

	private String getCSVForNotCustomizedCAsForFeature(String idbaseline, String featurenamemodified, ArrayList<String> featureCAsCustomized) {
		//String csvheader = source,target,value,pr,fname,id_ca,id_pa;
		String csvContent="";
		Iterator<CoreassetsAndFeatures> it = coreassetsForFeature.findAll().iterator();
		System.out.println(it.toString());
		CoreassetsAndFeatures caf;
		
		
		while (it.hasNext()) {
			caf = it.next();
			//caf.getIdcoreasset();
			if (caf.getBaseline().equals(idbaseline) &&(caf.getFeatureid().equals(featurenamemodified)) &&(!featureCAsCustomized.contains(caf.getCapath()))) {
				csvContent = csvContent.concat("\n"+caf.getCaname()+",NOT_CUSTOMIZED,0.1");
			}
		}
		System.out.println("In getCSVForNotCustomizedCAsForFeature");
		System.out.println("In csvContent");
		return csvContent;
	}

	private String getCSVForNewlyCreatedProductAssetsForFeature() {
		String csvContent="";
		
		csvContent = "\nNEW_ASSET,properties.js,2";
		
		return csvContent;
	}


}
