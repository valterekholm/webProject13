package liber_application.data;

import org.springframework.data.repository.CrudRepository;

import liber_application.model.BookLoan;

public interface BookLoanRepository extends CrudRepository<BookLoan, Integer>{
}