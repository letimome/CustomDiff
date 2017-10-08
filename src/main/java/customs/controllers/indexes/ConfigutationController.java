package customs.controllers.indexes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class ConfigutationController {

	
	  @RequestMapping("/config")
			public  String getConfigurationPage( Model model) {
				return "extra/configuration"; //baselines html
			}
			

}
