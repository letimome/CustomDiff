package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface ComponentPackageDao extends CrudRepository< ComponentPackage, Long>{

	ComponentPackage getComponentPackageByIdpackage(int idpackage);

}
