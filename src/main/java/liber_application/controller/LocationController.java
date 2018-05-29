package liber_application.controller;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import liber_application.data.LocationRepository;
import liber_application.model.Location;

/**
 * The REST controller for book locations (shelves and similar)
 * @author Valter Ekholm
 *
 */
@RestController
@RequestMapping("/locations")
public class LocationController {
	
	@Autowired
	private LocationRepository locationRepo;
	
//	@GetMapping
//	public Iterable<Location> getAllLocations() {
//		// This returns a JSON or XML with the locations
//		return locationRepo.findAll();
//	}
	
	/**
	 * Used to get a well-formatted collection of all locations i database
	 * @return a LocationCollection
	 */
	@GetMapping
	public LocationCollection getLocationsCollection() {
		// This returns a JSON or XML with the locations
		return new LocationCollection((List<Location>) locationRepo.findAll());
	}
	
	@PostMapping(path="/{name}")
	public String addLocation(@PathVariable String name) {
		if(locationRepo.getByName(name)==null) {
			locationRepo.save(new Location(name));
			return "Saved";
		}
		else {
			return "Allready saved";
		}
		
	}
	
	@PostMapping
	public ResponseEntity<Location> addLocationREST(@RequestBody String name){
		
		if(locationRepo.getByName(name)!=null) {
			return new ResponseEntity<Location>(HttpStatus.ALREADY_REPORTED);
		}
		else {
			Location newLocation = locationRepo.save(new Location(name));
			return new ResponseEntity<Location>(newLocation,HttpStatus.CREATED);
		}
	}
	
	@PutMapping(path="/{name}")
	public ResponseEntity<Location> updateLocation(@PathVariable String oldName, String newName){
		
		Location l = locationRepo.getByName(oldName);
		
		return new ResponseEntity<Location>(HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	/**
	 * Delete a location post, by name
	 * @param name - the name of location to delete
	 * @return a ResponseEntity and if location not found HttpStatus.NOT_FOUND, else HttpStatus.OK
	 */
	@DeleteMapping(path="/{name}")
	public ResponseEntity<Location> deleteLocation(@PathVariable String name) {
		if(locationRepo.getByName(name)==null) {
			System.out.println("deleteLocation, " + name + " not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		System.out.println("deleteLocation, " + name + " found");
		locationRepo.deleteByName(name);
		return new ResponseEntity<>(HttpStatus.OK);
		
//		if(locationRepo.getByName(name)==null) {
//			return new ResponseEntity<>(HttpStatus.OK);
//		}
//		else {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}
}

/**
 * 
 * @author Valter Ekholm
 *
 */
@XmlRootElement
class LocationCollection{
    @JacksonXmlProperty(localName = "location")
    @JacksonXmlElementWrapper(useWrapping = false)
	private List<Location> locations;
    
    public LocationCollection() {}
    
    public LocationCollection(List<Location> locations) {
    	this.locations = locations;
    }

	public List<Location> getGenres() {
		return locations;
	}

	public void setGenres(List<Location> locations) {
		this.locations = locations;
	}
}
