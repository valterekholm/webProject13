package liber_application.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import liber_application.data.BookLoanRepository;
import liber_application.data.BookRepository;
import liber_application.data.UserRepository;
import liber_application.model.Book;
import liber_application.model.BookLoan;
import liber_application.model.User;
import liber_application.object_representation.BookLoanRepresentation;
import liber_application.object_representation.BookNotFoundException;
import liber_application.object_representation.UserNotFoundException;

/**
 * A REST controller for the book loans.
 * @author Valter Ekholm
 *
 */
@RestController
@RequestMapping("/rest/loans")
public class BookLoanController {
	
	@Autowired
	public BookLoanRepository loanRepo;
	
	@Autowired
	public UserRepository userRepo;
	
	@Autowired
	public BookRepository bookRepo;
	
	/**
	 * Get all loans
	 * Called with GET /books
	 * Method aimed for XML (or Json) format
	 * @return - a BookCollection that wraps all books
	 */
	@GetMapping
	public BookLoanCollection getBookLoansCollection() {
		// This returns a JSON or XML with the books
		return new BookLoanCollection((List<BookLoan>) loanRepo.findAll());
	}
	
	/**
	 * Get loan by id
	 * Call with GET loans/1
	 * @param id id of loan
	 * @return a ResponseEntity and if loan is found: the loan and HttpStatus.FOUND, else just HttpStatus.NOT_FOUND
	 */
	@GetMapping(path="/{id}")
	public ResponseEntity<BookLoan> getBookLoanById(@PathVariable Integer id) {
		Optional<BookLoan> loan = loanRepo.findById(id);
		
		if(loan.isPresent()) {
			return new ResponseEntity<>(loan.get(),HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Get all loans connected to a specific email address
	 * Call with GET loans/email/john@supermail.se
	 * @param email - the email that can be stored in User of a BookLoan
	 * @return a ResponseEntity and if any found: a BookLoanCollection with loans, else just HttpStatus.NOT_FOUND
	 */
	@GetMapping(path="/email/{email}")
	public ResponseEntity<BookLoanCollection> getBookLoanByEmail(String email) {
		Iterable<BookLoan> loans = loanRepo.findByReaderEmail(email);
		
		if(loans.iterator().hasNext()) {
			return new ResponseEntity<>(new BookLoanCollection((List<BookLoan>)loans), HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	/**
	 * Check if a loan is overdue (it's return date has passed)
	 * Call with GET /loans/isOverdue/1
	 * @param loanId - the id of a loan
	 * @return a ResponseEntity and if found true/false else just HttpStatus.NOT_FOUND
	 */
	@GetMapping(path="/isOverdue/{loanId}")
	public ResponseEntity<Boolean> isLoanOverdue(@PathVariable Integer loanId) {
		System.out.println("isLoanOverdue with arg " + loanId);
		Optional<BookLoan> l = loanRepo.findById(loanId);
		
		if(l.isPresent()) {
			System.out.println("Found the loan, " + loanId + ", " + l.get() + ", i-o-d: " + l.get().isOverdue());
			return new ResponseEntity<>(l.get().isOverdue(), HttpStatus.OK);
		}
		else {
			System.out.println("Didn't find loan, " + loanId);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	/**
	 * Get the "status" of a loan, defined in the JavaDoc of class BookLoan
	 * @param loanId - the id of a loan
	 * @return string telling the status of a loan
	 */
	@GetMapping(path="/status/{loanId}")
	public ResponseEntity<String> getLoanStatus(@PathVariable Integer loanId){
		Optional<BookLoan> l = loanRepo.findById(loanId);
		
		if(l.isPresent()) {
			System.out.println("Found the loan, " + loanId + ", " + l.get() + ", i-o-d: ");
			
			boolean isClosed = l.get().isClosed();
			boolean isOverdue = l.get().isOverdue();
			
			if(isClosed){
				//was late?
				if(isOverdue) {
					return new ResponseEntity<>("late", HttpStatus.OK);
				}
				else {
					return new ResponseEntity<>("closed", HttpStatus.OK);
				}
			}
			else { //is open / not returned
				//is late?
				if(isOverdue) {
					return new ResponseEntity<>("missing", HttpStatus.OK);
				}
				else {
					return new ResponseEntity<>("open", HttpStatus.OK);
				}
			}
		}
		else {
			System.out.println("Didn't find loan, " + loanId);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
//	/**
//	 * Create a BookLoan and save
//	 * Called with POST Json {"readerId":1,"bookId":1,"startingDate":"2018-01-01","allowedWeeksLength":2} or {"readerId":1,"bookId":1,"startingDate":"2018-01-01"} or {"readerId":1,"bookId":1}
//	 * @param bookLoan
//	 * @return
//	 * @throws BookNotFoundException 
//	 * @throws UserNotFoundException 
//	 */
//	@PostMapping
//	public ResponseEntity<BookLoan> addBookLoan(@Valid @RequestBody BookLoanRepresentation bookLoan) throws UserNotFoundException, BookNotFoundException{
//		
//		System.out.println("addBookLoan med " + bookLoan);
//		
////		BookLoan loan;
////		HttpHeaders responseHeaders = new HttpHeaders();
////		
////		try {
////			loan = bookLoan.makeBookLoanObject();
////		} catch (UserNotFoundException | BookNotFoundException e) {
////			responseHeaders.add("Error", e.getMessage());
////			return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_ACCEPTABLE);
////		}
////		
////		loan = loanRepo.save(loan);
////		return new ResponseEntity<BookLoan>(loan, HttpStatus.CREATED);
//		
//		
//		BookLoan loan = new BookLoan();
//		HttpHeaders responseHeaders = new HttpHeaders();
//		boolean accepted = true;
//		
//		if(bookLoan.getReaderId()!=null) {
//			Optional<User> user = userRepo.findById(bookLoan.getReaderId());
//			
//			if(!user.isPresent()) {
//				accepted = false;
//				responseHeaders.add("User not found", bookLoan.getReaderId().toString());
//			}
//		}
//		else {
//			accepted = false;
//		}
//		
//		if(bookLoan.getBookId()!=null) {
//			Optional<Book> book = bookRepo.findById(bookLoan.getBookId());
//			
//			if(!book.isPresent()) {
//				accepted = false;
//				responseHeaders.add("Book not found", bookLoan.getReaderId().toString());
//			}
//		}
//		else {
//			accepted = false;
//		}
//		
//		if(accepted) {
//			loan = bookLoan.makeBookLoanObject();
//			loan = loanRepo.save(loan);
//			return new ResponseEntity<>(loan, HttpStatus.CREATED);
//		}
//		else {
//			return new ResponseEntity<>(responseHeaders, HttpStatus.BAD_REQUEST);
//		}
//	} //out commented 21 may 2018 due to malfunction
	
	/**
	 * Save a loan
	 * Called with POST to /loans - Json {"readerId":2,"bookId":1} or {"readerId":2,"bookId":1,"startingDate":"2010-01-01"}
	 * Or with POST to /loans {"readerEmail":"valterekholm1@gmail.com","bookId":1} or {"readerEmail":"valterekholm1@gmail.com","bookId":1,"startingDate":"2010-01-01"}
	 * Or with a forth argument of "allowedWeeksLength":1
	 * @param bookLoan - serialized data of a book loan (readerId, readerEmail, bookId, startingDate, allowedWeeksLength)
	 * @return ResponseEntity with saved BookLoan or (if error) id of any book/user not found
	 */
	@PostMapping
	public ResponseEntity<BookLoan> saveLoan(@RequestBody BookLoanRepresentation bookLoan) { //@RequestBody Integer userId, @RequestParam Integer bookId
		HttpHeaders responseHeaders = new HttpHeaders();
		
		//Set user from id (if present) or email
		Optional<User> user = bookLoan.getReaderId()!=null
				? userRepo.findById(bookLoan.getReaderId())
						: bookLoan.getReaderEmail()!=null
						? userRepo.getByEmail(bookLoan.getReaderEmail())
								: Optional.empty();

		Optional<Book> book = bookRepo.findById(bookLoan.getBookId());
		System.out.println("Save loan: " + bookLoan);

		if (user.isPresent() && book.isPresent()) {	
			
			Date startingDate = bookLoan.getStartingDate() == null ? new Date() : bookLoan.getStartingDate();
			BookLoan loan = new BookLoan(user.get(), book.get(), startingDate);
			loan = loanRepo.save(loan);
			return new ResponseEntity<>(loan, HttpStatus.CREATED);
		}
		else {
			if(!user.isPresent()) {
				responseHeaders.add("User not found", bookLoan.getEmailOrId());
			}
			if(!book.isPresent()) {
				responseHeaders.add("Book not found", bookLoan.getBookId().toString());
			}
			return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	/**
	 * Update a loan - overwriting all fields except id
	 * Called with PUT /loans/1 and Json Body {"readerId": 1, "bookId":1, "startingDate":"2011-11-11", "allowedWeeksLength":1}, see saveLoan()
	 * @param loanId - the id of a loan
	 * @param loan - serialized data of a book loan
	 * @return The updated BookLoan
	 */
	@PutMapping(path="/{loanId}")
	public ResponseEntity<BookLoan> updateLoan(@PathVariable Integer loanId, @RequestBody BookLoanRepresentation loan){//@PathVariable Integer loanId
		System.out.println("updateLoan med " + loan);
		Optional<BookLoan> realLoan = loanRepo.findById(loanId);
		HttpHeaders responseHeaders = new HttpHeaders();
		
		boolean accepted = true;
		
		if(!realLoan.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		System.out.println("Hittade matchande lån: " + realLoan.get());
		
		//Overwrite the loan data
		
		//Get the book
		Optional<Book> realBook = bookRepo.findById(loan.getBookId());
		
		if(!realBook.isPresent()) {
			responseHeaders.add("Book not found", loan.getBookId().toString());
			accepted=false;
			System.out.println("Book not found " + loan.getBookId());
		}
		
		//Get the user
		Optional<User> realUser = userRepo.getByEmail(loan.getReaderEmail());
		if(!realUser.isPresent()) {
			realUser = userRepo.findById(loan.getReaderId());
		}

		
		if(!realUser.isPresent()) {
			responseHeaders.add("User not found", "email: " + loan.getReaderEmail() + " or id: " + loan.getReaderId());
			accepted = false;
			System.out.println("User not found email: " + loan.getReaderEmail() + " or id: " + loan.getReaderId());
		}
		
		if(!accepted) {
			return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_ACCEPTABLE);
		}
		
		realLoan.get().setBook(realBook.get());
		realLoan.get().setReader(realUser.get());
		realLoan.get().setStartingDate(loan.getStartingDate());
		realLoan.get().setWeeksLength(loan.getAllowedWeeksLength());
		
		BookLoan saved = loanRepo.save(realLoan.get());
				
		return new ResponseEntity<>(saved, HttpStatus.ACCEPTED);
	}
	
	/**
	 * Register a returning of a book, setting the returnedDate to present date and time
	 * Called with PUT /back/1
	 * @param id - the id of a loan
	 * @return the updated BookLoan
	 */
	@PutMapping(path="/back/{id}")
	public ResponseEntity<BookLoan> endNow(@PathVariable Integer id){
		Optional<BookLoan> realLoan = loanRepo.findById(id);
		
		if(realLoan.isPresent()) {
			realLoan.get().makeEndedNow();
			BookLoan saved = loanRepo.save(realLoan.get());
			
			return new ResponseEntity<>(saved, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Called with DELETE /loans/1
	 * @param loanId - the id of a loan
	 * @return a ResponseEntity and if id not found HttpStatus.NOT_FOUND, else HttpStatus.OK
	 */
	@DeleteMapping(path="/{id}")
	public ResponseEntity<BookLoan> deleteLoan(@PathVariable Integer loanId){
		if(loanRepo.findById(loanId)==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		loanRepo.deleteById(loanId);
		return new ResponseEntity<>(HttpStatus.OK);
		
		//When trying to check if delete worked (the deleted object was gone) : some error happened
	}
}

/**
 * Used to enable returning of list of user with good format
 * @author Valter Ekholm
 *
 */
@XmlRootElement
class BookLoanCollection{
    @JacksonXmlProperty(localName = "bookLoan")
    @JacksonXmlElementWrapper(useWrapping = false)
	private List<BookLoan> bookLoans;
	public BookLoanCollection() {}
	
	public BookLoanCollection(List<BookLoan> bookLoanCollection) {
		this.bookLoans = bookLoanCollection;
	}
	public List<BookLoan> getBookLoans() {
		return bookLoans;
	}

	public void setBookLoans(List<BookLoan> bookLoans) {
		this.bookLoans = bookLoans;
	}
}