package sg.edu.nus.webdemo.crud;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;

import sg.edu.nus.webdemo.config.SecurityConfig;
import sg.edu.nus.webdemo.model.User;
import sg.edu.nus.webdemo.service.UserService;

@DataJpaTest // declare as a jpa test
@Import({UserService.class, SecurityConfig.class}) // imports classes for injection for testing
//@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql({"/data.sql"})
public class UserCRUDTest {
	
	@Autowired
	private UserService userSvc;
	
	@Autowired
	private TestEntityManager em; // provides helper functions for persist/flush/find
	
	// test save user function
	@Test // declare method as test method
	@DisplayName("01 Create User") // declare test name for display on reports/logs etc
	public void saveUser() {
		// create test user and save
		User savedUser = userSvc.saveUser(createTestUser1());
		// persist() makes entity and schedules DB insertion
		// flush() forces JPA to execute SQL immediately
//		em.persistAndFlush(savedUser); // now needed
		// assertThat() writes assertion
		// assertThat() returns an Assertion object which holds the value
		// subsequent isNotNull() and isPositive() check if value meets conditions
		assertThat(savedUser.getId())
			.isNotNull()
			.isPositive();
	}
	
	// test find user by username function
	@Test
	@DisplayName("02 Find User By Username")
	public void findUserByUsername() {
		User savedUser = em.persistAndFlush(createTestUser1()); // save using test entity manager
		Optional<User> optUser = userSvc.findUserByUsername(savedUser.getUsername());
		assertThat(optUser.isPresent()); // check that user is present in optional
	}
	
	// test find users function
	@Test
	@DisplayName("03 Find All Users")
	public void findUsers() {
		 em.persistAndFlush(createTestUser1()); // save using test entity manager
		 em.persistAndFlush(createTestUser2());
		List<User> users = userSvc.findAllUsers();
		assertThat(users)
			.isNotEmpty() // checks that list is not empty
			.hasSize(2); // check that list size is 2
	}
	
	// test update user function
	@Test
	@DisplayName("04 Update Existing User")
	public void updateUser() {
		User savedUser = em.persistAndFlush(createTestUser1()); // save using test entity manager
		savedUser.setUsername("lia123"); // update username
		User updatedUser = userSvc.saveUser(savedUser);
		assertThat(updatedUser.getBirthDate())
			.isNotNull()
			.isEqualTo(savedUser.getBirthDate());
	}
	
	// test user delete function
	@Test
	@DisplayName("05 Delete Existing User")
	public void deleteUser() {
		User savedUser = em.persist(createTestUser1()); // save using test entity manager
		userSvc.deleteUserByUsername(savedUser.getUsername());
		User retrievedUser = em.find(User.class, 1);
		assertThat(retrievedUser)
			.isNull(); // should be null
	}

	// data for testing
	private User createTestUser1() {
		return new User("ahbeng1", "password", "ahbeng1@nus.edu.sg", "AH1", "BENG1", null, null);
	}
	
	private User createTestUser2() {
		return new User("ahbeng2", "password", "ahbeng2@nus.edu.sg", "AH2", "BENG2", null, null);
	}

}
