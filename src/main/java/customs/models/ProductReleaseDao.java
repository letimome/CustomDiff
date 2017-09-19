package customs.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductReleaseDao extends CrudRepository<ProductRelease, Long>{

	ProductRelease getProductReleaseByIdrelease(String idrelease);
}
