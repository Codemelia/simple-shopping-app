package sg.edu.nus.webdemo.crud;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;

import sg.edu.nus.webdemo.model.Category;
import sg.edu.nus.webdemo.service.CategoryService;

@DataJpaTest
@Import(CategoryService.class)
public class CategoryCRUDTest {

	@Autowired
	private CategoryService catSvc;
	
	@Autowired
	private TestEntityManager em;
	
	// create and save
	@Test
	@DisplayName("01 Create Category")
	public void createCategory() {
		Category savedCat = catSvc.saveCategory(createTestCat1());
		assertThat(savedCat.getId())
			.isNotNull()
			.isPositive();
	}
	
	// find cat by name
	@Test
	@DisplayName("02 Find Category By Name")
	public void findCategoryByName() {
		Category savedCat = em.persistAndFlush(createTestCat1());
		Optional<Category> optCat = catSvc.findCategoryByName(savedCat.getName());
		assertThat(optCat.isPresent());
	}
	
	// find all cats
	@Test
	@DisplayName("03 Find All Categories")
	public void findAllCategories() {
		em.persistAndFlush(createTestCat1());
		em.persistAndFlush(createTestCat2());
		List<Category> cats = catSvc.findAllCategories();
		assertThat(cats)
			.isNotEmpty()
			.hasSize(2);
	}
	
	// update cat
	@Test
	@DisplayName("04 Update Category")
	public void updateCategory() {
		Category savedCat = em.persistAndFlush(createTestCat1());
		savedCat.setName("Toilet Accessories");
		Category updatedCat = catSvc.saveCategory(savedCat);
		assertThat(updatedCat.getName())
			.isNotNull()
			.isEqualTo(savedCat.getName());
	}
	
	// delete cat
	@Test
	@DisplayName("05 Delete Category")
	public void deleteCategory() {
		Category savedCat = em.persistAndFlush(createTestCat1());
		catSvc.deleteCategoryByName(savedCat.getName());
		Category retrievedCat = em.find(Category.class, savedCat.getId());
		assertThat(retrievedCat).isNull();
	}
	
	// helper
	public Category createTestCat1() {
		return new Category("Kitchen Appliances", 
			"Appliances to be used in the kitchen");
	}
	
	public Category createTestCat2() {
		return new Category("Bedroom Furniture", 
			"Furniture to be placed in the bedroom");
	}
	
}
