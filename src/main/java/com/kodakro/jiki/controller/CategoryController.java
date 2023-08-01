package com.kodakro.jiki.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.jiki.exception.CustomResponseType;
import com.kodakro.jiki.model.Category;
import com.kodakro.jiki.service.CategoryService;

@RestController
@RequestMapping(path = "/api/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/project/{projectId}")
	List<Category> findByProject(@PathVariable("projectId") Long projectId){
		return categoryService.findByProject(projectId);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody Category category) {
		final Category dbCategory = categoryService.create(category);
		if (dbCategory == null) {
			return new ResponseEntity<CustomResponseType<Category>>(new CustomResponseType<Category>("KO", null, "Category  "+ category.getTitle() + " already exists !"), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<CustomResponseType<Category>>(new CustomResponseType<Category>("OK", dbCategory, "Team created"), HttpStatus.OK);
	}
}
