package liber_application.data;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import liber_application.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer>{
	
	//public long countByName(String name);
	Genre getByName(String name);
	
	@Transactional
	List<Genre> deleteByName(String name);
	
//	@Modifying
//	@Query("delete from data where createdAt < ?1")
//	int retainDataBefore(Date retainDate); //commentet 18/5 -18 due to errors

}
