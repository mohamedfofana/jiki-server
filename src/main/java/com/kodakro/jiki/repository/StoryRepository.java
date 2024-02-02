package com.kodakro.jiki.repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.helpers.TimeHelper;
import com.kodakro.jiki.model.Story;
import com.kodakro.jiki.repository.intrf.IGenericRepository;
import com.kodakro.jiki.repository.intrf.IStoryRepository;
import com.kodakro.jiki.repository.mapper.StoryRowMapper;
import com.kodakro.jiki.repository.request.AbstractStoryRequest;

@Repository
public class StoryRepository extends AbstractStoryRequest  implements IGenericRepository<Story>, IStoryRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Value("${sql.story.insert}")
	private String sqlInsert;
	
	@Value("${sql.story.update}")
	private String sqlUpdate;
	
	@Value("${sql.story.delete}")
	private String sqlDelete;
	
	@Override
	public List<Story> findAll() {
		return jdbcTemplate.query(getJoinSelect(null), new StoryRowMapper());
	}
	
	@Override
	public Long maxId() {
		final Long maxId = jdbcTemplate.queryForObject(getMaxId(), null, null, Long.class );
		return maxId!=null?maxId:1;
	}

	@Override
	public Optional<Story> exists(Long id) {
		final String whereSql= " WHERE ST.ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Story story = null;
		try {
			story = jdbcTemplate.queryForObject(getExists(whereSql), param, types,
					new StoryRowMapper());
		}catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("exists", "Story", "id", id);
		}
		return Optional.ofNullable(story);
	}
	
	@Override
	public Optional<Story> findById(Long id) {
		final String whereSql= " AND ST.ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Story story = null;
		try {
			story = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types,
					new StoryRowMapper());
		}catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("findById", "Story", "id", id);
		}
		return Optional.ofNullable(story);
	}
	
	@Override
	public List<Story> findStoriesOnBacklogsByProjectId(Long id) {
		final String whereSql= " AND ST.SPRINT_ID IS NULL AND ST.PROJECT_ID =? AND BA_ASS.ID IS NOT NULL";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		List<Story> stories = jdbcTemplate.query(getJoinSelectOnProjectBacklog(whereSql), param, types,
				new StoryRowMapper());
		return stories;
	}

	@Override
	public List<Story> findByProject(Long id) {
		final String whereSql= " AND ST.PROJECT_ID =?";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		List<Story> stories = jdbcTemplate.query(getJoinSelectOnProjectBacklog(whereSql), param, types,
				new StoryRowMapper());
		return stories;
	}
	
	@Override
	public List<Story> findByReporter(Long id) {
		final String whereSql= " AND ST.REPORTER_ID =?";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		List<Story> stories = jdbcTemplate.query(getJoinSelect(whereSql), param, types,
				new StoryRowMapper());
		return stories;
	}
	
	@Override
	public List<Story> findByProjectIdAndSprintId(Long projectId, Long sprintId) {
		final String whereProject= " AND ST.PROJECT_ID =?";
		Object[] param = {projectId, sprintId};
		int[] types = {Types.INTEGER, Types.INTEGER};
		final String whereSprint= " AND ST.SPRINT_ID =?";
		List<Story> stories = jdbcTemplate.query(getJoinSelectProjectSprint(whereProject, whereSprint), param, types,
				new StoryRowMapper());
		
		return stories;
	}
	
	@Override
	public List<Story> findBySprintId(Long id) {
		final String whereSql= " AND ST.SPRINT_ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		List<Story> stories = jdbcTemplate.query(getJoinSelectSprint(whereSql), param, types,
				new StoryRowMapper());
		
		return stories;
	}

	@Override
	public boolean deleteById(Long id) {
		final String sql = "DELETE * FROM "+TABLE+" WHERE ST.ID=?";
		Optional<Story> story = findById(id);
		Object[] param = {id};
		int[] types = {Types.BIGINT};
		if (story.isPresent())
			return jdbcTemplate.update(sql, param, types)==1;
		
		return false;
	}

	@Override
	public boolean update(Story story) {
		Object[] param = { 
				story.getTitle(), 
				story.getDescription(), 
				story.getStatus(), 
				story.getPriority(),  
				story.getStoryPoints(), 
				story.getBusinessValue(), 
				story.getAppliVersion(), 
				story.getStartDate(), 
				story.getUpdateDate(),
				story.getEndDate(),
				story.getEstimatedEndDate(),  
				story.getId()};
		
		return jdbcTemplate.update(sqlUpdate, param)==1;
	}

	@Override
	public void moveToBacklog(Long id, List<Story> stories) {
		StringBuilder ids = new StringBuilder();
		Long[] idStories = new Long[stories.size()];
		ids.append("?");
		idStories[0] = stories.get(0).getId();
		for (int i = 1; i < stories.size(); i++) {
			ids.append(",");
			ids.append("?");	
			idStories[i] = stories.get(i).getId();
		}
		final String sql = "UPDATE "+TABLE+" SET "
				+ "BACKLOG_ID=?, "
				+ "SPRINT_ID=NULL "
				+ "WHERE ID IN ("
				+ ids.toString()
				+ ")";
		Object[] param = new Object[stories.size()+1];
		param[0] = id;
		for (int i = 0; i < idStories.length; i++) {
			param[i+1] = idStories[i];
		}
		
		jdbcTemplate.update(sql, param);
	}

	@Override
	public void moveToSprint(Long id, List<Story> stories) {
		StringBuilder ids = new StringBuilder();
		Long[] idStories = new Long[stories.size()];
		ids.append("?");
		idStories[0] = stories.get(0).getId();
		for (int i = 1; i < stories.size(); i++) {
			ids.append(",");
			ids.append("?");	
			idStories[i] = stories.get(i).getId();
		}
		final String sql = "UPDATE "+TABLE+" SET "
				+ "SPRINT_ID=?, "
				+ "BACKLOG_ID=NULL "
				+ "WHERE ID IN ("
				+ ids.toString()
				+ ")";
		Object[] param = new Object[stories.size()+1];
		param[0] = id;
		for (int i = 0; i < idStories.length; i++) {
			param[i+1] = idStories[i];
		}
		
		jdbcTemplate.update(sql, param);
	}

	public void updateStatus(Story story) {
		final String sql = "UPDATE "+TABLE+" SET "
				+ "STATUS=?, "
				+ "UPDATE_DATE=? "
				+ "WHERE ID=?";
		Object[] param = { story.getStatus(), TimeHelper.timestampNow(), story.getId()};
		jdbcTemplate.update(sql, param);
	}

	@Override
	public void updateSprintAndBacklog(Story story) {
		final String sql = "UPDATE "+TABLE+" SET "
				+ "PROJECT_ID=?, "
				+ "BACKLOG_ID=?, "
				+ "SPRINT_ID=?, "
				+ "UPDATE_DATE=? "
				+ " WHERE ID=?";
		Long projectId = story.getProject()!=null? story.getProject().getId():null;
		Long backlogId = story.getBacklog()!=null? story.getBacklog().getId():null;
		Long sprintId = story.getSprint()!=null? story.getSprint().getId():null;
		Object[] param = { projectId, backlogId, sprintId, TimeHelper.timestampNow(), story.getId()};
		int[] types   = {  projectId==null?Types.NULL:Types.BIGINT, 
						   backlogId==null?Types.NULL:Types.BIGINT,
						   sprintId==null?Types.NULL:Types.BIGINT, 
						   Types.TIMESTAMP,	   
						   Types.BIGINT};
		jdbcTemplate.update(sql, param, types);
	}
	
	@Override
	public void updateField(Long id, Map<String, Object> fieldValueMap) {
		final String key = fieldValueMap.keySet().stream().findFirst().get();
		final Object value = fieldValueMap.get(key);
		final String sql = "UPDATE "+TABLE+" SET "
				+ key + "=?, "
				+ "UPDATE_DATE=? "
				+ " WHERE ID=?";
		Object[] param = { value, TimeHelper.timestampNow(), id};
		jdbcTemplate.update(sql, param);
		
	}

	@Override
	public Story create(Story story) {
		story.setId(maxId()+1);
	
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sqlInsert);
			ps.setLong(1, story.getId());
			ps.setLong(2, story.getReporter().getId());
			if(story.getSprint() != null) {
				ps.setLong(3, story.getSprint().getId());
			}else {
				ps.setNull(3,  Types.INTEGER);
			}
			ps.setLong(4, story.getProject().getId());
			if(story.getBacklog() != null) {
				ps.setLong(5, story.getBacklog().getId());
			}else {
				ps.setNull(5,  Types.INTEGER);
			}
			if(story.getAssignedUser() != null) {
				ps.setLong(6, story.getAssignedUser().getId());
			}else {
				ps.setNull(6,  Types.INTEGER);
			}
			ps.setString(7, story.getProject().getShortname() + "-" + story.getId());
			ps.setString(8, story.getTitle());
			ps.setString(9, story.getDescription());
			ps.setString(10, story.getType());
			ps.setString(11, story.getScope());
			ps.setString(12, story.getStatus());
			ps.setString(13, story.getPriority());
			ps.setInt(14, story.getStoryPoints());
			if(story.getAppliVersion() != null) {
				ps.setString(15, story.getAppliVersion());
			}else {
				ps.setNull(15,  Types.VARCHAR);
			}
			ps.setTimestamp(16, story.getCreationDate());
			return ps;
		});
		return story;
	}

	
	
}
