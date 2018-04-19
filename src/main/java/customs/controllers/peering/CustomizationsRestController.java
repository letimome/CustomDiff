package customs.controllers.peering;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import customs.models.Churn_PoductPortfolioAndFeaturesDao;
import customs.models.Churn_PoductPortfolioAndFeatures;


@RestController
//@RequestMapping("/customizations")
public class CustomizationsRestController {
	@Autowired private Churn_PoductPortfolioAndFeaturesDao customsDao;
	
	@RequestMapping(value="/customizations", method = RequestMethod.GET, produces = "Accept=application/json")
	Iterable<Churn_PoductPortfolioAndFeatures> readAllCustomizations() {
		return customsDao.findAll();
	}
	
	@RequestMapping(value="/customizations/product/{productName}", method = RequestMethod.GET)
	Iterable<Churn_PoductPortfolioAndFeatures> readCustomizationsForProduct(@PathVariable String productName) {
		Iterable<Churn_PoductPortfolioAndFeatures> customs = customsDao.findAll();
		
		ArrayList<Churn_PoductPortfolioAndFeatures> list = new ArrayList<Churn_PoductPortfolioAndFeatures>();

		Iterator<Churn_PoductPortfolioAndFeatures> it = customs.iterator();
		Churn_PoductPortfolioAndFeatures c;
		while (it.hasNext()) {
			c = it.next();
			if(c.getPr_name().equals(productName))
				list.add(c);
		}
		System.out.println(list.size());
		return list;
	}
	
	@RequestMapping(value="/customizations/feature/{featureName}", method = RequestMethod.GET)
	Iterable<Churn_PoductPortfolioAndFeatures> readCustomizationsForFeatures(@PathVariable String featureName) {
		Iterable<Churn_PoductPortfolioAndFeatures> customs = customsDao.findAll();
		
		ArrayList<Churn_PoductPortfolioAndFeatures> list = new ArrayList<Churn_PoductPortfolioAndFeatures>();

		Iterator<Churn_PoductPortfolioAndFeatures> it = customs.iterator();
		Churn_PoductPortfolioAndFeatures c;
		while (it.hasNext()) {
			c = it.next();
			if(c.getId_feature().equals(featureName))
				list.add(c);
		}
		System.out.println(list.size());
		return list;
	}
}



	

