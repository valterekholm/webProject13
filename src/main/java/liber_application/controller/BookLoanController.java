package liber_application.controller;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import liber_application.data.BookLoanRepository;
import liber_application.model.Book;
import liber_application.model.BookLoan;

@RestController
@RequestMapping("/loans")
public class BookLoanController {
	
	public BookLoanRepository loanRepo;
	
	/**
	 * Called with GET /books
	 * Method aimed for XML (or Json) format
	 * @return - a BookCollection that wraps all books
	 */
	@GetMapping
	public BookLoanCollection getBooksCollection() {
		// This returns a JSON or XML with the books
		return new BookLoanCollection((List<BookLoan>) loanRepo.findAll());
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
	public List<BookLoan> getBooks() {
		return bookLoans;
	}

	public void setBooks(List<BookLoan> bookLoans) {
		this.bookLoans = bookLoans;
	}
}