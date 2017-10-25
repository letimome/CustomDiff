package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="feature")
public class Feature {
	@Id String idfeature;
	String name;
	int isnew;
	
	public Feature() {}

	public String getIdfeature() {
		return idfeature;
	}

	public void setIdfeature(String idfeature) {
		this.idfeature = idfeature;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIsNew() {
		return isnew;
	}

	public void setIsNew(int isNew) {
		this.isnew = isNew;
	}
	
}
