package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface TreeMapLightsDao extends CrudRepository<TreeMapLights, Long>{
	
	Iterable<TreeMapLights>  getCustomsByIdbaseline(String idBaseline);
	Iterable<TreeMapLights>  getCustomsByFeaturemodified(String featuremodified);
	
}
