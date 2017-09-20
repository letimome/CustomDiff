package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


	
	@Entity
	@Table(name = "coreassetsbyfeatureid")
	//UUID() as 'id', b.idbaseline as 'baseline', ca.idcoreasset as 'idcoreasset', f.idfeature as 'featureid'
	public class CoreassetsAndFeatures {
		@Id String id;
		String baseline;
		int idcoreasset;
		String featureid;
		
		public CoreassetsAndFeatures() {}
		

		public CoreassetsAndFeatures(String id, String baseline, int idcoreasset, String featureid) {
			super();
			this.id = id;
			this.baseline = baseline;
			this.idcoreasset = idcoreasset;
			this.featureid = featureid;
		}

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getBaseline() {
			return baseline;
		}
		public void setBaseline(String baseline) {
			this.baseline = baseline;
		}
		public int getIdcoreasset() {
			return idcoreasset;
		}
		public void setIdcoreasset(int idcoreasset) {
			this.idcoreasset = idcoreasset;
		}
		public String getFeatureid() {
			return featureid;
		}
		public void setFeatureid(String featureid) {
			this.featureid = featureid;
		}
		
		
		
}
