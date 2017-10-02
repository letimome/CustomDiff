package customs.controllers;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.NotCustomizedProductAssets;
import customs.models.NotCustomizedProductAssetsDao;
import customs.models.ProductAsset;
import customs.models.ProductAssetDao;
import customs.models.ProductDao;
import customs.models.ProductReleaseDao;
import customs.models.CustomizationsGByOperationDao;
import customs.models.CustomizationsGByPRandFile;
import customs.models.CustomizationsGByPRandFileDao;
import customs.models.CustomizationsGByOperation;
import customs.models.SPLdao;
import customs.utils.Formatting;


@Controller
public class ReuseLevelController {
	
	
	 @Autowired private SPLdao SPLdao;	
	 @Autowired private NotCustomizedProductAssetsDao notCustAssetsDao;
	 @Autowired private ProductAssetDao paDao;
	 @Autowired private ProductReleaseDao prDao;
	 @Autowired private CustomizationsGByPRandFileDao customsByPROp;
	 @Autowired private CustomizationsGByOperationDao customsByFileOp;
	 
	 private String pathToResource = "./src/main/resources/static/";

	 @RequestMapping("reuseLevelView")
	   public String getTreeMapForProductRelease(
	   				//@RequestParam(value="base", required=false) String idbaseline,
			   		@RequestParam(value="zoom", required=false) String zoom,
			   		@RequestParam(value="pr", required=false) String productrelease,
	   				Model model){
		 
		   System.out.println("The productrelease: "+productrelease);
		   
		   Iterator<ProductAsset> it = paDao.findAll().iterator(); //all product asset
		   ProductAsset pa;
		   ArrayList<String>  paths =new ArrayList<String>();
		   String csvheader="id,value,operation,pr,p_asset_id";
		   ArrayList<String> initialPaths= new ArrayList<>();
		   String csvInitialPaths="";
		   String csvContent="";
		   String filepath;
		   while (it.hasNext()) {//for each product asset
			   pa =  it.next();
			   if(pa.getProductrelease_idrelease().equals(productrelease)) {
				   filepath=pa.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "");
				   paths.add(filepath);
				   csvContent = csvContent.concat("\n"+filepath+",0.2"
				   //+pa.getSize()/4
				   ); //insert the path + size
				   
			   }
		   }

		   //csvContent = csvContent.concat(computeCSVForCustomization(productrelease));//customizations splited into added/deleted
		   csvContent = csvContent.concat(computeCSVForCustomizationChurn(productrelease));
		   
		   
		   initialPaths = Formatting.extractPathsFromPathListWitoutFilePath(paths);
		   Iterator<String> ite = initialPaths.iterator();
		   while (ite.hasNext()) {
			   csvInitialPaths= csvInitialPaths.concat("\n"+ite.next()+",");
		   }
		   
