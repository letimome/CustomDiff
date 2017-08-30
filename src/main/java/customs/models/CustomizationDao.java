package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;


public interface CustomizationDao extends CrudRepository <Customization, Long>{
	
	//List <Customization> findCustomizationBy SPL_idSPL(String sPL_idSPL);
	
}
