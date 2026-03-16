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

import sg.edu.nus.webdemo.model.Tag;
import sg.edu.nus.webdemo.service.TagService;

@DataJpaTest
@Import(TagService.class) // import tag service class	
//@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql({"/data.sql"})
public class TagCRUDTest {
	
	@Autowired
	private TagService tagSvc;
	
	@Autowired
	private TestEntityManager em;
	
	// create and save tag
	@Test
	@DisplayName("01 Create Tag")
	public void createTag() {
		Tag savedTag = tagSvc.saveTag(createTestTag1());
		assertThat(savedTag.getId())
			.isNotNull()
			.isPositive();
	}
	
	// find tag by name
	@Test
	@DisplayName("02 Find Tag By Name")
	public void findTagByName() {
		Tag savedTag = em.persistAndFlush(createTestTag1());
		Optional<Tag> optTag = tagSvc.findTagByName(savedTag.getTagName());
		assertThat(optTag.isPresent());
	}
	
	// find all tags
	@Test
	@DisplayName("03 Find All Tags")
	public void findAllTags() {
		em.persistAndFlush(createTestTag1());
		em.persistAndFlush(createTestTag2());
		List<Tag> tags = tagSvc.findAllTags();
		assertThat(tags)
			.isNotEmpty()
			.hasSize(2);
	}
	
	// update tag
	@Test
	@DisplayName("04 Update Tag")
	public void updateTag() {
		Tag savedTag = em.persistAndFlush(createTestTag1());
		savedTag.setTagName("Soon-To-Expire");
		Tag updatedTag = tagSvc.saveTag(savedTag);
		assertThat(updatedTag.getTagName())
			.isNotNull()
			.isEqualTo(savedTag.getTagName());
	}
	
	// delete tag
	@Test
	@DisplayName("05 Delete Tag")
	public void deleteTag() {
		Tag savedTag = em.persistAndFlush(createTestTag1());
		tagSvc.deleteTagByName(savedTag.getTagName());
		Tag retrievedTag = em.find(Tag.class, savedTag.getId());
		assertThat(retrievedTag).isNull(); // should be null
	}
	
	// helper
	public Tag createTestTag1() {
		return new Tag("On Sale");
	}
	
	public Tag createTestTag2() {
		return new Tag("New Products");
	}

}
