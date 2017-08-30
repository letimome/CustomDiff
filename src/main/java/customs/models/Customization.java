package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customization")
public class Customization {
	
	@Id int idcustomization;
	String operation;
	int coreAsset_idcoreAsset;
	int productasset_idproductasset;
	String feature_idfeature;
	int isnewfeature;
	int isnewasset;
	String featurenamemodified;
	
	
	public Customization () {}


	public int getIdCustomization() {
		return idcustomization;
	}


	public void setIdCustomization(int idcustomization) {
		this.idcustomization = idcustomization;
	}


	public String getOperation() {
		return operation;
	}


	public void setOperation(String operation) {
		this.operation = operation;
	}


	public int getCoreAsset_idCoreAsset() {
		return coreAsset_idcoreAsset;
	}


	public void setCoreAsset_idCoreAsset(int coreAsset_idCoreAsset) {
		coreAsset_idcoreAsset = coreAsset_idCoreAsset;
	}


	public int getProductAsset_idProductAsset() {
		return productasset_idproductasset;
	}


	public void setProductAsset_idProductAsset(int productAsset_idProductAsset) {
		productasset_idproductasset = productAsset_idProductAsset;
	}


	public String getFeature_idFeature() {
		return feature_idfeature;
	}


	public void setFeature_idFeature(String feature_idFeature) {
		feature_idfeature = feature_idFeature;
	}


	public int getIsNewFeature() {
		return isnewfeature;
	}


	public void setIsNewFeature(int isNewFeature) {
		this.isnewfeature = isNewFeature;
	}


	public int getIsNewAsset() {
		return isnewasset;
	}


	public void setIsNewAsset(int isNewAsset) {
		this.isnewasset = isNewAsset;
	}


	public String getFeatureNameModified() {
		return featurenamemodified;
	}


	public void setFeatureNameModified(String featurenamemodified) {
		this.featurenamemodified = featurenamemodified;
	}
	
	
	
}
