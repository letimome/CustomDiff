package customs.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;


@Entity
@Table(name = "churn_coreassets_and_features_by_pr")
public class Churn_CoreAssetsAndFeaturesByPR {
	@Id String id;//
	int idproductrelease;
	String idfeature;
	int idcoreasset;
	String pr_name;
	String ca_name;
	String ca_path;
	int churn;
	

	public Churn_CoreAssetsAndFeaturesByPR() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIdcoreasset() {
		return idcoreasset;
	}

	public void setIdcoreasset(int idcoreasset) {
		this.idcoreasset = idcoreasset;
	}


	public int getId_coreasset() {
		return idcoreasset;
	}

	public void setId_coreasset(int id_coreasset) {
		this.idcoreasset = id_coreasset;
	}

	public String getPr_name() {
		return pr_name;
	}

	public void setPr_name(String pr_name) {
		this.pr_name = pr_name;
	}

	public String getCa_name() {
		return ca_name;
	}

	public void setCa_name(String ca_name) {
		this.ca_name = ca_name;
	}

	public String getCa_path() {
		return ca_path;
	}

	public void setCa_path(String ca_path) {
		this.ca_path = ca_path;
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

	public int getIdproductrelease() {
		return idproductrelease;
	}

	public void setIdproductrelease(int idproductrelease) {
		this.idproductrelease = idproductrelease;
	}
	
}
