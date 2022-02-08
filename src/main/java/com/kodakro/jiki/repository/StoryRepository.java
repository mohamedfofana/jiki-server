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
			// log no Story found
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
			// log no Story found
		}
		return Optional.ofNullable(story);
	}
	
	@Override
	public List<Story> findStoriesOnBacklogsByProjectId(Long id) {
		final String whereSql= " AND ST.SPRINT_ID IS NULL AND ST.PROJECT_ID =? AND BA_ASS.ID IS NOT NULL";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		List<Story> stories = jdbcTemplate.query(getJoinSelectOnBacklogsProject(whereSql), param, types,
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
		final String sql = "UPDATE "+TABLE+" SET TITLE='?', DESCRIPTION='?', TYPE=?, STATUS='?', PRIORITY=?, WORKFLOW='?', CREATION_DATE=?, UPDATE_DATE=?, "
				+ " STORY_POINTS=?, BUSINESS_VALUE=?, APPLI_VERSION='?', START_DATE=?, END_DATE=?, ESTIMATED_END_DATE=? "
				+ " WHERE ID=?";
		Object[] param = { story.getTitle(), story.getDescription(), story.getType(), story.getStatus(), story.getPriority(), story.getCreationDate(), story.getUpdateDate(),
				story.getStoryPoints(), story.getBusinessValue(), story.getAppliVersion(), story.getStartDate(), story.getEndDate(),
				story.getEstimatedEndDate(),  story.getId()};
		return jdbcTemplate.update(sql, param)==1;
	}

	public void updateStatus(Story story) {
		final String sql = "UPDATE "+TABLE+" SET STATUS=? "
				+ " WHERE ID=?";
		Object[] param = { story.getStatus(), story.getId()};
		jdbcTemplate.update(sql, param);
	}

	@Override
	public void updateSprintAndBacklog(Story story) {
		final String sql = "UPDATE "+TABLE+" SET PROJECT_ID=?, BACKLOG_ID=?, SPRINT_ID=? "
				+ " WHERE ID=?";
		Long projectId = story.getProject()!=null? story.getProject().getId():null;
		Long backlogId = story.getBacklog()!=null? story.getBacklog().getId():null;
		Long sprintId = story.getSprint()!=null? story.getSprint().getId():null;
		Object[] param = { projectId, backlogId, sprintId, story.getId()};
		int[] types   = {  projectId==null?Types.NULL:Types.BIGINT, 
						   backlogId==null?Types.NULL:Types.BIGINT, 
						   sprintId==null?Types.NULL:Types.BIGINT, 
						   Types.BIGINT};
		jdbcTemplate.update(sql, param, types);
	}
	
	@Override
	public Story create(Story story) {
		final String sql = "INSERT INTO "+TABLE+"(ID,REPORTER_ID, SPRINT_ID, BACKLOG_ID, ASSIGNED_TEAM_ID, ASSIGNED_USER_ID, TITLE, DESCRIPTION, TYPE, STATUS, PRIORITY, WORKFLOW, "
				+ "CREATION_DATE, UPDATE_DATE, STORY_POINTS, BUSINESS_VALUE, APPLI_VERSION, START_DATE, "
				+ "END_DATE, ESTIMATED_END_DATE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		story.setId(maxId()+1);

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sql);
			ps.setLong(1, story.getId());
			ps.setLong(2, story.getReporter().getId());
			ps.setLong(3, story.getSprint().getId());
			ps.setLong(4, story.getBacklog().getId());
			ps.setLong(5, story.getAssignedTeam().getId());
			ps.setLong(6, story.getAssignedUser().getId());
			ps.setString(7, story.getTitle());
			ps.setString(8, story.getDescription());
			ps.setString(9, story.getType());
			ps.setString(10, story.getStatus());
			ps.setString(11, story.getPriority());
			ps.setString(12, story.getWorkflow());
			ps.setTimestamp(13, story.getCreationDate());
			ps.setTimestamp(14, story.getUpdateDate());
			ps.setInt(15, story.getStoryPoints());
			ps.setInt(16, story.getBusinessValue());
			ps.setString(17, story.getAppliVersion());
			ps.setTimestamp(18, story.getStartDate());
			ps.setTimestamp(19, story.getEndDate());
			ps.setTimestamp(20, story.getEstimatedEndDate());
			return ps;
		});
		return story;
	}

	
	
}
