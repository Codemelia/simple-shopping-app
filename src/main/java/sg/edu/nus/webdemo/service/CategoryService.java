package sg.edu.nus.webdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.webdemo.model.Category;
import sg.edu.nus.webdemo.repo.CategoryRepository;
import sg.edu.nus.webdemo.repo.ProductRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository catRepo;
	
	@Autowired
	ProductRepository prodRepo;

	// save category
	public Category saveCategory(Category cat) {
		if (cat == null) return new Category();
		// only block saves for new duplicate category names
		if (cat.getId() == null && categoryExists(cat.getCategoryName()))
			return new Category();
		return catRepo.save(cat);
	}
	
	// check if cat exists by name
	public boolean categoryExists(String catName) {
		return catRepo.existsByCategoryName(catName);
	}
	
	// find cat by name
	public Optional<Category> findCategoryByName(String catName) {
		return catRepo.findByCategoryName(catName);
	}
	
	// find cat by id
	public Optional<Category> findCategoryById(Long id) {
		return catRepo.findById(id);
	}
	
	// find all cats
	public List<Category> findAllCategories() {
		return catRepo.findAll();
	}
	
	// delete cat by id
	public void deleteCategoryById(Long id) {
		if(prodRepo.existsByCategoryId(id)) // check if there are existing products under the category
		    throw new IllegalStateException("Cannot delete category with existing products");
		else catRepo.deleteById(id);
	}
	
}
