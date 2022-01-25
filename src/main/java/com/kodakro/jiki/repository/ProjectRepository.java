package com.kodakro.jiki.repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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
	public Long maxId() {
		final Long maxId = jdbcTemplate.queryForObject(getMaxId(), null, null, Long.class );
		return maxId!=null?maxId:1;
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
	public boolean deleteById(Long id) {
		final String sql = "DELETE FROM T_PROJECT WHERE ID=?";
		Optional<Project> project = exists(id);
		Object[] teamId = new Object[] {id};
		if (project.isPresent()) {
		    return jdbcTemplate.update(sql, teamId)==1;
		}
		return false;
	}

	@Override
	public boolean update(Project project ) {
		final String sql = "UPDATE T_PROJECT SET NAME=?, DESCRIPTION=?, STATUS=?, TEAM_ID=?, UPDATE_DATE=? "
				+ " WHERE ID=?";
		Object[] param = { project.getName(), project.getDescription(), project.getStatus(), project.getTeam().getId(),LocalDate.now(), 
				project.getId()};
		return jdbcTemplate.update(sql, param)==1;
		
	}

	@Override
	public Project create(Project project) {
		final String sql = "INSERT INTO T_PROJECT (ID, NAME, DESCRIPTION, STATUS, TEAM_ID, BACKLOG_ID, CREATION_DATE) VALUES (?,?,?,?,?,?,?)";
		project.setId(maxId()+1);

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sql);
			ps.setLong(1, project.getId());
			ps.setString(2, project.getName());
			ps.setString(3, project.getDescription());
			ps.setString(4, project.getStatus());
			ps.setLong(5, project.getTeam().getId());
			ps.setLong(6, project.getBacklog().getId());
			ps.setTimestamp(7, project.getCreationDate());
			return ps;
		});
		return project;
	}

	@Override
	public Optional<Project> exists(Long id) {
		final String whereSql= " WHERE PR.ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Project project = null;
		try {
			project = jdbcTemplate.queryForObject(getExists(whereSql), param, types,
					new ProjectRowMapper());
		}catch (EmptyResultDataAccessException e) {
			// log no user found
		}
		return Optional.ofNullable(project);
	}

}
