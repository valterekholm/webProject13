package liber_application.data;

import org.springframework.data.repository.CrudRepository;

import liber_application.model.Location;

public interface LocationRepository extends CrudRepository<Location, Integer>{

}
