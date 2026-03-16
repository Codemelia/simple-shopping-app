package sg.edu.nus.webdemo.crud;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import lombok.Data;
import sg.edu.nus.webdemo.model.Category;
import sg.edu.nus.webdemo.service.CategoryService;

@DataJpaTest
@Import(CategoryService.class)
@Data
@Sql("/data.sql")
public class CategoryCRUDTest {

	@Autowired
	private CategoryService catSvc;
	
//	@Autowired
//	private TestEntityManager em;
	
	// create and save
	@Test
	@DisplayName("01 Create Category")
	public void createCategory() {
		Category newCategory = new Category("Pocket Knives", "Compact everyday-carry blades");
        Category saved = catSvc.saveCategory(newCategory);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCategoryName()).isEqualTo("Pocket Knives");
        assertThat(catSvc.findAllCategories()).hasSize(6);
	}
	
	// find cat by name
	@Test
	@DisplayName("02 Find Category By Name")
	public void findCategoryByName() {
//		Category savedCat = em.persistAndFlush(createTestCat1());
		Optional<Category> optCat = catSvc.findCategoryByName("Chef Knives");
		assertThat(optCat.isPresent());
		assertThat(optCat.get().getCategoryDescription())
			.isEqualTo("High-precision kitchen blades for the professional cook");
	}
	
	// find all cats
	@Test
	@DisplayName("03 Find All Categories")
	public void findAllCategories() {
//		em.persistAndFlush(createTestCat1());
//		em.persistAndFlush(createTestCat2());
		List<Category> cats = catSvc.findAllCategories();
		assertThat(cats)
			.isNotEmpty()
			.hasSize(5);
	}
	
	// find non existent cat
	@Test
	@DisplayName("04 Find Non-Existentcategory by Id")
	public void findNonExistentCategoryById() {
		 Optional<Category> found = catSvc.findCategoryById(999L);
        assertThat(found).isNotPresent();
	}
	
	// update cat
	@Test
	@DisplayName("05 Update Category")
	public void updateCategory() {
		Category category = catSvc.findCategoryById(1L).orElseThrow();
        category.setCategoryDescription("Updated description for Chef Knives");
        catSvc.saveCategory(category);

        Category updated = catSvc.findCategoryById(1L).orElseThrow();
        assertThat(updated.getCategoryDescription()).isEqualTo(category.getCategoryDescription());
	}
	
	// delete unused cat 
	@Test
	@DisplayName("06 Delete Unused Category")
	public void deleteCategory() {
		 catSvc.deleteCategoryById(5L);   // Protective Gear
		 assertThat(catSvc.findAllCategories()).hasSize(4);
		 assertThat(catSvc.findCategoryById(5L)).isNotPresent();
	}
	
//	// helper
//	public Category createTestCat1() {
//		return new Category("Kitchen Appliances", 
//			"Appliances to be used in the kitchen");
//	}
//	
//	public Category createTestCat2() {
//		return new Category("Bedroom Furniture", 
//			"Furniture to be placed in the bedroom");
//	}
	
}
