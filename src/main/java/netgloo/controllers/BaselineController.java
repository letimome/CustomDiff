package netgloo.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import netgloo.models.BaselineDao;
import netgloo.models.Coreassetbaseline;
import netgloo.models.User;

@Controller
public class BaselineController {
	
	

	  
	  /**
	   * /update  --> Update the email and the name for the user in the database 
	   * having the passed id.
	   * 
	   * @param id The id for the user to update.
	   * @param email The new email.
	   * @param name The new name.
	   * @return A string describing if the user is succesfully updated or not.
	   */
	  
	  @RequestMapping("baselines/get-all")
		public @ResponseBody Iterable<Coreassetbaseline> getAllCoreAssetBselines() {
			// This returns a JSON or XML with the users
			return baselineDao.findAll();
		}
	  
	  
	  @RequestMapping("baselines/create")
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
