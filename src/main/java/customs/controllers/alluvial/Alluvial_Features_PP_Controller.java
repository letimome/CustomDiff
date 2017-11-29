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
    @Autowired private Churn_PoductPortfolioAndFeaturesDao churnCustoms;
	
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
		  if (pf == null)
		  addFilteredFeatureToTheModel(model,featuresToInclude);
		  model.addAttribute("maintitle","How are "+pf.getName()+"'s features being customized by the products?");
		  model.addAttribute("from",from);
		  model.addAttribute("idparentfeature",idparentfeature);
		  String filterJson = customs.utils.FeaturesToJason.getJsonForFeatures(fDao.getFeaturesByIdparent(idparentfeature));
		  
		  model.addAttribute("filterJson",filterJson);
		  
		  customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureSide(pf.getName(), "core-asset", "Filter", "Variant","Component");
		  
		  System.out.println(csvCustoms);
		  return "alluvials/diff_features_pp";
		  
	 	}
	
	
	
	
	@RequestMapping("diff_all_features_pp")
	public String get_diff_all_features_pp ( 	   		
			   @RequestParam(value="filter", required=false)  String filter, Model model){
		   
		   ArrayList<String> featuresToInclude ;
		   if (filter==null)   featuresToInclude=null;
		   else {
			   featuresToInclude = customs.utils.Formatting.stringToArrayList(filter, ",");
			   System.out.println(featuresToInclude.toString());
		   }
		   
		   Iterable<Churn_PoductPortfolioAndFeatures> customsObj = churnCustoms.findAll();
		   Iterator<Churn_PoductPortfolioAndFeatures> it = customsObj.iterator();
		   String csvCustoms= "source,target,value,idparentfeature,idproductrelease";
		   Churn_PoductPortfolioAndFeatures custo;
		   
		   ArrayList<String> customizedfeatures=new ArrayList<>() ;
		   ArrayList<String> customizedproductreleases = new ArrayList<>();
		   boolean include=false;
		   
		   
		   while (it.hasNext()) {
			    custo=it.next();
			 
			    include=false;
			   if( featuresToInclude==null)  include=true;
			   else if (featuresToInclude.contains(custo.getId_feature())) include=true;
			    	 
			  if(custo.getId_feature()!=null && !custo.getId_feature().equals("null")
					     && !custo.getId_feature().equals("undefined") && include) {
				    	   csvCustoms = csvCustoms.concat ("\n" +custo.getId_feature()+","+custo.getPr_name() 
				    	   +","+custo.getChurn()+","
				    			   +fDao.getFeatureByIdfeature(custo.getId_feature()).getIdparent()+","+custo.getId_pr() );
				    	   
				    	   customizedproductreleases.add(custo.getPr_name());
				    	   customizedfeatures.add(custo.getId_feature());
			   }
		   }
		  //if there is a filter, then do not show products which has not been customized.
		   if(filter==null) csvCustoms = csvCustoms.concat(extractCSVForNotCustomizedProducts(customizedproductreleases));
		  
		   csvCustoms = csvCustoms.concat(extractCSVForNotCustomizedFeatures(customizedfeatures, featuresToInclude));
		 
		  customs.utils.FileUtils.writeToFile(pathToResource+"alluvial.csv",csvCustoms);//path and test
		  String filterJson = customs.utils.FeaturesToJason.getJsonForFeatures(fDao.findAll());
		 System.out.println("filterJson");
		 System.out.println(filterJson);
		  model.addAttribute("filterJson",filterJson);
		  model.addAttribute("maintitle","How are all 'child features' being customized by the products?");
		 
		  addFilteredFeatureToTheModel(model,featuresToInclude);
		  customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureSide("All features", "PL asset", "Filter", "Variant", "Component");
		  
		  System.out.println(csvCustoms);
		  return "alluvials/diff_features_pp";
		  
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
	
	 
	 private String extractCSVForNotCustomizedFeatures( ArrayList<String> customizedfeatures, ArrayList<String> featuresToInclude) {
			String csv_notcustomizedFeatures="";
			
			Iterator<Feature> it = fDao.findAll().iterator();
			Feature f;
			
			while (it.hasNext()) {
				f = it.next();
				if(featuresToInclude==null) {//if no filter available
					if (!customizedfeatures.contains(f.getName()))
						csv_notcustomizedFeatures=csv_notcustomizedFeatures.concat("\n"+f.getName()+",NONE,0.2");
				}
				else //if filter available
				if (!customizedfeatures.contains(f.getName()) && featuresToInclude.contains(f.getName())) {
					csv_notcustomizedFeatures=csv_notcustomizedFeatures.concat("\n"+f.getName()+",NONE,0.2");
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
					csv_notcustomizedprs=csv_notcustomizedprs.concat("\n"+"NONE,"+pr.getName()+",0.2");
				}
			}
			return csv_notcustomizedprs;
		}
		  private void addFilteredFeatureToTheModel(Model model, ArrayList<String> featuresToInclude) {
				if (featuresToInclude==null) return;

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
