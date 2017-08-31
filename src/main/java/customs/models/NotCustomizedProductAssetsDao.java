package customs.models;

import org.springframework.data.repository.CrudRepository;

public interface NotCustomizedProductAssetsDao extends CrudRepository<NotCustomizedProductAssets, Long> {
	
	Iterable<NotCustomizedProductAssets> getNotCustomizedAssetsByIdrelease(String idrelease);

}
