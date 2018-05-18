package liber_application.data;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import liber_application.model.Genre;

public interface GenreRepository extends CrudRepository<Genre, Integer>{
	
	//public long countByName(String name);
	Genre getByName(String name);
	
	@Transactional
	List<Genre> deleteByName(String name);
	
//	@Modifying
//	@Query("delete from data where createdAt < ?1")
//	int retainDataBefore(Date retainDate); //commentet 18/5 -18 due to errors

}
