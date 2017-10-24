package customs.models;


import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface CustomsByFeatureAndCoreAssetDao  extends 
			CrudRepository<CustomsByFeatureAndCoreAsset, Long>{

}
