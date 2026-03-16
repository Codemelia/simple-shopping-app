package sg.edu.nus.webdemo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.webdemo.model.Category;
import sg.edu.nus.webdemo.model.Tag;
import sg.edu.nus.webdemo.model.User;
import sg.edu.nus.webdemo.service.CategoryService;
import sg.edu.nus.webdemo.service.TagService;
import sg.edu.nus.webdemo.service.UserService;

@RequestMapping("/test")
@RestController
public class DummyController {

	@Autowired
	UserService userSvc;
	
	@Autowired
	CategoryService catSvc;
	
	@Autowired
	TagService tagSvc;
	
	@GetMapping()
	public String getTestResults(Model m) {
		
		Tag t1 = new Tag("Professional");
		Tag t2 = new Tag("On Sale");
		Tag t3 = new Tag("Carbon");
		Tag t4 = new Tag("Stainless Steel");
		tagSvc.saveTag(t1);
		tagSvc.saveTag(t2);
		tagSvc.saveTag(t3);
		tagSvc.saveTag(t4);
		
		Category c1 = new Category("Kitchen Knives", "Simple Home Knives");
		Category c2 = new Category("Chef Knives", "Simple Professional Knives");
		Category c3 = new Category("Protection Gear", "Knife Glove");
		catSvc.saveCategory(c1);
		catSvc.saveCategory(c2);
		catSvc.saveCategory(c3);
		
		User u1 = new User("ahbeng", "password", "ahbeng@nus.edu.sg", 
			"AH", "BENG", LocalDate.of(2000, 3, 29), null);
		userSvc.saveUser(u1);
		
		m.addAttribute("okmessage", "allok");
		
		return new String("test");
		
	}
	
}
