package customs.controllers.indexes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller



public class CustomizationAnalysisController {

	@RequestMapping("analysis")
	   public String getDocumentationView() {
		return "extra/customizationanalysis";
		 
	 }

}