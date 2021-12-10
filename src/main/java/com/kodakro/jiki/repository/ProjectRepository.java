package com.kodakro.jiki.repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.repository.mapper.ProjectRowMapper;
import com.kodakro.jiki.repository.request.AbstractProjectRequest;

@Repository
public class ProjectRepository extends AbstractProjectRequest implements IGenericRepository<Project> {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Project> findAll() {
		return jdbcTemplate.query(getJoinSelect(null), new ProjectRowMapper());
	}

	@Override
	public Optional<Project> findById(Long id) {
		final String whereSql= " AND PR.ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Project project = null;
		try {		
			project = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types,
					new ProjectRowMapper());
		}catch(EmptyResultDataAccessException e) {
			// Log no project found
		}
		return Optional.ofNullable(project);
	}

	@Override
	public void deleteById(Long id) {
		final String sql = "DELETE * FROM "+TABLE+" WHERE ST.ID=?";
		Optional<Project> project = findById(id);
		Object[] param = {id};
		int[] types = {Types.BIGINT};
		if (project.isPresent())
			jdbcTemplate.update(sql, param, types);
		
	}

	@Override
	public void update(Project project ) {
		final String sql = "UPDATE PROJECT  SET NAME='?', DESCRIPTION='?', STATUS='?', CREATION_DATE=?, UPDATE_DATE=?, "
				+ " END_DATE=? "
				+ " WHERE ID=?";
		Object[] param = { project.getName(), project.getDescription(), project.getStatus(), project.getCreationDate(), project.getUpdateDate(), project.getEndDate(),
				project.getId()};
		jdbcTemplate.update(sql, param);
		
	}

	@Override
	public Project create(Project project) {
		final String sql = "INSERT INTO "+TABLE+"(NAME, DESCRIPTION, STATUS, CREATION_DATE) VALUES (?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sql);
			ps.setString(1, project.getName());
			ps.setString(2, project.getDescription());
			ps.setString(3, project.getStatus());
			ps.setDate(4, project.getCreationDate());
			return ps;
		}, keyHolder);
		project.setId(keyHolder.getKey().longValue());
		return project;
	}

}
