package sg.edu.nus.webdemo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.webdemo.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

	// find tag by name
	public Optional<Tag> findByName(String tagName);
	
	// tag exist check
	public boolean existsByName(String tagName);
	
	// delete tag by name
	public void deleteByName(String tagName);
	
}
