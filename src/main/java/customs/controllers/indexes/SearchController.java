package customs.controllers.indexes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {
	
	 @RequestMapping("search")
	   public String getSearch() {
		 return "extra/search";
	 }

}
