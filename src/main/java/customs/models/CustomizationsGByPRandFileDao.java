package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface CustomizationsGByPRandFileDao extends CrudRepository<CustomizationsGByPRandFile, Long>{

	CustomizationsGByPRandFile  getCustomsByIdcoreasset(int idcoreasset);
	Iterable<CustomizationsGByPRandFile> getCustomsByIdrelease(String productrelease);
	
	
}
