package customs.peering;

import customs.models.ProductRelease;
import customs.utils.Formatting;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.api.ApplyResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.PatchApplyException;
import org.eclipse.jgit.api.errors.PatchFormatException;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.FileHeader.PatchType;
import org.springframework.beans.factory.annotation.Autowired;

import customs.utils.FileComparator;
import customs.utils.FileUtils;
import customs.models.CoreAsset;
import customs.models.CoreAssetDao;
import customs.models.CoreassetsAndFeatures;
import customs.models.CoreassetsAndFeaturesDao;
import customs.models.CustomsByFeatureAndCoreAsset;
import customs.models.CustomsByFeatureAndCoreAssetDao;
import customs.models.Feature;
import customs.models.FeaturesInVariationpoints;
import customs.models.FeaturesInVariationpointsDao;
//import org.eclipse.jgit.patch.*;


public class FeaturePatcher {
	ThreeWayDiffWorkspace workspace;
	private String pathToResource = "./src/main/resources/kdiff-workspace/";
	
	public void patchFilesForFeatureAndProduct(ProductRelease yours, ProductRelease mine , Feature feature, CustomsByFeatureAndCoreAssetDao customsDao,
			CoreassetsAndFeaturesDao featuresInCoreassetsDao, CoreAssetDao caDao) {
		//Step 1: get all the files in which the "feature" is present
		ArrayList<CoreAsset> baselineFiles = getBaselineCoreAssets4Feature(feature, featuresInCoreassetsDao, caDao, yours.getName(), mine.getName());
		try {
			
			ArrayList<CoreAsset> yoursFiles = patchingCustomizationsForProduct(yours,feature, baselineFiles,customsDao,featuresInCoreassetsDao, caDao);
			ArrayList<CoreAsset> myFiles = patchingCustomizationsForProduct(mine,feature, baselineFiles,customsDao,featuresInCoreassetsDao, caDao);
			//Create workspace
		
			//ThreeWayDiffWorkspace kdiff3 = new ThreeWayDiffWorkspace(workspacePath, commonAncestor, mine, theirs, featurename, baselineFolderName, myFolderName, theirFolderName)
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Autowired
	private ArrayList<CoreAsset> patchingCustomizationsForProduct(ProductRelease product, Feature feature,ArrayList<CoreAsset> baselineFiles, CustomsByFeatureAndCoreAssetDao customsDao, CoreassetsAndFeaturesDao featuresInCoreassetsDao, CoreAssetDao caDao) {
		
		
		Iterator<CustomsByFeatureAndCoreAsset> customs = customsDao.getCustomsByIdproductreleaseAndIdfeature(product.getId_productrelease(),feature.getIdfeature()).iterator();
		CustomsByFeatureAndCoreAsset custom;
		String patch;
		CoreAsset ca;
		int i=0;
		ArrayList<CoreAsset> patchedFiles = new ArrayList<CoreAsset>();
		patchedFiles.addAll(baselineFiles);//copy all the files
		
		while(customs.hasNext()) {
			custom = customs.next();
			patch = Formatting.decodeFromBase64(custom.getCustom_diff());
			ca = caDao.getCoreAssetByIdcoreasset(custom.getIdcoreasset());
			System.out.println("PATCHING FOR "+custom.getPrname()+ ": asset "+custom.getCaname()+ ", changed by  for feature "+custom.getIdfeature()+", with patch: "+Formatting.decodeFromBase64(custom.getCustom_diff()));

			//try to patch the files
			try {
				
				FileUtils.writeToFile(this.pathToResource+"/patches/"+product.getName()+"/patch"+i+".patch", patch.replace("\t", " "));
				File initialFile = new File (this.pathToResource+"/patches/"+product.getName()+"/patch"+i+".patch");
				
				InputStream targetStream = new FileInputStream(initialFile);
				System.out.println("targetStream:"+targetStream.read());
				
				ApplyPatchCommand applyPatch = new ApplyPatchCommand(null);
				applyPatch.setPatch(targetStream);//the patch to apply
				
				File  f = new File(pathToResource+product.getName()+"/"+ca.getPath());
				System.out.println("BEFORE calling call path");
				ApplyResult result = applyPatch.call(f,product.getName()); 
				
				System.out.println("result: "+result.getUpdatedFiles().size());
				File arc = result.getUpdatedFiles().get(0);
				CoreAsset patchedCA = getCoreAssetByPath(patchedFiles, ca.getPath());//.setContent();
				
				//update the content of the files
				patchedCA.setContent(FileUtils.readFromFile(this.pathToResource+product.getName()+"/"+ca.getPath()));
			
				
			} catch (PatchFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (GitAPIException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}

	

	private CoreAsset getCoreAssetByPath(ArrayList<CoreAsset> patchedFiles, String path) {
		Iterator<CoreAsset> it = patchedFiles.iterator();
		CoreAsset ca;
		while(it.hasNext()) {
			ca = it.next();
			if(ca.getPath().equals(path))
				return ca;
		}
		return null;
	}

	public ArrayList<CoreAsset> getBaselineCoreAssets4Feature(Feature feature, CoreassetsAndFeaturesDao featuresInCoreassetsDao, CoreAssetDao caDao, String observed,String observer){
		//find all the assets that belong to a feature
		Iterator<CoreassetsAndFeatures> res = featuresInCoreassetsDao.getFeatureCoreAssetsByIdfeature(feature.getIdfeature()).iterator();
		CoreassetsAndFeatures caFeature;
		ArrayList<CoreAsset> baselineCoreAssets = new ArrayList<CoreAsset>();
		CoreAsset ca;
		while(res.hasNext()) {
			caFeature = res.next();
			ca = caDao.getCoreAssetByIdcoreasset(caFeature.getId_coreasset());
			if (!baselineCoreAssets.contains(ca)) {
				baselineCoreAssets.add(ca);
				System.out.println("CA path getBaselineCoreAssets4Feature: "+ca.getPath());
				//write to all the folders: baseline, observed & observer
				FileUtils.writeToFile(pathToResource+"baseline/"+ca.getPath(),Formatting.decodeFromBase64(ca.getContent()));
				FileUtils.writeToFile(pathToResource+observed+"/"+ca.getPath(),Formatting.decodeFromBase64(ca.getContent()));
				FileUtils.writeToFile(pathToResource+observer+"/"+ca.getPath(),Formatting.decodeFromBase64(ca.getContent()));
			}	
		}
		return baselineCoreAssets;
	}
	
	
	
	
	/*************************************************************//*************************************************************/
	/*************************************************************//*************************************************************/
	/***Non used functions ***/
	private void pruebaJgit(ProductRelease yours, Feature feature, ArrayList<CoreAsset> baselineFiles,
			CustomsByFeatureAndCoreAssetDao customsDao, CoreassetsAndFeaturesDao featuresInCoreassetsDao,
			CoreAssetDao caDao) {
		
		System.out.println("PRUEBA WITH JGIT :-)");
		//1: get the patch text
		String text=
				"diff --git a/input/js/sensors.js b/input/js/sensors.js\n" + 
				"index 58d4441..e86007f 100644\n" + 
				"--- a/input/js/sensors.js\n" + 
				"+++ b/input/js/sensors.js\n" + 
				"@@ -40,14 +40,16 @@ Expression:(pv:hasFeature('WindSpeed') or pv:hasFeature('AirPressure'))Change Type: CHANGE_IN_VARIATION_POINT_BODY\n" + 
				" 		var measure = measureText.value;\n" + 
				" 		var intValue = checkMeasure(min, max, measure);\n" + 
				" 		if (isNaN(intValue)) return false;\n" + 
				"+		var root = stratify(data) \n" + 
				"+	      .sum(function(d) { return d.value; })\n" + 
				"+	      .sort(function(a, b) { return b.height - a.height || b.value - a.value; });\n" + 
				"+ \n" + 
				" 		function getMetaContentByName(name,content){\n" + 
				" 			   var content = (content==null)?'content':content;\n" + 
				" 			   return document.querySelector(\"meta[name='\"+name+\"']\").getAttribute(content);\n" + 
				" 			}\n" + 
				" 			\n" + 
				" 			$(document).ready(function(){\n" + 
				"-				//console.log(diffString); //diffString should be the value of diffview value\n" + 
				"-				//get select(\"#diffvalue\"). metadata\n" + 
				" 				var diffvalue = getMetaContentByName(\"diffvalue\");\n" + 
				" 				if (diffvalue==null) return null;\n" + 
				" 			});"; 
		
		//2: parse with JGit library
		try {

			/**Lo que vale**/
		
		
			
			
			try {
				File initialFile = new File(pathToResource+"observed/patch0.patch");
			    InputStream targetStream = new FileInputStream(initialFile);
			    System.out.println("targetStream:"+targetStream.read());
				ApplyPatchCommand applyPatch = new ApplyPatchCommand(null);
				applyPatch.setPatch(targetStream);//the patch to apply
				File  f = new File(pathToResource+"kdiff-workspace/baseline/input/js/sensors.js");
				System.out.println("BEFORE calling call path");
				ApplyResult result = applyPatch.call(f,"baseline"); //!!!!
				System.out.println("result: "+result.getUpdatedFiles().size());
				File arc = result.getUpdatedFiles().get(0);
				
			} catch (PatchFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //parse the patch file
		 
	}

}
