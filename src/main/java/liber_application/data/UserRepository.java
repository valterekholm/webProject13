package liber_application.data;

import org.springframework.data.repository.CrudRepository;

import liber_application.model.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {
	
	
	User getByEmail(String email);
}
