package sg.edu.nus.webdemo.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NonNull;

@Entity
@Table(name = "categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	@Column(length = 50)
	private String name;
	
	@NonNull
	@Column(length = 100)
	private String description;

	@ManyToOne // many categories can share the same tag
	private Tag tag;
	
	public Long getId() { return id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public Tag getTag() { return tag; }
	public void setTag(Tag tag) { this.tag = tag; }
	
	public Category() { super(); }
	public Category(@NonNull String name, @NonNull String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(description, name, tag);
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
		return Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& Objects.equals(tag, other.tag);
	}
	
//	public Long getId() { return id; }
//	public void setId(Long id) { this.id = id; }
//	public String getName() { return name; }
//	public void setName(String name) { this.name = name; }
//	public String getDescription() { return description; }
//	public void setDescription(String description) { this.description = description; }
//	public Tag getTag() { return tag; }
//	public void setTag(Tag tag) { this.tag = tag; }
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(description, id, name, tag);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Category other = (Category) obj;
//		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
//				&& Objects.equals(name, other.name) && Objects.equals(tag, other.tag);
//	}
//	
//	public Category() { super(); }
//
//	public Category(String name, String description, Tag tag) {
//		super();
//		this.name = name;
//		this.description = description;
//		this.tag = tag;
//	}
	
}
