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
/**
 * Used to simplify data transfer with REST, no nested data is needed in JSON / XML, and hide some fields
 * @author Valter Ekholm
 *
 */
public class BookLoanRepresentation {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	
	Integer readerId;
	String readerEmail;//Have email also to make searching/POST'ing etc. easy for user
	Integer bookId;
	
	//@JsonFormat(pattern="yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd")
	Date startingDate; //TODO: change to LocalDate
	
	Integer allowedWeeksLength;
	
	public BookLoanRepresentation() {
		super();
		System.out.println("Constructor");
	}
	
	

	public BookLoanRepresentation(Integer readerId, Integer bookId) {
		System.out.println("Constructor with " + readerId + " and " + bookId);
		this.readerId = readerId;
		this.bookId = bookId;
		startingDate = new Date();
		this.allowedWeeksLength = BookLoan.STANDARD_LOAN_PERIOD_WEEKS;
	}


	public BookLoanRepresentation(Integer readerId, Integer bookId, Date startingDate) {
		System.out.println("Constructor with " + readerId + ", " + bookId + " and " + startingDate);
		this.readerId = readerId;
		this.bookId = bookId;
		this.startingDate = startingDate;
		this.allowedWeeksLength = BookLoan.STANDARD_LOAN_PERIOD_WEEKS;
	}



	public BookLoanRepresentation(Integer readerId, Integer bookId, Date startingDate, Integer allowedWeeksLength) {
		System.out.println("Constructor with " + readerId + ", " + bookId + ", " + startingDate + " and " + allowedWeeksLength);
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
		System.out.println("makeBookLoanObject");
		Optional<User> reader;
		
		//if both are set
		if(getReaderId()!=null && getReaderEmail()!=null) {
			System.out.println("id and email set");
			//Search by them
			Optional<User> found = userRepo.findByIdAndEmail(getReaderId(), getReaderEmail());
			
			//If email and id not match
			if(!found.isPresent()) {//not present
				//favor email more then id
				reader = userRepo.getByEmail(getReaderEmail());
				
				//Message that id/email did not match, and email choosen
				
				if(!reader.isPresent()) {
					//But if email didn't work
					reader = userRepo.findById(getReaderId());//try again
				}
			}
			else {
				reader = userRepo.findById(getReaderId());
			}
		}
		
		//Tries to find the user/reader by it's ID, but if there is no such - then by email - and if no email throw exception
		else if(getReaderId()!=null) {
			System.out.println("only id set");
			reader = userRepo.findById(getReaderId());
		}
		else if(getReaderEmail()!=null) {
			System.out.println("only email set");
			reader = userRepo.getByEmail(getReaderEmail());
		}
		else {
			System.out.println("neither id email set");
			throw new NullPointerException();
		}
		
		
		
		if(!reader.isPresent()) {
			System.out.println("User not found");
			throw new UserNotFoundException("User not found");
		}
		else {
			//System.out.println("Hittade user");
		}
		
		Optional<Book> book = bookRepo.findById(getBookId());
		
		if(!book.isPresent()) {
			System.out.println("Book not found");
			throw new BookNotFoundException("Book not found");
		}
		else {
			//System.out.println("Book found");
		}
		
		System.out.println("To create a BookLoan with args: " + reader.get()+","+ book.get()+","+ getStartingDate()+","+ getAllowedWeeksLength());
		
		BookLoan bookLoan = new BookLoan(reader.get(), book.get(), getStartingDate(), getAllowedWeeksLength());
		
		return bookLoan;
	}
	
	public String getEmailOrId() {
		return getReaderEmail()!=null?getReaderEmail():getReaderId()!=null?getReaderId().toString():"";
	}
	@Override
	public String toString() {
		return "BookLoanRepresentation [readerId=" + readerId + ", readerEmail=" + readerEmail + ", bookId=" + bookId
				+ ", startingDate=" + startingDate + ", allowedWeeksLength=" + allowedWeeksLength + "]";
	}
	
	
	
}
