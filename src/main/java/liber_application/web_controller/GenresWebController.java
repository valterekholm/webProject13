package liber_application.web_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import liber_application.data.GenreRepository;
import liber_application.model.Book;
import liber_application.model.Genre;

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
	
	@GetMapping("/addGenre")
	public String addGenre(Model m) {
		m.addAttribute("genre", new Genre());
		return "addGenre";
	}
	
	@PostMapping("/addGenre")
	public String saveGenre(Genre genre, Model m) {
		m.addAttribute("genre", new Genre());
		Genre savedG = genresRepo.save(genre);
		m.addAttribute("message", "Saved genre: " + savedG.getName());
		return "addgenre";
	}

}
