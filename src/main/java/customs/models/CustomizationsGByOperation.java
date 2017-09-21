package customs.models;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customizationsbyoperation")


public class CustomizationsGByOperation {
	
	@Id String id;
	int idproductasset;
	int idcoreasset;
	String pa_name;
	String idproduct; 
	String product_name; 
	String idrelease; 
	String operation ; //ADDED or REMOVED
	String path;
	int  locs;
	int ca_size; 
	int pa_size; 
	int delta;
	
	public CustomizationsGByOperation() {
		
	}
	



	public CustomizationsGByOperation(String id, int idproductasset, int idcoreasset, String pa_name, String idproduct,
			String product_name, String idrelease, String operation, String path, int locs, int ca_size, int pa_size,
			int delta) {
		super();
		this.id = id;
		this.idproductasset = idproductasset;
		this.idcoreasset = idcoreasset;
		this.pa_name = pa_name;
		this.idproduct = idproduct;
		this.product_name = product_name;
		this.idrelease = idrelease;
		this.operation = operation;
		this.path = path;
		this.locs = locs;
		this.ca_size = ca_size;
		this.pa_size = pa_size;
		this.delta = delta;
	}




	public int getIdproductasset() {
		return idproductasset;
	}


	public void setIdproductasset(int idproductasset) {
		this.idproductasset = idproductasset;
	}


	public int getIdcoreasset() {
		return idcoreasset;
	}


	public void setIdcoreasset(int idcoreasset) {
		this.idcoreasset = idcoreasset;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getPa_name() {
		return pa_name;
	}

	public void setPa_name(String pa_name) {
		this.pa_name = pa_name;
	}

	public String getIdproduct() {
		return idproduct;
	}

	public void setIdproduct(String idproduct) {
		this.idproduct = idproduct;
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

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int getLocs() {
		return locs;
	}

	public void setLocs(int locs) {
		this.locs = locs;
	}

	public int getCa_size() {
		return ca_size;
	}

	public void setCa_size(int ca_size) {
		this.ca_size = ca_size;
	}

	public int getPa_size() {
		return pa_size;
	}

	public void setPa_size(int pa_size) {
		this.pa_size = pa_size;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}
	

}
