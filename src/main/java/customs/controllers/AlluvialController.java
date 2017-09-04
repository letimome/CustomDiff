package customs.controllers;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import customs.models.Alluvial;
import customs.models.AlluvialDao;
import customs.models.BaselineDao;
import customs.models.Coreassetbaseline;
import customs.models.Product;
import customs.models.ProductDao;


@Controller
public class AlluvialController {
	   
	   @RequestMapping("alluvials")
	 	public @ResponseBody Iterable<Alluvial> getAllCustoms() {
	 		// This returns a JSON or XML with the users
	 		
		   //write to local path
		   Iterable<Alluvial> customsOb = alluvialDao.findAll();
		   Iterator<Alluvial> it = customsOb.iterator();
		   String csvCustoms= "source,target,value";
		   Alluvial custo;
		   while (it.hasNext()) {
			   custo= it.next();
			   csvCustoms = csvCustoms.concat("\n"+custo.getName()+","+custo.getFeatureModified()+","+custo.getChurn());
		   }
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvCustoms);//path and test
		   return customsOb;
	 	}
	   
	   @RequestMapping("alluvialView")
	   public String getCustomsByBaselineId
	   		(@RequestParam(value="idbaseline", required=false) String idbaseline, Model model){
		   System.out.println("THIS IS idBbSeline: "+idbaseline);
		   
		   Iterable<Alluvial> customsObj = alluvialDao.findAll();
		   Iterator<Alluvial> it = customsObj.iterator();
		   String csvCustoms= "source,target,value";
		   Alluvial custo;
		   while (it.hasNext()) {
			   custo= it.next();
			   if(custo.getIdbaseline().equals(idbaseline))
			     if(custo.getFeatureModified()!=null && !custo.getFeatureModified().equals("null") && !custo.getFeatureModified().equals("undefined"))
				   csvCustoms = csvCustoms.concat("\n"+custo.getName()+","+custo.getFeatureModified()+","+custo.getChurn()+"");
		   }
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvCustoms);//path and test
		   
		  return "alluvial";
		  
	 	}
	   
	   
	   @RequestMapping("alluvialView2")
	   public String getCustomsByBaselineId2
	   		(@RequestParam(value="idbaseline", required=false) String idbaseline, Model model){
		   System.out.println("THIS IS idBbSeline: "+idbaseline);
		   
		   Iterable<Alluvial> customsObj = alluvialDao.findAll();
		   Iterator<Alluvial> it = customsObj.iterator();
		   String csvCustoms= "source,target,value";
		   Alluvial custo;
		   while (it.hasNext()) {
			   custo= it.next();
			   if(custo.getIdbaseline().equals(idbaseline))
			     if(custo.getFeatureModified()!=null && !custo.getFeatureModified().equals("null") && !custo.getFeatureModified().equals("undefined"))
				   csvCustoms = csvCustoms.concat("\n"+custo.getName()+","+custo.getFeatureModified()+","+custo.getChurn()+"");
		   }
		   customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvCustoms);//path and test
		   
		  return "alluvial2";
		  
	 	}
	  


	   @RequestMapping("alluvial/baselines")
	 		public   String getAllCoreAssetBselines( Model model) {
	 			
			// This returns a JSON or XML with the users
	 		  Iterable<Coreassetbaseline> baselines =  baselineDao.findAll();
	 			model.addAttribute("baselines", baselines);
	 			return "alluvial"; //baselines html
	 	}
	   
	   @RequestMapping("alluvial/products")
		public   String getAllProductsByBaseline( Model model) {
			
		// This returns a JSON or XML with the users
		  Iterable<Product> products =  productDao.findAll();
			model.addAttribute("products", products);
			return "alluvial"; //baselines html
	}
	   // ------------------------
	   // PRIVATE FIELDS
	   // ------------------------
	   @Autowired
	   private AlluvialDao alluvialDao;
	   private ProductDao productDao;
	   @Autowired private BaselineDao baselineDao;
	   private String pathToResource = "./src/main/resources/static/";
}
