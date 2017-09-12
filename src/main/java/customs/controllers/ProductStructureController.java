package customs.controllers;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.CustomizationsByVPandPR;
import customs.models.CustomizationsByVPandPRDao;
import customs.models.NotCustomizedProductAssetsDao;
import customs.models.ProductAsset;
import customs.models.ProductAssetDao;
import customs.models.ReuseLevelDao;
import customs.models.SPLdao;
import customs.models.TreeMapLights;
import customs.models.VariationPoint;
import customs.models.VariationPointDao;
import customs.utils.Formatting;

@Controller
public class ProductStructureController {

	 
	 @Autowired private CustomizationsByVPandPRDao prsAndVps;
	 @Autowired private VariationPointDao variationPointDao;
	 @Autowired private ProductAssetDao productAssetDao;
	 @Autowired private SPLdao SPLdao;
	 private String pathToResource = "./src/main/resources/static/";

	 
	  @RequestMapping("product_structure")
	   public String getTreeMapTrafficLight(
	   				@RequestParam(value="pr", required=false) String idrelease,
	   				Model model){
		   System.out.println("This IS idrelease: "+idrelease);
		   
		   Iterable<CustomizationsByVPandPR> customsObj = prsAndVps.getCustomizationByInproduct(idrelease);
		   Iterator<CustomizationsByVPandPR> it = customsObj.iterator();
		   
		   String csvContent="id,value,size";//id,value,size
		   ArrayList<String> paths = new ArrayList<>();
		   CustomizationsByVPandPR custo;
		   VariationPoint vp;
		   String path;
		   String csvCustoms= "";
		   ArrayList<String> filePaths=new ArrayList<String>();
		   while (it.hasNext()) {
			  custo= it.next();
			  ProductAsset pa = productAssetDao.getProductAssetByIdproductasset(custo.getFilechanged());
			  vp = variationPointDao.getVariationPointByIdvariationpoint(custo.getIdvariationpoint());
			  path = pa.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","");
			  
			  if(!filePaths.contains(pa.getPath())) {
				  filePaths.add(pa.getPath());
				  csvCustoms=  csvCustoms.concat("\n" +pa.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+",");
			  }
			  //clave
			  csvCustoms = csvCustoms.concat("\n"+path+"/" + Formatting.decodeFromBase64(vp.getExpression()).
					  split("//")[1]+","+ custo.getChurn());
			  paths.add(path);
			  
		   }
		   System.out.println("Paths");
		 
		   
		   ArrayList<String>  paths2 = customs.utils.Formatting.extractMiniPaths(paths);
		   System.out.println(paths);
		   System.out.println(paths2);
		
		   
		   
		   for (int i=0; i< paths2.size();i++) {
			   if(!paths2.get(i).equals(""))
			     csvContent=csvContent.concat("\n"+paths2.get(i)+",");
		   }
		
		   csvContent=csvContent.concat(csvCustoms);
		   System.out.println(paths2);
		 
		   customs.utils.FileUtils.writeToFile(pathToResource+"productstructure.csv",csvContent);//path and test
		   
		  return "productVariationPoints"; 
	 	}
}
