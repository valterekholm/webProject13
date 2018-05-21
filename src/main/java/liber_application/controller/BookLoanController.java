package liber_application.controller;

import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import liber_application.data.BookLoanRepository;
import liber_application.model.Book;
import liber_application.model.BookLoan;
import liber_application.object_representation.BookLoanRepresentation;
import liber_application.object_representation.BookNotFoundException;
import liber_application.object_representation.UserNotFoundException;

@RestController
@RequestMapping("/loans")
public class BookLoanController {
	
	@Autowired
	public BookLoanRepository loanRepo;
	
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
		Optional<BookLoan> l = loanRepo.findById(loanId);
		
		if(l.isPresent()) {
			return new ResponseEntity<>(l.get().isOverdue(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	/**
	 * Create a BookLoan and save
	 * Called with POST {"readerId":1,"bookId":1,"startingDate":"2018-01-01","allowedWeeksLength":2} or {"readerId":1,"bookId":1,"startingDate":"2018-01-01"} or {"readerId":1,"bookId":1}
	 * @param bookLoan
	 * @return
	 */
	@PostMapping
	public ResponseEntity<BookLoan> addBookLoan(BookLoanRepresentation bookLoan){
		
		BookLoan loan;
		
		try {
			loan = bookLoan.makeBookLoanObject();
		} catch (UserNotFoundException | BookNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		loan = loanRepo.save(loan);
		return new ResponseEntity<BookLoan>(loan, HttpStatus.CREATED);
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