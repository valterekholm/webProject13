package liber_application;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class RestIntegerAdder {
	
	@Autowired
	IntegerAdder adder;
	
	@RequestMapping("/adder/{i1}/{i2}")
	public IntegerValue add(@PathVariable("i1") int i, @PathVariable("i2")int j) {
		return new IntegerValue(adder.add(new IntegerPair(i,j)));
	}
	
	@RequestMapping("/all")
	public List<IntegerPair> getAll() {
		return adder.getList();
	}

}

class IntegerValue {
	int i;
	
	public IntegerValue(int i) {
		this.i = i;
	}
	public IntegerValue() {}
	public int getValue() {
		return i;
	}
	public void setValue(int i) {
		this.i = i;
	}


}
