package liber_application.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location {
	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	private Integer id;
	private String name;
	private String description;
	
	public Location() {}
	
	public Location(String name) {
		this.name = name;
	}
	
	public Location(String name, String description) {
		this.name = name;
		this.description = description;
	}

//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Location [name=" + name + ", description=" + description + "]";
	}
	
	
	
}
