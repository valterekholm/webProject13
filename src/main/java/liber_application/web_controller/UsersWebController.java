package liber_application.web_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import liber_application.data.UserRepository;

@Controller
@RequestMapping("users")
public class UsersWebController {

	@Autowired
	UserRepository usersRepo;
	
	@GetMapping("/all")
	public String getAllUsers(Model m) {
		m.addAttribute("users", usersRepo.findAll());
		return "listusers";
	}
}
