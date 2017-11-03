package customs.controllers.alluvial;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import customs.models.Churn_PoductPortfolioAndFeatures;
import customs.models.Churn_PoductPortfolioAndFeaturesDao;
import customs.models.Feature;
import customs.models.FeatureDao;
import customs.models.ParentFeature;
import customs.models.ParentFeatureDao;
import customs.models.ProductRelease;
import customs.models.ProductReleaseDao;



@Controller
public class Alluvial_Features_PP_Controller {
	@Autowired private Churn_PoductPortfolioAndFeaturesDao alluvialDao;
    
	private String pathToResource = "./src/main/resources/static/";
    @Autowired private ProductReleaseDao prDao;
    @Autowired private FeatureDao fDao;
    @Autowired private ParentFeatureDao parentFeatureDao;
	
	@RequestMapping("diff_features_pp")
	   public String getMainAlluvialView ( 	   		
			   @RequestParam(value="idparentfeature", required=true) int idparentfeature,
			   @RequestParam(value="from", required=false) String from,
			   @RequestParam(value="filter", required=false) String filter
			   , Model model){
		   
		   
		   ArrayList<String> featuresToInclude ;
		   if (filter==null)   featuresToInclude=null;
		   else {
			   featuresToInclude = customs.utils.Formatting.stringToArrayList(filter, ",");
			   System.out.println(featuresToInclude.toString());
		   }
		   
		   Iterable<Churn_PoductPortfolioAndFeatures> customsObj = alluvialDao.findAll();
		   Iterator<Churn_PoductPortfolioAndFeatures> it = customsObj.iterator();
		   String csvCustoms= "source,target,value,idparentfeature,idproductrelease";
		   Churn_PoductPortfolioAndFeatures custo;
		   
		   ArrayList<String> customizedfeatures=new ArrayList<>() ;
		   ArrayList<String> customizedproductreleases = new ArrayList<>();
		   boolean include=false;
		   Feature f;
		   while (it.hasNext()) {
			    custo=it.next();
			 
			    include=false;
			   if( featuresToInclude==null)  include=true;
			   else if (featuresToInclude.contains(custo.getFeaturemodified())) include=true;
			   f = fDao.getFeatureByName(custo.getFeaturemodified());
			  if(custo.getFeaturemodified()!=null
					     && f.getIdparent()==idparentfeature
					  	 && !custo.getFeaturemodified().equals("null")
					     && !custo.getFeaturemodified().equals("undefined") && include) {
				    	   csvCustoms = csvCustoms.concat ("\n" +custo.getFeaturemodified()+","+custo.getPr_name() +","+custo.getChurn()
				    	   +","+idparentfeature+","+custo.getId_pr());
				    	  
				    	   customizedproductreleases.add(custo.getPr_name());
				    	   customizedfeatures.add(custo.getFeaturemodified());
			   }     
		   }
		  
		//   if(filter==null) csvCustoms = csvCustoms.concat(extractCSVForNotCustomizedProducts(customizedproductreleases));
		  
		   csvCustoms = csvCustoms.concat(extractCSVForNotCustomizedFeatures(customizedfeatures, idparentfeature,featuresToInclude));
		 
		  customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvCustoms);//path and test
		  ParentFeature pf = parentFeatureDao.getParentFeatureByIdparentfeature(idparentfeature);
		  
		  addFilteredFeatureToTheModel(model,featuresToInclude);
		  model.addAttribute("maintitle","How are "+pf.getName()+"'s features being customized by the products?");
		  model.addAttribute("from",from);
		  model.addAttribute("idparentfeature",idparentfeature);
		  String filterJson = customs.utils.FeaturesToJason.getJsonForFeatures(fDao.getFeaturesByIdparent(idparentfeature));
		  
		  model.addAttribute("filterJson",filterJson);
		  customs.utils.NavigationMapGenerator.generateNavigationMapForAlluvial();
		  
		  System.out.println(csvCustoms);
		  return "alluvials/diff_features_pp";
		  
	 	}
	   private void addFilteredFeatureToTheModel(Model model, ArrayList<String> featuresToInclude) {
	
		if (featuresToInclude==null) return;
		Iterator<String> it = featuresToInclude.iterator();
		ArrayList<Feature> features=new ArrayList<Feature>();
		String f; Feature fe;
		while(it.hasNext()) {
			f= it.next();
			fe=fDao.getFeatureByName(f);
			if (fe!=null) features.add(fe);
		}
		
		model.addAttribute("features", features);
		System.out.println(features.toString());
	}

	private String extractCSVForNotCustomizedFeatures( ArrayList<String> customizedfeatures, int idparentfeature, ArrayList<String> featuresToInclude) {
		String csv_notcustomizedFeatures="";
		
		Iterator<Feature> it = fDao.findAll().iterator();
		Feature f;
		
		while (it.hasNext()) {
			f = it.next();
			if(featuresToInclude==null && f.getIdparent()==idparentfeature) {//if no filter available
				if (!customizedfeatures.contains(f.getIdfeature()))
						csv_notcustomizedFeatures=csv_notcustomizedFeatures.concat("\n"+f.getIdfeature()+",NOT_CUSTOMIZED,0.2");
			}
			else //if filter available
			if (   (!customizedfeatures.contains(f.getIdfeature())) 
					&& (featuresToInclude!=null) && (f.getIdparent()==idparentfeature)
					&& (featuresToInclude.contains(f.getIdfeature()))
				) {
				csv_notcustomizedFeatures=csv_notcustomizedFeatures.concat("\n"+f.getIdfeature()+",NOT_CUSTOMIZED,0.2");
			}
		}
		return csv_notcustomizedFeatures;
	}
	
	
}
