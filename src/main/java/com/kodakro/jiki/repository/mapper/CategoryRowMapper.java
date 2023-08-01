package com.kodakro.jiki.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.model.Category;

public class CategoryRowMapper extends AbstractRowMapper implements  RowMapper<Category>{
	
	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		Category category = new Category();
		category.setId(rs.getLong("ID"));
		category.setTitle(rs.getString("TITLE"));
		Project project = new Project();
		project.setId(rs.getLong("PROJECT_ID"));
		category.setProject(project);
		
		return category;
	}
}
