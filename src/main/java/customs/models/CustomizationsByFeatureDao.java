package customs.models;

import java.util.Iterator;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface CustomizationsByFeatureDao extends CrudRepository<CustomizationsByFeature, Long> {

	Iterable <CustomizationsByFeature> getCustomsByFeatureid(String featureid);

}
