package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface Churn_ParentFeatures_ProductComponentsDao extends CrudRepository<Churn_ParentFeatures_ProductComponents, Long>{

	Iterable<Churn_ParentFeatures_ProductComponents> getCustomsByIdproductrelease(int idproductrelease);

}
