package customs.models;


import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface Churn_Features_PackageAssetsDao extends CrudRepository<Churn_Features_PackageAssets, Long>{

}
