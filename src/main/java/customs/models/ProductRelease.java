package customs.models;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="productrelease")
public class ProductRelease {

	@Id int idrelease;
	Date releaseDate;
	int roduct_idproduct;
	
	public ProductRelease() {}

	public ProductRelease(int idrelease, Date releaseDate, int roduct_idproduct) {
		super();
		this.idrelease = idrelease;
		this.releaseDate = releaseDate;
		this.roduct_idproduct = roduct_idproduct;
	}

	public int getIdrelease() {
		return idrelease;
	}

	public void setIdrelease(int idrelease) {
		this.idrelease = idrelease;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getRoduct_idproduct() {
		return roduct_idproduct;
	}

	public void setRoduct_idproduct(int roduct_idproduct) {
		this.roduct_idproduct = roduct_idproduct;
	}
	
}
