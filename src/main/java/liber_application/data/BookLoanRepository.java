package liber_application.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import liber_application.model.BookLoan;

public interface BookLoanRepository extends JpaRepository<BookLoan, Integer>{
}