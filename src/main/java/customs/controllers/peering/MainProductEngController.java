package customs.controllers.peering;

import java.util.ArrayList;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import customs.models.Churn_PoductPortfolioAndFeatures;
import customs.models.Churn_PoductPortfolioAndFeaturesDao;
import customs.models.FeatureDao;
import customs.models.ProductReleaseDao;
import customs.models.ProductRelease;

@Controller
public class MainProductEngController {

	@Autowired private ProductReleaseDao prDao;
	@Autowired private FeatureDao fDao;
	@Autowired private Churn_PoductPortfolioAndFeaturesDao pp_by_feature;
	private String pathToResource = "./src/main/resources/static/";
	
	@RequestMapping("product_features_pp")
	public String getAlluvial_product_features_pp(
			 @RequestParam(value="idproductrelease", required=true) int idproductRelease, Model model) {
		//source, target, value
		ArrayList<String> featuresReusedByProduct = new ArrayList<String>();
		
		System.out.println("HA ENTRADO!!");
		ProductRelease mainProduct = prDao.getProductReleaseByIdproductrelease(idproductRelease);
		System.out.println(mainProduct.getName());
		String csvCustoms= "source,target,value";
		//get_features_for product_A
		//Which features is the product reusing
		csvCustoms = csvCustoms.concat ("\nproductDenmark,WindSpeed,0.1");
		csvCustoms = csvCustoms.concat ("\nproductDenmark,AirPressure,0.1" );
		csvCustoms = csvCustoms.concat ("\nproductDenmark,English,0.1" );
		csvCustoms = csvCustoms.concat ("\nproductDenmark,Temperature,0.1" );
		csvCustoms = csvCustoms.concat ("\nproductDenmark,Gale,5" );
		
		
		Iterator<Churn_PoductPortfolioAndFeatures> it = pp_by_feature.findAll().iterator();
		
		featuresReusedByProduct.add("WindSpeed");
		featuresReusedByProduct.add("AirPressure");
		featuresReusedByProduct.add("Temperature");
		featuresReusedByProduct.add("English");
		featuresReusedByProduct.add("Gale");
		
		Churn_PoductPortfolioAndFeatures churnByProduct;
		while(it.hasNext()) {
			churnByProduct = it.next();
			if(churnByProduct.getId_pr()==idproductRelease) {
				if(! churnByProduct.getId_feature().equals("No Feature")) {
					featuresReusedByProduct.add(churnByProduct.getId_feature());
					csvCustoms = csvCustoms.concat ("\n"+ mainProduct.getName()+","+churnByProduct.getFeaturemodified()+","+churnByProduct.getChurn());
				}
			}
			
		}
		
		//know which products have customized it
		Iterator<String> ite = featuresReusedByProduct.iterator();
		//csv source (the feature),target(other products),value (churn)
		String feature;
		Iterator<Churn_PoductPortfolioAndFeatures> iterator = pp_by_feature.findAll().iterator();
		while(ite.hasNext()) {
			feature=ite.next();
			while(iterator.hasNext()) {
				churnByProduct = iterator.next();
				if( churnByProduct.getId_pr()!=idproductRelease && churnByProduct.getFeaturemodified().equals(feature)){//
					
					csvCustoms = csvCustoms.concat ("\n"+churnByProduct.getFeaturemodified()+ 
							"," + prDao.getProductReleaseByIdproductrelease(
									churnByProduct.getId_pr()).getName()+","+
							churnByProduct.getChurn());
				}
			}
			
			
		}
		
		//draw products that reuse the features although not customized
		
		
		csvCustoms = csvCustoms.concat ("\nproductDenmark,English,2");
		csvCustoms = csvCustoms.concat ("\nproductDenmark,AirPressure,2" );
		csvCustoms = csvCustoms.concat ("\nproductDenmark,WindSpeed,8");
		csvCustoms = csvCustoms.concat ("\nproductDenmark,Temperature,2");
		csvCustoms = csvCustoms.concat ("\nproductDenmark,Heat,0.1");
		
		
		csvCustoms = csvCustoms.concat ("\nEnglish,productNewYork,4");
		csvCustoms = csvCustoms.concat ("\nWindSpeed,productNewYork,1");
		csvCustoms = csvCustoms.concat ("\nAirPressure,productDonosti,4");
		csvCustoms = csvCustoms.concat ("\nEnglish,productLondon,6");
		csvCustoms = csvCustoms.concat ("\nGale,productNewYork,6");
		csvCustoms = csvCustoms.concat ("\nAirPressure,productLondon,3");
		csvCustoms = csvCustoms.concat ("\nTemperature,productLondon,5");
		csvCustoms = csvCustoms.concat ("\nHeat,no_change,0.1" );
		
		
		
		customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvCustoms);//path and test
			return "alluvial-for-AE/main_product-features-otherProducts";
		//return csvCustoms;
	} 		
	
}
