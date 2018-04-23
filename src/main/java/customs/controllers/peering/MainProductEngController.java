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
	
	@RequestMapping("/peering")
	public String getPeeringForProduct(
			 @RequestParam(value="productbranch", required=true) String branchName, Model model) {
		//source, target, value
		ArrayList<String> featuresReusedByProduct = new ArrayList<String>();
		
		System.out.println("HA ENTRADO!!");
		Iterable<ProductRelease> products = prDao.findAll();
		Iterator<ProductRelease> ite = products.iterator();
		ProductRelease p = null;
		while (ite.hasNext()) {
			p = ite.next();
			if ((p.getName().split("-")[0]).equals(branchName))
				break;
			else p = null;
		}
		if (p ==null) return null;
		//ProductRelease mainProduct = prDao.getProductReleaseByIdproductrelease(idproductRelease);
		System.out.println("The peering product is: "+p.getName());
		

		Iterator<Churn_PoductPortfolioAndFeatures> it = pp_by_feature.findAll().iterator();		
		Churn_PoductPortfolioAndFeatures observer; // Know which features is the product customizing !!
		String pname = p.getName();
		if(p.getName().contains("-"))
			 pname=(p.getName()).split("-")[0];
		else
		  pname=p.getName();
		
		String csvCustoms="source,target,value";
		int churn = 0;
		String featuremodified ="";
		while(it.hasNext()) {
			observer = it.next();
			if(observer.getId_pr() == p.getId_productrelease()) {
				if(! observer.getId_feature().equals("No Feature")) {
					featuresReusedByProduct.add(observer.getId_feature());
					System.out.println(observer.toString());
					featuremodified = observer.getFeaturemodified();
					churn = observer.getChurn() ;
					csvCustoms=csvCustoms.concat( "\n" +pname+ ","+featuremodified + "," +churn);
				}
			}
		}
			
		String pr_name="";
			Iterator<Churn_PoductPortfolioAndFeatures> iterator = pp_by_feature.findAll().iterator();
			Churn_PoductPortfolioAndFeatures peers;
			while(iterator.hasNext()) {	
				peers = iterator.next();//observer.getId_pr()!=p.getId_productrelease() &&
				
				if( featuresReusedByProduct.contains(peers.getFeaturemodified()) && (peers.getId_pr()!=p.getId_productrelease())){//
					featuremodified = peers.getFeaturemodified();
					churn = peers.getChurn() ;
					pr_name = peers.getPr_name();
					if (pr_name.contains("-"))
						pr_name = (pr_name.split("-"))[0];
					csvCustoms=csvCustoms.concat("\n"+ featuremodified + "," + pr_name + ","+ churn);
				}
			}		

		customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvCustoms);//path and test
		
		System.out.println(csvCustoms);
		System.out.println("Before returning the HTML");
		return "alluvials/peering-alluvial";
	} 		
		
}
