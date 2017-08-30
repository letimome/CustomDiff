package customs.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import customs.models.SPLdao;
import customs.models.TreeMapLights;
import customs.models.TreeMapLightsDao;

@Controller
public class TreeMapLightsController {
	  @Autowired
	   private TreeMapLightsDao treemapLightDao;
	  @Autowired
	  private SPLdao SPLdao;
	   private String pathToResource = "./src/main/resources/public/";
	
	
	  @RequestMapping("treemapLightsView")
	   public String getCustomsByBaselineId (
	   				@RequestParam(value="base", required=false) String idbaseline,
	   				@RequestParam(value="fname", required=false) String featurenamemodified,
	   				Model model){
		   System.out.println("THIS IS featurenamemodified: "+featurenamemodified);
		   
		   Iterable<TreeMapLights> customs = treemapLightDao.getCustomsByIdbaseline(idbaseline);
		   Iterator<TreeMapLights> it = customs.iterator();
		   
		   String csvContent="id,value";//id,value,Freq.\ninput,
		   ArrayList<String> paths = new ArrayList<>();
		   TreeMapLights custo;
		   String csvCustoms= "";
		   while (it.hasNext()) {
			  custo= it.next();
			  if(custo.getFeaturemodified().equals(featurenamemodified) )
					  //||  custo.getFeaturemodified().equals("undefined")) 
			  {
			     csvCustoms = csvCustoms.concat("\n"+custo.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","")+","+custo.getChurn()+","+custo.getNumberofproductscustomizing());
			     paths.add(custo.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", ""));
			  	// System.out.println(custo.getPath()+","+custo.getFeaturemodified()+","+custo.getChurn());
			  	 }
		   }
		   ArrayList<String>  paths2 = extractMiniPaths(paths);
		   for (int i=0; i< paths2.size();i++) {
			   if(!paths2.get(i).equals(""))
			     csvContent=csvContent.concat("\n"+paths2.get(i)+",");
		   }
		   csvContent=csvContent.concat(csvCustoms);
		   System.out.println(paths2+"\n\n");
		 
		   onekin.utils.FileUtils.writeToFile(pathToResource+"treemapLights.csv",csvContent);//path and test
		   
		  return "treemapLights"; 
	 	}



	private ArrayList<String> extractMiniPaths(ArrayList<String> longPath) {
		ArrayList<String> splittedPaths = new ArrayList<>();
		Iterator<String> it = longPath.iterator();
		String filePath;
		String[] folders;
		while(it.hasNext()) {
			filePath = it.next();
			 folders = filePath.split("/");
			 for (int i=0; i < folders.length-1;i++) {
				 if ((i<=0) && (!splittedPaths.contains(folders[i])))
				   splittedPaths.add(folders[i]);
				 else 
					 if ((i>0) && (!splittedPaths.contains(folders[i-1]+"/"+folders[i])))
					   splittedPaths.add(folders[i-1]+"/"+folders[i]);
				} 
			 }
	   return splittedPaths;
	} 
}
//treemapLights.html