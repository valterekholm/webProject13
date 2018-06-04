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
	
	//This was an attempt to make the genre-field optional
//	@GetMapping("/addBook")
//	public String addBook2(Model m) {
//		m.addAttribute("book", new BookRepresentation());
//		m.addAttribute("genres", genresRepo.findAll());
//		m.addAttribute("locations", locationsRepo.findAll());
//		return "addbook2";
//	}
//	
//	//testing BookRepresentation for enable null genre
//	@PostMapping("/addBook")
//	public String saveBook(BookRepresentation bookRepresentation, Model m) {
//		System.out.println("saveBook with " + bookRepresentation);
//		
//		Book b = new Book();
//		
//		m.addAttribute("book", new BookRepresentation());
//		
//		//null fields...
//		
//		boolean accepted = true;
//		
//		if(bookRepresentation.getGenre()!=null) { //so genre can be null
//			String genre = bookRepresentation.getGenre();
//			//book.setGenre(new Genre(bookRepresentation.getGenre()));
//			//load from database
//			if(genresRepo.getByName(genre)!=null) {
//				//Genre Exists
//				b.setGenre(genresRepo.getByName(genre));
//			}
//			else {
//				System.out.println("Genre not found, " + genre);
//				accepted = false;
//				//responseHeaders.add("Genre not found", genre);
//				//return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//			}
//		}
//		
//		if(bookRepresentation.getLocation()!=null) {
//
//			String location = bookRepresentation.getLocation();
//			if(locationsRepo.getByName(location)!=null) {
//				b.setLocation(locationsRepo.getByName(location));
//			}
//			else {
//				accepted = false;
//				System.out.println("Location not found");
//				//responseHeaders.add("Location not found", location);
//				//return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//			}
//		}
//		
//		if(!accepted) {
//			m.addAttribute("message", "Could not save book: " + bookRepresentation.getTitle());
//			return "addBook2";
//		}
//		
//		b.setTitle(bookRepresentation.getTitle());
//		b.setIsbn(bookRepresentation.getIsbn());
//		
//		b = bookRepo.save(b);
//		
//		return "addbook2";
//	} //out-comm. due to complex code
	
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
