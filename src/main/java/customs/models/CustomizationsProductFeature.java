package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "customizationgbproductfeature")

public class CustomizationsProductFeature {
		@Id String id;
		String idbaseline;
		String featuremodified;
		String idproduct;
		String name; //prpduct name
		String idrelease;
		int churn;
		
		public CustomizationsProductFeature () {}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getBaseline() {
			return idbaseline;
		}
		public void setBaseline(String baseline) {
			this.idbaseline = baseline;
		}
		public String getFeaturemodified() {
			return featuremodified;
		}
		public void setFeaturemodified(String featuremodified) {
			this.featuremodified = featuremodified;
		}
		public String getIdproduct() {
			return idproduct;
		}
		public void setIdproduct(String idproduct) {
			this.idproduct = idproduct;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getIdrelease() {
			return idrelease;
		}
		public void setIdrelease(String idrelease) {
			this.idrelease = idrelease;
		}
		public int getChurn() {
			return churn;
		}
		public void setChurn(int churn) {
			this.churn = churn;
		}
		
		
		
		
}
