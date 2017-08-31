package customs.models;

import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;



@Transactional
public interface ReuseLevelDao extends CrudRepository<ReuseLevel, Long>{

	Iterable<ReuseLevel> getCustomsByIdrelease(String productrelease);

}
