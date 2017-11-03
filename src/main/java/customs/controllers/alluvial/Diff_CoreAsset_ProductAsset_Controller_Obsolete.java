package customs.controllers.alluvial;

import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import customs.models.CoreAsset;
import customs.models.CoreAssetDao;
import customs.models.CustomsByFeatureAndCoreAsset;
import customs.models.CustomsByFeatureAndCoreAssetDao;
import customs.utils.Formatting;


@Controller
public class Diff_CoreAsset_ProductAsset_Controller_Obsolete {
	  @Autowired private CustomsByFeatureAndCoreAssetDao customsToAssetsByproducts;
	  @Autowired private CoreAssetDao caDao;
	  

	/*
	 @RequestMapping("diff_ca_pa_code")
	   public String getDiffCaPaCode(
  				@RequestParam(value="base", required=false) String idbaseline, 
  				@RequestParam(value="fname", required=false) String featurenamemodified,
  				@RequestParam(value="pr", required=false) String pr, 
  				@RequestParam(value="idfile", required=false) int idfile,
  				@RequestParam(value="from", required=false) String from, 
  				Model model){
	  
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
*/
	 
	 private void addDiffViewForCoreAssetId(Model model, int idcoreasset, String pr, String featureid) {
			
		if(pr==null) return;
		//I need to get the absolute diff  that modifies the idcoreasset in release pr
		
			
		 CoreAsset ca = caDao.getCoreAssetByIdcoreasset(idcoreasset);
		 System.out.println("CA:"+ca);
		 System.out.print("\ndiff for idcoreasset: "+idcoreasset+" and ca name:"+ca.getName()); 
		 System.out.println(" for pr: "+pr);
			
		Iterable<CustomsByFeatureAndCoreAsset>  customs= customsToAssetsByproducts.findAll(); //
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
		System.out.println("\n--------diffvalue:"+ diffvalue);	 
		model.addAttribute("diffvalue", diffvalue); 
		model.addAttribute("pr",pr);
		model.addAttribute("fname",featureid);
		model.addAttribute("cavalue",ca.getIdcoreasset());			 // model.addAttribute("diffHeader", "diff (core-asset:'"+pa.getName()+"', product-asset:'"+pa.getName()+"' [file.getVPExpression('"+expression+")]");
		String header= "diff( Baseline-v1.0."+ca.getName()+",  "+pr+"."+ca.getName()+" [VP.contains(hasFeature('"+featureid+"'))]";
		model.addAttribute("diffHeader", header);
		model.addAttribute("maintitle", "How is feature '"+featureid+"' being customized in products?");			 model.addAttribute("difftitle", "diff(Feature: '" +featureid+"', Product-Portfolio)");
	 }

}
