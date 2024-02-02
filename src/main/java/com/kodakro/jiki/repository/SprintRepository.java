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

import com.kodakro.jiki.enums.SprintStatusEnum;
import com.kodakro.jiki.exception.ResourceNotFoundException;
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
	
	@Value("${sql.sprint.start}")
	private String sqlStart;

	@Value("${sql.sprint.close}")
	private String sqlClose;
	
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
			throw new ResourceNotFoundException("findById", "Sprint", "id", id);
		}
		return Optional.ofNullable(sprint);
	}

	@Override
	public Optional<Sprint> findCurrentByProjectId(Long id) {
		Sprint sprint = null;
		Optional<Sprint> runningSprint =  findRunningByProjectId(id);
		if(runningSprint.isEmpty()) {
			final String whereSql= " AND PR.ID =? "
					+ "AND SP.STATUS = '"+ SprintStatusEnum.CREATED + "'";
			Object[] param = {id};
			int[] types = {Types.INTEGER};
			try {
				sprint = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types,
						new SprintRowMapper());			
			}catch(EmptyResultDataAccessException e) {
				throw new ResourceNotFoundException("findCurrentByProjectId", "Sprints.Project", "id", id);
			}
		}else {
			sprint = runningSprint.get();
		}
		
		return Optional.ofNullable(sprint);
	}

	@Override
	public Optional<Sprint> findRunningByProjectId(Long id) {
		final String whereSql= " AND PR.ID =? "
				+ "AND SP.STATUS IN ('" + SprintStatusEnum.IN_PROGRESS + "')";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Sprint sprint = null;
		try {
			sprint = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types,
					new SprintRowMapper());			
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("findRunningByProjectId", "Sprints.Project", "id", id);
		}
		return Optional.ofNullable(sprint);
	}
	
	@Override
	public List<Sprint> findByProjectId(Long id) {
		final String whereSql= " AND PR.ID =?";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		List<Sprint> sprints = null;
		sprints = jdbcTemplate.query(getJoinSelect(whereSql), param, types,
					new SprintRowMapper());			
		return sprints;
	}
	
	@Override
	public boolean deleteById(Long id) {
		Optional<Sprint> sprint = exists(id);
		Object[] param = {id};
		int[] types = {Types.BIGINT};
		if (sprint.isPresent())
			return jdbcTemplate.update(sqlDelete, param, types)==1;
		return false;
	}

	@Override
	public boolean start(Sprint sprint) {
		Object[] param = { 
				sprint.getStatus(), 
				sprint.getName(), 
				sprint.getGoal(), 
				sprint.getDuration(), 
				sprint.getBusinessValue(), 
				sprint.getStartDate(),
				sprint.getUpdateDate(),
				sprint.getEndDate(),
				sprint.getId()};
		return jdbcTemplate.update(sqlStart, param)==1;
	}
	
	@Override
	public boolean update(Sprint sprint) {
//		Object[] param = { 
//				SprintStatusEnum.RUNNING, 
//				sprint.getBusinessValue(), 
//				TimeHelper.timestampNow(),
//				TimeHelper.timestampNow(),
//				Timestamp.valueOf(LocalDate.now().plusWeeks(3).atStartOfDay()),
//				sprint.getId()};
//		return jdbcTemplate.update(sqlUpdate, param)==1;
		return true;
	}

	@Override
	public Sprint create(Sprint sprint) {
		sprint.setId(maxId()+1);
		sprint.setStatus(SprintStatusEnum.CREATED.toString());
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
			throw new ResourceNotFoundException("exists", "Sprints.Project", "id", id);
		}
		return Optional.ofNullable(sprint);
	}

	@Override
	public List<Sprint> findByStatusInProject(Long id, String status) {
			final String whereSql= " AND PR.ID =? AND SP.STATUS =? ";
			Object[] param = {id, status};
			int[] types = {Types.INTEGER, Types.VARCHAR};
			
			List<Sprint> sprints = null;
			sprints = jdbcTemplate.query(getJoinSelect(whereSql), param, types,
					new SprintRowMapper());		
			
			return sprints;
	}

	@Override
	public boolean close(Sprint sprint) {
		Object[] param = { 
				sprint.getStatus(), 
				sprint.getUpdateDate(),
				sprint.getEndDate(),
				sprint.getId()};
		
		return jdbcTemplate.update(sqlClose, param)==1;
	}


}
