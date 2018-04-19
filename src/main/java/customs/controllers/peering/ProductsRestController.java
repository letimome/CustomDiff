package customs.controllers.peering;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import customs.models.ProductRelease;
import customs.models.ProductReleaseDao;

@RestController
@RequestMapping("/products")
public class ProductsRestController {
	
	@Autowired private ProductReleaseDao prDao;
	
	@RequestMapping(method = RequestMethod.GET)
	Iterable<ProductRelease> readProducts() {
		return prDao.findAll();
	}



}
