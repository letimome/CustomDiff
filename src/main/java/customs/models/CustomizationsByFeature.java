package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customizationsByFeature")
public class CustomizationsByFeature {
	@Id String id;
	int coreasset_id;
	String ca_name;
	String feature_id;
	String product_release;
	int amount;
	
	
	public  CustomizationsByFeature() {}
	
	
	
	public CustomizationsByFeature(String id, int coreasset_id, String ca_name, String feature_id,
			String product_release, int amount) {
		super();
		this.id = id;
		this.coreasset_id = coreasset_id;
		this.ca_name = ca_name;
		this.feature_id = feature_id;
		this.product_release = product_release;
		this.amount = amount;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCoreasset_id() {
		return coreasset_id;
	}
	public void setCoreasset_id(int coreasset_id) {
		this.coreasset_id = coreasset_id;
	}
	public String getCa_name() {
		return ca_name;
	}
	public void setCa_name(String ca_name) {
		this.ca_name = ca_name;
	}
	public String getFeature_id() {
		return feature_id;
	}
	public void setFeature_id(String feature_id) {
		this.feature_id = feature_id;
	}
	public String getProduct_release() {
		return product_release;
	}
	public void setProduct_release(String product_release) {
		this.product_release = product_release;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
