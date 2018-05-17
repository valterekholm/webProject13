package liber_application.data;

import org.springframework.data.repository.CrudRepository;

import liber_application.model.Genre;

public interface GenreRepository extends CrudRepository<Genre, Integer>{

}
