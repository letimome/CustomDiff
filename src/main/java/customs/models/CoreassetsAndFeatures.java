package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


	
	@Entity
	@Table(name = "coreassets_and_features")
	public class CoreassetsAndFeatures {
		@Id String id;
		int idcoreasset;
		String idfeature;
		String caname;
		String capath;
		int size;
		
		public CoreassetsAndFeatures() {}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getId_coreasset() {
			return idcoreasset;
		}

		public void setId_coreasset(int id_coreasset) {
			this.idcoreasset = id_coreasset;
		}

		public String getId_feature() {
			return idfeature;
		}

		public void setId_feature(String id_feature) {
			this.idfeature = id_feature;
		}

		public String getCa_name() {
			return caname;
		}

		public void setCa_name(String ca_name) {
			this.caname = ca_name;
		}

		public String getCa_path() {
			return capath;
		}

		public void setCa_path(String ca_path) {
			this.capath = ca_path;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}
}
