package customs.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;


@Entity
@Table (name="nonono")
public class CustomsByProductAssetsToFeatures {
//customsByProductAssetsToFeatures
	@Id String id;
	String inproduct;//productrelease
	int idproductasset;
	String name;
	String path;
	String featurechanged;
	int churn;
	
    public CustomsByProductAssetsToFeatures() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInproduct() {
		return inproduct;
	}

	public void setInproduct(String inproduct) {
		this.inproduct = inproduct;
	}

	public int getIdproductasset() {
		return idproductasset;
	}

	public void setIdproductasset(int idproductasset) {
		this.idproductasset = idproductasset;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFeaturechanged() {
		return featurechanged;
	}

	public void setFeaturechanged(String featurechanged) {
		this.featurechanged = featurechanged;
	}

	public int getChurn() {
		return churn;
	}

	public void setChurn(int churn) {
		this.churn = churn;
	}
    
    
	
}
