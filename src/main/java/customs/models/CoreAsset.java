package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table (name="coreasset")
public class CoreAsset {


	
		@Id int idcoreasset;
		String name;
		String path;
		String content;
		int size;
		String coreassetbaseline_idbaseline;
		
		public CoreAsset() {}

		public int getIdcoreasset() {
			return idcoreasset;
		}

		public void setIdcoreasset(int idcoreasset) {
			this.idcoreasset = idcoreasset;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public String getCoreassetbaseline_idbaseline() {
			return coreassetbaseline_idbaseline;
		}

		public void setCoreassetbaseline_idbaseline(String coreassetbaseline_idbaseline) {
			this.coreassetbaseline_idbaseline = coreassetbaseline_idbaseline;
		}
		
	
}
