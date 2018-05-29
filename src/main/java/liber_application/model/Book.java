package liber_application.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * The book class - representing a single physical book
 * @author Valter Ekholm
 *
 */
@Entity
public class Book {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String isbn;
	@NotNull
	@Size(min=2, max=50)
	private String title;
	@ManyToOne
	private Genre genre;
	
	@ManyToOne
	private Location location;
	
	public Book() {

	}
	
	public Book(String isbn) {
		super();
		this.isbn = isbn;
	}
	
	public Book(String isbn, String title) {
		super();
		this.isbn = isbn;
		this.title = title;
	}
	public Book(String isbn, String title, Genre genre) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.genre = genre;
	}
	
	public Book(String isbn, String title, Genre genre, Location location) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.genre = genre;
		this.location = location;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public String toString() {
		return "Book id:"+getId()+", ISBN:"+getIsbn()+", title:"+getTitle()+", genre:"+getGenre().getName()+", location:"+getLocation().getName();
	}
		
}
