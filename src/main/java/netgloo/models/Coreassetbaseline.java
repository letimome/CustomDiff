package netgloo.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "Coreassetbaseline")
public class Coreassetbaseline {
	@Id
	String idbaseline;
	Date releaseDate;
	String SPL_idSPL;
	
	public Coreassetbaseline() {}
	
	public Coreassetbaseline(String idBaseline, Date releaseDate, String sPL_idSPL) {
		this.idbaseline = idBaseline;
		this.releaseDate = releaseDate;
		this.SPL_idSPL = sPL_idSPL;
	}
	
	public String getIdBaseline() {
		return idbaseline;
	}
	public void setIdBaseline(String idBaseline) {
		this.idbaseline = idBaseline;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getSPL_idSPL() {
		return SPL_idSPL;
	}
	public void setSPL_idSPL(String sPL_idSPL) {
		SPL_idSPL = sPL_idSPL;
	}

}
