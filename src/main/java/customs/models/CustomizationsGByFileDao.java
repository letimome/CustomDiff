package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface CustomizationsGByFileDao extends CrudRepository<CustomizationsGByFile, Long>{
	
	Iterable<CustomizationsGByFile>  getCustomsByIdbaseline(String idBaseline);
	//Iterable<TreeMapLights>  getCustomsByFeaturemodified(String featuremodified);
	CustomizationsGByFile  getCustomsByIdcoreasset(int idcoreasset);
	
	
}
