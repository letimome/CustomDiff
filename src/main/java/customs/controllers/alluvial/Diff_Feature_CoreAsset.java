package customs.controllers.alluvial;

import java.util.ArrayList;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.ComponentPackage;
import customs.models.ComponentPackageDao;
import customs.models.CoreAsset;
import customs.models.CoreAssetDao;
import customs.models.CustomsByFeatureAndCoreAsset;
import customs.models.CustomsByFeatureAndCoreAssetDao;
import customs.models.Feature;
import customs.models.FeatureDao;
import customs.models.ParentFeature;
import customs.models.ParentFeatureDao;
import customs.models.ProductRelease;
import customs.models.ProductReleaseDao;
import customs.utils.Formatting;


@Controller
public class Diff_Feature_CoreAsset {
	  @Autowired private CustomsByFeatureAndCoreAssetDao customsToAssetsByproducts;
	  @Autowired private CoreAssetDao caDao;
	  @Autowired private ComponentPackageDao packDao;
	  @Autowired private ProductReleaseDao prDao;
	  @Autowired private FeatureDao fDao;
	  @Autowired private ParentFeatureDao parentFeatureDao;

	
	 @RequestMapping("diff_feature_core_asset")
	   public String getDiffCaPaCode(
  				 
  				@RequestParam(value="idfeature", required=false) String idfeature,
  				@RequestParam(value="pr", required=false) int idproductrelease, 
  				@RequestParam(value="idcoreasset", required=false) int idcoreasset,
  				@RequestParam(value="from", required=false) String from, 
  				Model model){
	  
	
	
		 ProductRelease pr = prDao.getProductReleaseByIdproductrelease(idproductrelease);
	   Feature feature = fDao.getFeatureByIdfeature(idfeature);
	   
	   addDiffViewForCoreAssetId(model,idcoreasset ,pr.getName(), idfeature);
	 
	   CoreAsset ca = caDao.getCoreAssetByIdcoreasset(idcoreasset);
	  /*
	   if(from.equals("fp"))
	     customs.utils.NavigationMapGenerator.generateNavigationMapForCodeDiffFP(idfeature,ca.getName(),"hasFeature ("+idfeature+")",pr.getName());
	   if(from.equals("f"))
		   customs.utils.NavigationMapGenerator.generateNavigationMapForCodeDiffF(idfeature,ca.getName(),"hasFeature ("+idfeature+")",pr.getName());
	   if(from.equals("p"))
		   customs.utils.NavigationMapGenerator.generateNavigationMapForCodeDiffP(idfeature,ca.getName(),"hasFeature ("+idfeature+")",pr.getName());
	   */
	   Feature f = fDao.getFeatureByIdfeature(idfeature);
	   ParentFeature parent = parentFeatureDao.getParentFeatureByIdparentfeature(f.getIdparent());
	   ComponentPackage component = packDao.getComponentPackageByIdpackage(ca.getIdpackage()); 
	   if(from.equals("capp"))
		     customs.utils.NavigationMapGenerator.generateNavigationMapForFeatureSideLevel4(idfeature, ca.getName(), idfeature, component.getName(), parent.getName(), "files", ca.getName(), pr.getName());
	   
	   model.addAttribute("maintitle", "How is '"+pr.getName()+"' modifying '"+ca.getName()+"' for feature '"+idfeature+"' ?");
	   model.addAttribute("pr",pr.getName());
	   model.addAttribute("idproductrelease",idproductrelease);
	   model.addAttribute("idparentfeature",feature.getIdparent());
	   model.addAttribute("idcoreasset",idcoreasset);
	   model.addAttribute("idpackage",ca.getIdpackage());
	   
	   model.addAttribute("from",from);
	   model.addAttribute("idfeature",idfeature);
	   model.addAttribute("difftitle", "diff(Feature: '" +idfeature+"', "+pr.getName()+")");
	   
	   return "alluvials/diff_ca_pa_filter";
	 }

	 
	 //diff_features_core_assets
	 @RequestMapping("diff_features_core_asset")
	 public String getDiffParentFeatureCoreAsset(
				 
				@RequestParam(value="idparentfeature", required=false) int idparentfeature,
				@RequestParam(value="idproductrelease", required=false) int idproductrelease, 
				@RequestParam(value="idcoreasset", required=false) int idcoreasset,
				@RequestParam(value="from", required=false) String from, 
				Model model){
	  
		ProductRelease pr = prDao.getProductReleaseByIdproductrelease(idproductrelease);
		ParentFeature parentFeature = parentFeatureDao.getParentFeatureByIdparentfeature(idparentfeature);
	    
		addDiffViewForCoreAssetIdAndParentFeature(model,idcoreasset ,pr, parentFeature);
	 
	   CoreAsset ca = caDao.getCoreAssetByIdcoreasset(idcoreasset);
	  /*
	   if(from.equals("fp"))
	     customs.utils.NavigationMapGenerator.generateNavigationMapForCodeDiffFP(idfeature,ca.getName(),"hasFeature ("+idfeature+")",pr.getName());
	   if(from.equals("f"))
		   customs.utils.NavigationMapGenerator.generateNavigationMapForCodeDiffF(idfeature,ca.getName(),"hasFeature ("+idfeature+")",pr.getName());
	   if(from.equals("p"))
		   customs.utils.NavigationMapGenerator.generateNavigationMapForCodeDiffP(idfeature,ca.getName(),"hasFeature ("+idfeature+")",pr.getName());
	   */
	   model.addAttribute("maintitle", "How is '"+pr.getName()+"' modifying '"
			   	+ca.getName()+"' for parent-feature '"+parentFeature.getName()+"' ?");
	   
	   model.addAttribute("pr",pr.getName());
	   model.addAttribute("idproductrelease",pr.getId_productrelease());
	   model.addAttribute("idpackage",ca.getIdpackage());
	   
	//   System.out.println("FRom"+from);
	 //  model.addAttribute("from",from);
	   model.addAttribute("fname",parentFeature.getName());
	   model.addAttribute("difftitle", "diff(Parent Feature: '" +parentFeature.getName()+"', "+pr.getName()+")");
	   
	   return "alluvials/diff_features_core_asset";
	 
	 }
	 
	 
	 
