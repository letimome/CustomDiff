package customs.models;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface ParentFeatureDao extends CrudRepository <ParentFeature, Long>{

	ParentFeature getFeaturePArentByName(String name);

	ParentFeature getParentFeatureByIdparentfeature(int idparentfeature);

}
