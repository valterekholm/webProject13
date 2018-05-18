package liber_application.controller;


import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import liber_application.data.BookRepository;
import liber_application.data.GenreRepository;
import liber_application.data.LocationRepository;
import liber_application.model.Book;
import liber_application.model.Genre;
import liber_application.model.Location;
import liber_application.model.User;
import liber_application.object_representation.BookRepresentation;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private GenreRepository genreRepo;
	@Autowired
	private LocationRepository locationRepo;
	

	/**
	 * Called with GET /books
	 * Method aimed for XML (or Json) format
	 * @return - a BookCollection that wraps all books
	 */
	@GetMapping
	public BookCollection getBooksCollection() {
		// This returns a JSON or XML with the books
		return new BookCollection((List<Book>) bookRepo.findAll());
	}
	
	
//	/**
//	 * 
//	 * @param isbn
//	 * @param title
//	 * @return
//	 */
//	@GetMapping(path="/add") // Map ONLY GET Requests
//	public String addNewBook (@RequestParam String isbn, @RequestParam(required = false) String title) { //@RequestParam(value = "someParameter", required = true)
//		// @ResponseBody means the returned String is the response, not a view name
//		// @RequestParam means it is a parameter from the GET or POST request
//		
//		Book b = new Book();
//		b.setIsbn(isbn);
//		b.setTitle(title);
//		bookRepo.save(b);
//		return "Saved";
//	} //not restfull

//	/**
//	 * For adding book with existing genre (genre id and/or name)
//	 * @param book - Book object, JSON {"isbn":"1996679", "title":"My story", "genre":{"id":1}}} or {"isbn":"1996677", "title":"My story"}
//	 * @return
//	 */
//	@PostMapping(path="/add")
//	public String addNewBookObject (@Valid @RequestBody Book book) {
//		// @ResponseBody means the returned String is the response, not a view name
//		// @RequestParam means it is a parameter from the GET or POST request
//		
//		if(book.getGenre()!=null) {
//			
//			Genre bGenre = book.getGenre();
//
//			Iterable<Genre> allGenres = genreRepo.findAll();
//			boolean foundGenre = false;
//			
//			if(bGenre.getId()!=null)
//			for(Genre g : allGenres) {//If id exists
//				if(g.getId() == bGenre.getId()) {
//					foundGenre = true;
//					bGenre = g;
//				}
//			}
//			
//			for(Genre g : allGenres) {//But if name exists, that overrides previous check-match
//				if(g.getName().equalsIgnoreCase(bGenre.getName())) {
//					System.out.println("Hittade sparat namn");
//					foundGenre = true;
//					//bGenre = g; // fungerar ej
//					bGenre.setId(g.getId());
//				}
//			}
//			
//			if(!foundGenre) {
//				return "genre not found, nothing saved";
//			}
//		}
//		
//		bookRepo.save(book);
//		return "Saved";
//	} //outcomm 18/5 -18 after removing integer id from Genre
	
	/**
	 * Called with POST /users
	 * 	and body of JSON {"title": "abc", "isbn":"123", "genre":"abc", "location":"abc"}
	 * @param book
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Book> addNewBook (@Valid @RequestBody BookRepresentation bookRepresentation) {
		
		Book book = new Book();
		HttpHeaders responseHeaders = new HttpHeaders();
		boolean accepted = true;
		
		if(bookRepresentation.getGenre()!=null) {
			String genre = bookRepresentation.getGenre();
			//book.setGenre(new Genre(bookRepresentation.getGenre()));
			//load from database
			if(genreRepo.getByName(genre)!=null) {
				//Genre Exists
				book.setGenre(genreRepo.getByName(genre));
			}
			else {
				accepted = false;
				responseHeaders.add("Genre not found", genre);
				//return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		}
		
		if(bookRepresentation.getLocation()!=null) {

			String location = bookRepresentation.getLocation();
			if(locationRepo.getByName(location)!=null) {
				book.setLocation(locationRepo.getByName(location));
			}
			else {
				accepted = false;
				responseHeaders.add("Location not found", location);
				//return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		}
		
		if(!accepted) {
			return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_ACCEPTABLE);
		}
		
		book.setTitle(bookRepresentation.getTitle());
		book.setIsbn(bookRepresentation.getIsbn());
		
		book = bookRepo.save(book);
		
		return new ResponseEntity<Book>(book, HttpStatus.CREATED);
	}
	
	/**
	 * Update a book
	 * Called with PUT /books/1
	 * 	with a body of REST {"isbn":"123d","title":"abc","genre":"abc","location":"abc"}
	 * 	Notice: an empty field will overwrite previous data
	 * @param id
	 * @param book
	 * @return
	 */
	@PutMapping(path="/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody BookRepresentation book){
		//Updates the user found on id of passed user-object
		System.out.println("updateBook med " + book);
		
		
		if (bookRepo.findById(id) == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Book newBook = book.makeBookObject();
		newBook.setId(id);
		System.out.println("updateBook med " + newBook);
		newBook = bookRepo.save(newBook);
		return new ResponseEntity<>(newBook, HttpStatus.ACCEPTED);
	}
	
	/**
	 * Delete a book by id
	 * Called with DELETE books/1
	 * @param id
	 * @return
	 */
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Book> deleteBook(@PathVariable Integer id) {
		
		if(bookRepo.findById(id)==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		bookRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
}
/**
 * A class used for XML data of a collection of books
 * @author User
 *
 */
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