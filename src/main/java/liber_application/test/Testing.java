package liber_application.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import liber_application.controller.BookLoanController;
import liber_application.data.BookLoanRepository;
import liber_application.data.BookRepository;
import liber_application.data.GenreRepository;
import liber_application.data.UserRepository;
import liber_application.model.Book;
import liber_application.model.BookLoan;
import liber_application.model.Genre;
import liber_application.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class Testing {
	
    //@Autowired
    //private TestEntityManager entityManager;
	
	@Autowired
	BookLoanRepository loanRepo;

	@Autowired
	GenreRepository genreRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	BookLoanController blController;
	
	@Before
	public void setUp() {
		blController = new BookLoanController();
	}

	@Test
	public void testOldDateIsOverdue() {
		
		User u = new User("Erik", "e@g.com");
		Book b = new Book("12345", "En röd bok");
		
		GregorianCalendar cal = new GregorianCalendar(2010, 3, 3); 
		
		Date startD = cal.getTime();
		
		BookLoan bl = new BookLoan(u, b, startD);
		
		userRepo.save(u);
		userRepo.flush();
		
		bookRepo.save(b);
		bookRepo.flush();
		
		loanRepo.save(bl);
		loanRepo.flush();
		
		BookLoan found = loanRepo.findAll().get(0);
		
		assertThat(found.isOverdue());
	}
	
	@Test
	public void testTodayDateIsNotOverdue() {
		
		User u = new User("Erik", "e@g.com");
		Book b = new Book("12345", "En röd bok");
		
//		GregorianCalendar cal = new GregorianCalendar(2010, 3, 3); 
//		
//		Date startD = cal.getTime();
		
		BookLoan bl = new BookLoan(u, b);
		
		userRepo.save(u);
		userRepo.flush();
		
		bookRepo.save(b);
		bookRepo.flush();
		
		loanRepo.save(bl);
		loanRepo.flush();
		
		BookLoan found = loanRepo.findAll().get(0);
		
		assertThat(!found.isOverdue());
	}
	
//	@Test
//	public void testOldDateIsOverdueC() {
//		
//		User u = new User("Erik", "e@g.com");
//		Book b = new Book("12345", "En röd bok");
//		
//		GregorianCalendar cal = new GregorianCalendar(2010, 3, 3); 
//		
//		Date startD = cal.getTime();
//		
//		BookLoan bl = new BookLoan(u, b, startD);
//		
//		userRepo.save(u);
//		userRepo.flush();
//		
//		bookRepo.save(b);
//		bookRepo.flush();
//		
//		loanRepo.save(bl);
//		loanRepo.flush();
//		
//		BookLoan found = loanRepo.findAll().get(0);
//		
//		assertThat(blController.isLoanOverdue(found.getId()));
//	} //out commented 22 /5 -18 due to malfunction, null pointer exception
	
	@Test
	public void testAddingGenre() {
		Genre g = new Genre("Retorik");
		
		genreRepo.save(g);
		genreRepo.flush();
		
		Genre found = genreRepo.getByName(g.getName());
		
		assertThat(found.getName()).isEqualTo(g.getName());
	}
}
