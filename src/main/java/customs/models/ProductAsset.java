package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="productasset")
public class ProductAsset {
	@Id int productasset;
	String name;
	String path;
	String content;
	int size;
	String productrelease_idrelease;
	
	public ProductAsset() {
		
	}
	
	
}
