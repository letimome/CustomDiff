package customs.controllers.peering;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import customs.models.ProductReleaseDao;
import customs.models.ProductRelease;


@RestController
@RequestMapping("/{productName}/peerings")
public class PeeringRestController {
	
	@Autowired private ProductReleaseDao prDao;
	
	@RequestMapping(method = RequestMethod.GET)
	ProductRelease readProduct(@PathVariable String productName) {
		this.findProductByName(productName);
		ProductRelease pr=findProductByName(productName);
		return prDao.getProductReleaseByIdproductrelease(pr.getId_productrelease());
	}


	private ProductRelease findProductByName(String productName) {
		Iterable<ProductRelease> products = prDao.getProductReleaseByName(productName);
		Iterator<ProductRelease> iter = products.iterator();
		ProductRelease mainProduct = null;
		while(iter.hasNext()) {
			mainProduct=iter.next();
			if (!mainProduct.getName().equals(productName))
				mainProduct=null;
			else 
				return mainProduct;
		}
		return mainProduct;
	}
}
