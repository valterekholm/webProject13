package liber_application.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import liber_application.model.Location;

public interface LocationRepository extends CrudRepository<Location, Integer>{
	
	Location getByName(String name);
	
	@Transactional
	List<Location> deleteByName(String name);

}
