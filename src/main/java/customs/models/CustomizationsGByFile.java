package customs.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="customizationbgfile")
public class CustomizationsGByFile {
	
	 @Id String id;
	 int idcoreasset;
	 String path;
	 String cafilename;
	 String idbaseline;
	
	// String featuremodified;
	 int churn;
	 int numberofproductscustomizing;
	
	
	public int getIdcoreasset() {
		return idcoreasset;
	}
	public void setIdcoreasset(int idcoreasset) {
		this.idcoreasset = idcoreasset;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCafilename() {
		return cafilename;
	}
	public void setCafilename(String cafilename) {
		this.cafilename = cafilename;
	}
	public String getIdbaseline() {
		return idbaseline;
	}
	public void setIdbaseline(String idbaseline) {
		this.idbaseline = idbaseline;
	}
	/*public String getFeaturemodified() {
		return featuremodified;
	}
	public void setFeaturemodified(String featuremodified) {
		this.featuremodified = featuremodified;
	}*/
	public int getChurn() {
		return churn;
	}
	public void setChurn(int churn) {
		this.churn = churn;
	}
	public int getNumberofproductscustomizing() {
		return numberofproductscustomizing;
	}
	public void setNumberofproductscustomizing(int numberofproductscustomizing) {
		this.numberofproductscustomizing = numberofproductscustomizing;
	}
}