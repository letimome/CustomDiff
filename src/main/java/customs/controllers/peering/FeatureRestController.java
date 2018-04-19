package customs.controllers.peering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import customs.models.Feature;
import customs.models.FeatureDao;

@RestController
@RequestMapping("/features")
public class FeatureRestController {
		
		@Autowired private FeatureDao fDao;
		
		@RequestMapping(method = RequestMethod.GET)
		Iterable<Feature> readProducts() {
			return fDao.findAll();
		}

}
