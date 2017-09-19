package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
@Transactional
public interface NotCustomizedProductAssetsDao extends CrudRepository<NotCustomizedProductAssets, Long> {
	
	Iterable<NotCustomizedProductAssets> getNotCustomizedAssetsByIdrelease(String idrelease);

}
