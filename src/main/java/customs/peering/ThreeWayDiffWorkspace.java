package customs.peering;

import java.io.File;
import java.util.ArrayList;

import customs.models.CoreAsset;

public class ThreeWayDiffWorkspace {
	
//	String workspacePath;
	ArrayList<CoreAsset> commonAncestor;
	ArrayList<CoreAsset> mine;
	ArrayList<CoreAsset> theirs;
	String featurename;
	String baselineFolderName;
	String myFolderName;
	String theirFolderName;
	
	
	
	public ThreeWayDiffWorkspace( ArrayList<CoreAsset> commonAncestor, ArrayList<CoreAsset> mine,
			ArrayList<CoreAsset> theirs, String featurename, String baselineFolderName, String myFolderName,
			String theirFolderName) {
		super();
	
		//this.workspacePath = workspacePath;
		this.commonAncestor = commonAncestor;
		this.mine = mine;
		this.theirs = theirs;
		this.featurename = featurename;
		this.baselineFolderName = baselineFolderName;
		this.myFolderName = myFolderName;
		this.theirFolderName = theirFolderName;
	}

	/*public String getWorkspacePath() {
		return workspacePath;
	}

	public void setWorkspacePath(String workspacePath) {
		this.workspacePath = workspacePath;
	}*/

	public ArrayList<CoreAsset> getCommonAncestor() {
		return commonAncestor;
	}

	public void setCommonAncestor(ArrayList<CoreAsset> commonAncestor) {
		this.commonAncestor = commonAncestor;
	}

	public ArrayList<CoreAsset> getMine() {
		return mine;
	}

	public void setMine(ArrayList<CoreAsset> mine) {
		this.mine = mine;
	}

	public ArrayList<CoreAsset> getTheirs() {
		return theirs;
	}

	public void setTheirs(ArrayList<CoreAsset> theirs) {
		this.theirs = theirs;
	}

	public String getFeaturename() {
		return featurename;
	}

	public void setFeaturename(String featurename) {
		this.featurename = featurename;
	}

	public String getBaselineFolderName() {
		return baselineFolderName;
	}

	public void setBaselineFolderName(String baselineFolderName) {
		this.baselineFolderName = baselineFolderName;
	}

	public String getMyFolderName() {
		return myFolderName;
	}

	public void setMyFolderName(String myFolderName) {
		this.myFolderName = myFolderName;
	}

	public String getTheirFolderName() {
		return theirFolderName;
	}

	public void setTheirFolderName(String theirFolderName) {
		this.theirFolderName = theirFolderName;
	}
	


}
