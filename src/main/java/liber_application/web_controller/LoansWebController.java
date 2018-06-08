package liber_application.web_controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import liber_application.data.BookLoanRepository;
import liber_application.data.BookRepository;
import liber_application.data.UserRepository;
import liber_application.model.Book;
import liber_application.model.BookLoan;
import liber_application.object_representation.BookLoanRepresentation;
import liber_application.object_representation.BookNotFoundException;
import liber_application.object_representation.LoanNotFoundException;
import liber_application.object_representation.UserNotFoundException;

@Controller
@RequestMapping("loans")
public class LoansWebController {

	@Autowired
	BookLoanRepository loansRepo;
	
	@Autowired
	UserRepository usersRepo;
	
	@Autowired
	BookRepository booksRepo;
	
	@GetMapping("/all")
	public String getAllBooks(Model m) {
		m.addAttribute("loans", loansRepo.findAll());
		return "listloans";
	}
	
	@GetMapping("/addLoan")
	public String addLoan(Model m) {
		m.addAttribute("loan", new BookLoan());
		m.addAttribute("users", usersRepo.findAll());
		m.addAttribute("books", booksRepo.findAll());
		return "addloan";
	}
	
	/**
	 * Saves a loan starting at the present time
	 * @param loan - a loan with user and book
	 * @param m
	 * @return
	 * @throws UserNotFoundException
	 * @throws BookNotFoundException
	 */
	@PostMapping("/addLoan")
	public String saveLoan(BookLoan loan, Model m) throws UserNotFoundException, BookNotFoundException {
		//BookLoan bl = loan.makeBookLoanObject();
		m.addAttribute("loan", new BookLoan());
		m.addAttribute("users", usersRepo.findAll());
		m.addAttribute("books", booksRepo.findAll());
		
		//Date wasn't auto added
		loan.setStartingDate(new Date());
		
		loan.setWeeksLength(BookLoan.STANDARD_LOAN_PERIOD_WEEKS);
		
		
		BookLoan savedBL = loansRepo.save(loan);//bl
		m.addAttribute("message", "Saved loan: " + savedBL.getId());
		return "addloan";
	}
	
	@GetMapping("/endLoan")
	public String endLoan(Integer id, Model m) throws LoanNotFoundException {
		Optional<BookLoan> bl = loansRepo.findById(id);
		
		if(bl.isPresent()) {
			BookLoan loan = bl.get();
			loan.makeEndedNow();
			loan = loansRepo.save(loan);
			m.addAttribute("message", "Loan has ended");
			return "messagepage";
		}
		else {
			throw new LoanNotFoundException();
		}
	}
	
	//TODO : /loans/deleteLoan
	@GetMapping("/deleteLoan")
	public String deleteBook(Integer id, Model m) {
		Optional<BookLoan> awayLoan = loansRepo.findById(id);
		
		if(awayLoan.isPresent()) {
			loansRepo.delete(awayLoan.get());
			m.addAttribute("loans", loansRepo.findAll());
			m.addAttribute("message", "Loan deleted: " + awayLoan.get().getId());
		}
		
		else {
			m.addAttribute("message", "Could not find loan by id: " + id);
		}
		return "listloans";
	}
}