	 private void addDiffViewForCoreAssetIdAndParentFeature(Model model, int idcoreasset, ProductRelease pr, ParentFeature parentFeature) {
		 CoreAsset ca = caDao.getCoreAssetByIdcoreasset(idcoreasset);
		 System.out.println("CA:"+ca);
		 System.out.print("\ndiff for idcoreasset: "+idcoreasset+" and ca name:"+ca.getName()); 
		 System.out.println(" for pr: "+pr.getName());
			
		Iterable<CustomsByFeatureAndCoreAsset>  customs= customsToAssetsByproducts.findAll(); 
		CustomsByFeatureAndCoreAsset custom=null;
		Iterator<CustomsByFeatureAndCoreAsset> it = customs.iterator();
		ArrayList<Integer> listofcutomsAdded = new ArrayList<Integer>();
		
		String diffvalue ="";
		while(it.hasNext()) {
			custom= it.next();
			
			if(custom.getIdcoreasset()==idcoreasset && custom.getIdparentfeature()==parentFeature.getIdparentFeature()
					&&(!listofcutomsAdded.contains(custom.getIdcustomization()))
						&&(custom.getIdproductrelease() == pr.getId_productrelease())) {
				listofcutomsAdded.add(custom.getIdcustomization());
				diffvalue = diffvalue.concat(Formatting.decodeFromBase64( custom.getCustom_diff())).concat("\n");	
			}
		}
		
		model.addAttribute("diffvalue", diffvalue); 
		model.addAttribute("pr",pr);
		model.addAttribute("fname", parentFeature.getName());
		model.addAttribute("cavalue",ca.getIdcoreasset());			 // model.addAttribute("diffHeader", "diff (core-asset:'"+pa.getName()+"', product-asset:'"+pa.getName()+"' [file.getVPExpression('"+expression+")]");
		String header= "diff( Baseline-v1.0."+ca.getName()+",  "+pr+"."+ca.getName()+" [VP.contains(hasFeature('"+parentFeature.getName()+"'))]";
		model.addAttribute("diffHeader", header);
		model.addAttribute("maintitle", "How is parent feature '"+parentFeature.getName()+"' being customized in ?");			
		model.addAttribute("difftitle", "diff(Feature: '" +parentFeature.getName()+"', Product-Portfolio)");
	}


	private void addDiffViewForCoreAssetId(Model model, int idcoreasset, String pr, String featureid) {
			
		if(pr==null) return;

		 CoreAsset ca = caDao.getCoreAssetByIdcoreasset(idcoreasset);
		 System.out.println("CA:"+ca);
		 System.out.print("\ndiff for idcoreasset: "+idcoreasset+" and ca name:"+ca.getName()); 
		 System.out.println(" for pr: "+pr);
			
		Iterable<CustomsByFeatureAndCoreAsset>  customs= customsToAssetsByproducts.findAll();
		CustomsByFeatureAndCoreAsset custom=null;
		Iterator<CustomsByFeatureAndCoreAsset> it = customs.iterator();
			
		String diffvalue ="";
		while(it.hasNext()) {
			custom= it.next();
			if(custom.getIdcoreasset()==idcoreasset && (custom.getIdfeature().equals(featureid))
						&&(custom.getPrname().equals(pr))) {
				diffvalue = diffvalue.concat(Formatting.decodeFromBase64( custom.getCustom_diff())).concat("\n");	
			}
		}
		 
		model.addAttribute("diffvalue", diffvalue); 
		model.addAttribute("pr",pr);
		model.addAttribute("fname",featureid);
		model.addAttribute("cavalue",ca.getIdcoreasset());			 // model.addAttribute("diffHeader", "diff (core-asset:'"+pa.getName()+"', product-asset:'"+pa.getName()+"' [file.getVPExpression('"+expression+")]");
		String header= "diff( Baseline-v1.0."+ca.getName()+",  "+pr+"."+ca.getName()+" [VP.contains(hasFeature('"+featureid+"'))]";
		model.addAttribute("diffHeader", header);
		model.addAttribute("maintitle", "How is feature '"+featureid+"' being customized in products?");			
		model.addAttribute("difftitle", "diff(Feature: '" +featureid+"', Product-Portfolio)");
	 }

}
