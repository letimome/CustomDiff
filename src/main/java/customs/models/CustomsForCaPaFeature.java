package customs.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customs_ca_pa_gb_feature_pr")
public class CustomsForCaPaFeature {	
	@Id String id;
	int id_ca;
	int id_pa;
	String p_release;
	String filename;
	String path;
	int churn;
	String featuremodified;
	String idbaseline;
	
	public CustomsForCaPaFeature() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getId_ca() {
		return id_ca;
	}

	public void setId_ca(int id_ca) {
		this.id_ca = id_ca;
	}

	public int getId_pa() {
		return id_pa;
	}

	public void setId_pa(int id_pa) {
		this.id_pa = id_pa;
	}

	public String getP_release() {
		return p_release;
	}

	public void setP_release(String p_release) {
		this.p_release = p_release;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getChurn() {
		return churn;
	}

	public void setChurn(int churn) {
		this.churn = churn;
	}

	public String getFeaturemodified() {
		return featuremodified;
	}

	public void setFeaturemodified(String featuremodified) {
		this.featuremodified = featuremodified;
	}

	public String getIdbaseline() {
		return idbaseline;
	}

	public void setIdbaseline(String idbaseline) {
		this.idbaseline = idbaseline;
	}
	
	

}
