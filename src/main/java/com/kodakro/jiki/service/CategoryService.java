package com.kodakro.jiki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodakro.jiki.model.Category;
import com.kodakro.jiki.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public List<Category> findByProject(Long projectId){
		return categoryRepository.findByProject(projectId);
	}
	
	public Category create(Category category) {
		Optional<Category> dbCategory = categoryRepository.exists(category.getId());
		if (dbCategory.isPresent()) {
			return null;
		}
		return categoryRepository.create(category);
	}
}
