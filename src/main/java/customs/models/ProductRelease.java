package customs.models;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="product_release")
public class ProductRelease {

	@Id int idproductrelease;
	String name;//tag
	Date date;
	String commits_set;
	int idproduct;
	
	public ProductRelease() {}

	
	
	public ProductRelease(int id_productrelease, String name, Date date, String commits_set, int id_product) {
		super();
		this.idproductrelease = id_productrelease;
		this.name = name;
		this.date = date;
		this.commits_set = commits_set;
		this.idproduct = id_product;
	}



	public int getId_productrelease() {
		return idproductrelease;
	}

	public void setId_productrelease(int id_productrelease) {
		this.idproductrelease = id_productrelease;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCommits_set() {
		return commits_set;
	}

	public void setCommits_set(String commits_set) {
		this.commits_set = commits_set;
	}

	public int getId_product() {
		return idproduct;
	}

	public void setId_product(int id_product) {
		this.idproduct = id_product;
	}


	
}
