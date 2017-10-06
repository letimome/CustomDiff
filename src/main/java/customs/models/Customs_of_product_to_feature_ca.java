package customs.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;


@Entity
@Table(name = "customs_by_products_to_features")
public class Customs_of_product_to_feature_ca {
	@Id String id;
	
	int idcoreasset;
	int idproductasset;
	String pr;
	String assetname;
	String assetpath;
	int churn;
	String idfeature;
	String idbaseline;
	
	public Customs_of_product_to_feature_ca() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
	public String getIdbaseline() {
		return idbaseline;
	}

	public void setIdbaseline(String idbaseline) {
		this.idbaseline = idbaseline;
	}

	public int getIdcoreasset() {
		return idcoreasset;
	}

	public void setIdcoreasset(int idcoreasset) {
		this.idcoreasset = idcoreasset;
	}

	public int getIdproductasset() {
		return idproductasset;
	}

	public void setIdproductasset(int idproductasset) {
		this.idproductasset = idproductasset;
	}

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public String getAssetname() {
		return assetname;
	}

	public void setAssetname(String assetname) {
		this.assetname = assetname;
	}

	public String getAssetpath() {
		return assetpath;
	}

	public void setAssetpath(String assetpath) {
		this.assetpath = assetpath;
	}

	public int getChurn() {
		return churn;
	}

	public void setChurn(int churn) {
		this.churn = churn;
	}

	public String getIdfeature() {
		return idfeature;
	}

	public void setIdfeature(String idfeature) {
		this.idfeature = idfeature;
	}
}
