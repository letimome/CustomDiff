package customs.models;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;


@Transactional
public interface BaselineDao extends CrudRepository <Coreassetbaseline, Long> {

	//List <CoreAssetBaseline> findbySPL_idSPL(String sPL_idSPL);

}
