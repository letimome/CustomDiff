package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;



@Transactional
public interface FeatureDao extends CrudRepository<Feature, Long> {

	Feature getFeatureByName(String name);

	Feature getFeatureByIdfeature(String idfeature);

	Iterable<Feature> getFeaturesByIdparent(int idparentfeature);


}