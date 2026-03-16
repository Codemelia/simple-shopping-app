package sg.edu.nus.webdemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carts")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreatedDate
	@DateTimeFormat
	private LocalDateTime createdAt;
	
//	@UpdateTimestamp
//	@DateTimeFormat
//	private LocalDateTime updatedAt;
	
	@OneToOne(fetch = FetchType.LAZY) // one cart can only accom one user
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // one cart can have many cart items
	private List<CartItem> items = new ArrayList<CartItem>();
	
	public Long getId() { return id; }
	public LocalDateTime getCreatedAt() { return createdAt; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }
	public List<CartItem> getItems() { return items; }
	public void setItems(List<CartItem> items) { this.items = items; }

	public Cart() {}
	public Cart(User user) {
		this.user = user;
	}
	
	public Double getTotal() {
		return items.stream().mapToDouble(CartItem::getSubtotal).sum();
	}

	public int getTotalItems() {
		return items.stream().mapToInt(CartItem::getQuantity).sum();
	}
	
	@Override
	public String toString() {
		return "Cart [id=" + id + ", createdAt=" + createdAt + "]";
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
		Cart other = (Cart) obj;
		return Objects.equals(id, other.id);
	}
	
}
