package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface  Customs_of_product_to_feature_caDao extends CrudRepository<Customs_of_product_to_feature_ca, Long>{

}
