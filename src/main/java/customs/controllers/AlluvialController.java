package customs.controllers;

import java.util.Iterator;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import customs.models.Alluvial;
import customs.models.AlluvialDao;


@Controller
public class AlluvialController {
	   
	   @RequestMapping("alluvials")
	 	public @ResponseBody Iterable<Alluvial> getAllCustoms() {
	 		// This returns a JSON or XML with the users
	 		
		   //write to local path
		   Iterable<Alluvial> customs = alluvialDao.findAll();
		   Iterator<Alluvial> it = customs.iterator();
		   String csvCustoms= "source,target,value";
		   Alluvial custo;
		   while (it.hasNext()) {
			   custo= it.next();
			   csvCustoms = csvCustoms.concat("\n"+custo.getName()+","+custo.getFeatureModified()+","+custo.getChurn());
		   }
		   onekin.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvCustoms);//path and test
		   return customs;
	 	}
	   
	   @RequestMapping("alluvialView")
	   public String getCustomsByBaselineId
	   		(@RequestParam(value="idbaseline", required=false) String idbaseline, Model model){
		   System.out.println("THIS IS idBbSeline: "+idbaseline);
		   
		   Iterable<Alluvial> customs = alluvialDao.findAll();
		   Iterator<Alluvial> it = customs.iterator();
		   String csvCustoms= "source,target,value";
		   Alluvial custo;
		   while (it.hasNext()) {
			   custo= it.next();
			   if(custo.getIdbaseline().equals(idbaseline))
			     if(custo.getFeatureModified()!=null && !custo.getFeatureModified().equals("null") && !custo.getFeatureModified().equals("undefined"))
				   csvCustoms = csvCustoms.concat("\n"+custo.getName()+","+custo.getFeatureModified()+","+custo.getChurn()+"");
		   }
		   onekin.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvCustoms);//path and test
		   
		  return "alluvial";
		  
	 	}
	  
	   // ------------------------
	   // PRIVATE FIELDS
	   // ------------------------

	   
	   
	   @Autowired
	   private AlluvialDao alluvialDao;
	   private String pathToResource = "./src/main/resources/public/";
}
