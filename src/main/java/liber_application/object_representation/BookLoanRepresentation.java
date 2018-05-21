package liber_application.object_representation;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import liber_application.data.BookRepository;
import liber_application.data.UserRepository;
import liber_application.model.Book;
import liber_application.model.BookLoan;
import liber_application.model.User;

public class BookLoanRepresentation {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	
	Integer readerId;
	Integer bookId;
	Date startingDate; //TODO: change to LocalDate
	Integer allowedWeeksLength;
	
	public BookLoanRepresentation() {
	}
	
	

	public BookLoanRepresentation(Integer readerId, Integer bookId) {
		this.readerId = readerId;
		this.bookId = bookId;
		startingDate = new Date();
		this.allowedWeeksLength = BookLoan.STANDARD_LOAN_PERIOD_WEEKS;
	}


	public BookLoanRepresentation(Integer readerId, Integer bookId, Date startingDate) {
		this.readerId = readerId;
		this.bookId = bookId;
		this.startingDate = startingDate;
		this.allowedWeeksLength = BookLoan.STANDARD_LOAN_PERIOD_WEEKS;
	}



	public BookLoanRepresentation(Integer readerId, Integer bookId, Date startingDate, Integer allowedWeeksLength) {
		this.readerId = readerId;
		this.bookId = bookId;
		this.startingDate = startingDate;
		this.allowedWeeksLength = allowedWeeksLength;
	}



	public Integer getReaderId() {
		return readerId;
	}



	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
	}



	public Integer getBookId() {
		return bookId;
	}



	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	
	public Date getStartingDate() {
		return startingDate;
	}
	
	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}
	
	public Integer getAllowedWeeksLength() {
		return allowedWeeksLength;
	}
	
	public void setAllowedWeeksLength(Integer allowedWeeksLength) {
		this.allowedWeeksLength = allowedWeeksLength;
	}
	
	public BookLoan makeBookLoanObject() throws UserNotFoundException, BookNotFoundException{
		
		Optional<User> reader = userRepo.findById(getReaderId());
		
		if(!reader.isPresent()) {
			throw new UserNotFoundException();
		}
		
		Optional<Book> book = bookRepo.findById(getBookId());
		
		if(!book.isPresent()) {
			throw new BookNotFoundException();
		}
		
		BookLoan bookLoan = new BookLoan(reader.get(), book.get(), getStartingDate(), getAllowedWeeksLength());
		
		return bookLoan;
	}
	
	

}
