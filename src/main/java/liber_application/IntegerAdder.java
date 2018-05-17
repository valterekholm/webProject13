package liber_application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IntegerAdder {
	
	@Autowired
	IntegerPairRepository rep;
	
	public int add(IntegerPair ip) {
		int sum = ip.getFirst() + ip.getSecond();
		rep.save(ip);
		return sum;
	}
	
	public List<IntegerPair> getList(){
		return rep.findAll();
	}
	
}
