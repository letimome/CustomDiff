package customs.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customizationgbproductfeature")
public class CustomizationByPF implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	String idbaseline;
	int idproduct;
	@Id String featuremodified;
	String name;
	String idrelease;
	int churn;
	
	public CustomizationByPF() {
		
	}

	public CustomizationByPF( String idBaseline, String featureModified, int idProduct, String name,
			String idRelease, int churn) {
		this.idbaseline = idBaseline;
		this.featuremodified = featureModified;
		this.idproduct = idProduct;
		this.name = name;
		this.idrelease = idRelease;
		this.churn = churn;
	}
	
	public String getIdbaseline() {
		return idbaseline;
	}

	public void setIdbaseline(String idbaseline) {
		this.idbaseline = idbaseline;
	}

	public String getFeaturemodified() {
		return featuremodified;
	}

	public void setFeaturemodified(String featuremodified) {
		this.featuremodified = featuremodified;
	}

	public int getIdproduct() {
		return idproduct;
	}

	public void setIdproduct(int idproduct) {
		this.idproduct = idproduct;
	}

	public String getIdrelease() {
		return idrelease;
	}

	public void setIdrelease(String idrelease) {
		this.idrelease = idrelease;
	}

	public String getIdBaseline() {
		return idbaseline;
	}

	public void setIdBaseline(String idBaseline) {
		this.idbaseline = idBaseline;
	}

	public String getFeatureModified() {
		return featuremodified;
	}

	public void setFeatureModified(String featureModified) {
		this.featuremodified = featureModified;
	}

	public int getIdProduct() {
		return idproduct;
	}

	public void setIdProduct(int idProduct) {
		this.idproduct = idProduct;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdRelease() {
		return idrelease;
	}

	public void setIdRelease(String idRelease) {
		this.idrelease = idRelease;
	}

	public int getChurn() {
		return churn;
	}

	public void setChurn(int churn) {
		this.churn = churn;
	}
	
}
