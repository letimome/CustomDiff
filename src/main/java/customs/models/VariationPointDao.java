package customs.models;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface VariationPointDao extends CrudRepository <VariationPoint, Long > {
	
	VariationPoint getVariationPointByIdvariationpoint(int idvariationpoint);
	Iterable<VariationPoint> getVariationPointsByIdproductasset(int idproductasset);

}
