package liber_application.object_representation;


import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import liber_application.model.Book;
import liber_application.model.Genre;
import liber_application.model.Location;

/**
 * A representation to be used for POST of book-data (REST), to clean up the receiving of REST data for new book entry
 * So user can avoid nested JSON/XML
 * @author Valter Ekholm
 *
 */
public class BookRepresentation {

	private String isbn;
	private String title;
	private String genre; //Corresponds Genre.name
	private String location; //Corresponds Location.name
	private String author;
	
	public BookRepresentation() {
		super();
	}

	public BookRepresentation(String isbn, String title, String genre, String location) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.genre = genre;
		this.location = location;
	}
	
	public BookRepresentation(String isbn, String title, String author, String genre, String location) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.location = location;
	}
	
	public BookRepresentation(Book book) {
		System.out.println("new BookRepresentation by Book");
		isbn = book.getIsbn();
		title = book.getTitle();
		author = book.getAuthor();
		genre = book.getGenre()!=null?book.getGenre().getName():null;
		location = book.getLocation()!=null?book.getLocation().getName():null;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Book makeBookObject() {
		Book b;
		Genre g = genre!=null?new Genre(genre):null;
		Location l = location!=null?new Location(location):null;

		b = new Book(isbn,title,g,l);

		return b;
	}

	@Override
	public String toString() {
		return "BookRepresentation [isbn=" + isbn + ", title=" + title + ", genre=" + genre + ", location=" + location
				+ ", author=" + author + "]";
	}
		
}
