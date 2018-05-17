package liber_application.object_representation;

import javax.persistence.ManyToOne;
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

	@NotNull
	@Size(min=2, max=50)
	private String isbn;
	@NotNull
	@Size(min=2, max=50)
	private String title;
	private String genre; //Corresponds Genre.name
	private String location; //Corresponds Location.name
}
