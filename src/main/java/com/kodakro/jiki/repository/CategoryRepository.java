package com.kodakro.jiki.repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kodakro.jiki.model.Category;
import com.kodakro.jiki.repository.intrf.ICategoryRepository;
import com.kodakro.jiki.repository.intrf.IGenericRepository;
import com.kodakro.jiki.repository.mapper.CategoryRowMapper;
import com.kodakro.jiki.repository.request.AbstractCategoryRequest;

@Repository
public class CategoryRepository extends AbstractCategoryRequest implements IGenericRepository<Category>, ICategoryRepository{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Value("${sql.category.insert}")
	private String sqlInsert;
	
	@Override
	public List<Category> findByProject(Long projectId) {
		final String whereSql= " WHERE PROJECT_ID =? ";
		Object[] param = {projectId};
		int[] types = {Types.INTEGER};
		List<Category> categorys = jdbcTemplate.query(getNoJointSelect(whereSql), param, types,
				new CategoryRowMapper());
		
		return categorys;
	}

	@Override
	public Category create(Category category) {
		category.setId(maxId()+1);

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sqlInsert);
			ps.setLong(1, category.getId());
			ps.setLong(2, category.getProject().getId());
			ps.setString(3, category.getTitle());
			
			return ps;
		});
		
		return category;
	}

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long maxId() {
		final Long maxId = jdbcTemplate.queryForObject(getMaxId(), null, null, Long.class );
		return maxId!=null?maxId:1;
	}

	@Override
	public Optional<Category> exists(Long id) {
		final String whereSql= " WHERE ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Category category = null;
		try {
			category = jdbcTemplate.queryForObject(getNoJointSelect(whereSql), param, types,
					new CategoryRowMapper());
		}catch (EmptyResultDataAccessException e) {
			// log no user found
		}
		return Optional.ofNullable(category);
	}

	@Override
	public Optional<Category> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean deleteById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Category t) {
		// TODO Auto-generated method stub
		return false;
	}

}
