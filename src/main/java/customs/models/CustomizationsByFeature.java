package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customizationsbyfeature")
public class CustomizationsByFeature {
	@Id String id;
	int coreassetid;
	String caname;
	String capath;
	String featureid;
	String pr;
	int amount;
	
	
	public  CustomizationsByFeature() {}
	
	
	
	public CustomizationsByFeature(String id, int coreasset_id, String ca_name, String feature_id,
			String product_release, int amount) {
		super();
		this.id = id;
		this.coreassetid = coreasset_id;
		this.caname = ca_name;
		this.featureid = feature_id;
		this.pr = product_release;
		this.amount = amount;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public int getCoreassetid() {
		return coreassetid;
	}



	public void setCoreassetid(int coreassetid) {
		this.coreassetid = coreassetid;
	}



	public String getCaname() {
		return caname;
	}



	public void setCaname(String caname) {
		this.caname = caname;
	}



	public String getCapath() {
		return capath;
	}



	public void setCapath(String capath) {
		this.capath = capath;
	}



	public String getFeatureId() {
		return featureid;
	}



	public void setFeatureId(String featureId) {
		this.featureid = featureId;
	}



	public String getPr() {
		return pr;
	}



	public void setPr(String pr) {
		this.pr = pr;
	}



	public int getAmount() {
		return amount;
	}



	public void setAmount(int amount) {
		this.amount = amount;
	}



	
}
