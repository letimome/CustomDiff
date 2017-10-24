package customs.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface NewProductAssetDao extends CrudRepository<NewProductAsset, Long>{
	
	NewProductAsset getProductAssetByIdasset(int idasset);

	NewProductAsset getProductAssetByPath(String path);

	NewProductAsset getProductAssetByName(String name);

}
