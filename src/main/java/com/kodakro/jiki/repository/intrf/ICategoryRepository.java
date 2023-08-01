package com.kodakro.jiki.repository.intrf;

import java.util.List;

import com.kodakro.jiki.model.Category;

public interface ICategoryRepository {

	List<Category> findByProject(Long projectId);
	Category create(Category category);
}
