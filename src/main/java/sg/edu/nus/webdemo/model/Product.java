package sg.edu.nus.webdemo.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 120)
	private String productName;
	
	@Column(length = 1000)
	private String productDescription;
	
	@Column(length = 80)
	private String brand;
	
	@Column(length = 255)
	private String imageUrl;
	
	@Column(nullable = false)
	private Double price;
	
	private Double discountPercent = 0.0;
	
	@Column(nullable = false)
	private Integer stockQuantity;
	
	private LocalDateTime createdAt;
	
	private Boolean active;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "product_tags", 
		joinColumns = @JoinColumn(name = "product_id"), 
		inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<>();

	public Long getId() { return id; }
	public String getProductName() { return productName; }
	public void setProductName(String productName) { this.productName = productName; }
	public String getProductDescription() { return productDescription; }
	public void setProductDescription(String productDescription) { this.productDescription = productDescription; }
	public String getBrand() { return brand; }
	public void setBrand(String brand) { this.brand = brand; }
	public String getImageUrl() { return imageUrl; }
	public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
	public Double getPrice() { return price; }
	public void setPrice(Double price) { this.price = price; }
	public Double getDiscountPercent() { return discountPercent; }
	public void setDiscountPercent(Double discountPercent) { this.discountPercent = discountPercent; }
	public Integer getStockQuantity() { return stockQuantity; }
	public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
	public LocalDateTime getCreatedAt() { return createdAt; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
	public Boolean getActive() { return active; }
	public void setActive(Boolean active) { this.active = active; }
	public Category getCategory() { return category; }
	public void setCategory(Category category) { this.category = category; }
	public Set<Tag> getTags() { return tags; }
	public void setTags(Set<Tag> tags) { this.tags = tags; }
	
	@PrePersist
	protected void onCreate() {
		if (this.active == null)
			this.active = true;
		if (this.discountPercent == null)
			this.discountPercent = 0.0;
		if (this.stockQuantity == null)
			this.stockQuantity = 0;
	}
	
	public Product() { super(); }
	public Product(String productName, String productDescription, String brand, String imageUrl, Double price,
			Double discountPercent, Integer stockQuantity, Boolean active, Category category) {
		super();
		this.productName = productName;
		this.productDescription = productDescription;
		this.brand = brand;
		this.imageUrl = imageUrl;
		this.price = price;
		this.discountPercent = discountPercent;
		this.stockQuantity = stockQuantity;
		this.active = active;
		this.category = category;
	}
	
	/** Effective price after applying discount. */
	public Double getEffectivePrice() {
		if (discountPercent == null || discountPercent == 0.0)
			return price;
		return price * (1.0 - discountPercent / 100.0);
	}

	public boolean isInStock() {
		return stockQuantity != null && stockQuantity > 0;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", productDescription=" + productDescription
				+ ", brand=" + brand + ", imageUrl=" + imageUrl + ", price=" + price + ", discountPercent="
				+ discountPercent + ", stockQuantity=" + stockQuantity + ", createdAt=" + createdAt + ", active="
				+ active + "]";
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
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}
	
}
