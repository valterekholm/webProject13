package myApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import myApplication.data.BookRepository;
import myApplication.model.Book;
import myApplication.model.User;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookRepository bookRepo;
	
	@GetMapping(path="/all")
	public Iterable<Book> getAllBooks() {
		// This returns a JSON or XML with the users
		return bookRepo.findAll();
	}
	
	@GetMapping(path="/add") // Map ONLY GET Requests
	public String addNewBook (@RequestParam String isbn, @RequestParam(required = false) String title) { //@RequestParam(value = "someParameter", required = true)
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		Book b = new Book();
		b.setIsbn(isbn);
		b.setTitle(title);
		bookRepo.save(b);
		return "Saved";
	}
	
	@PostMapping(path="/add")
	public String addNewBookPost (@RequestParam String isbn, @RequestParam(required = false) String title) { //@RequestParam(value = "someParameter", required = true)
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		Book b = new Book();
		b.setIsbn(isbn);
		b.setTitle(title);
		bookRepo.save(b);
		return "Saved";
	}
	
	@PostMapping(path="/addJson")
	public String addNewBookJson (@RequestBody Book book) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		bookRepo.save(book);
		return "Saved";
	}

}