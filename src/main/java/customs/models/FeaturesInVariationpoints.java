package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="features_in_variationpoints")
public class FeaturesInVariationpoints {
	@Id int id;
	int id_feature;
	int id_variationpoint;
	String expression;
	String feature_name;
	int id_coreasset;
	
	public FeaturesInVariationpoints() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_feature() {
		return id_feature;
	}

	public void setId_feature(int id_feature) {
		this.id_feature = id_feature;
	}

	public int getId_variationpoint() {
		return id_variationpoint;
	}

	public void setId_variationpoint(int id_variationpoint) {
		this.id_variationpoint = id_variationpoint;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getFeature_name() {
		return feature_name;
	}

	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}

	public int getId_coreasset() {
		return id_coreasset;
	}

	public void setId_coreasset(int id_coreasset) {
		this.id_coreasset = id_coreasset;
	}

}
