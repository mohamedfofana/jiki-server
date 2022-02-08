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

import com.kodakro.jiki.model.Sprint;
import com.kodakro.jiki.repository.intrf.IGenericRepository;
import com.kodakro.jiki.repository.intrf.ISprintRepository;
import com.kodakro.jiki.repository.mapper.SprintRowMapper;
import com.kodakro.jiki.repository.request.AbstractSprintRequest;

@Repository
public class SprintRepository extends AbstractSprintRequest implements IGenericRepository<Sprint>, ISprintRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Value("${sql.sprint.insert}")
	private String sqlInsert;
	
	@Value("${sql.sprint.update}")
	private String sqlUpdate;
	
	@Value("${sql.sprint.delete}")
	private String sqlDelete;
	
	@Override
	public List<Sprint> findAll() {
		return jdbcTemplate.query(getJoinSelect(null), new SprintRowMapper());
	}
	
	@Override
	public Long maxId() {
		final Long maxId = jdbcTemplate.queryForObject(getMaxId(), null, null, Long.class );
		return maxId!=null?maxId:1;
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
	public boolean deleteById(Long id) {
		final String sql = "DELETE * FROM T_SPRINT WHERE SP.ID=?";
		Optional<Sprint> sprint = exists(id);
		Object[] param = {id};
		int[] types = {Types.BIGINT};
		if (sprint.isPresent())
			return jdbcTemplate.update(sql, param, types)==1;
		return false;
	}

	@Override
	public boolean update(Sprint sprint) {
		Object[] param = { sprint.getTitle(), sprint.getDescription(), sprint.getStatus(), sprint.getUpdateDate(),
				sprint.getBusinessValue(), sprint.getEndDate(),
				sprint.getId()};
		return jdbcTemplate.update(sqlUpdate, param)==1;
	}

	@Override
	public Sprint create(Sprint sprint) {
		sprint.setId(maxId()+1);

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sqlInsert);
			ps.setLong(1, sprint.getId());
			ps.setLong(2, sprint.getReporter().getId());
			ps.setLong(3, sprint.getProject().getId());
			ps.setString(4, sprint.getTitle());
			ps.setString(5, sprint.getDescription());
			ps.setString(6, sprint.getStatus());
			ps.setTimestamp(7, sprint.getCreationDate());
			ps.setInt(8, sprint.getBusinessValue());
			return ps;
		});
		return sprint;
	}

	@Override
	public Optional<Sprint> exists(Long id) {
		final String whereSql= " WHERE SP.ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Sprint sprint = null;
		try {
			sprint = jdbcTemplate.queryForObject(getExists(whereSql), param, types,
					new SprintRowMapper());
		}catch (EmptyResultDataAccessException e) {
			// log no entity found
		}
		return Optional.ofNullable(sprint);
	}


}
