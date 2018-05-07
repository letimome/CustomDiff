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
	//private String workspacePath = "/Users/Onekin/Download";
	
	public ThreeWayDiffWorkspace patchFilesForFeatureAndProduct(ProductRelease yours, ProductRelease mine , Feature feature, CustomsByFeatureAndCoreAssetDao customsDao,
			CoreassetsAndFeaturesDao featuresInCoreassetsDao, CoreAssetDao caDao) {
		//step 0 : delete directory
		
		
		deleteDir(new File(pathToResource));
		
		//Step 1: get all the files in which the "feature" is present
		System.out.println("Getting the baseline files for "+yours.getName()+"  mine:"+mine.getName()+ "  feature"+feature.getName());
		ArrayList<CoreAsset> baselineFiles = getBaselineCoreAssets4Feature(feature, featuresInCoreassetsDao, caDao, yours.getName(), mine.getName());
		
			
		ArrayList<CoreAsset> yoursFiles = patchingCustomizationsForProduct(yours,feature, baselineFiles,customsDao,featuresInCoreassetsDao, caDao);
		ArrayList<CoreAsset> myFiles = patchingCustomizationsForProduct(mine,feature, baselineFiles,customsDao,featuresInCoreassetsDao, caDao);
		//Create workspace
		
		ThreeWayDiffWorkspace kdiff3 = new ThreeWayDiffWorkspace( baselineFiles, myFiles, yoursFiles, feature.getIdfeature(), 
				"baseline", mine.getName(), yours.getName());
		try {
			customs.utils.ZipUtils zip = new customs.utils.ZipUtils();
			zip.pack(pathToResource, pathToResource+"kdiff.zip");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return kdiff3;
		
		
	}



	private void deleteDir(File file)  {
		try {
			System.out.println("Deleting folder "+pathToResource);
			 File[] contents = file.listFiles();
			    if (contents != null) {
			        for (File f : contents) {
			            deleteDir(f);
			        }
			    }
			    file.delete();
				System.out.println("delete "+file.getName());
			
		}catch (Exception e) {
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
				
				FileUtils.writeToFile(this.pathToResource+"patches/"+product.getName()+"/patch"+i+".patch", patch.replace("\t", " "));
				File initialFile = new File (this.pathToResource+"patches/"+product.getName()+"/patch"+i+".patch");
				
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
			
				return patchedFiles;
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
		return patchedFiles;
		
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

	public ArrayList<CoreAsset> getBaselineCoreAssets4Feature(Feature feature, CoreassetsAndFeaturesDao featuresInCoreassetsDao, CoreAssetDao caDao, 
			String observed,String observer){
		//find all the assets that belong to a feature
		Iterator<CoreassetsAndFeatures> res =featuresInCoreassetsDao.findAll().iterator(); //featuresInCoreassetsDao.getFeatureCoreAssetsByIdfeature(feature.getIdfeature()).iterator();
		CoreassetsAndFeatures caFeature;
		ArrayList<CoreAsset> baselineCoreAssets = new ArrayList<CoreAsset>();
		CoreAsset ca;
	//	System.out.println("RES has next:"+res.hasNext());
		while(res.hasNext()) {
			caFeature = res.next();
			//System.out.println("CA: "+caFeature.getCa_name());
			ca = caDao.getCoreAssetByIdcoreasset(caFeature.getId_coreasset());
			if ( caFeature.getId_feature().equals(feature.getIdfeature()) && (!baselineCoreAssets.contains(ca))) {
				baselineCoreAssets.add(ca);
				System.out.println("CA path getBaselineCoreAssets4Feature: "+ca.getPath());
			//	System.out.println(" & ca content: "+ca.getContent());
				//write to all the folders: baseline, observed & observer
				FileUtils.writeToFile(pathToResource+"baseline/"+ca.getPath(), ca.getContent());
				FileUtils.writeToFile(pathToResource+observed+"/"+ca.getPath(),ca.getContent());
				FileUtils.writeToFile(pathToResource+observer+"/"+ca.getPath(),ca.getContent());
			}	
		}	
		return baselineCoreAssets;
	}
	
	
	
	
}
