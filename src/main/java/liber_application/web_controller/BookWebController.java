package liber_application.web_controller;

import java.util.Objects;
import java.util.Optional;

import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import liber_application.data.BookRepository;
import liber_application.data.GenreRepository;
import liber_application.data.LocationRepository;
import liber_application.model.Book;
import liber_application.object_representation.BookRepresentation;

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
	
	@Autowired
	GenreRepository genresRepo;
	
	@Autowired
	LocationRepository locationsRepo;
	
	@GetMapping("/all")
	public String getAllBooks(Model m) {
		m.addAttribute("books", bookRepo.findAll());
		return "listbooks";
	}
	
	@GetMapping("/addBook")
	public String addBook(Model m) {
		m.addAttribute("book", new Book());
		m.addAttribute("genres", genresRepo.findAll());
		m.addAttribute("locations", locationsRepo.findAll());
		return "addbook";
	}
	
	@PostMapping("/addBook")
	public String saveBook(Book book, Model m) {
		System.out.println("saveBook with " + book);
		
		m.addAttribute("book", new Book());
		m.addAttribute("genres", genresRepo.findAll());
		m.addAttribute("locations", locationsRepo.findAll());
		
		Book savedB = bookRepo.save(book);
		m.addAttribute("message", "Saved book: " + savedB.getTitle());
		return "addbook";
	}
	
	@GetMapping("/editBook/{id}")
	public String editBook(@PathVariable Integer id, Model m) {
		System.out.println("editBook with id " + id);
		Optional<Book> bookToEdit = bookRepo.findById(id);
		
		if(bookToEdit.isPresent()) {
			System.out.println("isPresent");
			//go to edit form
			m.addAttribute("book", bookToEdit.get());
			m.addAttribute("genres", genresRepo.findAll());
			m.addAttribute("locations", locationsRepo.findAll());
			m.addAttribute("message", "Could find book");
			return "editbook";
		}
		
		else {
			System.out.println("not found");
			m.addAttribute("message", "Could not find book by id: " + id);
			return "listbooks";
		}
		
	}
	
	@PostMapping("/editBook")
	public String updateBook(Book book, Model m) {
		
		Book updatedBook = bookRepo.save(book);
		m.addAttribute("message", "Updated book: " + updatedBook.getTitle());
		
		m.addAttribute("books", bookRepo.findAll());
		
		return "listbooks";
	}
	
	
	@PostMapping("/deleteBook")
	public String deleteBook(Integer id, Model m) {
		Optional<Book> awayBook = bookRepo.findById(id);
		
		if(awayBook.isPresent()) {
			bookRepo.delete(awayBook.get());
			m.addAttribute("books", bookRepo.findAll());
			m.addAttribute("message", "Book deleted: " + awayBook.get().getTitle());
		}
		
		else {
			m.addAttribute("message", "Could not find book by id: " + id);
		}
		return "listbooks";
	}

}
