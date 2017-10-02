package customs.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
@Entity
@Table (name="variationpoint")
public class VariationPoint {
	
	@Id int idvariationpoint;
	String body;
	String expression;
	int line_init;
	int line_end;
	String idcoreasset;
	int idproductasset;
	String parent;
	
	public int getIdvariationpoint() {
		return idvariationpoint;
	}
	public void setIdvariationpoint(int idvariationpoint) {
		this.idvariationpoint = idvariationpoint;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public int getLine_init() {
		return line_init;
	}
	public void setLine_init(int line_init) {
		this.line_init = line_init;
	}
	public int getLine_end() {
		return line_end;
	}
	public void setLine_end(int line_end) {
		this.line_end = line_end;
	}
	public String getIdcoreasset() {
		return idcoreasset;
	}
	public void setIdcoreasset(String idcoreasset) {
		this.idcoreasset = idcoreasset;
	}
	public int getIdproductasset() {
		return idproductasset;
	}
	public void setIdproductasset(int idproductasset) {
		this.idproductasset = idproductasset;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}


}
