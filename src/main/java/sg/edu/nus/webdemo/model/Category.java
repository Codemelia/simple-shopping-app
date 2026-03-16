package sg.edu.nus.webdemo.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 80)
	private String categoryName;
	
	@Column(length = 255)
	private String categoryDescription;
	
	// deleting category should not delete products, so no cascade
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<Product> products;
	
	public Long getId() { return id; }
	public String getCategoryName() { return categoryName; }
	public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
	public String getCategoryDescription() { return categoryDescription; }
	public void setCategoryDescription(String categoryDescription) { this.categoryDescription = categoryDescription; }
	public List<Product> getProducts() { return products; }
	public void setProducts(List<Product> products) { this.products = products; }
	
	public Category() { super(); }
	public Category(String categoryName, String categoryDescription) {
		super();
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
	}
	
	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + "]";
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
		Category other = (Category) obj;
		return Objects.equals(id, other.id);
	}

}
