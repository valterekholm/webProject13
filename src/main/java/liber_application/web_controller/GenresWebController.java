package liber_application.web_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import liber_application.data.GenreRepository;

@Controller
@RequestMapping("genres")
public class GenresWebController {
	
	@Autowired
	GenreRepository genresRepo;
	
	@GetMapping("/all")
	public String getAllGenres(Model m) {
		m.addAttribute("genres", genresRepo.findAll());
		return "listgenres";
	}

}
