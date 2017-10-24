package customs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table (name="core_asset")
public class CoreAsset {

		@Id int idcoreasset;
		String name;
		String path;
		String content;
		int size;
		int isnewasset;
		
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

		public int getIsnewasset() {
			return isnewasset;
		}

		public void setIsnewasset(int isnewasset) {
			this.isnewasset = isnewasset;
		}
}
