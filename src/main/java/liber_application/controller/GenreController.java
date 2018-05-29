package liber_application.controller;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import liber_application.data.GenreRepository;
import liber_application.model.Genre;

/**
 * The REST controller for genre.
 * Because the class has only one field (which is id also), no updates can currently be made to an existing genre
 * If change of genre is wanted, a new genre must be created, and books altered to this new genre.
 * @author Valter Ekholm
 *
 */
@RestController
@RequestMapping("/rest/genres")
public class GenreController {
	
	@Autowired
	private GenreRepository genreRepo;
	
	@GetMapping(path="/all")
	public Iterable<Genre> getAllGenres() {
		// This returns a JSON or XML with the books
		return genreRepo.findAll();
	}
	
	/**
	 * Used to get a well-formatted collection of all genres i database
	 * @return a GenreCollection of all genres
	 */
	@GetMapping(path="/allXML")
	public GenreCollection getGenresCollection() {
		// This returns a JSON or XML with the books
		return new GenreCollection((List<Genre>) genreRepo.findAll());
	}
	
	/**
	 * Add a genre by use of url
	 * @param name - name of new genre
	 * @return String with info
	 */
	@PostMapping(path="/add/{name}")
	public String addGenre(@PathVariable String name) {
		if(genreRepo.getByName(name)==null) {
			genreRepo.save(new Genre(name));
			return "Saved";
		}
		else {
			return "Allready saved";
		}
		
	}
	
	/**
	 * Add genre by use of Request body
	 * @param name - a String
	 * @return a ResponseEntity and if the genre allready exists HttpStatus.ALREADY_REPORTED, else the new genre and HttpStatus.CREATED
	 */
	@PostMapping(path="/add")
	public ResponseEntity<Genre> addGenreREST(@RequestBody String name){
		
		if(genreRepo.getByName(name)!=null) {
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}
		else {
			Genre newGenre = genreRepo.save(new Genre(name));
			return new ResponseEntity<>(newGenre,HttpStatus.CREATED);
		}
	}
	
	/**
	 * Delete a genre post by name
	 * @param name - a String
	 * @return a ResponseEntity and if was deleted HttpStatus.OK, else if not found HttpStatus.NOT_FOUND, else HttpStatus.INTERNAL_SERVER_ERROR
	 */
	@PostMapping(path="/delete/{name}")
	public ResponseEntity<Genre> deleteGenre(@PathVariable String name) {
		if(genreRepo.getByName(name)==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		genreRepo.delete(new Genre(name));
		
		if(genreRepo.getByName(name)==null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

@XmlRootElement
class GenreCollection{
    @JacksonXmlProperty(localName = "genre")
    @JacksonXmlElementWrapper(useWrapping = false)
	private List<Genre> genres;
    
    public GenreCollection() {}
    
    public GenreCollection(List<Genre> genres) {
    	this.genres = genres;
    }

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
}