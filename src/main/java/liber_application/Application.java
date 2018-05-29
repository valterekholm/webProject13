package liber_application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import liber_application.data.BookLoanRepository;
import liber_application.data.BookRepository;
import liber_application.data.GenreRepository;
import liber_application.data.LocationRepository;
import liber_application.data.UserRepository;
import liber_application.model.Book;
import liber_application.model.BookLoan;
import liber_application.model.Genre;
import liber_application.model.Location;
import liber_application.model.User;

/**
 * A Spring Boot application made for Lexicon in 2018
 * @author Valter Ekholm
 *
 */
@SpringBootApplication
public class Application {
	
	@Autowired
	BookRepository bookRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	GenreRepository genreRepo;
	@Autowired
	LocationRepository locationRepo;
	@Autowired
	BookLoanRepository loanRepo;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
	public CommandLineRunner init() {

		return (args) -> {

			Book a = new Book("1234567", "En låtsasbok");
			Book b = new Book("1234568", "En fejkbok");
			Book c = new Book("1234569", "En icke-bok");

			Genre genre1 = new Genre("Låtsasböcker");
			Genre genre2 = new Genre("IT");

			Location shelf1 = new Location("A1", "Hylla A1");

			User u1 = new User("Valter","valterekholm1@gmail.com");
			


			genreRepo.save(genre1);
			genreRepo.save(genre2);
			locationRepo.save(shelf1);

			a.setGenre(genre1);
			a.setLocation(shelf1);
			
			a = bookRepo.save(a);
			u1 = userRepo.save(u1);
			
			BookLoan loan1 = new BookLoan(u1, a);
			loanRepo.save(loan1);
			System.out.println(loan1);
		};
    }
}