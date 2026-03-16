package sg.edu.nus.webdemo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity // declare as entity
@Table(name = "users") // create table to store class data
public class User {
	
	 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 120)
    private String userPassword;

    @Column(length = 60)
    private String firstName;

    @Column(length = 60)
    private String lastName;

    /** java.time.LocalDate → DATE column */
    private LocalDate birthDate;

    /** java.time.LocalDateTime → TIMESTAMP column */
    private LocalDateTime createdAt;

    private Boolean active;

    /** @OneToOne – bidirectional; Cart holds the FK (user_id). */
    // It is reverse because User can exist without Cart. But not the otherway.
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

    /** @OneToMany – one user → many purchase orders. */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PurchaseOrder> orders = new ArrayList<>();
	
	public Long getId() { return id; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getUserPassword() { return userPassword; }
	public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
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
	public Boolean getActive() { return active; }
	public void setActive(Boolean active) { this.active = active; }
	public Cart getCart() { return cart; }
	public void setCart(Cart cart) { this.cart = cart; }
	public List<PurchaseOrder> getOrders() { return orders; }
	public void setOrders(List<PurchaseOrder> orders) { 
		this.orders = orders;
		this.active = true; // set user to active once they place an order
	}
	
    @PrePersist
    protected void onCreate() {
        if (this.active == null) this.active = true;
    }
	
	public User() { super(); }
	
	// create/update timestamps updated on mysql
	public User(String username,  String password, String email, 
		String firstName, String lastName, LocalDate birthDate, Boolean active) {
		super();
		this.username = username;
		this.email = email;
		this.userPassword = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.active = active;
	}
	
	public String getFullName() {
        return firstName + " " + lastName;
    }

	public User(String username, String password) {
		super();
		this.username = username;
		this.userPassword = password;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", userPassword=" + userPassword + ", email=" + email
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + ", createdAt="
				+ createdAt + ", active=" + active + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return Objects.equals(id, other.id);
	}
	
}
