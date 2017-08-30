package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SPL")
public class SPL {
	
	@Id String idSPL;
	
	
	public SPL() {}


	public String getIdSPL() {
		return idSPL;
	}


	public void setIdSPL(String idSPL) {
		this.idSPL = idSPL;
	}
	
}
