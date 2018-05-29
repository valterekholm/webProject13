package liber_application.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import liber_application.data.UserRepository;
import liber_application.model.User;
import liber_application.object_representation.UserRepresentation;

/**
 * A REST controller for user
 * @author Valter Ekholm
 *
 */
@RestController
@RequestMapping("/rest/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	/**
	 * Method for JSON repr. of all books in database
	 * 
	 * @return
	 */
//	@GetMapping
//	public Iterable<User> getAllUsers() {
//		// This returns a JSON or XML with the users
//		return userRepo.findAll();
//	}

	/**
	 * Method aimed for XML (or Json) format
	 * Called with GET /users
	 * 
	 * @return - a BookCollection that wraps all books
	 */
	@GetMapping
	public UserCollection getUsersCollection() {
		// This returns a JSON or XML with the users
		return new UserCollection((List<User>) userRepo.findAll());
	}
	
	/**
	 * 
	 * Called with GET /users/1
	 * @param id - id of user
	 * @return Optional of User (can be null)
	 */
	@GetMapping(path="/{id}")
	public Optional<User> getUserById(@PathVariable String id) {
		return userRepo.findById(Integer.parseInt(id));
	}

//	@GetMapping(path = "/add") // Map ONLY GET Requests
//	public String addNewUser(@RequestParam String name, @RequestParam(required = false) String email) { // @RequestParam(value
//
//		// @ResponseBody means the returned String is the response, not a view name (MVC)
//		// @RequestParam means it is a parameter from the GET or POST request
//
//		User u = new User();
//		u.setName(name);
//		u.setEmail(email);
//		userRepo.save(u);
//		return "Saved";
//	}//Not restfull

	/**
	 * Called with POST /users
	 * 	and body with JSON {"name":"b","email":"c"}
	 * @param userRepresentation - serialized data of a user (name, email) 
	 * @return a ResponseEntity and HttpStatus.CREATED
	 */
	@PostMapping
	public ResponseEntity<User> addNewUser(@Valid @RequestBody UserRepresentation userRepresentation) {

		User user = new User();
		HttpHeaders responseHeaders = new HttpHeaders();
//		boolean accepted = true;
//		if (userRepo.getByEmail(userRepresentation.getEmail())!=null) {//allready in db
//			return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_ACCEPTABLE);
//		}
		
		user.setEmail(userRepresentation.getEmail());
		user.setName(userRepresentation.getName());
		user = userRepo.save(user);
		
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	/**
	 * Called with PUT users/1
	 * 	and body with JSON {"name":"a", "email":"b"}
	 * 	id only needed in url
	 * @param id - the id of a user
	 * @param user - serialized data of a user (name, email)
	 * @return a ResponseEntity and user if not found HttpStatus.BAD_REQUEST, else the saved user, and HttpStatus.ACCEPTED
	 */
	@PutMapping(path="/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user){
		//Updates the user found on id of passed user-object
		System.out.println("updateUser med " + user);
		
		user.setId(id);
		
		if (userRepo.findById(user.getId()) == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		user = userRepo.save(user);
		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}
	
	/**
	 * Called with DELETE users/1
	 * @param id - the id of a user
	 * @return a ResponseEntity and if user not found HttpStatus.NOT_FOUND, else HttpStatus.OK
	 */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Integer id) {
		if (userRepo.findById(id) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
		
		//When trying to check if delete worked (the deleted object was gone) : some error happened
	}
	
	/**
	 * A object to collect Users used for XML
	 * @author Valter Ekholm
	 *
	 */
	@XmlRootElement
	//@JsonRootName("restObjectList")
	class UserCollection{
	    @JacksonXmlProperty(localName = "user")
	    @JacksonXmlElementWrapper(useWrapping = false)
		private List<User> users;
		public UserCollection() {}
		
		public UserCollection(List<User> userCollection) {
			this.users = userCollection;
		}
		public List<User> getUsers() {
			return users;
		}
		public void setUsers(List<User> users) {
			this.users= users;
		}
	}
}