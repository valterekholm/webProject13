package liber_application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import liber_application.data.BookRepository;
import liber_application.data.GenreRepository;
import liber_application.data.LocationRepository;
import liber_application.model.Book;
import liber_application.model.Genre;
import liber_application.model.Location;

@SpringBootApplication
public class Application {
	
	@Autowired
	BookRepository bookRepo;
	@Autowired
	GenreRepository gennreRepo;
	@Autowired
	LocationRepository locationRepo;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public CommandLineRunner init() {
    	
    	Book a = new Book("1234567","En låtsasbok");
    	Book b = new Book("1234568","En fejkbok");
    	Book c = new Book("1234569","En icke-bok");
    	
    	Genre genre1 = new Genre("Låtsasböcker");
    	Genre genre2 = new Genre("IT");
    	
    	Location shelf1 = new Location("A1", "Hylla A1");
    	
    	return (args)->{
    		gennreRepo.save(genre1);
    		gennreRepo.save(genre2);
    		locationRepo.save(shelf1);
    		
        	a.setGenre(genre1);
        	a.setLocation(shelf1);
        	
    		bookRepo.save(a);
    	};
    }
}