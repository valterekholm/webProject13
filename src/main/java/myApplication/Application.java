package myApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import myApplication.data.BookRepository;
import myApplication.model.Book;

@SpringBootApplication
public class Application {
	
	@Autowired
	BookRepository bookRepo;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public CommandLineRunner init() {
    	
    	Book a = new Book("1234567","En låtsasbok");
    	Book b = new Book("1234568","En fejkbok");
    	Book c = new Book("1234569","En icke-bok");
    	
    	return (args)->{
    		bookRepo.save(a);
    		bookRepo.save(b);
    		bookRepo.save(c);
    	};
    }
}
