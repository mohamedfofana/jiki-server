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

import com.kodakro.jiki.enums.ProjectStatusEnum;
import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.repository.intrf.IGenericRepository;
import com.kodakro.jiki.repository.intrf.IProjectRepository;
import com.kodakro.jiki.repository.mapper.ProjectRowMapper;
import com.kodakro.jiki.repository.request.AbstractProjectRequest;

@Repository
public class ProjectRepository extends AbstractProjectRequest implements IGenericRepository<Project>, IProjectRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Value("${sql.project.insert}")
	private String sqlInsert;
	
	@Value("${sql.project.update}")
	private String sqlUpdate;
	
	@Value("${sql.project.delete}")
	private String sqlDelete;
	
	@Override
	public List<Project> findAll() {
		return jdbcTemplate.query(getJoinSelect(null), new ProjectRowMapper());
	}

	@Override
	public Project findByTeam(Long id) {
		final String whereSql= " WHERE PR.TEAM_ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Project project = null;
		try {		
			project = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types, new ProjectRowMapper());
		}catch(EmptyResultDataAccessException e) {
			// Log no project found
		}
		return project;
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
		Optional<Project> project = exists(id);
		Object[] teamId = new Object[] {id};
		if (project.isPresent()) {
		    return jdbcTemplate.update(sqlDelete, teamId)==1;
		}
		return false;
	}

	@Override
	public boolean update(Project project ) {
		Object[] param = { 
				project.getShortname(), 
				project.getName(), 
				project.getDescription(), 
				project.getStatus(), 
				project.getTeam().getId(),
				project.getUpdateDate(), 
				project.getId()};
		return jdbcTemplate.update(sqlUpdate, param)==1;
		
	}

	@Override
	public Project create(Project project) {
		project.setId(maxId()+1);
		project.setStatus(ProjectStatusEnum.CREATED.toString());
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sqlInsert);
			ps.setLong(1, project.getId());
			ps.setString(2, project.getShortname());
			ps.setString(3, project.getName());
			ps.setString(4, project.getDescription());
			ps.setString(5, project.getStatus());
			ps.setLong(6, project.getTeam().getId());
			ps.setLong(7, project.getBacklog().getId());
			ps.setTimestamp(8, project.getCreationDate());
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
