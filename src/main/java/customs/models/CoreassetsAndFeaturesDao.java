package customs.models;


import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface CoreassetsAndFeaturesDao extends CrudRepository <CoreassetsAndFeatures, Long>{

	

	Iterable<CoreassetsAndFeatures> getFeatureCoreAssetsByIdfeature(String idfeature);
	
	
}