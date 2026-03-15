package sg.edu.nus.webdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.webdemo.model.User;
import sg.edu.nus.webdemo.repo.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	// save user and return boolean on save success/fail
	public User saveUser(User user) {
	    if (user == null) return new User();
	    // only block duplicates for new users
	    if (user.getId() == null && userExists(user.getUsername()))
	        return new User();
	    return userRepo.save(user);
	}
	
	// find user by username
	public Optional<User> findUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
	// find all users
	public List<User> findAllUsers() {
		return userRepo.findAll();
	}
	
	// check if username exists in db
	public boolean userExists(String username) {
		return userRepo.existsByUsername(username);
	}
	
	// delete user by username
	public void deleteUserByUsername(String username) {
		userRepo.deleteByUsername(username);
	}
	
}
