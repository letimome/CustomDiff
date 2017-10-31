package customs.models;
import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface Churn_Features_ComponentPackagesDao extends CrudRepository <Churn_Features_ComponentPackages, Long>{

	Iterable<Churn_Features_ComponentPackages> getCustomsByIdproductrelease(int id_productrelease);

}
