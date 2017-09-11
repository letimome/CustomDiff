package customs.models;


import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface CustomizationsByVPandPRDao extends CrudRepository <CustomizationsByVPandPR, Long> {

}
