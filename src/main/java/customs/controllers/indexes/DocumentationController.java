package customs.controllers.indexes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DocumentationController {
	
	@RequestMapping("documentation")
	   public String getDocumentationView() {
		return "extra/documentation";
	 }

}
