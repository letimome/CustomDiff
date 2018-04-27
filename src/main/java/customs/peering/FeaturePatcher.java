package customs.peering;

import customs.models.ProductRelease;
import customs.utils.Formatting;
import com.github.difflib.*;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.DiffUtils;
import com.github.difflib.patch.Patch;
import com.github.difflib.patch.PatchFailedException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
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
	private String pathToResource = "./src/main/resources/";
	
	public void patchFilesForFeatureAndProduct(ProductRelease yours, ProductRelease mine , Feature feature, CustomsByFeatureAndCoreAssetDao customsDao,
			CoreassetsAndFeaturesDao featuresInCoreassetsDao, CoreAssetDao caDao) {
		//Step 1: get all the files in which the "feature" is present
		ArrayList<CoreAsset> baselineFiles = getBaselineCoreAssets4Feature(feature, featuresInCoreassetsDao, caDao);
		try {
			//pruebaJavaDiffUtils(yours,feature, baselineFiles,customsDao,featuresInCoreassetsDao, caDao);
			pruebaJgit(yours,feature, baselineFiles,customsDao,featuresInCoreassetsDao, caDao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Step 2: for "my" product, get all the customizations that happen to that files, and for the feature at hand
		// UNCOMENT line below
	//	ArrayList<CoreAsset> yoursFiles = patchingCustomizationsForProduct(yours,feature, baselineFiles,customsDao,featuresInCoreassetsDao, caDao);
		
		//step 3: repeat process of step 2, but for the "yours" pr
		//singlePatchPrueba(yours,mine,feature,customsDao, featuresInCoreassetsDao,  caDao);
	}

	@Autowired
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
			/*org.eclipse.jgit.patch.Patch jGitPatch = new org.eclipse.jgit.patch.Patch();
			InputStream is = new ByteArrayInputStream( text.getBytes());
			jGitPatch.parse(is);
			System.out.println("PRINT:"+text.getBytes().length );
			System.out.println("IS:"+is.available());
			System.out.println("IS:"+is.read())
		*/
			
			 File initialFile = new File(pathToResource+"observed/patch2.patch");
		    InputStream targetStream = new FileInputStream(initialFile);
		    System.out.println("targetStream:"+targetStream.read());
			
			
		

			/**Lo que vale**/
			ApplyPatchCommand applyPatch = new ApplyPatchCommand(null);
			applyPatch.setPatch(targetStream);//the patch to apply
		
			
			File  f = new File(pathToResource+"kdiff-workspace/baseline/input/js/sensors.js");
			try {
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

	

	private ArrayList<CoreAsset> patchingCustomizationsForProduct(ProductRelease yours, Feature feature,ArrayList<CoreAsset> baselineFiles, CustomsByFeatureAndCoreAssetDao customsDao, CoreassetsAndFeaturesDao featuresInCoreassetsDao, CoreAssetDao caDao) {
		
		ArrayList<CoreAsset> yourFiles = new ArrayList<CoreAsset>();
		yourFiles.addAll(baselineFiles);
		Iterator<CustomsByFeatureAndCoreAsset> customs = customsDao.getCustomsByIdproductreleaseAndIdfeature(yours.getId_productrelease(),feature.getIdfeature()).iterator();
		CustomsByFeatureAndCoreAsset custom;
		String patch;
		CoreAsset ca;
		int i=0;
		while(customs.hasNext()) {
			custom = customs.next();
			patch = Formatting.decodeFromBase64(custom.getCustom_diff());
			ca = caDao.getCoreAssetByIdcoreasset(custom.getIdcoreasset());
			System.out.println("PATCHING asset "+custom.getCaname()+ ", changed by "+custom.getPrname()+ " for feature "+custom.getIdfeature()+", with patch: "+Formatting.decodeFromBase64(custom.getCustom_diff()));
			
			//try to patch the files
		//	try {
				FileUtils.writeToFile(this.pathToResource+"/observed/patch"+i+".patch", patch);
			//	List<String> patchedFile = FileComparator.patchFile(Formatting.stringToArrayList(patch, "\n"), Formatting.stringToArrayList(ca.getContent(), "\n") );
				i++;
		//	} 
				/* catch (IOException e) {
				System.out.println("ERROR IO for patching file ");
				e.printStackTrace();
			} catch (com.github.difflib.patch.PatchFailedException e) {
				System.out.println("ERROR PatchFailedException");
				e.printStackTrace();
			}*/
		}
		return null;
	}

	

	public ArrayList<CoreAsset> getBaselineCoreAssets4Feature(Feature feature, CoreassetsAndFeaturesDao featuresInCoreassetsDao, CoreAssetDao caDao){
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
				System.out.println("The path: "+ca.getPath());
				FileUtils.writeToFile(pathToResource+"/kdiff-workspace/baseline/"+ca.getPath(),Formatting.decodeFromBase64(ca.getContent()));
			}	
		}
		return baselineCoreAssets;
	}
	
	
	
	
	/*************************************************************//*************************************************************/
	/*************************************************************//*************************************************************/
	/***Non used functions ***/
	private void singlePatchPrueba(ProductRelease theirs, ProductRelease mine , Feature feature, CustomsByFeatureAndCoreAssetDao customsDao, CoreassetsAndFeaturesDao featuresInCoreassetsDao, CoreAssetDao caDao) {
		//Changes for THEIRS
		Iterator<CustomsByFeatureAndCoreAsset> customs = customsDao.getCustomsByIdproductreleaseAndIdfeature(theirs.getId_productrelease(),feature.getIdfeature()).iterator();
		CustomsByFeatureAndCoreAsset custom;
		
		System.out.println("THEIR CHANGES:");
		while(customs.hasNext()) {
			custom = customs.next();
			System.out.println("Asset "+custom.getCaname()+ " changed by "+custom.getPrname()+ " for feature "+custom.getIdfeature()+"with patch: "+Formatting.decodeFromBase64(custom.getCustom_diff()));
		}
		
		System.out.println("OUR CHANGES:");
		Iterator<CustomsByFeatureAndCoreAsset> ours = customsDao.getCustomsByIdproductreleaseAndIdfeature(theirs.getId_productrelease(),feature.getIdfeature()).iterator();
		CustomsByFeatureAndCoreAsset our;
		while(ours.hasNext()) {
			our = ours.next();
			System.out.println("Asset "+our.getCaname()+ " changed by " +our.getPrname()+ " for feature "+our.getIdfeature()+"with patch: "+Formatting.decodeFromBase64(our.getCustom_diff()));
		}
		
	
		
		
	}
	
}
