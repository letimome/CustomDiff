package customs.controllers.alluvial;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import customs.models.AddedCustomsByProductsToFeaturesDao;
import customs.models.CoreAsset;
import customs.models.CoreAssetDao;
import customs.models.CoreassetsAndFeaturesDao;
import customs.models.CustomizationsByFeature;
import customs.models.CustomizationsByFeatureDao;
import customs.models.CustomizationsGByOperationDao;
import customs.models.DeletedCustomsByProductsToFeaturesDao;
import customs.models.ProductAsset;
import customs.models.ProductAssetDao;
import customs.models.SPLdao;
import customs.models.VariationPointDao;
import customs.utils.VPDiffUtils;

@Controller
public class Diff_CoreAsset_ProductAsset_Controller {
	  @Autowired private CustomizationsByFeatureDao featureCustomsDao;
	  @Autowired private VariationPointDao variationPointDao;
	  @Autowired private ProductAssetDao paDao;
	  @Autowired private CoreAssetDao caDao;
	  

	
	 @RequestMapping("diff_ca_pa_code")
	   public String getDiffCaPaCode(
  				@RequestParam(value="base", required=false) String idbaseline, @RequestParam(value="fname", required=false) String featurenamemodified,
  				@RequestParam(value="pr", required=false) String pr, 
  				@RequestParam(value="idfile", required=false) int idfile,
  				@RequestParam(value="from", required=false) String from, 
  				Model model){
	  
		 //tby == triggered by. Values:
		 //f, fp, pro
		 System.out.println("THIS IS base: "+idbaseline);
		 System.out.println("THIS IS fname: "+featurenamemodified);
		 System.out.println("THIS IS pr: "+pr);
		 System.out.println("THIS IS idfile: "+idfile);
	   
	   
	   addDiffViewForCoreAssetId(model,idfile,pr, featurenamemodified);
	 
	   CoreAsset ca = caDao.getCoreAssetByIdcoreasset(idfile);
	   if(from.equals("fp"))
	     customs.utils.NavigationMapGenerator.generateNavigationMapForCodeDiffFP(featurenamemodified,ca.getName(),"hasFeature ("+featurenamemodified+")",pr);
	   if(from.equals("f"))
		   customs.utils.NavigationMapGenerator.generateNavigationMapForCodeDiffF(featurenamemodified,ca.getName(),"hasFeature ("+featurenamemodified+")",pr);
	   if(from.equals("p"))
		   customs.utils.NavigationMapGenerator.generateNavigationMapForCodeDiffP(featurenamemodified,ca.getName(),"hasFeature ("+featurenamemodified+")",pr);
	   model.addAttribute("maintitle", "How is "+pr+" modifying the code in file '"+ca.getName()+"' for feature '"+featurenamemodified+"' ?");
	   
	   model.addAttribute("pr",pr);
	   System.out.println("FRom"+from);
	   model.addAttribute("from",from);
	   model.addAttribute("fname",featurenamemodified);
	   model.addAttribute("difftitle", "diff(Feature: '" +featurenamemodified+"', "+pr+")");
	   
	   return "alluvials/diff_ca_pa_filter";
	 }

	 
	 private void addDiffViewForCoreAssetId(Model model, int idcoreasset,String pr, String featureid) {
			
			if(pr==null) return;
			//I need to get the absolute diff  that modifies the idcoreasset in release pr
			 System.out.println("diff for idcoreasset: "+idcoreasset); System.out.println("diff for pr: "+pr);
			 ProductAsset pa=null;
			 CoreAsset ca=null;
			 
			 if(idcoreasset==0) {//then select the first one
				 Iterator<CustomizationsByFeature>  it= featureCustomsDao.getCustomsByFeatureid(featureid).iterator();
				 CustomizationsByFeature aux;
				 while(it.hasNext()) {
					 aux= it.next();
					 if(aux.getPr().equals(pr)) {
						 ca =  caDao.getCoreAssetByIdcoreasset(aux.getCoreassetid());
						 break;
					 }
				 }
			 }
			 else ca = caDao.getCoreAssetByIdcoreasset(idcoreasset);
			 
			 Iterator <ProductAsset> ite = paDao.findAll().iterator();
			 while(ite.hasNext()) {
				 pa=ite.next();
				 if(pa.getProductrelease_idrelease().equals(pr) && ca.getPath().equals(pa.getPath()))
					 break;
			 }
			 
			 System.out.println("pa: "+pa);
			 System.out.println("pa: "+pa.getPath());
			 String diffvalue =  customs.utils.Formatting.decodeFromBase64(pa.getRelative_diff());
			 //process here the content of the relative diff
			 System.out.println(diffvalue);
			 
			
			 String enhancedDiffValue = VPDiffUtils.getEnhancedDiffWithVPs(pa, diffvalue, variationPointDao);
			/*/ ArrayList<String> featureList= new ArrayList<>();
			 featureList.add(featureid);*/
			 String filteredDiff= VPDiffUtils.getFilteredDiffForFeature(enhancedDiffValue,featureid);
			
			 model.addAttribute("diffvalue",filteredDiff); 
			 model.addAttribute("pr",pr);
			 model.addAttribute("fname",featureid);
			 model.addAttribute("cavalue",ca.getIdcoreasset());
			 // model.addAttribute("diffHeader", "diff (core-asset:'"+pa.getName()+"', product-asset:'"+pa.getName()+"' [file.getVPExpression('"+expression+")]");
			 String header= "diff( Baseline-v1.0."+ca.getName()+",  "+pr+"."+ca.getName()+" [VP.contains(hasFeature('"+featureid+"'))]";
			 		
			 model.addAttribute("diffHeader", header);
			 model.addAttribute("maintitle", "How is feature '"+featureid+"' being customized in products?");
			 model.addAttribute("difftitle", "diff(Feature: '" +featureid+"', Product-Portfolio)");
		}

}
