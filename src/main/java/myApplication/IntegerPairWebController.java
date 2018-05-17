package myApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IntegerPairWebController {
	
	
	@Autowired
	IntegerAdder adder;
	
     @GetMapping("/adder")
     public String getForm(Model m) {
    	 	m.addAttribute("pair", new IntegerPair());
    	 	return "addform";
     }
     
     @PostMapping("/adder")
     public String fillForm(IntegerPair ip, Model m) {
    	 		m.addAttribute("pair", new IntegerPair());
    	 		m.addAttribute("sum", adder.add(ip));
    	 		return "addform";
     }
     
     @GetMapping("/pairlist")
     public String getAll(Model m) {
    	        m.addAttribute("list", adder.getList());
    	        return "pairlist";
     }

}
