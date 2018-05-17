package myApplication.controller;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import myApplication.data.BookRepository;
import myApplication.data.GenreRepository;
import myApplication.model.Book;
import myApplication.model.Genre;
import myApplication.model.User;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private GenreRepository genreRepo;
	
	/**
	 * Method for JSON repr. of all books in database
	 * @return
	 */
	@GetMapping(path="/all")
	public Iterable<Book> getAllBooks() {
		// This returns a JSON or XML with the books
		return bookRepo.findAll();
	}
	/**
	 * Method aimed for XML format
	 * @return - a BookCollection that wraps all books
	 */
	@GetMapping(path="/allXML")
	public BookCollection getBooksCollection() {
		// This returns a JSON or XML with the books
		return new BookCollection((List<Book>) bookRepo.findAll());
	}
	
	
	/**
	 * 
	 * @param isbn
	 * @param title
	 * @return
	 */
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
//	/**
//	 * 
//	 * @param isbn
//	 * @param title
//	 * @return
//	 */
//	@PostMapping(path="/add")
//	public String addNewBookPost (@RequestParam String isbn, @RequestParam(required = false) String title) { //@RequestParam(value = "someParameter", required = true)
//		// @ResponseBody means the returned String is the response, not a view name
//		// @RequestParam means it is a parameter from the GET or POST request
//		
//		Book b = new Book();
//		b.setIsbn(isbn);
//		b.setTitle(title);
//		bookRepo.save(b);
//		return "Saved";
//	}
	/**
	 * 
	 * @param book - Book object, JSON {"isbn":"1996679", "title":"My story", "genre":{"id":1}}} or {"isbn":"1996677", "title":"My story"}
	 * @return
	 */
	@PostMapping(path="/add")
	public String addNewBookObject (@RequestBody Book book) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		if(book.getGenre()!=null) {
			
			Genre bGenre = book.getGenre();

			Iterable<Genre> allGenres = genreRepo.findAll();
			boolean foundGenre = false;
			
			if(bGenre.getId()!=null)
			for(Genre g : allGenres) {//If id exists
				if(g.getId() == bGenre.getId()) {
					foundGenre = true;
					bGenre = g;
				}
			}
			
			for(Genre g : allGenres) {//But if name exists, that overrides previous check-match
				if(g.getName().equalsIgnoreCase(bGenre.getName())) {
					System.out.println("Hittade sparat namn");
					foundGenre = true;
					//bGenre = g;
					bGenre.setId(g.getId());
				}
			}
			
			if(!foundGenre) {
				return "genre not found, nothing saved";
			}
		}
		
		bookRepo.save(book);
		return "Saved";
	}
	
}

@XmlRootElement
//@JsonRootName("restObjectList")
class BookCollection{
    @JacksonXmlProperty(localName = "book")
    @JacksonXmlElementWrapper(useWrapping = false)
	private List<Book> books;
	public BookCollection() {}
	
	public BookCollection(List<Book> bookCollection) {
		this.books = bookCollection;
	}
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
}