package sg.edu.nus.webdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.webdemo.model.Category;
import sg.edu.nus.webdemo.repo.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository catRepo;

	// save category
	public Category saveCategory(Category cat) {
		if (cat == null) return new Category();
		// only block saves for new duplicate category names
		if (cat.getId() == null && categoryExists(cat.getName()))
			return new Category();
		return catRepo.save(cat);
	}
	
	// check if cat exists by name
	public boolean categoryExists(String catName) {
		return catRepo.existsByName(catName);
	}
	
	// find cat by name
	public Optional<Category> findCategoryByName(String catName) {
		return catRepo.findByName(catName);
	}
	
	// find all cats
	public List<Category> findAllCategories() {
		return catRepo.findAll();
	}
	
	// delete cat
	public void deleteCategoryByName(String catName) {
		catRepo.deleteByName(catName);
	}
	
}
