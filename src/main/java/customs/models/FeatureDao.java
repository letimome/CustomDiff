package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;



@Transactional
public interface FeatureDao extends CrudRepository<Feature, Long> {

	Feature getFeatureByIdfeature(String f);


}