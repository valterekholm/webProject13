package liber_application.web_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import liber_application.data.BookLoanRepository;

@Controller
@RequestMapping("loans")
public class LoansWebController {

	@Autowired
	BookLoanRepository loansRepo;
	
	@GetMapping("/all")
	public String getAllBooks(Model m) {
		m.addAttribute("loans", loansRepo.findAll());
		return "allloans";
	}
}
