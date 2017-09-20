package customs.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import customs.models.CustomizationsByVPandPR;
import customs.models.CustomizationsByVPandPRDao;
import customs.models.ProductAsset;
import customs.models.ProductAssetDao;
import customs.models.SPLdao;
import customs.models.VariationPoint;
import customs.models.VariationPointDao;
import customs.utils.Formatting;
import customs.utils.VPDiffUtils;

@Controller
public class ProductStructureController {

	 
	 @Autowired private CustomizationsByVPandPRDao prsAndVps;
	 @Autowired private VariationPointDao variationPointDao;
	 @Autowired private ProductAssetDao productAssetDao;
	 @Autowired private SPLdao SPLdao;
	 private String pathToResource = "./src/main/resources/static/";

	 
	  @RequestMapping("product_structure")//for all files of product_release
	   public String getProductReleaseStructureVPs(
	   				@RequestParam(value="pr", required=false) String idrelease,
	   				@RequestParam(value="file" , required = false) int  id_asset,
	   				Model model){
		   System.out.println("This IS idrelease: "+idrelease);
		   System.out.println("This IS filePath selected: "+id_asset);
		   
		   //compute CSV for VariationPoints
		   String csvContent = extractVPsCSVForProuductRelease(idrelease);
		  
		   customs.utils.FileUtils.writeToFile(pathToResource+"productstructure.csv",csvContent);//path and test
		   
		   computeDiffForSelectedReleaseFile(idrelease, id_asset, model);
		   
		   model.addAttribute("maintitle", "Which core-assets is product' "+ idrelease + "' customizing?");
			model.addAttribute("difftitle", "diff (Baseline-v1.0, " + idrelease+")");
		  return "productVariationPoints"; 
	 	}
	  
	  @RequestMapping("file_vp_structure")//just for one file
	   public String getFileStructureVPs(
	   				@RequestParam(value="pr", required=false) String idrelease,
	   				@RequestParam(value="file" , required = false) int  id_asset,
	   				@RequestParam(value="expression" , required = false) String  expression,
	   				Model model){
		  
		   System.out.println("This IS idrelease: "+idrelease);
		   System.out.println("This IS filePath selected: "+id_asset);
		   System.out.println("This IS expression selected: "+expression);
		   
		   //compute CSV for VariationPoints
		   ProductAsset pa = productAssetDao.getProductAssetByIdproductasset(id_asset);
		   String csvContent = extractVPsCSVForSingleProductAsset(idrelease,id_asset);
		  
		   customs.utils.FileUtils.writeToFile(pathToResource+"productstructure.csv",csvContent);//path and test
		  
		
		  if(expression==null) {
			  computeDiffForSelectedReleaseFile(idrelease, id_asset, model);
			  model.addAttribute("diffHeader", "diff (Baseline-v1.0.getAsset('"+pa.getName()+"') ,"+idrelease+".getAsset('"+pa.getName()+"') [VP.Expression.contains('Features.all()')]");
		  } 
		  else {
			  String diffvalue =addDiffViewForProductAssetId(idrelease, id_asset, expression);
			  model.addAttribute("diffvalue", diffvalue);
			  model.addAttribute("diffHeader", "diff (Baseline-v1.0.'"+pa.getName()+"' ,"+idrelease+".'"+pa.getName()+"' ["+expression+"]");
		  } 
	
		   model.addAttribute("maintitle", "How is product '"+ idrelease + "' customizing core-asset '"+pa.getName()+"'?");
		   return "productVariationPoints"; 
	 	}


	private String addDiffViewForProductAssetId(String idrelease, int id_asset, String expression) {
		//1: get the diff from the product-asset
		ProductAsset pa = productAssetDao.getProductAssetByIdproductasset(id_asset);
		String diffvalue =  customs.utils.Formatting.decodeFromBase64(pa.getRelative_diff());
		
		String enhancedDiff = VPDiffUtils.getEnhancedDiffWithVPs(pa, diffvalue, variationPointDao);
		String filteredDiff = VPDiffUtils.getFilteredDiffForVPExpression(enhancedDiff, expression);
		System.out.println("addDiffViewForProductAssetId");
		System.out.println(filteredDiff);
		
		return filteredDiff;
		
	}

