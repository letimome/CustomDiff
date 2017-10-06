package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface CustomsByProductAssetsToFeaturesDao extends CrudRepository<CustomsByProductAssetsToFeatures, Long>{

	Iterable<CustomsByProductAssetsToFeatures> getCustomsByInproduct(String inproduct);

}
