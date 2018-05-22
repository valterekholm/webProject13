package liber_application.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true) //makes any wrong field sent from user ignored
public class BookLoan {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id;
	@OneToOne
	User reader;
	@OneToOne
	Book book;
	Date startingDate;
	Integer allowedWeeksLength;
	
	public static final Integer STANDARD_LOAN_PERIOD_WEEKS = 3;//weeks
	
	public BookLoan() {}
	
	public BookLoan(User reader, Book book) {
		
		this.reader = reader;
		this.book = book;
		startingDate = new Date();
		allowedWeeksLength = STANDARD_LOAN_PERIOD_WEEKS;
		
	}
	
	public BookLoan(User reader, Book book, Date startingDate) {
		
		this.reader = reader;
		this.book = book;
		this.startingDate = startingDate;
		allowedWeeksLength = STANDARD_LOAN_PERIOD_WEEKS;
		
	}
	
	public BookLoan(User reader, Book book, Date startingDate, Integer weeksLength) {
		
		this.reader = reader;
		this.book = book;
		this.startingDate = startingDate;
		this.allowedWeeksLength = weeksLength;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getReader() {
		return reader;
	}

	public void setReader(User reader) {
		this.reader = reader;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	public Integer getWeeksLength() {
		return allowedWeeksLength;
	}

	public void setWeeksLength(Integer weeksLength) {
		this.allowedWeeksLength = weeksLength;
	}
	
	/**
	 * Check if a loan has gone over it's allowed time (is overdue)
	 * @return true if loan is overdue, else false
	 */
	@JsonIgnore
	public boolean isOverdue() {
		System.out.println("isOverdue");
		LocalDate today = LocalDate.now();
		LocalDate dateTemp = startingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		dateTemp.plusWeeks(this.allowedWeeksLength);//set dateTemp to day of expire
		
		if(today.isAfter(dateTemp)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "BookLoan [id=" + id + ", reader=" + reader + ", book=" + book + ", startingDate=" + startingDate
				+ ", allowedWeeksLength=" + allowedWeeksLength + "]";
	}
	
	
}