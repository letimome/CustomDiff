package customs.models;

import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;



@Transactional
public interface CustomizationsGByOperationDao extends CrudRepository<CustomizationsGByOperation, Long>{

	Iterable<CustomizationsGByOperation> getCustomsByIdrelease(String productrelease);

}
