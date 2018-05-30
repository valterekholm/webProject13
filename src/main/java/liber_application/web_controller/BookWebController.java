package liber_application.web_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import liber_application.data.BookRepository;
import liber_application.model.Book;

/**
 * Web-controller for books
 * @author Valter Ekholm
 *
 */
@Controller
@RequestMapping("books")
public class BookWebController {
	
	@Autowired
	BookRepository bookRepo;
	
	@GetMapping("/all")
	public String getAllBooks(Model m) {
		m.addAttribute("books", bookRepo.findAll());
		return "listbooks";
	}
	
	@GetMapping("/addBook")
	public String addBook(Model m) {
		m.addAttribute("book", new Book());
		return "addbook";
	}
	
	@PostMapping("/addBook")
	public String saveBook(Book book, Model m) {
		m.addAttribute("book", new Book());
		Book savedB = bookRepo.save(book);
		m.addAttribute("message", "Saved book: " + savedB.getTitle());
		return "addbook";
	}

}
