package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customs_added_by_products_to_features")
public class AddedCustomsByProductsToFeatures {

	

	@Id  String id;
	int idcoreasset;
	int idproductasset;
	String pr;
	String assetname;
	String assetpath;
	int addedlines;
	String idfeature;
	
	public AddedCustomsByProductsToFeatures() {}
	
	public AddedCustomsByProductsToFeatures(String id, int idcoreasset, int idproductasset, String pr,
			String assetname, String assetpath, int linesadded) {
		super();
		this.id = id;
		this.idcoreasset = idcoreasset;
		this.idproductasset = idproductasset;
		this.pr = pr;
		this.assetname = assetname;
		this.assetpath = assetpath;
		this.addedlines = linesadded;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIdfeature() {
		return idfeature;
	}

	public void setIdfeature(String idfeature) {
		this.idfeature = idfeature;
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
	public int getLinesadded() {
		return addedlines;
	}
	public void setLinesadded(int linesadded) {
		this.addedlines = linesadded;
	}
	
	

}
