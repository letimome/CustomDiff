package customs.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SPLdao extends CrudRepository <SPL, Long>{

}
