package customs.controllers.alluvial;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.Churn_AbstractFeatures_PP;
import customs.models.Churn_AbstractFeatures_PPDao;
import customs.models.ParentFeature;
import customs.models.ParentFeatureDao;
import customs.models.ProductRelease;
import customs.models.ProductReleaseDao;

@Controller
public class Alluvial_ParentFeatures_PP_Controller {
	

	 @Autowired private Churn_AbstractFeatures_PPDao churnCustoms;
	 @Autowired private ProductReleaseDao prDao;
	 @Autowired private ParentFeatureDao parentFeatureDao;

		 
	   private String pathToResource = "./src/main/resources/static/";
	
	 @RequestMapping("diff_parent_features_products")
	 public String getAlluvial_parent_feature_pp ( 	   		
			   @RequestParam(value="filter", required=false) 
			   String filter, @RequestParam(value="fmLevel", required=false) String depth, Model model){
		   
		   
		   ArrayList<String> featuresToInclude ;
		   if (filter==null)   featuresToInclude=null;
		   else {
			   featuresToInclude = customs.utils.Formatting.stringToArrayList(filter, ",");
			   System.out.println(featuresToInclude.toString());
		   }

		   
		   Iterable<Churn_AbstractFeatures_PP> customsObj = churnCustoms.findAll();
		   Iterator<Churn_AbstractFeatures_PP> it = customsObj.iterator();
		   String csvCustoms= "source,target,value,idparentfeature,idproductrelease";
		   Churn_AbstractFeatures_PP custo;
		   
		   ArrayList<String> customizedfeatures=new ArrayList<>() ;
		   ArrayList<String> customizedproductreleases = new ArrayList<>();
		   boolean include=false;
		   
		   
		   while (it.hasNext()) {
			    custo=it.next();
			 
			    include=false;
			   if( featuresToInclude==null)  include=true;
			   else if (featuresToInclude.contains(custo.getParentfeaturename())) include=true;
			    	 
			  if(custo.getParentfeaturename()!=null && !custo.getParentfeaturename().equals("null")
					     && !custo.getParentfeaturename().equals("undefined") && include) {
				    	   csvCustoms = csvCustoms.concat ("\n" +custo.getParentfeaturename()+","+custo.getPr_name() 
				    	   +","+custo.getChurn()+","+custo.getId_parentfeature()+","+custo.getId_pr());
				    	   
				    	   customizedproductreleases.add(custo.getPr_name());
				    	   customizedfeatures.add(custo.getParentfeaturename());
			   }
		   }
		  //if there is a filter, then do not show products which has not been customized.
		   if(filter==null) csvCustoms = csvCustoms.concat(extractCSVForNotCustomizedProducts(customizedproductreleases));
		  
		   csvCustoms = csvCustoms.concat(extractCSVForNotCustomizedFeatures(customizedfeatures, featuresToInclude));
		 
		  customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvCustoms);//path and test
		  String filterJson = customs.utils.FeaturesToJason.getJsonForParentFeatures(parentFeatureDao.findAll());
		 System.out.println("filterJson");
		 System.out.println(filterJson);
		  model.addAttribute("filterJson",filterJson);
		 
		  addFilteredFeatureToTheModel(model,featuresToInclude);
		  
		  customs.utils.NavigationMapGenerator.generateNavigationMapForAlluvial();
		  
		  System.out.println(csvCustoms);
		  return "alluvials/diff_parent_features_pp";
		  
	 	}
	 
	 
	 private String extractCSVForNotCustomizedFeatures( ArrayList<String> customizedfeatures, ArrayList<String> featuresToInclude) {
			String csv_notcustomizedFeatures="";
			
			Iterator<ParentFeature> it = parentFeatureDao.findAll().iterator();
			ParentFeature f;
			
			while (it.hasNext()) {
				f = it.next();
				if(featuresToInclude==null) {//if no filter available
					if (!customizedfeatures.contains(f.getName()))
							csv_notcustomizedFeatures=csv_notcustomizedFeatures.concat("\n"+f.getName()+",NOT_CUSTOMIZED,0.2");
				}
				else //if filter available
				if (!customizedfeatures.contains(f.getName()) && featuresToInclude.contains(f.getName())) {
					csv_notcustomizedFeatures=csv_notcustomizedFeatures.concat("\n"+f.getName()+",NOT_CUSTOMIZED,0.2");
				}
			}
			return csv_notcustomizedFeatures;
		}
		
		private String extractCSVForNotCustomizedProducts(ArrayList<String> customizedproductreleases) {
			String csv_notcustomizedprs="";
			Iterator<ProductRelease> it = prDao.findAll().iterator();
			ProductRelease pr;
			while(it.hasNext()) {
				pr=it.next();
				if(!customizedproductreleases.contains(pr.getName())) {
					csv_notcustomizedprs=csv_notcustomizedprs.concat("\n"+"NOT_CUSTOMIZED,"+pr.getName()+",0.2");
				}
			}
			return csv_notcustomizedprs;
		}
		  private void addFilteredFeatureToTheModel(Model model, ArrayList<String> featuresToInclude) {
				if (featuresToInclude==null) return;

				System.out.println("SEGUIDOOOO");
				String result="";
				Iterator<String> it = featuresToInclude.iterator();
				ArrayList<ParentFeature> features=new ArrayList<ParentFeature>();
				String f; ParentFeature fe;
				while(it.hasNext()) {
					f= it.next();
					fe=parentFeatureDao.getFeaturePArentByName(f);
					if (fe!=null) features.add(fe);
				}
				
				model.addAttribute("features", features);
				System.out.println(features.toString());
			}
		  
		  
}
