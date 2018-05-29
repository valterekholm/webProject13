package liber_application.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * The genre entity
 * It has no artificial key and a genre can currently not be altered once saved.
 * If you want to change an existing genre that is used, you must create a new genre and change the connected books to that
 * @author Valter Ekholm
 *
 */
@Entity
public class Genre {
	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	private Integer id;
	private String name;
	
	public Genre() {}
	
	public Genre(String name) {
		super();
		this.name = name;
	}
	
	
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}