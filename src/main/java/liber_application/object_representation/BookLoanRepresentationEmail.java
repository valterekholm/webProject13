package liber_application.object_representation;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonFormat;

import liber_application.data.BookRepository;
import liber_application.data.UserRepository;
import liber_application.model.Book;
import liber_application.model.BookLoan;
import liber_application.model.User;

public class BookLoanRepresentationEmail {

	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	
	String readerEmail;
	Integer bookId;
	
	//@JsonFormat(pattern="yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd")
	Date startingDate; //TODO: change to LocalDate
	
	Integer allowedWeeksLength;
	
	public BookLoanRepresentationEmail() {
		super();
		System.out.println("Constructor");
	}
	
	

	public BookLoanRepresentationEmail(String readerEmail, Integer bookId) {
		System.out.println("Constructor with " + readerEmail + " and " + bookId);
		this.readerEmail = readerEmail;
		System.out.println("1");
		this.bookId = bookId;
		System.out.println("2");
		startingDate = new Date();
		System.out.println("3");
		this.allowedWeeksLength = BookLoan.STANDARD_LOAN_PERIOD_WEEKS;
		System.out.println(this);
	}


	public BookLoanRepresentationEmail(String readerEmail, Integer bookId, Date startingDate) {
		System.out.println("Constructor with " + readerEmail + ", " + bookId + " and " + startingDate);
		this.readerEmail = readerEmail;
		this.bookId = bookId;
		this.startingDate = startingDate;
		this.allowedWeeksLength = BookLoan.STANDARD_LOAN_PERIOD_WEEKS;
	}



	public BookLoanRepresentationEmail(String readerEmail, Integer bookId, Date startingDate, Integer allowedWeeksLength) {
		System.out.println("Constructor with " + readerEmail + ", " + bookId + ", " + startingDate + " and " + allowedWeeksLength);
		this.readerEmail = readerEmail;
		this.bookId = bookId;
		this.startingDate = startingDate;
		this.allowedWeeksLength = allowedWeeksLength;
	}



	public String getReaderEmail() {
		return readerEmail;
	}



	public void setReaderEmail(String readerEmail) {
		this.readerEmail = readerEmail;
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
		
		Optional<User> reader = userRepo.getByEmail(getReaderEmail());
		
		if(!reader.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		else {
			System.out.println("Hittade user");
		}
		
		Optional<Book> book = bookRepo.findById(getBookId());
		
		if(!book.isPresent()) {
			throw new BookNotFoundException("Book not found");
		}
		else {
			System.out.println("Book found");
		}
		
		BookLoan bookLoan = new BookLoan(reader.get(), book.get(), getStartingDate(), getAllowedWeeksLength());
		
		return bookLoan;
	}

	@Override
	public String toString() {
		return "BookLoanRepresentationEmail [readerEmail=" + readerEmail + ", bookId=" + bookId + ", startingDate=" + startingDate
				+ ", allowedWeeksLength=" + allowedWeeksLength + "]";
	}
	
}