	private String extractVPsCSVForSingleProductAsset(String idrelease, int id_asset) {
		   Iterable<CustomizationsByVPandPR> customsObj = prsAndVps.getCustomizationByInproduct(idrelease);
		   Iterator<CustomizationsByVPandPR> it = customsObj.iterator();
		   
		   
		   String csvContent="id,value,id_asset,p_release,expression"; //id,value,size
		   ArrayList<String> paths = new ArrayList<>();
		   CustomizationsByVPandPR custo;
		   VariationPoint vp;
		   String path;
		   String csvCustoms= "";
		   ArrayList<String> filePaths=new ArrayList<String>();
		   while (it.hasNext()) {
			  custo= it.next();
			  ProductAsset pa = productAssetDao.getProductAssetByIdproductasset(custo.getFilechanged());
			  System.out.println(pa.getIdProductasset());
			  if(pa.getIdProductasset()==id_asset) {
				  System.out.println("Product Asset Found");
			  
				  vp = variationPointDao.getVariationPointByIdvariationpoint(custo.getIdvariationpoint());
				  path = pa.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","");
				  
				  if(!filePaths.contains(pa.getPath())) {
					  filePaths.add(pa.getPath());
					  csvCustoms=  csvCustoms.concat("\n" +pa.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+",");
				  }
				  //clave
				  String exp= Formatting.decodeFromBase64(vp.getExpression());
				  String formated = exp.split("PV:IFCOND")[1];
				  csvCustoms = csvCustoms.concat("\n"+path+"/" + Formatting.decodeFromBase64(vp.getExpression()).
						  split("//")[1]+","+ custo.getChurn()+","
						  +pa.getIdProductasset() + ","+ idrelease
						 +","+formated//Formatting.decodeFromBase64(vp.getExpression())
						 );
				  paths.add(path);
			  } 
		   }//end while
		   
		   System.out.println("Paths");
		   ArrayList<String>  paths2 = customs.utils.Formatting.extractMiniPaths(paths);
		   System.out.println(paths);
		   System.out.println(paths2);
		   
		   for (int i=0; i< paths2.size();i++) 
			   if(!paths2.get(i).equals(""))
			     csvContent=csvContent.concat("\n"+paths2.get(i)+",");
		   
		   csvContent=csvContent.concat(csvCustoms);
		   System.out.println(paths2);
		return csvContent;
	}

	
	private void computeDiffForSelectedReleaseFile(String idrelease, int id, Model model) {
		
		ProductAsset pa = productAssetDao.getProductAssetByIdproductasset(id);
		 if (pa==null) return;
		 
		 String diffvalue =  customs.utils.Formatting.decodeFromBase64(pa.getRelative_diff());
		 //process here the content of the relative diff
		 System.out.println(diffvalue);
		 
		
		 String enhancedDiffValue="";
		 List<String> diffList = customs.utils.FileComparator.fileToLines(diffvalue);
		 Iterator<String> it = diffList.iterator();
		 String line;

		String expression="";
		Iterable<VariationPoint> vps = variationPointDao.getVariationPointsByIdproductasset(id);
		System.out.println(vps.toString());
		int line_number=-100;
		 while (it.hasNext()) {
			
			 line_number++;
			 line = it.next();
			 System.out.println(line.toString());
			 if(line.startsWith("@@")) {
				 System.out.println("split: "+line.split(" +")[2].toString());
				 line_number = Integer.parseInt (line.split(" +")[2].split(",")[0]);
				 System.out.println("The diff starts in: "+line_number);
				 enhancedDiffValue=enhancedDiffValue.concat(line+"\n");
			 }
			 else{
				 if(line_number<=0) {
					 enhancedDiffValue=enhancedDiffValue.concat(line+"\n");
				 }else {
					 if(line.startsWith("-") || line.startsWith("+")) {
						 enhancedDiffValue=enhancedDiffValue.concat(line+"\n");
					 }
					 else {//this line is a context line - we need to add the VP it belongs to.
							
						 expression =extractVPExpressionByLineNumber(line_number,vps);
						 
						 if(line.contains("PV:IFCOND")) 
								 enhancedDiffValue=enhancedDiffValue.concat(" "+Formatting.decodeFromBase64(expression)+"\n");// +"--> Autogenerated VP expression \n");
						 else  if((line.contains("PV:ENDCOND")))
							 	enhancedDiffValue=enhancedDiffValue.concat(line+"\n");
						 		else
						 			enhancedDiffValue=enhancedDiffValue.concat(line +" "+Formatting.decodeFromBase64(expression)+"\n");// +"--> Autogenerated VP expression \n");
					 }
				 }
				 
			 }
		 }
		 System.out.println(enhancedDiffValue);
		 
	
		String header= "Differences between product '"+idrelease+"' for core-asset '"+pa.getName()+"'";
		model.addAttribute("diffHeader", header);	 
	    model.addAttribute("diffvalue",enhancedDiffValue); 
	}

/*
	private int getHowManyChunks(String diffvalue) {
		String[] chunks = diffvalue.split("@@ -");
		System.out.println("getHowManyChunks \n"+chunks.toString());
		return chunks.length;
	}
	
	private String getChunkInitLineNumberInNewFile(int chunk_num, String diffvalue) {
		String[] chunks = diffvalue.split("@@ -");
		String chunk = chunks[chunk_num];
		String[] line_num = chunk.split(",");
		System.out.println(line_num[0]);
		return line_num[0];
	}*/


