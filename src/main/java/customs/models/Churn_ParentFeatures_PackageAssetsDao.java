package customs.models;


import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface Churn_ParentFeatures_PackageAssetsDao extends CrudRepository<Churn_ParentFeatures_PackageAssets, Long> {

}
