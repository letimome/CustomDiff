package customs.controllers.alluvial;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.Alluvial;
import customs.models.AlluvialDao;
import customs.models.BaselineDao;
import customs.models.Feature;
import customs.models.FeatureDao;
import customs.models.ProductAssetDao;
import customs.models.ProductDao;
import customs.models.ProductRelease;
import customs.models.ProductReleaseDao;
import customs.models.SPLdao;
import customs.models.VariationPointDao;



//the root controller for the alluvial diagram view
@Controller
public class Alluvial_Platform_PP_Controller {
	@Autowired private AlluvialDao alluvialDao;
    private String pathToResource = "./src/main/resources/static/";
    @Autowired private ProductReleaseDao prDao;
    @Autowired private FeatureDao fDao;
	
	@RequestMapping("diff_features_pp")
	   public String getMainAlluvialView (@RequestParam(value="idbaseline", required=false) String idbaseline, 
	   		@RequestParam(value="filter", required=false) String filter,
	   		@RequestParam(value="fmLevel", required=false) String depth,
	   		Model model){
		   
		   System.out.println("Baseline: "+idbaseline); System.out.println("FeatureList: "+filter);
		   
		   ArrayList<String> featuresToInclude ;
		   if (filter==null)   featuresToInclude=null;
		   else {
			   featuresToInclude = customs.utils.Formatting.stringToArrayList(filter, ",");
			   System.out.println(featuresToInclude.toString());
		   }

		   
		   Iterable<Alluvial> customsObj = alluvialDao.findAll();
		   Iterator<Alluvial> it = customsObj.iterator();
		   String csvCustoms= "source,target,value";
		   Alluvial custo;
		   
		   ArrayList<String> customizedfeatures=new ArrayList<>() ;
		   ArrayList<String> customizedproductreleases = new ArrayList<>();
		   boolean include=false;
		   
		   while (it.hasNext()) {
			    custo=it.next();
			    System.out.println((custo.getIdBaseline()));
			    include=false;
			   if( featuresToInclude==null)  include=true;
			   else if (featuresToInclude.contains(custo.getFeatureModified())) include=true;
			    	 
			   if(custo.getIdbaseline().equals(idbaseline)) {
				   if(custo.getFeatureModified()!=null && !custo.getFeatureModified().equals("null")
						     && !custo.getFeatureModified().equals("undefined") && include) {
						    	   csvCustoms = csvCustoms.concat ("\n" +custo.getFeatureModified()+","+custo.getIdRelease() +","+custo.getChurn()+"");
						    	   customizedproductreleases.add(custo.getIdRelease());
						    	   customizedfeatures.add(custo.getFeatureModified());
						     }
			   }
			     
		   }
		  //if there is a filter, then do not show products which has not been customized.
		   if(filter==null) csvCustoms = csvCustoms.concat(extractCSVForNotCustomizedProducts(idbaseline,customizedproductreleases));
		  
		   csvCustoms = csvCustoms.concat(extractCSVForNotCustomizedFeatures(idbaseline,customizedfeatures, featuresToInclude));
		 
		  customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvCustoms);//path and test
		  
		  addFilteredFeatureToTheModel(model,featuresToInclude);
		  
		  customs.utils.NavigationMapGenerator.generateNavigationMapForAlluvial();
		  
		  System.out.println(csvCustoms);
		  return "alluvials/diff_features_pp";
		  
	 	}
	   private void addFilteredFeatureToTheModel(Model model, ArrayList<String> featuresToInclude) {
		// TODO Auto-generated method stub
		if (featuresToInclude==null) return;
		//  result += "<span class='selectedName'>" + checkedNodes[i].FullName + "</span>";
		System.out.println("SEGUIDOOOO");
		String result="";
		Iterator<String> it = featuresToInclude.iterator();
		ArrayList<Feature> features=new ArrayList<Feature>();
		String f; Feature fe;
		while(it.hasNext()) {
			f= it.next();
			fe=fDao.getFeatureByIdfeature(f);
			if (fe!=null) features.add(fe);
		}
		
		model.addAttribute("features", features);
		System.out.println(features.toString());
	}

	private String extractCSVForNotCustomizedFeatures(String idbaseline, ArrayList<String> customizedfeatures, ArrayList<String> featuresToInclude) {
		String csv_notcustomizedFeatures="";
		
		Iterator<Feature> it = fDao.findAll().iterator();
		Feature f;
		
		while (it.hasNext()) {
			f = it.next();
			if(featuresToInclude==null) {//if no filter available
				if (!customizedfeatures.contains(f.getIdfeature()))
						csv_notcustomizedFeatures=csv_notcustomizedFeatures.concat("\n"+f.getIdfeature()+",NOT_CUSTOMIZED,0.2");
			}
			else //if filter available
			if (!customizedfeatures.contains(f.getIdfeature()) && featuresToInclude.contains(f.getIdfeature())) {
				csv_notcustomizedFeatures=csv_notcustomizedFeatures.concat("\n"+f.getIdfeature()+",NOT_CUSTOMIZED,0.2");
			}
		}
		return csv_notcustomizedFeatures;
	}
	
	private String extractCSVForNotCustomizedProducts(String idbaseline, ArrayList<String> customizedproductreleases) {
		String csv_notcustomizedprs="";
		Iterator<ProductRelease> it = prDao.findAll().iterator();
		ProductRelease pr;
		while(it.hasNext()) {
			pr=it.next();
			if(!customizedproductreleases.contains(pr.getIdrelease())) {
				csv_notcustomizedprs=csv_notcustomizedprs.concat("\n"+"NOT_CUSTOMIZED,"+pr.getIdrelease()+",0.2");
			}
		}
		return csv_notcustomizedprs;
	}
}
