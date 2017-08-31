package customs.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import customs.models.BaselineDao;
import customs.models.Coreassetbaseline;

@Controller
public class BaselineController {
	
	  
	  @RequestMapping("/baselines")
		public  String getAllCoreAssetBselines( Model model) {
			// This returns a JSON or XML with the users
		  Iterable<Coreassetbaseline> baselines =  baselineDao.findAll();
			model.addAttribute("baselines", baselines);
			return "index"; //baselines html
		}
	  
	  
	  @RequestMapping("baseline")
	  @ResponseBody
	  public String create(String idBaseline, Date releaseDate, String SPL_idSPL) {
		  Coreassetbaseline baseline = null;
	    try {
	    	baseline = new Coreassetbaseline(idBaseline, releaseDate, SPL_idSPL);
	      baselineDao.save(baseline);
	    }
	    catch (Exception ex) {
	      return "Error creating the user: " + ex.toString();
	    }
	    return "User succesfully created! (id = " + baseline.getIdBaseline() + ")";
	  }
	  // ------------------------
	  // PRIVATE FIELDS
	  // ------------------------

	  @Autowired
	  private BaselineDao baselineDao;
	  
	} // class UserController
