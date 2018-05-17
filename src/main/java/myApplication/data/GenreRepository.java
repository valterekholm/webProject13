package myApplication.data;

import org.springframework.data.repository.CrudRepository;

import myApplication.model.Genre;

public interface GenreRepository extends CrudRepository<Genre, Integer>{

}
