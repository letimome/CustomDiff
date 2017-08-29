package netgloo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

//@RequestMapping(value="html", method = RequestMethod.GET )
@RequestMapping("/")
public String index() {
    return "index";//index.html
  }
}
