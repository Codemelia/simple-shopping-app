package sg.edu.nus.webdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sg.edu.nus.webdemo.model.User;
import sg.edu.nus.webdemo.repo.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	// save user and return boolean on save success/fail
	public User saveUser(User user) {
		// handle invalid user object
	    if (user == null) return new User();
	    
	    // for new users, encode password
	    else if (user.getId() == null) {
	    	user.setUserPassword(encoder.encode(user.getUserPassword()));
	    	return userRepo.save(user);
	    }
	    
	    // users are existing from here, retrieve user object
	    User existUser = userRepo.findByUsername(user.getUsername()).orElse(null);    	
    	if (existUser == null) return new User();
	    
	    // if input password is null or blank
	    // retrieve existing user password and set it
    	// else retrieve new input password and set it
    	else if (user.getUserPassword() == null || user.getUserPassword().isBlank())
	    	user.setUserPassword(existUser.getUserPassword());
    	else user.setUserPassword(encoder.encode(user.getUserPassword()));
    	
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
