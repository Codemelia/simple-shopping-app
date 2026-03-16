package sg.edu.nus.webdemo.model;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Integer quantity;
	
	@DateTimeFormat
	private LocalDateTime addedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id", nullable = false)
	private Cart cart;
	
	@ManyToOne(fetch = FetchType.EAGER) // EAGER so that thymeleaf can read name/price
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	public Long getId() { return id; }
	public Integer getQuantity() { return quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }
	public LocalDateTime getAddedAt() { return addedAt; }
	public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }
	public Cart getCart() { return cart; }
	public void setCart(Cart cart) { this.cart = cart; }
	public Product getProduct() { return product; }
	public void setProduct(Product product) { this.product = product; }

	public CartItem() {}
	public CartItem(Cart cart, Product product, Integer quantity) {
		this.cart = cart;
		this.product = product;
		this.quantity = quantity;
	}

	public Double getSubtotal() {
		if (product == null || quantity == null)
			return 0.0;
		return product.getEffectivePrice() * quantity;
	}
	
	@Override
	public String toString() {
		return "CartItem [id=" + id + ", quantity=" + quantity + ", addedAt=" + addedAt + "]";
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
		CartItem other = (CartItem) obj;
		return Objects.equals(id, other.id);
	}
	
}
