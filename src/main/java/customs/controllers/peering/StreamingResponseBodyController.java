package customs.controllers.peering;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import customs.models.CoreAssetDao;
import customs.models.CoreassetsAndFeaturesDao;
import customs.models.CustomsByFeatureAndCoreAssetDao;
import customs.models.Feature;
import customs.models.FeatureDao;
import customs.models.ProductRelease;
import customs.models.ProductReleaseDao;
import customs.peering.FeaturePatcher;
import customs.peering.ThreeWayDiffWorkspace;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
/**
 * Created by Its Spring boot download file using StreamingResponseBody it same like spring mvc download file  on 19-07-2017.
 */



@RestController  
public class StreamingResponseBodyController {
	@Autowired private ProductReleaseDao prDao;
	@Autowired private FeatureDao fDao;
	@Autowired private CustomsByFeatureAndCoreAssetDao customsDao;
	@Autowired private CoreassetsAndFeaturesDao coreAssetsAndFeatures; 
	@Autowired private CoreAssetDao caDao; 
   // @RequestMapping(value = "download3waydiff", method = RequestMethod.GET)
	 // public StreamingResponseBody getSteamingFile(HttpServletResponse response) throws IOException {
	
	
	 @RequestMapping(value="/downlod3waydiff/{baseline}/{yours}/{mine}/{featurename}", method = RequestMethod.GET)
	 public StreamingResponseBody generate3wayDIFF(HttpServletResponse response,
			 @PathVariable String baseline, @PathVariable String yours,@PathVariable String mine, 
			 @PathVariable String featurename) throws IOException {
		 
		 ProductRelease p = findProductByname(mine,"-");
			if (p ==null) return null;
			
		System.out.println("The peering product is: "+p.getName());
			
		//call to the patcher function!! /**** NEW ****/
		if( (!featurename.equals("none")) && (!yours.equals("none"))) {
			System.out.println("INSIDE IF!");
			FeaturePatcher fp = new FeaturePatcher();
			ProductRelease yourp = findProductByname(yours,"-");  
			Feature featurepatch = fDao.getFeatureByIdfeature(featurename);
			ThreeWayDiffWorkspace files = fp.patchFilesForFeatureAndProduct(yourp, p, featurepatch, customsDao, coreAssetsAndFeatures, caDao);
		}
		 
		/**download to client**/
        return download(response, featurename);
    }

	 
	private StreamingResponseBody download(HttpServletResponse response, String featurename) throws FileNotFoundException {
		response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"kdiff"+featurename+".zip\"");
        InputStream inputStream = new FileInputStream(new File("./src/main/resources/kdiff-workspace/kdiff.zip"));//
        return outputStream -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                System.out.println("Writing  bytes..");
                outputStream.write(data, 0, nRead);
            }
        };
	}
	
	 private ProductRelease findProductByname(String observed, String spliter) {
		  
		  Iterator<ProductRelease> ite = prDao.findAll().iterator();
		  ProductRelease  p = null;
		  while (ite.hasNext()) {
				p = ite.next();
				if ((p.getName().split(spliter)[0]).equals(observed))
					return p;
			}
		  return null;
	}
	
}