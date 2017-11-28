package customs.controllers.simple;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.Churn_CoreAssetsAndFeaturesByPR;
import customs.models.Churn_CoreAssetsAndFeaturesByPRDao;
import customs.models.Churn_Features_ComponentPackagesDao;
import customs.models.ComponentPackageDao;
import customs.models.CoreAsset;
import customs.models.CoreAssetDao;
import customs.models.CoreassetsAndFeatures;
import customs.models.CoreassetsAndFeaturesDao;
import customs.models.Feature;
import customs.models.FeatureDao;
import customs.models.ProductRelease;
import customs.models.ProductReleaseDao;
@Controller
public class Alluvial_Coressets_Productassets_Controller {

	

	@Autowired private Churn_Features_ComponentPackagesDao featuresPackagesDao;
	@Autowired private ProductReleaseDao prDao;
	@Autowired private CoreassetsAndFeaturesDao caFeaturesDao;
	@Autowired private FeatureDao fDao;
	@Autowired private CoreAssetDao caDao;
	@Autowired private ComponentPackageDao componentDao;
	@Autowired private Churn_CoreAssetsAndFeaturesByPRDao churnCAforPrDao;
	private String lastFrom=null;
	private String pathToResource = "./src/main/resources/static/";
	
	
	 @RequestMapping("simple_diff_coreassets_productassets")
	   public String getAlluvialForCaPa(
			   @RequestParam(value="idproductrelease", required=true) int idproductrelease, 
			   @RequestParam(value="idfeature", required=true) String idfeature, 
			   @RequestParam(value="from", required=false) String from, 
			   Model model){

			if(from==null) 
				from = lastFrom;
		   lastFrom = from;
		   ProductRelease pr = prDao.getProductReleaseByIdproductrelease(idproductrelease);
		   Feature f = fDao.getFeatureByIdfeature(idfeature);

		   model.addAttribute("pr",pr.getName());
		   model.addAttribute("idproductrelease",idproductrelease);
		   model.addAttribute("idfeature",idfeature);
		   model.addAttribute("from",from);
		   //model.addAttribute("idpackage",idpackage);
		   model.addAttribute("idparentfeature",f.getIdparent());
		   model.addAttribute("maintitle", "Which "+f.getName()+"'s core assets are customized by '"+pr.getName()+"'?");
		   
		   String csvheader="source,target,value,idcoreasset,idpackage,idfeature,idproductrelease";
		   String csvContent  = computeCustomizationsForPRFeatureAssets(pr,f);
		   
		   customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureProduct(f.getName(), pr.getName(), "core-assets", "Expression");
		   
		  // customs.utils.NavigationMapGenerator.generateNavigationMapForProductSide(f.getName(),"core-asset","Expression",pr.getName());
		   
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvheader+csvContent);//path and test// + csvInitialPaths
		   
		   return "alluvials/simple_diff_coreassets_productassets"; 
	 	}
	 
	 
	 
	private String computeCustomizationsForPRFeatureAssets(ProductRelease pr, Feature f) {
		// String csvheader="source,target,value,idcoreasset,idpackage,idfeature,idproductrelease";
		String csvContent= "";
		CoreAsset ca;
		ArrayList<Integer> listOfidcoreasset = new ArrayList<Integer>();
		Iterator<Churn_CoreAssetsAndFeaturesByPR> it = churnCAforPrDao.getCustomsByIdfeature(f.getIdfeature()).iterator();
		Churn_CoreAssetsAndFeaturesByPR custom;
		while (it.hasNext()) {
			custom = it.next();
			if(custom.getIdproductrelease()==pr.getId_productrelease()) {
				ca=caDao.getCoreAssetByIdcoreasset(custom.getId_coreasset());
				listOfidcoreasset.add(custom.getId_coreasset());
				csvContent= csvContent.concat("\n"+custom.getCa_name()+"  ["+f.getIdfeature()+"],"
				+custom.getCa_name()+","
						+custom.getChurn()+","+custom.getId_coreasset()+","+ca.getIdpackage()+","+custom.getIdfeature()+","+custom.getIdproductrelease());
			}
				
		}
		csvContent= csvContent + computeCSVForNotCustomizedFeatureAssets(f,listOfidcoreasset);
		return csvContent;
	}
	
	
	private String computeCSVForNotCustomizedFeatureAssets(Feature f, ArrayList<Integer> listOfidcoreasset) {
		String csvContent="";
		Iterator<CoreassetsAndFeatures> it = caFeaturesDao.getFeatureCoreAssetsByIdfeature(f.getIdfeature()).iterator();
		CoreassetsAndFeatures caf;
		ArrayList<Integer> listassets = new ArrayList<Integer>();
		while(it.hasNext()) {
			caf = it.next();
			if(!listOfidcoreasset.contains(caf.getId_coreasset()) && (!listassets.contains(caf.getId_coreasset()))) {
				listassets.add(caf.getId_coreasset());
				csvContent = csvContent.concat("\n"+caf.getCaname()+",NOT_CUSTOMIZED,0.01");
			}
				
		}
		return csvContent;
	}
}
