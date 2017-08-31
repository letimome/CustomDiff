package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="notcustomizedproductassets")
public class NotCustomizedProductAssets {

	@Id String id;
	int idproductasset; 
	String name;
	String path;
	String content;
	int size; 
	String idrelease;
	
	public NotCustomizedProductAssets() {}
	
	public NotCustomizedProductAssets(String id, int idproductasset, String name, String path, String content, int size,
			String idrelease) {
		super();
		this.id = id;
		this.idproductasset = idproductasset;
		this.name = name;
		this.path = path;
		this.content = content;
		this.size = size;
		this.idrelease = idrelease;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIdproductasset() {
		return idproductasset;
	}
	public void setIdproductasset(int idproductasset) {
		this.idproductasset = idproductasset;
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
	public String getIdrelease() {
		return idrelease;
	}
	public void setIdrelease(String idrelease) {
		this.idrelease = idrelease;
	}	
	
	
	
}
