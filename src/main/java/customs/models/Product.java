package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="product")
public class Product {
	
	@Id int idproduct;
	String name;
	
	public Product() {}

	public Product(int idproduct, String name, String productportfolio_idportfolio) {
		super();
		this.idproduct = idproduct;
		this.name = name;

	}

	public int getIdproduct() {
		return idproduct;
	}
	
	public void setIdproduct(int idproduct) {
		this.idproduct = idproduct;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	
}
