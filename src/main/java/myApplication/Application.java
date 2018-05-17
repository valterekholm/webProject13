package myApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import myApplication.data.BookRepository;
import myApplication.data.GenreRepository;
import myApplication.model.Book;
import myApplication.model.Genre;

@SpringBootApplication
public class Application {
	
	@Autowired
	BookRepository bookRepo;
	@Autowired
	GenreRepository gennreRepo;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public CommandLineRunner init() {
    	
    	Book a = new Book("1234567","En låtsasbok");
    	Book b = new Book("1234568","En fejkbok");
    	Book c = new Book("1234569","En icke-bok");
    	
    	Genre genre1 = new Genre("Låtsasböcker");
    	
    	return (args)->{
    		gennreRepo.save(genre1);
    		
        	a.setGenre(genre1);
        	b.setGenre(genre1);
        	c.setGenre(genre1);
        	
    		bookRepo.save(a);
    		bookRepo.save(b);
    		bookRepo.save(c);
    	};
    }
}
