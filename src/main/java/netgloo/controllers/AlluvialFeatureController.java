package netgloo.controllers;

import java.util.Iterator;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import netgloo.models.AlluvialFeatureView;
import netgloo.models.AlluvialFeatureViewDao;


@Controller
public class AlluvialFeatureController {
	   

	   @RequestMapping("alluvials")
	 	public @ResponseBody Iterable<AlluvialFeatureView> getAllCustoms() {
	 		// This returns a JSON or XML with the users
	 		
		   //write to local path
		   Iterable<AlluvialFeatureView> customs = alluvialDao.findAll();
		   Iterator<AlluvialFeatureView> it = customs.iterator();
		   String csvCustoms= "source,target,value";
		   AlluvialFeatureView custo;
		   while (it.hasNext()) {
			   custo= it.next();
			   csvCustoms = csvCustoms.concat("\n"+custo.getName()+","+custo.getFeatureModified()+","+custo.getChurn());
		   }
		   onekin.utils.FileUtils.writeToFile("./src/main/resources/static/alluvial.csv",csvCustoms);//path and test
		   return customs;
	 	}
	   
	   @RequestMapping("alluvialView")
	   public String getCustomsByBaselineId
	   		(@RequestParam(value="idbaseline", required=false) String idbaseline, Model model){
		   System.out.println("THIS IS idBbSeline: "+idbaseline);
		   
		   Iterable<AlluvialFeatureView> customs = alluvialDao.findAll();
		   Iterator<AlluvialFeatureView> it = customs.iterator();
		   String csvCustoms= "source,target,value";
		   AlluvialFeatureView custo;
		   while (it.hasNext()) {
			   custo= it.next();
			   if(custo.getIdbaseline().equals(idbaseline))
			     csvCustoms = csvCustoms.concat("\n"+custo.getName()+","+custo.getFeatureModified()+","+custo.getChurn());
		   }
		   onekin.utils.FileUtils.writeToFile("./src/main/resources/templates/alluvial.csv",csvCustoms);//path and test
		   
		  return "alluvial";
		  
	 	}
	  
	   // ------------------------
	   // PRIVATE FIELDS
	   // ------------------------

	   
	   
	   @Autowired
	   private AlluvialFeatureViewDao alluvialDao;
}
