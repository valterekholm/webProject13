package liber_application.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import liber_application.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer>{
	
	Location getByName(String name);
	
	@Transactional
	List<Location> deleteByName(String name);

}
