package sg.edu.nus.webdemo.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
        
@Entity
@Table(name = "tags")
public class Tag {

	 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String tagName;

    /** @ManyToMany – inverse (mappedBy); Product is the owner side. */
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();
	
	public Long getId() { return id; }
	public String getTagName() { return tagName; }
	public void setTagName(String tagName) { this.tagName = tagName; }
	
	public Tag() { super(); }
	public Tag(String tagName) {
		super();
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", tagName=" + tagName + "]";
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
		Tag other = (Tag) obj;
		return Objects.equals(id, other.id);
	}

}
