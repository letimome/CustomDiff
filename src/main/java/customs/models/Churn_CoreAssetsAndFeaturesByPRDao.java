package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface  Churn_CoreAssetsAndFeaturesByPRDao extends CrudRepository<Churn_CoreAssetsAndFeaturesByPR, Long>{

	Iterable<Churn_CoreAssetsAndFeaturesByPR> getCustomsByIdproductrelease(int idproductrelease);

}
