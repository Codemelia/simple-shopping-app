package sg.edu.nus.webdemo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // declare as entity
@Table(name = "users") // create table to store class data
public class User {
	
	@Id // declare as id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // DB generates data as identity
	private Long id;
	
	@Column(length = 15) // declare as column with length limit = 12
	private String username;
	
	@Column(length = 15)
	private String password;
	
	@Column(length = 254)
	private String email;
	
	@Column(length = 25)
	private String firstName;
	@Column(length = 25)
	private String lastName;
	
	@DateTimeFormat
	private LocalDate birthDate;
	
	@CreatedDate
	@DateTimeFormat // declare object as date time
	private LocalDateTime createdAt;
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public LocalDateTime getCreatedAt() { return createdAt; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public LocalDate getBirthDate() { return birthDate; }
	public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
	
	public User() { super(); }
	
	public User(Long id, String username, String password, LocalDateTime createdAt) {
		super();
		this.username = username;
		this.password = password;
		this.createdAt = LocalDateTime.now();
	}
	
	public User(String username, String password, String email,
		String firstName, String lastName, LocalDate birthDate) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.createdAt = LocalDateTime.now();
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + ", createdAt="
				+ createdAt + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(birthDate, createdAt, email, firstName, id, lastName, password, username);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}

}
