package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="productasset")
public class ProductAsset {
	@Id int idproductasset;
	String name;
	String path;
	String content;
	int size;
	String productrelease_idrelease;
	String relative_diff;
	String absolute_diff;
	
	public ProductAsset() {
		
	}

	public int getIdProductasset() {
		return idproductasset;
	}

	public void setProductasset(int productasset) {
		this.idproductasset = productasset;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	

	public String getRelative_diff() {
		return relative_diff;
	}

	public void setRelative_diff(String relative_diff) {
		this.relative_diff = relative_diff;
	}

	public String getAbsolute_diff() {
		return absolute_diff;
	}

	public void setAbsolute_diff(String absolute_diff) {
		this.absolute_diff = absolute_diff;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getProductrelease_idrelease() {
		return productrelease_idrelease;
	}

	public void setProductrelease_idrelease(String productrelease_idrelease) {
		this.productrelease_idrelease = productrelease_idrelease;
	}
	
	
}
