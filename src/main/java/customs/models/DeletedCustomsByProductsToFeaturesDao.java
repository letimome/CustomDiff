package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface DeletedCustomsByProductsToFeaturesDao extends CrudRepository <DeletedCustomsByProductsToFeatures, Long>{

}
