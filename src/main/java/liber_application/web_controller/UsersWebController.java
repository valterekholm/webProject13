package liber_application.web_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import liber_application.data.UserRepository;
import liber_application.model.Book;
import liber_application.model.Location;
import liber_application.model.User;

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
	
	@GetMapping("/addUser")
	public String addUser(Model m) {
		m.addAttribute("user", new User());
		return "adduser";
	}
	
	@PostMapping("/addUser")
	public String saveUser(User user, Model m) {
		m.addAttribute("user", new User());

		User savedU = usersRepo.save(user);
		m.addAttribute("message", "Saved user: " + savedU.getName());
		return "adduser";
	}
}
