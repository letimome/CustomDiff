package customs.models;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="productrelease")
public class ProductRelease {

	@Id String idrelease;
	Date releasedate;
	int product_idproduct;
	
	public ProductRelease() {}

	public ProductRelease(String idrelease, Date releaseDate, int roduct_idproduct) {
		super();
		this.idrelease = idrelease;
		this.releasedate = releaseDate;
		this.product_idproduct = roduct_idproduct;
	}

	public String getIdrelease() {
		return idrelease;
	}

	public void setIdrelease(String idrelease) {
		this.idrelease = idrelease;
	}

	public Date getReleaseDate() {
		return releasedate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releasedate = releaseDate;
	}

	public int getRoduct_idproduct() {
		return product_idproduct;
	}

	public void setRoduct_idproduct(int roduct_idproduct) {
		this.product_idproduct = roduct_idproduct;
	}
	
}
