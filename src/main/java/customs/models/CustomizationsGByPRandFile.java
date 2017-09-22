package customs.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="customizationsgbprandfile")
public class CustomizationsGByPRandFile {
	
	 @Id String id;
	
	 String pa_name;
	 int idproduct;
	 int idcoreasset;
	 int idproductasset;
	 String product_name;
	 String idrelease;
	 String path;
	 int churn;
	 
	public CustomizationsGByPRandFile() {}
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPa_name() {
		return pa_name;
	}
	public void setPa_name(String pa_name) {
		this.pa_name = pa_name;
	}
	public int getIdproduct() {
		return idproduct;
	}
	public void setIdproduct(int idproduct) {
		this.idproduct = idproduct;
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
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getIdrelease() {
		return idrelease;
	}
	public void setIdrelease(String idrelease) {
		this.idrelease = idrelease;
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
	 

}