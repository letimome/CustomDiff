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
import customs.models.ReuseLevelDao;
import customs.models.ReuseLevel;
import customs.models.SPLdao;


@Controller
public class ReuseLevelController {
	
	
	 @Autowired private SPLdao SPLdao;
	 @Autowired private ReuseLevelDao reuseLevelDao;
	 @Autowired private NotCustomizedProductAssetsDao notCustAssetsDao;
	 
	 private String pathToResource = "./src/main/resources/static/";

	 
	 @RequestMapping("reuseLevelView")
	   public String getTreeMapTrafficLight(
	   				//@RequestParam(value="base", required=false) String idbaseline,
			   		@RequestParam(value="zoom", required=false) String zoom,
			   		@RequestParam(value="pr", required=false) String productrelease,
	   				Model model){
		 
		   System.out.println("The productrelease to analyze is: "+productrelease);
		   Iterable<ReuseLevel> customizedAssets = reuseLevelDao.getCustomsByIdrelease (productrelease);
		   Iterator<ReuseLevel> itCust  = customizedAssets.iterator();
		   
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
		   String csvContent="id,value,operation";
		   Iterator<String> ite = paths.iterator();
		   while (ite.hasNext()) {
			   csvContent= csvContent.concat("\n"+ite.next()+",");
		   }
		   
		   itCust  = customizedAssets.iterator();
		   ReuseLevel custo ;
		   
		   while(itCust.hasNext()) {//getting lines for Customized Assets
			   custo = itCust.next();
			   csvContent = csvContent.concat("\n"+custo.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+","+
			   custo.getLocs()+","+custo.getOperation().toLowerCase());
			   csvContent = csvContent.concat("\n"+custo.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+","+
					   Math.abs(custo.getPa_size()-custo.getCa_size())+",reused");
		   }
		   
		   it  = unCustomizedPAs.iterator();
		   NotCustomizedProductAssets notCust;
			  while(it.hasNext()) { //getting lines for not customized ones;
			       notCust = it.next();
			       csvContent = csvContent.concat("\n"+notCust.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+
			       ","+notCust.getSize()+",reused");
			       csvContent = csvContent.concat("\n"+notCust.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+",0,added");
			       csvContent = csvContent.concat("\n"+notCust.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+",0,removed");
			  }		   
		  
		   System.out.println(paths);
		   System.out.println(csvContent);
		   customs.utils.FileUtils.writeToFile(pathToResource+"reuseLevel.csv",csvContent);//path and test
		   
		   return "reuseLevel"; 
	 	}

}
