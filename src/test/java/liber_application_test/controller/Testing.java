package liber_application_test.controller;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import liber_application.controller.BookLoanController;
import liber_application.data.BookLoanRepository;
import liber_application.model.BookLoan;

public class Testing {
	
//	@Autowired
	BookLoanRepository loanRepo;
//	@Autowired
	BookLoanController blController;
	
	public void setUp() {
		
	}

	@Test
	public void testOldDateIsOverdue() {
		
		
		List<BookLoan> allLoans = (List<BookLoan>) loanRepo.findAll();
		
		if(allLoans.size()>0) {
			BookLoan bl1 = allLoans.get(0);
			bl1.setStartingDate(new Date(2010,1,1));
			assertTrue(bl1.isOverdue());
		}
		
		
		blController.isLoanOverdue(2);
		
		fail("Not yet implemented");
	}

}
