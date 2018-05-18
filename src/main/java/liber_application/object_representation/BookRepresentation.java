package liber_application.object_representation;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import liber_application.model.Genre;
import liber_application.model.Location;

/**
 * A representation to be used for POST of book-data (REST), to clean up the receiving of REST data for new book entry
 * So user can avoid nested XML/REST
 * @author User
 *
 */
public class BookRepresentation {

	private String isbn;
	private String title;
	private String genre; //Corresponds Genre.name
	private String location; //Corresponds Location.name
	
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
		
}
