package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

	

@Transactional
public interface customizationsByVPandPRandOperationDao extends CrudRepository <customizationsByVPandPRandOperation, Long> {

	Iterable<customizationsByVPandPRandOperation> getCustomizationByInproduct(String idrelease);

}
