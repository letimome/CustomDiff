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
import customs.models.ProductDao;
import customs.models.CustomizationsGByOperationDao;
import customs.models.CustomizationsGByOperation;
import customs.models.SPLdao;


@Controller
public class ReuseLevelController {
	
	
	 @Autowired private SPLdao SPLdao;
	 @Autowired private CustomizationsGByOperationDao reuseLevelDao;
	 @Autowired private NotCustomizedProductAssetsDao notCustAssetsDao;
	 
	 private String pathToResource = "./src/main/resources/static/";

	 
	 @RequestMapping("reuseLevelView")
	   public String getTreeMapTrafficLight(
	   				//@RequestParam(value="base", required=false) String idbaseline,
			   		@RequestParam(value="zoom", required=false) String zoom,
			   		@RequestParam(value="pr", required=false) String productrelease,
	   				Model model){
		 
		   System.out.println("The productrelease to analyze is: "+productrelease);
		   Iterable<CustomizationsGByOperation> customizedAssets = reuseLevelDao.getCustomsByIdrelease (productrelease);
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
					   Math.abs(custo.getPa_size()-custo.getCa_size())+",reused"
					   +","+productrelease+","+custo.getIdproductasset());//TODO: Mirar!, parece que no va bien el comput
		   }
		   
		   it  = unCustomizedPAs.iterator();
		   System.out.println(zoom);
		   if(zoom!=null && zoom.equals("true")) {
			    NotCustomizedProductAssets notCust;
				  while(it.hasNext()) { //getting lines for not customized ones;
				       notCust = it.next();
				       csvContent = csvContent.concat("\n"+notCust.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+
				       ","+notCust.getSize()+",reused");
				       csvContent = csvContent.concat("\n"+notCust.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+",0,added"
				    		   +","+productrelease+", "+notCust.getIdproductasset());
				       csvContent = csvContent.concat("\n"+notCust.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+",0,removed"
				    		   +","+productrelease+", "+notCust.getIdproductasset());
			  }	
		   }
		  
		   System.out.println(paths); System.out.println(csvContent);
		   customs.utils.FileUtils.writeToFile(pathToResource+"reuseLevel.csv",csvContent);//path and test
		   
		   model.addAttribute("pr",productrelease);
		  model.addAttribute("maintitle", "Which assets is '"+productrelease+"' customizing?");
		  model.addAttribute("difftitle", "diff(Baseline-v1.0.getAllAssets(), "+productrelease+".getAllAssets())");
		   
		   return "reuseLevel"; 
	 	}

}