	private String extractVPExpressionByLineNumber(int line_number, Iterable<VariationPoint> vps) {
		VariationPoint vp=null;
		String expression= Formatting.encodeToBase64(" // a_mandatory_feature");
		Iterator<VariationPoint> it = vps.iterator();
		while (it.hasNext()) {
			vp=it.next();
			/*System.out.println(vp.getExpression());
			System.out.println("Init line:"+vp.getLine_init());
			System.out.println("End line:"+vp.getLine_end());
			System.out.println("line to search:"+line_number);
			System.out.println(vp.toString());*/
			if(vp.getLine_init()<= line_number && vp.getLine_end()>=line_number)//get this expression
				expression = vp.getExpression();//Note that the nested vps have already their parents expression concated.
		}
		System.out.println(expression);
		return expression;
	}


	private String extractVPsCSVForProuductRelease(String idrelease) {
		Iterable<CustomizationsByVPandPR> customsObj = prsAndVps.getCustomizationByInproduct(idrelease);
		   Iterator<CustomizationsByVPandPR> it = customsObj.iterator();
		   
		   String csvContent="id,value,id_asset,p_release"; //id,value,size
		   ArrayList<String> paths = new ArrayList<>();
		   CustomizationsByVPandPR custo;
		   VariationPoint vp;
		   String path;
		   String csvCustoms= "";
		   ArrayList<String> filePaths=new ArrayList<String>();
		   while (it.hasNext()) {
			  custo= it.next();
			  ProductAsset pa = productAssetDao.getProductAssetByIdproductasset(custo.getFilechanged());
			  vp = variationPointDao.getVariationPointByIdvariationpoint(custo.getIdvariationpoint());
			  path = pa.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/","");
			  
			  if(!filePaths.contains(pa.getPath())) {
				  filePaths.add(pa.getPath());
				  csvCustoms=  csvCustoms.concat("\n" +pa.getPath().replace(SPLdao.findAll().iterator().next().getIdSPL()+"/", "")+",");
			  }
			  //clave
			  csvCustoms = csvCustoms.concat("\n"+path+"/" + Formatting.decodeFromBase64(vp.getExpression()).
					  split("//")[1]+","+ custo.getChurn()+","
					  +pa.getIdProductasset() + ","+ idrelease);
			  paths.add(path);
			  
		   }
		   System.out.println("Paths");
		 
		   ArrayList<String>  paths2 = customs.utils.Formatting.extractMiniPaths(paths);
		   System.out.println(paths);
		   System.out.println(paths2);
		   
		   for (int i=0; i< paths2.size();i++) 
			   if(!paths2.get(i).equals(""))
			     csvContent=csvContent.concat("\n"+paths2.get(i)+",");
		   
		   csvContent=csvContent.concat(csvCustoms);
		   System.out.println(paths2);
		return csvContent;
	}
}
