package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface Churn_AbstractFeatures_PPDao extends CrudRepository<Churn_AbstractFeatures_PP, Long> {

}
