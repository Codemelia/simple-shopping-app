package sg.edu.nus.webdemo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.webdemo.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	// check if cat name exists
	public boolean existsByCategoryName(String catName);
	
	// find cat by cat name
	public Optional<Category> findByCategoryName(String catName);
	
}
