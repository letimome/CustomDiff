package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
 public interface CustomsForCaPaFeatureDao extends CrudRepository<CustomsForCaPaFeature, Long>{

	public Iterable<CustomsForCaPaFeature> findCustomsByFeaturemodified(String featurenamemodified);
	
}

