package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customizationsbyvpandprandoperation")
	
	
public class customizationsByVPandPRandOperation {
	
	@Id String id;
	String inproduct;
	int filechanged;
	String name;
	int locs;
	String operation;
	int idvariationpoint;
	String expression;
	String content;
	int vpsize;
	
	
	public customizationsByVPandPRandOperation() {
		
	}


	public customizationsByVPandPRandOperation(String id, String inproduct, int filechanged, String name, int locs,
			String operation, int idvariationpoint, String expression, String content, int vpsize) {
		super();
		this.id = id;
		this.inproduct = inproduct;
		this.filechanged = filechanged;
		this.name = name;
		this.locs = locs;
		this.operation = operation;
		this.idvariationpoint = idvariationpoint;
		this.expression = expression;
		this.content = content;
		this.vpsize = vpsize;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getInproduct() {
		return inproduct;
	}


	public void setInproduct(String inproduct) {
		this.inproduct = inproduct;
	}


	public int getFilechanged() {
		return filechanged;
	}


	public void setFilechanged(int filechanged) {
		this.filechanged = filechanged;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getLocs() {
		return locs;
	}


	public void setLocs(int locs) {
		this.locs = locs;
	}


	public String getOperation() {
		return operation;
	}


	public void setOperation(String operation) {
		this.operation = operation;
	}


	public int getIdvariationpoint() {
		return idvariationpoint;
	}


	public void setIdvariationpoint(int idvariationpoint) {
		this.idvariationpoint = idvariationpoint;
	}


	public String getExpression() {
		return expression;
	}


	public void setExpression(String expression) {
		this.expression = expression;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getVpsize() {
		return vpsize;
	}


	public void setVpsize(int vpsize) {
		this.vpsize = vpsize;
	}
	
	
	
}
