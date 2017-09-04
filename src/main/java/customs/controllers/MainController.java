package customs.controllers;

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

@RequestMapping("/index")
public String index2() {
    return "index";//index.html
  }

@RequestMapping("/settings")
public String settings() {
    return "settings";//index.html
  }
@RequestMapping("/customs")
public String customs() {
    return "customs";//index.html
  }
}
