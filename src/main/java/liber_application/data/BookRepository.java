package liber_application.data;

import org.springframework.data.repository.CrudRepository;

import liber_application.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

}
