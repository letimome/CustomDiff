package customs.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface Churn_PoductPortfolioAndFeaturesDao extends CrudRepository<Churn_PoductPortfolioAndFeatures, Long> {
	
	
	

}
