package sg.edu.nus.webdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.webdemo.model.Tag;
import sg.edu.nus.webdemo.repo.TagRepository;

@Service
public class TagService {
	
	@Autowired
	TagRepository tagRepo;

	// save tag
	public Tag saveTag(Tag tag) {
	    if (tag == null) return new Tag();
	    // only block duplicates for new tags
	    if (tag.getId() == null && tagExists(tag.getTagName()))
	        return new Tag();
	    return tagRepo.save(tag);
	}
	
	// find tag by name
	public Optional<Tag> findTagByName(String tagName) {
		return tagRepo.findByTagName(tagName);
	}
	
	// find all tags
	public List<Tag> findAllTags() {
		return tagRepo.findAll();
	}
	
	// delete tag
	public void deleteTagByName(String tagName) {
		tagRepo.deleteByTagName(tagName);
	}
	
	// tag exist check
	public boolean tagExists(String tagName) {
		return tagRepo.existsByTagName(tagName);
	}

}