		   System.out.println(csvheader+csvInitialPaths+csvContent);
		   customs.utils.FileUtils.writeToFile(pathToResource+"reuseLevel.csv",csvheader+csvInitialPaths+csvContent);//path and test
		   model.addAttribute("pr",productrelease);
		   model.addAttribute("maintitle", "Which assets are customized by '"+productrelease+"'?");
		   model.addAttribute("difftitle", "diff(Baseline-v1.0, "+productrelease+")");
		   return "reuseLevel2"; 
	 	}

	 private String computeCSVForCustomizationChurn(String productrelease) {
			//	  String csvheader="id,value,operation,pr,p_asset_id";
			String csvCustoms="";
			   System.out.println("The productrelease to analyze is: "+productrelease);

			   Iterable<CustomizationsGByPRandFile>  customizedAssets = customsByPROp.getCustomsByIdrelease (productrelease);
			   Iterator<CustomizationsGByPRandFile> itCust= customizedAssets.iterator();
			   itCust  = customizedAssets.iterator();
			   CustomizationsGByPRandFile custo;
			   
			   while(itCust.hasNext()) {//getting lines for Customized Assets
				   custo = itCust.next();
				   csvCustoms = csvCustoms.concat("\n"+custo.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+","+
				   custo.getChurn()+",churn"//+custo.getOperation().toLowerCase()
				   +","+productrelease+","+custo.getIdproductasset());
			   }
			return csvCustoms;
		}
	 
	private String computeCSVForCustomization(String productrelease) {
		//	   String csvheader="id,value,operation,pr,p_asset_id";
		String csvCustoms="";
		
		Iterator<CustomizationsGByOperation> it = customsByFileOp.getCustomsByIdrelease(productrelease).iterator();
		CustomizationsGByOperation custom;
		String path;
		while(it.hasNext()) {
			custom=it.next();
			path = custom.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "");
			csvCustoms = csvCustoms.concat("\n"+path.concat("/"+custom.getOperation())+
					","+custom.getLocs()+","+custom.getOperation()+","+productrelease+","+custom.getIdproductasset()
					);
		}
		return csvCustoms;
	}

	
	/****  Old One*****/
	 @RequestMapping("reuseLevelOld")
	   public String getTreeMapTrafficLight(
	   				//@RequestParam(value="base", required=false) String idbaseline,
			   		@RequestParam(value="zoom", required=false) String zoom,
			   		@RequestParam(value="pr", required=false) String productrelease,
	   				Model model){
		 
		   System.out.println("The productrelease to analyze is: "+productrelease);
		   
		   Iterable<CustomizationsGByOperation> customizedAssets = customsByFileOp.getCustomsByIdrelease (productrelease);
		   Iterator<CustomizationsGByOperation> itCust  = customizedAssets.iterator();
		   
		   Iterable<NotCustomizedProductAssets> unCustomizedPAs = notCustAssetsDao.getNotCustomizedAssetsByIdrelease(productrelease);
		   Iterator<NotCustomizedProductAssets> it  = unCustomizedPAs.iterator();
		   ArrayList<String>  notCustomPaths=new ArrayList<>();
		   ArrayList<String>  customPaths=new ArrayList<>();
		   
		   while (it.hasNext())//getting paths for not customized assets
			   notCustomPaths.add(it.next().getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", ""));
		   
		   while(itCust.hasNext())//getting paths for customized assets
			   customPaths.add(itCust.next().getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", ""));
			   
		   ArrayList<String>  paths ,aux=new ArrayList<String>();
		   aux.addAll(notCustomPaths); 
		   aux.addAll(customPaths);
		   
		   
		   paths = customs.utils.Formatting.extractMiniPaths(aux);
		   String csvContent="id,value,operation,pr,p_asset_id";
		   Iterator<String> ite = paths.iterator();
		   while (ite.hasNext()) {
			   csvContent= csvContent.concat("\n"+ite.next()+",");
		   }
		   
		   itCust  = customizedAssets.iterator();
		   CustomizationsGByOperation custo ;
		   
		   while(itCust.hasNext()) {//getting lines for Customized Assets
			   custo = itCust.next();
			   csvContent = csvContent.concat("\n"+custo.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+","+
			   custo.getLocs()+","+custo.getOperation().toLowerCase()
			   +","+productrelease+","+custo.getIdproductasset());
			   
			   csvContent = csvContent.concat("\n"+custo.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+","+
					   Math.abs(custo.getPa_size()-custo.getCa_size())+","
					   +","+productrelease+","+custo.getIdproductasset());//TODO: Mirar!, parece que no va bien el comput
		   }
		   
		   it  = unCustomizedPAs.iterator();
		   System.out.println(zoom);
		   if(zoom!=null && zoom.equals("true")) {
			    NotCustomizedProductAssets notCust;
				  while(it.hasNext()) { //getting lines for not customized ones;
				       notCust = it.next();
				       csvContent = csvContent.concat("\n"+notCust.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+
				       ","+notCust.getSize()/5+",");
				       csvContent = csvContent.concat("\n"+notCust.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+"/added"+",0,added"
				    		   +","+productrelease+", "+notCust.getIdproductasset());
				       csvContent = csvContent.concat("\n"+notCust.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+"/removed,0,removed"
				    		   +","+productrelease+", "+notCust.getIdproductasset());
			  }	
		   }
		  
		   System.out.println(paths); System.out.println(csvContent);
		   customs.utils.FileUtils.writeToFile(pathToResource+"reuseLevel.csv",csvContent);//path and test
		   
		   model.addAttribute("pr",productrelease);
		  model.addAttribute("maintitle", "Which assets are customized by '"+productrelease+"'?");
		  model.addAttribute("difftitle", "diff(Baseline-v1.0.coreAssets(), "+productrelease+".productAssets())");
		   
		   return "reuseLevel2"; 
	 	}
	 

}
