package liber_application.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import liber_application.data.BookLoanRepository;
import liber_application.data.BookRepository;
import liber_application.data.UserRepository;
import liber_application.model.Book;
import liber_application.model.BookLoan;
import liber_application.model.User;
import liber_application.object_representation.BookLoanRepresentation;
import liber_application.object_representation.BookLoanRepresentationEmail;


@RestController
@RequestMapping("/loans")
public class BookLoanController {
	
	@Autowired
	public BookLoanRepository loanRepo;
	
	@Autowired
	public UserRepository userRepo;
	
	@Autowired
	public BookRepository bookRepo;
	
	/**
	 * Called with GET /books
	 * Method aimed for XML (or Json) format
	 * @return - a BookCollection that wraps all books
	 */
	@GetMapping
	public BookLoanCollection getBookLoansCollection() {
		// This returns a JSON or XML with the books
		return new BookLoanCollection((List<BookLoan>) loanRepo.findAll());
	}
	
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
	 * Called with POST Json using userId {"readerId":2,"bookId":1} or {"readerId":2,"bookId":1,"startingDate":"2010-01-01"}
	 * Or with POST Json using userEmail {"readerEmail":"valterekholm1@gmail.com","bookId":1} or {"readerEmail":"valterekholm1@gmail.com","bookId":1,"startingDate":"2010-01-01"}
	 * @param bookLoan
	 * @param bookLoanEmail
	 * @return
	 */
	@PostMapping
	public String saveLoan(@RequestBody BookLoanRepresentation bookLoan) { //@RequestBody Integer userId, @RequestParam Integer bookId
		
		Optional<User> user = bookLoan.getReaderId()!=null
				? userRepo.findById(bookLoan.getReaderId())
						: bookLoan.getReaderEmail()!=null
						? userRepo.getByEmail(bookLoan.getReaderEmail())
								: null;
						
						
		Optional<Book> book = bookRepo.findById(bookLoan.getBookId());

		System.out.println("Save loan: " + bookLoan);

		if (user.isPresent() && book.isPresent()) {
			Date startingDate = bookLoan.getStartingDate() == null ? new Date() : bookLoan.getStartingDate();
			BookLoan loan = new BookLoan(user.get(), book.get(), startingDate);
			loanRepo.save(loan);
			return "Saved";
		}
		return "Not saved";
	}
}

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