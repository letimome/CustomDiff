package netgloo.controllers;

import java.util.Iterator;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import netgloo.models.AlluvialFeatureView;
import netgloo.models.AlluvialFeatureViewDao;


@Controller
public class AlluvialFeatureController {
	   

	   @RequestMapping("alluvial/get-all")
	 	public @ResponseBody Iterable<AlluvialFeatureView> getAllCustoms() {
	 		// This returns a JSON or XML with the users
	 		
		   //write to local path
		   Iterable<AlluvialFeatureView> customs = alluvialDao.findAll();
		   Iterator<AlluvialFeatureView> it = customs.iterator();
		   String csvCustoms= "source,target,value\n";
		   AlluvialFeatureView custo;
		   while (it.hasNext()) {
			   custo= it.next();
			   csvCustoms = csvCustoms.concat(custo.getName()+","+custo.getFeatureModified()+","+custo.getChurn()+"\n");
		   }
		   onekin.utils.FileUtils.writeToFile("./src/main/resources/static/alluvial.csv",csvCustoms);//path and test
		   return customs;
	 	}

	   public @ResponseBody Iterable<AlluvialFeatureView> getAllCustomsById() {
	 		// This returns a JSON or XML with the users	 		
		  return null;
		  
	 	}
	   

	   // ------------------------
	   // PRIVATE FIELDS
	   // ------------------------

	   
	   
	   @Autowired
	   private AlluvialFeatureViewDao alluvialDao;
}
