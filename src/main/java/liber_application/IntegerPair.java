package liber_application;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IntegerPair {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private int first;
	private int second;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public IntegerPair(int first, int second) {
		super();
		this.first = first;
		this.second = second;
	}
	public IntegerPair() {	
	}
	
}
