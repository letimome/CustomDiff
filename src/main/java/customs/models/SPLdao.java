package customs.models;

import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;


@Transactional
public interface SPLdao extends CrudRepository <SPL, Long>{

}
