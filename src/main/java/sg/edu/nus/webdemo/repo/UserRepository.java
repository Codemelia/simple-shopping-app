package sg.edu.nus.webdemo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.webdemo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	// find and return optional of User by username
	public Optional<User> findByUsername(String username);
	
	// check if username exists in db
	public boolean existsByUsername(String username);
	
	// delete user by username
	public void deleteByUsername(String username);
}
