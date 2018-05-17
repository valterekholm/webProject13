package myApplication.data;

import org.springframework.data.repository.CrudRepository;

import myApplication.model.Location;

public interface LocationRepository extends CrudRepository<Location, Integer>{

}
