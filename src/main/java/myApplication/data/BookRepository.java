package myApplication.data;

import org.springframework.data.repository.CrudRepository;

import myApplication.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

}
