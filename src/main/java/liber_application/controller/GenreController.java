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

@RestController
@RequestMapping("/genres")
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
	 * @return
	 */
	@GetMapping(path="/allXML")
	public GenreCollection getGenresCollection() {
		// This returns a JSON or XML with the books
		return new GenreCollection((List<Genre>) genreRepo.findAll());
	}
	
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
	 * @param name
	 * @return
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