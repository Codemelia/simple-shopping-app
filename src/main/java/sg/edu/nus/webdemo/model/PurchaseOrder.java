package sg.edu.nus.webdemo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Double – grand total including all items at checkout prices */
	@Column(name = "total_amount", nullable = false)
	private Double totalAmount;

	/** String – full delivery address captured at checkout */
	@Column(name = "shipping_address", length = 500)
	private String shippingAddress;

	@Column(length = 300)
	private String notes;

	/** Enum stored as VARCHAR; never as ordinal to survive reordering. */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private OrderStatus orderStatus;

	/** LocalDateTime – when the order was placed */
	@Column(nullable = false)
	private LocalDateTime orderDate;

	/** LocalDateTime – when the order was delivered (nullable until delivered) */
	private LocalDateTime deliveredAt;

	/** @ManyToOne – many orders belong to one user; FK user_id. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	/** @OneToMany – one order has many line-items. */
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderItem> items = new ArrayList<>();
	
	public Long getId() { return id; }
	public Double getTotalAmount() { return totalAmount; }
	public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
	public String getShippingAddress() { return shippingAddress; }
	public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
	public String getNotes() { return notes; }
	public void setNotes(String notes) { this.notes = notes; }
	public OrderStatus getOrderStatus() { return orderStatus; }
	public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }
	public LocalDateTime getOrderDate() { return orderDate; }
	public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
	public LocalDateTime getDeliveredAt() { return deliveredAt; }
	public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }
	public List<OrderItem> getItems() { return items; }
	public void setItems(List<OrderItem> items) { this.items = items; }
	
	@PrePersist
	protected void onCreate() {
		if (this.orderStatus == null)
			this.orderStatus = OrderStatus.PENDING;
	}
	
	public PurchaseOrder() { super(); }
	public PurchaseOrder(Double totalAmount, String shippingAddress, String notes, OrderStatus orderStatus,
			LocalDateTime orderDate, LocalDateTime deliveredAt) {
		super();
		this.totalAmount = totalAmount;
		this.shippingAddress = shippingAddress;
		this.notes = notes;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.deliveredAt = deliveredAt;
	}
	
	public int getTotalQuantity() {
		return items.stream().mapToInt(OrderItem::getQuantity).sum();
	}
	
	@Override
	public String toString() {
		return "PurchaseOrder [id=" + id + ", totalAmount=" + totalAmount + ", shippingAddress=" + shippingAddress
				+ ", notes=" + notes + ", orderStatus=" + orderStatus + ", orderDate=" + orderDate + ", deliveredAt="
				+ deliveredAt + "]";
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
		PurchaseOrder other = (PurchaseOrder) obj;
		return Objects.equals(id, other.id);
	}
	
}
