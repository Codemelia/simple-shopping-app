package sg.edu.nus.webdemo.model;

import java.util.Objects;

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
@Table(name = "order_items")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Integer quantity;
	
	@Column(nullable = false)
    private Double unitPrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private PurchaseOrder order;

	public Long getId() { return id; }
	public Integer getQuantity() { return quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }
	public Double getUnitPrice() { return unitPrice; }
	public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
	public Product getProduct() { return product; }
	public void setProduct(Product product) { this.product = product; }
	public PurchaseOrder getOrder() { return order; }
	public void setOrder(PurchaseOrder order) { this.order = order; }
	
	public OrderItem() { super(); }
	public OrderItem(PurchaseOrder order, Product product, Integer quantity, Double unitPrice) {
        this.order     = order;
        this.product   = product;
        this.quantity  = quantity;
        this.unitPrice = unitPrice;
    }
	
	public Double getSubtotal() {
        return unitPrice * quantity;
    }
	
	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", quantity=" + quantity + "]";
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
		OrderItem other = (OrderItem) obj;
		return Objects.equals(id, other.id);
	}
	
}
