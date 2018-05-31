package liber_application.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import liber_application.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	
	List<Book> findByTitleContaining(String title);
	
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
    public List<Book> findByTitle(@Param("title") String title);

	List<Book> getByIsbn(String isbn);
}