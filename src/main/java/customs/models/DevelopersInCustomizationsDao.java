package customs.models;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DevelopersInCustomizationsDao extends CrudRepository<DevelopersInCustomizations, Long>{

}
