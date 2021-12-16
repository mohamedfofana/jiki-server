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

import com.kodakro.jiki.model.Sprint;
import com.kodakro.jiki.repository.mapper.SprintRowMapper;
import com.kodakro.jiki.repository.request.AbstractSprintRequest;

@Repository
public class SprintRepository extends AbstractSprintRequest implements IGenericRepository<Sprint>, IGenericSprintRepository<Sprint> {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Sprint> findAll() {
		return jdbcTemplate.query(getJoinSelect(null), new SprintRowMapper());
	}

	@Override
	public Optional<Sprint> findById(Long id) {
		final String whereSql= " AND SP.ID =?";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Sprint sprint = null;
		try {
			sprint = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types,
				new SprintRowMapper());
		}catch(EmptyResultDataAccessException e) {
			// log No sprint found
		}
		return Optional.ofNullable(sprint);
	}

	@Override
	public Optional<Sprint> findCurrentByProjectId(Long id) {
		final String whereSql= " AND PR.ID =? AND SP.STATUS='RUNNING'";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Sprint sprint = null;
		try {
			sprint = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types,
					new SprintRowMapper());			
		}catch(EmptyResultDataAccessException e) {
			// log No sprint found
		}
		return Optional.ofNullable(sprint);
	}
	
	@Override
	public List<Sprint> findByProjectId(Long id) {
		final String whereSql= " AND PR.ID =?";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		List<Sprint> sprint = null;
		sprint = jdbcTemplate.query(getJoinSelect(whereSql), param, types,
					new SprintRowMapper());			
		return sprint;
	}
	
	@Override
	public void deleteById(Long id) {
		final String sql = "DELETE * FROM "+ TABLE +" WHERE SP.ID=?";
		Optional<Sprint> sprint = findById(id);
		Object[] param = {id};
		int[] types = {Types.BIGINT};
		if (sprint.isPresent())
			jdbcTemplate.update(sql, param, types);
	}

	@Override
	public void update(Sprint sprint) {
		final String sql = "UPDATE SPRINT SET TITLE='?', DESCRIPTION='?', STATUS='?', WORKFLOW='?', CREATION_DATE=?, UPDATE_DATE=?, "
				+ " STORY_POINTS=?, BUSINESS_VALUE=?, APPLI_VERSION='?', START_DATE=?, END_DATE=?, ESTIMATED_END_DATE=? "
				+ " WHERE ID=?";
		Object[] param = { sprint.getTitle(), sprint.getDescription(), sprint.getStatus(), sprint.getCreationDate(), sprint.getUpdateDate(),
				sprint.getStoryPoints(), sprint.getBusinessValue(), sprint.getAppliVersion(), sprint.getStartDate(), sprint.getEndDate(),
				sprint.getEstimatedEndDate(),  sprint.getId()};
		jdbcTemplate.update(sql, param);
	}

	@Override
	public Sprint create(Sprint sprint) {
		final String sql = "INSERT INTO SPRINT(REPORTER_ID, PROJECT_ID, TITLE, DESCRIPTION, STATUS, WORKFLOW, "
				+ "CREATION_DATE, UPDATE_DATE, STORY_POINTS, BUSINESS_VALUE, APPLI_VERSION, START_DATE, "
				+ "END_DATE, ESTIMATED_END_DATE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sql);
			ps.setLong(1, sprint.getReporter().getId());
			ps.setLong(2, sprint.getProject().getId());
			ps.setString(3, sprint.getTitle());
			ps.setString(4, sprint.getDescription());
			ps.setString(5, sprint.getStatus());
			ps.setDate(6, sprint.getCreationDate());
			ps.setInt(7, sprint.getStoryPoints());
			ps.setInt(8, sprint.getBusinessValue());
			ps.setString(9, sprint.getAppliVersion());
			ps.setDate(10, sprint.getStartDate());
			ps.setDate(11, sprint.getEndDate());
			ps.setDate(12, sprint.getEstimatedEndDate());
			return ps;
		}, keyHolder);
		sprint.setId((long) keyHolder.getKey());
		return sprint;
	}

	@Override
	public Optional<Sprint> exists(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


}
