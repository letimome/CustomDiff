package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customizationsbyvpandpr")
public class CustomizationsByVPandPR {
	
	@Id String id;
	String inproduct;
	int filechanged;
	String name;
	//String featureimpacted;
	int churn;
	int idvariationpoint;
	String expression;
	String content;
	
	
	public CustomizationsByVPandPR() {
		
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


/*	public String getFeatureImpacted() {
		return featureimpacted;
	}


	public void setFeatureImpacted(String featureImpacted) {
		this.featureimpacted = featureImpacted;
	}
*/

	public int getChurn() {
		return churn;
	}


	public void setChurn(int churn) {
		this.churn = churn;
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
	

}
