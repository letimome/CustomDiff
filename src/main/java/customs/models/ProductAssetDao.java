package customs.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductAssetDao extends CrudRepository<ProductAsset, Long>{
	
	ProductAsset getProductAssetByIdproductasset(int id);

	ProductAsset getProductAssetByPath(String path);

	ProductAsset getProductAssetByName(String name);

}
