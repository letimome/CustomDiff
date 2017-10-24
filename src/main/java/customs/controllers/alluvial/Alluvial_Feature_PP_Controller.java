package customs.controllers.alluvial;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import customs.models.CoreassetsAndFeatures;
import customs.models.CoreassetsAndFeaturesDao;
import customs.models.Customs_of_product_to_feature_ca;
import customs.models.Customs_of_product_to_feature_caDao;



@Controller
public class Alluvial_Feature_PP_Controller {

	  @Autowired private CoreassetsAndFeaturesDao coreassetsForFeature;
	  @Autowired private Customs_of_product_to_feature_caDao customsPRtoCA;
	 
	  private String pathToResource = "./src/main/resources/static/";	
	   
	 @RequestMapping("diff_feature_pp")
	   public String getDiffFeaturePP(
	   				@RequestParam(value="base", required=false) String idbaseline,
	   				@RequestParam(value="fname", required=false) String featurenamemodified,
	   				Model model){
		  
		   System.out.println("THIS IS featurenamemodified: "+featurenamemodified);System.out.println("THIS IS idbaseline: "+idbaseline);
		   String csvContent= extractCSVForFeatureProductPortfolioChurn(featurenamemodified,idbaseline);
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvContent);//path and test
		  
		  
		   model.addAttribute("fname",featurenamemodified);
		   model.addAttribute("maintitle", "How are feature '"+featurenamemodified+"' assets customized by the product portfolio?");

		  customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureSide(featurenamemodified, "core-asset","Expression","product"); 
		  
		  return "alluvials/diff_feature_pp";
	  }
	
	 
		private String extractCSVForFeatureProductPortfolioChurn(String featurenamemodified, String idbaseline) {
			//String csvheader = "id,value,product_release,operation,fname";
			String csvheader = "source,target,value,pr,operation,fname,id_ca,id_pa";
			String csvcontent= extractCSVForFeatureProductPorfolioView(featurenamemodified,idbaseline);	
			return csvheader.concat(csvcontent);
		}


		private String extractCSVForFeatureProductPorfolioView(String featurenamemodified, String idbaseline) {
			Iterator<Customs_of_product_to_feature_ca> it = customsPRtoCA.findAll().iterator();
			Customs_of_product_to_feature_ca custom;
			ArrayList<String> listcustomizedCas= new ArrayList<String>();
			
			String csvContent ="";//"\n"+featurenamemodified+",";
			while (it.hasNext()) {
				custom = it.next();
				if(custom.getIdfeature().equals(featurenamemodified)) {
					listcustomizedCas.add(custom.getCa_path());
					csvContent=csvContent.concat("\n"+custom.getCa_name()+",").concat(custom.getPr_name()+","+custom.getChurn()+","
					+custom.getPr_name()+",churn,"+featurenamemodified+","+custom.getIdcoreasset()+","+custom.getId_coreasset());
				}
				/*csvContent=csvContent.concat("\n"+featurenamemodified+"/").concat(custom.getIdrelease()+","+custom.getChurn()+","+custom.getIdrelease()+",churn,"+featurenamemodified);
				}*/ 
			}
			csvContent = addNotCustomizedCoreAssetsTotheCSV(listcustomizedCas,csvContent, idbaseline,featurenamemodified);
			return csvContent;
		}


		private String addNotCustomizedCoreAssetsTotheCSV(ArrayList<String> listcustomizedCas, String csvContent, String idBaseline,String featurenamemodified) {
			//String csvheader = "source,target,value,product_release,operation,fname";
			System.out.println("addNotCustomizedCoreAssetsTotheCSV");
			Iterator<CoreassetsAndFeatures> it = coreassetsForFeature.findAll().iterator();
			System.out.println(it.toString());
			CoreassetsAndFeatures caf;
			
			
			while (it.hasNext()) {
				caf = it.next();
				if ((caf.getId_feature().equals(featurenamemodified)) &&(!listcustomizedCas.contains(caf.getCa_path()))) {
					csvContent = csvContent.concat("\n"+caf.getCa_name()+",NOT_CUSTOMIZED,0.1");
				}
			}
			
			return csvContent;
		}
		  
	
}
